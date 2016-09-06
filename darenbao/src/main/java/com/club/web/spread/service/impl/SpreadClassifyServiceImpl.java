package com.club.web.spread.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.spread.domain.MarketSpreadClassifyDo;
import com.club.web.spread.domain.repository.SpreadClassifyRepository;
import com.club.web.spread.service.SpreadClassifyService;
import com.club.web.spread.vo.MarketSpreadClassifyVo;
import com.club.web.store.domain.GoodsBaseLabelDo;
import com.club.web.store.vo.GoodsBaseLabelVo;
import com.club.web.util.CommonUtil;
/**
 * 营销推广分类的service层接口的实现类
 * @author czj
 *
 */
@Service("SpreadClassifyService")
public class SpreadClassifyServiceImpl implements SpreadClassifyService{
   
    private Logger logger = LoggerFactory.getLogger(SpreadClassifyServiceImpl.class);
	
	@Autowired
	private  SpreadClassifyRepository spreadClassifyRepository;
	
	
	@Override
	public Page<Map<String, Object>> selectSpreadClassify(Page<Map<String, Object>> page, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		try {
			List<MarketSpreadClassifyVo> list = spreadClassifyRepository.selectAllSpreadClassify(map);
			result.setResultList(CommonUtil.listObjTransToListMap(list));			
		} catch (Exception e) {
			logger.error("查询所有推广分类异常:", e);
		}
		try {
			Long count = spreadClassifyRepository.querySpreadClassifyCountPage();
			result.setTotalRecords(count.intValue());
		} catch (Exception e) {
			logger.error("查询推广分类数量异常:", e);
		}
		
		return result;
	}
	/**
	 * 查出所有分类，返回list
	 */
	@Override
	public List<MarketSpreadClassifyVo> findAllClassify() {
		// TODO Auto-generated method stub
		 return spreadClassifyRepository.findAllSpreadClassify();
	}
	

    /**
     * 
     * 添加推广分类
     */
	@Override
	public Map<String, Object> addSpreadClassify(MarketSpreadClassifyVo marketSpreadClassifyVo,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		MarketSpreadClassifyDo marketSpreadClassifyDo=new MarketSpreadClassifyDo();
		BeanUtils.copyProperties(marketSpreadClassifyVo, marketSpreadClassifyDo);
		marketSpreadClassifyDo.setId(Long.parseLong(marketSpreadClassifyVo.getId()));
		marketSpreadClassifyDo.setCreateTime(new Date());
		//添加类别信息
		try {
			marketSpreadClassifyDo.insert();
			result.put("success", true);
			result.put("msg", "新增推广分类成功");		
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("新增推广分类异常:", e);
		}
		
		return result;
	}

   /**
    * 编辑推广分类
    */
	@Override
	public Map<String, Object> editSpreadClassify(MarketSpreadClassifyVo marketSpreadClassifyVo) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		MarketSpreadClassifyDo marketSpreadClassifyDo=new MarketSpreadClassifyDo();
		BeanUtils.copyProperties(marketSpreadClassifyVo, marketSpreadClassifyDo);
		marketSpreadClassifyDo.setId(Long.parseLong(marketSpreadClassifyVo.getId()));
		marketSpreadClassifyDo.setUpdateTime(new Date());
		//编辑推广分类
		try {
			marketSpreadClassifyDo.update();
			result.put("success", true);
			result.put("msg", "编辑推广分类成功");		
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("编辑推广分类异常:", e);
		}
		
		return result;
	}
   
	   /**
	    * 删除推广分类
	    */
		@Override
		public Map<String, Object> deleteSpreadClassify(String idStr) {
			// TODO Auto-generated method stub
			Map<String, Object> result = new HashMap<String, Object>();
			String[] ids = idStr.split(",");
			try {
				for (String id : ids) {
					spreadClassifyRepository.deleteById(Long.parseLong(id));
					result.put("success", true);
					result.put("msg", "删除推广分类成功");								
				}
			}catch (Exception e) {
				   result.put("success", false);
				   result.put("msg", e.getMessage());
			}
			return result;
		}
		
		
		@Override
		public Map<String, Object> changeStatus(String idStr, int status) {
			Map<String, Object> result = new HashMap<String, Object>();
			String[] ids = idStr.split(",");
			try {
				result.put("success", true);
				result.put("msg", "修改状态成功");
				for (String id : ids) {
					spreadClassifyRepository.updateStatusById(Long.valueOf(id), status);
				}
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
				logger.error("修改状态异常:", e);
			}
			return result;
		}
		
		/**
		 * 查出所有未被禁用的分类，返回list
		 */
		@Override
		public List<MarketSpreadClassifyVo> findAllClassifyOnStatus() {
			// TODO Auto-generated method stub
			 return spreadClassifyRepository.findAllSpreadClassifyOnStatus();
		}
}
