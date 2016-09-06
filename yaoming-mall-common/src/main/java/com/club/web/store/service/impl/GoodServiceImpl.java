/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.stock.domain.CargoSkuTypeDo;
import com.club.web.stock.domain.repository.CargoSkuTypeRepository;
import com.club.web.stock.domain.repository.StockManagerRepository;
import com.club.web.stock.service.CargoClassifyService;
import com.club.web.stock.service.CargoService;
import com.club.web.stock.service.StockManagerService;
import com.club.web.stock.vo.CargoInfoVo;
import com.club.web.stock.vo.SkuGoodsParam;
import com.club.web.store.dao.base.po.TradeGood;
import com.club.web.store.dao.extend.GoodsColumnExtendMapper;
import com.club.web.store.dao.extend.TradeGoodExtendMapper;
import com.club.web.store.dao.repository.StoreSupplyPriceRepositoryImpl;
import com.club.web.store.domain.GoodUpDo;
import com.club.web.store.domain.StoreSupplyPriceDo;
import com.club.web.store.domain.TradeGoodDo;
import com.club.web.store.domain.TradeGoodSkuDo;
import com.club.web.store.domain.repository.GoodEvaluationRepository;
import com.club.web.store.domain.repository.GoodLabelsRepository;
import com.club.web.store.domain.repository.GoodRepository;
import com.club.web.store.domain.repository.GoodSKURepository;
import com.club.web.store.service.GoodEvaluationService;
import com.club.web.store.service.GoodLabelsService;
import com.club.web.store.service.GoodService;
import com.club.web.store.service.GoodSkuService;
import com.club.web.store.service.MessagePushCommon;
import com.club.web.store.service.TradeHeadStoreService;
import com.club.web.store.vo.GoodDetailsVo;
import com.club.web.store.vo.GoodEvaluationVo;
import com.club.web.store.vo.GoodLabelsVo;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodVo;
import com.club.web.store.vo.GoodsColumnVo;
import com.club.web.store.vo.PushMsgVo;
import com.club.web.store.vo.ShanguoGoodMsg;
import com.club.web.store.vo.TradeGoodSkuVo;
import com.club.web.store.vo.TradeGoodVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

/**
 * @Title:
 * @Description: 商品service层的实现类
 * @Author:Administrator
 * @Since:2016年3月25日
 * @Version:1.1.0
 */
@Transactional
@Service("goodServiceImpl")
public class GoodServiceImpl implements GoodService {

