package com.club.web.coupon.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.club.core.common.Page;
import com.club.core.spring.context.CustomPropertyConfigurer;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.ZipCompressor;
import com.club.web.coupon.service.CouponDetailService;
import com.club.web.coupon.service.CouponService;
import com.club.web.coupon.vo.CouponDetailVo;
import com.club.web.coupon.vo.CouponVo;
import com.club.web.spread.vo.GoodandCargoSimpleInfoVo;
import com.club.web.util.IdGenerator;
import com.club.web.util.ReadExcel;

@Controller
@RequestMapping("/couponManagerController")
public class CouponManagerController {

	private Logger logger = LoggerFactory.getLogger(CouponManagerController.class);
	@Autowired
	CouponService couponService;
	@Autowired
	CouponDetailService couponDetailService;

	/**
	 * 查询所有的卡券信息
	 * 
	 * @param page
	 *            分页信息
	 * @param conditionStr
	 *            条件名称
	 * @return
	 */
	@RequestMapping("/selectCoupon")
	@ResponseBody
	public Page<Map<String, Object>> selectCoupon(Page<Map<String, Object>> page, String conditionStr,
			HttpServletRequest request) {
		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		page = couponService.selectCoupon(page, request);
		return page;
	}
	
	/**
	 * 新增/编辑兑换券
	 * 
	 * @param modelJson
	 *            兑换券对象值字符串
	 */
	@RequestMapping("/addOrUpdateCoupon")
	@ResponseBody
	public Map<String, Object> addOrUpdateCoupon(String modelJson, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回信息
		CouponVo couponVo = JsonUtil.toBean(modelJson, CouponVo.class);
		if (couponVo != null) {
			// 以ID来判断是新增或者修改操作
			if (couponVo.getId() != null && !couponVo.getId().isEmpty() && !couponVo.getId().equals("")) {
				try {
					couponService.updateCoupon(couponVo);
					result.put("success", true);
					result.put("msg", "编辑成功");
				} catch (Exception e) {
					result.put("success", false);
					result.put("msg", "编辑失败");
					logger.error("编辑兑换券失败-controller", e.getMessage());
				}

			} else {
				try {
					couponVo.setId(IdGenerator.getDefault().nextId() + "");
					couponService.addCoupon(couponVo);
					result.put("success", true);
					result.put("msg", "新增成功");
				} catch (Exception e) {
					result.put("success", false);
					result.put("msg", "新增失败");
					logger.error("新增兑换券失败-controller", e.getMessage());
				}
			}
		} else {
			result.put("success", false);
			result.put("msg", "兑换券为空，编辑失败");
		}

		return result;
	}

