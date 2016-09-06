/**
 * 
 */
package com.club.web.datasource.service.impl;


import com.club.core.common.Page;
import com.club.core.convert.IArgConversionService;
import com.club.core.idproduce.ISequenceGenerator;
import com.club.web.datasource.db.arg.WfDataSetArg;
import com.club.web.datasource.db.dao.WfDataSetDao;
import com.club.web.datasource.db.po.WfDataSetPO;
import com.club.web.datasource.service.IWfDataSetService;
import com.club.framework.exception.BaseAppException;
import com.club.framework.log.ClubLogManager;
import com.club.web.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <Description> <br>
 * 
 * @author codeCreater<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014�?11�?11�? <br>
 * @since V1.0<br>
 * @see com.club.web.datasource.service.impl <br>
 */

@Service("wfDataSetService")
public class WfDataSetServiceImpl implements IWfDataSetService {

    private static final ClubLogManager logger = ClubLogManager
            .getLogger(WfDataSetServiceImpl.class);

    @Autowired
    private WfDataSetDao wfDataSetDao;
    

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
    public WfDataSetPO selectByPrimaryKey(Integer key) throws BaseAppException {
        return wfDataSetDao.selectByPrimaryKey(key);
    }

    @Override
    public List<WfDataSetPO> selectByArg(WfDataSetPO record) throws BaseAppException {
        logger.debug("selectByArg begin...record={0}", record);

        WfDataSetArg arg = argConversionService.invokeArg(WfDataSetArg.class, record);
        return wfDataSetDao.selectByArg(arg);
    }

    @Override
    public Page<WfDataSetPO> selectByArgAndPage(WfDataSetPO record, Page<WfDataSetPO> resultPage)
            throws BaseAppException {
        logger.debug("selectByArgAndPage begin...record={0}", record);

        WfDataSetArg arg = new WfDataSetArg();
        WfDataSetArg.WfDataSetCriteria criteria = arg.createCriteria();
        if(StringUtils.isNotBlank(record.getName())){
            arg.setConditionFlag1("true");
            arg.setParamName(record.getName());
        }
        if(StringUtils.isNotBlank(record.getDisplayName())){
            arg.setConditionFlag2("true");
            arg.setParamDisplayName(record.getDisplayName());
        }

        resultPage = wfDataSetDao.selectByArgAndPage(arg, resultPage);


        return resultPage;
    }

    @Override
    public int add(WfDataSetPO record,HttpServletRequest request) throws BaseAppException {
        logger.debug("add begin...record={0}", record);
        record.setModifyUser(1);
        int pkId = sequenceGenerator.sequenceIntValue("WF_DATA_SET","DATA_SET_ID");
        record.setDataSetId(pkId);
        record.setStatus("0");//0-����״̬ 1-ʧЧ״̬
        record.setCreateDate(new Date());
        return wfDataSetDao.insertSelective(record);
    }

    @Override
    public int update(WfDataSetPO record,HttpServletRequest request) throws BaseAppException {
        logger.debug("update begin...record={0}", record);

        record.setModifyUser(1);
        return wfDataSetDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(WfDataSetPO record,HttpServletRequest request) throws BaseAppException {
        logger.debug("delete begin...record={0}", record);

        WfDataSetPO po = new WfDataSetPO();
        po.setDataSetId(record.getDataSetId());
        po.setModifyUser(1);
        po.setStatus("1");//1-ʧЧ
        return wfDataSetDao.updateByPrimaryKeySelective(po);
    }

}
