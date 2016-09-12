package com.compses.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <p>Title: BSNWeb</p>
 *
 * <p>Description: 每个业务模块实现IWorkJob方法,这是一个简单倒置字串方法</p>
 *
 *
 *
 * @version 1.0
 */
public class AppSimple implements IWorkJob {

    /** logger */
    public static final Logger logger = LoggerFactory.getLogger(AppSimple.class);

    /**
     * 构造函数

     */
    public AppSimple() {

    }

    /** strList */
    public String strList;

    /**
     *
     * @param strList String
     */
    AppSimple(String strList) {
        this.strList = strList;
    }


    /**
     * 业务类

     * @return String
     */
    public String convertStr() {
        return this.strList;
    }

    /**
     * 实现toDoJob
     */
    public void toDoJob(int threadNo) {
        //业务擦作
        logger.info("Thread " + threadNo + " deal: " + this.convertStr()+"测试卷哦");
        System.out.println("Thread " + threadNo + " deal: " + this.convertStr()+"测试卷哦");
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //模拟高低水 --- 动态 新增  减少 线程
    public static void main(String[] args) throws Exception {
        //启动1个线程的队列，在线式
        WorkQueue workQueue = new WorkQueue(100, 0, "test");
        for (int i = 0; i < 10000; i++) {
        	System.out.println("test");
            AppSimple petriTimerJob = new AppSimple();
            //放入队列
            workQueue.addWorkJobToQueue(petriTimerJob);
        }
        
    }


}
