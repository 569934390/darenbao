package com.club.web.store.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.framework.util.BeanUtils;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.store.dao.repository.GoodSKUReposityImpl;
import com.club.web.store.domain.GoodEvaluationDo;
import com.club.web.store.domain.repository.GoodEvaluationRepository;
import com.club.web.store.service.GoodEvaluationService;
import com.club.web.store.vo.EvaluationImageVo;
import com.club.web.store.vo.GoodEvaluationVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.IdGenerator;

@Transactional
@Service("goodEvaluationServiceImlp")
public class GoodEvaluationServiceImlp implements GoodEvaluationService {

	@Autowired
	private GoodEvaluationRepository goodEvaluationRepository;
	@Autowired
	private GoodSKUReposityImpl goodSkuReposityImpl;
	@Autowired
	private ImageService iamgeService;

	@Override
	// 存储评价详情
	public void saveEvaluation(GoodEvaluationVo goodEvaluationVo, String[] urlArray, long userId) {
		// 先判断是否有图片传到后台，保存评价上传的图片
		long groupId=-1L;
		if(urlArray!=null && !urlArray.equals("") && urlArray.length !=0){
			groupId = IdGenerator.getDefault().nextId();
			iamgeService.saveOrUpdateByGroupId(groupId, userId, urlArray);
		}

		// 保存评价的信息
		GoodEvaluationDo evaluationDo = new GoodEvaluationDo();
		BeanUtils.copyProperties(goodEvaluationVo, evaluationDo);
		evaluationDo.setId(IdGenerator.getDefault().nextId());
		evaluationDo.setGoodSkuid(Long.parseLong(goodEvaluationVo.getGoodSkuid()));
		evaluationDo.setEvaluateTime(new Date());
		if(urlArray!=null && !urlArray.equals("") && urlArray.length !=0 &&groupId !=-1){
			evaluationDo.setImage(Long.toString(groupId));
		}
		goodEvaluationRepository.saveEvaluation(evaluationDo);
	}

	public List<GoodEvaluationVo> selectEvaluationByGoodId(long goodId) {
		List<GoodEvaluationVo> evaluationVoList = goodEvaluationRepository.selectEvaluationByGoodId(goodId);
		for (GoodEvaluationVo goodEvaluationVo : evaluationVoList) {
			goodEvaluationVo.setSkuName(goodSkuReposityImpl.selectSkuById(
					Long.parseLong(goodEvaluationVo.getGoodSkuid())).getCargoSkuName());
			String groupId = goodEvaluationVo.getImage();
			if (groupId != null && groupId != "") {
				List<ImageVo> imageVoList = iamgeService.selectImagesByGroupId(Long.parseLong(goodEvaluationVo
						.getImage()));
				List<EvaluationImageVo> eimageVoList = new ArrayList<EvaluationImageVo>();
				for (ImageVo imageVo : imageVoList) {
					EvaluationImageVo evaluationImageVo = new EvaluationImageVo();
					evaluationImageVo.setId(imageVo.getId());
					evaluationImageVo.setUrl(imageVo.getPicUrl());
					eimageVoList.add(evaluationImageVo);
				}
				goodEvaluationVo.setImageVoList(eimageVoList);
			}
		}
		return goodEvaluationRepository.selectEvaluationByGoodId(goodId);
	}

	/**
	 * 根据Id删除商品评价
	 * 
	 * @param id
	 * @return Map<String, Object>
	 * @add by 2016-04-25
	 */
	@Override
	public Map<String, Object> deleteEvaluationSer(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (CommonUtil.isDigital(id)) {
			goodEvaluationRepository.deleteById(Long.valueOf(id));
			result.put("code", "0");
			result.put("msg", "删除成功！");
		} else {
			result.put("code", "-2");
			result.put("msg", "参数有误！");
		}
		return result;
	}
}
