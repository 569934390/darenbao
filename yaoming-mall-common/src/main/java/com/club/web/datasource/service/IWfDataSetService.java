/**
 * 
 */
package com.club.web.datasource.service;


import com.club.core.common.Page;
import com.club.web.datasource.db.po.WfDataSetPO;
import com.club.framework.exception.BaseAppException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <Description> <br>
 * 
 * @author codeCreater<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014�?11�?11�? <br>
 * @since V1.0<br>
 * @see com.club.web.datasource.service <br>
 */

public interface IWfDataSetService {

    WfDataSetPO selectByPrimaryKey(Integer key) throws BaseAppException;

    List<WfDataSetPO> selectByArg(WfDataSetPO record) throws BaseAppException;

    Page<WfDataSetPO> selectByArgAndPage(WfDataSetPO record, Page<WfDataSetPO> resultPage)
            throws BaseAppException;

    int add(WfDataSetPO record,HttpServletRequest request) throws BaseAppException;

    int update(WfDataSetPO record,HttpServletRequest request) throws BaseAppException;

    int delete(WfDataSetPO record,HttpServletRequest request) throws BaseAppException;

}
