package com.compses.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.compses.common.BaseException;
import com.compses.common.Constants;
import com.compses.common.JsonObjectMapper;
import com.compses.dto.TreeNode;

public class JsonUtils {

	private static ObjectMapper objectMapper = new JsonObjectMapper();
	private static ObjectMapper agentObjectMapper = new ObjectMapper();
	// 指定日期类型的格式化
	static {
//		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		agentObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		agentObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		agentObjectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		
//		agentObjectMapper
//				.configure(
//						DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
//						false);
//		agentObjectMapper.configure(
//				SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
//		agentObjectMapper.getSerializationConfig().setDateFormat(
//				new SimpleDateFormat("yyyyMMddHHmmssS"));
//		// agentObjectMapper.getSerializationConfig().withDateFormat(new
//		// SimpleDateFormat("yyyyMMddHHmmssS"));
//		agentObjectMapper.getDeserializationConfig().withDateFormat(
//				new SimpleDateFormat("yyyyMMddHHmmssS"));
//		agentObjectMapper.getDeserializationConfig().setDateFormat(
//				new SimpleDateFormat("yyyyMMddHHmmssS"));
	}

	/**
	 * 字符串转为bean
	 * 
	 * @param content
	 * @param valueType
	 * @return
	 */
	public static <T> T toAgentBean(String content, Class<T> valueType) {
		try {
			return agentObjectMapper.readValue(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 字符串转为bean
	 * 
	 * @param content
	 * @param valueType
	 * @return
	 */
	public static <T> T toBean(String content, Class<T> valueType) {
		try {
			return objectMapper.readValue(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 字符串转为List
	 * 
	 * @param object
	 * @param T
	 * @return
	 */
	public static <T> List<T> toList(String content, Class<T> T) {
		try {
			JavaType javaType = objectMapper.getTypeFactory()
					.constructParametricType(List.class, T);
			return objectMapper.readValue(content, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 字符串转为Array
	 * 
	 * @param object
	 * @param T
	 * @return
	 */
	public static <T> T[] toArray(String content, Class<T> T) {
		try {
			List<T> list = JsonUtils.toList(content, T);
			@SuppressWarnings("unchecked")
			T[] a = (T[]) java.lang.reflect.Array.newInstance(T, list.size());
			return list.toArray(a);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * List串转为Array
	 * 
	 * @param list
	 * @param T
	 * @return
	 */
	public static <T> T[] toArray(List<T> list, Class<T> T) {
		try {
			@SuppressWarnings("unchecked")
			T[] a = (T[]) java.lang.reflect.Array.newInstance(T, list.size());
			return list.toArray(a);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 字符串转为Map<String, Object>
	 * 
	 * @param content
	 * @return
	 */
	public static Map<String, Object> toMap(String content) {
		try {
			return objectMapper.readValue(content,
					new TypeReference<Map<String, Object>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 对象转为Map<String, Object>
	 * 
	 * @param content
	 * @return
	 */
	public static Map<String, Object> toMap(Object object) {
		try {
			return objectMapper.convertValue(object,
					new TypeReference<Map<String, Object>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static <T, V> Map<T, V> toJavaMap(String jsonString,
			Class<T> keyClass, Class<V> valueClass) throws Exception {
		return objectMapper.readValue(jsonString, objectMapper.getTypeFactory()
				.constructMapLikeType(Map.class, keyClass, valueClass));
	}

	/**
	 * 把JavaBean转换为json字符串 普通对象转换：toJson(Student) List转换：toJson(List)
	 * Map转换:toJson(Map) 我们发现不管什么类型，都可以直接传入这个方法
	 * 
	 * @param object
	 *            JavaBean对象
	 * @return json字符串
	 */
	public static String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String toAgentJson(Object object) {
		try {
			return agentObjectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param response
	 * @param list
	 *            要输出的数据
	 * @param idKey
	 *            tree的id字段,可以是多个字段的组合逗号分割
	 * @param textKey
	 *            tree的text
	 * @param cssKey
	 *            tree的图标样式
	 * @param requestTreeNode
	 *            传入的tree参数对象
	 * @throws org.json.JSONException
	 */
	public static void writeJsonTree(HttpServletResponse response,
			Collection<?> list, String idKey, String textKey, String cssKey,
			TreeNode requestTreeNode) throws JSONException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter print = null;
		try {
			print = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<?> it = list.iterator();
		TreeNode child = null;
		TreeNode root = null;
		List<TreeNode> children = new ArrayList<TreeNode>();

		JSONObject jsonObject = new JSONObject();
		while (it.hasNext()) {
			Object object = (Object) it.next();
			jsonObject = new JSONObject(object);
			child = new TreeNode();
			if (child.getChildren() == null || child.getChildren().size() == 0) {
				jsonObject.remove("children");
			}
			if (idKey.contains(",")) {
				String[] idkeyArr = idKey.split(",");
				for (int i = 0; i < idkeyArr.length; i++) {
					if (StringUtils.isEmpty(jsonObject.getString(idkeyArr[i]))) {
						child.setId(child.getId() + idkeyArr[i]);
					} else {
						child.setId(child.getId()
								+ jsonObject.getString(idkeyArr[i]));
					}
				}
			} else {
				child.setId(jsonObject.getString(idKey));
			}
			jsonObject.put("idKey", idKey);
			jsonObject.put("textKey", textKey);
			child.setText(jsonObject.getString(textKey));
			if (requestTreeNode.isLeaf()) {
				child.setLeaf(true);
			}
			if (!StringUtils.isEmpty(cssKey)) {
				child.setIconCls(Constants.NODE_ICON_VIRTUAL);
				if (!StringUtils.isEmptyForObject(jsonObject.get(cssKey))) {
					if (jsonObject.get(cssKey).toString().equals("2")) {
						child.setIconCls(Constants.NODE_ICON_ACUTAL);
					} else if (jsonObject.get(cssKey).toString().equals("6")) {
						child.setIconCls(Constants.NODE_ICON_SYSTEM);
					}
				}
			}
			if (child.getId() != requestTreeNode.getId()) {
				children.add(child);
			} else {
				root = child;
			}
		}
		if (root == null) {
			print.write(toJson(children).toString());
		} else {
			root.setChildren(children);
			children = new ArrayList<TreeNode>();
			root.setIconCls(Constants.NODE_ICON_VIRTUAL);
			children.add(root);
			print.write(toJson(children));
		}
	}

	/**
	 * 
	 * @param response
	 * @param list
	 *            要输出的数据
	 * @param idKey
	 *            tree的id字段,可以是多个字段的组合逗号分割
	 * @param textKey
	 *            tree的text
	 * @param iconUrl
	 *            tree的图标url
	 * @param requestTreeNode
	 *            传入的tree参数对象
	 * @throws org.json.JSONException
	 */
	public static void writeJsonTreeParam(HttpServletRequest request,
			HttpServletResponse response, Collection<?> list,
			Map<String, Object> paramMap, TreeNode requestTreeNode)
			throws JSONException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter print = null;
		try {
			print = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<?> it = list.iterator();
		TreeNode child = null;
		TreeNode root = null;
		List<TreeNode> children = new ArrayList<TreeNode>();

		JSONObject jsonObject = new JSONObject();
		while (it.hasNext()) {
			Object object = (Object) it.next();
			jsonObject = new JSONObject(JsonUtils.toJson(object));
			child = new TreeNode();
			if (child.getChildren() == null || child.getChildren().size() == 0) {
				jsonObject.remove("children");
			}
			String idKey = paramMap.get("nodeId").toString();
			if (idKey.contains(",")) {
				String[] idkeyArr = idKey.split(",");
				for (int i = 0; i < idkeyArr.length; i++) {
					if (StringUtils.isEmpty(jsonObject.getString(idkeyArr[i]))) {
						child.setId(child.getId() + idkeyArr[i]);
					} else {
						child.setId(child.getId()
								+ jsonObject.getString(idkeyArr[i]));
					}
				}
			} else {
				child.setId(jsonObject.getString(idKey));
			}
			jsonObject.put("idKey", idKey);
			jsonObject.put("textKey", paramMap.get("nodeName").toString());
			child.setText(jsonObject.getString(paramMap.get("nodeName")
					.toString()));
			if (requestTreeNode.isLeaf()) {
				child.setLeaf(true);
			}
			if(!"".equals(paramMap.get("iconClass"))&&paramMap.get("iconClass")!=null){
				try {
					String iconClass = paramMap.get("iconClass").toString();					//获取到类名
					String[] clazzArr = paramMap.get("iconClass").toString().split("\\.");    //获取到方法
					String calzzPkg = iconClass.replace("."+clazzArr[clazzArr.length-1], "");
					
					Class<?> clazz = Class.forName(calzzPkg);
					Method method = clazz.getDeclaredMethod(clazzArr[clazzArr.length-1],TreeNode.class,Object.class);
					method.invoke(clazz, new Object[] {child,jsonObject.toString()});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!StringUtils.isEmpty(paramMap.get("nodeIcon"))) {
				String cssKey = paramMap.get("nodeIcon").toString();
				child.setIcon(Constants.ctx + jsonObject.get(cssKey).toString());
				// if(!"".equals(jsonObject.get(cssKey))&&jsonObject.get(cssKey)!=null){
				// String absPath =
				// request.getSession().getServletContext().getRealPath("");
				// String path = absPath +
				// "/"+jsonObject.get(cssKey).toString();
				// if(new File(path).exists()){
				// child.setIcon(jsonObject.get(cssKey).toString());
				// }
				// }
				if (!StringUtils.isEmptyForObject(jsonObject.get(cssKey))) {
					if (jsonObject.get(cssKey).toString().equals("2")) {
						child.setIconCls(Constants.NODE_ICON_ACUTAL);
					} else if (jsonObject.get(cssKey).toString().equals("6")) {
						child.setIconCls(Constants.NODE_ICON_SYSTEM);
					}
				}
			}
			if (child.getId() != requestTreeNode.getId()) {
				children.add(child);
			} else {
				root = child;
			}
		}
		if (root == null) {
			print.write(toJson(children).toString());
		} else {
			root.setChildren(children);
			children = new ArrayList<TreeNode>();
			root.setIconCls(Constants.NODE_ICON_VIRTUAL);
			children.add(root);
			print.write(toJson(children));
		}
	}

	/**
	 * 输出
	 * 
	 * @param response
	 * @param map
	 */
	public static void writeJson(HttpServletResponse response, Map<?, ?> map) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter print = null;
		try {
			print = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print.write(toJson(map).toString());
	}

	/**
	 * 输出
	 * 
	 * @param response
	 * @param map
	 */
	public static void writeJson(HttpServletResponse response, Object object) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter print = null;
		try {
			print = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print.write(toJson(object).toString());
	}

	public static String packageException(boolean flag, String message) {
		Map<String,Object> exceptionMap=new HashMap<String, Object>();
		exceptionMap.put("success", flag);
		exceptionMap.put("message", message);
		return JsonUtils.toJson(exceptionMap);
	}


	public static void writeBaseException(HttpServletRequest request,
			HttpServletResponse response, BaseException baseException) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(404);
		PrintWriter print = null;
		String exceptionMessage=packageException(false, baseException.getMessage());
		try {
			print = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print.write(exceptionMessage);
		print.flush();
	}


	/**
	 * 把对象转换成json
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj) {
		if(obj==null){
			return null;
		}
		return JsonBinder.toJson(obj);
	}



}