package com.compses.util;

import java.util.ArrayList;
import java.util.List;

import com.compses.dto.TreeNode;
import com.compses.model.DopPrivilegeResource;


public class PrivilegeUtils {
	public static boolean doUrlFilter(List<DopPrivilegeResource> prs,String url){
		if(url.contains("portal/portalView.jsp"))return true;
		if(url.endsWith(".do"))return true;
		for(DopPrivilegeResource pr:prs){
			if(url!=null&&pr.getDirection()!=null&&url.equals("/"+pr.getDirection()))
				return true;
		}
		return false;
	}
	
	public static boolean doUrlFilter(List<DopPrivilegeResource> prs,TreeNode node,int depth){
		if(depth>50)return true;//防死循环
		List<TreeNode> childrens = node.getChildren();
		List<TreeNode> news = new ArrayList<TreeNode>();
		for(TreeNode children:childrens){
			boolean flag = doUrlFilter(prs, children,depth+1);
			for(DopPrivilegeResource pr:prs){
				if(!StringUtils.isEmptyForObject(pr.getDirection())&&!StringUtils.isEmptyForObject(children.getText())&&pr.getDirection().equals(children.getText())){
					flag = true;
					break;
				}
			}
			if(flag)news.add(children);
		}
		node.setChildren(news);
		return news.size()>0;
	}
	
	public static boolean doIdFilter(List<DopPrivilegeResource> prs,TreeNode node,int depth){
		if(depth>50)return true;//防死循环
		List<TreeNode> childrens = node.getChildren();
		List<TreeNode> news = new ArrayList<TreeNode>();
		for(TreeNode children:childrens){
			boolean flag = doIdFilter(prs, children,depth+1);
			for(DopPrivilegeResource pr:prs){
				if(pr.getCode().equals(children.getId())){
					flag = true;
					break;
				}
			}
			if(flag)news.add(children);
		}
		node.setChildren(news);
		return news.size()>0;
	}
}
