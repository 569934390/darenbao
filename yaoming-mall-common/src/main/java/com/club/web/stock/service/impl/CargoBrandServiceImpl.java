package com.club.web.stock.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.core.common.Page;
import com.club.web.common.Constants;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.stock.domain.CargoBrandDo;
import com.club.web.stock.domain.repository.CargoBrandRepository;
import com.club.web.stock.service.CargoBrandService;
import com.club.web.stock.vo.CargoBrandVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

/**
 * @Title: CargoBrandServiceImpl.java
 * @Package com.club.web.stock.service.impl
 * @Description: 品牌ServiceImpl
 * @author 柳伟军
 * @date 2016年3月26日 上午9:29:48
 * @version V1.0
 */
@Service("cargoBrandService")
@Transactional
public class CargoBrandServiceImpl implements CargoBrandService {

	private Logger logger = LoggerFactory.getLogger(CargoBrandServiceImpl.class);

	@Autowired
	CargoBrandRepository cargoBrandRepository;
	@Autowired
	ImageService imageService;

	/**
	 * 添加修改品牌信息
	 */
	@Override
	public Map<String, Object> saveOrUpdateCargoBrand(CargoBrandVo cargoBrand, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		if (cargoBrand != null) {
			if (null == cargoBrand.getName() || "".equals(cargoBrand.getName())) {
				result.put("success", false);
				result.put("msg", "请输入品牌名称");
				return result;
			}

			// TODO 这个变量名必须吐槽
			// 这个list仅仅使用了两次.size()方法
			// 可以考虑不返回list对象，返回count数量
			List<CargoBrandVo> cargoBrandByName = cargoBrandRepository.queryCargoBrandByName(cargoBrand.getName());
			int count = cargoBrandByName.size();
			/**
			 * 获取当前登录用户的session
			 */
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);

			if (cargoBrand.getId() == null || "".equals(cargoBrand.getId())) {
				if (count > 0) {
					result.put("success", false);
					result.put("msg", "该品牌名称已经存在!");
					return result;
				}
				cargoBrand.setId(IdGenerator.getDefault().nextId() + "");
				// 如果传过来的图片不为空。则保存记录
				if (cargoBrand.getLogo() != null && !"".equals(cargoBrand.getLogo())) {
					ImageVo imageVo = imageService.saveImage(cargoBrand.getLogo());
					cargoBrand.setLogo(imageVo.getId() + "");
				}
				cargoBrand.setCreateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					cargoBrand.setCreateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				cargoBrand.setUpdateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					cargoBrand.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				CargoBrandDo cargoBrandDo = cargoBrandRepository.create(cargoBrand);
				cargoBrandDo.insert();
			} else {
				CargoBrandDo cargoBrandDo = cargoBrandRepository
						.getCargoBrandDoById(Long.parseLong(cargoBrand.getId()));
				if (count > 0 && !cargoBrandDo.getName().equalsIgnoreCase(cargoBrand.getName())) {
					result.put("success", false);
					result.put("msg", "该品牌名称已经存在!");
					return result;
				}

				// 如果之前图片为空
				if (cargoBrandDo.getLogo() == null || "".equals(cargoBrandDo.getLogo())) {
					// 如果传过来的图片不为空。则保存记录
					if (cargoBrand.getLogo() != null && !"".equals(cargoBrand.getLogo())) {
						ImageVo imageVo = imageService.saveImage(cargoBrand.getLogo());
						cargoBrandDo.setLogo(imageVo.getId() + "");
					}
				} else {
					// 如果传过来的图片不为空。则更新记录
					if (cargoBrand.getLogo() != null && !"".equals(cargoBrand.getLogo())) {
						// 查询图片记录并更新
						ImageVo imageVo = imageService.selectImageById(Long.parseLong(cargoBrandDo.getLogo()));
						imageVo.setPicUrl(cargoBrand.getLogo());
						imageService.updateImage(imageVo);
					} else {
						// 如果传过来的图片为空，则删除记录
						imageService.deleteById(Long.parseLong(cargoBrandDo.getLogo()));
						cargoBrandDo.setLogo(null);
					}
				}
				cargoBrandDo.setName(cargoBrand.getName());
				cargoBrandDo.setUrl(cargoBrand.getUrl());
				cargoBrandDo.setSupplierName(cargoBrand.getSupplierName());
				cargoBrandDo.setBrandRecommendation(cargoBrand.getBrandRecommendation());
				cargoBrandDo.setUpdateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					cargoBrandDo.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				cargoBrandDo.update();
			}
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "品牌信息不能为空");
		}

		return result;
	}

	/**
	 * 根据名称查询品牌分页信息
	 */
	public Page<Map<String, Object>> queryCargoBrandPage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = cargoBrandRepository.queryCargoBrandPage(page);
		Long count = cargoBrandRepository.queryCargoBrandCountPage(page);
		result.setResultList(CommonUtil.listObjTransToListMap(list));
		result.setTotalRecords(count.intValue());
		return result;
	}

	/**
	 * 查询所有品牌
	 */
	@Override
	public List<CargoBrandVo> findListAll() {

		List<CargoBrandVo> list = null;
		list = cargoBrandRepository.findListAll();

		return list;
	}

	/**
	 * 删除品牌信息
	 */
	public Map<String, Object> deleteCargoBrand(String idStr) {
		String[] Ids = idStr.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		for (String id : Ids) {

			// 根据id查询
			CargoBrandDo cargoBrandDo = cargoBrandRepository.getCargoBrandDoById(Long.parseLong(id));

			// 删除图片
			if (cargoBrandDo.getLogo() != null && !"".equals(cargoBrandDo.getLogo())) {
				imageService.deleteById(Long.parseLong(cargoBrandDo.getLogo()));
			}
			// 删除
			cargoBrandDo.delete();
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 根据Id查询品牌
	 */
	public CargoBrandVo findCargoBrandById(Long id) {
		CargoBrandVo cargoBrand = null;
		cargoBrand = cargoBrandRepository.getCargoBrandVoById(id);
		return cargoBrand;
	}

}
