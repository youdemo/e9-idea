package xhgz.report.scmx.SERVICE.IMPL;

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
import xhgz.report.scmx.CMD.WeaTableCmd;
import xhgz.report.scmx.CMD.WeatableConditonCmd;
import xhgz.report.scmx.SERVICE.XhgzScmxServcie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XhgzScmxServiceImpl extends Service implements XhgzScmxServcie {

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
        RecordSet rs_dt = new RecordSet();
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
            cell.setCellValue("编号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(1);
            cell.setCellValue("设计开发主题");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(2);
            cell.setCellValue("设计开发类别");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(3);
            cell.setCellValue("目標客戶");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(4);
            cell.setCellValue("指派研发专员");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(5);
            cell.setCellValue("接案日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(6);
            cell.setCellValue("1阶料号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("9阶料号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("试车日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("试车结果");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("试车重量（KG）");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("试车地点");
            cell.setCellStyle(sheetStyle);


            String tablenamesjkf = "formtable_main_2";//设计开发变更申请
            String tablenamebom = "formtable_main_27";//BOM
            String tablenametscqr = "formtable_main_35";//试车确认
            String tablenametlc = "formtable_main_33";//试车量产
            String jdid="";
            String sql = "select jdid from uf_report_mt where bs='SJKFBGYFZY'";
            rs.execute(sql);
            if(rs.next()){
                jdid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(jdid)){
                jdid = "0";
            }
            String cwid="";
            sql = "select jdid from uf_report_mt where bs='BOMCW'";
            rs.execute(sql);
            if(rs.next()){
                cwid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(cwid)){
                cwid = "0";
            }

            String yfzgid="";
            sql = "select jdid from uf_report_mt where bs='BOMYFZG'";
            rs.execute(sql);
            if(rs.next()){
                yfzgid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(yfzgid)){
                yfzgid = "0";
            }
            sql = "select requestid,bh,sjkfzt,(select c.selectname from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"+tablenamesjkf+"' and a.fieldname='a' and c.selectvalue=t.sjkflb) as sjkflb,mbkh,(select lastname from hrmresource where id=t.zpyfzy) as zpyfzy,jarq,yjlh,jjlh,scrq,scjg,clkg,scdd " +
                    " from (select a.requestid,a.bh,a.sjkfzt,a.a as sjkflb,a.mbkh,a.zpyfzy,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+jdid+") and requestid=a.requestid)  as jarq,(select kzlh from "+tablenamebom+"_dt1 where id=(select min(t1.id) from "+tablenamebom+"_dt1 t1 where e.id=t1.mainid )) as yjlh,(select kzlh from "+tablenamebom+"_dt1 where id=(select min(t1.id) from "+tablenamebom+"_dt3 t1 where e.id=t1.mainid )) as jjlh,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+cwid+") and requestid=c.requestid)  as scrq,c.requestid as scjg,d.clkg,d.scdd from "+tablenamesjkf+" a join workflow_requestbase b on a.requestId=b.requestid left join "+tablenamebom+" e on a.requestid=e.sjkfbgsq left join "+tablenametlc+" d on e.requestid=d.lcid left join "+tablenametscqr+" c on d.requestid=c.lcid  where b.currentnodetype=3)  t "+
                    " where 1=1 ";
            String fromdate =  Util.null2String(params.get("fromdate"));
            String lenddate =  Util.null2String(params.get("lenddate"));
            String startDate = Util.null2String(params.get("startDate"));
            if("1".equals(startDate)){
                sql += " and t.jarq = '"+ TimeUtil.getCurrentDateString()+"'";
            }else if("2".equals(startDate)){
                sql += " and t.jarq >='"+TimeUtil.getFirstDayOfWeek()+"'";
                sql += " and t.jarq <='" + TimeUtil.getLastDayOfWeek().substring(0, 10) + "'";
            }else if("3".equals(startDate)){
                sql += " and t.jarq >='"+TimeUtil.getFirstDayOfMonth()+"'";
                sql += " and t.jarq <='" + TimeUtil.getLastDayOfMonth().substring(0, 10) + "'";
            }else if("4".equals(startDate)){
                sql += " and t.jarq >='"+TimeUtil.getFirstDayOfSeason()+"'";
                sql += " and t.jarq <='" +TimeUtil.getLastDayDayOfSeason().substring(0, 10)+ "'";
            }else if("5".equals(startDate)){
                sql += " and substring(t.jarq,0,5) ='"+TimeUtil.getCurrentDateString().substring(0,4)+"'";
            }else if("6".equals(startDate)) {
                if (StringUtils.isNotBlank(fromdate) && !"null".equals(fromdate)) {
                    sql += " and t.jarq >='" + fromdate + "'";
                }
                if (StringUtils.isNotBlank(lenddate) && !"null".equals(lenddate)) {
                    sql += " and t.jarq <='" + lenddate + "'";
                }
            }
            sql = sql+" order by requestid desc";
            rs.execute(sql);
            int indexrow = 1;
            while(rs.next()){
                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("bh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(Util.null2String(rs.getString("sjkfzt")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(Util.null2String(rs.getString("sjkflb")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(Util.null2String(rs.getString("mbkh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(Util.null2String(rs.getString("zpyfzy")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(Util.null2String(rs.getString("jarq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(rs.getString("yjlh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("jjlh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("scrq")));
                cell.setCellStyle(sheetStyle2);
                String remark = "";
                String rqid = Util.null2String(rs.getString("scjg"));
                if("".equals(rqid)){
                    remark = "";
                }else{
                    String sql_dt = "select remark from workflow_requestlog where nodeid="+yfzgid+" and requestid="+rqid+" order by operatedate desc";
                    rs_dt.execute(sql_dt);
                    if(rs_dt.next()){
                        remark = removeHtmlTag(Util.null2String(rs_dt.getString("remark")));
                    }
                }

                cell=rowdt.createCell(9);
                cell.setCellValue(remark);
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("clkg")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("scdd")));
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
    private String removeHtmlTag(String content) {
        Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>");
        Matcher m = p.matcher(content);
        if (m.find()) {
            content = content
                    .replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
            content = removeHtmlTag(content);
        }
        return content;
    }
}
