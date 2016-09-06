package com.club.web.message.timer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.club.web.common.Constants;
import com.club.web.message.dao.extend.MessageContentExtendMapper;
import com.club.web.util.DateParseUtil;

@Component
@Transactional
public class MessageTimer {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageContentExtendMapper messageContentDao;
	
    //每天0点清除回复数据
	@Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(fixedRate=120*1000)
    public void clearReply(){
		logger.info("start clear message_content....");
		try{
			Map<String, Object> con = new HashMap<>();
			Date time = DateParseUtil.getBeforeMonthDate(new Date(), 3);
			con.put("beforeTime", DateParseUtil.formatDate(time, Constants.YYYYMMDDHHMMSS));
			messageContentDao.deleteByBeforeTime(con);
		}catch(Exception e){
			logger.error("clear message_content error:",e);
		}
		
    }
}
