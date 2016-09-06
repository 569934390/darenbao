/**
 * 
 */
package com.club.core.idproduce;

import java.util.HashMap;
import java.util.Map;

import com.club.web.common.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.club.core.db.dao.SequenceProcDao;
import com.club.core.spring.context.SpringApplicationContextHolder;
import com.club.framework.exception.BaseAppException;
import com.club.framework.exception.ExceptionHandler;
import com.club.framework.exception.SystemErrorCode;
import com.club.framework.util.Utils;

/**
 * <Description>序列号生成器实现类，调用数据库的Procedure实现 <br>
 * 
 * @author pan.xiaobo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月27日 <br>
 * @since V1.0<br>
 * @see com.club.core.idproduce <br>
 */

@Component("sequenceProcGenerator")
public class SequenceProcGenerator extends AbstractSequenceGenerator {

    private final static SequenceProcDao sequenceProcDao = SpringApplicationContextHolder
            .getBean(SequenceProcDao.class);

    @Autowired
    private IBaseService baseService;

    /*
     * (non-Javadoc)
     * @see com.club.core.idproduce.AbstractSequenceGenerator#sequenceBatchLongValue(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.Integer)
     */
    @Override
    public Long[] sequenceBatchLongValue(String tableName, String columnName,
            String schemaName, Integer count) throws BaseAppException {
        if (Utils.isEmpty(tableName) || Utils.isEmpty(schemaName)
                || Utils.isEmpty(columnName) || Utils.isEmpty(count)) {
            throw ExceptionHandler.publish(SystemErrorCode.NO_VALIDE_PARAM);
        }
        Long[] resultLongs = selectSeqFunc(tableName, columnName, schemaName,
                count);
        if (null == resultLongs || resultLongs.length < 1)
            throw ExceptionHandler.publish(SystemErrorCode.SQUENCE_IS_NULL);
        return resultLongs;
    }

    private Long[] selectSeqFunc(String tableName, String columnName,
            String schemaName, Integer count) throws BaseAppException {
        synchronized (sequenceProcDao) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tableName", tableName.toUpperCase());
            params.put("columnName", columnName.toUpperCase());
            params.put("schemaName", schemaName.toUpperCase());
            params.put("count", count);
            //params.put("resultStr", "");
            sequenceProcDao.selectSequence(params);
            String resultStr = (String) params.get("resultStr");
            if (null == resultStr || Utils.isEmpty(resultStr)) {
                params.put("schemaName", DEFAULT_SCHEMA);
                sequenceProcDao.selectSequence(params);
                resultStr = (String) params.get("resultStr");
                if (null == resultStr || Utils.isEmpty(resultStr)) {
                    // 为空，该类型的主键未初始化
                    throw ExceptionHandler
                            .publish(SystemErrorCode.SQUENCE_TYPE_NO_INITIALIZE);
                }
            }
            String[] strArray = resultStr.split(",");
            Long[] longArray = new Long[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                longArray[i] = Long.valueOf(strArray[i].trim());
            }
            return longArray;
        }

    }

}
