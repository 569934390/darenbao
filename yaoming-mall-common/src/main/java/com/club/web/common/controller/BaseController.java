package com.club.web.common.controller;

import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.club.framework.util.DBUtils;
import com.club.web.common.vo.DBColumn;
import com.club.web.common.vo.DBTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.core.common.TreeNode;
import com.club.core.common.TreePO;
import com.club.core.common.TreeQueryPO;
import com.club.core.spring.context.CustomPropertyConfigurer;
import com.club.core.spring.context.SpringApplicationContextHolder;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.framework.util.ValidateUtils;
import com.club.web.common.cache.CodeValueCache;
import com.club.web.common.cache.DBMetaCache;
import com.club.web.common.db.po.CodeValuePO;
import com.club.web.common.message.MessageCell;
import com.club.web.common.message.MessageHeart;
import com.club.web.common.service.IBaseService;

/**
 * <Description>自动生成代码 <br>
 * 
 * @author lifei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年12月11日 <br>
 * @since V1.0<br>
 */

@Controller
@RequestMapping("/base")
public class BaseController {

	public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";//放到session中的key
	
    private static final ClubLogManager logger = ClubLogManager.getLogger(BaseController.class);

	@Value("#{propertyConfigurer.ctxPropertiesMap['framework.jdbc.driverClassName']}")
	private String driver;

	@Value("#{propertyConfigurer.ctxPropertiesMap['framework.jdbc.url']}")
	private String url;

	@Value("#{propertyConfigurer.ctxPropertiesMap['framework.jdbc.username']}")
	private String username;

	@Value("#{propertyConfigurer.ctxPropertiesMap['framework.jdbc.password']}")
	private String password;

    @Autowired
    private IBaseService baseService;
    
    @RequestMapping("getList")
	@ResponseBody
	public List<?> getList(String tableName,String conditionsKey) throws BaseAppException {
		Map<String,Object> conditions;
		if(StringUtils.isEmpty(conditionsKey)){
			conditions = new HashMap<String, Object>();
		}
		else{
			conditions = JsonUtil.toMap(conditionsKey);
		}
		List<?> list = baseService.selectList(tableName, conditions);
		return list;
	}

    @RequestMapping("getPage")
	@ResponseBody
	public Page<?> getPage(String tableName,String conditionsKey) throws BaseAppException {
		Map<String,Object> conditions;
		if(StringUtils.isEmpty(conditionsKey)){
			conditions = new HashMap<String, Object>();
		}
		else{
			conditions = JsonUtil.toMap(conditionsKey);
		}
		Page<?> list = baseService.selectPage(tableName, conditions);
		return list;
	}

