package com.club.web.store.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.web.store.domain.CarriageRuleDetailDo;
import com.club.web.store.domain.CarriageRuleDo;
import com.club.web.store.domain.repository.CarriageRuleDetailRepository;
import com.club.web.store.domain.repository.CarriageRuleRepository;
import com.club.web.store.domain.repository.GoodRepository;
import com.club.web.store.service.CarriageRuleService;
import com.club.web.store.vo.CarriageRuleDetailVo;
import com.club.web.store.vo.CarriageRuleVo;
import com.club.web.store.vo.TradeGoodVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;
import com.club.web.util.sqlexecutor.SqlExecuteRepository;
import com.yaoming.common.util.StringUtil;

/**   
* @Title: CarriageRuleServiceImpl.java
* @Package com.club.web.store.service.impl 
* @Description: 包邮规则Service接口实现类 
* @author hqLin   
* @date 2016/07/19
* @version V1.0   
*/
@Service("carriageRuleService")
public class CarriageRuleServiceImpl implements CarriageRuleService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	CarriageRuleRepository carriageRuleRepository;
	@Autowired
	CarriageRuleDetailRepository carriageRuleDetailRepository;
	@Autowired
	private SqlExecuteRepository sqlExecuteRepository;
	@Autowired
	private GoodRepository goodRepository;
	
	@Override
	public Page<Map<String, Object>> selectCarriageRule(Page<Map<String, Object>> page){
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		try {
			List<CarriageRuleVo> list = carriageRuleRepository.selectCarriageRule(map);
			result.setResultList(CommonUtil.listObjTransToListMap(list));
			Long count = carriageRuleRepository.selectCarriageRuleCountPage(map);
			result.setTotalRecords(count.intValue());
		} catch (Exception e) {
			logger.error("查询包邮规则异常:", e);
		}
		
		return result;
	}
	
	@Override
	public Map<String, Object> addOrUpdCarriageRule(String carriageRuleId, String templateName, BigDecimal carriage, 
			List<CarriageRuleDetailVo> CarriageRuleDetailList){
		Map<String, Object> result = new HashMap<String, Object>();
		CarriageRuleDo carriageRuleDo = carriageRuleRepository.createCarriageRuleDo();
		carriageRuleDo.setTemplateName(templateName);
		carriageRuleDo.setCarriage(carriage);
		carriageRuleDo.setUpdateTime(new Date());
		if(carriageRuleId != null && StringUtil.isNotEmpty(carriageRuleId)){
			List<CarriageRuleDetailVo> detailLst = carriageRuleDetailRepository.selectCarriageRuleDetailListByCarriageRuleId(Long.valueOf(carriageRuleId));
			StringBuffer sb = new StringBuffer();
			if(detailLst != null){
				detailLst.stream().forEach(args -> {
					sb.append(args.getId()).append(",");
				});				
			}
			String ids = sb.toString();
			carriageRuleDo.setId(Long.valueOf(carriageRuleId));
			carriageRuleDo.update();
			
			//对页面传过来的值做新增或修改
			if(CarriageRuleDetailList != null && CarriageRuleDetailList.size() > 0){
				for(CarriageRuleDetailVo tmp : CarriageRuleDetailList){
					CarriageRuleDetailDo carriageRuleDetailDo = carriageRuleDetailRepository.voChangeDo(tmp);
					if(ids != null && !ids.isEmpty() && tmp.getId() != null && !tmp.getId().isEmpty() && ids.indexOf(tmp.getId()) != -1){
						int a = ids.indexOf(tmp.getId());
						ids = ids.substring(0, a) + ids.substring(a+2, ids.length());
						carriageRuleDetailDo.setCarriageRuleId(Long.valueOf(carriageRuleId));
						carriageRuleDetailDo.update();
					} else{
						carriageRuleDetailDo.setId(IdGenerator.getDefault().nextId());
						carriageRuleDetailDo.setCarriageRuleId(Long.valueOf(carriageRuleId));
						carriageRuleDetailDo.insert();
					}
				}
			}
			
			//对没匹配到的配送区域做删除
			if(!ids.isEmpty() && ids.length() > 0){
				String[] idArray = ids.substring(0, ids.length()-1).split(",");
				for(String str : idArray){
					carriageRuleDetailRepository.deleteCarriageRuleDetail(Long.valueOf(str));
				}
			}
			result.put("code", true);
			result.put("msg", "修改成功");
		} else{
			carriageRuleDo.setId(IdGenerator.getDefault().nextId());
			carriageRuleDo.insert();
			if(CarriageRuleDetailList != null && CarriageRuleDetailList.size() > 0){
				CarriageRuleDetailList.stream().forEach(args -> {
					CarriageRuleDetailDo carriageRuleDetailDo = carriageRuleDetailRepository.voChangeDo(args);
					carriageRuleDetailDo.setId(IdGenerator.getDefault().nextId());
					carriageRuleDetailDo.setCarriageRuleId(carriageRuleDo.getId());
					carriageRuleDetailDo.insert();
				});				
			}
			result.put("code", true);
			result.put("msg", "新增成功");
		}
		
		return result;
	}
	
	@Override
	public Map<String, Object> queryCarriageRuleDetail(String carriageRuleId){
		Map<String, Object> result = new HashMap<String, Object>();
		CarriageRuleVo carriageRuleVo = carriageRuleRepository.selectCarriageRuleById(Long.valueOf(carriageRuleId));
		List<CarriageRuleDetailVo> detailLst = carriageRuleDetailRepository.selectCarriageRuleDetailListByCarriageRuleId(Long.valueOf(carriageRuleId));
		if(carriageRuleVo != null){
			result.put("templateName", carriageRuleVo.getTemplateName());
			result.put("carriage", carriageRuleVo.getCarriage());
			result.put("id", carriageRuleVo.getId());
		}
		result.put("detail", detailLst);
		
		return result;
	}
	
	@Override
	public Map<String, Object> deleteCarriageRule(String carriageRuleId){
		Map<String, Object> result = new HashMap<String, Object>();
		Long goodsNo = carriageRuleRepository.getGoodsColumnByCarriageRuleId(Long.valueOf(carriageRuleId));
		if(goodsNo == null || goodsNo == 0){
			// 关闭外键约束
			sqlExecuteRepository.disableForeignKeyChecks();
			carriageRuleRepository.deleteCarriageRule(Long.valueOf(carriageRuleId));
			carriageRuleDetailRepository.deleteCarriageRuleDetailByCarriageRuleId(Long.valueOf(carriageRuleId));
			result.put("code", true);
			result.put("msg", "删除成功");			
		} else{
			result.put("code", false);
			result.put("msg", "包邮规则被商品引用，不能删除");	
		}
		
		return result;
	}
	
	@Override
	public List<CarriageRuleVo> getCarriageRuleList(){
		
		return carriageRuleRepository.getCarriageRuleList();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BigDecimal getCarriageByRegionId(List<Map> list){
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		BigDecimal decimal = BigDecimal.ZERO;
		if(list != null && list.size() > 0){
			for(Map<String, Object> tmp : list){
				if(map.get(tmp.get("carriageRuleId")+"") != null){
					Map<String, Object> tmpMap = map.get(tmp.get("carriageRuleId")+"");
					tmpMap.put("money", Long.valueOf(tmpMap.get("money")+"")+Long.valueOf(tmp.get("money")+""));
					map.put(tmp.get("carriageRuleId")+"", tmpMap);
				} else{
					map.put(tmp.get("carriageRuleId")+"", tmp);
				}
			}
		}
		
		for(Entry<String, Map<String, Object>> entry : map.entrySet()){
			BigDecimal carriage = carriageRuleDetailRepository.getCarriageByRegionId(entry.getValue());
			if(carriage != null && carriage.compareTo(decimal) == 1){
				decimal = carriage;
			}
		}
		
		return decimal;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public BigDecimal getCarriageByRegionIdAndGoodId(List<Map> list){
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		Map<String, Map<String, Object>> carriageMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		BigDecimal decimal = BigDecimal.ZERO;
		if(list != null && list.size() > 0){
			for(Map<String, Object> tmp : list){
				if(map.get(tmp.get("goodId")+"") != null){
					Map<String, Object> tmpMap = map.get(tmp.get("goodId")+"");
					BigDecimal money = new BigDecimal(tmpMap.get("money")+"");
					money.add(new BigDecimal(tmp.get("money")+""));
					tmpMap.put("money", money);
					map.put(tmp.get("goodId")+"", tmpMap);
				} else{
					map.put(tmp.get("goodId")+"", tmp);
				}
			}
		}
		
		for(Entry<String, Map<String, Object>> entry : map.entrySet()){
			param = new HashMap<String, Object>();
			param.putAll(entry.getValue());
			TradeGoodVo tradeGoodInfo = goodRepository.queryTradeGoodInfo(Long.valueOf(param.get("goodId").toString()));
			param.put("carriageRuleId", tradeGoodInfo.getPostid());
			
			if(carriageMap.get(param.get("carriageRuleId")+"") != null){
				Map<String, Object> tmpMap = carriageMap.get(param.get("carriageRuleId")+"");
				BigDecimal money = new BigDecimal(tmpMap.get("money")+"");
				money.add(new BigDecimal(param.get("money")+""));
				tmpMap.put("money", money);
				carriageMap.put(param.get("carriageRuleId")+"", tmpMap);
			} else{
				carriageMap.put(param.get("carriageRuleId")+"", param);
			}
		}
		
		for(Entry<String, Map<String, Object>> entry : carriageMap.entrySet()){
			BigDecimal carriage = carriageRuleDetailRepository.getCarriageByRegionId(entry.getValue());
			if(carriage != null && carriage.compareTo(decimal) == 1){
				decimal = carriage;
			}
		}
		
		return decimal;
	}
}