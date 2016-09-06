package com.club.web.store.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.web.store.dao.base.MsgPushLogMapper;
import com.club.web.store.vo.MsgPushLogVo;

public interface MsgPushLogExtendMapper extends MsgPushLogMapper {
	List<MsgPushLogVo> getMsgPushList(@Param("userId") int userId, @Param("deviceType") String deviceType,
			@Param("start") int start, @Param("pageSize") int pageSize);
}