	@RequestMapping("tableInfo/{tableName}")
	@ResponseBody
	public DBTable tableInfo(@PathVariable String tableName) throws BaseAppException {
		DBTable dbtable = DBUtils.getTalbe(tableName.toUpperCase());
		DBTable table = new DBTable();
		table.setTableName(dbtable.getTableName());
		table.setRemarks(dbtable.getRemarks());
		table.setPks(dbtable.getPks());
		table.setFks(dbtable.getFks());
		List<DBColumn> columns = new ArrayList<DBColumn>();
		for (DBColumn column : dbtable.getColumns()){
			DBColumn c = new DBColumn();
			c.setColumnName(StringUtils.toHump(column.getColumnName()));
			c.setDbType(column.getDbType());
			c.setDisplayName(column.getDisplayName());
			c.setType(column.getType());
			c.setIsNull(column.getIsNull());
			c.setLength(column.getLength());
			columns.add(c);
		}
		table.setColumns(columns);
		return table;
	}
	
//	@RequestMapping("update")
//	@ResponseBody
//	public Map<String,Object> update(String tableName,String entityStr){
//		Map<String,Object> result = new HashMap<String, Object>();
//		try{
//			result.put("success", true);
//			result.put("message", baseCommonService.update(tableName, JsonUtils.toMap(entityStr)));
//		}catch(Exception e){
//			e.printStackTrace();
//			result.put("success", false);
//			result.put("message", e.getMessage()+e.toString());
//		}
//		return result;
//	}
//	@RequestMapping("delete")
//	@ResponseBody
//	public Map<String,Object> delete(String tableName,String ids){
//		Map<String,Object> result = new HashMap<String, Object>();
//		try{
//			result.put("success", true);
//			result.put("message", baseCommonService.delete(tableName, ids));
//		}catch(Exception e){
//			e.printStackTrace();
//			result.put("success", false);
//			result.put("message", e.getMessage()+e.toString());
//		}
//		return result;
//	}
	
//	@RequestMapping("load")
//	@ResponseBody
//	public Map<String,Object> load(String tableName,String id){
//		Map<String,Object> result = new HashMap<String, Object>();
//		try{
//			result.put("success", true);
//			result.put("message", baseService.selectOne(tableName, id));
//		}catch(Exception e){
//			e.printStackTrace();
//			result.put("success", false);
//			result.put("message", e.getMessage()+e.toString());
//			logger.error(e);
//		}
//		return result;
//	}
	
//	@RequestMapping("updateItems")
//	@ResponseBody
//	public Map<String,Object> updateItems(String tableName,String entityStr){
//		Map<String,Object> result = new HashMap<String, Object>();
//		try{
//			result.put("success", true);
//			result.put("message", baseCommonService.updateItems(tableName, JsonUtils.toMap(entityStr)));
//		}catch(Exception e){
//			e.printStackTrace();
//			result.put("success", false);
//			result.put("message", e.getMessage()+e.toString());
//		}
//		return result;
//	}
	
	
	@RequestMapping("proxy")
	public void proxy(String strURL,HttpServletResponse response){
		try{
			Writer writer = response.getWriter();
			URL url = new URL(strURL);   
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();   
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String temp;
            int i=0;
            while((temp = br.readLine()) != null){
            	if(i++==6)
            		writer.append("<meta http-equiv=\"Access-Control-Allow-Origin\" content=\"*\" />");
                writer.append(temp+"\n");
            }      
            br.close();   
            isr.close();   
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("testDBConnection")
	@ResponseBody
	public Map<String,Object> testDBConnection(String driver,String dbName,String characterEncoding,String ip,String port,String params,String username,String password){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("message", baseService.testDBConnection(driver, dbName, characterEncoding, ip, port, params, username, password));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage() + e.toString());
		}
		return result;
	}
	
