package kstjj.doc.report;

import com.fr.third.v2.org.apache.poi.hssf.usermodel.*;
import com.fr.third.v2.org.apache.poi.hssf.util.HSSFColor;
import com.fr.third.v2.org.apache.poi.ss.util.CellRangeAddress;
import weaver.conn.RecordSet;
import weaver.general.Util;

import java.io.IOException;

/**
 * 按类别数量统计表 导出excel
 */
public class DocReportGetExcelForLb {

    public HSSFWorkbook createExcel(String year) throws IOException {
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet("sheet1");
        // 创建行
        int rowNum = 0;


        // 字体设置
        HSSFFont font = workBook.createFont();
        font.setFontName("宋体");
        font.setColor(HSSFFont.COLOR_NORMAL);
        // 样式
        HSSFCellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);

// 设置边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFCellStyle cellStyle2 = workBook.createCellStyle();
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle2.setFont(font);
        cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置第一行
        int cellnum=0;
        HSSFRow titleRow = sheet.createRow((short) rowNum++);
        HSSFCell titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("类别");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("数量");

        RecordSet rs = new RecordSet();
        String sql = "";
        String gzdt = "";
        String jjxx = "";
        String hj1 = "";
        String tjfx = "";
        String tjnc = "";
        String djwg = "";
        String hj2 = "";
        String hj3 = "";
        if(!"".equals(year)){
            sql = "select a.*,hj1+hj2 as hj3 from (select dbo.f_get_documentcount('','',"+year+",'',3,0) as gzdt,dbo.f_get_documentcount('','',"+year+",'',3,1) as jjxx,dbo.f_get_documentcount('','',"+year+",'',3,0)+dbo.f_get_documentcount('','',"+year+",'',3,1) as hj1,dbo.f_get_documentcount('','',"+year+",'',3,2) as tjfx,dbo.f_get_documentcount('','',"+year+",'',3,3) as tjnc,dbo.f_get_documentcount('','',"+year+",'',3,4) as djwg,dbo.f_get_documentcount('','',"+year+",'',3,2)+dbo.f_get_documentcount('','',"+year+",'',3,3)+dbo.f_get_documentcount('','',"+year+",'',3,4) as hj2 ) a";
        }else{
            sql = "select a.*,hj1+hj2 as hj3 from (select dbo.f_get_documentcount('','','','',3,0) as gzdt,dbo.f_get_documentcount('','','','',3,1) as jjxx,dbo.f_get_documentcount('','','','',3,0)+dbo.f_get_documentcount('','','','',3,1) as hj1,dbo.f_get_documentcount('','','','',3,2) as tjfx,dbo.f_get_documentcount('','','','',3,3) as tjnc,dbo.f_get_documentcount('','','','',3,4) as djwg,dbo.f_get_documentcount('','','','',3,2)+dbo.f_get_documentcount('','','','',3,3)+dbo.f_get_documentcount('','','','',3,4) as hj2 ) a";
        }
        rs.execute(sql);
        if(rs.next()){

            gzdt = Util.null2String(rs.getString("gzdt"));
            jjxx = Util.null2String(rs.getString("jjxx"));
            hj1 = Util.null2String(rs.getString("hj1"));
            tjfx = Util.null2String(rs.getString("tjfx"));
            tjnc = Util.null2String(rs.getString("tjnc"));
            djwg = Util.null2String(rs.getString("djwg"));
            hj2 = Util.null2String(rs.getString("hj2"));
            hj3 = Util.null2String(rs.getString("hj3"));

        }
        HSSFRow row = sheet.createRow((short) rowNum++);
        HSSFCell Cell = row.createCell((short) 0);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue("工作动态");
        Cell = row.createCell((short) 1);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue(gzdt);

        row = sheet.createRow((short) rowNum++);
        Cell = row.createCell((short) 0);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue("经济信息");
        Cell = row.createCell((short) 1);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue(jjxx);

        row = sheet.createRow((short) rowNum++);
        Cell = row.createCell((short) 0);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue("信息合计");
        Cell = row.createCell((short) 1);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue(hj1);

        row = sheet.createRow((short) rowNum++);
        Cell = row.createCell((short) 0);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue("统计分析");
        Cell = row.createCell((short) 1);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue(tjfx);

        row = sheet.createRow((short) rowNum++);
        Cell = row.createCell((short) 0);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue("统计内参");
        Cell = row.createCell((short) 1);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue(tjnc);

        row = sheet.createRow((short) rowNum++);
        Cell = row.createCell((short) 0);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue("党政文稿");
        Cell = row.createCell((short) 1);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue(djwg);

        row = sheet.createRow((short) rowNum++);
        Cell = row.createCell((short) 0);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue("分析等合计");
        Cell = row.createCell((short) 1);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue(hj2);

        row = sheet.createRow((short) rowNum++);
        Cell = row.createCell((short) 0);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue("总的合计");
        Cell = row.createCell((short) 1);
        Cell.setCellStyle(cellStyle2);
        Cell.setCellValue(hj3);

        return workBook;
    }
}
