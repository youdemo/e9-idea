package wldq.workflow;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import wldq.util.TransUtil;

/**
 * 暂支单归档调用更新
 */
public class AddPersonBorrowMoney implements Action {
    @Override
    public String execute(RequestInfo info) {
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        TransUtil tu = new TransUtil();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        String sqr = "";
        String sqje = "";
        String sql = "select sqr,sqje from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            sqr = Util.null2String(rs.getString("sqr"));
            sqje = Util.null2String(rs.getString("sqje"));
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
        sql = "update cus_fielddata set field0=isnull(field0,0)+isnull("+sqje+",0) where scopeid=1 and id="+sqr;
        rs.execute(sql);

        return SUCCESS;
    }
}
