package com.compses.threadpool;

/**
 * <p>Title: BSNWeb</p>
 *
 * <p>Description: 业务工作任务接口</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * @version 1.0
 */
public interface IWorkJob {

    /**
     * 工作任务接口，由各业务类实现业务方法
     */
    public void toDoJob(int threadNo);
}
