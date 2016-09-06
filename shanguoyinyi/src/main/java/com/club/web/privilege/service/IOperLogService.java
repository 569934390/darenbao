package com.club.web.privilege.service;

import com.club.framework.exception.BaseAppException;

/**
 * Created by lyhcn on 16/2/16.
 */
public interface IOperLogService {
    /**
     * 操作日志
     * @param bizIdStr
     * @param state
     * @param creater
     * @param type
     * @param context
     * @throws BaseAppException
     */
    void saveOperate(String bizIdStr, String state, String creater, String type, String context) throws BaseAppException;
}
