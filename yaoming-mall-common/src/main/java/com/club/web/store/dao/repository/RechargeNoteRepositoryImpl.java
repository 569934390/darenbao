package com.club.web.store.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.club.framework.util.BeanUtils;
import com.club.web.store.dao.base.RechargeNoteMapper;
import com.club.web.store.dao.base.po.RechargeNote;
import com.club.web.store.domain.RechargeNoteDo;
import com.club.web.store.domain.repository.RechargeNoteRepository;

@Repository
public class RechargeNoteRepositoryImpl implements RechargeNoteRepository {
    
	@Autowired
	private RechargeNoteMapper rechargeNoteMapper;
	
	@Override
	public boolean queryRechargeNote(Long indentId){
		
		RechargeNote rechargeNote = rechargeNoteMapper.selectByPrimaryKey(indentId);
		if(rechargeNote != null){
			return false;
		} else{
			return true;
		}
	}
	
	@Override
	public int addRechargeNote(RechargeNoteDo rechargeNoteDo){
		RechargeNote rechargeNote = new RechargeNote();
		BeanUtils.copyProperties(rechargeNoteDo, rechargeNote);
		
		return rechargeNoteMapper.insert(rechargeNote);
	}
	
	@Override
	public int updateRechargeNote(RechargeNoteDo rechargeNoteDo){
		RechargeNote rechargeNote = new RechargeNote();
		BeanUtils.copyProperties(rechargeNoteDo, rechargeNote);
		
		return rechargeNoteMapper.updateByPrimaryKeySelective(rechargeNote);
	}
}