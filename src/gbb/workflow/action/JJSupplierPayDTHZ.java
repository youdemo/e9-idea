package gbb.workflow.action;

import gbb.workflow.util.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 *  间接供应商付款明细金额汇总明细1
 */
public class JJSupplierPayDTHZ implements Action {
    @Override
    public String execute(RequestInfo info) {
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        TransUtil tu = new TransUtil();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        String mainid = "";
        String ze = "";//总额
        String se = "";//总额
        String bhsje = "";//总额
        String sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            mainid = Util.null2String(rs.getString("id"));
        }

        sql = "select isnull(sum(isnull(ze,0)),0) as ze,isnull(sum(isnull(se,0)),0) as se,isnull(sum(isnull(bhsje,0)),0) as bhsje from "+tableName+"_dt2 where mainid="+mainid;
        rs.execute(sql);
        if(rs.next()){
            ze = Util.null2String(rs.getString("ze"));
            se = Util.null2String(rs.getString("se"));
            bhsje = Util.null2String(rs.getString("bhsje"));
        }

        sql = "update "+tableName+"_dt1 set hsje="+ze+",se="+se+",bhsje="+bhsje+" where mainid="+mainid;
        rs.execute(sql);
        return SUCCESS;
    }
}
