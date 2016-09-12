package com.compses.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.compses.common.Constants;
import com.compses.common.converter.vo.Avp;
import com.compses.common.converter.vo.Config;
import com.compses.common.converter.vo.Dcc;
import com.compses.common.converter.vo.Diameter;
import com.compses.common.converter.vo.Request;
import com.compses.common.converter.vo.Response;
import com.compses.dto.TreeNode;

public class TreeGridUtils {
	public static Map<String,Object> parseGrid(Map<String,Object> rootMap){
		if(rootMap.get("attributeMap")!=null){
			rootMap.putAll((Map<String,Object>)rootMap.get("attributeMap"));
			rootMap.remove("attributeMap");
		}
		List<Map<String,Object>> childrens = (List<Map<String,Object>>)rootMap.get("children");
		if(childrens!=null){
			for(Map<String,Object> children : childrens){
				parseGrid(children);
			}
		}
		return rootMap;
	}
	public static TreeNode parseGrid(Config config){
		TreeNode treeNode = new TreeNode();
		treeNode.setHref("false");
		treeNode.setId("root");
		treeNode.setText("config");
		treeNode.setExpanded(true);
		List<TreeNode> dccs = new ArrayList<TreeNode>();
		config.setRequestTxt(new StringBuffer("<div class=\"requestTxt\">"));
		config.setResponseTxt(new StringBuffer("<div class=\"requestTxt\">"));
		for(Dcc dcc : config.getMsgs()){
			TreeNode dccNode = new TreeNode();
			dccNode.setId(UUID.randomUUID()+"--"+dcc.getId());
			String dccName = "消息包";
			
			if(!StringUtils.isEmptyForObject(dcc.getName())){
				dccName +="("+dcc.getName()+")";
			}
			dccNode.setText(dccName);
			dccNode.setExpanded(true);
			List<TreeNode> res = new ArrayList<TreeNode>();
			
			TreeNode requestNode = parseRequest(dcc.getRequest(),config.getRequestTxt());
			if(requestNode!=null){
				res.add(requestNode);
			}
			TreeNode responseNode = parseResponse(dcc.getResponse(),config.getResponseTxt());
			if(responseNode!=null){
				res.add(responseNode);
			}
			dccs.add(dccNode);
			dccNode.setChildren(res);
			treeNode.setChildren(dccs);
		}
		config.getRequestTxt().append("</div>");
		config.getResponseTxt().append("</div>");
		if(config.getRequestTxt().toString().equals("<div class=\"requestTxt\"></div>")){
			config.getRequestTxt().delete(0, config.getRequestTxt().length());
		}
		if(config.getResponseTxt().toString().equals("<div class=\"requestTxt\"></div>")){
			config.getResponseTxt().delete(0, config.getResponseTxt().length());
		}
		return treeNode;
	}
	
	private static TreeNode parseRequest(Request request,StringBuffer requestTxt){
		if(request==null)
			return null;
		TreeNode node = new TreeNode();
		node.setExpanded(true);
		node.setId(UUID.randomUUID().toString());
		node.setText("请求包");
		node.setIcon(Constants.getContextProperty("ctx").toString()+"/common/images/16x16/request.png");
		List<TreeNode> childrens = new ArrayList<TreeNode>();
		Diameter diameter = request.getDiameter();
		childrens.add(parseDiameter(diameter));
		childrens.add(convertAvp(request.getAvps(),requestTxt));
		node.setChildren(childrens);
		return node;
	}
	private static TreeNode parseResponse(Response response,StringBuffer responseTxt){
		if(response==null)
			return null;
		TreeNode node = new TreeNode();
		node.setId(UUID.randomUUID().toString());
		node.setExpanded(true);
		node.setIcon(Constants.getContextProperty("ctx").toString()+"/common/images/16x16/response.png");
		node.setText("返回包");
		List<TreeNode> childrens = new ArrayList<TreeNode>();
		Diameter diameter = response.getDiameter();
		childrens.add(parseDiameter(diameter));
		childrens.add(convertAvp(response.getAvps(),responseTxt));
		node.setChildren(childrens);
		return node;
	}
	
	private static TreeNode parseDiameter(Diameter diameter){
		TreeNode node = new TreeNode();
		node.setId(UUID.randomUUID().toString());
		node.setExpanded(true);
		node.setIcon(Constants.getContextProperty("ctx").toString()+"/common/images/16x16/diameter.png");
		node.setText("Diameter消息头");
		List<TreeNode> diameters = new ArrayList<TreeNode>();
		Map<String,Object> diameterMap = JsonUtils.toMap(JsonUtils.toJson(diameter));
		for(String key : diameterMap.keySet()){
			TreeNode diameterAttr = new TreeNode();
			diameterAttr.setId(UUID.randomUUID().toString());
			diameterAttr.setText(key);
			diameterAttr.setIcon(Constants.getContextProperty("ctx").toString()+"/common/images/16x16/diameter_head.png");
			Map<String,Object> attrs = new HashMap<String, Object>();
			attrs.put("avpValue", diameterMap.get(key));
			diameterAttr.setAttributeMap(attrs);
			diameterAttr.setLeaf(true);
			diameters.add(diameterAttr);
		}
		node.setChildren(diameters);
		return node;
	}
	
	private static TreeNode convertAvp(List<Avp> avps,StringBuffer txt){
		TreeNode avp = new TreeNode();
		avp.setId(UUID.randomUUID().toString());
		avp.setText("avps");
		avp.setIcon(Constants.getContextProperty("ctx").toString()+"/common/images/16x16/diameter.png");
		avp.setChildren(parseAvp(avps,txt));
		avp.setExpanded(true);
		return avp;
	}
	private static List<TreeNode> parseAvp(List<Avp> avps,StringBuffer txt){
		List<TreeNode> avpNodes = new ArrayList<TreeNode>();
		txt.append("<div class=dccContext>");
		for(Avp subAvp : avps){
			TreeNode avpNode = new TreeNode();
			avpNode.setId(UUID.randomUUID().toString());
			avpNode.setText(subAvp.getName());
			avpNode.setIcon(Constants.getContextProperty("ctx").toString()+"/common/images/16x16/avp.gif");
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(subAvp.getAvpValue());
			String dest = m.replaceAll("");
			txt.append("<div class=dccAvpItem><span class=dccAvpName>"+subAvp.getName()+"</span>("+subAvp.getCode()+")"+" = "+"[<span class=avpValue> "+ dest+"</span>]"+"</div>");
//			Map<String,Object> attrs = new HashMap<String, Object>();
//			attrs.put("code", subAvp.getCode());
//			attrs.put("value", subAvp.getAvpValue());
//			attrs.put("src", subAvp.getSrc());
//			attrs.put("flags", subAvp.getFlags());
			if(subAvp.getType().equals("Grouped")&&!StringUtils.isEmptyForObject(subAvp.getAvp())&&subAvp.getAvp().size()>0){
				avpNode.setChildren(parseAvp(subAvp.getAvp(),txt));
				avpNode.setExpanded(true);
			}
			else{
				avpNode.setLeaf(true);
			}
			avpNode.setAttributeMap(subAvp.convertToMap());
			avpNodes.add(avpNode);
		}
		txt.append("</div>");
		return avpNodes;
	}
}
