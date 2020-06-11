package APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.SERVICE.IMPL;

import APPDEV.HQ.Contract.ConCommonClass;
import APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD.WeaTableCmd;
import APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD.WeatableConditonCmd;
import APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.SERVICE.FlightOrderDataServcie;
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
import org.json.JSONArray;
import org.json.JSONException;
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


public class FlightOrderDataServcieImpl20200528 extends Service implements FlightOrderDataServcie {

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

            cell=row0.createCell(6);
            cell.setCellValue("入账公司");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("成本中心");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("机票(9%)");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("民航基金");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("服务费(6%)");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("总金额");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(12);
            cell.setCellValue("历史汇总金额");
            cell.setCellStyle(sheetStyle);

//            cell=row0.createCell(11);
//            cell.setCellValue("改签费");
//            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(13);
            cell.setCellValue("退票费");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(14);
            cell.setCellValue("退票状态");
            cell.setCellStyle(sheetStyle);

            String year =  Util.null2String(params.get("year"));
            String month =  Util.null2String(params.get("month"));
            if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)){
                lastmonth = year+month;
            }
            ConCommonClass com = new ConCommonClass();
            String tablesq = "formtable_main_"+com.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
            String tablebx = "formtable_main_"+com.getformodeid("FNA_TravleBX", "TravleBX_Request_Form_ID");
            String tabletp = "formtable_main_"+com.getformodeid("FNA_TravelTP", "Formid");
            String operator = user.getUID()+"";
            String corpcode = "";
            String sql = "select corpcode from uf_fna_monrecouth where operator='"+operator+"'";
            rs.execute(sql);
            if(rs.next()){
                corpcode = Util.null2String(rs.getString("corpcode"));
            }
            sql = "select requestid,xcydsy,rzgsbm,costcenter,price,tax,fwf,xcjpzje,sfbx,BtrPER,BTRBEGDA,BTRENDDA,WFStatus,REBOOKQUERYFEE,REFUND,hzje,tpstatus from ";
            sql += " (select a.requestid,a.xcydsy,a.rzgsbm,a.costcenter,a.price,a.tax,a.fwf,a.xcjpzje,a.sfbx,(select sum(nvl(amount,0)) from ctrip_SettlemenFlightOrdert where DefineFlag=a.requestid and FlightClass='N' and OrderType='1') as hzje,(select lastname from hrmresource where id=b.BtrPER)as BtrPER,b.BtrPER as ccr,b.wfstatus as clzt,BTRBEGDA,BTRENDDA,(select description from uf_wfstatus where code=b.wfstatus and status='1') as WFStatus,REBOOKQUERYFEE,REFUND,(select wfstatus from "+tabletp+" where clsqid=a.requestid and ny='"+lastmonth+"') as tpzt,(select (select description from uf_fna_wfstatus where code=wfstatus) from "+tabletp+"  where clsqid=a.requestid and ny='"+lastmonth+"') as tpstatus from ((select DefineFlag as requestid,max(JourneyReason) as xcydsy,max(COSTCENTER) as rzgsbm,max(COSTCENTER2) as costcenter,sum(nvl(price,0)) as price ,sum(nvl(tax,0)) as tax ,sum(nvl(servicefee,0)+nvl(itineraryfee,0)) as fwf,sum(nvl(amount,0)) as xcjpzje,case when (select count(1) from "+tablebx+" t,workflow_requestbase t1 where t.REQUESTID=t1.REQUESTID and t1.CURRENTNODETYPE>=1 and t.traappli=a.DefineFlag) > 0 then '是' else '否' end as sfbx,(select nvl(sum(nvl(amount,0)),0) from ctrip_SettlemenFlightOrdert where orderid in (select orderid from ctrip_SettlemenFlightOrdert where orderdetailtype='退票' and DefineFlag=a.DefineFlag and FlightClass='N' and OrderType='1' and substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"')) as REFUND,sum(nvl(REBOOKQUERYFEE,0)) as REBOOKQUERYFEE from ctrip_SettlemenFlightOrdert a where FlightClass='N' and OrderType='1' and substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"' ";
            if(!"1".equals(operator)){
                if(!"".equals(corpcode)){
                    sql += " and COSTCENTER in ("+corpcode+") ";
                }else{
                    sql += " and 1=2 ";
                }
            }
            sql +="group by DefineFlag) a left join "+tablesq+" b on a.requestid=b.requestid)) a where 1=1";


            String requestid =  Util.null2String(params.get("requestid"));
            if (StringUtils.isNotBlank(requestid)) {
                sql += " and requestid like '%" + requestid + "%' ";
            }

            //入账公司编码
            String rzgsbm =  Util.null2String(params.get("rzgsbm"));
            if (StringUtils.isNotBlank(rzgsbm)) {
                sql += " and rzgsbm in("+rzgsbm+") ";
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
            String hasrefund =  Util.null2String(params.get("hasrefund"));
            if (StringUtils.isNotBlank(hasrefund) ) {
                if("0".equals(hasrefund)){
                    sql += " and REFUND >0 ";
                }else if("1".equals(hasrefund)){
                    sql += " and REFUND <=0 ";
                }
            }
            String tpStatus =  Util.null2String(params.get("tpStatus"));
            if (StringUtils.isNotBlank(tpStatus) ) {
                sql += " and tpzt = '" + tpStatus + "' ";
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

                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(rs.getString("rzgsbm")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("costcenter")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("price")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("tax")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("fwf")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("xcjpzje")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(12);
                cell.setCellValue(Util.null2String(rs.getString("hzje")));
                cell.setCellStyle(sheetStyle2);

//                cell=rowdt.createCell(11);
//                cell.setCellValue(Util.null2String(rs.getString("REBOOKQUERYFEE")));
//                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(13);
                cell.setCellValue(Util.null2String(rs.getString("REFUND")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(14);
                cell.setCellValue(Util.null2String(rs.getString("tpstatus")));
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
        String FEE_TYPE = "A";//机票
        ConCommonClass comc = new ConCommonClass();
        String tablesq = "formtable_main_"+comc.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
        String showResult = "";
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        String sql = "select DefineFlag as ZREINR,max(COSTCENTER) as BUKRS,sum(nvl(amount,0)) as BZ_FEE,sum(nvl(nondeductibletax,0)+nvl(tax,0)) as jpws,sum(nvl(deductibletax,0)) as jpse,sum(round((nvl(servicefee,0)+nvl(itineraryfee,0))/1.06,2)) as fwws,sum((nvl(servicefee,0)+nvl(itineraryfee,0)-round((nvl(servicefee,0)+nvl(itineraryfee,0))/1.06,2))) as fwse from ctrip_SettlemenFlightOrdert a where DefineFlag in("+rqids+") and FlightClass='N' and OrderType='1' and substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"'  group by DefineFlag";
        rs.execute(sql);
        while(rs.next()){
            String jpws = Util.null2String(rs.getString("jpws"));//机票未税
            String jpse = Util.null2String(rs.getString("jpse"));//机票税额
            String fwws = Util.null2String(rs.getString("fwws"));//服务未税
            String fwse = Util.null2String(rs.getString("fwse"));//服务税额
            Map<String,String> map = new HashMap<String,String>();
            map.put("ZREINR",Util.null2String(rs.getString("ZREINR")));//差旅号
            map.put("BUKRS",Util.null2String(rs.getString("BUKRS")));//公司代码
            map.put("BZ_FEE",Util.null2String(rs.getString("BZ_FEE")));//业务货币的余额结转
            map.put("SATUS_B","1");//公司代码
            map.put("WS_FEE",jpws);//机票未税
            map.put("SE_FEE",jpse);//机票税额
            list.add(map);
            map = new HashMap<String,String>();
            map.put("ZREINR",Util.null2String(rs.getString("ZREINR")));//差旅号
            map.put("BUKRS",Util.null2String(rs.getString("BUKRS")));//公司代码
            map.put("BZ_FEE",Util.null2String(rs.getString("BZ_FEE")));//业务货币的余额结转
            map.put("SATUS_B","2");//公司代码
            map.put("WS_FEE",fwws);//机票未税
            map.put("SE_FEE",fwse);//机票税额
            list.add(map);
        }

        log.writeLog("FlightOrderDataServcieImpl sendToSap","PERNR:"+PERNR+",FEE_TYPE:"+FEE_TYPE+",lsit:"+ com.alibaba.fastjson.JSONObject.toJSONString(list).toString());
        String result = getBaseSapData(PERNR,FEE_TYPE,list,"42");
        log.writeLog("FlightOrderDataServcieImpl sendToSap","result:"+result);
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
                    sql = "insert into uf_fna_sapfkts(travelno,year,month,feeno,type) values('"+rqid+"','"+year+"','"+month+"','"+DOCNO+"','0')";
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

    @Override
    public String createTpLc(Map<String, Object> params) {
        RecordSet rs = new RecordSet();
        BaseBean log = new BaseBean();
        String rqids =  Util.null2String(params.get("selectkeys"));
        String year =  Util.null2String(params.get("year"));
        String month =  Util.null2String(params.get("month"));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        ConCommonClass com = new ConCommonClass();
        String tabletp = "formtable_main_"+com.getformodeid("FNA_TravelTP", "Formid");
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
        String hastprqids = "";
        String flag = "";
        String sql = "select distinct DefineFlag from ctrip_SettlemenFlightOrdert a where DefineFlag in("+rqids+") and a.FlightClass='N' and OrderType='1' and substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"' and (select nvl(sum(nvl(amount,0)),0) from ctrip_SettlemenFlightOrdert where orderid in (select orderid from ctrip_SettlemenFlightOrdert where orderdetailtype='退票' and DefineFlag=a.DefineFlag and FlightClass='N' and OrderType='1' and substr(AccCheckBatchNo,instr(AccCheckBatchNo,'_',-1)+1,6) ='"+lastmonth+"'))>0";
        rs.execute(sql);
        while(rs.next()){
            String DefineFlag = Util.null2String(rs.getString("DefineFlag"));
            hastprqids = hastprqids+flag+DefineFlag;
            flag = ",";
        }
        if("".equals(hastprqids)){
            return "{\"result\":\"创建失败,请选择有退票费的数据\"}";
        }
        String clsqidarr[] = hastprqids.split(",");
        int count = 0;
        sql = "select count(1) as count from "+tabletp+" where clsqid in("+hastprqids+") and ny='"+lastmonth+"'";
        rs.execute(sql);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count >= clsqidarr.length){
            return "{\"result\":\"创建失败,请选择没有创建退票流程的数据\"}";
        }
        String resultstr = "创建成功";
        for(String clsqid:clsqidarr){
            count = 0;
            sql = "select count(1) as count from "+tabletp+" where clsqid in("+clsqid+") and ny='"+lastmonth+"'";
            rs.execute(sql);
            if(rs.next()){
                count = rs.getInt("count");
            }
            if(count ==0){
                try {
                    String result = createRequest(clsqid,lastmonth);
                    if(!"S".equals(result)){
                        resultstr = "部分流程创建失败，请联系系统管理员查看问题";
                    }
                } catch (JSONException e) {
                    new BaseBean().writeLog(this.getClass().getName(),"clsqid:"+clsqid);
                    new BaseBean().writeLog(this.getClass().getName(),e);
                    resultstr = "部分流程创建失败，请联系系统管理员查看问题";
                }
            }

        }

        return "{\"result\":\""+resultstr+"\"}";
    }

    public String createRequest(String clsqid,String lastmonth) throws JSONException {
        ConCommonClass com = new ConCommonClass();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        AutoRequestService ars = new AutoRequestService();
        String nowdate = sf.format(new Date());
        RecordSet rs = new RecordSet();
        String workflowid = com.getformodeid("FNA_TravelTP", "WFID");
        String tablesq = "formtable_main_"+com.getformodeid("FNA_TravleSQ", "TravleSQ_Request_Form_ID");
        JSONObject header = new JSONObject();
        String cjr = "";
        String leader = "";
        String mainid = "";
        String sql = "select * from "+tablesq+" where requestid="+clsqid;
        rs.execute(sql);
        if(rs.next()){
            mainid = Util.null2String(rs.getString("id"));
            cjr = Util.null2String(rs.getString("BtrPER"));
            String manager3 = Util.null2String(rs.getString("manager3"));
            String manager4 = Util.null2String(rs.getString("manager4"));
            String manager5 = Util.null2String(rs.getString("manager5"));
            if(!"".equals(manager3)){
                leader = manager3.split(",")[0];
            }else if(!"".equals(manager4)){
                leader = manager4.split(",")[0];
            }else if(!"".equals(manager5)){
                leader = manager5.split(",")[0];
            }
            header.put("clsqid",clsqid);//
            header.put("ny",lastmonth);//

            header.put("HanPER",Util.null2String(rs.getString("HanPER")));//经办人
            header.put("HanJOB",Util.null2String(rs.getString("HanJOB")));//经办岗位
            header.put("HanTEL",Util.null2String(rs.getString("HanTEL")));//经办人联系方式
            header.put("HanDEP",Util.null2String(rs.getString("HanDEP")));//经办部门
            header.put("ApplyDate",nowdate);//
            header.put("BtrPER",Util.null2String(rs.getString("BtrPER")));//出差人
            header.put("BtrJOB",Util.null2String(rs.getString("BtrJOB")));//出差人岗位
            header.put("BtrTEL",Util.null2String(rs.getString("BtrTEL")));//出差人联系方式
            header.put("BtrDEP",Util.null2String(rs.getString("BtrDEP")));//出差人部门
            header.put("BTRBEGDA",Util.null2String(rs.getString("BTRBEGDA")));//差旅开始
            header.put("BTRBEGTIME",Util.null2String(rs.getString("BTRBEGTIME")));//差旅开始时间
            header.put("BTRENDDA",Util.null2String(rs.getString("BTRENDDA")));//差旅结束
            header.put("BTRENDTIME",Util.null2String(rs.getString("BTRENDTIME")));//差旅结束时间
            header.put("TavelType",Util.null2String(rs.getString("TavelType")));//差旅方案
            header.put("BTRCon",Util.null2String(rs.getString("BTRCon")));//差旅国内/外
            header.put("BTRArea",Util.null2String(rs.getString("BTRArea")));//差旅范围
            header.put("SENDCAR",Util.null2String(rs.getString("SENDCAR")));//是否全程派车
            header.put("Leader",leader);//审批人

        }

        JSONObject DETAILS = new JSONObject();
        DETAILS.put("DT1",new JSONArray());
        DETAILS.put("DT2",new JSONArray());
        DETAILS.put("DT3",new JSONArray());
        DETAILS.put("DT4",new JSONArray());
        JSONArray dt5 = new JSONArray();
        sql = "select * from "+tablesq+"_dt5 where mainid="+mainid;
        rs.execute(sql);
        while(rs.next()){
            JSONObject jo = new JSONObject();
            jo.put("AirDepCity",Util.null2String(rs.getString("AirDepCity")));//出发城市
            jo.put("AirDestCity",Util.null2String(rs.getString("AirDestCity")));//到达城市
            jo.put("AirBEGDA",Util.null2String(rs.getString("AirBEGDA")));//出发日期
            jo.put("AirBEGTI",Util.null2String(rs.getString("AirBEGTI")));//出发时间
            jo.put("AirENDDA",Util.null2String(rs.getString("AirENDDA")));//到达日期
            jo.put("AirENDTI",Util.null2String(rs.getString("AirENDTI")));//到达时间
            jo.put("AirLine",Util.null2String(rs.getString("AirLine")));//航空公司
            jo.put("AirNum",Util.null2String(rs.getString("AirNum")));//航班号
            jo.put("AirCOST",Util.null2String(rs.getString("AirCOST")));//总金额(RMB)
            jo.put("xcsljpmx",Util.null2String(rs.getString("xcsljpmx")));//携程商旅机票明细
            dt5.put(jo);
        }
        DETAILS.put("DT5",dt5);
        JSONObject json = new JSONObject();
        json.put("HEADER",header);
        json.put("DETAILS",DETAILS);
        new BaseBean().writeLog(this.getClass().getName(),"clsqid:"+clsqid+" lastmonth:"+lastmonth+" json:"+json.toString());
        String result = ars.createRequest(workflowid,json.toString(),cjr,"1");
        new BaseBean().writeLog(this.getClass().getName(),"clsqid:"+clsqid+" result:"+result);
        result = new JSONObject(result).getString("MSG_TYPE");
        return result;

    }


}
