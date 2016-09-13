package com.compses.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.compses.util.JsonUtils;

public class MessageHeartThread extends Thread {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private MessageHeart messageHeart;
	public MessageHeartThread(MessageHeart messageHeart) {
		super();
		this.messageHeart = messageHeart;
	}
	public void run() {
		while (true) {
			Map<String,Date> timeQueue=this.messageHeart.getTimeQueue();
			logger.info("scan queue:{}",JsonUtils.toJson(timeQueue));
			try {
				MessageHeartThread.sleep(120 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!timeQueue.isEmpty()) {
				List<String> uuidList=new ArrayList<String>();
				for (String uuid : timeQueue.keySet()) {
					if (System.currentTimeMillis()-timeQueue.get(uuid).getTime()>10*1000) {
						uuidList.add(uuid);
					}
				}
				for (String uuid : uuidList) {
					this.messageHeart.unbind(uuid);
				}
			
			}else{
				synchronized (timeQueue) {
					try {
						logger.info("timeQueue wait");
						timeQueue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
