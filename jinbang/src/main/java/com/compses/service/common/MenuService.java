package com.compses.service.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compses.common.Constants;
import com.compses.dao.IMenuDao;
import com.compses.model.DopPrivilegeResource;
import com.compses.model.DopPrivilegeUser;
import com.compses.model.Menu;


@Service
public class MenuService {
	
	@Autowired
	private IMenuDao menuDAO;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Collection<Menu> getMenuList(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		logger.info("---加载菜单-------");
		List<Menu> menuList=menuDAO.getMenuList();
		List<Menu> filterMenus = new ArrayList<Menu>();
		DopPrivilegeUser staff=(DopPrivilegeUser) request.getSession().getAttribute(Constants.STAFF);
		if(!staff.getName().equals("admin")){
			@SuppressWarnings("unchecked")
			List<DopPrivilegeResource> rps=(List<DopPrivilegeResource>) request.getSession().getAttribute(Constants.RESOURCE_PRIVILEGE_LIST);
			for (Menu menu : menuList) {
				for(DopPrivilegeResource rp : rps){
					if(rp.getType().equals("URL")&&menu.getMenuId()==Integer.parseInt(rp.getCode())){
						filterMenus.add(menu);
					}
				}
			}
		}
		else{
			filterMenus = menuList;
		}
		LinkedHashMap<Long,Menu> menuMap=new LinkedHashMap<Long, Menu>();

		for (Menu menu : filterMenus) {
			if(menu.getParentMenuId()==-1){
				menu.setChildren(new ArrayList<Menu>());
				menuMap.put(menu.getMenuId(), menu);
			}

		}

		for (Menu menu : filterMenus) {
			if(menu.getParentMenuId()!=-1&&menuMap.containsKey(menu.getParentMenuId())) {
				menuMap.get(menu.getParentMenuId()).getChildren().add(menu);
			}
		}
		
		return menuMap.values();
	}
}
