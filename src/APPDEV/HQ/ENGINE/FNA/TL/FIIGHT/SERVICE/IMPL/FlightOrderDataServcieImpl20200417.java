package APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.SERVICE.IMPL;

import APPDEV.HQ.Contract.ConCommonClass;
import APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD.WeaTableCmd;
import APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD.WeatableConditonCmd;
import APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.SERVICE.FlightOrderDataServcie;
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
import weaver.general.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class FlightOrderDataServcieImpl20200417 extends Service implements FlightOrderDataServcie {

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
        Map<String, Object> params= ParamUtil.request2Map(request);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        String lastmonth = format.format(date);
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
            cell.setCellValue("差旅单号");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(1);
            cell.setCellValue("差旅申请");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(2);
            cell.setCellValue("携程预定事由");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(3);
            cell.setCellValue("入账公司");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(4);
            cell.setCellValue("成本中心");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(5);
            cell.setCellValue("机票总金额");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(6);
            cell.setCellValue("申请预估金额");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("是否报销");
            cell.setCellStyle(sheetStyle);
            String year =  Util.null2String(params.get("year"));
            String month =  Util.null2String(params.get("month"));
            if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)){
                lastmonth = year+month;
            }
            ConCommonClass com = new ConCommonClass();
            String tablesq = "formtable_main_"+com.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
            String tablebx = "formtable_main_"+com.getformodeid("FNA_TravleBX", "TravleBX_Request_Form_ID");

            String sql = "select requestid,requestname,xcydsy,rzgsbm,costcenter,xcjpzje,AirAmo,sfbx from (select DefineFlag as requestid,(select requestname from WORKFLOW_REQUESTBASE where requestid=a.DefineFlag) as  requestname ,max(JourneyReason) as xcydsy,max(COSTCENTER) as rzgsbm,max(COSTCENTER2) as costcenter,sum(nvl(amount,0)) as xcjpzje,(select nvl(AirAmo,0.00) as AirAmo from "+tablesq+" where requestid=a.DefineFlag) as AirAmo,case when (select count(1) from "+tablebx+" t,workflow_requestbase t1 where t.REQUESTID=t1.REQUESTID and t1.CURRENTNODETYPE>=1 and t.traappli=a.DefineFlag) > 0 then '是' else '否' end as sfbx,AccCheckBatchNo from ctrip_SettlemenFlightOrdert a where substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"' group by DefineFlag) a where 1=1 ";

            String requestid =  Util.null2String(params.get("requestid"));
            if (StringUtils.isNotBlank(requestid)) {
                sql += " and requestid like '%" + requestid + "%' ";
            }

            //入账公司编码
            String rzgsbm =  Util.null2String(params.get("rzgsbm"));
            if (StringUtils.isNotBlank(rzgsbm)) {
                sql += " and rzgsbm like '%" + rzgsbm + "%' ";
            }

            //成本中心
            String costcenter =  Util.null2String(params.get("costcenter"));
            if (StringUtils.isNotBlank(costcenter)) {
                sql += " and costcenter like '%" + costcenter + "%' ";
            }

            //是否报销
            String sfbx =  Util.null2String(params.get("sfbx"));
            if (StringUtils.isNotBlank(sfbx) ) {
                sql += " and sfbx = '" + sfbx + "' ";
            }
            rs.execute(sql);
            int indexrow = 1;
            while(rs.next()){
                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("requestid")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(Util.null2String(rs.getString("requestname")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(Util.null2String(rs.getString("xcydsy")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(Util.null2String(rs.getString("rzgsbm")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(Util.null2String(rs.getString("costcenter")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(Util.null2String(rs.getString("xcjpzje")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(rs.getString("AirAmo")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("sfbx")));
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

    @Override
    public String sendToSap(Map<String, Object> params) {
        return null;
    }

    @Override
    public String createTpLc(Map<String, Object> params) {
        return null;
    }
}
