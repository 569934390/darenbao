package com.club.web.store.domain.repository;

import java.util.List;
import java.util.Map;

import com.club.web.store.domain.MsgPushLogWithBLOBsDo;
import com.club.web.store.vo.MsgPushLogVo;
import com.club.web.store.vo.PushMsgVo;

/**
 * 消息推送仓库
 * 
 * @author wqh
 * 
 * @add by 2016-05-10
 */
public interface MsgPushRepository extends CommonRepository {

	/**
	 * 创建对象
	 * 
	 * @param result
	 * @param pushObj
	 * @param customerOrder
	 * @return MsgPushLogWithBLOBsDo
	 * @add by 2016-05-10
	 */
	MsgPushLogWithBLOBsDo createCarouseObj(Map<String, Object> result, PushMsgVo pushObj, String customerOrder);

	/**
	 * 查看消息推送列表
	 * 
	 * @param userId
	 * @param deviceType
	 * @param start
	 * @param pageSize
	 * @return List<MsgPushLogVo>
	 * @add by 2016-05-10
	 */
	List<MsgPushLogVo> getMsgPushList(int userId, String deviceType, int start, int pageSize);
}
