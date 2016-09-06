package com.club.web.message.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.framework.util.StringUtils;
import com.club.web.autoRepeat.service.AutoRepeatService;
import com.club.web.message.constant.MessageContentStatus;
import com.club.web.message.constant.MessageContentType;
import com.club.web.message.dao.extend.MessageContentExtendMapper;
import com.club.web.message.domain.MessageContentDo;
import com.club.web.message.service.MessageContentService;
import com.club.web.message.vo.MessageContentPage;
import com.club.web.util.IdGenerator;
/**
 * 信息服务接口实现类
 * @author zhuzd
 *
 */
@Service
public class MessageContentServiceImpl implements MessageContentService{

	@Autowired private MessageContentExtendMapper messageContentDao;
	@Autowired private AutoRepeatService autoRepeatService;
	
	@Override
	public List<MessageContentPage> queryListByMap(Map<String,Object> con) {
		return messageContentDao.queryListByMap(con);
	}
	
	public void updateContentStatus(Map<String,Object> con){
		messageContentDao.updateContentStatus(con);
	}

	@Override
	public void noticeAdd(Long messageId,Long senderId,String content) {
		MessageContentDo contentDo = new MessageContentDo();
		contentDo.setId(IdGenerator.getDefault().nextId());
		contentDo.setMessageId(messageId);
		contentDo.setSendTime(new Date());
		contentDo.setContent(content);
		contentDo.setSenderId(senderId);
		contentDo.setType(MessageContentType.文本.getDbData());;
		contentDo.setStatus(MessageContentStatus.否.getDbData());
		contentDo.newsAdd();
	}
	
	@Override
	public MessageContentPage newsAdd(MessageContentPage content) {
		MessageContentPage replyContent = null;
		content.setId(IdGenerator.getDefault().nextId()+"");
		content.setSendTime(new Date());
		content.setStatus(MessageContentStatus.否.getDbData());
		if(content.getSendType() != null && content.getSendType() == 0){
			//调用自动回复接口
			String returnText = autoRepeatService.selectRepeat(content.getContent(), null);
			if(StringUtils.isNotEmpty(returnText)){
				content.setStatus(MessageContentStatus.是.getDbData());
				replyContent = new MessageContentPage();
				replyContent.setId(IdGenerator.getDefault().nextId()+"");
				replyContent.setSendTime(new Date());
				replyContent.setSenderId("-1");
				replyContent.setContent(returnText);
				replyContent.setMessageId(content.getMessageId());
				replyContent.setStatus(MessageContentStatus.是.getDbData());
				replyContent.setType(MessageContentType.文本.getDbData());
			}
		}
		getDoByVo(content).newsAdd();
		if(replyContent != null){
			getDoByVo(replyContent).newsAdd();
		}
		return replyContent;
	}

	private MessageContentDo getDoByVo(MessageContentPage src) {
		MessageContentDo target = null;
		if(src != null){
			target = new MessageContentDo();
			target.setId(StringUtils.isNotEmpty(src.getId())?Long.valueOf(src.getId()):null);
			target.setMessageId(StringUtils.isNotEmpty(src.getMessageId())?Long.valueOf(src.getMessageId()):null);
			target.setContent(src.getContent());
			target.setSenderId(StringUtils.isNotEmpty(src.getSenderId())?Long.valueOf(src.getSenderId()):null);
			target.setSendTime(src.getSendTime());
			target.setStatus(src.getStatus());
			target.setType(src.getType());
		}
		return target;
	}
}
