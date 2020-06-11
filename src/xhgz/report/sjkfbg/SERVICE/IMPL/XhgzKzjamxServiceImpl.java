package xhgz.report.sjkfbg.SERVICE.IMPL;

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
import xhgz.report.sjkfbg.CMD.WeaTableCmd;
import xhgz.report.sjkfbg.CMD.WeatableConditonCmd;
import xhgz.report.sjkfbg.SERVICE.XhgzKzjamxServcie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;


public class XhgzKzjamxServiceImpl extends Service implements XhgzKzjamxServcie {

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
            cell.setCellValue("需求完成日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("预估完成日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("送样日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("送样次数");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("是否完成（Y/N）");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("预计反馈日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(12);
            cell.setCellValue("样品编号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(13);
            cell.setCellValue("实际反馈日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(14);
            cell.setCellValue("反馈结果");
            cell.setCellStyle(sheetStyle);

            String tablenamesjkfbg = "formtable_main_2";//设计开发/变更申请单
            String tablenameyppc = "formtable_main_23";//样品品尝及送样记录表
            String tablenameypfk = "formtable_main_24";//样品反馈信息表
            String jdid="";
            String sql = "select jdid from uf_report_mt where bs='SJKFBGYFZY'";
            rs.execute(sql);
            if(rs.next()){
                jdid = Util.null2String(rs.getString("jdid"));
            }
            if("".equals(jdid)){
                jdid = "0";
            }
            sql = "select syjlid,requestid,bh,sjkfzt,(select c.selectname from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"+tablenamesjkfbg+"' and a.fieldname='a' and c.selectvalue=t.sjkflb) as sjkflb,mbkh,(select lastname from hrmresource where id=t.zpyfzy)zpyfzy,jarq,xqwcrq,yjwcrq,syrq,sycs,sfwc,yjfkrq,ypbh,sjfkrq, (select c.selectname from workflow_billfield a, workflow_bill b,workflow_selectitem c where a.billid=b.id and c.fieldid=a.id  and b.tablename='"+tablenameypfk+"' and a.fieldname='fkjg' and c.selectvalue=t.fkjg) as fkjg " +
                    " from (select c.requestid as syjlid,a.requestid,a.bh,a.sjkfzt,a.a as sjkflb,a.mbkh,a.zpyfzy,(select isnull(max(operatedate),'') from workflow_requestlog where nodeid in("+jdid+") and requestid=a.requestid) as jarq,a.xqwcrq,a.yjwcrq,(select lastoperatedate from workflow_requestbase where requestid=c.requestId and currentnodetype=3) as syrq,c.sycs,(select case when currentnodetype=3 then 'Y' else 'N' end from workflow_requestbase where requestid=c.requestId) as sfwc,c.yjfkrq,c.ypbh,(select createdate from workflow_requestbase where requestid=d.requestid) as sjfkrq, fkjg from "+tablenamesjkfbg+" a join workflow_requestbase b on a.requestId=b.requestid left join "+tablenameyppc+" c on a.sjkfzt = c.ypmc left join "+tablenameypfk+" d on c.requestid = d.lcid where b.currentnodetype=3) t" +
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
            sql = sql+" order by requestid desc,syjlid asc";
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
                cell.setCellValue(Util.null2String(rs.getString("xqwcrq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("yjwcrq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("syrq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("sycs")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("sfwc")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("yjfkrq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(12);
                cell.setCellValue(Util.null2String(rs.getString("ypbh")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(13);
                cell.setCellValue(Util.null2String(rs.getString("sjfkrq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(14);
                cell.setCellValue(Util.null2String(rs.getString("fkjg")));
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
