package com.compses.util;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import com.compses.dto.ReportRequest;

import freemarker.template.Configuration;

public class ReportUtils {
	
//    private static JRAbstractExporter exporter;
    private static Configuration cfg = new Configuration();
    static {
    	cfg.setClassForTemplateLoading(ReportUtils.class, "/template/report");
    	cfg.setEncoding(Locale.CHINA, "UTF-8");
    }

	public static enum REPORT_TYPE {
		PDF, HTML, EXCEL, RTF
	}
	public static String FILE_TYPE_PDF=".pdf";
	public static String FILE_TYPE_HTML=".html";
	public static String FILE_TYPE_EXCEL=".xls";
	public static String FILE_TYPE_RTF=".rtf";
    
    public static void exportReport(HttpServletResponse response,ReportRequest reportRequest) {
//    	response.setCharacterEncoding("UTF-8");
////    	List<String> parameterList=(List<String>) reportRequest.getTemplateMap().get("parameters");
//    	Set<String> fieldSet=reportRequest.getTemplateParameter().getFieldMap().keySet();
//    	Integer columnWidth=555/fieldSet.size();
//    	reportRequest.getTemplateParameter().setColumnWidth(columnWidth);
//    	reportRequest.getTemplateParameter().setLastColumnWidth(555-columnWidth*fieldSet.size()+columnWidth);
//    	reportRequest.getTemplateParameter().setTotalCount(reportRequest.getDataSet().size());
////    	parameterList.add("pageStartDesc");
////    	parameterList.add("pageMiddleDesc");
////    	parameterList.add("pageEndDesc");
////    	parameterList.add("totalStartDesc");
////    	parameterList.add("totalEndDesc");
////    	reportRequest.getParameterMap().put("pageStartDesc", "第");
////    	reportRequest.getParameterMap().put("pageMiddleDesc", "页/共");
////    	reportRequest.getParameterMap().put("pageEndDesc", "页");
////    	reportRequest.getParameterMap().put("totalStartDesc", "共");
////    	reportRequest.getParameterMap().put("totalEndDesc", "条记录");
//    	String fileType="";
//    	if (REPORT_TYPE.PDF.equals(reportRequest.getFileType())) {
//    		response.setContentType("application/pdf");
//    		fileType=ReportUtils.FILE_TYPE_PDF;
//    		exporter=new JRPdfExporter();
//        } else if (REPORT_TYPE.HTML.equals(reportRequest.getFileType())) {
//        	response.setContentType("text/html");
//    		fileType=ReportUtils.FILE_TYPE_HTML;
//        	exporter = new JRHtmlExporter();
//            exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
//        } else if (REPORT_TYPE.EXCEL.equals(reportRequest.getFileType())) {
//    		response.setContentType("application/vnd.ms-excel");
//    		fileType=ReportUtils.FILE_TYPE_EXCEL;
//            exporter = new JRXlsExporter();
//            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
//        } else {
//    		response.setContentType("application/rtf");
//    		fileType=ReportUtils.FILE_TYPE_RTF;
//            exporter = new JRRtfExporter();
//        }
//    	List<?> dataSet=reportRequest.getDataSet();
//    	JRDataSource dataSource = new JRBeanCollectionDataSource(dataSet);
//		Template template;
//		try {
//			template = cfg.getTemplate(reportRequest.getTemplateName());
//			StringWriter writer=new StringWriter();
//			template.process(reportRequest.getTemplateParameter(), writer);
//			String content=writer.toString();
//			writer.close();
//			System.out.println("=============报表模版");
//			System.out.println(content);
//			System.out.println("=============报表模版");
//			JasperDesign design = JRXmlLoader.load(new ByteArrayInputStream(content.getBytes("UTF-8")));
//			JasperReport report = JasperCompileManager.compileReport(design);
//			JRDesignParameter parameter=new JRDesignParameter();
//			JasperPrint jasperPrint = JasperFillManager.fillReport(report, reportRequest.getReportParameterMap(),
//					dataSource);
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//            exporter.exportReport();
//            String fileName=URLEncoder.encode(reportRequest.getReportName()+fileType,"UTF-8");
//			response.setContentLength(baos.size());
//			response.setHeader("Content-Disposition", "inline; filename=" +fileName);
//			ServletOutputStream out = response.getOutputStream();
//			baos.writeTo(out);
//			out.flush();
//			out.close();
//		}catch (TemplateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
    }
    public static String processTemplate(){
		return null;
    	
    }
}