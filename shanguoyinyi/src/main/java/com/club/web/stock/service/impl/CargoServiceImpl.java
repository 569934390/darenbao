package com.club.web.stock.service.impl;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.club.core.common.Page;
import com.club.framework.util.StringUtils;
import com.club.web.image.service.ImageService;
import com.club.web.stock.dao.extend.CargoExtendMapper;
import com.club.web.stock.dao.extend.CargoSkuExtendMapper;
import com.club.web.stock.domain.CargoClassifyDo;
import com.club.web.stock.domain.CargoDo;
import com.club.web.stock.domain.CargoSkuDo;
import com.club.web.stock.domain.CargoSkuItemDo;
import com.club.web.stock.domain.CargoSkuTypeDo;
import com.club.web.stock.domain.repository.CargoBrandRepository;
import com.club.web.stock.domain.repository.CargoClassifyRepository;
import com.club.web.stock.domain.repository.CargoRepository;
import com.club.web.stock.domain.repository.CargoSkuRepository;
import com.club.web.stock.domain.repository.CargoSkuTypeRepository;
import com.club.web.stock.domain.repository.CargoSupplierRepository;
import com.club.web.stock.domain.repository.StockManagerRepository;
import com.club.web.stock.listener.CargoListenerManager;
import com.club.web.stock.service.CargoService;
import com.club.web.stock.vo.CargoInfoVo;
import com.club.web.stock.vo.CargoInfoVo.CargoSkuItemSimpleVo;
import com.club.web.stock.vo.CargoInfoVo.CargoSkuTypeSimpleVo;
import com.club.web.stock.vo.CargoSaveVo;
import com.club.web.stock.vo.CargoSimpleEditVo;
import com.club.web.stock.vo.CargoSimpleInfoVo;
import com.club.web.stock.vo.CargoSkuAddVo;
import com.club.web.stock.vo.CargoSkuChangeVo;
import com.club.web.stock.vo.CargoSkuSimpleVo;
import com.club.web.stock.vo.CargoSkuTypeSaveVo;
import com.club.web.stock.vo.ImageGroupVo;
import com.club.web.stock.vo.ImageVo;
import com.club.web.util.IdGenerator;
import com.club.web.util.sqlexecutor.SqlExecuteRepository;

/**
 * Cargo Service
 * 
 * @author yunpeng.xiong
 *
 */
@Service
public class CargoServiceImpl implements CargoService {

	@Autowired
	private CargoSupplierRepository cargoSupplierRepository;
	@Autowired
	private CargoBrandRepository cargoBrandRepository;
	@Autowired
	private CargoClassifyRepository cargoClassifyRepository;
	@Autowired
	private CargoSkuTypeRepository cargoSkuTypeRepository;
	@Autowired
	private ImageService imageService;
	@Autowired
	private StockManagerRepository stockManagerRepository;
	@Autowired
	private CargoSkuRepository cargoSkuRepository;
	@Autowired
	private SqlExecuteRepository sqlExecuteRepository;

	@Autowired
	private CargoRepository cargoRepository;
	@Autowired
	private CargoExtendMapper cargoDao;
	@Autowired
	private CargoSkuExtendMapper cargoSkuDao;
	@Autowired
	private CargoListenerManager goodsListener;

