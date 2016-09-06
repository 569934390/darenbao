package com.club.web.store.service;

import java.util.List;
import java.util.Map;

import com.club.web.store.vo.MsgPushLogVo;
import com.club.web.store.vo.PushMsgVo;

/**
 * 信息推送-service接口
 * 
 * @author wqh
 * @add by 2016-05-09
 */
public interface PushMessageService {

	/**
	 * 消息推送
	 * 
	 * @param pushObj
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	Map<String, Object> pushMsgSer(PushMsgVo pushObj) throws Exception;

	/**
	 * 查询消息推送状态
	 * 
	 * @param pushObj
	 * @return Map<String, Object>
	 * @add by 2016-04-13
	 */
	Map<String, Object> getMsgPushStatus(PushMsgVo pushObj) throws Exception;

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
	List<MsgPushLogVo> getMsgPushListSer(String userId, String deviceType, int pageSize, int pageNum);

}
