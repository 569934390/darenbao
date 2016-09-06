package com.club.web.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.StringUtils;
import com.club.web.common.db.arg.WfDbColumnsArg;
import com.club.web.common.db.arg.WfDbMetaArg;
import com.club.web.common.db.arg.WfDbTableArg;
import com.club.web.common.db.dao.WfDbColumnsDao;
import com.club.web.common.db.dao.WfDbMetaDao;
import com.club.web.common.db.dao.WfDbTableDao;
import com.club.web.common.db.po.WfDbColumnsPO;
import com.club.web.common.db.po.WfDbMetaPO;
import com.club.web.common.db.po.WfDbTablePO;
import com.club.web.common.service.IBaseService;
import com.club.web.common.vo.DBColumn;
import com.club.web.common.vo.DBMeta;
import com.club.web.common.vo.DBTable;

/**
 * create by lf
 * create date 2015-6-30
 */
public class DBMetaCache {
	private static final ClubLogManager logger = ClubLogManager
			.getLogger(DBMetaCache.class);
	@Autowired
	private IBaseService baseService;

	@Autowired
	private WfDbMetaDao metaDao;

	@Autowired
	private WfDbTableDao tableDao;

	@Autowired
	private WfDbColumnsDao columnsDao;

	private static DBMeta meta;
	
	private static Map<String,DBTable> tables = new HashMap<String,DBTable>();

	@Value("#{propertyConfigurer.ctxPropertiesMap['framework.jdbc.driverClassName']}")
	private String driver;

	@Value("#{propertyConfigurer.ctxPropertiesMap['framework.jdbc.url']}")
	private String url;

	@Value("#{propertyConfigurer.ctxPropertiesMap['framework.jdbc.username']}")
	private String username;

	@Value("#{propertyConfigurer.ctxPropertiesMap['framework.jdbc.password']}")
	private String password;

	@Value("#{propertyConfigurer.ctxPropertiesMap['auto_flush_tables_cache']}")
	private String autoFlushCache;


	private void putMap(Map<String, List<WfDbColumnsPO>> map, WfDbColumnsPO po){
		if(map==null || po==null)
			return;
		String tableName = po.getTableName();
		if(tableName==null)
			return;
		if(!map.containsKey(tableName))
			map.put(tableName, new ArrayList<>());
		map.get(tableName).add(po);
	}
	
	public void init() throws BaseAppException{

		List<WfDbMetaPO> metas = metaDao.selectByArg(new WfDbMetaArg());

		if (metas.size()>0){
			meta = DBMeta.parse(metas.get(0));
		}
		WfDbTableArg tableArg = new WfDbTableArg();

		tableArg.setOrderByClause(" remarks desc ");

		List<WfDbTablePO> tablePOss = tableDao.selectByArg(tableArg);

		WfDbColumnsArg arg = new WfDbColumnsArg();
		List<WfDbColumnsPO> columnsPOs = columnsDao.selectByArg(arg);
		
		Map<String, List<WfDbColumnsPO>> tempMap = new HashMap<>();
		for (WfDbColumnsPO columnsPO : columnsPOs)
			putMap(tempMap, columnsPO);
		
		for(WfDbTablePO tablePO : tablePOss){
			DBTable table = DBTable.parse(tablePO);
			List<WfDbColumnsPO> tempList = tempMap.get(table.getTableName());
			if(tempList!=null)
				for (WfDbColumnsPO columnsPO : tempList){
					DBColumn column = DBColumn.parse(columnsPO);
					table.getColumns().add(column);
					table.getColumnMap().put(column.getColumnName(),column);
					table.getColumnMap().put(column.getColumnName().toUpperCase(),column);
					table.getColumnMap().put(StringUtils.toHump(column.getColumnName()), column);
					if(column.getType()!=null&&column.getType().indexOf("pk")!=-1){
						table.getPks().add(column.getColumnName());
					}
					if(column.getType()!=null&&column.getType().indexOf("fk")!=-1){
						table.getFks().add(column.getColumnName());
					}
				}
			tables.put(table.getTableName().toUpperCase(), table);
			meta.getTables().add(table);
		}
		//如果WF_DB_TALBES表为空
		if (tablePOss.size()==0){
			logger.info("table count == 0 auto run saveDBMeta driver:"+driver+",url:"+url+",username:"+username+",password:"+password);
			baseService.saveDBMeta(driver, url, username, password);
		}
		else {
			//如果配置自动刷新
			if(autoFlushCache!=null&&autoFlushCache.equals("true")) {
				logger.info("autoFlushCache is true auto run saveDBMeta driver:"+driver+",url:"+url+",username:"+username+",password:"+password);
				new Thread(new Runnable() {
					@Override
					public void run() {
					try {
						baseService.saveDBMeta(driver, url, username, password);
					} catch (BaseAppException e) {
						e.printStackTrace();
						logger.error(e.getMessage()+"["+e.toString()+"]");
					}
					}
				}).start();
			}
		}
	}

	public static DBMeta getMeta() {
		return meta;
	}

	public static DBMeta getMeta(boolean filter) {
		DBMeta _meta = new DBMeta();
		_meta.setDbName(meta.getDbName());
		_meta.setType(meta.getType());
		for(DBTable table : meta.getTables()){
			if(table.getSource()!=null&&!table.getSource().toUpperCase().equals("SYS")) {
				_meta.getTables().add(table);
			}
		}
		return _meta;
	}
	
	public static DBTable getTable(String tableName) {
		return tables.get(tableName.toUpperCase());
	}

	public static void setMeta(DBMeta meta) {
		DBMetaCache.meta = meta;
		tables = new HashMap<String, DBTable>();
		for (DBTable table : meta.getTables()){
			tables.put(table.getTableName().toUpperCase(),table);
		}
	}
	
}
