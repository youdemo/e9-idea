package gbb.workflow.action;

import com.alibaba.fastjson.JSONObject;
import gbb.workflow.util.TransUtil;
import gbb.workflow.service.ZWS_FI_GOS_VENDOR_PAYStub;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日常费用报销 凭证冲销
 */
public class CurrentExpensesReimPZ implements Action {
    @Override
    public String execute(RequestInfo info) {
        new BaseBean().writeLog("CurrentExpensesReimPZ start");
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        TransUtil tu = new TransUtil();
        String tableName = tu.getTableName(workflowid);
        int currentid = info.getRequestManager().getUserId();
        String workcode = tu.getWorkcode(currentid+"");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sf.format(new Date());
        RecordSet rs = new RecordSet();
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        String djbh = "";//单据编号
        String fkgsbm = "";//公司代码
        String cbzxbm = "";//成本中心编码
        String bxrgh = "";
        String mainid = "";
        String sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            bxrgh = tu.getWorkcode(Util.null2String(rs.getString("bxr")));
            mainid = Util.null2String(rs.getString("id"));
            djbh = Util.null2String(rs.getString("djbh"));
            fkgsbm = Util.null2String(rs.getString("fkgsbm"));
            cbzxbm = Util.null2String(rs.getString("cbzxbm"));
        }
        if(djbh.length()>13){
            djbh = djbh.substring(0,13);
        }
        cbzxbm = cbzxbm+fkgsbm;
        int num = 1;
        sql = "select * from "+tableName+"_dt5 where mainid="+mainid+" order by id asc";
        rs.execute(sql);
        while(rs.next()){
            String zy = Util.null2String(rs.getString("zy"));
            if(zy.length()>15){
                zy = zy.substring(0,15);
            }
            Map<String,String> map = new HashMap<>();
            map.put("XBLNR",djbh);//GOS单号
            map.put("XBLNR2",num+"");//GOS行号
            map.put("BUKRS",fkgsbm);//公司代码
            map.put("LIFNR",bxrgh);//供应商编码
            map.put("RACCT",Util.null2String(rs.getString("zzkmbm")));//费用科目
            map.put("KOSTL",cbzxbm);//成本中心
            map.put("PS_POSID","");//WBS编码
            map.put("KUNNR","");//中心号
            map.put("BANFN","");//采购申请号
            map.put("WAERS","CNY");//币种
            map.put("TSL",Util.null2String(rs.getString("cnhzje")));//不含税金额
            map.put("TSL2","0.00");//税额
            map.put("TAX","N");//是否抵扣
            map.put("BKTXT",zy);//描述 15
            map.put("BLDAT",nowDate);//审核日期
            map.put("USNAM",workcode);//审核人
            num++;
            list.add(map);


        }
        Map<String,String> resultMap = doService(list);
        String pzh = Util.null2String(resultMap.get("BELNR"));
        String kjnd = Util.null2String(resultMap.get("GJAHR"));
        String xxlx = Util.null2String(resultMap.get("MSGTY"));
        String xxms = Util.null2String(resultMap.get("MSGDS")).replaceAll("'","''");
        sql = "update "+tableName+" set pzh='"+pzh+"',hjnd='"+kjnd+"',xxlx='"+xxlx+"',xxms='"+xxms+"' where requestid="+requestid;
        rs.execute(sql);
        if("".equals(pzh)){
            info.getRequestManager().setMessagecontent("生成凭证失败，请联系系统管理员");
            info.getRequestManager().setMessageid("ErrorMsg:");
            return FAILURE_AND_CONTINUE;

        }
        return SUCCESS;
    }

    public  Map<String,String> doService(List<Map<String,String>> list)  {

       BaseBean log = new BaseBean();
       log.writeLog("CurrentExpensesReimPZ start");
        Map<String,String> resultMap = new HashMap<String,String>();
       try {
            String username = Util.null2o(weaver.file.Prop.getPropValue("sapmt", "username"));
            String password = Util.null2o(weaver.file.Prop.getPropValue("sapmt", "password"));
           String sysidmt = Util.null2o(weaver.file.Prop.getPropValue("sapmt", "sysid"));
            DecimalFormat df1 = new DecimalFormat("#.00");
            ZWS_FI_GOS_VENDOR_PAYStub service = new ZWS_FI_GOS_VENDOR_PAYStub();
            HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
            auth.setUsername(username);
            auth.setPassword(password);
            service._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
            ZWS_FI_GOS_VENDOR_PAYStub.Z_GOS_VENDOR_PAY zgvp = new ZWS_FI_GOS_VENDOR_PAYStub.Z_GOS_VENDOR_PAY();
            ZWS_FI_GOS_VENDOR_PAYStub.TABLE_OF_ZFIS_VENDORPAY toz = new ZWS_FI_GOS_VENDOR_PAYStub.TABLE_OF_ZFIS_VENDORPAY();
            for(Map<String,String> map :list) {
                log.writeLog("CurrentExpensesReimPZ map:"+ JSONObject.toJSONString(map).toString());
                ZWS_FI_GOS_VENDOR_PAYStub.ZFIS_VENDORPAY zv = new ZWS_FI_GOS_VENDOR_PAYStub.ZFIS_VENDORPAY();
                ZWS_FI_GOS_VENDOR_PAYStub.Char13 XBLNR = new ZWS_FI_GOS_VENDOR_PAYStub.Char13();
                XBLNR.setChar13(map.get("XBLNR"));
                zv.setXBLNR(XBLNR);//GOS单号
                ZWS_FI_GOS_VENDOR_PAYStub.Char3 XBLNR2 = new ZWS_FI_GOS_VENDOR_PAYStub.Char3();
                XBLNR2.setChar3(map.get("XBLNR2"));
                zv.setXBLNR2(XBLNR2);//GOS行号
                ZWS_FI_GOS_VENDOR_PAYStub.Char4 BUKRS = new ZWS_FI_GOS_VENDOR_PAYStub.Char4();
                BUKRS.setChar4(map.get("BUKRS"));
                zv.setBUKRS(BUKRS);//公司代码
                ZWS_FI_GOS_VENDOR_PAYStub.Char10 LIFNR = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
                LIFNR.setChar10(map.get("LIFNR"));
                zv.setLIFNR(LIFNR);//供应商编码
                ZWS_FI_GOS_VENDOR_PAYStub.Char10 RACCT = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
                RACCT.setChar10(map.get("RACCT"));
                zv.setRACCT(RACCT);//费用科目
                ZWS_FI_GOS_VENDOR_PAYStub.Char10 KOSTL = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
                KOSTL.setChar10(map.get("KOSTL"));
                zv.setKOSTL(KOSTL);//成本中心
                ZWS_FI_GOS_VENDOR_PAYStub.Char8 PS_POSID = new ZWS_FI_GOS_VENDOR_PAYStub.Char8();
                PS_POSID.setChar8(map.get("PS_POSID"));
                zv.setPS_POSID(PS_POSID);//WBS编码
                ZWS_FI_GOS_VENDOR_PAYStub.Char10 KUNNR = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
                KUNNR.setChar10(map.get("KUNNR"));
                zv.setKUNNR(KUNNR);//中心号
                ZWS_FI_GOS_VENDOR_PAYStub.Char10 BANFN = new ZWS_FI_GOS_VENDOR_PAYStub.Char10();
                BANFN.setChar10(map.get("BANFN"));
                zv.setBANFN(BANFN);//采购申请号
                ZWS_FI_GOS_VENDOR_PAYStub.Char3 WAERS = new ZWS_FI_GOS_VENDOR_PAYStub.Char3();
                WAERS.setChar3(map.get("WAERS"));
                zv.setWAERS(WAERS);//币种
                ZWS_FI_GOS_VENDOR_PAYStub.Decimal122 TSL = new ZWS_FI_GOS_VENDOR_PAYStub.Decimal122();
                TSL.setDecimal122(new BigDecimal(df1.format(Double.valueOf(map.get("TSL")))));
                zv.setTSL(TSL);//不含税金额
                ZWS_FI_GOS_VENDOR_PAYStub.Decimal122 TSL2 = new ZWS_FI_GOS_VENDOR_PAYStub.Decimal122();
                TSL2.setDecimal122(new BigDecimal(df1.format(Double.valueOf(map.get("TSL2")))));
                zv.setTSL2(TSL2);//税额
                ZWS_FI_GOS_VENDOR_PAYStub.Char1 TAX = new ZWS_FI_GOS_VENDOR_PAYStub.Char1();
                TAX.setChar1(map.get("TAX"));
                zv.setTAX(TAX);//是否抵扣
                ZWS_FI_GOS_VENDOR_PAYStub.Char30 BKTXT = new ZWS_FI_GOS_VENDOR_PAYStub.Char30();
                BKTXT.setChar30(map.get("BKTXT"));
                zv.setBKTXT(BKTXT);//描述
                ZWS_FI_GOS_VENDOR_PAYStub.Date10 BLDAT = new ZWS_FI_GOS_VENDOR_PAYStub.Date10();
                BLDAT.setDate10(map.get("BLDAT"));
                zv.setBLDAT(BLDAT);//审核日期
                ZWS_FI_GOS_VENDOR_PAYStub.Char12 USNAM = new ZWS_FI_GOS_VENDOR_PAYStub.Char12();
                USNAM.setChar12(map.get("USNAM"));
                zv.setUSNAM(USNAM);//审核人
                toz.addItem(zv);
            }
            zgvp.setIT_ITEM(toz);
            ZWS_FI_GOS_VENDOR_PAYStub.Char3 sysid = new ZWS_FI_GOS_VENDOR_PAYStub.Char3();
            sysid.setChar3(sysidmt);
            zgvp.setI_SYSID(sysid);
            ZWS_FI_GOS_VENDOR_PAYStub.TABLE_OF_ZFIS_RETURN tzr = new ZWS_FI_GOS_VENDOR_PAYStub.TABLE_OF_ZFIS_RETURN();
            zgvp.setET_RETURN(tzr);

            ZWS_FI_GOS_VENDOR_PAYStub.Z_GOS_VENDOR_PAYResponse res = service.Z_GOS_VENDOR_PAY(zgvp);

           ZWS_FI_GOS_VENDOR_PAYStub.ZFIS_RETURN zr=res.getET_RETURN().getItem()[0];
           resultMap.put("XBLNR",zr.getXBLNR().toString());
           resultMap.put("MSGTY",zr.getMSGTY().toString());
           resultMap.put("MSGDS",zr.getMSGDS().toString());
           resultMap.put("GJAHR",zr.getGJAHR().toString());
           resultMap.put("BELNR",zr.getBELNR().toString());
           log.writeLog("CurrentExpensesReimPZ result:"+ JSONObject.toJSONString(resultMap).toString());

       }catch(Exception e){
            new BaseBean().writeLog("CurrentExpensesReimPZ",e);
        }
        return resultMap;

    }
}
