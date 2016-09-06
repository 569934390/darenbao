package com.club.web.store.dao.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.common.Constants;
import com.club.web.store.dao.base.po.TradeHeadStore;
import com.club.web.store.dao.extend.TradeHeadStoreExtendMapper;
import com.club.web.store.domain.TradeHeadStore2Do;
import com.club.web.store.domain.TradeHeadStoreDo;
import com.club.web.store.domain.repository.TradeHeadStoreRepository;
import com.club.web.store.vo.TradeHeadStoreVo;

/**
 * @Title: TradeHeadStoreRepositoryImpl.java
 * @Package com.club.web.store.dao.repository
 * @Description: 总店RepositoryImpl
 * @author 柳伟军
 * @date 2016年3月26日 上午9:28:55
 * @version V1.0
 */
@Repository
public class TradeHeadStoreRepositoryImpl implements TradeHeadStoreRepository {

	@Autowired
	private TradeHeadStoreExtendMapper tradeHeadStoreExtendMapper;

	@Override
	public TradeHeadStoreDo create(TradeHeadStoreVo TradeHeadStoreVo) {
		if (TradeHeadStoreVo == null)
			return null;
		TradeHeadStore2Do TradeHeadStoreDo = new TradeHeadStore2Do();
		BeanUtils.copyProperties(TradeHeadStoreVo, TradeHeadStoreDo);
		TradeHeadStoreDo.setId(Long.parseLong(TradeHeadStoreVo.getId()));
		return TradeHeadStoreDo;
	}

	private TradeHeadStoreDo getDomainByPo(TradeHeadStore TradeHeadStore) {
		if (TradeHeadStore == null)
			return null;
		TradeHeadStore2Do TradeHeadStoreDo = new TradeHeadStore2Do();
		BeanUtils.copyProperties(TradeHeadStore, TradeHeadStoreDo);
		return TradeHeadStoreDo;
	}

	private TradeHeadStore getPoByDomain(TradeHeadStoreDo TradeHeadStoreDo) {
		if (TradeHeadStoreDo == null)
			return null;
		TradeHeadStore TradeHeadStore = new TradeHeadStore();
		BeanUtils.copyProperties(TradeHeadStoreDo, TradeHeadStore);
		return TradeHeadStore;
	}

	private TradeHeadStoreVo getVoByPo(TradeHeadStore TradeHeadStore) {
		if (TradeHeadStore == null)
			return null;
		TradeHeadStoreVo TradeHeadStoreVo = new TradeHeadStoreVo();
		BeanUtils.copyProperties(TradeHeadStore, TradeHeadStoreVo);
		TradeHeadStoreVo.setId(TradeHeadStore.getId() + "");
		return TradeHeadStoreVo;
	}

	public void insert(TradeHeadStoreDo tradeHeadStoreDo) {
		tradeHeadStoreExtendMapper.insert(getPoByDomain(tradeHeadStoreDo));
	}

	@Override
	public void update(TradeHeadStoreDo tradeHeadStoreDo) {
		tradeHeadStoreExtendMapper.updateByPrimaryKey(getPoByDomain(tradeHeadStoreDo));
	}

	@Override
	public TradeHeadStoreVo getTradeHeadStoreVoById(Long id) {
		return getVoByPo(tradeHeadStoreExtendMapper.selectByPrimaryKey(id));
	}

	@Override
	public TradeHeadStoreDo getTradeHeadStoreDoById(long id) {
		return getDomainByPo(tradeHeadStoreExtendMapper.selectByPrimaryKey(id));
	}

	@Override
	public void deleteByPrimaryKey(long id) {
		tradeHeadStoreExtendMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> queryTradeHeadStorePage(Page<Map<String, Object>> page,
			HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("conditions", page.getConditons().get("conditions").toString());
			if (page.getConditons().get("statue") != null
					&& Long.parseLong(page.getConditons().get("statue").toString()) > -1) {
				map.put("statue", page.getConditons().get("statue").toString());
			}
		}

		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		if (loginMap != null && loginMap.get("staffId") != null) {
			map.put("staffId", loginMap.get("staffId").toString());
		}

		map.put("start", page.getStart());
		map.put("limit", page.getLimit());

		return tradeHeadStoreExtendMapper.queryTradeHeadStorePage(map);
	}

	@Override
	public Long queryTradeHeadStoreCountPage(Page<Map<String, Object>> page, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (page.getConditons() != null) {
			map.put("conditions", page.getConditons().get("conditions").toString());
			if (page.getConditons().get("statue") != null
					&& Long.parseLong(page.getConditons().get("statue").toString()) > -1) {
				map.put("statue", page.getConditons().get("statue").toString());
			}
		}

		/**
		 * 获取当前登录用户的session
		 */
		Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
		if (loginMap != null && loginMap.get("staffId") != null) {
			map.put("staffId", loginMap.get("staffId").toString());
		}

		return tradeHeadStoreExtendMapper.queryTradeHeadStoreCountPage(map);
	}

	@Override
	public List<TradeHeadStoreVo> queryTradeHeadStoreByName(String name) {
		return tradeHeadStoreExtendMapper.queryTradeHeadStoreByName(name);
	}

	@Override
	public List<TradeHeadStoreVo> queryTradeHeadStoreByOwner(Long owner) {
		return tradeHeadStoreExtendMapper.queryTradeHeadStoreByOwner(owner);
	}

}
