package com.club.web.store.dao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.po.GoodsColumn;
import com.club.web.store.dao.extend.GoodsColumnExtendMapper;
import com.club.web.store.domain.GoodsColumnDo;
import com.club.web.store.domain.repository.GoodsColumnRepository;
import com.club.web.store.vo.GoodListVo;
import com.club.web.store.vo.GoodsColumnIndexVo;
import com.club.web.store.vo.GoodsColumnVo;

/**   
* @Title: GoodsColumnRepositoryImpl.java
* @Package com.club.web.store.dao.repository
* @Description: 商品基础栏目domain接口实现类
* @author hqLin   
* @date 2016/03/19
* @version V1.0   
*/

@Repository
public class GoodsColumnRepositoryImpl implements GoodsColumnRepository{

	@Autowired
	GoodsColumnExtendMapper GoodsColumnDao;
	
	@Override
	public List<GoodsColumnVo> selectGoodsColumnByColumnName(Map<String, Object> map){
		
		return GoodsColumnDao.selectGoodsColumnByColumnName(map);
	}
	
	@Override
	public int addGoodsColumn(GoodsColumnDo goodsColumnDo) {
		GoodsColumn goodsColumn = new GoodsColumn();
		BeanUtils.copyProperties(goodsColumnDo, goodsColumn);
		
		return GoodsColumnDao.insert(goodsColumn);
	}
	
	@Override
	public int editGoodsColumn(GoodsColumnDo goodsColumnDo) {
		GoodsColumn goodsColumn = new GoodsColumn();
		BeanUtils.copyProperties(goodsColumnDo, goodsColumn);
		
		return GoodsColumnDao.updateByPrimaryKeySelective(goodsColumn);
	}
	
	@Override
	public int deleteGoodsColumn(Long id) {
		
		return GoodsColumnDao.deleteByPrimaryKey(id);
	}

	@Override
	public int updateStatusForGoodsColumnById(Long id, String status) {
		 
		return GoodsColumnDao.updateStatusForGoodsColumnById(id, status);
	}
	
	@Override
	public Long queryGoodsColumnCountPage(Map<String, Object> map) {
		
		return GoodsColumnDao.queryGoodsColumnCountPage(map);
	}
	
	@Override
	public GoodsColumnDo voChangeDo(GoodsColumnVo goodsColumnVo) {
		GoodsColumnDo newGoodsBaseLabelDo = create();
		if(goodsColumnVo != null){
			BeanUtils.copyProperties(goodsColumnVo, newGoodsBaseLabelDo);
			newGoodsBaseLabelDo.setId(Long.valueOf(goodsColumnVo.getId()));
			if(goodsColumnVo.getShopId() != null && !goodsColumnVo.getShopId().isEmpty()){
				newGoodsBaseLabelDo.setShopId(Long.valueOf(goodsColumnVo.getShopId()));
			}
			if(goodsColumnVo.getRuleSourceId() != null && !goodsColumnVo.getRuleSourceId().isEmpty()){
				newGoodsBaseLabelDo.setRuleSourceId(Long.valueOf(goodsColumnVo.getRuleSourceId()));
			}
		}
		
		return newGoodsBaseLabelDo;
	}
	
	@Override
	public GoodsColumnDo create(){
		GoodsColumnDo newGoodsColumnDo = new GoodsColumnDo();
		
		return newGoodsColumnDo;
	}
	
	@Override
	public List<GoodListVo> selectColumnAllGoods(Map<String, Object> map){
		
		return GoodsColumnDao.selectColumnAllGoods(map);
	}
	
	@Override
	public List<GoodListVo> selectGoodsByColumnId(Map<String, Object> map){
		
		return GoodsColumnDao.selectGoodsByColumnId(map);
	}
	
	@Override
	public List<GoodListVo> selectColumnsGood(Map<String, Object> map){
		
		return GoodsColumnDao.selectColumnsGood(map);
	}

	@Override
	public List<GoodsColumnIndexVo> selectNoRuleGoodsColumn() {
		return GoodsColumnDao.selectNoRuleGoodsColumn();
	}

	@Override
	public List<GoodsColumnIndexVo> selectRuleGoodsColumn() {
		return GoodsColumnDao.selectRuleGoodsColumn();
	}
}