package wldq.workflow;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import wldq.util.TransUtil;

/**
 * 还款单归档调用更新
 */
public class CutPersonBorrowMoney implements Action {
    @Override
    public String execute(RequestInfo info) {
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        TransUtil tu = new TransUtil();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        String sqr = "";
        String jehj = "";
        String sql = "select sqr,jehj from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            sqr = Util.null2String(rs.getString("sqr"));
            jehj = Util.null2String(rs.getString("jehj"));
        }
        int count =0;
        sql = "select count(1) as count from cus_fielddata where scopeid=1 and id="+sqr;
        rs.execute(sql);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count<=0){
           rs.execute("insert into cus_fielddata(scope,scopeid,id) values('HrmCustomFieldByInfoType','1','"+sqr+"')");
        }
        sql = "update cus_fielddata set field0=isnull(field0,0)-isnull("+jehj+",0) where scopeid=1 and id="+sqr;
        rs.execute(sql);

        return SUCCESS;
    }
}
