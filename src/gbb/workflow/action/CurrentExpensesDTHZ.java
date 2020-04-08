package gbb.workflow.action;

import gbb.workflow.util.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 将明细1 科目金额数据汇总
 */
public class CurrentExpensesDTHZ implements Action {
    @Override
    public String execute(RequestInfo info) {
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        TransUtil tu = new TransUtil();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        String mainid = "";
        String sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            mainid = Util.null2String(rs.getString("id"));
        }
        sql = "delete from "+tableName+"_dt5 where mainid="+mainid;
        rs.execute(sql);
        sql = "insert into "+tableName+"_dt5(mainid,bxje,yskm,zy,fylx,yskmbm,zzkmbm,cnhzje) " +
                "select "+mainid+",bxje,yskm,case when num>1 then '' else zy end as zy,fylx,yskmbm,zzkmbm,cnhzje from ( " +
                "select max(yskm) as yskm,count(id) as num,max(yskmbm) as yskmbm,sum(bxje) as bxje,sum(cnhzje) as cnhzje,max(zy) as zy,fylx,max(kmbm) as zzkmbm  from "+tableName+"_dt1 where mainid="+mainid+" group by fylx " +
                ") a";
        rs.execute(sql);

        return SUCCESS;
    }
}
