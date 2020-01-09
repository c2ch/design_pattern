package com.fline.file;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("D:\\Users\\User\\Desktop\\IMPORT.xlsx");
        List<AgentPVo> agentPVos = readExcel(new FileInputStream(file), "");
    }


    public static List<AgentPVo> readExcel(InputStream inputStream, String sheetName) {

        //定义工作簿
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            System.out.println("Excel data file cannot be found!");
        }
        //定义工作表
        XSSFSheet xssfSheet;
        if (sheetName.equals("")) {
            // 默认取第一个子表
            xssfSheet = xssfWorkbook.getSheetAt(0);
        } else {
            xssfSheet = xssfWorkbook.getSheet(sheetName);
        }

        List<AgentPVo> list = new ArrayList<AgentPVo>();

        //定义行
        //默认第一行为标题行，index = 0
        XSSFRow titleRow = xssfSheet.getRow(0);

        //循环取每行的数据
        for (int rowIndex = 1; rowIndex < xssfSheet.getPhysicalNumberOfRows(); rowIndex++) {
//		for (int rowIndex = rowIndexBegin; rowIndex < xssfSheet.getPhysicalNumberOfRows(); rowIndex++) {
            XSSFRow xssfRow = xssfSheet.getRow(rowIndex);
            if (xssfRow == null) {
                continue;
            }

            //循环取每个单元格(cell)的数据
            for (int cellIndex = 1; cellIndex < xssfRow.getPhysicalNumberOfCells(); cellIndex++) {
                XSSFCell titleCell = titleRow.getCell(cellIndex);

                XSSFCell xssfCell = xssfRow.getCell(cellIndex);



            }
        }
        return list;
    }



    /**
     * 把单元格的内容转为字符串
     * @param xssfCell 单元格
     * @return 字符串
     */
    public static String getString(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return "";
        }
        if (xssfCell.getCellTypeEnum() == CellType.NUMERIC) {
            return String.valueOf(xssfCell.getNumericCellValue());
        } else if (xssfCell.getCellTypeEnum() == CellType.BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else {
            return xssfCell.getStringCellValue();
        }
    }
}