	@Override
	public void saveCargo(long creatorId, CargoSaveVo cargoSaveVo) {
		try {
			// 检查数据
			Assert.notNull(cargoClassifyRepository.findDoById(cargoSaveVo.getClassifyId()), "分类错误");
			// Assert.notNull(cargoSupplierRepository.getCargoSupplierDoById(cargoSaveVo.getSupplierId()),
			// "供应商错误");
			Assert.notNull(cargoBrandRepository.getCargoBrandDoById(cargoSaveVo.getBrandId()), "品牌错误");
			checkImage(cargoSaveVo.getSmallImage(), "缩略图信息不正确");
			checkImageGroup(cargoSaveVo.getShowImages(), "展示图信息不正确");
			checkImageGroup(cargoSaveVo.getDetailImages(), "详情图信息不正确");

			// 关闭外键约束
			sqlExecuteRepository.disableForeignKeyChecks();

			// 货品
			CargoDo cargo;
			if (cargoSaveVo.getCargoId() <= 0)
				cargo = createCargo(creatorId, cargoSaveVo);
			else
				cargo = updateCargo(creatorId, cargoSaveVo);

			// SKU
			cargo.deleteSku(cargoSaveVo.getSkuDelete());

			// SKU规格类型
			CargoSkuTypeSaveVo[] stvos = cargoSaveVo.getSkuTypes();
			long[] stids = new long[stvos.length];
			for (int i = 0; i < stvos.length; i++)
				stids[i] = stvos[i].getSkuTypeId();
			List<CargoSkuTypeDo> stdos = cargo.setSkuTypes(creatorId, stids);

			// SKU规格选项
			for (CargoSkuTypeDo stdo : stdos)
				for (CargoSkuTypeSaveVo stvo : stvos)
					if (stdo.getCargoBaseSkuTypeId() == stvo.getSkuTypeId())
						stdo.setSkuItems(creatorId, stvo.getSkuItemIds());

			// SKU
			if (cargoSaveVo.getSkuChange() != null)
				for (CargoSkuChangeVo cscvo : cargoSaveVo.getSkuChange())
					if (cscvo != null)
						cargo.updateSku(creatorId, cscvo.getSkuId(), cscvo.getSkuCode(), cscvo.getPrice());
			if (cargoSaveVo.getSkuAdd() != null)
				for (CargoSkuAddVo cscvo : cargoSaveVo.getSkuAdd())
					if (cscvo != null)
						cargo.addSku(creatorId, cscvo.getSkuCode(), cscvo.getPrice(), cscvo.getSkuItems());
		} finally {
			// 打开外键约束
			sqlExecuteRepository.enableForeignKeyChecks();
		}

	}

	private CargoDo createCargo(long creatorId, CargoSaveVo csvo) {

		// 保存图片
		long smallImageId = saveImage(csvo.getSmallImage());
		long showImageGroupId = saveImageGroup(csvo.getShowImages().getGroupIdLong(), creatorId, csvo.getShowImages()
				.getImages());
		long detailImageGroupId = saveImageGroup(csvo.getDetailImages().getGroupIdLong(), creatorId, csvo
				.getDetailImages().getImages());

		// 保存货品
		CargoDo cargo = cargoRepository.create(creatorId, csvo.getCargoNo(), csvo.getCargoName(), csvo.getClassifyId(),
				csvo.getSupplierId(), csvo.getBrandId(), smallImageId, showImageGroupId, detailImageGroupId);
		try {
			cargo.insert();
		} catch (Exception e) {
			if (e instanceof BatchUpdateException || e instanceof DuplicateKeyException)
				throw new RuntimeException("货品保存失败，货品编号[" + cargo.getCargoNo() + "]已存在");
		}
		return cargo;
	}

	private CargoDo updateCargo(long creatorId, CargoSaveVo csvo) {

		// 保存图片
		Assert.state(csvo.getShowImages().getGroupIdLong() > 0, "展示图信息不正确");
		Assert.state(csvo.getDetailImages().getGroupIdLong() > 0, "详情图信息不正确");
		saveImageGroup(csvo.getShowImages().getGroupIdLong(), creatorId, csvo.getShowImages().getImages());
		saveImageGroup(csvo.getDetailImages().getGroupIdLong(), creatorId, csvo.getDetailImages().getImages());

		// 更新货品
		CargoDo cargo = cargoRepository.getCargoById(csvo.getCargoId());
		cargo.setCargoNo(csvo.getCargoNo());
		cargo.setName(csvo.getCargoName());
		cargo.setClassifyId(csvo.getClassifyId());
		cargo.setBrandId(csvo.getBrandId());
		if (csvo.getSupplierId() > 0)
			cargo.setSupplierId(csvo.getSupplierId());
		else
			cargo.setSupplierId(0);
		if (csvo.getSmallImage().getIdLong() <= 0)
			cargo.setSmallImageId(saveImage(csvo.getSmallImage()));
		cargo.update();
		return cargo;
	}

