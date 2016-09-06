/**
 * 
 */
package com.club.web.datasource.service;


import com.club.core.common.Page;
import com.club.web.datasource.db.po.WfGeneralTableColumnsPO;
import com.club.framework.exception.BaseAppException;

import java.util.List;



public interface IWfGeneralTableColumnsService {

    WfGeneralTableColumnsPO selectByPrimaryKey(Integer key) throws BaseAppException;

    List<WfGeneralTableColumnsPO> selectByArg(WfGeneralTableColumnsPO record) throws BaseAppException;

    Page<WfGeneralTableColumnsPO> selectByArgAndPage(WfGeneralTableColumnsPO record, Page<WfGeneralTableColumnsPO> resultPage)
            throws BaseAppException;

    int add(WfGeneralTableColumnsPO record) throws BaseAppException;

    int update(WfGeneralTableColumnsPO record) throws BaseAppException;

    int delete(WfGeneralTableColumnsPO record) throws BaseAppException;

}
