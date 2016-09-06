package com.club.web.store.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.web.store.vo.SubbranchGoodSoldoutVo;
import com.club.web.store.vo.SubbranchVo;

@Service
public interface SubbranchService {

	Map<String, Object> saveOrUpdateSubbranch(SubbranchVo subbranchVo, HttpServletRequest request)
			throws NoSuchAlgorithmException;

	Page getSubbranchList(Page page);

	Page getSubbranceName(Page page);

	int updateSubbranchState(String[] subbranchId, String action);

	int deleteSubbranch(String subbranchIds);

	Map<String, Object> saveSubbranchGoodsSoldout(SubbranchGoodSoldoutVo subbranchGoodSoldoutVo);

	Page getSubbranchGoodsSoldoutList(Page page);

	int deleteSubbranchGoodsSoldout(Long subbranchId, String goodIds);

	SubbranchVo selectByPrimaryKey(Long id);

	/**
	 * 查询店铺协议
	 * 
	 * @param shopId
	 * @return String
	 * @add by 2016-05-16
	 */
	String getShopProtocolSer(long shopId);

	/**
	 * 修改我的资料
	 * 
	 * @param paramStr
	 * @return Map<String, Object>
	 * @add by 2016-05-16
	 */
	Map<String, Object> updateUserMsgSer(Map<String, Object> paramStr);

	/**
	 * 查询商品是否上下架
	 * 
	 * @param goodId
	 * @return int
	 * @add by 2016-05-18
	 */
	int getShopGoodStatus(long goodId, long shopId);

	/**
	 * 店铺商品上架
	 * 
	 * @param shopId
	 * @param goodIds
	 * @return Map<String, String>
	 * @add by 2016-05-18
	 */
	Map<String, Object> setGoodShelveSer(String shopId, String goodIds);

	/**
	 * 店铺商品下架
	 * 
	 * @param shopId
	 * @param goodIds
	 * @return Map<String, String>
	 * @add by 2016-05-18
	 */
	Map<String, Object> setGoodOutShelveSer(String shopId, String goodIds);

	SubbranchVo selectByNumber(String number);

	List<SubbranchVo> selectByMobile(String mobile);

	Map<String, Object> login(String loginName, String password);

	Map<String, Object> verifyPhone(String phoneCode);

	Map<String, Object> resetPassword(String verifyCode, String mobile, String password, String repassword,
			HttpServletRequest request) throws NoSuchAlgorithmException;

	List<SubbranchVo> getSubbranchListForActivity();

}