	/**
	 * 批量删除兑换券
	 * 
	 * @param idStr
	 *            推广分类的id数组
	 * @return
	 */
	@RequestMapping("/deleteCoupon")
	@ResponseBody
	public Map<String, Object> deleteCoupon(String IdStr) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回信息
		boolean ifExgist=false;
		if (IdStr != null) {
			try {
				String[] ids = IdStr.split(",");
				for (String id : ids) {
					if(couponDetailService.queryBycouponId(id)!=null){
						ifExgist=true;
						break;
					}
				}
				if(!ifExgist){
					couponService.deleteCoupon(IdStr);
					result.put("success", true);
				}else{
					result.put("success", false);
					result.put("msg", "有兑换券编码存在，兑换券无法删除");
				}
				
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "删除兑换券失败");
			}
		} else {
			result.put("success", false);
			result.put("msg", "要删除的兑换券ID不能为空");
		}

		return result;
	}

	/**
	 * 导出兑换券二维码
	 * 
	 * @param idStr
	 *            id数组
	 * @return
	 */
	@RequestMapping("/exportCoupon")
	@ResponseBody
	public Map<String, Object> exportCoupon(String IdStr, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回信息
		if (IdStr != null) {
			try {
				String path=couponService.exportByIds(IdStr,request);
				result.put("success", true);
				result.put("msg", path);
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "导出兑换券失败");
			}
		} else {
			result.put("success", false);
			result.put("msg", "要导出的兑换券ID不能为空");
		}

		return result;
	}

	/**
	 * 查询所有的卡券信息
	 * 
	 * @param page
	 *            分页信息
	 * @param conditionStr
	 *            条件名称
	 * @return
	 */
	@RequestMapping("/selectCouponDetail")
	@ResponseBody
	public Page<Map<String, Object>> selectCouponDetail(Page<Map<String, Object>> page, String conditionStr,
			HttpServletRequest request) {
		// 如果查询条件不为空则添加查询条件
		if (conditionStr != null) {
			page.setConditons(JsonUtil.toMap(conditionStr));
		}
		page = couponDetailService.selectCouponDetail(page, request);
		return page;
	}

	/**
	 * 批量删除兑换券编码
	 * 
	 * @param idStr
	 *            id数组
	 * @return
	 */
	@RequestMapping("/deleteCouponDetail")
	@ResponseBody
	public Map<String, Object> deleteCouponDetail(String IdStr) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回信息
		if (IdStr != null) {
			try {
				couponDetailService.deleteCouponDetail(IdStr);
				result.put("success", true);
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "删除兑换券编码失败");
			}
		} else {
			result.put("success", false);
			result.put("msg", "要删除的兑换券编码ID不能为空");
		}

		return result;
	}

	/**
	 * 禁用兑换券编码
	 * 
	 * @param idStr
	 *            id数组
	 * @return
	 */
	@RequestMapping("/forbidCouponDetail")
	@ResponseBody
	public Map<String, Object> forbidCouponDetail(String IdStr) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回信息
		if (IdStr != null) {
			try {
				couponDetailService.forbidStatus(IdStr);
				result.put("success", true);
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "禁用兑换券编码失败");
			}
		} else {
			result.put("success", false);
			result.put("msg", "要禁用的兑换券编码ID不能为空");
		}

		return result;
	}

	/**
	 * 分配店铺
	 * 
	 * @param idStr
	 *            id数组
	 * @param shopId
	 *            店铺id
	 * @return
	 */
	@RequestMapping("/allocateShop")
	@ResponseBody
	public Map<String, Object> allocateShop(String IdStr, String shopId, String shopCode) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回信息
		if (IdStr != null && !IdStr.equals("") && shopId != null && !shopId.equals("")) {
			try {
				couponDetailService.allocateShop(IdStr, shopId, shopCode);
				result.put("success", true);
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "分配店铺失败");
			}
		} else {
			result.put("success", false);
			result.put("msg", "兑换券编码ID或者店铺ID不能为空");
		}

		return result;
	}

	/**
	 * 导入兑换券
	 * 
	 * @param idStr
	 *            id数组
	 * @return
	 */
	@RequestMapping("/importCoupon")
	@ResponseBody
	public List<Object> importCoupon(DefaultMultipartHttpServletRequest request) {
		List<Object> rs = new ArrayList<Object>();
		try {
			String iconPath = CustomPropertyConfigurer.getContextProperty("excelPath").toString() + "icons/";
			File f = new File(iconPath);
			if (!f.exists()) {
				f.mkdirs();
			}
			long iconNameTime = System.currentTimeMillis();
			for (Iterator<String> iter = request.getFileNames(); iter.hasNext();) {
				String fileName = iter.next();
				String iconName = request.getFile(fileName).getOriginalFilename();
				String iconNames = iconName.substring(iconName.lastIndexOf("."));
				iconName = iconNameTime + iconNames;
				// 为避免中文乱码问题，将文件名统一用时间戳命名
				FileOutputStream os = new FileOutputStream(iconPath + iconName);
				InputStream in = request.getFile(fileName).getInputStream();
				int b = 0;
				while ((b = in.read()) != -1) {
					os.write(b);
				}
				os.flush();
				in.close();
				os.close();
				String hashFileName = URLEncoder.encode(iconName, "UTF-8");
				ReadExcel readExcel = new ReadExcel();
				List<Map<Integer, Object>> list = readExcel.readExcel(iconPath + iconName);
				List<Map<String, Object>> list1=new ArrayList<>(); 
				if(list !=null){
					for(int i=0;i<list.size();i++){
						Map<String, Object> map = new HashMap<>();
						map.put("shopCode", list.get(i).get(0));
						map.put("code", list.get(i).get(1));
						map.put("password", list.get(i).get(2));
						list1.add(map);
					}
				}
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("name", hashFileName);
				result.put("size", request.getFile(fileName).getSize());
				result.put("url", list1);
				result.put("thumbnail_url", "upload.do?getthumb=" + hashFileName);
				result.put("delete_url", "upload.do?delfile=" + hashFileName);
				result.put("delete_type", "GET");
				rs.add(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

		}
		return rs;
	}
	
	private File getFile(String basePath,String filePath,String fileName,boolean needZip){
        File resultFile = null;
        if(needZip){
            List<String> files = Arrays.asList(filePath.split(","));
            List<String> realNames = Arrays.asList(fileName.split(","));
            String zipFileName = realNames.get(0);
            zipFileName = zipFileName.substring(0, zipFileName.lastIndexOf(".")) + "等.zip";
            ZipCompressor zc = new ZipCompressor(basePath + zipFileName);
            zc.compress(files, realNames,basePath);
            resultFile = zc.getZipFile();
        }else{
            resultFile = new File(basePath + filePath);
        }
        return resultFile;
    }
	
	public String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
//            URLConnection uc = new URL("file://" + file.getAbsolutePath()).openConnection();
//            String mimetype = uc.getContentType();
//            MimetypesFIleTypeMap gives PNG as application/octet-stream, but it seems so does URLConnection
//            have to make dirty workaround
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype  = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }



    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }
    
    /**
	 * 新增/编辑兑换券编码(excel导入的数据)
	 * 
	 * @param modelJson
	 *            兑换券对象值字符串
	 */
	@RequestMapping("/addCouponDetail")
	@ResponseBody
	public Map<String, Object> addCouponDetail(String modelJson,String couponId,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>(); // 返回信息
		Map<String, Object> serviceResult = new HashMap<String, Object>();
		List<CouponDetailVo> couponDetailVoList = JsonUtil.toList(modelJson, CouponDetailVo.class);
		if (couponDetailVoList != null) {
				try {
					serviceResult=couponDetailService.addCouponDetail(couponDetailVoList, couponId);
					if((Integer)serviceResult.get("ifExgistShopCode")==0){
						result.put("success", false);
						result.put("msg", String.valueOf(serviceResult.get("info")));
					}else{
						result.put("success", true);
						result.put("msg", "添加兑换券编码成功");
					}
					
				} catch (Exception e) {
					result.put("success", false);
					result.put("msg", "添加兑换券编码失败");
					logger.error("编辑兑换券编码失败-controller", e.getMessage());
				}

			} 
		return result;
	}
	
}
