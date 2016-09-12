package com.compses.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.compses.dto.Page;
import com.compses.dto.Quartz;
import com.compses.message.MessageCell;
import com.compses.message.MessageHeart;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.QuartzUtil;

@Controller
@RequestMapping("base")
public class BaseCommonController {
	
//	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BaseCommonService baseCommonService;
	

	@RequestMapping("getList.do")
	@ResponseBody
	public List<?> getList(HttpServletRequest request) {
		String sqlKey =request.getParameter("sqlKey");
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("sqlKey",sqlKey);
		List<?> list = baseCommonService.loadData(paramMap);
		return list;
	}
	
	@RequestMapping("getPages.do")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Object> getPages(String pageStr,String nativeSql) {
		List<Object> results = new ArrayList<Object>();
		List<Object> cs = JsonUtils.toBean(pageStr, List.class);
		for(Object c : cs){
			try{
				Map<String,Object> map = (Map<String,Object>)c;
				@SuppressWarnings("rawtypes")
				Page page = new Page();
				if(map.containsKey("start"))
					page.setStart(Integer.parseInt(map.get("start")+""));
				if(map.containsKey("limit"))
					page.setLimit(Integer.parseInt(map.get("limit")+""));
				if(map.containsKey("sqlKey"))
					page.setSqlKey(map.get("sqlKey")+"");
				if(map.containsKey("conditionStr")){
					page.setConditions(JsonUtils.toMap(map.get("conditionStr")));
				}
				if(nativeSql!=null){
					results.add( baseCommonService.loadPages(page));
				}
				else{
					results.add( baseCommonService.loadPage(page));
				}
			}catch(Exception e){
				e.printStackTrace();
				results.add("error:"+e.toString());
			}
		}
		return results;
	}
	
	@RequestMapping("getPage.do")
	@ResponseBody
	public Page<?> getPage(Page<?> page,String conditionsStr) throws Exception {
		if(conditionsStr!=null)
			page.setConditions(JsonUtils.toMap(conditionsStr));
		return baseCommonService.loadPage(page);
	}
	
	@RequestMapping("converQuartz")
	@ResponseBody
	public Quartz converQuartz(String cronExpression){
		return QuartzUtil.convertToQuartz(cronExpression);
	}
	
	@RequestMapping("sendMessage.do")
	@ResponseBody
	public Result send(String channel,String message){
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage(channel+":"+message);
		MessageHeart.getInstance().send(channel,JsonUtils.toJson(message));
		return result;
	}
	@RequestMapping("message.do")
	@ResponseBody
	public Map<String,Object> message(String uuid){
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			Long startTime = System.currentTimeMillis();
			while(System.currentTimeMillis()-startTime<59000){
				List<MessageCell> cells = MessageHeart.getInstance().loadData(uuid);
				if(cells!=null&&cells.size()>0){
					result.put("msg", "推送包");
					result.put("jsonData", cells);
					result.put("success", true);
					return result;
				}
				Thread.sleep(100);
			}
			result.put("msg","心跳包");
			result.put("success", true);
			
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public Map<String,Object> save(String tableName,String entityStr){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("message", baseCommonService.saveForUUID(tableName, JsonUtils.toMap(entityStr)));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage()+e.toString());
		}
		return result;
	}
	@RequestMapping("update")
	@ResponseBody
	public Map<String,Object> update(String tableName,String entityStr){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("message", baseCommonService.update(tableName, JsonUtils.toMap(entityStr)));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage()+e.toString());
		}
		return result;
	}
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,Object> delete(String tableName,String ids){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("message", baseCommonService.delete(tableName, ids));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage()+e.toString());
		}
		return result;
	}

	@RequestMapping("load")
	@ResponseBody
	public Map<String,Object> load(String tableName,String id){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("message", baseCommonService.selectOne(tableName, id));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage()+e.toString());
		}
		return result;
	}
	
	@RequestMapping("updateItems")
	@ResponseBody
	public Map<String,Object> updateItems(String tableName,String entityStr){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("message", baseCommonService.updateItems(tableName, JsonUtils.toMap(entityStr)));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage()+e.toString());
		}
		return result;
	}
	
	@RequestMapping("testDBConnection")
	@ResponseBody
	public Map<String,Object> testDBConnection(String driver,String dbName,String characterEncoding,String ip,String port,String params,String username,String password){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("message", baseCommonService.testDBConnection( driver, dbName, characterEncoding, ip, port, params, username, password));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage()+e.toString());
		}
		return result;
	}
	
	@RequestMapping("loadDBMeta")
	@ResponseBody
	public Map<String,Object> loadDBMeta(String driver,String dbName,String characterEncoding,String ip,String port,String params,String username,String password){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			result.put("message", baseCommonService.loadDBMeta( driver, dbName, characterEncoding, ip, port, params, username, password));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage()+e.toString());
		}
		return result;
	}

	@RequestMapping("saveSqlCache")
	@ResponseBody
	public Map<String,Object> saveSqlCache(String sqlKey,String contextStr){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result.put("success", true);
			baseCommonService.saveSqlCache(sqlKey, contextStr);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("message", e.getMessage()+e.toString());
		}
		return result;
	}




}
