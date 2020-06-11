package APPDEV.HQ.ENGINE.FNA.TL.HOTELGW.SERVICE.IMPL;

import APPDEV.HQ.Contract.ConCommonClass;
import APPDEV.HQ.ENGINE.FNA.TL.HOTELGW.CMD.WeaTableCmd;
import APPDEV.HQ.ENGINE.FNA.TL.HOTELGW.CMD.WeatableConditonCmd;
import APPDEV.HQ.ENGINE.FNA.TL.HOTELGW.SERVICE.HotelOrderDataGWServcie;
import APPDEV.HQ.UTIL.BringMainAndDetailByMain;
import APPDEV.HQ.UTIL.GetMachineUtil;
import com.engine.common.util.ParamUtil;
import com.engine.core.impl.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


public class HotelOrderDataGWServcieImpl extends Service implements HotelOrderDataGWServcie {

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
            cell.setCellValue("出差人");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(2);
            cell.setCellValue("差旅开始");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(3);
            cell.setCellValue("差旅结束");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(4);
            cell.setCellValue("差旅状态");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(5);
            cell.setCellValue("是否报销");
            cell.setCellStyle(sheetStyle);

//            cell=row0.createCell(6);
//            cell.setCellValue("携程预定事由");
//            cell.setCellStyle(sheetStyle);

            //            cell=row0.createCell(6);
//            cell.setCellValue("入账公司");
//            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(6);
            cell.setCellValue("成本中心");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("酒店总金额");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(8);
            cell.setCellValue("累计金额");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(9);
            cell.setCellValue("个人支付");
            cell.setCellStyle(sheetStyle);

