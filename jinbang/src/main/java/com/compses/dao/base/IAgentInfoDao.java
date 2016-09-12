package com.compses.dao.base;

import com.compses.dao.IBaseCommonDAO;
import com.compses.model.AgentInfo;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public interface IAgentInfoDao extends IBaseCommonDAO {

    AgentInfo getByParentId(String parentId);

    AgentInfo getParent(String agentId);

    AgentInfo getByMobile(String mobile);

}
