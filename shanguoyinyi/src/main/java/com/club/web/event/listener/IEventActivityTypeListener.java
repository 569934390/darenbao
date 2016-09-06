package com.club.web.event.listener;

import com.club.web.util.listener.IBaseListener;

public interface IEventActivityTypeListener extends IBaseListener {
	/**
	 * 删除分类
	 * @return true:成功,false:失败
	 */
	public boolean delete(String[] ids);
}
