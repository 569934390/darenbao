package com.club.web.weixin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.StringUtils;
import com.club.web.event.domain.EventActivityUserinfoDo;
import com.club.web.event.domain.repository.EventActivityUserinfoRepository;
import com.club.web.event.service.EventActivityUserinfoService;
import com.club.web.event.vo.EventActivityUserinfoVo;
import com.club.web.util.IdGenerator;
import com.club.web.weixin.config.Constants;
import com.club.web.weixin.config.WeixinGlobal;
import com.club.web.weixin.domain.WeixinUserInfoDo;
import com.club.web.weixin.domain.repository.WeixinUserInfoRepository;
import com.club.web.weixin.service.WeixinStoreWeixinuserService;
import com.club.web.weixin.service.WeixinUserInfoService;
import com.club.web.weixin.util.AdvancedUtil;
import com.club.web.weixin.util.EmojiFilter;
import com.club.web.weixin.vo.FansMsgVo;
import com.club.web.weixin.vo.WeixinUserInfoVo;
import com.club.web.weixin.weixinpojo.WeixinOauth2Token;

@Service("weixinClientService")
public class WeixinUserInfoServiceImpl implements WeixinUserInfoService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	WeixinUserInfoRepository weixinUserInfoRepository;// 微信会员Service

	@Autowired
	EventActivityUserinfoService eventActivityUserinfoService; // 活动用户Service

	@Autowired
	EventActivityUserinfoRepository activityUserinfoRepository; // 活动用户Dao

	@Autowired
	WeixinStoreWeixinuserService weixinStoreWeixinuserService; // 微信用户分店Service

	public WeixinUserInfoVo getVoByDo(WeixinUserInfoDo weixinUserInfoDo) {
		if(weixinUserInfoDo==null){
			return null;
		}
		WeixinUserInfoVo weixinUserInfoVo = new WeixinUserInfoVo();
		BeanUtils.copyProperties(weixinUserInfoDo, weixinUserInfoVo);
		weixinUserInfoVo.setId(weixinUserInfoDo.getId() + "");
		return weixinUserInfoVo;
	}

	public EventActivityUserinfoVo getVoByDo(EventActivityUserinfoDo eventActivityUserinfoDo) {
		if(eventActivityUserinfoDo==null){
			return null;
		}
		EventActivityUserinfoVo eventActivityUserinfoVo = new EventActivityUserinfoVo();
		BeanUtils.copyProperties(eventActivityUserinfoDo, eventActivityUserinfoVo);
		eventActivityUserinfoVo.setId(eventActivityUserinfoDo.getId() + "");
		return eventActivityUserinfoVo;
	}

	private WeixinUserInfoDo getDoByVo(WeixinUserInfoVo weixinUserInfoVo) {
		if(weixinUserInfoVo==null){
			return null;
		}
		WeixinUserInfoDo weixinUserInfoDo = new WeixinUserInfoDo();
		BeanUtils.copyProperties(weixinUserInfoVo, weixinUserInfoDo);
		weixinUserInfoDo.setId(Long.parseLong(weixinUserInfoVo.getId()));
		return weixinUserInfoDo;
	}

	/**
	 * 分页
	 */
	public Page<Map<String, Object>> queryWeixinFansPage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = weixinUserInfoRepository.queryWeixinFansPage(page);
		Long count = weixinUserInfoRepository.queryWeixinFansCountPage(page);
		result.setResultList(list);
		result.setTotalRecords(count.intValue());
		return result;
	}

	@Override
	public String weixinIndex(String code, String storeId, HttpServletRequest request) {
		logger.info("WeixinClientServiceImpl weixinIndex code-->{}", code);

		String userId = "";
		// 用户同意授权
		if (!"authdeny".equals(code)) {

			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(WeixinGlobal.getAppid(),
					WeixinGlobal.getAppsecret(), code);
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();

			logger.info("WeixinClientController oAuth openId-->{}", openId);

			// 获取用户信息
			WeixinUserInfoVo weixinUserinfoVo = AdvancedUtil.getWeixinUserInfoVo(accessToken, openId);
			weixinUserinfoVo.setNickname(EmojiFilter.filterEmoji(weixinUserinfoVo.getNickname()));

			WeixinUserInfoVo oldWeixinUserInfoVo = weixinUserInfoRepository.findByOpenId(weixinUserinfoVo.getOpenid());
			Date nowTime = new Date();
			if (oldWeixinUserInfoVo != null) {
				// 更新
				WeixinUserInfoDo weixinUserInfoDo = new WeixinUserInfoDo();
				BeanUtils.copyProperties(oldWeixinUserInfoVo, weixinUserInfoDo);
				weixinUserInfoDo.setId(Long.parseLong(oldWeixinUserInfoVo.getId()));
				weixinUserInfoDo.setSubscribe(weixinUserinfoVo.getSubscribe());
				weixinUserInfoDo.setSubscribetime(weixinUserinfoVo.getSubscribetime());
				weixinUserInfoDo.setNickname(weixinUserinfoVo.getNickname());
				weixinUserInfoDo.setSex(weixinUserinfoVo.getSex());
				weixinUserInfoDo.setCountry(weixinUserinfoVo.getCountry());
				weixinUserInfoDo.setProvince(weixinUserinfoVo.getProvince());
				weixinUserInfoDo.setCity(weixinUserinfoVo.getCity());
				weixinUserInfoDo.setLanguage(weixinUserinfoVo.getLanguage());
				weixinUserInfoDo.setHeadimgurl(weixinUserinfoVo.getHeadimgurl());
				weixinUserInfoDo.setLastLoginTime(nowTime);
				weixinUserInfoDo.update();

				userId =weixinUserInfoDo.getId()+"";

				// 会员店铺表保存修改
				weixinStoreWeixinuserService.saveorUpdate(Long.parseLong(storeId), weixinUserInfoDo.getId());

				// 保存session
				request.getSession().setAttribute(Constants.WEIXIN_USERINFO_SESSION, getVoByDo(weixinUserInfoDo));
			} else {
				// 新增
				weixinUserinfoVo.setCreateTime(nowTime);
				weixinUserinfoVo.setLastLoginTime(nowTime);
				WeixinUserInfoDo weixinUserInfoDo = weixinUserInfoRepository.create(weixinUserinfoVo);
				weixinUserInfoDo.insert();

				userId =weixinUserInfoDo.getId()+"";

				// 会员店铺表保存修改
				weixinStoreWeixinuserService.saveorUpdate(Long.parseLong(storeId), weixinUserInfoDo.getId());

				// 保存session
				request.getSession().setAttribute(Constants.WEIXIN_USERINFO_SESSION, getVoByDo(weixinUserInfoDo));
			}
		}
		return userId;
	}

	@Override
	public String weixinEvent(String code, String storeId, HttpServletRequest request) {
		logger.error("WeixinClientServiceImpl weixinEvent code-->{}", code);

		String userId = "";
		// 用户同意授权
		if (!"authdeny".equals(code)) {

			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(WeixinGlobal.getAppid(),
					WeixinGlobal.getAppsecret(), code);
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();

			logger.error("WeixinClientController oAuth openId-->{}", openId);

			// 获取用户信息
			WeixinUserInfoVo weixinUserinfoVo = AdvancedUtil.getWeixinUserInfoVo(accessToken, openId);
			weixinUserinfoVo.setNickname(EmojiFilter.filterEmoji(weixinUserinfoVo.getNickname()));

			EventActivityUserinfoVo oldeventActivityUserinfoVo = eventActivityUserinfoService
					.findByOpenId(weixinUserinfoVo.getOpenid());
			Date nowTime = new Date();
			if (oldeventActivityUserinfoVo != null) {
				// 更新
				EventActivityUserinfoDo eventActivityUserinfoDo = new EventActivityUserinfoDo();
				BeanUtils.copyProperties(oldeventActivityUserinfoVo, eventActivityUserinfoDo);
				eventActivityUserinfoDo.setId(Long.parseLong(oldeventActivityUserinfoVo.getId()));
				eventActivityUserinfoDo.setSubscribe(weixinUserinfoVo.getSubscribe());
				eventActivityUserinfoDo.setSubscribetime(weixinUserinfoVo.getSubscribetime());
				eventActivityUserinfoDo.setNickname(weixinUserinfoVo.getNickname());
				eventActivityUserinfoDo.setSex(weixinUserinfoVo.getSex());
				eventActivityUserinfoDo.setCountry(weixinUserinfoVo.getCountry());
				eventActivityUserinfoDo.setProvince(weixinUserinfoVo.getProvince());
				eventActivityUserinfoDo.setCity(weixinUserinfoVo.getCity());
				eventActivityUserinfoDo.setLanguage(weixinUserinfoVo.getLanguage());
				eventActivityUserinfoDo.setHeadimgurl(weixinUserinfoVo.getHeadimgurl());
				eventActivityUserinfoDo.setLastLoginTime(nowTime);
				eventActivityUserinfoDo.update();
				
				userId =eventActivityUserinfoDo.getId()+"";
				// 保存session
				request.getSession().setAttribute(Constants.EVENT_ACTIVITY_USERINFO_SESSION,
						getVoByDo(eventActivityUserinfoDo));
			} else {
				// 新增
				EventActivityUserinfoDo eventActivityUserinfoDo = activityUserinfoRepository.create(weixinUserinfoVo);
				eventActivityUserinfoDo.setId(IdGenerator.getDefault().nextId());
				eventActivityUserinfoDo.setLastLoginTime(nowTime);
				eventActivityUserinfoDo.setCreateTime(nowTime);
				eventActivityUserinfoDo.insert();

				userId =eventActivityUserinfoDo.getId()+"";
				
				// 保存session
				request.getSession().setAttribute(Constants.EVENT_ACTIVITY_USERINFO_SESSION,
						getVoByDo(eventActivityUserinfoDo));
			}
		}
		return userId;
	}

	/**
	 * 手机端修改个人信息
	 */
	public Map<String, Object> updateMyInfo(WeixinUserInfoVo weixinUserInfoVo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (weixinUserInfoVo.getUserName() == null || "".equals(weixinUserInfoVo.getUserName())) {
			result.put("success", false);
			result.put("msg", "用户名不能为空！");
		}
		if (weixinUserInfoVo.getTel() == null || "".equals(weixinUserInfoVo.getTel())) {
			result.put("success", false);
			result.put("msg", "电话不能为空！");
		}

		WeixinUserInfoDo weixinUserInfoDo = weixinUserInfoRepository.findWeixinUserInfoDoById(weixinUserInfoVo.getId());
		weixinUserInfoDo.setUserName(weixinUserInfoVo.getUserName());
		weixinUserInfoDo.setTel(weixinUserInfoVo.getTel());
		weixinUserInfoDo.setWeixinNum(weixinUserInfoVo.getWeixinNum());
		weixinUserInfoDo.setBirthday(weixinUserInfoVo.getBirthday());
		weixinUserInfoDo.setPersonSign(weixinUserInfoVo.getPersonSign());
		weixinUserInfoDo.setLastLoginTime(new Date());
		weixinUserInfoDo.update();

		// 修改session
		request.getSession().setAttribute(Constants.WEIXIN_USERINFO_SESSION, getVoByDo(weixinUserInfoDo));
		result.put("success", true);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> loadClientInfo(Long id) {
		Map<String, Object> map =null;
		WeixinUserInfoDo weixinUserInfoDo = weixinUserInfoRepository.findWeixinUserInfoDoById(id + "");
		if(weixinUserInfoDo!=null){
			WeixinUserInfoVo weixinUserInfoVo = new WeixinUserInfoVo();
			BeanUtils.copyProperties(weixinUserInfoDo, weixinUserInfoVo);
			weixinUserInfoVo.setId(weixinUserInfoDo.getId() + "");
			map = JsonUtil.toMap(weixinUserInfoVo);
		}
		return map;
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
	public List<FansMsgVo> getFansListSer(String shopId, int startIndex, int pageSize) {
		List<FansMsgVo> list = null;
		long shop = 0;
		if (StringUtils.isNotEmpty(shopId)) {
			shop = Long.valueOf(shopId);
			list = weixinUserInfoRepository.getFansList(shop, startIndex, pageSize);
		}
		return list;
	}

}
