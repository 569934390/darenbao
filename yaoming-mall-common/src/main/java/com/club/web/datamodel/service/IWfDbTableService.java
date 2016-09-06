/**
 * 
 */
package com.club.web.datamodel.service;


import com.club.core.common.Page;
import com.club.web.datamodel.db.po.WfDbTablePO;
import com.club.framework.exception.BaseAppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <Description> <br>
 * 
 * @author codeCreater<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see com.club.web.datamodel.service <br>
 */

public interface IWfDbTableService {

    WfDbTablePO selectByPrimaryKey(Integer key) throws BaseAppException;

    List<WfDbTablePO> selectByArg(WfDbTablePO record) throws BaseAppException;

    Page<WfDbTablePO> selectByArgAndPage(WfDbTablePO record, Page<WfDbTablePO> resultPage)
            throws BaseAppException;

    int add(WfDbTablePO record) throws BaseAppException;

    int update(WfDbTablePO record) throws BaseAppException;

    int delete(WfDbTablePO record) throws BaseAppException;

    Page<Map<String,Object>> getData(Integer dataSetId, Page<Map<String,Object>> resultPage,HttpServletResponse response)
            throws BaseAppException;
    Page<Map<String,Object>> getData(String name, Page<Map<String,Object>> resultPage,HttpServletResponse response)
            throws BaseAppException;

    Page<Map<String,Object>> getDataNoHump(String name, Page<Map<String,Object>> resultPage,HttpServletResponse response)
            throws BaseAppException;
    /**
     * nameOrId false - 使用name  true - 使用dataSetId
     * dataSetIdOrName name 或者dataSetId 的值
     * */
    String getDataSetSql(Object dataSetIdOrName,boolean nameOrId) throws BaseAppException;
    
    List<Object> getDataListByIdAndColumns(String name, String columns)
            throws BaseAppException;
    Page<Map<String,Object>> getDataListByIdAndColumns
            (String name, String columns, Page<Map<String,Object>> resultPage) throws BaseAppException;
    
}
