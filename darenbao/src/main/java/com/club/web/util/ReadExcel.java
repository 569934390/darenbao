package com.club.web.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author hqLin
 * @created 2016-05-11
 */
public class ReadExcel {
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
	
    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     */
    public List<Map<Integer, Object>> readExcel(String path) {
        if(path == null || "".equals(path)) {
            return null;
        } else{
            String postfix = getPostfix(path);
            if(!"".equals(postfix)) {
                if(OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    try {
						return readXls(path);
					} catch (IOException e) {
						e.printStackTrace();
					}
                } else if(OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    try {
						return readXlsx(path);
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            } else {
                System.out.println(path + NOT_EXCEL_FILE);
            }
        }
        
        return null;
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public List<Map<Integer, Object>> readXlsx(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        Map<Integer, Object> content = new HashMap<Integer, Object>();
        List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
        boolean addRow = false;
        // Read the Sheet
        for(int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            XSSFRow row = xssfSheet.getRow(0);
            int colNum = row.getPhysicalNumberOfCells();
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                addRow = false;
                if (xssfRow != null) {
                	int j = 0;
                	content = new HashMap<Integer, Object>();
                    while(j < colNum) {
                    	String cell = getValue(xssfRow.getCell(j));
                    	if(cell != null && !cell.isEmpty() && !addRow){
                    		addRow = true;
                    	}
                    	content.put(j, getValue(xssfRow.getCell(j)).trim());
                        j++;
                    }
                    if(addRow){
                    	list.add(content);                    	
                    }
                }
            }
        }
        is.close();
        
        return list;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public List<Map<Integer, Object>> readXls(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        Map<Integer, Object> content = new HashMap<Integer, Object>();
        List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
        boolean addRow = false;
        
        // Read the Sheet      
        for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if(hssfSheet == null) {
                continue;
            }
            // Read the Row
            HSSFRow row = hssfSheet.getRow(0);
            int colNum = row.getPhysicalNumberOfCells();
            for(int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if(hssfRow != null) {
                	int j = 0;
                	content = new HashMap<Integer, Object>();
                	addRow = false;
                    while (j < colNum) {
                    	String cell = getValue(hssfRow.getCell(j));
                    	if(cell != null && !cell.isEmpty()  && !addRow){
                    		addRow = true;
                    	}
                    	content.put(j, cell.trim());
                        j++;
                    }
                    if(addRow){
                    	list.add(content);                    	
                    }
                }
            }
        }
        is.close();
        
        return list;
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
    	if(xssfRow != null && !xssfRow.equals("")){
    		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
    			return String.valueOf(xssfRow.getBooleanCellValue());
    		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
    			return String.valueOf(xssfRow.getNumericCellValue());
    		} else {
    			return String.valueOf(xssfRow.getStringCellValue());
    		}    		
    	} else{
    		return "";
    	}
    }

    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
    	if(hssfCell != null){
    		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
    			return String.valueOf(hssfCell.getBooleanCellValue());
    		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
    			return String.valueOf(hssfCell.getNumericCellValue());
    		} else {
    			return String.valueOf(hssfCell.getStringCellValue());
    		}    		
    	} else{
    		return "";
    	}
    }
    
    public String getPostfix(String path) {
        if (path == null || "".equals(path.trim())) {
            return "";
        }
        if (path.contains(".")) {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
        
        return "";
    }
}