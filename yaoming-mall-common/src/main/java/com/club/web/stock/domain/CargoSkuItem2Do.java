package com.club.web.stock.domain;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * TODO 
 * 这个文件用以规避BUG，
 * 父类的文件Configurable注解不生效，原因不知
 * 暂时使用子类进行实例化
 *
 * BUG表现：
 * 1. 相同包下的Java文件，有的Configurable注解生效，有的失效
 * 2. 将失效文件在包下复制一份，仅类名不同，新文件生效，旧文件无效
 * 3. 将失效文件改名称，复制一份文件与原文件相同，新文件生效，旧文件无效
 *
 * 为了找出原因，已经耗费至少6个工时，毫无进展
 * 因此暂时保留，待项目一期结束后再看
 * 
 * @author yunpeng.xiong
 * @date 2016-04-08
 *
 */
@Configurable
public class CargoSkuItem2Do extends CargoSkuItemDo {
	
}