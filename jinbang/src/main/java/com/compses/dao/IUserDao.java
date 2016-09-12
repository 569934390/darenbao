package com.compses.dao;

import com.compses.model.DopPrivilegeUser;

public interface IUserDao extends IBaseCommonDAO{
	public DopPrivilegeUser selectByUsername(String username);
}
