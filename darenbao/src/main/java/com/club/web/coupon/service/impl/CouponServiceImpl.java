package com.club.web.coupon.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.club.core.common.Page;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.StringUtils;
import com.club.web.coupon.domain.CouponDo;
import com.club.web.coupon.domain.repository.CouponRepository;
import com.club.web.coupon.service.CouponService;
import com.club.web.coupon.vo.CouponVo;
import com.club.web.coupon.vo.ExportCouponVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.ExportExcel;
import com.club.web.util.QRCodeUtil;
import com.club.web.weixin.config.WeixinGlobal;

@Service("couponService")
public class CouponServiceImpl implements CouponService {
	private Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
	@Autowired
	CouponRepository couponRepository;

	/**
	 * 查出所有兌換券信息
	 */
	@Override
	public Page<Map<String, Object>> selectCoupon(Page<Map<String, Object>> page, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", page.getStart());
		map.put("limit", page.getLimit());
		map.put("conditions", page.getConditons().get("conditions").toString());
		try {
			List<CouponVo> list = couponRepository.queryCouponPage(map);
			for(CouponVo couponVo : list){
				couponVo.setQrcode(getCouponQrcode(couponVo.getShopId(),couponVo.getGoodId()));
			}
			result.setResultList(CommonUtil.listObjTransToListMap(list));
		} catch (Exception e) {
			logger.error("查询所有兌換券信息异常:", e);
		}
		try {
			Long count = couponRepository.queryCouponCountPage(map);
			result.setTotalRecords(count.intValue());
		} catch (Exception e) {
			logger.error("查询兌換券总数 异常:", e);
		}

		return result;
	}

	@Override
	public void addCoupon(CouponVo couponVo) throws ParseException {
		// TODO Auto-generated method stub
		CouponDo couponDo = new CouponDo();
		BeanUtils.copyProperties(couponVo, couponDo);
		couponDo.setId(Long.parseLong(couponVo.getId()));
		couponDo.setUpdatetime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		couponDo.setBeginTime(sdf.parse(couponVo.getBeginTimeStr()));
		couponDo.setEndTime(sdf.parse(couponVo.getEndTimeStr()));
		if (couponVo.getGoodId() != null && !couponVo.getGoodId().equals("")) {
			couponDo.setGoodId(Long.parseLong(couponVo.getGoodId()));
		}
		if (couponVo.getShopId() != null && !couponVo.getShopId().equals("")) {
			couponDo.setShopId(Long.parseLong(couponVo.getShopId()));
		}
		couponDo.insert();
	}

	/**
	 * 删除兑换券(支持批量删除)
	 */
	@Override
	public void deleteCoupon(String idStr) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = idStr.split(",");
		for (String id : ids) {
			couponRepository.deleteById(Long.parseLong(id));
		}
	}

	/**
	 * 编辑兑换券
	 * 
	 * @throws ParseException
	 */
	@Override
	public void updateCoupon(CouponVo couponVo) throws ParseException {
		// TODO Auto-generated method stub
		CouponDo couponDo = new CouponDo();
		BeanUtils.copyProperties(couponVo, couponDo);
		couponDo.setId(Long.parseLong(couponVo.getId()));
		couponDo.setUpdatetime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		couponDo.setBeginTime(sdf.parse(couponVo.getBeginTimeStr()));
		couponDo.setEndTime(sdf.parse(couponVo.getEndTimeStr()));
		if (couponVo.getGoodId() != null && !couponVo.getGoodId().equals("")) {
			couponDo.setGoodId(Long.parseLong(couponVo.getGoodId()));
		}
		if (couponVo.getShopId() != null && !couponVo.getShopId().equals("")) {
			couponDo.setShopId(Long.parseLong(couponVo.getShopId()));
		}
		couponRepository.update(couponDo);
	}

	/**
	 * 批量查询兑换券
	 */
	@Override
	public List<ExportCouponVo> selectCouponByIds(String idStr) {
		// TODO Auto-generated method stub
		List<ExportCouponVo> list = new ArrayList<ExportCouponVo>();
		String[] ids = idStr.split(",");
		for (String id : ids) {
			ExportCouponVo exportCouponVo = couponRepository.selectById(Long.parseLong(id));
			// 生成二维码
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				QRCodeUtil.encode(id, os);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				os.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			exportCouponVo.setErweima(os.toByteArray());
			list.add(exportCouponVo);
		}
		return list;
	}

	/**
	 * 批量导出excel
	 * 
	 * @throws IOException
	 */
	@Override
	public String exportByIds(String idStr, HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		List<ExportCouponVo> list = selectCouponByIds(idStr);
		ExportExcel<ExportCouponVo> ex = new ExportExcel<ExportCouponVo>();
		String[] headers = { "兑换券名称", "商品名称", "开始期限", "截止期限", "兑换券个数", "二维码" };
		// 获取服务器tomcat服务器项目部署的路径的绝对地址
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		String serverRealPath = application.getRealPath("/");
		isExist(serverRealPath + "sumgoteakaquan");// E:\yaoming\apache-tomcat-8.0.29-windows-x64\apache-tomcat-8.0.29
													// - 副本\wtpwebapps
		String fileName = "sumgoteakaquan/erweima-" + System.currentTimeMillis() + ".xls";
		OutputStream outputStream = new FileOutputStream(serverRealPath + fileName);
		ex.exportExcel(headers, list, outputStream, "yyyy-MM-dd");
		outputStream.close();
		return "/" + fileName;
	}

	public static void isExist(String path) {
		File file = new File(path);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * 查询卡劵列表
	 * 
	 * @param shopId
	 * @param startIndex
	 * @param pageSize
	 * @return List<CouponVo>
	 * @add by 2016-05-13
	 */
	@Override
	public List<CouponVo> getVoucherListSer(String shopId, int startIndex, int pageSize) {
		List<CouponVo> list = null;
		long shop = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotEmpty(shopId)) {
			shop = Long.valueOf(shopId);
			list = couponRepository.getVoucherList(shop, startIndex, pageSize);
			if (list != null && list.stream().count() > 0) {
				list.stream().forEach(args -> {
					if (args.getBeginTime() != null) {
						args.setBeginDate(format.format(args.getBeginTime()));
					}
					if (args.getEndTime() != null) {
						args.setEndDate(format.format(args.getEndTime()));
					}
				});
			}
		}
		return list;
	}

	@ResponseBody
	public String getCouponQrcode(String shopId, String goodId) {
		String qrcode="";
		try {
			if(goodId != null && !"".equals(goodId)){
				return WeixinGlobal.getQrcodeCouponTemplate().replace("{storeId}", "").replace("{goodId}", goodId);
			}
		} catch (Exception e) {
			
		}
		return qrcode;
	}
}
