package com.compses.threadpool;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: BSNWeb</p>
 *
 * <p>Description: Prodigy</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * @version 1.0
 */
public class PoolWorker extends Thread {
	
	private int threadNo = 0;

    /** 共享的等待处理工作类 */
    private WorkQueue workQueue;

    /** 共享的等待处理队列 */
    private LinkedList queue;
    
    /** 控制启停 */
    private boolean isStop;

    public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	/** 日志 */
    public static final Logger logger = LoggerFactory.getLogger(PoolWorker.class);
    

    /**
     * 构造函数


     */
    PoolWorker(WorkQueue workQueue) {
        this.workQueue = workQueue;
        this.queue = this.workQueue.queue;
    }

    /**
     * 构造函数

     */
    PoolWorker(WorkQueue workQueue, int i, boolean isStop) {
        this.workQueue = workQueue;
        this.queue = this.workQueue.queue;
        this.threadNo = i;
        this.isStop = isStop;
    }

    /**
     * 线程方法，线程获取队列里面的业务todo
     */
    public void run() {
        IWorkJob iWorkJob;
        while (true) {
        	if (this.isStop){
        		return;
        	}
            synchronized (this.queue) {
                while (this.queue.isEmpty()) {
                    try {
                        if (this.workQueue.getRunType() == WorkQueue.RUN_TYPE_TASK
                            && this.workQueue.isQueueAddOver()) { //任务式,才判断,队列增加已经完毕
                            logger.info("---PoolWorker Work Over---");
                            return; //返回
                        }
                        //logger.debug("thread No:" + this.threadNo + "---PoolWorker Work Wait---");
                        this.queue.wait();
                    } catch (Exception ex) {
                        logger.error("---Queue Exception---", ex);
                    }
                }
                //logger.debug("---Queue Size : "+ queue.size());
                iWorkJob = (IWorkJob)this.queue.removeFirst();
                this.workQueue.RemoveJobNum();
            }
            //用户强制终止
            if(this.workQueue.isStop() && this.workQueue.getRunType() == WorkQueue.RUN_TYPE_TASK){
            	return;
            }
            //执行接口方法
            try {
                iWorkJob.toDoJob(this.threadNo);
            } catch (Exception ex) {
                logger.error("---PoolWorker Exception---", ex);
            }
        }
    }
}
