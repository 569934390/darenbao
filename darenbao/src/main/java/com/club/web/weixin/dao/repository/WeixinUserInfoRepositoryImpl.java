package com.club.web.weixin.dao.repository;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.web.util.IdGenerator;
import com.club.web.weixin.dao.base.po.WeixinUserInfo;
import com.club.web.weixin.dao.extend.WeixinUserInfoExtendMapper;
import com.club.web.weixin.domain.WeixinUserInfoDo;
import com.club.web.weixin.domain.repository.WeixinUserInfoRepository;
import com.club.web.weixin.util.EmojiFilter;
import com.club.web.weixin.vo.FansMsgVo;
import com.club.web.weixin.vo.WeixinUserInfoVo;

@Repository
public class WeixinUserInfoRepositoryImpl implements WeixinUserInfoRepository {

	@Autowired
	WeixinUserInfoExtendMapper weixinUserInfoExtendMapper;

	@Override
	public WeixinUserInfoDo create(WeixinUserInfoVo oldWeixinUserInfoVo) {
		if (oldWeixinUserInfoVo == null) {
			return null;
		}
		WeixinUserInfoDo weixinUserInfoDo = new WeixinUserInfoDo();
		BeanUtils.copyProperties(oldWeixinUserInfoVo, weixinUserInfoDo);
		weixinUserInfoDo.setId(IdGenerator.getDefault().nextId());
		return weixinUserInfoDo;
	}

	private WeixinUserInfo getPoByDo(WeixinUserInfoDo weixinUserInfoDo) {
		if (weixinUserInfoDo == null) {
			return null;
		}
		WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
		BeanUtils.copyProperties(weixinUserInfoDo, weixinUserInfo);
		return weixinUserInfo;
	}

	private WeixinUserInfoDo getDoByPo(WeixinUserInfo weixinUserInfo) {
		if (weixinUserInfo == null) {
			return null;
		}
		WeixinUserInfoDo weixinUserInfoDo = new WeixinUserInfoDo();
		BeanUtils.copyProperties(weixinUserInfo, weixinUserInfoDo);
		return weixinUserInfoDo;
	}

	@Override
	public WeixinUserInfoVo findByOpenId(String openId) {
		return weixinUserInfoExtendMapper.findByOpenId(openId);
	}

	@Override
	public void insert(WeixinUserInfoDo weixinUserInfoDo) {
		weixinUserInfoExtendMapper.insert(getPoByDo(weixinUserInfoDo));
	}

	@Override
	public void update(WeixinUserInfoDo weixinUserInfoDo) {
		weixinUserInfoExtendMapper.updateByPrimaryKey(getPoByDo(weixinUserInfoDo));
	}

	@Override
	public List<Map<String, Object>> queryWeixinFansPage(Page<Map<String, Object>> page) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		map.put("name", page.getConditons().get("name").toString());
		map.put("address", page.getConditons().get("address").toString());
		map.put("startTime", page.getConditons().get("startTime").toString());
		map.put("endTime", page.getConditons().get("endTime").toString());
		return weixinUserInfoExtendMapper.queryWeixinFansPage(map);
	}

	@Override
	public Long queryWeixinFansCountPage(Page<Map<String, Object>> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", page.getConditons().get("name").toString());
		map.put("address", page.getConditons().get("address").toString());
		map.put("startTime", page.getConditons().get("startTime").toString());
		map.put("endTime", page.getConditons().get("endTime").toString());
		return weixinUserInfoExtendMapper.queryWeixinFansCountPage(map);
	}

	@Override
	public WeixinUserInfoDo findWeixinUserInfoDoById(String id) {
		return getDoByPo(weixinUserInfoExtendMapper.selectByPrimaryKey(Long.parseLong(id)));
	}

	/**
	 * 查看店铺的粉丝列表
	 * 
	 * @param shopId
	 * @param startIndex
	 * @param pageSize
	 * @return List<FansMsgVo>
	 * @add by 2016-05-13
	 */
	@Override
	public List<FansMsgVo> getFansList(long shopId, int startIndex, int pageSize) {
		List<FansMsgVo> list = weixinUserInfoExtendMapper.getFansList(shopId, startIndex, pageSize);
		return list;
	}

}
