package com.club.web.message.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.message.dao.base.po.MessageContent;
import com.club.web.message.dao.extend.MessageContentExtendMapper;
import com.club.web.message.domain.MessageContentDo;
import com.club.web.message.domain.repository.MessageContentRepository;

@Repository
public class MessageContentRepositoryImpl implements MessageContentRepository {

	
	@Autowired
	private MessageContentExtendMapper dao;
	
	@Override
	public void newsAdd(MessageContentDo content) {
		dao.insert(getPoByDo(content));
	}

	private MessageContent getPoByDo(MessageContentDo src) {
		MessageContent target = null;
		if(src != null){
			target = new MessageContent();
			target.setId(src.getId());
			target.setMessageId(src.getMessageId());
			target.setContent(src.getContent());
			target.setSenderId(src.getSenderId());
			target.setSendTime(src.getSendTime());
			target.setStatus(src.getStatus());
			target.setType(src.getType());
		}
		return target;
	}

}
