/**
 * 
 */
package com.club.web.store.service;

import com.club.web.store.vo.PushMsgVo;

/**
 * 消息推送公共接口
 * 
 * @author wqh
 *
 * @add by 2016-05-11
 */
public interface MessagePushCommon {
	void pushMsgUtil(PushMsgVo pushObj);
}
