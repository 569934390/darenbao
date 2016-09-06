package com.club.web.stock.service.impl;

import java.util.ArrayList;
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
import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.stock.domain.CargoSupplierDo;
import com.club.web.stock.domain.repository.CargoSupplierRepository;
import com.club.web.stock.service.CargoSupplierService;
import com.club.web.stock.vo.CargoSupplierVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

/**
 * @Title: CargoSupplierServiceImpl.java
 * @Package com.club.web.stock.service.impl
 * @Description: 供应商ServiceImpl
 * @author 柳伟军
 * @date 2016年3月26日 上午9:29:31
 * @version V1.0
 */
@Service("cargoSupplierService")
@Transactional
public class CargoSupplierServiceImpl implements CargoSupplierService {

	private Logger logger = LoggerFactory.getLogger(CargoSupplierServiceImpl.class);

	@Autowired
	CargoSupplierRepository cargoSupplierRepository;

	/**
	 * 添加修改供应商信息
	 */
	@Override
	public Map<String, Object> saveOrUpdateCargoSupplier(CargoSupplierVo cargoSupplier, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		if (cargoSupplier != null) {
			if (null == cargoSupplier.getCode() || "".equals(cargoSupplier.getCode())) {
				result.put("success", false);
				result.put("msg", "请输入供应商编号");
				return result;
			}
			if (null == cargoSupplier.getName() || "".equals(cargoSupplier.getName())) {
				result.put("success", false);
				result.put("msg", "请输入供应商名称");
				return result;
			}
			// TODO 这个变量名必须吐槽
			// 这个list仅仅使用了两次.size()方法
			// 可以考虑不返回list对象，返回count数量
			List<CargoSupplierVo> cargoSupplierByName = cargoSupplierRepository
					.queryCargoSupplierByName(cargoSupplier.getName());
			int count = cargoSupplierByName.size();
			/**
			 * 获取当前登录用户的session
			 */
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			if (cargoSupplier.getId() == null || "".equals(cargoSupplier.getId())) {
				if (count > 0) {
					result.put("success", false);
					result.put("msg", "该供应商名称已经存在!");
					return result;
				}
				cargoSupplier.setId(IdGenerator.getDefault().nextId() + "");
				cargoSupplier.setCreateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					cargoSupplier.setCreateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				cargoSupplier.setUpdateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					cargoSupplier.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				CargoSupplierDo cargoSupplierDo = cargoSupplierRepository.create(cargoSupplier);
				cargoSupplierDo.insert();
			} else {
				CargoSupplierDo cargoSupplierDo = cargoSupplierRepository
						.getCargoSupplierDoById(Long.parseLong(cargoSupplier.getId()));
				if (count > 0 && !cargoSupplierDo.getName().equalsIgnoreCase(cargoSupplier.getName())) {
					result.put("success", false);
					result.put("msg", "该供应商名称已经存在!");
					return result;
				}
				cargoSupplierDo.setName(cargoSupplier.getName());
				cargoSupplierDo.setContacts(cargoSupplier.getContacts());
				cargoSupplierDo.setTel(cargoSupplier.getTel());
				cargoSupplierDo.setAddr(cargoSupplier.getAddr());
				cargoSupplierDo.setUpdateTime(new Date());
				if (loginMap != null && loginMap.get("staffId") != null) {
					cargoSupplierDo.setUpdateBy(Long.parseLong(loginMap.get("staffId").toString()));
				}
				cargoSupplierDo.update();
			}
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "供应商信息不能为空");
		}

		return result;
	}

	/**
	 * 根据名称查询供应商分页信息
	 */
	public Page<Map<String, Object>> queryCargoSupplierPage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = cargoSupplierRepository.queryCargoSupplierPage(page);
		Long count = cargoSupplierRepository.queryCargoSupplierCountPage(page);
		result.setResultList(CommonUtil.listObjTransToListMap(list));
		result.setTotalRecords(count.intValue());
		return result;
	}

	/**
	 * 查询所有供应商
	 */
	@Override
	public List<CargoSupplierVo> findListAll() {

		List<CargoSupplierVo> list = null;
		list = cargoSupplierRepository.findListAll();
		return list;
	}

	/**
	 * 删除供应商信息
	 */
	public Map<String, Object> deleteCargoSupplier(String idStr) {
		String[] Ids = idStr.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		for (String id : Ids) {
			cargoSupplierRepository.deleteByPrimaryKey(Long.parseLong(id));
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 根据Id查询供应商
	 */
	public CargoSupplierVo findCargoSupplierById(Long id) {
		CargoSupplierVo cargoSupplier = null;
		cargoSupplier = cargoSupplierRepository.getCargoSupplierVoById(id);
		return cargoSupplier;
	}
}
