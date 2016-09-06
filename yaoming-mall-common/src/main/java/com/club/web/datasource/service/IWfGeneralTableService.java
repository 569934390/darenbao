/**
 * 
 */
package com.club.web.datasource.service;


import com.club.core.common.Page;
import com.club.web.datasource.db.po.WfGeneralTablePO;
import com.club.framework.exception.BaseAppException;

import java.util.List;



public interface IWfGeneralTableService {

    WfGeneralTablePO selectByPrimaryKey(Integer key) throws BaseAppException;

    List<WfGeneralTablePO> selectByArg(WfGeneralTablePO record) throws BaseAppException;

    Page<WfGeneralTablePO> selectByArgAndPage(WfGeneralTablePO record, Page<WfGeneralTablePO> resultPage)
            throws BaseAppException;

    int add(WfGeneralTablePO record) throws BaseAppException;

    int update(WfGeneralTablePO record) throws BaseAppException;

    int delete(WfGeneralTablePO record) throws BaseAppException;

}
