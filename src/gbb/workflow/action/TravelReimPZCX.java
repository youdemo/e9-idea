package gbb.workflow.action;

import com.alibaba.fastjson.JSONObject;
import gbb.workflow.service.ZWS_FI_GOS_DOC_REStub;
import gbb.workflow.util.TransUtil;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 差旅费用报销流程 凭证冲销接口
 */
public class TravelReimPZCX implements Action {
    @Override
    public String execute(RequestInfo info) {
        BaseBean log = new BaseBean();
        log.writeLog("CurrentExpensesReimPZCX start");
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        TransUtil tu = new TransUtil();
        String tableName = tu.getTableName(workflowid);
        int currentid = info.getRequestManager().getUserId();
        String workcode = tu.getWorkcode(currentid+"");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sf.format(new Date());
        RecordSet rs = new RecordSet();
        String djbh = "";
        String gsdm = "";
        String hjnd = "";
        String pzh = "";
        String cxyy = "";
        String sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            djbh = Util.null2String(rs.getString("djbh"));
            gsdm = Util.null2String(rs.getString("fkgsbm"));
            pzh = Util.null2String(rs.getString("pzh"));
            hjnd = Util.null2String(rs.getString("hjnd"));
            cxyy = Util.null2String(rs.getString("cxyy"));
        }
        String xxlx = "";
        String xxms = "";
        String cxpzh = "";
        try {
            log.writeLog("djbh:"+djbh+",gsdm:"+gsdm+",hjnd:"+hjnd+",pzh:"+pzh+",nowDate:"+nowDate+",cxyy:"+cxyy);
            Map<String,String> resultMap = doService(djbh,gsdm,hjnd,pzh,nowDate,workcode,cxyy);
            xxlx = Util.null2String(resultMap.get("MSGTY"));
            xxms = Util.null2String(resultMap.get("MSGDS")).replaceAll("'","''");
            cxpzh = Util.null2String(resultMap.get("BELNR"));

        } catch (Exception e) {
            log.writeLog(e);
            xxlx = "E";
        }
        sql = "update "+tableName+" set xxlx='"+xxlx+"',xxms='"+xxms+"',cxpzh='"+cxpzh+"' where requestid="+requestid;
        rs.execute(sql);
        if(!"S".equals(xxlx)){
            info.getRequestManager().setMessagecontent("冲销凭证失败，请联系系统管理员");
            info.getRequestManager().setMessageid("ErrorMsg:");
            return FAILURE_AND_CONTINUE;
        }


        return SUCCESS;
    }

    /**
     *
     * @param djbh GOS单号
     * @param gsdm 公司代码
     * @param hjnd 会计年度
     * @param pzh 凭证号
     * @param shrq 冲销审核日期
     * @return
     * @throws Exception
     */
    public Map<String,String> doService(String djbh, String gsdm, String hjnd, String pzh, String shrq,String workcode,String cxyy) throws Exception {
        BaseBean log = new BaseBean();
        Map<String,String> resultMap = new HashMap<String,String>();
        String username = Util.null2o(weaver.file.Prop.getPropValue("sapmt", "username"));
        String password = Util.null2o(weaver.file.Prop.getPropValue("sapmt", "password"));
        String sysidmt = Util.null2o(weaver.file.Prop.getPropValue("sapmt", "sysid"));
        ZWS_FI_GOS_DOC_REStub service = new ZWS_FI_GOS_DOC_REStub();
        HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
        auth.setUsername(username);
        auth.setPassword(password);
        service._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
        ZWS_FI_GOS_DOC_REStub.Z_GOS_DOC_RE zgdr = new ZWS_FI_GOS_DOC_REStub.Z_GOS_DOC_RE();
        ZWS_FI_GOS_DOC_REStub.TABLE_OF_ZFIS_DOC_RE tozdr = new  ZWS_FI_GOS_DOC_REStub.TABLE_OF_ZFIS_DOC_RE();
        ZWS_FI_GOS_DOC_REStub.ZFIS_DOC_RE zdr = new ZWS_FI_GOS_DOC_REStub.ZFIS_DOC_RE();
        ZWS_FI_GOS_DOC_REStub.Char13 XBLNR = new ZWS_FI_GOS_DOC_REStub.Char13();
        XBLNR.setChar13(djbh);
        zdr.setXBLNR(XBLNR);//GOS单号
        ZWS_FI_GOS_DOC_REStub.Char3 XBLNR2 = new ZWS_FI_GOS_DOC_REStub.Char3();
        XBLNR2.setChar3("1");
        zdr.setXBLNR2(XBLNR2);
        ZWS_FI_GOS_DOC_REStub.Char4 BUKRS = new ZWS_FI_GOS_DOC_REStub.Char4();
        BUKRS.setChar4(gsdm);
        zdr.setBUKRS(BUKRS);//公司代码
        ZWS_FI_GOS_DOC_REStub.Char4 GJAHR = new ZWS_FI_GOS_DOC_REStub.Char4();
        GJAHR.setChar4(hjnd);
        zdr.setGJAHR(GJAHR);//会计年度
        ZWS_FI_GOS_DOC_REStub.Char10 BELNR = new ZWS_FI_GOS_DOC_REStub.Char10();
        BELNR.setChar10(pzh);
        zdr.setBELNR(BELNR);//凭证号
        ZWS_FI_GOS_DOC_REStub.Date10 BLDA = new ZWS_FI_GOS_DOC_REStub.Date10();
        BLDA.setDate10(shrq);
        zdr.setBLDAT(BLDA);//冲销审核日期
        ZWS_FI_GOS_DOC_REStub.Char12 ZUSNAM = new ZWS_FI_GOS_DOC_REStub.Char12();
        ZUSNAM.setChar12(workcode);
        zdr.setZUSNAM(ZUSNAM);
        ZWS_FI_GOS_DOC_REStub.Char25 ZBKTXT = new ZWS_FI_GOS_DOC_REStub.Char25();
        ZBKTXT.setChar25(cxyy);
        zdr.setZBKTXT(ZBKTXT);
        tozdr.addItem(zdr);
        zgdr.setIT_ITEM(tozdr);
        ZWS_FI_GOS_DOC_REStub.TABLE_OF_ZFIS_RETURN_RE rr = new ZWS_FI_GOS_DOC_REStub.TABLE_OF_ZFIS_RETURN_RE();
        zgdr.setET_RETURN(rr);
        ZWS_FI_GOS_DOC_REStub.Char3 sysid = new ZWS_FI_GOS_DOC_REStub.Char3();
        sysid.setChar3(sysidmt);
        zgdr.setI_SYSID(sysid);
        ZWS_FI_GOS_DOC_REStub.Z_GOS_DOC_REResponse response = service.Z_GOS_DOC_RE(zgdr);
        ZWS_FI_GOS_DOC_REStub.ZFIS_RETURN_RE result = response.getET_RETURN().getItem()[0];
        resultMap.put("XBLNR",result.getXBLNR().toString());
        resultMap.put("MSGTY",result.getMSGTY().toString());
        resultMap.put("MSGDS",result.getMSGDS().toString());
        resultMap.put("BELNR",result.getBELNR().toString());
        log.writeLog("CurrentExpensesReimPZ result:"+ JSONObject.toJSONString(resultMap).toString());

        return resultMap;

    }


}
