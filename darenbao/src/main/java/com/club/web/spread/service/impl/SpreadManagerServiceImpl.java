package com.club.web.spread.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.spread.dao.base.po.MarketSpreadManager;
import com.club.web.spread.dao.extend.MarketSpreadManagerExtendMapper;
import com.club.web.spread.domain.MarketSpreadManagerDo;
import com.club.web.spread.domain.repository.SpreadClassifyRepository;
import com.club.web.spread.domain.repository.SpreadManagerRepository;
import com.club.web.spread.service.SpreadManagerService;
import com.club.web.spread.vo.GoodandCargoSimpleInfoVo;
import com.club.web.spread.vo.MarketSpreadClassifyVo;
import com.club.web.spread.vo.MarketSpreadManagerVo;
import com.club.web.spread.vo.SpreadVo;
import com.club.web.stock.vo.CargoSimpleInfoVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

/**
 * 
 * @author czj 推广管理service
 *
 */
@Service("spreadManagerService")
@Transactional
public class SpreadManagerServiceImpl implements SpreadManagerService {

	private Logger logger = LoggerFactory.getLogger(SpreadManagerServiceImpl.class);
	@Autowired
	private SpreadManagerRepository spreadManagerRepository;
	@Autowired
	private MarketSpreadManagerExtendMapper marketSpreadManagerExtendMapper;
	@Autowired
	ImageService imageService;

	/**
	 * 查出所有推广记录
	 */
	@Override
	public Page<Map<String, Object>> selectSpread(Page<Map<String, Object>> page, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		map.put("conditions", page.getConditons().get("conditions").toString());
		map.put("classifyConditions", Long.parseLong(page.getConditons().get("classifyConditions").toString()));
		try {
			List<SpreadVo> list = spreadManagerRepository.querySpreadPage(map);
			result.setResultList(CommonUtil.listObjTransToListMap(list));
		} catch (Exception e) {
			logger.error("查询所有推广记录异常:", e);
		}
		try {
			Long count = spreadManagerRepository.querySpreadCountPage(map);
			result.setTotalRecords(count.intValue());
		} catch (Exception e) {
			logger.error("查询推广数量异常:", e);
		}

		return result;
	}

	/**
	 * 提供商品列表和对应的货品信息，以供创建推广时选择
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public Page<GoodandCargoSimpleInfoVo> queryGoodandCargoList(Page<GoodandCargoSimpleInfoVo> page) {
		Map<String, Object> params = new HashMap<>();
		params.putAll(page.getConditons());
		params.put("start", page.getStart());
		params.put("limit", page.getLimit());
		long classifyId = Long.valueOf(params.remove("classifyId") + "");
		page.setResultList(marketSpreadManagerExtendMapper.queryGoodandCargoList(params));
		page.setTotalRecords(marketSpreadManagerExtendMapper.queryGoodandCargoListCount(params));
		return page;
	}

	/**
	 * 添加推广
	 */
	@Override
	public void addSpread(SpreadVo spreadVo) {
		// TODO Auto-generated method stub
		MarketSpreadManagerDo spreadDo = new MarketSpreadManagerDo();
		BeanUtils.copyProperties(spreadVo, spreadDo);
		spreadDo.setId(Long.parseLong(spreadVo.getId()));
		spreadDo.setUpdateTime(new Date());
		spreadDo.setReadNum(0L);
		spreadDo.setShareNum(0L);
		spreadDo.setCollectNum(0L);
		if (spreadVo.getGoodId() != null && !spreadVo.getGoodId().equals("")) {
			spreadDo.setGoodId(Long.parseLong(spreadVo.getGoodId()));
		}
		if (spreadVo.getHeadshopId() != null && !spreadVo.getHeadshopId().equals("")) {
			spreadDo.setHeadshopId(Long.parseLong(spreadVo.getHeadshopId()));
		}
		if (spreadVo.getClassifyId() != null && !spreadVo.getClassifyId().equals("")) {
			spreadDo.setClassifyId(Long.parseLong(spreadVo.getClassifyId()));
		}
		// 如果传过来的图片不为空。则保存记录
		if (spreadVo.getLogo() != null && !"".equals(spreadVo.getLogo())) {
			ImageVo imageVo = imageService.saveImage(spreadVo.getLogo());
			spreadDo.setLogo(imageVo.getId());
		}
		spreadManagerRepository.addSpread(spreadDo);
	}

