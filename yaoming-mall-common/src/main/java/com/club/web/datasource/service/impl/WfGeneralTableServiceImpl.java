/**
 * 
 */
package com.club.web.datasource.service.impl;


import com.club.core.common.Page;
import com.club.core.convert.IArgConversionService;
import com.club.core.idproduce.ISequenceGenerator;
import com.club.web.datasource.db.arg.WfGeneralTableArg;
import com.club.web.datasource.db.dao.WfGeneralTableDao;
import com.club.web.datasource.db.po.WfGeneralTablePO;
import com.club.web.datasource.service.IWfGeneralTableService;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;



@Service("wfGeneralTableService")
public class WfGeneralTableServiceImpl implements IWfGeneralTableService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(WfGeneralTableServiceImpl.class);

    @Autowired
    private WfGeneralTableDao wfGeneralTableDao;
    

    /**
     * 查询条件转换成Arg类的服务接口
     */
    @Resource(name = "defaultArgConversionService")
    private IArgConversionService argConversionService;

    /**
     * 主键生成�?
     */
    @Resource(name = "sequenceProcGenerator")
    private ISequenceGenerator sequenceGenerator;
    

    @Override
    public WfGeneralTablePO selectByPrimaryKey(Integer key) throws BaseAppException {
        // ///////
        // TODO 根据业务场景，设置查询条件�?�数据校验等

        // ///////
        return wfGeneralTableDao.selectByPrimaryKey(key);
    }

    @Override
    public List<WfGeneralTablePO> selectByArg(WfGeneralTablePO record) throws BaseAppException {
        logger.debug("selectByArg begin...record={0}", record);

        // 第一种方式：自己创建arg，自行设置查询条件及操作�?
        //WfGeneralTableArg arg = new WfGeneralTableArg();
        //WfGeneralTableCriteria criteria = arg.createCriteria();
        
        // 第二种方式：利用arg转换服务，转换出arg，带上查询条件及操作符，
        // 转换后，还可以自行对arg进行设置修改
        WfGeneralTableArg arg = argConversionService.invokeArg(WfGeneralTableArg.class, record);

        // ///////
        // TODO 根据业务场景，设置查询条件，示例
        // if (Utils.notEmpty(record.getUserName())) {
        // criteria.andUserNameLike(record.getUserName());
        // }
        // ///////

        return wfGeneralTableDao.selectByArg(arg);
    }

    @Override
    public Page<WfGeneralTablePO> selectByArgAndPage(WfGeneralTablePO record, Page<WfGeneralTablePO> resultPage)
            throws BaseAppException {
        logger.debug("selectByArgAndPage begin...record={0}", record);

        // 第一种方式：自己创建arg，自行设置查询条件及操作�?
        // WfGeneralTableArg arg = new WfGeneralTableArg();
        // //TODO 根据业务场景，设置查询条件，示例
        // WfGeneralTableCriteria criteria = arg.createCriteria();
        // if (Utils.notEmpty(record.getUserName())) {
        // criteria.andUserNameLike(record.getUserName());
        // }

        // 第二种方式：利用arg转换服务，转换出arg，带上查询条件及操作符，
        // 转换后，还可以自行对arg进行设置修改
        WfGeneralTableArg arg = argConversionService.invokeArg(WfGeneralTableArg.class, record);

        resultPage = wfGeneralTableDao.selectByArgAndPage(arg, resultPage);


        return resultPage;
    }

    @Override
    public int add(WfGeneralTablePO record) throws BaseAppException {
        logger.debug("add begin...record={0}", record);

        // ///////
        // TODO 根据业务场景，进行重名校验�?�设置主键�?�设置属性默认�?�等
        // 获取主键
        // int pkId = sequenceGenerator.sequenceIntValue("表名","主键�?");
        // record.setUserId(pkId);
        // record.setCreatedDate(new Date());
        // ///////

        return wfGeneralTableDao.insertSelective(record);
    }

    @Override
    public int update(WfGeneralTablePO record) throws BaseAppException {
        logger.debug("update begin...record={0}", record);

        // ///////
        // TODO 根据业务场景，进行重名校验�?�数据有效�?�校验�?�设置属性默认�?�等

        // ///////

        return wfGeneralTableDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(WfGeneralTablePO record) throws BaseAppException {
        logger.debug("delete begin...record={0}", record);

        // ///////
        // TODO 根据业务场景，进行关联�?�校验�?�关联删除等

        // ///////

        return wfGeneralTableDao.deleteByPrimaryKey(record.getTableId());
    }

}
