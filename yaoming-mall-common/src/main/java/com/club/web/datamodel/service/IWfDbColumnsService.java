/**
 * 
 */
package com.club.web.datamodel.service;


import com.club.core.common.Page;
import com.club.web.datamodel.db.po.WfDbColumnsPO;
import com.club.framework.exception.BaseAppException;
import com.club.web.common.vo.DBColumn;
import com.club.web.common.vo.DBMeta;
import com.club.web.common.vo.DBTable;

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

public interface IWfDbColumnsService {

    WfDbColumnsPO selectByPrimaryKey(String key) throws BaseAppException;

    List<WfDbColumnsPO> selectByArg(WfDbColumnsPO record) throws BaseAppException;

    Page<WfDbColumnsPO> selectByArgAndPage(WfDbColumnsPO record, Page<WfDbColumnsPO> resultPage) throws BaseAppException;

    Page<WfDbColumnsPO> queryRecordByPageWithDbName(WfDbColumnsPO record, Page<WfDbColumnsPO> resultPage) throws BaseAppException;

    int add(WfDbColumnsPO record) throws BaseAppException;

    WfDbColumnsPO convertWfDbColumnsPO(String content, DBTable dbTable);

    void createTable( List<WfDbColumnsPO> columnsList,WfDbColumnsPO entity,  WfDbColumnsPO record) throws Exception;

    void saveTableAndColumns(List<WfDbColumnsPO> columns,   WfDbColumnsPO record, DBTable dbTable )  throws Exception;


    int isExist(WfDbColumnsPO record) throws BaseAppException;

    int update(WfDbColumnsPO record) throws BaseAppException;

    int delete(WfDbColumnsPO record) throws BaseAppException;

    int deleteBatch(String deleteRecords) throws BaseAppException;
}
