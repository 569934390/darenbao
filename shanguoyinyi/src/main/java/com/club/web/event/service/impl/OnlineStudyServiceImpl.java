package com.club.web.event.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.club.core.common.Page;
import com.club.web.common.Constants;
import com.club.web.event.domain.EventOnlineStudyDo;
import com.club.web.event.domain.repository.OnlineStudyRepository;
import com.club.web.event.service.OnlineStudyService;
import com.club.web.event.vo.EventOnlineStudyVo;
import com.club.web.image.service.ImageService;
import com.club.web.image.service.vo.ImageVo;
import com.club.web.stock.domain.CargoBrandDo;
import com.club.web.util.IdGenerator;

/**
 * @Title: OnlineStudyServiceImpl.java
 * @Package com.club.web.event.service.impl
 * @Description: TODO(在线学习service)
 * @author 柳伟军
 * @date 2016年4月13日 下午2:16:15
 * @version V1.0
 */
@Service("onlineStudyService")
@Transactional
public class OnlineStudyServiceImpl implements OnlineStudyService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	OnlineStudyRepository onlineStudyRepository;

	@Autowired
	ImageService imageService;

	@Override
	public Map<String, Object> saveOrUpdateOnlineStudy(EventOnlineStudyVo eventOnlineStudyVo,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (eventOnlineStudyVo != null) {
			if (null == eventOnlineStudyVo.getTitle() || "".equals(eventOnlineStudyVo.getTitle())) {
				result.put("success", false);
				result.put("msg", "请输入学习标题");
				return result;
			}

			List<EventOnlineStudyVo> eventOnlineStudyVoByTitle = onlineStudyRepository
					.queryOnlineStudyVoByTitle(eventOnlineStudyVo.getTitle());
			/**
			 * 获取当前登录用户的session
			 */
			Map<String, Object> loginMap = (Map<String, Object>) request.getSession().getAttribute(Constants.STAFF);
			Long user_id = null;
			if (loginMap != null && loginMap.get("staffId") != null) {
				user_id = Long.parseLong(loginMap.get("staffId").toString());
			}
			Date nowTime = new Date();
			
		/*	if(eventOnlineStudyVo.getFile()!=null&&!"".equals(eventOnlineStudyVo.getFile())){
				try {
					eventOnlineStudyVo.setFile(URLDecoder.decode(eventOnlineStudyVo.getFile(), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}*/

			if (eventOnlineStudyVo.getId() == null || "".equals(eventOnlineStudyVo.getId())) {
				if (eventOnlineStudyVoByTitle.size() > 0) {
					result.put("success", false);
					result.put("msg", "该标题已经存在!");
					return result;
				}
				eventOnlineStudyVo.setId(IdGenerator.getDefault().nextId() + "");
				// 如果传过来的图片不为空。则保存记录
				if (eventOnlineStudyVo.getCovePic() != null && !"".equals(eventOnlineStudyVo.getCovePic())) {
					ImageVo imageVo = imageService.saveImage(eventOnlineStudyVo.getCovePic());
					eventOnlineStudyVo.setCovePic(imageVo.getId() + "");
				}
				eventOnlineStudyVo.setCreateTime(nowTime);
				eventOnlineStudyVo.setCreateBy(user_id);
				eventOnlineStudyVo.setUpdateTime(nowTime);
				eventOnlineStudyVo.setUpdateBy(user_id);
				EventOnlineStudyDo eventOnlineStudyDo = onlineStudyRepository.create(eventOnlineStudyVo);
				eventOnlineStudyDo.insert();
			} else {
				EventOnlineStudyDo eventOnlineStudyDo = onlineStudyRepository
						.getEventOnlineStudyDoById(Long.parseLong(eventOnlineStudyVo.getId()));
				if (eventOnlineStudyVoByTitle.size() > 0
						&& !eventOnlineStudyDo.getTitle().equalsIgnoreCase(eventOnlineStudyVo.getTitle())) {
					result.put("success", false);
					result.put("msg", "该标题已经存在!");
					return result;
				}

				// 如果之前图片为空
				if (eventOnlineStudyDo.getCovePic() == null || "".equals(eventOnlineStudyDo.getCovePic())) {
					// 如果传过来的图片不为空。则保存记录
					if (eventOnlineStudyVo.getCovePic() != null && !"".equals(eventOnlineStudyVo.getCovePic())) {
						ImageVo imageVo = imageService.saveImage(eventOnlineStudyVo.getCovePic());
						eventOnlineStudyDo.setCovePic(imageVo.getId() + "");
					}
				} else {
					// 如果传过来的图片不为空。则更新记录
					if (eventOnlineStudyVo.getCovePic() != null && !"".equals(eventOnlineStudyVo.getCovePic())) {
						// 查询图片记录并更新
						ImageVo imageVo = imageService.selectImageById(Long.parseLong(eventOnlineStudyDo.getCovePic()));
						imageVo.setPicUrl(eventOnlineStudyVo.getCovePic());
						imageService.updateImage(imageVo);
					} else {
						// 如果传过来的图片为空，则删除记录
						imageService.deleteById(Long.parseLong(eventOnlineStudyDo.getCovePic()));
						eventOnlineStudyDo.setCovePic(null);
					}
				}
				eventOnlineStudyDo.setFile(eventOnlineStudyVo.getFile());
				eventOnlineStudyDo.setTitle(eventOnlineStudyVo.getTitle());
				eventOnlineStudyDo.setStudyType(Long.parseLong(eventOnlineStudyVo.getStudyType()));
				eventOnlineStudyDo.setStudyChildType(Long.parseLong(eventOnlineStudyVo.getStudyChildType()));
				eventOnlineStudyDo.setType(eventOnlineStudyVo.getType());
				eventOnlineStudyDo.setAuthor(eventOnlineStudyVo.getAuthor());
				eventOnlineStudyDo.setVideoUrl(eventOnlineStudyVo.getVideoUrl());
				eventOnlineStudyDo.setContent(eventOnlineStudyVo.getContent());
				eventOnlineStudyDo.setUpdateTime(nowTime);
				eventOnlineStudyDo.setUpdateBy(user_id);
				eventOnlineStudyDo.update();
			}
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("msg", "在线学习信息不能为空");
		}
		return result;
	}

	@Override
	public Page<Map<String, Object>> queryOnlineStudyPage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = onlineStudyRepository.queryOnlineStudyPage(page);
		Long count = onlineStudyRepository.queryOnlineStudyCountPage(page);
		result.setResultList(list);
		result.setTotalRecords(count.intValue());
		return result;
	}

	@Override
	public Map<String, Object> delete(String idStr) {
		String[] Ids = idStr.split(",");
		Map<String, Object> result = new HashMap<String, Object>();
		for (String id : Ids) {

			// 根据id查询
			EventOnlineStudyDo eventOnlineStudyDo = onlineStudyRepository.getEventOnlineStudyDoById(Long.parseLong(id));

			// 删除图片
			if (eventOnlineStudyDo.getCovePic() != null && !"".equals(eventOnlineStudyDo.getCovePic())) {
				imageService.deleteById(Long.parseLong(eventOnlineStudyDo.getCovePic()));
			}

			// 删除
			eventOnlineStudyDo.delete();
		}
		result.put("success", true);
		return result;
	}

	/**
	 * 手机详情
	 */
	public Map<String, Object> findOneById(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (id == null || "".equals(id)) {
			result.put("success", false);
			result.put("msg", "参数错误！");
		} else {
			EventOnlineStudyDo eventOnlineStudyDo = onlineStudyRepository
					.getEventOnlineStudyDoById(Long.parseLong(id));
			eventOnlineStudyDo.setReadNum(eventOnlineStudyDo.getReadNum()+1);
			eventOnlineStudyDo.update();
			result.put("success", true);
			result.put("msg", onlineStudyRepository.getEventOnlineStudyVoById(Long.parseLong(id)));
		}
		return result;
	}

}
