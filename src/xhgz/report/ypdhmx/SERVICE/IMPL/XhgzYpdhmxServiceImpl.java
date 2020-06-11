package xhgz.report.ypdhmx.SERVICE.IMPL;

import com.engine.common.util.ParamUtil;
import com.engine.core.impl.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import xhgz.report.ypdhmx.CMD.WeaTableCmd;
import xhgz.report.ypdhmx.CMD.WeatableConditonCmd;
import xhgz.report.ypdhmx.SERVICE.XhgzYpdhmxServcie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;


public class XhgzYpdhmxServiceImpl extends Service implements XhgzYpdhmxServcie {

    @Override
    public Map<String, Object> weatableDemo(Map<String, Object> params) {
        return commandExecutor.execute(new WeaTableCmd(user,params));
    }

    @Override
    public Map<String, Object> weatableConditonDemo(Map<String, Object> params) {
        return commandExecutor.execute(new WeatableConditonCmd(user,params));
    }

    @Override
    public InputStream WeaReportOutExcel(HttpServletRequest request, HttpServletResponse response) {
        String userid = user.getUID()+"";
        Map<String, Object> params= ParamUtil.request2Map(request);
        RecordSet rs = new RecordSet();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setColor(HSSFFont.COLOR_NORMAL);
        HSSFCellStyle sheetStyle = wb.createCellStyle();
        sheetStyle.setAlignment(HorizontalAlignment.CENTER);
        sheetStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        sheetStyle.setFont(font);
        sheetStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        sheetStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());

        //sheetStyle.setWrapText(true);
        sheetStyle.setBorderBottom(BorderStyle.THIN);
        sheetStyle.setBorderRight(BorderStyle.THIN);
        sheetStyle.setBorderTop(BorderStyle.THIN);
        sheetStyle.setBorderLeft(BorderStyle.THIN);
        HSSFCellStyle sheetStyle2 = wb.createCellStyle();
        sheetStyle2.setAlignment(HorizontalAlignment.CENTER);
        sheetStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        sheetStyle2.setFont(font);

        sheetStyle2.setBorderBottom(BorderStyle.THIN);
        sheetStyle2.setBorderRight(BorderStyle.THIN);
        sheetStyle2.setBorderTop(BorderStyle.THIN);
        sheetStyle2.setBorderLeft(BorderStyle.THIN);

        try{
            //创建标题
            //因为这里是前端固定的表头,所以后台手动添加
            HSSFRow row0=sheet.createRow((short)0);
            cell=row0.createCell(0);
            cell.setCellValue("表单编号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(1);
            cell.setCellValue("客户名称");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(2);
            cell.setCellValue("类别");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(3);
            cell.setCellValue("业务员");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(4);
            cell.setCellValue("研发接收日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(5);
            cell.setCellValue("编号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(6);
            cell.setCellValue("品名");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("数量（KG）");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("研发专员");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("要求出货时间");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("确认出货时间");
            cell.setCellStyle(sheetStyle);



            String tablename = "formtable_main_128";//样品订货单

            String jdid="";
            String sql = "select jdid from uf_report_mt where bs='YPDHDYFJDR'";
            rs.execute(sql);
            if(rs.next()){
                jdid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(jdid)){
                jdid = "0";
            }
            sql = "select requestid,bdbh,khmc, (select  c.selectname from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"+tablename+"' and a.fieldname='lb' and c.selectvalue=t.lb) as lb,ywy,yfjsrq,bh,pm,slkg,yfjdr,yqchsj,sjchsj " +
                    " from (select a.requestid,a.bh as bdbh,khmc,lb,(select lastname from hrmresource where id=a.ywy) as ywy,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+jdid+") and requestid=a.requestid) as yfjsrq,b.bh,b.pm,b.slkg,(select lastname from hrmresource where id=a.yfjdr) as yfjdr,yqchsj,sjchsj  from "+tablename+" a,"+tablename+"_dt1 b,workflow_requestbase c where a.id=b.mainid and a.requestid = c.requestid and c.currentnodetype=3) t "+
                    " where 1=1 ";
            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            String startDate = Util.null2String(params.get("startDate"));
            if("1".equals(startDate)){
                sql += " and t.yfjsrq = '"+ TimeUtil.getCurrentDateString()+"'";
            }else if("2".equals(startDate)){
                sql += " and t.yfjsrq >='"+TimeUtil.getFirstDayOfWeek()+"'";
                sql += " and t.yfjsrq <='" + TimeUtil.getLastDayOfWeek().substring(0, 10) + "'";
            }else if("3".equals(startDate)){
                sql += " and t.yfjsrq >='"+TimeUtil.getFirstDayOfMonth()+"'";
                sql += " and t.yfjsrq <='" + TimeUtil.getLastDayOfMonth().substring(0, 10) + "'";
            }else if("4".equals(startDate)){
                sql += " and t.yfjsrq >='"+TimeUtil.getFirstDayOfSeason()+"'";
                sql += " and t.yfjsrq <='" +TimeUtil.getLastDayDayOfSeason().substring(0, 10)+ "'";
            }else if("5".equals(startDate)){
                sql += " and substring(t.yfjsrq,0,5) ='"+TimeUtil.getCurrentDateString().substring(0,4)+"'";
            }else if("6".equals(startDate)) {
                if (StringUtils.isNotBlank(fromdate) && !"null".equals(fromdate)) {
                    sql += " and t.yfjsrq >='" + fromdate + "'";
                }
                if (StringUtils.isNotBlank(lenddate) && !"null".equals(lenddate)) {
                    sql += " and t.yfjsrq <='" + lenddate + "'";
                }
            }
            sql = sql+" order by requestid desc";
            rs.execute(sql);
            int indexrow = 1;
            while(rs.next()){
                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("bdbh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(Util.null2String(rs.getString("khmc")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(Util.null2String(rs.getString("lb")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(Util.null2String(rs.getString("ywy")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(Util.null2String(rs.getString("yfjsrq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(Util.null2String(rs.getString("bh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(rs.getString("pm")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("slkg")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("yfjdr")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("yqchsj")));
                cell.setCellStyle(sheetStyle2);


                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("sjchsj")));
                cell.setCellStyle(sheetStyle2);




                indexrow++;
            }



            //将workbook转换为流的形式
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;

        }catch (Exception e){
            new BaseBean().writeLog("导出excel报错,错误信息为:"+e.getMessage());
        }


        return null;
    }
}
