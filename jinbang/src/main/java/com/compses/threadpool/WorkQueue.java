package com.compses.threadpool;

import java.util.ArrayList;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <p>Title: BSNWeb</p>
 *
 * <p>Description: 具有线程池的工作队列</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * @version 1.0
 */

public class WorkQueue {

    /** 并发处理线程数 */
    private int nThreads;//cur_process_num
    
    /** 并发处理线程池 */
    private PoolWorker[] threads;

    /** 并发处理线程池 */
    private ArrayList threadsList;//PoolWorker 线程列表

    /** 处理线程工作队列 */
    public final LinkedList queue;//cur_queue_size

    /** 处理线程工作类型——任务 */
    public final static int RUN_TYPE_TASK = 1;

    /**
     *  0 在线式 : 线程一直在线，反复扫描队列，缺省模式；
     *  适用于银行、VC接口等

     *  1 任务式 : 触发任务做完即销毁线程、队列，任务完成后即离线，

     *  适用于前台触发的批量处理，如余额批冲，批销账等
     */
    private int runType = 0;

    /** 队列增加完毕，仅在任务有用 */
    private boolean queueAddOver = false;
    
    /** 允许用户终止线程，仅在任务有用 */
    private boolean stop = false;
    
    /** 模块名  参数名前面的模块名 */
    private String module = "";
    
    //process_high_level  高水值

    private int processHighLevel = 0;
    //process_low_level 低水值

    private int processLowLevel = 0;
    //max_process 最大线程数
    private int maxProcess = 0;
    //min_process 最小线程数
    private int minProcess = 0;
    //maxQueueSize 最大队列长度

    private int maxQueueSize = 300000;
    //记录当前队列是否超负荷

    private boolean isScheduleBusyTime = false;
    
    /** 日志 */
    private static final Logger logger = LoggerFactory.getLogger(WorkQueue.class);
    
    /** 入队列数 - 出队列数 = 队列长度*/
    private int addJobNum = 0;
    private int removeJobNum = 0;

    public int getAddJobNum() {
		return addJobNum;
	}

	private void AddJobNum() {
		this.addJobNum = this.addJobNum + 1;
	}

	public int getRemoveJobNum() {
		return removeJobNum;
	}

	public void RemoveJobNum() {
		this.removeJobNum = this.removeJobNum + 1;
	}
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void validLength() {
		//logger.info("Valid Length Result: " + ((this.addJobNum - this.removeJobNum) == this.queue.size()));
	}

	/**
     * 获取具有线程池的工作队列(缺省是在线式)
     * @param nThreads int 最大并发处理线程

	 * @throws BaseAppException 
     */
    public WorkQueue(int nThreads, String module) throws Exception {
        this(nThreads, 0, module);
    }

