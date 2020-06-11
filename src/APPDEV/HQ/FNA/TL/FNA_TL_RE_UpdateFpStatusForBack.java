package APPDEV.HQ.FNA.TL;

import APPDEV.HQ.FNA.UTIL.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * createby jianyong.tang
 * createTime 2020/3/2 16:46
 * version v1
 * desc 退回时更新发票状态
 */
public class FNA_TL_RE_UpdateFpStatusForBack implements Action {
    @Override
    public String execute(RequestInfo info) {
        TransUtil tu = new TransUtil();
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        String mainId = "";
        String  sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            mainId = Util.null2String(rs.getString("id"));
        }
        //交通
        rs.execute("update uf_jyb set zt='0' where fp in(select Selinv from "+tableName+"_dt2 where mainid="+mainId+" and SELINV is not null ) and fp<>'{fpid}'");
        //其他
        rs.execute("update uf_jyb set zt='0' where fp in(select selinv from "+tableName+"_dt3 where mainid="+mainId+" and selinv is not null ) and fp<>'{fpid}'");
        //住宿
        rs.execute("update uf_jyb set zt='0' where fp in(select selinv from "+tableName+"_dt5 where mainid="+mainId+" and selinv is not null ) and fp<>'{fpid}'");

        return SUCCESS;
    }
}
