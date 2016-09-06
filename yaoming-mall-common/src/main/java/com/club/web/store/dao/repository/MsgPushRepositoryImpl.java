/**
 * 
 */
package com.club.web.store.dao.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.store.dao.base.po.MsgPushLogWithBLOBs;
import com.club.web.store.dao.extend.MsgPushLogExtendMapper;
import com.club.web.store.domain.MsgPushLogWithBLOBsDo;
import com.club.web.store.domain.repository.MsgPushRepository;
import com.club.web.store.vo.MsgPushLogVo;
import com.club.web.store.vo.PushMsgVo;
import com.club.web.util.IdGenerator;

/**
 * 消息推送仓库
 * 
 * @author wqh
 *
 */
@Repository
public class MsgPushRepositoryImpl implements MsgPushRepository {
	@Autowired
	MsgPushLogExtendMapper pushDao;

	/**
	 * 保存对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-12
	 */
	@Override
	public <T> void save(T t) throws Exception {
		if (t != null) {
			if (t instanceof MsgPushLogWithBLOBsDo) {
				MsgPushLogWithBLOBsDo obj = (MsgPushLogWithBLOBsDo) t;
				MsgPushLogWithBLOBs po = new MsgPushLogWithBLOBs();
				BeanUtils.copyProperties(obj, po);
				pushDao.insert(po);
			} else {
				throw new Exception("实例不存在");
			}
		} else {
			throw new NullPointerException("空指针异常");
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param t
	 * @return void
	 * @add by 2016-04-12
	 */
	@Override
	public <T> void update(T t) throws Exception {

	}

	/**
	 * 创建对象
	 * 
	 * @param result
	 * @param pushObj
	 * @return MsgPushLogWithBLOBsDo
	 * @add by 2016-05-10
	 */
	@Override
	public MsgPushLogWithBLOBsDo createCarouseObj(Map<String, Object> result, PushMsgVo pushObj, String customerOrder) {
		MsgPushLogWithBLOBsDo obj = new MsgPushLogWithBLOBsDo();
		String resultCode = StringUtils.EMPTY;
		String msgId = StringUtils.EMPTY;
		String status = StringUtils.EMPTY;
		if ("00000".equals(result.get(Constants.RESULT_CODE))) {
			resultCode = result.get("result") != null ? result.get("result").toString() : StringUtils.EMPTY;
			if (result.containsKey("contentId")) {
				msgId = result.get("contentId") != null ? result.get("contentId").toString() : StringUtils.EMPTY;
			}
			if (result.containsKey("taskId")) {
				msgId = result.get("taskId") != null ? result.get("taskId").toString() : StringUtils.EMPTY;
			}
			status = "Y";
		} else {
			if (result.containsKey("result")) {
				resultCode = result.get("result") != null ? result.get("result").toString() : StringUtils.EMPTY;
			}
			status = "N";
		}
		obj.setId(IdGenerator.getDefault().nextId());
		obj.setMsgTitle(pushObj.getTitle());
		obj.setMsgContent(pushObj.getContent());
		obj.setDeviceId(StringUtils.isNotEmpty(pushObj.getDeviceId()) ? pushObj.getDeviceId() : StringUtils.EMPTY);
		obj.setCustomerOrder(customerOrder);
		obj.setMsgId(msgId);
		obj.setUserId(pushObj.getUserId());
		obj.setTradeTime(new Date());
		obj.setPushChannel(pushObj.getChannel());
		obj.setPushType(pushObj.getPushType());
		obj.setMsgType(pushObj.getMsgType());
		obj.setPushCount(0);
		if ("1".equals(pushObj.getPushType())) {
			obj.setDeviceType(pushObj.getDeviceType());
		}
		obj.setStatus(status);
		obj.setResultCode(resultCode);
		return obj;
	}

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
	@Override
	public List<MsgPushLogVo> getMsgPushList(int userId, String deviceType, int start, int pageSize) {
		List<MsgPushLogVo> list = pushDao.getMsgPushList(userId, deviceType, start, pageSize);
		return list;
	}
}
