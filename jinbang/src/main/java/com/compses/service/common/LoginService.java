package com.compses.service.common;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.compses.util.Md5PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compses.common.Constants;
import com.compses.dao.IBaseCommonDAO;
import com.compses.dao.IUserDao;
import com.compses.model.DopPrivilegeResource;
import com.compses.model.DopPrivilegeUser;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;


@Service
public class LoginService {
	
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IBaseCommonDAO baseCommonDAO;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String login(HttpServletRequest request, DopPrivilegeUser user)
			throws IllegalAccessException, InvocationTargetException {
		String exMessage = null;
		// 验证是否是同一个IE，更换员工登陆了
		DopPrivilegeUser loginUser= (DopPrivilegeUser) request.getSession().getAttribute(
				Constants.STAFF);
		if (loginUser != null && user != null) {
			if (!loginUser.getName().equals(user.getName())) { // 同一个IE更换员工登陆了
				logger.error(" 同一个浏览器换员工登录了 : " + user.getName());
				exMessage = JsonUtils.packageException(false, "已经登录请关闭浏览器重新登录!");
				return exMessage;
			}
		}
		DopPrivilegeUser staffTemp = userDao.selectByUsername(user.getName());
		try {
			user.setPassword(Md5PasswordEncoder.md5Encode(user.getPassword()));
		}catch (Exception e){

		}
		// 鉴权服务器异常
		if (StringUtils.isEmptyForObject(staffTemp)) {
			logger.error("用户名不存在 : " + user.getName());
			exMessage = JsonUtils.packageException(false, "用户名不存在!");
			return exMessage;
		} else if (!user.getPassword().equals(staffTemp.getPassword())) {
			logger.error("密码错误 : " + staffTemp.getName());
			exMessage = JsonUtils.packageException(false, "密码错误!");
			return exMessage;
		}
		
		request.getSession().removeAttribute(Constants.STAFF);
		request.getSession().setAttribute(Constants.STAFF, staffTemp);
		
		DopPrivilegeResource rp = new DopPrivilegeResource();
		rp.setId(staffTemp.getId());
		List<DopPrivilegeResource> rps = baseCommonDAO.loadData("DopPrivilege","loadResource",rp);
		
		request.getSession().removeAttribute(Constants.RESOURCE_PRIVILEGE_LIST);
		request.getSession().setAttribute(Constants.RESOURCE_PRIVILEGE_LIST, rps);
		
		logger.info("======登录============="+JsonUtils.toJson(user));
		exMessage = JsonUtils.packageException(true, "登录成功!");
		return exMessage;
	}
	
	public void loginOut(HttpServletRequest request, HttpServletResponse response) {
		DopPrivilegeUser user=(DopPrivilegeUser) request.getSession().getAttribute(Constants.STAFF); 
		logger.info("======登出============="+JsonUtils.toJson(user));
		if (user== null) {
			request.getSession().invalidate();
			return;
		}

		request.getSession().removeAttribute(Constants.STAFF);
		
		// 手动释放session
		request.getSession().invalidate();
	}
}
