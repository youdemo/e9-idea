package xhgz.report.lhbomsq.SERVICE.IMPL;

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
import xhgz.report.lhbomsq.CMD.WeaTableCmd;
import xhgz.report.lhbomsq.CMD.WeatableConditonCmd;
import xhgz.report.lhbomsq.SERVICE.XhgzLhBomServcie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;


public class XhgzLhBomServiceImpl extends Service implements XhgzLhBomServcie {

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
            cell.setCellValue("列号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(1);
            cell.setCellValue("新/旧（N/O）");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(2);
            cell.setCellValue("1阶料号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(3);
            cell.setCellValue("名称");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(4);
            cell.setCellValue("9阶料号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(5);
            cell.setCellValue("规格");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(6);
            cell.setCellValue("B比例");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("C比例");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("备注");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("申请人");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("申请日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("完成日期");
            cell.setCellStyle(sheetStyle);
//
//            cell=row0.createCell(12);
//            cell.setCellValue("设计开发/变更申请");
//            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(12);
            cell.setCellValue("研发专员");
            cell.setCellStyle(sheetStyle);

            String tablenamecpjghs = "formtable_main_27";//产品价格核算单
            String sql = "select wname,requestId,sjkfbgsq,sqrq,lh,kfbgsqyfzy,cjr,lastoperatedate,xjno1,kzlh,mc,gg,bbl,cbl,bz,bcp from (select a.requestId," +
                    "a.sjkfbgsq," +
                    "a.sqrq," +
                    "a.lh," +
                    "case when a.kfbgsqyfzy='1' then '系统管理员' else (select lastname from HrmResource where id=a.kfbgsqyfzy) end as  kfbgsqyfzy," +
                    "case when a.cjr='1' then '系统管理员' else (select lastname from HrmResource where id=a.cjr) end as  cjr," +
                    "b.lastoperatedate," +
                    "(select g.selectname  from workflow_billfield e, workflow_bill f,workflow_selectitem g where e.billid=f.id and g.fieldid=e.id  and f.tablename='formtable_main_27' and e.fieldname='xjno1' and g.selectvalue=c.xjno1 and e.detailtable ='formtable_main_27_dt1')as xjno1," +
                    "c.kzlh," +
                    "c.mc," +
                    "c.gg," +
                    "c.bbl," +
                    "c.cbl," +
                    "c.bz," +
                    "(select d.workflowname from workflow_base d where d.id=b.workflowid and b.currentnodetype=3 and a.requestId=b.requestid) as wname,"+
                    "(select top 1 d.kzlh from "+tablenamecpjghs+"_dt3 d where a.id=d.mainid order by d.id asc) as bcp" +
                    " from "+tablenamecpjghs+" a,  workflow_requestbase b,"+tablenamecpjghs+"_dt1 c" +
                    " where a.requestId=b.requestid and a.id=c.mainid and b.currentnodetype=3) t where 1=1";


            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            String startDate = Util.null2String(params.get("startDate"));
            String kzlh = Util.null2String(params.get("kzlh"));
            String lh = Util.null2String(params.get("lh"));
            if("1".equals(startDate)){
                sql += " and t.sqrq = '"+ TimeUtil.getCurrentDateString()+"'";
            }else if("2".equals(startDate)){
                sql += " and t.sqrq >='"+TimeUtil.getFirstDayOfWeek()+"'";
                sql += " and t.sqrq <='" + TimeUtil.getLastDayOfWeek().substring(0, 10) + "'";
            }else if("3".equals(startDate)){
                sql += " and t.sqrq >='"+TimeUtil.getFirstDayOfMonth()+"'";
                sql += " and t.sqrq <='" + TimeUtil.getLastDayOfMonth().substring(0, 10) + "'";
            }else if("4".equals(startDate)){
                sql += " and t.sqrq >='"+TimeUtil.getFirstDayOfSeason()+"'";
                sql += " and t.sqrq <='" +TimeUtil.getLastDayDayOfSeason().substring(0, 10)+ "'";
            }else if("5".equals(startDate)){
                sql += " and substring(t.sqrq,0,5) ='"+TimeUtil.getCurrentDateString().substring(0,4)+"'";
            }else if("6".equals(startDate)) {
                if (StringUtils.isNotBlank(fromdate) && !"null".equals(fromdate)) {
                    sql += " and t.sqrq >='" + fromdate + "'";
                }
                if (StringUtils.isNotBlank(lenddate) && !"null".equals(lenddate)) {
                    sql += " and t.sqrq <='" + lenddate + "'";
                }
            }
            if(!"".equals(kzlh)){
                sql+= " and  t.kzlh ='"+kzlh+"'";
            }
            if(!"".equals(lh)){
                sql+= " and t.lh ='"+lh+"'";
            }

            sql = sql+" order by requestId desc";
            rs.execute(sql);
            int indexrow = 1;
            while(rs.next()){
                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("lh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(Util.null2String(rs.getString("xjno1")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(Util.null2String(rs.getString("kzlh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(Util.null2String(rs.getString("mc")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(Util.null2String(rs.getString("bcp")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(Util.null2String(rs.getString("gg")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(rs.getString("bbl")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("cbl")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("bz")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("cjr")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("sqrq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("lastoperatedate")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(12);
                cell.setCellValue(Util.null2String(rs.getString("kfbgsqyfzy")));
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