    /**
     * 获取具有线程池的工作队列
     * @param nThreads int 最大并发处理线程

     * @param runType int
     */
    public WorkQueue(int nThreads, int runType, String module) throws Exception {
        this.nThreads = nThreads;
        this.runType = runType;
        this.module = module;
        threadsList = new ArrayList(3);
        queue = new LinkedList();
        PoolWorker[] threads = new PoolWorker[nThreads];
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker(this, i, false);
            threadsList.add(threads[i]);
            try {
            	threads[i].start();
			} catch (Exception e) {
				logger.info("Thread " + i + " start failed!");
			}            
            logger.info("Module[" + module + "] Thread " + i + " is working!");
        }
    }

    /**
     * 获取具有线程池的工作队列
     * @param nThreads int 最大并发处理线程


     * @param runType int
     */
    public WorkQueue(int nThreads, int runType) {
        this.nThreads = nThreads;
        this.runType = runType;
        queue = new LinkedList();
        threads = new PoolWorker[nThreads];
        for (int i = 0; i < nThreads; i++) {
            threads[i] = new PoolWorker(this);
            threads[i].start();
            logger.debug("nThreads " + i + " is working");
        }

    }
    
    /**
     * 新增处理线程
     * @param nThreads int 最大并发处理线程

     * @param runType int
     */
    public void addPoolWork(int runType) {
        this.nThreads = this.nThreads + 1;
        this.runType = runType;
        PoolWorker poolWorker = new PoolWorker(this, this.nThreads - 1, false);
        poolWorker.start();
        threadsList.add(poolWorker);
        //logger.info("Thread " + (this.nThreads - 1) + " is working [Add]");
    }
    
    /**
     * 获取具有线程池的工作队列
     * @param nThreads int 最大并发处理线程

     * @param runType int
     */
    public void removePoolWork(int runType) {
    	((PoolWorker)this.threadsList.get(this.nThreads - 1)).setStop(true);//停止该线程

        this.nThreads = this.nThreads - 1;
        this.runType = runType;
        this.threadsList.remove(this.nThreads - 1);
        logger.info("Thread " + this.nThreads + " is destroyed [Del]");
    }


    /**
     * 加入工作队列
     * @param iWorkJob IWorkJob
     */
    public void addWorkJobToQueue(IWorkJob iWorkJob) {
        synchronized (queue) {
        	System.out.println(queue.size()+","+getMaxQueueSize());
        	if (queue.size() > getMaxQueueSize()){
        		String warnStr = "[" + module + "].queue_size current queue size is more than MaxQueueSize:" + getMaxQueueSize() + "不再加入队列！";
        		logger.warn(warnStr);
                return;
        	}
        	AddJobNum();
            queue.addLast(iWorkJob);
            queue.notify();
        }
    }
    

    /**
     * 获取工作队列长度
     * @return int 长度
     */
    public int getQueueSize() {
        synchronized (queue) {
            return queue.size();
        }
    }

    /**
     * 队列是否更新完毕
     * @return boolean
     */
    public boolean isQueueAddOver() {
        return this.queueAddOver;
    }

    /**
     * 队列更新完毕
     */
    public void setQueueAddOver() {
        synchronized (queue) {
            logger.info(" Queue Adding is Over ");
            this.queueAddOver = true; //
            this.queue.notifyAll(); //通知所有等待线程，不能是notify, 因为队列不再更新了

        }
    }

    /**
     * 得到线程类型
     * @return int
     */
    public int getRunType() {
        return this.runType;
    }

    /**
     * 得到定义的工作线程数
     * @return int
     */
    public int getThreadSize() {
        return this.nThreads;
    }

    /**
     * 队列是否处理完毕(只有任务式的情况，才能使用该函数)
     * @return boolean
     */
    public boolean isQueueDealOver() {
        if (this.queue.isEmpty()){
        	int length = threadsList.size();
            for (int i = 0; i < length; i++) {
                if (((PoolWorker)this.threadsList.get(i)).isAlive()){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 等待队列处理完毕(只有任务式的情况，该函数才起作用，并且必须用于setQueueAddOver()方法之后)
     * @return 
     */
    public void waitingThreadsOver() {
    	if(runType == WorkQueue.RUN_TYPE_TASK){
    		int length = threadsList.size();
    		for (int i = 0; i < length; i++) {
    			try {
    				((PoolWorker)this.threadsList.get(i)).join();
				} catch (InterruptedException e) {
					logger.error("nThreads " + i + " is Interrupted Exception");
				}
	    	}
	    }
    }

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public int getProcessHighLevel() {
		return processHighLevel;
	}

	public void setProcessHighLevel(int processHighLevel) {
		this.processHighLevel = processHighLevel;
	}

	public int getProcessLowLevel() {
		return processLowLevel;
	}

	public void setProcessLowLevel(int processLowLevel) {
		this.processLowLevel = processLowLevel;
	}

	public int getMaxProcess() {
		return maxProcess;
	}

	public void setMaxProcess(int maxProcess) {
		this.maxProcess = maxProcess;
	}

	public int getMinProcess() {
		return minProcess;
	}

	public void setMinProcess(int minProcess) {
		this.minProcess = minProcess;
	}

	public int getMaxQueueSize() {
		return maxQueueSize;
	}

	public void setMaxQueueSize(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
	}

	public boolean isScheduleBusyTime() {
		return isScheduleBusyTime;
	}

	public void setScheduleBusyTime(boolean isScheduleBusyTime) {
		this.isScheduleBusyTime = isScheduleBusyTime;
	}
}
