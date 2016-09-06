/**
 * 
 */
package com.club.core.convert;

import com.club.framework.dto.AbstractDto;
import com.club.framework.exception.BaseAppException;

/**
 * <Description>查询条件转换成Arg类的服务接口 <br>
 * 
 * @author pan.xiaobo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月17日 <br>
 * @since V1.0<br>
 * @see com.club.core.convert <br>
 */

public interface IArgConversionService {

    /**
     * 根据传入的Arg类型，及参数VO，构造出查询参数封装Arg
     * 
     * @param argClass Arg类
     * @param vo 前台到后台查询参数封装的VO，其中queryConditions字段形如 [{\"paramName\":\"userName\",\"operation\":\"Like\",\"paramValue\":[\"1\"]}...]
     * @return
     * @throws com.club.framework.exception.BaseAppException
     */
    <T> T invokeArg(Class<T> argClass, AbstractDto vo) throws BaseAppException;
}
