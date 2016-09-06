package com.club.web.deal.timer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.club.framework.util.StringUtils;
import com.club.web.common.Constants;
import com.club.web.deal.constant.IndentStatus;
import com.club.web.deal.service.IndentService;
import com.club.web.store.service.TimeCycleService;
import com.club.web.store.vo.TimeCycleVo;
import com.club.web.util.DateParseUtil;

@Component
@Transactional
public class IndentTimer {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IndentService indentService;
	
	@Autowired
	private TimeCycleService timeCycleService;
	
	
    //每天0点清除回复数据
	@Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(fixedRate=120*1000)
    public void evaluateAll(){
		logger.info("indentTimer start evaluateAll....");
		try{
			TimeCycleVo indentShipTime = timeCycleService.getIndentShipTime();
			Map<String, Object> con = new HashMap<>();
			Date time = DateParseUtil.getBeforeDate(new Date(), indentShipTime == null || StringUtils.isEmpty(indentShipTime.getDuration())?10:Integer.valueOf(indentShipTime.getDuration()));
			con.put("beforeShipTime", DateParseUtil.formatDate(time, Constants.YYYYMMDDHHMMSS));
			con.put("indentStatus", IndentStatus.待收货.getDbData());
			indentService.updateList(con);
		}catch(Exception e){
			logger.error("indentTimer evaluateAll error:",e);
		}
		
    }
}
