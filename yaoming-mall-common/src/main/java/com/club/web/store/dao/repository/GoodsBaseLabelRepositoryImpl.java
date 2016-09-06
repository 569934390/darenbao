package com.club.web.store.dao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.GoodsBaseLabel;
import com.club.web.store.dao.extend.GoodsBaseLabelExtendMapper;
import com.club.web.store.domain.GoodsBaseLabelDo;
import com.club.web.store.domain.repository.GoodsBaseLabelRepository;
import com.club.web.store.vo.GoodLabelsVo;
import com.club.web.store.vo.GoodsBaseLabelVo;

/**   
* @Title: GoodsBaseLabelRepositoryImpl.java
* @Package com.club.web.store.dao.repository
* @Description: 商品基础标签domain接口实现类
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Repository
public class GoodsBaseLabelRepositoryImpl implements GoodsBaseLabelRepository{

	@Autowired
	GoodsBaseLabelExtendMapper GoodBaseLabelDao;
	
	@Override
	public List<GoodsBaseLabelVo> selectGoodsBaseLabelByLabelName(Map<String, Object> map){
		
		return GoodBaseLabelDao.selectGoodsBaseLabelByLabelName(map);
	}
	
	@Override
	public int addGoodsBaseLabel(GoodsBaseLabelDo goodsBaseLabelDo) {
		GoodsBaseLabel goodsBaseLabel = new GoodsBaseLabel();
		BeanUtils.copyProperties(goodsBaseLabelDo, goodsBaseLabel);
		
		return GoodBaseLabelDao.insert(goodsBaseLabel);
	}
	
	@Override
	public int editGoodsBaseLabel(GoodsBaseLabelDo goodsBaseLabelDo) {
		GoodsBaseLabel goodsBaseLabel = new GoodsBaseLabel();
		BeanUtils.copyProperties(goodsBaseLabelDo, goodsBaseLabel);
		
		return GoodBaseLabelDao.updateByPrimaryKeySelective(goodsBaseLabel);
	}
	
	@Override
	public int deleteGoodsBaseLabel(Long id) {
		
		return GoodBaseLabelDao.deleteByPrimaryKey(id);
	}

	@Override
	public int updateStatusById(Long id, String status) {
		 
		return GoodBaseLabelDao.updateStatusById(id, status);
	}
	
	@Override
	public Long queryGoodsBaseLabelCountPage(Map<String, Object> map) {
		
		return GoodBaseLabelDao.queryGoodsBaseLabelCountPage(map);
	}
	
	@Override
	public GoodsBaseLabelDo voChangeDo(GoodsBaseLabelVo goodsBaseLabelVo) {
		GoodsBaseLabelDo newGoodsBaseLabelDo = create();
		if(goodsBaseLabelVo != null){
			BeanUtils.copyProperties(goodsBaseLabelVo, newGoodsBaseLabelDo);
			newGoodsBaseLabelDo.setId(Long.valueOf(goodsBaseLabelVo.getId()));
			if(goodsBaseLabelVo.getShopId() != null && !goodsBaseLabelVo.getShopId().isEmpty()){
				newGoodsBaseLabelDo.setShopId(Long.valueOf(goodsBaseLabelVo.getShopId()));
			}
		}
		
		return newGoodsBaseLabelDo;
	}
	
	@Override
	public GoodsBaseLabelDo create(){
		GoodsBaseLabelDo newGoodsBaseLabelDo = new GoodsBaseLabelDo();
		
		return newGoodsBaseLabelDo;
	}
	
	@Override
	public List<GoodsBaseLabelVo> selectGoodsBaseLabelListByLabelName(Map<String, Object> map) {
		
		return GoodBaseLabelDao.selectGoodsBaseLabelListByLabelName(map);
	}
	
	@Override
	public GoodsBaseLabelVo selectGoodsBaseLabelById(Long id) {
		GoodsBaseLabelVo goodsBaseLabelVo = new GoodsBaseLabelVo();
		GoodsBaseLabel goodsBaseLabel = GoodBaseLabelDao.selectByPrimaryKey(id);
		BeanUtils.copyProperties(goodsBaseLabel, goodsBaseLabelVo);
		if(goodsBaseLabel != null && goodsBaseLabel.getShopId() != null){
			goodsBaseLabelVo.setShopId(goodsBaseLabel.getShopId().toString());
		}
		
		return goodsBaseLabelVo;
	}
	
	
	@Override
	public List<GoodsBaseLabelVo> selectGoodsBaseLabelListByGoodId(long goodId) {
		
		return GoodBaseLabelDao.selectGoodsBaseLabelListByGoodId(goodId);
	}

	@Override
	public List<GoodsBaseLabelVo> findListAll() {
		return GoodBaseLabelDao.findListAll();
	}
}
