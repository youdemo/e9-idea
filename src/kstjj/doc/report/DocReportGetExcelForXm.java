package kstjj.doc.report;

import com.fr.third.v2.org.apache.poi.hssf.usermodel.*;
import com.fr.third.v2.org.apache.poi.hssf.util.HSSFColor;
import com.fr.third.v2.org.apache.poi.ss.usermodel.CellStyle;
import com.fr.third.v2.org.apache.poi.ss.usermodel.IndexedColors;
import com.fr.third.v2.org.apache.poi.ss.util.CellRangeAddress;
import com.fr.third.v2.org.apache.poi.hssf.usermodel.HSSFCell;
import weaver.conn.RecordSet;
import weaver.general.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 按姓名数量统计表 导出excel
 */
public class DocReportGetExcelForXm {

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
        titleCell.setCellValue("序号");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("姓名");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("信息");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("分析,内参，文稿");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);

        titleRow = sheet.createRow((short) rowNum++);
        cellnum=0;
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("工作动态");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("经济信息");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("合计");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("统计分析");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("统计内参");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("党政文稿");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell.setCellValue("合计");
        CellRangeAddress region = new CellRangeAddress(0, 1, 0, 0);
        sheet.addMergedRegion(region);
        region = new CellRangeAddress(0, 1, 1, 1);
        sheet.addMergedRegion(region);
        region = new CellRangeAddress(0, 0, 2, 4);
        sheet.addMergedRegion(region);
        region = new CellRangeAddress(0, 0, 5, 8);
        sheet.addMergedRegion(region);
        RecordSet rs = new RecordSet();
        String sql = "";
        int seq = 1;
        if(!"".equals(year)){
            sql = "select (select lastname from hrmresource where id=xm) as xm,dbo.f_get_documentcount(xm,'',"+year+",'',0,0) as gzdt,dbo.f_get_documentcount(xm,'',"+year+",'',0,1) as jjxx,dbo.f_get_documentcount(xm,'',"+year+",'',0,0)+dbo.f_get_documentcount(xm,'',"+year+",'',0,1) as hj1,dbo.f_get_documentcount(xm,'',"+year+",'',0,2) as tjfx,dbo.f_get_documentcount(xm,'',"+year+",'',0,3) as tjnc,dbo.f_get_documentcount(xm,'',"+year+",'',0,4) as djwg,dbo.f_get_documentcount(xm,'',"+year+",'',0,2)+dbo.f_get_documentcount(xm,'',"+year+",'',0,3)+dbo.f_get_documentcount(xm,'',"+year+",'',0,4) as hj2 from uf_Documentreport where 1=1 and nf="+year+" and (xm<>'' and xm is not null) group by xm";
        }else{
            sql = "select (select lastname from hrmresource where id=xm) as xm,dbo.f_get_documentcount(xm,'','','',0,0) as gzdt,dbo.f_get_documentcount(xm,'','','',0,1) as jjxx,dbo.f_get_documentcount(xm,'','','',0,0)+dbo.f_get_documentcount(xm,'','','',0,1) as hj1,dbo.f_get_documentcount(xm,'','','',0,2) as tjfx,dbo.f_get_documentcount(xm,'','','',0,3) as tjnc,dbo.f_get_documentcount(xm,'','','',0,4) as djwg,dbo.f_get_documentcount(xm,'','','',0,2)+dbo.f_get_documentcount(xm,'','','',0,3)+dbo.f_get_documentcount(xm,'','','',0,4) as hj2 from uf_Documentreport where 1=1 and (xm<>'' and xm is not null)  group by xm";
        }
        rs.execute(sql);
        while(rs.next()){
            String xm = Util.null2String(rs.getString("xm"));
            String gzdt = Util.null2String(rs.getString("gzdt"));
            String jjxx = Util.null2String(rs.getString("jjxx"));
            String hj1 = Util.null2String(rs.getString("hj1"));
            String tjfx = Util.null2String(rs.getString("tjfx"));
            String tjnc = Util.null2String(rs.getString("tjnc"));
            String djwg = Util.null2String(rs.getString("djwg"));
            String hj2 = Util.null2String(rs.getString("hj2"));
            cellnum = 0;
            HSSFRow row = sheet.createRow((short) rowNum++);
            HSSFCell Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(seq);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(xm);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(gzdt);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(jjxx);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(hj1);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(tjfx);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(tjnc);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(djwg);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(hj2);
        }

        return workBook;
    }
}
