package com.club.web.deal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.StringUtils;
import com.club.web.deal.domain.IndentListDo;
import com.club.web.deal.domain.IndentListDo2;
import com.club.web.deal.domain.repository.IndentListRepository;
import com.club.web.deal.exception.IndentException;
import com.club.web.deal.service.IndentListService;
import com.club.web.deal.vo.IndentListVo;
import com.club.web.store.dao.base.po.TradeGood;
import com.club.web.store.dao.extend.TradeGoodExtendMapper;
import com.club.web.store.service.GoodSkuService;
import com.club.web.store.service.SettlementBillService;
import com.club.web.store.vo.TradeGoodSkuVo;
import com.club.web.util.IdGenerator;
/**
 * 订单条目服务接口实现类
 * @author zhuzd
 *
 */
@Service
public class IndentListServiceImpl implements IndentListService {

	@Autowired
	private IndentListRepository indentListRepository; 
	
	@Autowired
	private GoodSkuService goodSkuService;	
	
	@Autowired
	private TradeGoodExtendMapper tradeGoodExtendDao;	
	
	@Autowired
	private SettlementBillService settlementBillService;

	@Override
	public boolean fillIndentListValue(String shopId,String indentId, IndentListVo indentListVo) throws IndentException,Exception{
		if(indentListVo != null){
			TradeGoodSkuVo tradeGoodSkuVo = goodSkuService.getSkuvoById(Long.valueOf(indentListVo.getTradeGoodSkuId()));
			if(tradeGoodSkuVo == null){
				throw new IndentException("商品已下架！");
			}
			TradeGood good = tradeGoodExtendDao.selectByPrimaryKey(Long.valueOf(tradeGoodSkuVo.getGoodId()));
			if(good == null){
				throw new IndentException("商品已下架！");
			}
			if(good.getBeginTime() != null && good.getEndTime() != null && good.getEndTime().getTime() < (new Date()).getTime()){
				throw new IndentException("商品活动时间已结束，无法购买！");
			}
			if(good.getPostid() == null){
				throw new IndentException("该商品未设置邮费规则，请联系客服处理！");
			}
			if(indentListVo.getNumber() > tradeGoodSkuVo.getNums()){
				throw new IndentException(tradeGoodSkuVo.getGoodName()+"已售罄！");
			}
			Map<String,Object> map = settlementBillService.getShopIdAndSkuId(Long.valueOf(shopId), Long.valueOf(indentListVo.getTradeGoodSkuId()));
//	        	number
//	        	final_amount
//	        	trade_good_sku_id
        	indentListVo.setId(IdGenerator.getDefault().nextId()+"");
			indentListVo.setIndentId(indentId);
			indentListVo.setCargoSkuId(tradeGoodSkuVo.getCargoSkuId());
			indentListVo.setTradeGoodAmount(tradeGoodSkuVo.getMarketPrice()+"");
			indentListVo.setFinalAmount(tradeGoodSkuVo.getSalePrice()+"");
			indentListVo.setTradeGoodImgUrl(indentListRepository.findImgUrlByGoodSkuId(Long.valueOf(indentListVo.getTradeGoodSkuId())));
			indentListVo.setTradeGoodType(tradeGoodSkuVo.getCargoSkuName());
			indentListVo.setSupplyPrice(map == null || StringUtils.isEmpty(map.get("supplyPrice")+"")?"0":map.get("supplyPrice")+"");
        	indentListVo.setId(IdGenerator.getDefault().nextId()+"");
			tradeGoodSkuVo.setSaleNum(indentListVo.getNumber() + (tradeGoodSkuVo.getSaleNum() != null ?tradeGoodSkuVo.getSaleNum():0));
			goodSkuService.updateGoodSku(tradeGoodSkuVo);
			indentListVo.setTradeGoodName(good.getName());
//			good.setSaleNum(indentListVo.getNumber()+(good.getSaleNum() != null ?good.getSaleNum():0));
//			tradeGoodExtendDao.updateByPrimaryKeySelective(good);
			return true;
		}
		return false;
	}

	@Override
	public void addAll(List<IndentListVo> indentListResult) {
		for (IndentListVo src : indentListResult) {
			this.add(src);
		}
	}

	@Override
	public void add(IndentListVo indentListVo) {
		getDomainByVo(indentListVo).save();
	}
	
	
	@Override
	public List<IndentListVo> getVoListByIndentId(Long indentId){
		return getVoListByDomainList(indentListRepository.getDoListByIndentId(indentId));
	}
	
	private List<IndentListVo> getVoListByDomainList(List<IndentListDo> srcs){
		List<IndentListVo> targets = new ArrayList<IndentListVo>();
		if(srcs != null && srcs.size() != 0){
			for (IndentListDo src : srcs) {
				targets.add(getVoByDomain(src));
			}
		}
		return targets;
	}
	
	public IndentListVo getVoByDomain(IndentListDo src){
		IndentListVo target = null;
		if(src != null){
			target = new IndentListVo();
			target.setId(src.getId() != null?src.getId()+"":null);
			target.setIndentId(src.getIndentId() != null?src.getIndentId()+"":null);
			target.setNumber(src.getNumber());
			target.setFinalAmount(src.getFinalAmount()+"");
			target.setSupplyPrice(src.getSupplyPrice()+"");
			target.setTradeGoodAmount(src.getTradeGoodAmount()+"");
			target.setTradeGoodSkuId(src.getTradeGoodSkuId() != null?src.getTradeGoodSkuId()+"":null);
			target.setCargoSkuId(src.getCargoSkuId() != null?src.getCargoSkuId()+"":null);
			target.setTradeGoodSku(src.getTradeGoodSkuId() != null?goodSkuService.getSkuvoById(src.getTradeGoodSkuId()):null);
			target.setTradeGoodImgUrl(src.getTradeGoodImgUrl());
			target.setTradeGoodType(src.getTradeGoodType()!=null?src.getTradeGoodType():(src.getTradeGoodSkuId()==null?null:goodSkuService.getSkuvoById(Long.valueOf(src.getTradeGoodSkuId())).getCargoSkuName()));			
			target.setTradeGoodName(src.getTradeGoodName());	
			target.setCargoNo(src.getCargoNo());	
		}
		return target;
	}

	private IndentListDo2 getDomainByVo(IndentListVo src){
		IndentListDo2 target = null;
		if(src != null){
			target = new IndentListDo2();
			target.setId(src.getId() != null?Long.valueOf(src.getId()):null);
			target.setIndentId(src.getIndentId() != null?Long.valueOf(src.getIndentId()):null);
			target.setNumber(src.getNumber());
			target.setFinalAmount(BigDecimal.valueOf(Double.valueOf(src.getFinalAmount())));
			target.setSupplyPrice(BigDecimal.valueOf(Double.valueOf(src.getSupplyPrice())));
			target.setTradeGoodAmount(BigDecimal.valueOf(Double.valueOf(src.getTradeGoodAmount())));
			target.setTradeGoodSkuId(StringUtils.isNotEmpty(src.getTradeGoodSkuId())?Long.valueOf(src.getTradeGoodSkuId()):null);
			target.setCargoSkuId(StringUtils.isNotEmpty(src.getCargoSkuId())?Long.valueOf(src.getCargoSkuId()):null);
			target.setTradeGoodImgUrl(src.getTradeGoodImgUrl());
			target.setTradeGoodType(src.getTradeGoodType());			
			target.setTradeGoodName(src.getTradeGoodName());	
			target.setCargoNo(src.getCargoNo());	
		}
		return target;
	}
}
