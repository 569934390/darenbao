package com.club.web.event.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.ListUtils;
import com.club.web.common.Constants;
import com.club.web.event.domain.EventActivitySignUpDo;
import com.club.web.event.domain.repository.EventActivitySignUpRepository;
import com.club.web.event.service.EventActivityService;
import com.club.web.event.service.EventActivitySignUpService;
import com.club.web.event.vo.EventActivitySignUpExportVo;
import com.club.web.event.vo.EventActivitySignUpSaveVo;
import com.club.web.event.vo.EventActivitySignUpVo;
import com.club.web.util.CommonUtil;
import com.club.web.util.DateParseUtil;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service("eventActivityUserService")
public class EventActivitySignUpServiceImpl implements EventActivitySignUpService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EventActivitySignUpRepository activitySignUpRepository;

	@Autowired
	EventActivityService eventActivityService;

	/**
	 * 分类查询报名用户
	 */
	public Page<Map<String, Object>> queryActivityUserPage(Page<Map<String, Object>> page) {
		Page<Map<String, Object>> result = new Page<Map<String, Object>>();
		result.setStart(page.getStart());
		result.setLimit(page.getLimit());
		List<Map<String, Object>> list = activitySignUpRepository.queryActivityUserPage(page);
		Long count = activitySignUpRepository.queryActivityUserCountPage(page);
		result.setResultList(list);
		result.setTotalRecords(count.intValue());
		return result;
	}

	/**
	 * 活动报名
	 */
	public Map<String, Object> signUp(EventActivitySignUpSaveVo activitySignUpVo, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (activitySignUpVo.getTel() == null || "".equals(activitySignUpVo.getTel())) {
			result.put("success", false);
			result.put("msg", "报名电话不能为空！");
			return result;
		} else {
			if (!CommonUtil.isMobile(activitySignUpVo.getTel())) {
				result.put("success", false);
				result.put("msg", "报名电话不正确！");
				return result;
			}
		}
		Long remainingNumber = eventActivityService
				.findRemainingNumberById(Long.parseLong(activitySignUpVo.getEventActivityId()));
		if (remainingNumber <= 0) {
			result.put("success", false);
			result.put("msg", "该活动已经超过限制人数！");
			return result;
		} else {
			EventActivitySignUpVo oldactivitySignUpVo = activitySignUpRepository
					.findEventActivitySignUpVoByUserInfoAndEventActivityId(activitySignUpVo.getEventActivityId(),
							Long.parseLong(activitySignUpVo.getEventActivityUserinfo()));
			if (oldactivitySignUpVo != null) {
				result.put("success", false);
				result.put("msg", "您已经报名过！");
				return result;
			} else {
				// activitySignUpVo.setEventActivityUserinfo(activitySignUpVo.getEventActivityUserinfo());
				EventActivitySignUpDo eventActivitySignUpDo = activitySignUpRepository.create(activitySignUpVo);
				eventActivitySignUpDo.insert();
				result.put("success", true);
			}
		}
		return result;
	}

	@Override
	public List<EventActivitySignUpVo> findByActivityId(Long eventActivityId) {
		return activitySignUpRepository.findByActivityId(eventActivityId);
	}

	@Override
	public void userExportData(Map map, HttpServletResponse response) throws Exception {
		List<EventActivitySignUpExportVo> list = selectExportData(map);
		ServletOutputStream outputStream = null;
		WritableWorkbook wbook = null;
		try {
			outputStream = response.getOutputStream();
			response.reset();

			String fileName = "活动报名名单-" + DateParseUtil.formatDate(new Date(), Constants.YYYYMMDD) + ".xls";
			// 文件名为中文不乱码
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			response.setContentType("application/msexcel;charset=UTF-8");
			response.setCharacterEncoding("utf-8");

			wbook = jxl.Workbook.createWorkbook(outputStream);
			String tmptitle = "活动报名名单";
			WritableSheet wsheet = wbook.createSheet(tmptitle, 0);
			initHeader(wsheet);
			initContext(wsheet, list);
		} catch (Exception e) {
			logger.error("writeExcel error:", e);
			throw e;
		} finally {
			if (wbook != null) {
				wbook.write();
				wbook.close();
			}
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}
		}
	}

	private void initHeader(WritableSheet wsheet) throws Exception {
		wsheet.setColumnView(0, 25);
		wsheet.setColumnView(1, 12);
		wsheet.setColumnView(2, 15);
		wsheet.setColumnView(3, 30);
		WritableCellFormat ccf = new WritableCellFormat();
		ccf.setBorder(Border.ALL, BorderLineStyle.THIN);
		ccf.setAlignment(Alignment.LEFT);
		ccf.setBackground(Colour.YELLOW);
		ccf.setWrap(true);
		wsheet.addCell(new Label(0, 0, "店铺名", ccf));
		wsheet.addCell(new Label(1, 0, "活动开展的时间", ccf));
		wsheet.addCell(new Label(2, 0, "活动标题", ccf));
		wsheet.addCell(new Label(3, 0, "用户昵称", ccf));
		wsheet.addCell(new Label(4, 0, "手机号码", ccf));
	}

	private void initContext(WritableSheet wsheet, List<EventActivitySignUpExportVo> list) throws Exception {
		if (ListUtils.isNotEmpty(list)) {
			WritableCellFormat ccf = new WritableCellFormat();
			ccf.setBorder(Border.ALL, BorderLineStyle.THIN);
			ccf.setVerticalAlignment(VerticalAlignment.CENTRE);
			ccf.setAlignment(Alignment.LEFT);
			ccf.setWrap(true);
			int rowIndex = 1;
			for (int row = 0; row < list.size(); row++) {
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				EventActivitySignUpExportVo vo = list.get(row);
				wsheet.addCell(new Label(0, row + rowIndex, vo.getSubbranchName(), ccf));
				wsheet.addCell(new Label(1, row + rowIndex, formatter.format(vo.getBeginTime()), ccf));
				wsheet.addCell(new Label(2, row + rowIndex, vo.getTitle(), ccf));
				wsheet.addCell(new Label(3, row + rowIndex, vo.getNickname(), ccf));
				wsheet.addCell(new Label(4, row + rowIndex, vo.getTel(), ccf));
			}
		}
	}

	private List<EventActivitySignUpExportVo> selectExportData(Map map) {
		return activitySignUpRepository.selectExportData(map);
	}

}
