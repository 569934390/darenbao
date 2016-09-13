package com.compses.message;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 飞爷 
 */
public class MessageHeart {

	private Logger logger=LoggerFactory.getLogger(this.getClass());

	private static MessageHeart instance=null;
	
	private Map<String,List<MessageCell>> queue;
	private Map<String,Date> timeQueue;
	private MessageHeartThread messageHeartThread;
	
	
	public Map<String, Date> getTimeQueue() {
		return timeQueue;
	}

	public void setTimeQueue(Map<String, Date> timeQueue) {
		this.timeQueue = timeQueue;
	}

	private MessageHeart() {
		queue = new HashMap<String, List<MessageCell>>();
		timeQueue=new HashMap<String,Date>();
	}
	
	public static MessageHeart getInstance() {
		if (instance == null){
		   synchronized (MessageHeart.class) {// 1 
			   if(instance == null){
					instance = new MessageHeart();
					instance.messageHeartThread=new MessageHeartThread(instance);
					instance.messageHeartThread.start();
			   }
		   }
		}
		return instance;
	}
	
	public void send(String channel,String message ){
		MessageCell cell = new MessageCell();
		cell.setChannel(channel);
		cell.setMessage(message);
        for(String uuid:queue.keySet()){
        	synchronized (queue.get(uuid)) {
	        	queue.get(uuid).add(cell);
        	}
        }
	}
	
	public void unbind(String uuid){
		synchronized (queue.get(uuid)) {
			queue.get(uuid).clear();
	        queue.remove(uuid);
	        timeQueue.remove(uuid);
		}
	}
	
	public void bind(String uuid){
		if(!queue.containsKey(uuid)){
			synchronized (queue) {
				if(!queue.containsKey(uuid)){
					List<MessageCell> cells = new ArrayList<MessageCell>();
					queue.put(uuid, cells);
				}
			}
		}
	}

	public List<MessageCell> loadData(String uuid){
		bind(uuid);
		if (this.timeQueue.isEmpty()) {
			synchronized (this.timeQueue) {
				this.timeQueue.notifyAll();
				logger.info("timeQueue wake up");
			}
		}
		synchronized (queue.get(uuid)){
			List<MessageCell> cells = new ArrayList<MessageCell>(queue.get(uuid).size());
			for(MessageCell cell: queue.get(uuid)){
				cells.add(cell);
			}
			queue.get(uuid).clear();
			timeQueue.put(uuid, new Date());
			return cells;
		}
	}
}