	@RequestMapping("loadDBMeta")
	@ResponseBody
	public Map<String,Object> loadDBMeta(){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("message", DBMetaCache.getMeta(true));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage()+e.toString());
		}
		return result;
	}
	
	@RequestMapping("saveDBMeta")
	@ResponseBody
	public Map<String,Object> saveDBMeta(String driver,String dbName,String characterEncoding,String ip,String port,String params,String username,String password){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			if(driver==null||driver.isEmpty()){
				baseService.saveDBMeta(this.driver,this.url,this.username,this.password);
			}
			result.put("message", baseService.saveDBMeta(driver, dbName, characterEncoding, ip, port, params, username, password));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage() + e.toString());
		}
		return result;
	}
	
	@RequestMapping("randCodeImage")
	public void randCodeImage(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		Object[] results = ValidateUtils.getRandcode();
		session.removeAttribute(RANDOMCODEKEY);
        session.setAttribute(RANDOMCODEKEY, results[0]);
        try {
            ImageIO.write((RenderedImage) results[1], "JPEG", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void getfFileToClient(HttpServletResponse response, String pic_path) throws IOException {
		FileInputStream is;
		is = new FileInputStream(pic_path);
		int i = is.available(); // 得到文件大小
		byte data[] = new byte[i];
		is.read(data); // 读数据
		is.close();
		response.setContentType("image/png"); // 设置返回的文件类型
		OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
		toClient.write(data); // 输出数据
		toClient.close();
	}

	@RequestMapping("showImage")
	public void showImage(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		String fullPath = request.getParameter("fullPath");// 图片路径
		if (StringUtils.isEmpty(fullPath)) {
			response.sendError(405, "filePath is null!");
			return;
		}
		fullPath = request.getSession().getServletContext().getRealPath("/") + fullPath;// 图片路径
		try {
			getfFileToClient(response, fullPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("getGeneralTree")
	@ResponseBody
	public TreeNode getGeneralTree(String paramMap,String node){
	    TreePO params = JsonUtil.toBean(paramMap,TreePO.class);
		Map<String,Object> data = JsonUtil.toMap(paramMap);
		String valueField = data.get("valueField").toString();
		if(StringUtils.isEmpty(params.getDeep())){
		    params.setDeep(10);
		}
		Object rootId = data.get("rootId");
		data.put(valueField, rootId);

		if(!StringUtils.isEmpty(node)){
		    data.put(valueField, node);
		}
		String unChangeParamStr = params.getUnChangeParamStr();
		if(!StringUtils.isEmpty(unChangeParamStr)){
		    String[] temp = unChangeParamStr.split(",");
		    Map<String,Object> paramData = new HashMap<String, Object>();
		    for (int i = 0; i < temp.length; i++) {
		        paramData.put(temp[i], data.get(temp[i]));
            }
		    params.setParamData(paramData);
		}
		String iconStr = params.getIconStr();
        if(!StringUtils.isEmpty(iconStr)){
            params.setIcons(iconStr.split(","));
        }else{
            params.setIcons(new String[0]);
        }
        TreeNode tree =  baseService.getTreeAllData(params,data,0);
		return tree;
	}
	
	@RequestMapping("queryTree")
    @ResponseBody
    public TreeNode queryTree(TreeQueryPO reqDto){
        return baseService.queryTree(reqDto);
    }
	
	@RequestMapping("getDataList")
    @ResponseBody
    public List<Map<String,Object>> getDataList(String sqlKey,String paramMap){
	    Map<String,Object> params = JsonUtil.toMap(paramMap);
//	    String realKey = Configuration.getString(sqlKey);
        return baseService.selectList2(sqlKey, params);
    }

    @RequestMapping("getCodeValues")
    @ResponseBody
    public List<CodeValuePO> getCodeValues(String codeType) throws BaseAppException{
        return CodeValueCache.findCodeValues(codeType);
    }

    @RequestMapping("refreshCodeValues")
    @ResponseBody
    public void refreshCodeValues(String codeType) throws BaseAppException{
        CodeValueCache cache = SpringApplicationContextHolder.getBean(CodeValueCache.class);
        cache.refresh(codeType);
    }

//	@RequestMapping("sendMessage.do")
//	public void send(String channel,String message,HttpServletResponse response)throws IOException {
//		MessageHeart.getInstance().send(channel,message);
//		response.getWriter().write("jsonData({success:true})");
//		response.getWriter().flush();
//		return;
//	}
//	@RequestMapping("message.do")
//	public void message(String uuid,String chanelStr,HttpServletResponse response) throws InterruptedException, IOException {
//		Map<String,Object> result = new HashMap<String, Object>();
//		Long startTime = System.currentTimeMillis();
//		while(System.currentTimeMillis()-startTime<59000){
//			String[] chanels = chanelStr.split(",");
//			List<MessageCell> cells = MessageHeart.getInstance().loadData(uuid,chanels);
//			if(cells!=null&&cells.size()>0){
//				result.put("success",true);
//				result.put("cells",cells);
//				response.getWriter().write("jsonData("+JsonUtil.toJson(result)+")");
//				response.getWriter().flush();
//				return;
//			}
//			Thread.sleep(100);
//		}
//		result.put("success",false);
//		response.getWriter().write("jsonData("+JsonUtil.toJson(result)+")");
//		response.getWriter().flush();
//		return;
//	}
}
