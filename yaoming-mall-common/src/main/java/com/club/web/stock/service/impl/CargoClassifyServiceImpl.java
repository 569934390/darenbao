package com.club.web.stock.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.ListUtils;
import com.club.framework.util.StringUtils;
import com.club.web.stock.constant.CargoClassifyStatus;
import com.club.web.stock.constant.ClassifyConstant;
import com.club.web.stock.domain.CargoClassifyDo;
import com.club.web.stock.domain.repository.CargoClassifyRepository;
import com.club.web.stock.domain.repository.CargoRepository;
import com.club.web.stock.service.CargoClassifyService;
import com.club.web.stock.service.CargoService;
import com.club.web.stock.vo.CargoClassifyAppVo;
import com.club.web.stock.vo.CargoClassifyVo;
import com.club.web.util.IdGenerator;
/**
 * 货品分类服务接口实现类
 * @author zhuzd
 *
 */
@Service
@PropertySource("classpath:/config/props/version-switch.properties")
public class CargoClassifyServiceImpl implements CargoClassifyService {

	@Autowired
	private CargoClassifyRepository cargoClassifyRepository;
	
	@Autowired
	private CargoService cargoService;
	
	@Value("${server.version}")
	private String serverVersion;
	
	@Override
	public boolean updateStatus(String ids, Integer status,long userId)  throws Exception{
		if(StringUtils.isNotEmpty(ids) && status != null){
			Date updateTime = new Date();
			List<CargoClassifyDo> doList = cargoClassifyRepository.findDoListByIds(ids);
			for (CargoClassifyDo cargoClassifyDo : doList) {
				// TODO 这里的中文枚举看起来就怪怪的
				if(CargoClassifyStatus.禁用.getDbData() == status){
					cargoClassifyDo.updateStop(updateTime,userId,status);
				}else{
					cargoClassifyDo.updateStart(updateTime,userId,status);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteByIds(String ids) throws Exception {
		List<CargoClassifyDo> doList = cargoClassifyRepository.findDoListByIds(ids);
		Set<Long> idSet = new HashSet<Long>();
		for (CargoClassifyDo cargoClassifyDo : doList) {
			idSet.addAll(cargoClassifyDo.getAllIds(null));
		}
		if(cargoService.queryCargoCountByClassifyIds(new ArrayList<>(idSet)) > 0){
			throw new Exception("货品中存在被引用的货品分类，请修改所有被引用的货品分类再删除！");
		}
		for (CargoClassifyDo cargoClassifyDo : doList) {
			cargoClassifyDo.delete();
		}
		return true;
	}

	@Override
	public boolean add(long userId,CargoClassifyVo cargoClassifyVo) throws Exception {
		cargoClassifyVo.setId(IdGenerator.getDefault().nextId()+"");
		cargoClassifyVo.setCreateBy(userId);
		cargoClassifyVo.setUpdateBy(userId);
		Date date = new Date();
		cargoClassifyVo.setCreateTime(date);
		cargoClassifyVo.setUpdateTime(date);
		cargoClassifyVo.setParentId(cargoClassifyVo.getParentId()==null?ClassifyConstant.CARGO_CLASSIFY+"":cargoClassifyVo.getParentId());
		if("zuitese".equals(serverVersion)){
			cargoClassifyVo.setStatus(CargoClassifyStatus.禁用.getDbData());
		}else{
			if(!(ClassifyConstant.CARGO_CLASSIFY+"").equals(cargoClassifyVo.getParentId())){
				CargoClassifyDo parent = cargoClassifyRepository.findDoById(Long.valueOf(cargoClassifyVo.getParentId()));
				if(parent != null && CargoClassifyStatus.禁用.getDbData() == parent.getStatus()){
					cargoClassifyVo.setStatus(CargoClassifyStatus.禁用.getDbData());
				}else{
					cargoClassifyVo.setStatus(CargoClassifyStatus.启用.getDbData());
				}
			}else{
				cargoClassifyVo.setStatus(CargoClassifyStatus.启用.getDbData());
			}
		}
		getDomainByVo(cargoClassifyVo).save();
		//this.updateStatus(cargoClassifyVo.getId(), cargoClassifyVo.getStatus(), userId);
		return true;
	}

	@Override
	public boolean modify(long updaterId,CargoClassifyVo cargoClassifyVo) throws Exception {
		CargoClassifyDo target = cargoClassifyRepository.findDoById(Long.valueOf(cargoClassifyVo.getId()));
		target.setName(cargoClassifyVo.getName()!=null && !cargoClassifyVo.getName().trim().equals("")?cargoClassifyVo.getName():"");
		target.setParentId(cargoClassifyVo.getParentId()!=null?Long.valueOf(cargoClassifyVo.getParentId()):ClassifyConstant.CARGO_CLASSIFY);
		target.setOrderIndex(cargoClassifyVo.getOrderIndex()!= null?cargoClassifyVo.getOrderIndex():0);
		//target.setStatus(cargoClassifyVo.getStatus()!=null?cargoClassifyVo.getStatus():CargoClassifyStatus.启用.getDbData());
		target.setImgUrl(cargoClassifyVo.getImgUrl());
		target.setUpdateTime(new Date());
		target.setUpdateBy(updaterId);
		target.update();
		//this.updateStatus(target.getId()+"", cargoClassifyVo.getStatus(), updaterId);
		return  true;
	}
	
	@Override
	public CargoClassifyVo findVoByIdAndStatus(Long id,Integer status) throws Exception {
		return getVoByDomain(cargoClassifyRepository.findDoByIdAndStatus(id,status));
	}
	
	@Override
	public List<Long> getAllIdsByIdAndStatus(Long id, Integer status) throws Exception {
		return cargoClassifyRepository.getAllIdsByIdAndStatus(id,status);
	}
	
	@Override
	public List<CargoClassifyVo> getVoListByParentId(Long parentId,Integer status) throws Exception {
		return getVoListByDomainList(cargoClassifyRepository.findDoByParentIdAndStatus(parentId,status));
	}

	//最特色2行4列
	//凯握1行5列
	@Override
	public Object getAppParents(String parentId) throws Exception {
		return listToObject(cargoClassifyRepository.findDoByParentIdAndStatus(parentId == null?ClassifyConstant.CARGO_CLASSIFY:Long.valueOf(parentId),1),2,4);
	}

	@Override
	public List<CargoClassifyAppVo> mobileFirstAndSecondList() {
		List<CargoClassifyAppVo> result = new ArrayList<>();
		List<CargoClassifyDo> parents = cargoClassifyRepository.findDoByParentIdAndStatus(ClassifyConstant.CARGO_CLASSIFY,1);
		for(CargoClassifyDo cargoClassifyDo : parents){
			CargoClassifyAppVo appVo = getAppVoByDomain(cargoClassifyDo);
			appVo.setChildrens(getAppVoListByDomainList(cargoClassifyRepository.findDoByParentIdAndStatus(cargoClassifyDo.getId(),1)));
			result.add(appVo);
		}
		return result;
	}
	
	private List<CargoClassifyAppVo> getAppVoListByDomainList(List<CargoClassifyDo> srcs){
		List<CargoClassifyAppVo> targets = new ArrayList<CargoClassifyAppVo>();
		if(srcs != null && srcs.size() != 0){
			for (CargoClassifyDo src : srcs) {
				targets.add(getAppVoByDomain(src));
			}
		}
		return targets;
	}

	private CargoClassifyAppVo getAppVoByDomain(CargoClassifyDo src){
		CargoClassifyAppVo traget = null;
		if(src != null){
			traget = new CargoClassifyAppVo();
			traget.setClassify(src.getId()+"");
			traget.setText(src.getName());
			traget.setImgUrl(src.getImgUrl());
		}
		return traget;
	}
	
	private Object listToObject(List<CargoClassifyDo> cargoClassifyDos,int rowNum,int colNum){
		int row = 1;
		int col = 1;
		
		List<Map> resultList = new ArrayList<>();
		Map<String, List> resultMap = new HashMap<>();
		
		List<Map> rowMapList = new ArrayList<>();
		Map<String,List> rowMap = new HashMap<>();
		List<CargoClassifyAppVo> rowlist = new ArrayList<>();
		for (CargoClassifyDo cargoClassifyDo : cargoClassifyDos) {
			CargoClassifyAppVo appVo = new CargoClassifyAppVo();
			appVo.setClassify(cargoClassifyDo.getId()+"");
			appVo.setText(cargoClassifyDo.getName());
			appVo.setImgUrl(cargoClassifyDo.getImgUrl());
			rowlist.add(appVo);
			if(col%colNum == 0){
				rowMap.put("row", rowlist);
				rowMapList.add(rowMap);
				rowlist = new ArrayList<>();
				rowMap = new HashMap<>();
				if(row%rowNum == 0){
					resultMap.put("rowList", rowMapList);
					resultList.add(resultMap);
					resultMap = new HashMap<>();
					rowMapList = new ArrayList<>();
					row = 0;
				}
				col = 0;
				row++;
			}
			col++;
		}
		if(ListUtils.isNotEmpty(rowlist)){
			rowMap.put("row", rowlist);
		}if(!rowMap.isEmpty()){
			rowMapList.add(rowMap);
		}if(ListUtils.isNotEmpty(rowMapList) ){
			resultMap.put("rowList", rowMapList);
		}if(!resultMap.isEmpty()){
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	@Override
	public Page<CargoClassifyVo> list() throws Exception {
		Page<CargoClassifyVo> page = new Page<CargoClassifyVo>(0,Integer.MAX_VALUE);	
		page.setResultList(getVoListByDomainList(cargoClassifyRepository.findDoByParentId(ClassifyConstant.CARGO_CLASSIFY)));
		return page;
	}

	@Override
	public List<CargoClassifyVo> getVoAllList(Integer status) {
		return getVoListByDomainList(cargoClassifyRepository.findAllDoByStatus(status));
	}
	
	
	private List<CargoClassifyVo> getVoListByDomainList(List<CargoClassifyDo> srcs){
		List<CargoClassifyVo> targets = new ArrayList<CargoClassifyVo>();
		if(srcs != null && srcs.size() != 0){
			for (CargoClassifyDo src : srcs) {
				targets.add(getVoByDomain(src));
			}
		}
		return targets;
	}
	
	// TODO 这个方法没用到，可以先删掉或注释掉
	private List<CargoClassifyDo> getDomainListByVoList(List<CargoClassifyVo> srcs){
		List<CargoClassifyDo> targets = new ArrayList<CargoClassifyDo>();
		if(srcs != null && srcs.size() != 0){
			for (CargoClassifyVo src : srcs) {
				targets.add(getDomainByVo(src));
			}
		}
		return targets;
	}
	
	private CargoClassifyVo getVoByDomain(CargoClassifyDo src){
		CargoClassifyVo target = null;
		if(src != null){
			target = new CargoClassifyVo();
			target.setId(src.getId()+"");
			target.setName(src.getName());
			target.setParentId(src.getParentId()==null?null:src.getParentId()+"");
			target.setParent(getVoByDomain(src.getParentId() != null && !src.getParentId().equals(ClassifyConstant.CARGO_CLASSIFY)?src.getParent():null));
			target.setStatus(src.getStatus());
			target.setOrderIndex(src.getOrderIndex());			
			target.setCreateBy(src.getCreateBy());
			target.setCreateTime(src.getCreateTime());
			target.setUpdateBy(src.getUpdateBy());
			target.setUpdateTime(src.getUpdateTime());
			target.setImgUrl(src.getImgUrl());
		}
		return target;
	}

	private CargoClassifyDo getDomainByVo(CargoClassifyVo src){
		CargoClassifyDo target = null;
		if(src != null){
			target = new CargoClassifyDo();
			target.setId(Long.valueOf(src.getId()));
			target.setName(src.getName());
			target.setStatus(src.getStatus());
			target.setOrderIndex(src.getOrderIndex());
			target.setParentId(src.getParentId() == null ? null : Long.valueOf(src.getParentId()));
			target.setCreateBy(src.getCreateBy());
			target.setCreateTime(src.getCreateTime());
			target.setUpdateBy(src.getUpdateBy());
			target.setUpdateTime(src.getUpdateTime());
			target.setImgUrl(src.getImgUrl());
		}
		return target;
	}
}
