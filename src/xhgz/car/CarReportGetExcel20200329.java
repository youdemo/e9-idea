package xhgz.car;

import com.fr.third.v2.org.apache.poi.hssf.usermodel.*;
import com.fr.third.v2.org.apache.poi.hssf.util.HSSFColor;
import com.fr.third.v2.org.apache.poi.ss.util.CellRangeAddress;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;

import java.util.ArrayList;

/**
 * 车辆使用 导出excel
 */
public class CarReportGetExcel20200329 {

    public HSSFWorkbook createExcel(String searchday) throws Exception {
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet("sheet1");
        sheet.setColumnWidth(0, (int)((10 + 0.72) * 256));
        sheet.setColumnWidth(1, (int)((20 + 0.72) * 256));
        sheet.setColumnWidth(2,(int)((20 + 0.72) * 256));
        sheet.setColumnWidth(3,(int)((20 + 0.72) * 256));
        sheet.setColumnWidth(4,(int)((20 + 0.72) * 256));
        sheet.setColumnWidth(5,(int)((20 + 0.72) * 256));
        // 创建行
        int rowNum = 0;
        String year =searchday.substring(0,4);
        String month =searchday.substring(5,7);
        if("0".equals(month.substring(0,1))){
            month = month.substring(1,2);
        }
        String day =searchday.substring(8,10);
        if("0".equals(day.substring(0,1))){
            day = day.substring(1,2);
        }
        String title = year+"年"+month+"月"+day+"日车辆使用情况";

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
        titleCell.setCellValue(title);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle);

        titleRow = sheet.createRow((short) rowNum++);
        cellnum=0;
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("姓名/工号");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("范炳旺");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("李永红");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("谢江平");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("林小东");
        titleRow = sheet.createRow((short) rowNum++);
        cellnum=0;
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("A1101001");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("A0708003");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("A1907019");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("A1507010");
        titleRow = sheet.createRow((short) rowNum++);
        cellnum=0;
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("所开车辆");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("苏E03T8V");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("苏EG83X0");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("沪DPG881");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("苏EA83B7");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("苏E7L01H");
        titleRow = sheet.createRow((short) rowNum++);
        cellnum=0;
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("上班时间");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("8:30~17:30");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("8:30~17:30");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("8:30~17:30//7:30~16:30");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("8:30~17:30//7:30~16:30");
        titleCell = titleRow.createCell((short) cellnum++);
        titleCell.setCellStyle(cellStyle2);
        titleCell.setCellValue("5:30~14:30");

        RecordSet rs = new RecordSet();
        ResourceComInfo res = new ResourceComInfo();
        ArrayList<String> list1 = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        ArrayList<String> list3 = new ArrayList<String>();
        ArrayList<String> list4 = new ArrayList<String>();
        ArrayList<String> list5 = new ArrayList<String>();
        String ccrymd = "";
        String ch = "";
        String sql = "select yjccsj1,yjfhsj1,ccrymd,mdd,(select lastname from hrmresource where id=a.jsy) as jsy,(select t2.selectname from workflow_billfield t, workflow_bill t1,workflow_selectitem t2 where t.billid=t1.id and t2.fieldid=t.id  and t1.tablename='formtable_main_68' and t.fieldname='ch' and t2.selectvalue=a.ch) as ch from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype<=3 and yjccrq1='"+searchday+"' order by yjccsj asc";
        rs.execute(sql);
        while(rs.next()){
            String ccrynames ="";
            ccrymd =  Util.null2String(rs.getString("ccrymd"));
            String ccrymdarr[] = ccrymd.split(",");
            String flag = "";
            for(String ryid:ccrymdarr){
                String name = res.getResourcename(ryid);
                if(!"".equals(name)){
                    ccrynames = ccrynames+flag+name;
                    flag="、";
                }
            }
            String ycqk = Util.null2String(rs.getString("yjccsj1"))+" "+ccrynames+" "+Util.null2String(rs.getString("mdd"))+" "+Util.null2String(rs.getString("yjfhsj1"));
            ch = Util.null2String(rs.getString("ch"));
            if("苏E03T8V".equals(ch)){
                list1.add(ycqk);
            }else if("苏EG83X0".equals(ch)){
                list2.add(ycqk);
            }else if("沪DPG881".equals(ch)){
                list3.add(ycqk);
            }else if("苏EA83B7".equals(ch)){
                list4.add(ycqk);
            }else if("苏E7L01H".equals(ch)){
                list5.add(ycqk);
            }
        }
        int size1 = list1.size();
        int size2 = list2.size();
        int size3 = list3.size();
        int size4 = list4.size();
        int size5 = list4.size();
        int maxsize = size1;
        if(maxsize<size2){
            maxsize = size2;
        }
        if(maxsize<size3){
            maxsize = size3;
        }
        if(maxsize<size4){
            maxsize = size4;
        }
        if(maxsize<size5){
            maxsize = size5;
        }
        for(int i=0;i<maxsize;i++){
            String ycqk1= "";
            String ycqk2 = "";
            String ycqk3 = "";
            String ycqk4 = "";
            String ycqk5 = "";
            if(size1>i){
                ycqk1 = list1.get(i);
            }
            if(size2>i){
                ycqk2 = list2.get(i);
            }
            if(size3>i){
                ycqk3 = list3.get(i);
            }
            if(size4>i){
                ycqk4 = list4.get(i);
            }
            if(size5>i){
                ycqk5 = list5.get(i);
            }
            cellnum = 0;
            HSSFRow row = sheet.createRow((short) rowNum++);
            HSSFCell Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue("用车情况");
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(ycqk1);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(ycqk2);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(ycqk3);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(ycqk4);
            Cell = row.createCell((short) cellnum++);
            Cell.setCellStyle(cellStyle2);
            Cell.setCellValue(ycqk5);
        }

        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(region);
        region = new CellRangeAddress(1, 2, 0, 0);
        sheet.addMergedRegion(region);
        region = new CellRangeAddress(1, 1, 1, 2);
        sheet.addMergedRegion(region);
        region = new CellRangeAddress(2, 2, 1, 2);
        sheet.addMergedRegion(region);


        return workBook;
    }
}
