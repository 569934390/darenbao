package com.club.web.message.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.web.message.dao.base.po.Message;
import com.club.web.message.dao.extend.MessageExtendMapper;
import com.club.web.message.domain.MessageDo;
import com.club.web.message.domain.repository.MessageRepository;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

	
	@Autowired
	private MessageExtendMapper dao;
	
	@Override
	public void newsAdd(MessageDo content) {
		dao.insert(getPoByDo(content));
	}

	private Message getPoByDo(MessageDo src) {
		Message target = null;
		if(src != null){
			target = new Message();
			target.setId(src.getId());
			target.setClientId(src.getClientId());
			target.setStatus(src.getStatus());
			target.setStoreId(src.getStoreId());
			target.setType(src.getType());
		}
		return target;
	}

}
