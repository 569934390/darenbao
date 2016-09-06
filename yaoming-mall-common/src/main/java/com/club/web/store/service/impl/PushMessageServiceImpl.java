package com.club.web.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.store.domain.MsgPushLogWithBLOBsDo;
import com.club.web.store.domain.repository.MsgPushRepository;
import com.club.web.store.service.PushMessageService;
import com.club.web.store.vo.MsgPushLogVo;
import com.club.web.store.vo.PushMsgVo;
import com.club.web.util.IdGenerator;
import com.club.web.util.SmsUtil;

/**
 * 信息推送-service接口
 * 
 * @author wqh
 * @add by 2016-05-09
 */
@Service
public class PushMessageServiceImpl implements PushMessageService {
	private Logger logger = LoggerFactory.getLogger(PushMessageServiceImpl.class);
	private Map<String, Object> result;
	@Autowired
	SmsUtil msgPush;
	@Autowired
	MsgPushRepository repository;

	/**
	 * 消息推送
	 * 
	 * @param pushObj
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	@Override
	public Map<String, Object> pushMsgSer(PushMsgVo pushObj) throws Exception {
		result = new HashMap<String, Object>();
		MsgPushLogWithBLOBsDo obj = null;
		if (pushObj != null) {
			if (StringUtils.isNotEmpty(pushObj.getContent())) {
				if (StringUtils.isNotEmpty(pushObj.getPushType())) {
					if (StringUtils.isNotEmpty(pushObj.getMsgType())) {
						String customerOrder = String.valueOf(IdGenerator.getDefault().nextId());
						result = msgPush.pushMsgUtil(pushObj, customerOrder);
						if (result != null) {
							try {
								obj = repository.createCarouseObj(result, pushObj, customerOrder);
								if (obj != null) {
									obj.save();
								}
							} catch (Exception e) {
								logger.error("消息推送记录保存失败:", e);
							}
						}
					} else {
						result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
						result.put(Constants.RESULT_MSG, "消息类型不能为空");
					}
				} else {
					result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
					result.put(Constants.RESULT_MSG, "推送类型不能为空");
				}
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "推送内容不能为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 查询消息推送状态
	 * 
	 * @param pushObj
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	@Override
	public Map<String, Object> getMsgPushStatus(PushMsgVo pushObj) throws Exception {
		result = new HashMap<String, Object>();
		if (pushObj != null) {
			if (StringUtils.isNotEmpty(pushObj.getMsgId())) {
				result = msgPush.getMsgPushStatus(pushObj);
			} else {
				result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
				result.put(Constants.RESULT_MSG, "消息Id不能为空");
			}
		} else {
			result.put(Constants.RESULT_CODE, Constants.PARAM_ERR_CODE);
			result.put(Constants.RESULT_MSG, "参数为空");
		}
		return result;
	}

	/**
	 * 查看消息推送列表
	 * 
	 * @param userId
	 * @param deviceType
	 * @param pageSize
	 * @param pageNum
	 * @return List<MsgPushLogVo>
	 * @add by 2016-05-10
	 */
	@Override
	public List<MsgPushLogVo> getMsgPushListSer(String userId, String deviceType, int pageSize, int pageNum) {
		List<MsgPushLogVo> list = null;
		int user = 0;
		if (StringUtils.isNotEmpty(userId)) {
			if (pageSize < 1) {
				pageSize = 6;
			}
			if (pageNum < 1) {
				pageNum = 1;
			}
			user = Integer.valueOf(userId);
			list = repository.getMsgPushList(user, deviceType, ((pageNum - 1) * pageSize), pageSize);
		}

		return list;
	}
}
