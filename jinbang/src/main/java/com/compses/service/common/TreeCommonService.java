package com.compses.service.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.DocumentException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compses.common.Constants;
import com.compses.common.converter.vo.Config;
import com.compses.common.converter.vo.Dcc;
import com.compses.dao.IBaseCommonDAO;
import com.compses.dao.ITreeCommonDAO;
import com.compses.dto.TreeNode;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;

@Service
@Transactional
public class TreeCommonService {
	@Autowired
	private ITreeCommonDAO treeCommonDAO;
	@Autowired
	private IBaseCommonDAO baseCommonDAO;

	public List<TreeNode> getIconTreeAllData(Map<String,Object> params,TreeNode parentNode,int count,HttpServletRequest request) throws JSONException, DocumentException{
		List<TreeNode> children=new ArrayList<TreeNode>();
		JSONObject jsonObject = new JSONObject();
		List<?> topoList = treeCommonDAO.loadTreeData(params);
		Iterator<?> it = topoList.iterator();
		while (it.hasNext()) {
			Object object = (Object) it.next();
			jsonObject = new JSONObject(JsonUtils.toJson(object));

			TreeNode node = new TreeNode();
			if (node.getChildren() == null || node.getChildren().size() == 0) {
				jsonObject.remove("children");
			}
			if (params.get("nodeId").toString().contains(",")) {
				String[] idkeyArr = params.get("nodeId").toString().split(",");
				for (int i = 0; i < idkeyArr.length; i++) {
					if (StringUtils.isEmpty(jsonObject.getString(idkeyArr[i]))) {
						node.setId(node.getId() + idkeyArr[i]);
					} else {
						node.setId(node.getId() + jsonObject.getString(idkeyArr[i]));
					}
				}
			} else {
				node.setId(jsonObject.getString(params.get("nodeId").toString()));
			}
			jsonObject.put("idKey", params.get("nodeId").toString());
			jsonObject.put("textKey", params.get("nodeName").toString());
			String nodeText = "";
			try {
				nodeText = jsonObject.getString(params.get("nodeName").toString());
			} catch (Exception e) {
				nodeText = "N/A";
			}finally{
				node.setText(nodeText);
			}
			
			node.setChecked(false);
			node.setExpandable(true);
			node.setAttributeMap(JsonUtils.toMap(jsonObject.toString()));
			if(!"".equals(params.get("iconClass"))&&params.get("iconClass")!=null){
				try {
					String iconClass = params.get("iconClass").toString();					//获取到类名
					String[] clazzArr = params.get("iconClass").toString().split("\\.");    //获取到方法
					String calzzPkg = iconClass.replace("."+clazzArr[clazzArr.length-1], "");
					
					Class<?> clazz = Class.forName(calzzPkg);
					Method method = clazz.getDeclaredMethod(clazzArr[clazzArr.length-1],TreeNode.class,Object.class);
					method.invoke(clazz, new Object[] {node,jsonObject.toString()});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!StringUtils.isEmpty(params.get("nodeLeaf").toString())) {
				String nodeLeaf = params.get("nodeLeaf").toString();
				if(!"".equals(jsonObject.get(nodeLeaf))&&jsonObject.get(nodeLeaf)!=null){
					int leafNum = Integer.parseInt(jsonObject.get(nodeLeaf).toString());
					if(leafNum>0){
						node.setLeaf(false);
					}else{
						node.setLeaf(true);
					}
				}
			}else{
				node.setLeaf(false);
			}
			if (!StringUtils.isEmpty(params.get("nodeIcon").toString())) {
				String nodeIcon = params.get("nodeIcon").toString();
				try {
					if(jsonObject.get(nodeIcon)!=null&&!"".equals(jsonObject.get(nodeIcon))){
						node.setIcon(Constants.ctx+jsonObject.get(nodeIcon).toString());
						if (!StringUtils.isEmptyForObject(jsonObject.get(nodeIcon))) {
							if (jsonObject.get(nodeIcon).toString().equals("2")) {
								node.setIconCls(Constants.NODE_ICON_ACUTAL);
							} else if (jsonObject.get(nodeIcon).toString().equals("6")) {
								node.setIconCls(Constants.NODE_ICON_SYSTEM);
							}
						}
					}
				} catch (Exception e) {
				}
			}
			children.add(node);
		}
		if(count>1){
			parentNode.setChildren(children);
		}
		for(TreeNode child:children){
			params.put(params.get("idKey").toString(), child.getId());
			getIconTreeAllData(params,child,count+1,request);
		}
		return children;
	}
	
	public List<?> loadTreeData(Map<String,Object> paramMap){
		return treeCommonDAO.loadTreeData(paramMap);
	}
	
	public List<?> getTreeGridList(Map<String,Object> paramMap){
		return treeCommonDAO.loadTreeData(paramMap);
	}

	@SuppressWarnings("unchecked")
	public List<TreeNode> getDopTestDccLogTreeAllData(Map<String, Object> paramMap,
			Object object, int i, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
			List<?> topoList = treeCommonDAO.loadTreeData(paramMap);
			LinkedHashMap<String,TreeNode> groupNodes=new LinkedHashMap<String,TreeNode>();
			String textField="name";
			String groupField="hop_and_end";
			for (Iterator<?> iterator = topoList.iterator(); iterator.hasNext();) {
				Object object2 = (Object) iterator.next();
				String groupKey=BeanUtils.getProperty(object2, groupField).toString();
				String textKey=BeanUtils.getProperty(object2, textField).toString();
				if(BeanUtils.getProperty(object2, "type").equals("0")){
					groupKey+="(客户端发起)";
					textKey+="(客户端发起)";
				}else{
					groupKey+="(服务端发起)";
					textKey+="(服务端发起)";
				}
				if(!groupNodes.containsKey(groupKey)){
					groupNodes.put(groupKey,new TreeNode());
					groupNodes.get(groupKey).setChildren(new ArrayList<TreeNode>());
					groupNodes.get(groupKey).setText(textKey);
					groupNodes.get(groupKey).setId(UUID.randomUUID().toString());
					groupNodes.get(groupKey).setExpanded(true);
				}
				TreeNode child=new TreeNode();
				child.setLeaf(true);
				if (paramMap.get("nodeId").toString().contains(",")) {
					String[] idkeyArr = paramMap.get("nodeId").toString().split(",");
					for (int index = 0; index < idkeyArr.length; i++) {
						String idValue=BeanUtils.getProperty(object2, idkeyArr[index]);
						if (StringUtils.isEmpty(idValue)) {
							child.setId(child.getId() + idkeyArr[i]);
						} else {
							child.setId(child.getId() + idValue);
						}
					}
				} else {
					child.setId(BeanUtils.getProperty(object2, paramMap.get("nodeId").toString()));
				}
				if(BeanUtils.getProperty(object2, "msg_type").equals("0")){
					child.setText("请求包");
				}else{
					child.setText("返回包");
				}
				child.setAttributeMap((Map<String, Object>) object2);
				groupNodes.get(groupKey).getChildren().add(child);
			}
			List<TreeNode> children = new ArrayList<TreeNode>();
			Iterator<String> it=groupNodes.keySet().iterator();
			while(it.hasNext()){
				children.add(groupNodes.get(it.next()));
			}
			return children;
	}
	
	public TreeNode getTreeAllData(String sqlKey,Map<String,Object> obj,String valueField,String displayField,int path,String checked,String[] icons) throws DocumentException{
		path++;
		TreeNode node = new TreeNode();
		node.setText(obj.get(displayField)+"");
		node.setId(obj.get(valueField)+"");
		node.setAttributeMap(obj);
		List<TreeNode> childs = new ArrayList<TreeNode>();
		node.setChildren(childs);
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sqlKey", sqlKey);
		paramMap.putAll(obj);
		List<Map<String,Object>> fields = baseCommonDAO.loadData(paramMap);
		if(fields.size()==0){
			node.setLeaf(true);
		}
		else{
			node.setExpandable(true);
			node.setExpanded(true);
		}
		for(Map<String,Object> field : fields){
			childs.add(getTreeAllData(sqlKey, field, valueField, displayField,path,checked,icons));
		}
		if(!"".equals(checked)&&!"null".equals(checked)){
			if(checked.indexOf(",")!=-1){
				for(String d : checked.split(",")){
					if(path==Integer.parseInt(d))
						node.setChecked(false);
				}
			}
			else{
				node.setChecked(false);
			}
		}
		if(path<=3){
			if(path==2){
				if(icons.length>path){
					node.setIcon(Constants.ctx+icons[path]);
				}
				else{
					node.setIcon(Constants.ctx+"/common/topoImages/16x16/module.png");
				}
			}
			else if(path==3){
				if(icons.length>path){
					node.setIcon(Constants.ctx+icons[path]);
				}
				else{
					node.setIcon(Constants.ctx+"/common/images/16x16/scene.png");
				}
			}
		}
		else{
			if(icons.length>path){
				node.setIcon(Constants.ctx+icons[path]);
			}
			else{
				node.setIcon(Constants.ctx+"/common/images/16x16/msg.png");
			}
		}
		if(obj.containsKey("icon")&&!StringUtils.isEmptyForObject(obj.get("icon"))){
			node.setIcon(Constants.ctx+obj.get("icon"));
		}
		return node;
	}
}