	/**
	 * 删除推广(支持批量删除)
	 */
	@Override
	public void deleteSpread(String idStr) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		for (String id : ids) {
			spreadManagerRepository.deleteById(Long.parseLong(id));
		}
	}

	/**
	 * 编辑推广
	 */
	@Override
	public void updateSpread(SpreadVo spreadVo) {
		// TODO Auto-generated method stub
		MarketSpreadManagerDo spreadDo = new MarketSpreadManagerDo();
		BeanUtils.copyProperties(spreadVo, spreadDo);
		spreadDo.setId(Long.parseLong(spreadVo.getId()));
		spreadDo.setUpdateTime(new Date());
		if (spreadVo.getGoodId() != null && !spreadVo.getGoodId().equals("")) {
			spreadDo.setGoodId(Long.parseLong(spreadVo.getGoodId()));
		}
		if (spreadVo.getHeadshopId() != null && !spreadVo.getHeadshopId().equals("")) {
			spreadDo.setHeadshopId(Long.parseLong(spreadVo.getHeadshopId()));
		}
		if (spreadVo.getClassifyId() != null && !spreadVo.getClassifyId().equals("")) {
			spreadDo.setClassifyId(Long.parseLong(spreadVo.getClassifyId()));
		}
		MarketSpreadManager oldSpread = marketSpreadManagerExtendMapper.selectByPrimaryKey(Long.parseLong(spreadVo
				.getId()));
		spreadDo.setReadNum(oldSpread.getReadNum());
		spreadDo.setShareNum(oldSpread.getShareNum());
		spreadDo.setCollectNum(oldSpread.getCollectNum());
		// 如果之前图片为空
		if (oldSpread.getLogo() == null || "".equals(oldSpread.getLogo())) {
			// 如果传过来的图片不为空。则保存记录
			if (spreadVo.getLogo() != null && !"".equals(spreadVo.getLogo())) {
				ImageVo imageVo = imageService.saveImage(spreadVo.getLogo());
				spreadDo.setLogo(imageVo.getId());
			}
		} else {
			// 如果传过来的图片不为空。则更新记录
			if (spreadVo.getLogo() != null && !"".equals(spreadVo.getLogo())) {
				// 查询图片记录并更新
				ImageVo imageVo = imageService.selectImageById(oldSpread.getLogo());
				imageVo.setPicUrl(spreadVo.getLogo());
				imageService.updateImage(imageVo);
				spreadDo.setLogo(oldSpread.getLogo());
			} else {
				// 如果传过来的图片为空，则删除记录
				imageService.deleteById(oldSpread.getLogo());
				spreadDo.setLogo(null);
			}
		}
		spreadManagerRepository.update(spreadDo);
	}

	@Override
	public List<SpreadVo> querySpreadList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SpreadVo> list = spreadManagerRepository.querySpreadList(map);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (SpreadVo spreadVo : list) {
			if(spreadVo.getUpdateTime()!=null){
				spreadVo.setUpdateTime1(format.format(spreadVo.getUpdateTime()));
			}
			
		}
		return list;
	}

	/**
	 * 根据id查询推广信息
	 * 
	 * @param id
	 * @return MarketSpreadManagerVo
	 */
	@Override
	public MarketSpreadManagerVo getSpreadDetailSer(long id) {
		MarketSpreadManagerVo obj = marketSpreadManagerExtendMapper.getSpreadDetail(id);
		return obj;
	}
	
	/**
	 * 根据id查询推广信息--B端接口
	 * 
	 * @param id
	 * @return MarketSpreadManagerVo
	 */
	@Override
	public MarketSpreadManagerVo getSpreadDetailSerandAddReadNum(long id) {
		MarketSpreadManager po = new MarketSpreadManager();
		MarketSpreadManagerVo obj = marketSpreadManagerExtendMapper.getSpreadDetail(id);
		po.setId(id);
		po.setReadNum(obj.getReadNum()+1);
		marketSpreadManagerExtendMapper.updateByPrimaryKeySelective(po);
		MarketSpreadManagerVo obj1 = marketSpreadManagerExtendMapper.getSpreadDetail(id);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(obj1.getUpdateTime() !=null){
			obj1.setUpdateTime1(format.format(obj1.getUpdateTime()));
		}
		return obj1;
	}

}
