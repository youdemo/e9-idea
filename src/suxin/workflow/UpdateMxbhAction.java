package suxin.workflow;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class UpdateMxbhAction implements Action {
    @Override
    public String execute(RequestInfo info) {
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        TransUtil tu = new TransUtil();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        String sfbhhb= "";//是否处理过合并
        String sql = "select sfbhhb from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            sfbhhb = Util.null2String(rs.getString("sfbhhb"));
        }
        //如果合并过 表名不是新建 无需再次合并
        if(!"1".equals(sfbhhb)){
            sql = "update "+tableName+"_dt1 set clmxbh=a.ddbh+clmxbh from "+tableName+" a where a.id="+tableName+"_dt1.mainid and a.requestid="+requestid;
            new BaseBean().writeLog(sql);
            rs.execute(sql);
            sql = "update "+tableName+" set sfbhhb='1' where requestid"+requestid;
            rs.execute(sql);
        }
        return SUCCESS;
    }
}
