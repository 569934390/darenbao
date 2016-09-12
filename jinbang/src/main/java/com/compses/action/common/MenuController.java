package com.compses.action.common;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.compses.model.Menu;
import com.compses.service.common.MenuService;

@Controller
@RequestMapping("menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("getMenuList")
	@ResponseBody
	public Collection<Menu> getMenuList(HttpServletRequest request)
			throws Exception {
		return menuService.getMenuList(request);
	}
}
