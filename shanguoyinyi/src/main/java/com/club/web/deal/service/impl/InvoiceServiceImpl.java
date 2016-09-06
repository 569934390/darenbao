package com.club.web.deal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.club.core.common.Page;
import com.club.framework.util.JsonUtil;
import com.club.framework.util.ListUtils;
import com.club.web.common.Constants;
import com.club.web.deal.dao.extend.InvoiceExtendMapper;
import com.club.web.deal.service.InvoiceService;
import com.club.web.deal.vo.InvoiceVo;
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
/**
 * 发票服务接口实现类
 * 
 * @author zhuzd
 *
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InvoiceExtendMapper dao;
	
	
	@Override
	public Page<Map<String, Object>> list(Page<Map<String, Object>> page) {
		int startIndex = 0;
		int pageSize = 10;
		int total = 0;
		List<InvoiceVo> list = null;
		List<Map<String, Object>> listMap = null;
		if (page != null) {
			startIndex = page.getStart();
			pageSize = page.getLimit();
			Map<String, Object> con = page.getConditons();
			total = dao.queryTotalByMap(con);
			page.setTotalRecords(total);
			if(total > 0){
				con.put("startIndex", startIndex);
				con.put("pageSize", pageSize);
				list = dao.queryListByMap(con);
				listMap = CommonUtil.listObjTransToListMap(list);
				if (listMap != null && listMap.size() > 0) {
					page.setResultList(listMap);
				}
			}
		}
		return page;
	}

	@Override
	public void exportExcel(String condition, HttpServletResponse response) {
		try{
			Map<String,Object> con = JsonUtil.toMap(condition);
			List<InvoiceVo> list = dao.queryListByMap(con);
            writeExcel(list,response);   
		}catch(Exception e){
            logger.error("invoiceService exportExcel",e);
		}
	}

	private void writeExcel(List<InvoiceVo> list, HttpServletResponse response) throws Exception {
		ServletOutputStream outputStream = null;
		WritableWorkbook wbook = null;
		try{			
			outputStream = response.getOutputStream();
	        response.reset();
	        
	        String fileName = "发票列表-"+DateParseUtil.formatDate(new Date(), Constants.YYYYMMDD)+".xls";
	        // 文件名为中文不乱码
	        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
	        response.setContentType("application/msexcel;charset=UTF-8");
	        response.setCharacterEncoding("utf-8");
	        
	        wbook = jxl.Workbook.createWorkbook(outputStream);
	        String tmptitle = "发票列表";
	        WritableSheet wsheet = wbook.createSheet(tmptitle, 0);
	        initHeader(wsheet);
	        initContext(wsheet,list);
		}catch(Exception e){
			logger.error("writeExcel error:",e);
			throw e;
		}finally{
			if(wbook != null){
				wbook.write();
		        wbook.close();
			}if(outputStream != null){
		        outputStream.flush();
		        outputStream.close();
			}
		}
	}
	
	
	private void initHeader(WritableSheet wsheet) throws Exception {
        wsheet.setColumnView(0,25);
        wsheet.setColumnView(1,12);
        wsheet.setColumnView(2,15);
        wsheet.setColumnView(3,30);
        wsheet.setColumnView(4,10);
        wsheet.setColumnView(5,10);
		WritableCellFormat ccf = new WritableCellFormat();
        ccf.setBorder(Border.ALL, BorderLineStyle.THIN);
        ccf.setAlignment(Alignment.LEFT);
        ccf.setBackground(Colour.YELLOW);
        ccf.setWrap(true);
		wsheet.addCell(new Label(0, 0, "订单号", ccf));
		wsheet.addCell(new Label(1, 0, "收货人",ccf));
		wsheet.addCell(new Label(2, 0, "收货人手机号",ccf));
		wsheet.addCell(new Label(3, 0, "抬头",ccf));
		wsheet.addCell(new Label(4, 0, "商品数量",ccf));
		wsheet.addCell(new Label(5, 0, "订单状态",ccf));
	}

	private void initContext(WritableSheet wsheet, List<InvoiceVo> list) throws Exception{
		if(ListUtils.isNotEmpty(list)){
			WritableCellFormat ccf = new WritableCellFormat();
	        ccf.setBorder(Border.ALL,BorderLineStyle.THIN);
	        ccf.setVerticalAlignment(VerticalAlignment.CENTRE);
	        ccf.setAlignment(Alignment.LEFT);
	        ccf.setWrap(true);
			int rowIndex = 1;
			for(int row = 0; row < list.size(); row++){
				InvoiceVo vo = list.get(row);
				wsheet.addCell(new Label(0, row+rowIndex, vo.getIndentName(), ccf));
				wsheet.addCell(new Label(1, row+rowIndex, vo.getReceiver(),ccf));
				wsheet.addCell(new Label(2, row+rowIndex, vo.getReceiverPhone(),ccf));
				wsheet.addCell(new Label(3, row+rowIndex, vo.getInvoiceText(),ccf));
				wsheet.addCell(new Label(4, row+rowIndex, vo.getNumber()+"",ccf));		
				wsheet.addCell(new Label(5, row+rowIndex, vo.getStatusName(),ccf));		
			}
		}		
	}
	
}