	private long saveImage(ImageVo imagevo) {
		com.club.web.image.service.vo.ImageVo iv = imageService.saveImage(imagevo.getUrl());
		Assert.notNull(iv, "保存图片失败");
		return iv.getId();
	}

	private String[] getUrls(ImageVo[] images) {
		Assert.notNull(images, "图片信息获取失败");
		String[] result = new String[images.length];
		for (int i = 0; i < images.length; i++)
			result[i] = images[i].getUrl();
		return result;
	}

	private long saveImageGroup(long groupId, long creatorId, ImageVo[] images) {
		if (groupId <= 0)
			groupId = IdGenerator.getDefault().nextId();
		int state = imageService.saveOrUpdateByGroupId(groupId, creatorId, getUrls(images));
		Assert.state(state == 1, "保存图片组失败");
		return groupId;
	}

	private void checkImage(ImageVo ivo, String msg) {
		Assert.state(ivo != null && !StringUtils.isEmpty(ivo.getUrl()), msg);
	}

	private void checkImageGroup(ImageGroupVo igvo, String msg) {
		Assert.state(igvo != null && igvo.getImages() != null && igvo.getImages().length > 0, msg);
		for (ImageVo ivo : igvo.getImages())
			Assert.state(ivo != null && !StringUtils.isEmpty(ivo.getUrl()), msg);
	}