	private Logger logger = LoggerFactory.getLogger(GoodServiceImpl.class);
	@Autowired
	private GoodRepository goodRepository;
	@Autowired
	private GoodSKURepository goodSkuRepository;
	@Autowired
	private StockManagerService stockManagerService;
	@Autowired
	private GoodSkuService goodSkuServiceImpl;
	@Autowired
	private GoodLabelsService goodLabelsServiceImpl;
	@Autowired
	private GoodLabelsRepository goodLabelsRepository;
	@Autowired
	private StockManagerRepository stockRepository;
	@Autowired
	private StoreSupplyPriceRepositoryImpl supplyPriceRepository;
	@Autowired
	private GoodEvaluationRepository evaluationRepository;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private GoodEvaluationService goodEvaluationService;
	@Autowired
	private CargoSkuTypeRepository cargoSkuTypeRepository;
	@Autowired
	private MessagePushCommon msgPush;
	@Autowired
	private ImageService imageService;
	@Autowired
	private TradeGoodExtendMapper goodMapper;
	@Autowired
	private TradeHeadStoreService tradeHeadStoreService;
	@Autowired
	private CargoClassifyService cargoClassify;
	@Autowired
	GoodsColumnExtendMapper goodsColumnExtendMapper;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.club.web.store.service.GoodService#addGood(com.club.web.store.vo.
	 * TradeGoodVo)
	 */
	/**
	 * 添加商品
	 */
	@Override
	public void addGood(GoodVo goodVo, List<String> labels, List<String> cargoSkuIds, List<String> cargoSkuNames,
			List<String> marketPriceList, List<String> salePriceList, List<String> supplyPriceList, long j,
			List<String> levelIds, HttpServletRequest request) throws Exception {
		TradeGoodVo tradevo = new TradeGoodVo();
		GoodLabelsVo goodLabelsVo = new GoodLabelsVo();
		TradeGoodDo tradeGoodDo = new TradeGoodDo();
		StoreSupplyPriceDo storeSupplyPricedo = new StoreSupplyPriceDo();
		// 创建以及更新时间
		Date date = new Date();
		goodVo.setCreateTime(date);
		goodVo.setTradeGoodId(Long.toString(IdGenerator.getDefault().nextId()));
		// 前台数据先复制给商品表对应的实体类
		BeanUtils.copyProperties(goodVo, tradevo);
		tradevo.setCargoId(Long.parseLong(goodVo.getCargoId()));
		// 根据规则生成ID
		tradevo.setId(Long.parseLong(goodVo.getTradeGoodId()));

		BeanUtils.copyProperties(tradevo, tradeGoodDo);
		// 如果传过来的长方形图片不为空。则保存记录
		if (goodVo.getImgRectangle() != null && !"".equals(goodVo.getImgRectangle())) {
			ImageVo imageVo = imageService.saveImage(goodVo.getImgRectangle());
			tradeGoodDo.setImgRectangle(imageVo.getId());
		}
		// 如果传过来的长方形图片不为空。则保存记录
		if (goodVo.getImgSquare() != null && !"".equals(goodVo.getImgSquare())) {
			ImageVo imageVo = imageService.saveImage(goodVo.getImgSquare());
			tradeGoodDo.setImgSquare(imageVo.getId());
		}
		// 如果传过来的大图不为空。则保存记录
		if (goodVo.getImgLarge() != null && !"".equals(goodVo.getImgLarge())) {
			ImageVo imageVo = imageService.saveImage(goodVo.getImgLarge());
			tradeGoodDo.setImgLarge(imageVo.getId());
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (goodVo.getBeginTimeStr() != null && !goodVo.getBeginTimeStr().equals("")) {
			tradeGoodDo.setBeginTime(sdf.parse(goodVo.getBeginTimeStr()));
		}
		if (goodVo.getEndTimeStr() != null && !goodVo.getEndTimeStr().equals("")) {
			tradeGoodDo.setEndTime(sdf.parse(goodVo.getEndTimeStr()));
		}

		if (tradeHeadStoreService.getStaffHeadStoreId(request) != null) {
			tradeGoodDo.setHeadstoreId(tradeHeadStoreService.getStaffHeadStoreId(request));
		}
		tradeGoodDo.setStatus(2);
		if(goodVo.getPostid()!=null){
			tradeGoodDo.setPostid(Long.parseLong(goodVo.getPostid()));
		}	
		goodRepository.addGood(tradeGoodDo);

		// 插入多条商品sku数据
		for (int i = 0; i < cargoSkuIds.size(); i++) {
			TradeGoodSkuVo tradeSkuVo = new TradeGoodSkuVo();
			// 前台数据复制对应属性给商品sku表对应的实体类
			BeanUtils.copyProperties(goodVo, tradeSkuVo);
			tradeSkuVo.setId((Long.toString(IdGenerator.getDefault().nextId())));
			// 设置外键的值，商品id
			tradeSkuVo.setGoodId(tradevo.getId().toString());
			tradeSkuVo.setCargoSkuId(cargoSkuIds.get(i).toString());
			tradeSkuVo.setCargoSkuName(cargoSkuNames.get(i).toString());
			tradeSkuVo.setStartTime(date);
			tradeSkuVo.setMarketPrice(Double.valueOf(marketPriceList.get(i).toString()));
			tradeSkuVo.setSalePrice(Double.valueOf(salePriceList.get(i).toString()));
			tradeSkuVo.setNums(0L);
			goodSkuServiceImpl.addGoodSku(tradeSkuVo);
			int k = 0;
			// 存储不同店铺等级的供货价格
			while (j != -1 && supplyPriceList != null && supplyPriceList.size() != 0 && levelIds != null
					&& levelIds.size() != 0 && !levelIds.equals("")) {

				storeSupplyPricedo.setId(IdGenerator.getDefault().nextId());
				storeSupplyPricedo.setGoodSkuId(Long.parseLong(tradeSkuVo.getId()));
				storeSupplyPricedo.setGoodId(Long.parseLong(tradeSkuVo.getGoodId()));
				if (!supplyPriceList.get(0).equals("")) {
					storeSupplyPricedo.setSupplyPrice(Double.parseDouble(supplyPriceList.get(0)));
				} else {
					storeSupplyPricedo.setSupplyPrice(0.0);
				}
				storeSupplyPricedo.setStoreLevelId(Long.parseLong(levelIds.get(0)));
				supplyPriceRepository.saveSupplyPrice(storeSupplyPricedo);
				supplyPriceList.remove(0);
				levelIds.remove(0);
				++k;
				if (k == j) {
					break;
				}
			}

		}

		// 插入多条商品标签数据
		if (labels != null && labels.size() > 0) {
			for (int i = 0; i < labels.size(); i++) {
				goodLabelsVo.setId(IdGenerator.getDefault().nextId());
				goodLabelsVo.setGoodId(tradevo.getId());
				goodLabelsVo.setGoodBaseLabelId(Long.parseLong(labels.get(i)));
				goodLabelsServiceImpl.addGoodLabels(goodLabelsVo);
			}
		}

	}

	/**
	 * 编辑商品
	 */
	@Override
	public void editGood(GoodVo goodVo, List<String> labels, List<String> cargoSkuIds, List<String> cargoSkuNames,
			List<String> marketPriceList, List<String> salePriceList, List<String> supplyPriceList, long j,
			List<String> levelIds, HttpServletRequest request) throws Exception {
		TradeGoodVo tradevo = new TradeGoodVo();
		GoodLabelsVo goodLabelsVo = new GoodLabelsVo();
		TradeGoodDo tradeGoodDo = new TradeGoodDo();
		StoreSupplyPriceDo storeSupplyPricedo = new StoreSupplyPriceDo();

		// 创建以及更新时间
		Date date = new Date();
		goodVo.setCreateTime(date);
		// 前台数据先复制给商品表对应的实体类
		BeanUtils.copyProperties(goodVo, tradevo);
		tradevo.setId(Long.parseLong(goodVo.getTradeGoodId()));
		tradevo.setCargoId(Long.parseLong(goodVo.getCargoId()));
		// 根据规则生成ID
		BeanUtils.copyProperties(tradevo, tradeGoodDo);
		tradeGoodDo.setId(tradevo.getId());
		tradeGoodDo.setCargoId(tradevo.getCargoId());
		if(goodVo.getSaleNum()!=null && !goodVo.getSaleNum().equals("")){
			tradeGoodDo.setSaleNum(Integer.parseInt(goodVo.getSaleNum()));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (goodVo.getBeginTimeStr() != null && !goodVo.getBeginTimeStr().equals("")) {
			tradeGoodDo.setBeginTime(sdf.parse(goodVo.getBeginTimeStr()));
		}
		if (goodVo.getEndTimeStr() != null && !goodVo.getEndTimeStr().equals("")) {
			tradeGoodDo.setEndTime(sdf.parse(goodVo.getEndTimeStr()));
		}
		if (tradeHeadStoreService.getStaffHeadStoreId(request) != null) {
			tradeGoodDo.setHeadstoreId(tradeHeadStoreService.getStaffHeadStoreId(request));
		}
		if(goodVo.getPostid()!=null){
			tradeGoodDo.setPostid(Long.parseLong(goodVo.getPostid()));
		}	
		/**
		 * TODO 这里有问题， PO不能够带到serice层
		 * 
		 * PS: 下面关于图片的处理可以做成一个方法， 调用三次即可
		 */
		TradeGood oldGood = goodMapper.selectByPrimaryKey(tradevo.getId());
		// 如果之前图片为空(首页图片1)
		if (oldGood.getImgSquare() == null || "".equals(oldGood.getImgSquare())) {
			// 如果传过来的图片不为空。则保存记录
			if (goodVo.getImgSquare() != null && !"".equals(goodVo.getImgSquare())) {
				ImageVo imageVo = imageService.saveImage(goodVo.getImgSquare());
				tradeGoodDo.setImgSquare(imageVo.getId());
			}
		} else {
			// 如果传过来的图片不为空。则更新记录
			if (goodVo.getImgSquare() != null && !"".equals(goodVo.getImgSquare())) {
				// 查询图片记录并更新
				ImageVo imageVo = imageService.selectImageById(oldGood.getImgSquare());
				imageVo.setPicUrl(goodVo.getImgSquare());
				imageService.updateImage(imageVo);
				tradeGoodDo.setImgSquare(oldGood.getImgSquare());
			} else {
				// 如果传过来的图片为空，则删除记录
				imageService.deleteById(oldGood.getImgSquare());
				tradeGoodDo.setImgSquare(null);
			}
		}

		// 如果之前图片为空(首页图片2)
		if (oldGood.getImgRectangle() == null || "".equals(oldGood.getImgRectangle())) {
			// 如果传过来的图片不为空。则保存记录
			if (goodVo.getImgRectangle() != null && !"".equals(goodVo.getImgRectangle())) {
				ImageVo imageVo = imageService.saveImage(goodVo.getImgRectangle());
				tradeGoodDo.setImgRectangle(imageVo.getId());
			}
		} else {
			// 如果传过来的图片不为空。则更新记录
			if (goodVo.getImgRectangle() != null && !"".equals(goodVo.getImgRectangle())) {
				// 查询图片记录并更新
				ImageVo imageVo = imageService.selectImageById(oldGood.getImgRectangle());
				imageVo.setPicUrl(goodVo.getImgRectangle());
				imageService.updateImage(imageVo);
				tradeGoodDo.setImgRectangle(oldGood.getImgRectangle());
			} else {
				// 如果传过来的图片为空，则删除记录
				imageService.deleteById(oldGood.getImgRectangle());
				tradeGoodDo.setImgRectangle(null);
			}
		}

		// 如果之前图片为空(首页图片3)
		if (oldGood.getImgLarge() == null || "".equals(oldGood.getImgLarge())) {
			// 如果传过来的图片不为空。则保存记录
			if (goodVo.getImgLarge() != null && !"".equals(goodVo.getImgLarge())) {
				ImageVo imageVo = imageService.saveImage(goodVo.getImgLarge());
				tradeGoodDo.setImgLarge(imageVo.getId());
			}
		} else {
			// 如果传过来的图片不为空。则更新记录
			if (goodVo.getImgLarge() != null && !"".equals(goodVo.getImgLarge())) {
				// 查询图片记录并更新
				ImageVo imageVo = imageService.selectImageById(oldGood.getImgLarge());
				imageVo.setPicUrl(goodVo.getImgLarge());
				imageService.updateImage(imageVo);
				tradeGoodDo.setImgLarge(oldGood.getImgLarge());
			} else {
				// 如果传过来的图片为空，则删除记录
				imageService.deleteById(oldGood.getImgLarge());
				tradeGoodDo.setImgLarge(null);
			}
		}
		goodRepository.updateTradeGood(tradeGoodDo);

		// 先查出所有的商品sku
		List<TradeGoodSkuVo> exgistSkuList = goodSkuRepository.selectGoodSkuByGoodId(tradevo.getId());

		// 先删除所有的店铺对应的供货价
		if (j != -1 && supplyPriceList != null && supplyPriceList.size() != 0 && levelIds != null
				&& levelIds.size() != 0) {
			supplyPriceRepository.deleteByGoodId(Long.parseLong(goodVo.getTradeGoodId()));
		}

		// 先查询货品skuid相关的商品skuid，删除该商品原来有关的所有SKU记录，再重新插入插入多条商品sku数据
		for (TradeGoodSkuVo oldGoodSkuVo : exgistSkuList) {
			int flag = -1;
			for (int i = 0; i < cargoSkuIds.size(); i++) {
				if (oldGoodSkuVo.getCargoSkuId().equals(cargoSkuIds.get(i).toString())) {
					flag = i;
					break;
				}
			}
			if (flag == -1) {
				// delete 編輯后的商品sku比原來的少，則刪除少去的商品Sku
				goodSkuRepository.deleteTradeGoodSKU(Long.parseLong(oldGoodSkuVo.getId()));
			} else {
				// update
				TradeGoodSkuVo tradeSkuVo = new TradeGoodSkuVo();
				// 前台数据复制对应属性给商品sku表对应的实体类
				BeanUtils.copyProperties(goodVo, tradeSkuVo);
				tradeSkuVo.setId(oldGoodSkuVo.getId());
				tradeSkuVo.setGoodId(goodVo.getTradeGoodId());
				TradeGoodSkuDo tradeGoodSkuDo = new TradeGoodSkuDo();
				tradeGoodSkuDo.setId(Long.parseLong(oldGoodSkuVo.getId()));
				tradeGoodSkuDo.setCargoSkuId(Long.parseLong(cargoSkuIds.get(flag)));
				tradeGoodSkuDo.setGoodId(tradevo.getId());
				tradeGoodSkuDo.setCargoSkuName(cargoSkuNames.get(flag));
				tradeGoodSkuDo.setStartTime(date);
				tradeGoodSkuDo.setMarketPrice(Double.valueOf(marketPriceList.get(flag)));
				tradeGoodSkuDo.setSalePrice(Double.valueOf(salePriceList.get(flag)));
				tradeGoodSkuDo.setNums(oldGoodSkuVo.getNums());
				goodSkuRepository.updateTradeGoodSKU(tradeGoodSkuDo);
				int k = 0;

				// 先根据店铺等级id删除原来的店铺等级的供货价格,再重新添加
				while (j != -1 && supplyPriceList != null && supplyPriceList.size() != 0 && levelIds != null
						&& levelIds.size() != 0) {
					storeSupplyPricedo.setId(IdGenerator.getDefault().nextId());
					storeSupplyPricedo.setGoodSkuId(Long.parseLong(tradeSkuVo.getId()));
					storeSupplyPricedo.setGoodId(Long.parseLong(tradeSkuVo.getGoodId()));
					if (!supplyPriceList.get(0).equals("")) {
						storeSupplyPricedo.setSupplyPrice(Double.parseDouble(supplyPriceList.get(0)));
					} else {
						storeSupplyPricedo.setSupplyPrice(0.0);
					}
					storeSupplyPricedo.setStoreLevelId(Long.parseLong(levelIds.get(0)));
					supplyPriceRepository.saveSupplyPrice(storeSupplyPricedo);
					supplyPriceList.remove(0);
					levelIds.remove(0);
					++k;
					if (k == j) {
						break;
					}
				}
			}

		}

		for (int i = 0; i < cargoSkuIds.size(); i++) {
			int flag = -1;
			for (TradeGoodSkuVo tempGoodSkuVo : exgistSkuList) {
				if (tempGoodSkuVo.getCargoSkuId().equals(cargoSkuIds.get(i).toString())) {
					flag = i;
					break;
				}

			}
			if (flag == -1) {
				// 傳入的SKU在現有的SKU集合中沒有發現，說明需要添加
				TradeGoodSkuVo tradeSkuVo = new TradeGoodSkuVo();
				// 前台数据复制对应属性给商品sku表对应的实体类
				BeanUtils.copyProperties(goodVo, tradeSkuVo);
				tradeSkuVo.setId(Long.toString(IdGenerator.getDefault().nextId()));
				// 设置外键的值，商品id
				tradeSkuVo.setGoodId(tradevo.getId().toString());
				tradeSkuVo.setCargoSkuId(cargoSkuIds.get(i).toString());
				tradeSkuVo.setCargoSkuName(cargoSkuNames.get(i).toString());
				tradeSkuVo.setStartTime(date);
				tradeSkuVo.setMarketPrice(Double.valueOf(marketPriceList.get(i).toString()));
				tradeSkuVo.setSalePrice(Double.valueOf(salePriceList.get(i).toString()));
				tradeSkuVo.setNums(0L);
				goodSkuServiceImpl.addGoodSku(tradeSkuVo);
				int k = 0;
				// 先根据店铺等级id删除原来的店铺等级的供货价格,再重新添加
				while (j != -1 && supplyPriceList != null && supplyPriceList.size() != 0 && levelIds != null
						&& levelIds.size() != 0) {
					storeSupplyPricedo.setId(IdGenerator.getDefault().nextId());
					storeSupplyPricedo.setGoodSkuId(Long.parseLong(tradeSkuVo.getId()));
					storeSupplyPricedo.setGoodId(Long.parseLong(tradeSkuVo.getGoodId()));
					if (!supplyPriceList.get(0).equals("")) {
						storeSupplyPricedo.setSupplyPrice(Double.parseDouble(supplyPriceList.get(0)));
					} else {
						storeSupplyPricedo.setSupplyPrice(0.0);
					}
					storeSupplyPricedo.setStoreLevelId(Long.parseLong(levelIds.get(0)));
					supplyPriceRepository.saveSupplyPrice(storeSupplyPricedo);
					supplyPriceList.remove(0);
					levelIds.remove(0);
					++k;
					if (k == j) {
						break;
					}
				}
			} else {
				// 傳入的SKU在現有的SKU集合中存在，由於之前已經更新過，現在不需要任何處理
			}

		}
		// 删除原来的多条该商品有关的标签，插入新的多条商品标签数据
		goodLabelsRepository.deleteByGoodId(tradevo.getId());
		for (int i = 0; i < labels.size(); i++) {
			goodLabelsVo.setId(IdGenerator.getDefault().nextId());
			goodLabelsVo.setGoodId(tradevo.getId());
			goodLabelsVo.setGoodBaseLabelId(Long.parseLong(labels.get(i)));
			goodLabelsServiceImpl.addGoodLabels(goodLabelsVo);
		}

	}

	/**
	 * 更改商品信息
	 */
	public void updateGood(TradeGoodVo vo) {
		TradeGoodDo tradeGoodDo = new TradeGoodDo();
		BeanUtils.copyProperties(vo, tradeGoodDo);
		goodRepository.updateTradeGood(tradeGoodDo);
	}

	/**
	 * 删除商品
	 */
	public void deleteById(long id) {
		try {
			goodRepository.deleteTradeGood(id);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
		}

	}

	/**
	 * 根据名称查询商品分页信息
	 */
	public Page<Map<String, Object>> queryGoodPage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		try {
			result.setStart(page.getStart());
			result.setLimit(page.getLimit());
			List<GoodVo> list = null;
			list = goodRepository.queryGoodPage(page);
			Long count = goodRepository.queryGoodCountPage(page);
			result.setResultList(CommonUtil.listObjTransToListMap(list));
			result.setTotalRecords(count.intValue());
		} catch (Exception e) {
			logger.error("根据商品名称查询异常<queryCargoBrandPageBrandSer>:", e);
		}
		return result;
	}

	/**
	 * 删除商品(可支持批量删除)
	 */
	public Map<String, Object> deleteGoods(String idStr) {
		String[] Ids = idStr.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		for (String id : Ids) {
			// 根据商品id删除商品评论
			evaluationRepository.deleteByGoodId(Long.parseLong(id));
			// 根据商品id删除跟该商品有关的所有SKU的供货价
			supplyPriceRepository.deleteByGoodId(Long.parseLong(id));
			// 根据商品id删除所有的商品标签
			goodLabelsRepository.deleteByGoodId(Long.parseLong(id));
			// 根据商品id删除商品sku记录
			goodSkuRepository.deleteSkuByGoodId(Long.parseLong(id));
			// 根据商品id删除商品
			goodRepository.deleteTradeGood(Long.parseLong(id));
		}
		return result;
	}

	public List<TradeGoodSkuVo> selectTradeGoodSkuVo(String goodId) {
		List<TradeGoodSkuVo> list = goodSkuRepository.selectGoodSkuByGoodId(Long.parseLong(goodId));
		for (TradeGoodSkuVo tradeGoodSkuVo : list) {
			tradeGoodSkuVo.setLeftNums(getLeftNums(Long.parseLong(tradeGoodSkuVo.getCargoSkuId())));
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.club.web.store.service.GoodService#modifyGoodStatus(java.lang.String,
	 * java.util.List)
	 */
	/**
	 * 商品上架
	 */
	@Override
	public Boolean updateUpGoodStatus(String goodId, List<SkuGoodsParam> list, long userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GoodUpDo> listDo = new ArrayList<GoodUpDo>();
		map.put("id", Long.parseLong(goodId));
		map.put("status", 1);
		// TODO Auto-generated method stub
		for (SkuGoodsParam skuGoodsParam : list) {
			GoodUpDo upDo = new GoodUpDo();
			upDo.setGoodSkuId(Long.parseLong(skuGoodsParam.getGoodSkuId()));
			upDo.setCargoSkuId(Long.parseLong(skuGoodsParam.getCargoSkuId()));
			upDo.setNum(Long.parseLong(skuGoodsParam.getNum()));
			listDo.add(upDo);
		}
		Map<String, Object> upMap = stockManagerService.updateUpAndDownGoodsMsg(list, userId, 0);
		if (upMap != null) {
			if (Constants.RESULT_SUCCESS_CODE.equals(upMap.get(Constants.RESULT_CODE).toString())) {
				// 更改商品表中上架的状态
				goodRepository.updateStatus(map);
				goodSkuRepository.updateNumById(listDo);
				// 消息推送
				// goodMsgPush(Long.parseLong(goodId));
				return true;

			}
		}

		return false;
	}

	private void goodMsgPush(long goodId) {
		TradeGoodVo good = null;
		try {
			good = goodRepository.queryTradeGoodInfo(goodId);
			PushMsgVo pushObj = new PushMsgVo();
			pushObj.setMsgType("0");
			pushObj.setPushType("3");
			pushObj.setOffline(true);
			pushObj.setChannel("3");
			pushObj.setTitle("新品上线");
			pushObj.setContent("[上新]" + good.getName());
			pushObj.setOffline(true);
			pushObj.setOfflineExpireTime(24 * 3600 * 1000);
			msgPush.pushMsgUtil(pushObj);
		} catch (Exception e) {
			logger.error("商品上架推送异常<goodMsgPush>:", e);
		}
	}

	/**
	 * 商品下架
	 */
	public Boolean updateDownGoodStatus(List<String> ids, long userId) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<TradeGoodSkuVo> goodSkuVoList = new ArrayList<TradeGoodSkuVo>();
		List<SkuGoodsParam> paramList = new ArrayList<SkuGoodsParam>();
		List<GoodUpDo> doList = new ArrayList<GoodUpDo>();
		// TODO Auto-generated method stub
		for (String id : ids) {
			List<TradeGoodSkuVo> goodSkuVo = goodSkuRepository.selectGoodSkuByGoodId(Long.parseLong(id));
			goodSkuVoList.addAll(goodSkuVo);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", Long.parseLong(id));
			map.put("status", 0);
			list.add(map);
		}
		for (TradeGoodSkuVo tradeGoodSkuVo : goodSkuVoList) {
			// 组成vo的List对象返回给库存接口
			SkuGoodsParam param = new SkuGoodsParam();
			param.setCargoSkuId(tradeGoodSkuVo.getCargoSkuId());
			param.setGoodSkuId(tradeGoodSkuVo.getId());
			if (tradeGoodSkuVo.getNums() == null) {
				param.setNum("0");
			} else {
				param.setNum(tradeGoodSkuVo.getNums().toString());
			}

			paramList.add(param);
			// 组成do的list对象用来改变下架的商品sku的数量
			GoodUpDo upDo = new GoodUpDo();
			upDo.setGoodSkuId(Long.parseLong(tradeGoodSkuVo.getId()));
			upDo.setCargoSkuId(Long.parseLong(tradeGoodSkuVo.getCargoSkuId()));
			upDo.setNum(0L);
			doList.add(upDo);
		}

		// 返回接口给库存
		Map<String, Object> map = stockManagerService.updateUpAndDownGoodsMsg(paramList, userId, 1);
		if (map != null) {
			if (Constants.RESULT_SUCCESS_CODE.equals(map.get(Constants.RESULT_CODE).toString())) {
				// 更改商品表中下架的状态
				for (Map<String, Object> downMap : list) {
					goodRepository.updateStatus(downMap);
				}
				goodSkuRepository.updateNumById(doList);

				return true;
			}
		}

		return false;
	}

	/**
	 * 根据订单的状态更改商品的上下架数量
	 * 
	 * @throws Exception
	 */
	public Map<String, String> updateGoodSkuNums(List<SkuGoodsParam> list, int status) throws Exception {

		Map<String, String> result = new HashMap<String, String>();
		List<GoodUpDo> goodUpList = new ArrayList<GoodUpDo>();
		List<GoodUpDo> goodUpSaleNumList = new ArrayList<GoodUpDo>();
		List<GoodUpDo> allSaleNumList = new ArrayList<GoodUpDo>();
		if (list != null && list.size() != 0) {
			for (SkuGoodsParam param : list) {
				GoodUpDo goodUpDo = new GoodUpDo();
				GoodUpDo goodUpDoSaleNum = new GoodUpDo();
				GoodUpDo goodUpDoAllSaleNum = new GoodUpDo();
				// 设置要修改的skuid
				goodUpDo.setGoodSkuId(Long.parseLong(param.getGoodSkuId()));
				goodUpDoSaleNum.setGoodSkuId(Long.parseLong(param.getGoodSkuId()));
				goodUpDoAllSaleNum.setGoodSkuId(Long.parseLong(param.getGoodSkuId()));
				// 查出当前sku对应的数量

				if (param.getGoodSkuId() != null && param.getGoodSkuId() != "" && param.getNum() != null
						&& param.getNum() != "") {
					// TODO 这里的逻辑有问题，塞值的地方没塞全
					TradeGoodSkuVo tradeGoodSkuVo = goodSkuRepository.selectSkuById(Long.parseLong(param.getGoodSkuId()));
					if(tradeGoodSkuVo == null){
						return result;
					}
					//查询现在剩余数量
					Long nowNum = tradeGoodSkuVo.getNums();
					//查询sku销售数量
					Integer nowSaleNum=tradeGoodSkuVo.getSaleNum();
					//查询商品的销售总数
					Integer allSaleNum=goodMapper.
							selectByPrimaryKey(
									Long.parseLong(tradeGoodSkuVo.getGoodId())).getSaleNum();
					nowNum = nowNum == null ? 0 : nowNum;
					nowSaleNum = nowSaleNum == null ? 0 : nowSaleNum;
					allSaleNum = allSaleNum==null?0:allSaleNum;
					// 如果status=0，表示付款成功，则商品sku的数量对应要减少,並且商品的現有數量應該大於用户的购买数量
					if (status == 0) {
						if (Long.parseLong(param.getNum()) <= nowNum) {
							// 上架数量扣去购买的数量，然后保存
							Long newNum = nowNum - Long.parseLong(param.getNum());
							//销售数量加上购买的数量，然后保存
							Integer newSaleNum = nowSaleNum + Integer.parseInt(param.getNum());
							Integer newAllSaleNum = allSaleNum + Integer.parseInt(param.getNum());
							goodUpDo.setNum(newNum);
							goodUpDoSaleNum.setNum(newSaleNum);
							goodUpDoAllSaleNum.setNum(newAllSaleNum);
						} else {
							throw new RuntimeException("商品sku的对应的上架数量小于客户购买的数量");
						}
					} else {
						// 如果status不为0，表示退货，要把上架数量加上用户退货的数量
						// 上架数量加上退货的数量，然后保存
						Long newNum = nowNum + Long.parseLong(param.getNum());
						Integer newSaleNum = nowSaleNum - Integer.parseInt(param.getNum());
						Integer newAllSaleNum = allSaleNum - Integer.parseInt(param.getNum());
						goodUpDo.setNum(newNum);
						//销售数量减去退货的数量，然后保存
						goodUpDo.setNum(newNum);
						goodUpDoSaleNum.setNum(newSaleNum);
						goodUpDoAllSaleNum.setNum(newAllSaleNum);
					}
					// 更新的list中添加这个元素
					goodUpList.add(goodUpDo);
					goodUpSaleNumList.add(goodUpDoSaleNum);
					allSaleNumList.add(goodUpDoAllSaleNum);
				}
			}
			goodSkuRepository.updateNumById(goodUpList);
			goodSkuRepository.updateSaleNumById(goodUpSaleNumList);
			goodRepository.updateSaleNumById(allSaleNumList);
			
			result.put("result", "商品上架数量更改成功");
		} else {
			throw new RuntimeException("传入的list参数为空");
		}
		return result;
	}

	/**
	 * 查询剩余库存
	 * 
	 * @param CargoSkuId
	 *            参数为货品的skuId
	 * @return 返回库存值
	 */
	public int getLeftNums(long CargoSkuId) {
		return stockRepository.queryStockCountBySkuId(CargoSkuId);
	}

	@Override
	public Long ifGoodExgist(long cargoId) {
		// TODO Auto-generated method stub
		return goodRepository.ifGoodExgist(cargoId);
	}

	@Override
	public List<GoodListVo> queryGoodList(Map<String, Object> map) {

		return goodRepository.queryGoodList(map);
	}

	@Override
	public GoodDetailsVo getGoodDetails(long goodId) {
		GoodDetailsVo goodDetailsVo = new GoodDetailsVo();
		Double marketPrice = Double.valueOf(0);
		Double salePrice = Double.valueOf(0);
		int index = 0;
		int nums = 0;
		Map<String, Object> skuType = new HashMap<String, Object>();
		List<Map<String, Object>> skuTypeList = new ArrayList<Map<String, Object>>();
		List<TradeGoodSkuVo> tradeGoodSkuList = goodSkuRepository.selectSkuList(goodId);
		for (TradeGoodSkuVo tradeGoodSkuVo : tradeGoodSkuList) {
			if (tradeGoodSkuVo.getId() != null && !tradeGoodSkuVo.getId().isEmpty()) {
				int leftNums = tradeGoodSkuVo.getNums() != null ? Integer.valueOf(tradeGoodSkuVo.getNums() + "") : 0;
				tradeGoodSkuVo.setLeftNums(leftNums);
				nums += leftNums;
				// 详情显示的价格为市场价最低的那个SKU
				if (index == 0) {
					marketPrice = tradeGoodSkuVo.getMarketPrice();
					salePrice = tradeGoodSkuVo.getSalePrice();
					index++;
				}
				if (tradeGoodSkuVo.getMarketPrice() > marketPrice) {
					marketPrice = tradeGoodSkuVo.getMarketPrice();
				}
				if (tradeGoodSkuVo.getSalePrice() < salePrice) {
					salePrice = tradeGoodSkuVo.getSalePrice();
				}
			}
		}
		goodDetailsVo.setKucun(nums);
		goodDetailsVo.setGoodSkuList(tradeGoodSkuList);
		goodDetailsVo.setMarketPrice(new BigDecimal(Double.toString(marketPrice)));
		goodDetailsVo.setSalePrice(new BigDecimal(Double.toString(salePrice)));
		TradeGoodVo tradeGoodInfo = goodRepository.queryTradeGoodInfo(goodId); // 商品信息
		if (tradeGoodInfo != null) {
			goodDetailsVo.setTradeGoodId(tradeGoodInfo.getId() + "");
			goodDetailsVo.setPost(tradeGoodInfo.getPost());
			goodDetailsVo.setName(tradeGoodInfo.getName());
			goodDetailsVo.setSaleNum(tradeGoodInfo.getSaleNum() == null ? 0 : tradeGoodInfo.getSaleNum());
			goodDetailsVo.setPostId(tradeGoodInfo.getPostid()== null ? "" : tradeGoodInfo.getPostid().toString());

			Long cargoId = tradeGoodInfo.getCargoId();
			CargoInfoVo cargoInfoVo = cargoService.getCargoInfo(cargoId);
			if (cargoInfoVo != null) {
				goodDetailsVo.setSmallImage(cargoInfoVo.getSmallImage() != null ? cargoInfoVo.getSmallImage().getUrl()
						: "");
				goodDetailsVo.setDetailImages(cargoInfoVo.getDetailImages());
				goodDetailsVo.setShowImages(cargoInfoVo.getShowImages());
			}
			List<CargoSkuTypeDo> cargoSkuTypeList = cargoSkuTypeRepository.getListByCargoId(cargoId);
			if (cargoSkuTypeList != null) {
				for (CargoSkuTypeDo cargoSkuTypeDo : cargoSkuTypeList) {
					skuType = new HashMap<String, Object>();
					skuType.put("skuName", cargoSkuTypeDo.getName());
					skuType.put("skuType", cargoSkuTypeDo.getType());
					skuTypeList.add(skuType);
				}
				goodDetailsVo.setSkuTypeList(skuTypeList);
			}
		}
		List<GoodEvaluationVo> goodEvaluationList = goodEvaluationService.selectEvaluationByGoodId(goodId);
		if (goodEvaluationList != null) {
			goodDetailsVo.setGoodEvaluationList(goodEvaluationList);
		}

		return goodDetailsVo;
	}

	@Override
	public List<TradeGoodSkuVo> queryGoodSkuList(Long goodId) {

		return goodSkuRepository.selectGoodSkuByGoodId(goodId);
	}

	/**
	 * 查询出售中的商品
	 * 
	 * @param param
	 * @return List<ShanguoGoodMsg>
	 */
	@Override
	public List<ShanguoGoodMsg> getSaleGoodListSer(Map<String, Object> param) {
		List<ShanguoGoodMsg> list = goodMapper.getSaleGoodList(param);
		return list;
	}

	/**
	 * 查询已下架的商品
	 *
	 * @param param
	 * @return List<ShanguoGoodMsg>
	 * @add by 2016-05-17
	 */
	@Override
	public List<ShanguoGoodMsg> getUnshelveGoodListSer(Map<String, Object> param) {
		List<ShanguoGoodMsg> list = goodMapper.getUnshelveGoodList(param);
		return list;
	}
	

	/**
	 * 商品列表（通用接口：出售中，已下架，查询商品名称）
	 * @return List<ShanguoGoodMsg>
	 */
	@Override
	public List<ShanguoGoodMsg> getGoodListByMap(Map<String, Object> param) {
		List<ShanguoGoodMsg> list = goodMapper.getGoodListByMap(param);
		return list;
	}

	/**
	 * 搜索商品列表
	 *
	 * @param goodsName
	 * @param pageSize
	 * @param pageNum
	 * @return List<ShanguoGoodMsg>
	 * @add by 2016-05-17
	 */
	@Override
	public List<ShanguoGoodMsg> getSearchGoodListSer(Map<String, Object> param) {
		List<ShanguoGoodMsg> list = goodMapper.getSearchGoodList(param);
		return list;
	}

	/**
	 * 查询商品详情
	 *
	 * @param goodId
	 * @return GoodDetailsVo
	 * @add by 2016-05-18
	 */
	@Override
	public GoodDetailsVo getShanguoGoodDetailSer(Long goodId, Long shopId) {
		GoodDetailsVo goodDetailsVo = new GoodDetailsVo();
		Map<String, Object> skuType = new HashMap<String, Object>();
		List<Map<String, Object>> skuTypeList = new ArrayList<Map<String, Object>>();
		List<TradeGoodSkuVo> tradeGoodSkuList = goodSkuRepository.selectSkuList(goodId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodId", goodId);
		map.put("shopId", shopId);
		ShanguoGoodMsg obj = goodMapper.getWeixinGoodObj(map);
		if (obj != null) {
			goodDetailsVo.setPostId(obj.getPostId());
			goodDetailsVo.setKucun(obj.getStock());
			goodDetailsVo.setGoodSkuList(tradeGoodSkuList);
			goodDetailsVo.setMarketPrice(new BigDecimal(obj.getMarketPrice()+"").setScale(2,RoundingMode.HALF_UP));
			if(obj.getSupplyPrice() != null){
				goodDetailsVo.setSupplyPrice(new BigDecimal(obj.getSupplyPrice()+"").setScale(2,RoundingMode.HALF_UP));				
			}
			goodDetailsVo.setSalePrice(new BigDecimal(obj.getSalePrice()+"").setScale(2, RoundingMode.HALF_UP));
			goodDetailsVo.setTradeGoodId(obj.getId() + "");
			goodDetailsVo.setPost(obj.getPost());
			goodDetailsVo.setName(obj.getGoodName());
			goodDetailsVo.setSaleNum(obj.getSaleNum());
			goodDetailsVo.setStartDate(obj.getStartDate());
			goodDetailsVo.setEndDate(obj.getEndDate());
			goodDetailsVo.setColumnName(obj.getColumnName());
			goodDetailsVo.setStoreNum(obj.getStoreNum());
			Long cargoId = obj.getCargoId();
			CargoInfoVo cargoInfoVo = null;
			if (cargoId != null) {
				cargoInfoVo = cargoService.getCargoInfo(cargoId);

			}
			if (cargoInfoVo != null) {
				goodDetailsVo.setSmallImage(cargoInfoVo.getSmallImage() != null ? cargoInfoVo.getSmallImage().getUrl()
						: "");
				goodDetailsVo.setDetailImages(cargoInfoVo.getDetailImages());
				goodDetailsVo.setShowImages(cargoInfoVo.getShowImages());
				goodDetailsVo.setCargoNo(cargoInfoVo.getCargoNo());
			}
			List<CargoSkuTypeDo> cargoSkuTypeList = null;
			if (cargoId != null) {
				cargoSkuTypeList = cargoSkuTypeRepository.getListByCargoId(cargoId);
			}
			if (cargoSkuTypeList != null) {
				for (CargoSkuTypeDo cargoSkuTypeDo : cargoSkuTypeList) {
					skuType = new HashMap<String, Object>();
					skuType.put("skuName", cargoSkuTypeDo.getName());
					skuType.put("skuType", cargoSkuTypeDo.getType());
					skuTypeList.add(skuType);
				}
				goodDetailsVo.setSkuTypeList(skuTypeList);
			}
		}
		List<GoodEvaluationVo> goodEvaluationList = goodEvaluationService.selectEvaluationByGoodId(goodId);
		if (goodEvaluationList != null) {
			goodDetailsVo.setGoodEvaluationList(goodEvaluationList);
		}

		return goodDetailsVo;
	}

	/**
	 * 获取微信端的商品列表
	 * 
	 * @param conditionStr
	 * @return List<ShanguoGoodMsg>
	 */
	@Override
	public List<ShanguoGoodMsg> getWeixinGoodListSer(String conditionStr) {
		Map<String, Object> param = null;
		long classifyId = 0;
		List<Long> listClassify = null;
		int pageSize = 6;
		int pageNum = 1;
		if (StringUtils.isNotEmpty(conditionStr)) {
			param = JsonUtil.toMap(conditionStr);
			if (param != null) {
				if (param.containsKey("classifyId")) {
					classifyId = param.get("classifyId") != null && !"".equals(param.get("classifyId").toString()) ? Long
							.valueOf(param.get("classifyId").toString()) : 0;
					if (classifyId > 0) {
						try {
							listClassify = cargoClassify.getAllIdsByIdAndStatus(classifyId, null);
							param.put("classify", listClassify);
						} catch (Exception e) {
							logger.error("查询分类子Id异常<getWeixinGoodListSer>:", e);
						}
					}
				}
				if (param.containsKey("pageSize")) {
					pageSize = param.get("pageSize") != null && !"".equals(param.get("pageSize").toString()) ? Integer
							.valueOf(param.get("pageSize").toString()) : pageSize;
				}
				if (param.containsKey("pageNum")) {
					pageNum = param.get("pageNum") != null && !"".equals(param.get("pageNum").toString()) ? Integer
							.valueOf(param.get("pageNum").toString()) : pageNum;
				}
			} else {
				param = new HashMap<String, Object>();
			}
		} else {
			param = new HashMap<String, Object>();
		}
		param.put("start", (pageNum - 1) * pageSize);
		param.put("limit", pageSize);
		List<ShanguoGoodMsg> list = goodMapper.getWeixinGoodList(param);
		return list;
	}
	
	
	/**
	 * 查询所有的商品栏目
	 */
	public List<GoodsColumnVo> getColumnList() {
       List<GoodsColumnVo> list =goodsColumnExtendMapper.selectAllGoodsColumn();
	return list;
	}
}
