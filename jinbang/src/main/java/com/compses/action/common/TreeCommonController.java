package com.compses.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.compses.common.Constants;
import com.compses.dto.TreeNode;
import com.compses.dto.TreeParam;
import com.compses.model.DopPrivilegeResource;
import com.compses.model.DopPrivilegeUser;
import com.compses.service.common.TreeCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.PrivilegeUtils;
import com.compses.util.StringUtils;
import com.compses.util.TreeGridUtils;

@Controller
@RequestMapping("tree")
public class TreeCommonController {

	@Autowired
	private TreeCommonService treeCommonService;
	
	@RequestMapping("getRecTree.do")
	@ResponseBody
	public TreeNode getRecTree(HttpServletRequest request,String paramMap) throws DocumentException{
		Map<String,Object> params = JsonUtils.toMap(paramMap);
		String sqlKey = params.get("sqlKey").toString();//DopTestDcc.selectSimulatorByParentId
		String rootId = params.get("rootId").toString();
		String valueField = params.get("valueField").toString();
		String displayField = params.get("displayField").toString();
		String checked = params.get("checked")+"";
		Map<String,Object> root = new HashMap<String,Object>();
		root.put(valueField, rootId);
		String iconStr = "";
		if(params.containsKey("iconStr"))
			iconStr = params.get("iconStr").toString();
		TreeNode tree =  treeCommonService.getTreeAllData(sqlKey,root,valueField,displayField,0,checked,iconStr.split(","));
		if(sqlKey.equals("DopMenu.getLevelMenuList")){
			DopPrivilegeUser staff=(DopPrivilegeUser) request.getSession().getAttribute(Constants.STAFF);
			if(!staff.getName().equals("admin")){
				@SuppressWarnings("unchecked")
				List<DopPrivilegeResource> rps=(List<DopPrivilegeResource>) request.getSession().getAttribute(Constants.RESOURCE_PRIVILEGE_LIST);
				PrivilegeUtils.doIdFilter(rps, tree, 0);
			}
		}
		else if(sqlKey.equals("DopMenu.getLevelDockList")){
			DopPrivilegeUser staff=(DopPrivilegeUser) request.getSession().getAttribute(Constants.STAFF);
			if(!staff.getName().equals("admin")){
				@SuppressWarnings("unchecked")
				List<DopPrivilegeResource> rps=(List<DopPrivilegeResource>) request.getSession().getAttribute(Constants.RESOURCE_PRIVILEGE_LIST);
				PrivilegeUtils.doUrlFilter(rps, tree, 0);
			}
		}
		return tree;
	}
	
	/**
	 * 同步加载树数据
	 * 
	 * @param request
	 * @param response
	 * @param treeNode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTreeAllData.do")
	@ResponseBody
	public List<Map<String,Object>> getTreeAllData(HttpServletRequest request,HttpServletResponse response,TreeParam treeParam) throws Exception {
		Map<String,Object> paramMap = JsonUtils.toMap(treeParam);
		if(!"".equals(treeParam.getIconAble())&&treeParam.getIconAble()!=null&&"true".equals(treeParam.getIconAble())){
		}else{
			paramMap.put("nodeIcon","");
		}
		paramMap.put(treeParam.getIdKey(),treeParam.getRootId());
		
		String para = treeParam.getParamMap();
		if(!"".equals(para)&&para!=null&&!"null".equals(para)){
			Map<String,Object> params = JsonUtils.toMap(para);
			Iterator<?> set = params.keySet().iterator();   
			for (Iterator<?> iterator = set; iterator.hasNext();) {
				Object obj = (Object) iterator.next();
				if(!"".equals(params.get(obj))&&params.get(obj)!=null){
					paramMap.put((String) obj, params.get(obj).toString());
				}
			}
		}
		List<TreeNode> nodes=treeCommonService.getIconTreeAllData(paramMap,null,1,request);;
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
		for (TreeNode node : nodes) {
			Map<String,Object> map=TreeGridUtils.parseGrid(JsonUtils.toMap(node));
			mapList.add(map);
		}
		return mapList;
	}
	
	
	/**
	 * 同步加载树数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDopTestDccLogTreeAllData.do")
	@ResponseBody
	public List<Map<String,Object>> getDopTestDccLogTreeAllData(HttpServletRequest request,HttpServletResponse response,TreeParam treeParam) throws Exception {
		Map<String,Object> paramMap = JsonUtils.toMap(treeParam);
		if(!"".equals(treeParam.getIconAble())&&treeParam.getIconAble()!=null&&"true".equals(treeParam.getIconAble())){
		}else{
			paramMap.put("nodeIcon","");
		}
		paramMap.put(treeParam.getIdKey(),treeParam.getRootId());
		
		String para = treeParam.getParamMap();
		if(!"".equals(para)&&para!=null&&!"null".equals(para)){
			Map<String,Object> params = JsonUtils.toMap(para);
			Iterator<?> set = params.keySet().iterator();   
			for (Iterator<?> iterator = set; iterator.hasNext();) {
				Object obj = (Object) iterator.next();
				if(!"".equals(params.get(obj))&&params.get(obj)!=null){
					paramMap.put((String) obj, params.get(obj).toString());
				}
			}
		}
		List<TreeNode> nodes=treeCommonService.getDopTestDccLogTreeAllData(paramMap,null,1,request);
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
		for (TreeNode node : nodes) {
			Map<String,Object> map=TreeGridUtils.parseGrid(JsonUtils.toMap(node));
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 异步加载树数据
	 * 
	 * @param request
	 * @param response
	 * @param treeNode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTreeData.do")
	@ResponseBody
	public ModelAndView getTreeData(HttpServletRequest request,HttpServletResponse response,TreeNode treeNode,TreeParam treeParam) throws Exception {
		Map<String,Object> paramMap = JsonUtils.toMap(treeParam);
		if(!"".equals(treeParam.getIconAble())&&treeParam.getIconAble()!=null&&"true".equals(treeParam.getIconAble())){
		}else{
			paramMap.put("nodeIcon","");
		}
		paramMap.put(treeParam.getIdKey(),treeParam.getRootId());
		
		String para = treeParam.getParamMap();
		if(!"".equals(para)&&para!=null&&!"null".equals(para)){
			Map<String,Object> params = JsonUtils.toMap(para);
			Object collectionField=params.get("collectionField");//标识那些要被转为collection的字段
			Iterator<?> set = params.keySet().iterator();   
			for (Iterator<?> iterator = set; iterator.hasNext();) {
				Object obj = (Object) iterator.next();
				if(!obj.toString().equals("collectionField")){
					paramMap.put((String) obj, params.get(obj).toString());
					if(!StringUtils.isEmpty(collectionField)&&collectionField.toString().indexOf(obj.toString())>-1){
						List<String> list=JsonUtils.toList(params.get(obj).toString(), String.class);
						paramMap.put((String) obj, list);
					}
				}
			}
		}
		
		List<?> treeList = treeCommonService.loadTreeData(paramMap);
		JsonUtils.writeJsonTreeParam(request,response, treeList,paramMap, treeNode);
		
		return null;
	}

	@RequestMapping("getTreeGridList.do")
	@ResponseBody
	public List<?> getTreeGridList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String sqlKey =request.getParameter("sqlKey");
		String parentId =request.getParameter("parentId");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("sqlKey",sqlKey);
		paramMap.put("parentId",parentId);
		
		List<?> list = treeCommonService.getTreeGridList(paramMap);
		return list;
	}
}
