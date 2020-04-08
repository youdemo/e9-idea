package xhgz.car;

import com.fr.third.v2.org.apache.poi.hssf.usermodel.*;
import com.fr.third.v2.org.apache.poi.hssf.util.HSSFColor;
import com.fr.third.v2.org.apache.poi.ss.util.CellRangeAddress;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.hrm.resource.ResourceComInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 车辆使用 导出excel
 */
public class CarReportGetExcel {

    public HSSFWorkbook createExcel(String searchday) throws Exception {
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet sheet = workBook.createSheet("sheet1");
        ResourceComInfo ResourceComInfo = new ResourceComInfo();
        RecordSet rs = new RecordSet();
        int jsrcount = 0;
        String sql = "select count(1) as count from (select jsy from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype=3 and ddapccrq='"+searchday+"' and jsy is not null group by jsy)a";
        rs.execute(sql);
        if(rs.next()){
            jsrcount = rs.getInt("count");
        }
        if(jsrcount>0) {
            sheet.setColumnWidth(0, (int) ((10 + 0.72) * 256));
            for(int i=0;i<jsrcount;i++) {
                sheet.setColumnWidth(i+1, (int) ((20 + 0.72) * 256));
            }
            // 创建行
            int rowNum = 0;
            String year = searchday.substring(0, 4);
            String month = searchday.substring(5, 7);
            if ("0".equals(month.substring(0, 1))) {
                month = month.substring(1, 2);
            }
            String day = searchday.substring(8, 10);
            if ("0".equals(day.substring(0, 1))) {
                day = day.substring(1, 2);
            }
            String title = year + "年" + month + "月" + day + "日车辆使用情况";

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
            int cellnum = 0;
            HSSFRow titleRow = sheet.createRow((short) rowNum++);
            HSSFCell titleCell = titleRow.createCell((short) cellnum++);
            titleCell.setCellStyle(cellStyle);
            titleCell.setCellValue(title);
            for(int i=0;i<jsrcount;i++) {
                titleCell = titleRow.createCell((short) cellnum++);
                titleCell.setCellStyle(cellStyle);
            }



            titleRow = sheet.createRow((short) rowNum++);
            cellnum = 0;
            titleCell = titleRow.createCell((short) cellnum++);
            titleCell.setCellStyle(cellStyle2);
            titleCell.setCellValue("驾驶员");
            String jsrarr[]=new String[jsrcount];
            Map<String,Object> map = new HashMap<String,Object>();
            sql = "select jsy,(select lastname from hrmresource where id=a.jsy) as jsyname from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype=3 and ddapccrq='"+searchday+"' and jsy is not null group by jsy";
            rs.execute(sql);
            int i =0;
            while(rs.next()) {
                String jsy = Util.null2String(rs.getString("jsy"));
                String jsyname = Util.null2String(rs.getString("jsyname"));
                jsrarr[i] = jsy;
                i++;
                titleCell = titleRow.createCell((short) cellnum++);
                titleCell.setCellStyle(cellStyle2);
                titleCell.setCellValue(jsyname);
            }

            titleRow = sheet.createRow((short) rowNum++);
            cellnum = 0;
            titleCell = titleRow.createCell((short) cellnum++);
            titleCell.setCellStyle(cellStyle2);
            titleCell.setCellValue("上班时间");
            for(String jsrid:jsrarr) {
                String sj = "";
                sql = "select sbsj+'~'+xbsj as sj from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype=3 and ddapccrq='" + searchday + "' and jsy=" + jsrid + " and (sbsj is not null and sbsj <> '' )";
                rs.execute(sql);
                if (rs.next()) {
                    sj = Util.null2String(rs.getString("sj"));
                }
                titleCell = titleRow.createCell((short) cellnum++);
                titleCell.setCellStyle(cellStyle2);
                titleCell.setCellValue(sj);
            }
            int maxsize = 0;
            for(String jsrid:jsrarr){
                ArrayList<String> list = new ArrayList<String>();
                sql="select ddapccsj,ddapfhsj,ccrymd,mdd,krmd1,(select t2.selectname from workflow_billfield t, workflow_bill t1,workflow_selectitem t2 where t.billid=t1.id and t2.fieldid=t.id  and t1.tablename='formtable_main_68' and t.fieldname='ch' and t2.selectvalue=a.ch) as ch from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype=3 and ddapccrq='"+searchday+"' and jsy="+jsrid+" order by ddapccsj asc";
                rs.execute(sql);
                while(rs.next()){
                    String ccrymd = "";
                    String ch = "";
                    String ccrynames ="";
                    String krmd =  Util.null2String(rs.getString("krmd1"));
                    ccrymd =  Util.null2String(rs.getString("ccrymd"));
                    String ccrymdarr[] = ccrymd.split(",");
                    String flag = "";
                    for(String ryid:ccrymdarr){
                        String name = ResourceComInfo.getResourcename(ryid);
                        if(!"".equals(name)){
                            ccrynames = ccrynames+flag+name;
                            flag="、";
                        }
                    }
                    if("".equals(ccrynames)){
                        ccrynames = krmd;
                    }else{
                        ccrynames =ccrynames+","+krmd;
                    }
                    String ycqk = Util.null2String(rs.getString("ddapccsj"))+"出发，乘车人："+ccrynames+"，目的地:"+Util.null2String(rs.getString("mdd"))+"，"+Util.null2String(rs.getString("ddapfhsj"))+"返回";
                    ch = Util.null2String(rs.getString("ch"));

                    list.add(ch+"###"+ycqk);
                }
                if(maxsize<list.size()){
                    maxsize = list.size();
                }
                map.put(jsrid,list);
                map.put("size_"+jsrid,list.size());
            }
            for(int j =0 ;j<maxsize;j++) {
                titleRow = sheet.createRow((short) rowNum++);
                cellnum = 0;
                titleCell = titleRow.createCell((short) cellnum++);
                titleCell.setCellStyle(cellStyle2);
                titleCell.setCellValue("车辆");
                for(String jsrid:jsrarr) {
                    String cph = "";
                    if ((int) map.get("size_" + jsrid) > j) {
                        cph = ((ArrayList<String>) map.get(jsrid)).get(j).split("###")[0];
                    }
                    titleCell = titleRow.createCell((short) cellnum++);
                    titleCell.setCellStyle(cellStyle2);
                    titleCell.setCellValue(cph);
                }
                titleRow = sheet.createRow((short) rowNum++);
                cellnum = 0;
                titleCell = titleRow.createCell((short) cellnum++);
                titleCell.setCellStyle(cellStyle2);
                titleCell.setCellValue("用车情况");
                for(String jsrid:jsrarr) {
                    String ycqk = "";
                    if ((int) map.get("size_" + jsrid) > j) {
                        ycqk = ((ArrayList<String>) map.get(jsrid)).get(j).split("###")[1];
                    }
                    titleCell = titleRow.createCell((short) cellnum++);
                    titleCell.setCellStyle(cellStyle2);
                    titleCell.setCellValue(ycqk);
                }

            }





            CellRangeAddress region = new CellRangeAddress(0, 0, 0, jsrcount);
            sheet.addMergedRegion(region);

        }

        return workBook;
    }
}
