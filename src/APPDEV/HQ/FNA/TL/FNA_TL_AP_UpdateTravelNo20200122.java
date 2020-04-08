package APPDEV.HQ.FNA.TL;

import APPDEV.HQ.FNA.UTIL.SeqUtil;
import APPDEV.HQ.FNA.UTIL.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class FNA_TL_AP_UpdateTravelNo20200122 implements Action {
    @Override
    public String execute(RequestInfo info) {
        TransUtil tu = new TransUtil();
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        SeqUtil  su = new SeqUtil();
        String TRANUM = "";
        String ApplyType = "";
        String WFStatus = "";
        String mainId = "";
        String TravelType = "";
        String flag = "";
        String  sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            TRANUM = Util.null2String(rs.getString("TRANUM"));
            ApplyType = Util.null2String(rs.getString("ApplyType"));
            mainId = Util.null2String(rs.getString("id"));

        }
        if("".equals(TRANUM) && "0".equals(ApplyType)){
            TRANUM = su.getTLAPSeq();
        }
        if("0".equals(ApplyType)){
            WFStatus = "C";
        }else if("1".equals(ApplyType)){
            WFStatus = "Y";
        }else if("2".equals(ApplyType)){
            WFStatus = "J";
        }

        sql = "select distinct TavelType from "+tableName+"_dt1 where mainid="+mainId;
        rs.execute(sql);
        while(rs.next()){
            TravelType = TravelType+flag+Util.null2String(rs.getString("TavelType"));
            flag = ",";
        }

        sql = "update "+tableName+" set TRANUM='"+TRANUM+"',RQID='"+requestid+"',WFStatus='"+WFStatus+"',TavelType='"+TravelType+"' where requestid="+requestid;
        rs.execute(sql);
        return SUCCESS;
    }
}