            String year =  Util.null2String(params.get("year"));
            String month =  Util.null2String(params.get("month"));
            if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)){
                lastmonth = year+month;
            }
            ConCommonClass com = new ConCommonClass();
            String tablesq = "formtable_main_"+com.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
            String tablebx = "formtable_main_"+com.getformodeid("FNA_TravleBX", "TravleBX_Request_Form_ID");
            String operator = user.getUID()+"";
            String corpcode = "";
            String sql = "select corpcode from uf_fna_monrecouth where operator='"+operator+"'";
            rs.execute(sql);
            if(rs.next()){
                corpcode = Util.null2String(rs.getString("corpcode"));
            }
            sql = "select requestid,xcydsy,rzgsbm,costcenter,xcjpzje,sfbx,BtrPER,BTRBEGDA,BTRENDDA,WFStatus,hzje,grzfje from ";
            sql += " (select a.requestid,a.xcydsy,a.rzgsbm,a.costcenter,a.xcjpzje,a.sfbx,(select sum(nvl(amount,0)) from CTRIP_SETTLEMENHOTELORDERT where DEFINETITLECONTENT=a.requestid and IsDomestic='false' and paytype='Y') as hzje,(select lastname from hrmresource where id=b.BtrPER)as BtrPER,b.BtrPER as ccr,b.wfstatus as clzt,BTRBEGDA,BTRENDDA,(select description from uf_wfstatus where code=b.wfstatus and status='1') as WFStatus,(select nvl(sum(nvl(t1.HOCOSTRMB,0)),0) as grzfje from "+tablebx+" t,"+tablebx+"_dt5 t1 where t.id=t1.mainid and t.traappli=a.requestid) as grzfje from ((select DEFINETITLECONTENT as requestid ,max(JourneyReason) as xcydsy,max(COSTCENTER) as rzgsbm,max(COSTCENTER2) as costcenter,sum(nvl(amount,0)) as xcjpzje,case when (select count(1) from "+tablebx+" t,workflow_requestbase t1 where t.REQUESTID=t1.REQUESTID and t1.CURRENTNODETYPE>=1 and t.traappli=a.DEFINETITLECONTENT) > 0 then '是' else '否' end as sfbx from CTRIP_SETTLEMENHOTELORDERT a where  IsDomestic='false' and paytype='Y' and substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"' ";
            if(!"1".equals(operator)){
                if(!"".equals(corpcode)){
                    sql += " and COSTCENTER in ("+corpcode+") ";
                }else{
                    sql += " and 1=2 ";
                }
            }
            sql +="group by DEFINETITLECONTENT) a left join "+tablesq+" b on a.requestid=b.requestid)) a where 1=1 ";


            String requestid =  Util.null2String(params.get("requestid"));
            if (StringUtils.isNotBlank(requestid)) {
                sql += " and requestid like '%" + requestid + "%' ";
            }

            //入账公司编码
            String rzgsbm =  Util.null2String(params.get("rzgsbm"));
            if (StringUtils.isNotBlank(rzgsbm)) {
                sql += " and rzgsbm in("+rzgsbm+")";
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

            String ccr =  Util.null2String(params.get("ccr"));
            if (StringUtils.isNotBlank(ccr) ) {
                sql += " and ccr = '" + ccr + "' ";
            }

            String wfstatus =  Util.null2String(params.get("WFStatus"));
            if (StringUtils.isNotBlank(wfstatus) ) {
                sql += " and clzt = '" + wfstatus + "' ";
            }
            rs.execute(sql);
            int indexrow = 1;
            while(rs.next()){
                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("requestid")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(Util.null2String(rs.getString("BtrPER")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(Util.null2String(rs.getString("BTRBEGDA")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(Util.null2String(rs.getString("BTRENDDA")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(Util.null2String(rs.getString("WFStatus")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(Util.null2String(rs.getString("sfbx")));
                cell.setCellStyle(sheetStyle2);

//                cell=rowdt.createCell(6);
//                cell.setCellValue(Util.null2String(rs.getString("xcydsy")));
//                cell.setCellStyle(sheetStyle2);

//                cell=rowdt.createCell(6);
//                cell.setCellValue(Util.null2String(rs.getString("rzgsbm")));
//                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(rs.getString("costcenter")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("xcjpzje")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("hzje")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("grzfje")));
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
        RecordSet rs = new RecordSet();
        BaseBean log = new BaseBean();
        String rqids =  Util.null2String(params.get("selectkeys"));
        String year =  Util.null2String(params.get("year"));
        String month =  Util.null2String(params.get("month"));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        String lastmonth = format.format(date);
        if("".equals(year)&&"".equals(month)){
            year= lastmonth.substring(0,4);
            month = lastmonth.substring(4);
        }else{
            lastmonth = year+month;
        }
        String PERNR = user.getUID()+"";
        String FEE_TYPE = "D";//酒店
        ConCommonClass comc = new ConCommonClass();
        String tablesq = "formtable_main_"+comc.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
        String showResult = "";
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        String sql ="select DEFINETITLECONTENT as ZREINR,max(COSTCENTER) as BUKRS,sum(nvl(amount,0)) as BZ_FEE from CTRIP_SETTLEMENHOTELORDERT a where DEFINETITLECONTENT in("+rqids+") and IsDomestic='false' and paytype='Y' and substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"' group by DEFINETITLECONTENT";
        rs.execute(sql);
        while(rs.next()){
            Map<String,String> map = new HashMap<String,String>();
            map.put("ZREINR",Util.null2String(rs.getString("ZREINR")));//差旅号
            map.put("BUKRS",Util.null2String(rs.getString("BUKRS")));//公司代码
            map.put("BZ_FEE",Util.null2String(rs.getString("BZ_FEE")));//业务货币的余额结转
            list.add(map);
        }

        log.writeLog("HotelOrderDataServcieImpl sendToSap","PERNR:"+PERNR+",FEE_TYPE:"+FEE_TYPE+",lsit:"+ com.alibaba.fastjson.JSONObject.toJSONString(list).toString());
        String result = getBaseSapData(PERNR,FEE_TYPE,list,"42");
        log.writeLog("HotelOrderDataServcieImpl sendToSap","result:"+result);
        try {
            String DOCNO = "";//费用报支单号
            String STATU = "";//Y 导入成功 N 不成功
            String STR_MSG = "";//消息

            JSONObject json = new JSONObject(result);
            JSONObject data = json.getJSONObject("type");
            DOCNO = data.getString("DOCNO");
            STATU = data.getString("STATU");
            STR_MSG = data.getString("STR_MSG");

            if("Y".equals(STATU)){
                showResult="提交成功";
                String rqidarr[]=rqids.split(",");
                for(String rqid:rqidarr){
                    sql = "insert into uf_fna_sapfkts(travelno,year,month,feeno,type) values('"+rqid+"','"+year+"','"+month+"','"+DOCNO+"','3')";
                    rs.execute(sql);
                }
            }else if("N".equals(STATU)){
                showResult=STR_MSG;
            }else{
                showResult = "提交失败";
            }

        } catch (Exception e) {
            log.writeLog(e);
            log.writeLog("解析json格式异常：" + result);
            showResult = "提交失败";

        }
        return "{\"result\":\""+showResult+"\"}";
    }

    public String getBaseSapData(String PERNR, String FEE_TYPE,List<Map<String,String>> list, String workflowId) {
        Map<String, String> oaDatas = new HashMap<String, String>();
        oaDatas.put("PERNR", PERNR);
        oaDatas.put("FEE_TYPE", FEE_TYPE);
        String dataSourceId = "";
        String machine = GetMachineUtil.getMachine();
        if ("PRO".equals(machine)) {//正式
            dataSourceId = "41";
        }else {//96
            dataSourceId = "141";
        }
        BringMainAndDetailByMain bmb = new BringMainAndDetailByMain(dataSourceId);//正式 41 测试141
        String result = bmb.getReturn(oaDatas, workflowId, "TRIPFEE_I", list);
        return result;
    }
}
