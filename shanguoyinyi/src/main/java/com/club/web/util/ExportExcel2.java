package com.club.web.util;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcel2<T> {
	public void exportExcel(Collection<T> dataset, OutputStream out,BigDecimal payMoney,BigDecimal supplyMoney) {
		String payMoneyStr = payMoney != null ? payMoney.toString() : "0";
		String supplyMoneyStr = supplyMoney != null ? supplyMoney.toString() : "0";
		exportExcel("汇总", null, dataset, out, "yyyy-MM-dd", payMoneyStr, supplyMoneyStr);
	}

	public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out,BigDecimal payMoney,BigDecimal supplyMoney) {
		String payMoneyStr = payMoney != null ? payMoney.toString() : "0";
		String supplyMoneyStr = supplyMoney != null ? supplyMoney.toString() : "0";
		exportExcel("汇总", headers, dataset, out, "yyyy-MM-dd", payMoneyStr, supplyMoneyStr);
	}

	public void exportExcel(String[] headers, Collection<T> dataset, OutputStream out, String pattern,BigDecimal payMoney,BigDecimal supplyMoney) {
		String payMoneyStr = payMoney != null ? payMoney.toString() : "0";
		String supplyMoneyStr = supplyMoney != null ? supplyMoney.toString() : "0";
		exportExcel("汇总", headers, dataset, out, pattern, payMoneyStr, supplyMoneyStr);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern,String payMoney,String supplyMoney) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);

		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();

		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// 把字体应用到当前的样式
		style.setFont(font);

		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		// 把字体应用到当前的样式
		style2.setFont(font2);
		String payMoneyStr = payMoney != null ? payMoney.toString() : "0";
		String supplyMoneyStr = supplyMoney != null ? supplyMoney.toString() : "0";

		try {
			makeSheet(workbook, sheet, dataset, headers, style, style2, pattern, payMoneyStr, supplyMoneyStr);
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeSheet(HSSFWorkbook workbook, HSSFSheet sheet, Collection<T> dataset, String[] headers,
			HSSFCellStyle style, HSSFCellStyle contentStyle, String pattern, String payMoney,String supplyMoney) {
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));

		// 设置注释内容
//		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));

		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(style);
		HSSFRichTextString text1 = new HSSFRichTextString("支付总金额");
		cell.setCellValue(text1);
		
		cell = row.createCell(1);
//		cell.setCellStyle(contentStyle);
		cell.setCellValue(Double.parseDouble(payMoney));
		
		cell = row.createCell(2);
		cell.setCellStyle(style);
		text1 = new HSSFRichTextString("结算总金额");
		cell.setCellValue(text1);
		
		cell = row.createCell(3);
//		cell.setCellStyle(contentStyle);
		cell.setCellValue(Double.parseDouble(supplyMoney));
		
		row = sheet.createRow(1);
		for (short i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		Iterator<T> it = dataset.iterator();
		int index = 1;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();

			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(contentStyle);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});

					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue) {
							textValue = "女";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);

						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));

						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, i, index, i,
								index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						if(value!=null)
							textValue = value.toString();
						else 
							textValue = "";
					}

					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLUE.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {

				}
			}
		}
	}
}