	private String getStrByList(List<Long> list) {
		if (list == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (Long l : list)
			sb.append(l).append("','");
		if (sb.length() > 0)
			return sb.substring(0, sb.length() - 3);
		return sb.toString();
	}

	@Override
	public Page<CargoSimpleInfoVo> queryCargoList(Page<CargoSimpleInfoVo> page) {
		Map<String, Object> params = new HashMap<>();
		params.putAll(page.getConditons());
		params.put("start", page.getStart());
		params.put("limit", page.getLimit());
		long classifyId = Long.valueOf(params.remove("classifyId") + "");
		if (classifyId > 0)
			params.put("classifyId", getStrByList(cargoClassifyRepository.getAllIdsByIdAndStatus(classifyId, null)));
		page.setResultList(cargoDao.queryCargoList(params));
		page.setTotalRecords(cargoDao.queryCargoListCount(params));
		return page;
	}

	@Override
	public int queryCargoCountByClassifyIds(List<Long> classifyIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("classifyId", getStrByList(classifyIds));
		return cargoDao.queryCargoListCount(params);
	}

	@Override
	public boolean delete(Long[] ids) {
		for (Long id : ids) {
			CargoDo cargo = cargoRepository.getCargoById(id);
			Assert.state(cargo != null, "货品不存在");
			Assert.state(cargo.getTotalCount() == 0, "货品[" + cargo.getName() + "]尚有" + cargo.getTotalCount() + "件未处理");
			if (cargo.deleteAllSku() && goodsListener.deleteCargo(id)) {
				cargo.delete();
			} else {
				throw new RuntimeException("删除失败，系统异常");
			}
		}
		return true;
	}

	@Override
	public List<CargoSkuSimpleVo> getSkuList(long cargoId) {
		return cargoSkuDao.getSkuList(cargoId);
	}

	@Override
	public CargoInfoVo getCargoInfo(long cargoId) {
		CargoSimpleEditVo smevo = cargoDao.getCargoSimpleEditVo(cargoId);
		Assert.notNull(smevo, "货品获取失败");
		CargoInfoVo civ = new CargoInfoVo();
		civ.setCargoId(cargoId + "");
		civ.setCargoName(smevo.getCargoName());
		civ.setCargoNo(smevo.getCargoNo());
		List<CargoClassifyDo> classifyList = getClassifyList(smevo.getClassifyId());
		for (CargoClassifyDo ccdo : classifyList)
			civ.addClassify(ccdo.getId() + "", ccdo.getName());
		civ.setSupplier(smevo.getSupplierId() + "", smevo.getSupplierName());
		civ.setBrand(smevo.getBrandId() + "", smevo.getBrandName());
		civ.setSmallImage(smevo.getSmallImageId(), smevo.getSmallImageUrl());
		civ.setShowImages(getImageGroup(smevo.getShowImageGroupId()));
		civ.setDetailImages(getImageGroup(smevo.getDetailImageGroupId()));
		civ.setSkuTypes(getSkuTypes(cargoId));
		civ.setSkus(getSkuList(cargoId));
		return civ;
	}

	private ImageGroupVo getImageGroup(long groupId) {
		List<com.club.web.image.service.vo.ImageVo> list = imageService.selectImagesByGroupId(groupId);
		Assert.notNull(list, "图片组获取失败");
		ImageGroupVo result = new ImageGroupVo();
		result.setGroupId(groupId);
		ImageVo[] ivos = new ImageVo[list.size()];
		result.setImages(ivos);
		for (int i = 0; i < list.size(); i++) {
			com.club.web.image.service.vo.ImageVo ivo = list.get(i);
			Assert.notNull(ivo, "图片组获取失败, 图片错误");
			ImageVo vo = new ImageVo();
			vo.setId(ivo.getId());
			vo.setUrl(ivo.getPicUrl());
			ivos[i] = vo;
		}
		return result;
	}

	private List<CargoSkuTypeSimpleVo> getSkuTypes(long cargoId) {
		List<CargoSkuTypeDo> list = cargoSkuTypeRepository.getListByCargoId(cargoId);
		List<CargoSkuTypeSimpleVo> result = new ArrayList<>();
		for (CargoSkuTypeDo cargoSkuTypeDo : list)
			result.add(new CargoSkuTypeSimpleVo(cargoSkuTypeDo.getCargoBaseSkuTypeId() + "", cargoSkuTypeDo.getName(),
					cargoSkuTypeDo.getType() + "", getSkuItems(cargoSkuTypeDo)));
		return result;
	}

	private List<CargoSkuItemSimpleVo> getSkuItems(CargoSkuTypeDo skuType) {
		List<CargoSkuItemDo> list = skuType.getSkuItemList();
		List<CargoSkuItemSimpleVo> result = new ArrayList<>();
		for (CargoSkuItemDo cargoSkuItemDo : list)
			result.add(new CargoSkuItemSimpleVo(cargoSkuItemDo.getCargoBaseSkuItemId() + "", cargoSkuItemDo.getName(),
					cargoSkuItemDo.getValue()));
		return result;
	}

	private List<CargoClassifyDo> getClassifyList(long classifyId) {
		List<CargoClassifyDo> list = new ArrayList<>();
		CargoClassifyDo classify = cargoClassifyRepository.findDoById(classifyId);
		if (classify != null)
			list.add(classify);
		while (classify != null && classify.getParentId() != null)
			if ((classify = cargoClassifyRepository.findDoById(classify.getParentId())) != null
					&& classify.getName() != null && !classify.getName().isEmpty())
				list.add(0, classify);
		return list;
	}

	@Override
	public boolean isSkuCanDelete(long skuId) {
		Assert.state(skuId > 0, "SKU[" + skuId + "]无效");
		int count = stockManagerRepository.queryStockTotalBySkuId(skuId);
		if (count > 0) {
			CargoSkuDo csdo = cargoSkuRepository.getById(skuId);
			throw new RuntimeException("SKU[" + csdo.getCode() + "]尚有" + count + "件未处理");
		}
		return true;
	}
}
