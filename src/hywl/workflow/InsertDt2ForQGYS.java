package hywl.workflow;


import hywl.util.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * 将明细1数据按数量插入明细2中 类型排除低值易耗品
 * @author tangj
 *
 */
public class InsertDt2ForQGYS implements Action{
	public String execute(RequestInfo info) {
		String workflowid = info.getWorkflowid();
		String requestid = info.getRequestid();
		TransUtil tu = new TransUtil();
		String tableName = tu.getTableName(workflowid);
		RecordSet rs = new RecordSet();
		String mainid = "";
		String dtid = "";//明细1id
		double sl = 0.00;
		String sql = "select * from "+tableName+" where requestid="+requestid;
		rs.execute(sql);
		if(rs.next()) {
			mainid = Util.null2String(rs.getString("id"));
		}
		sql = "delete from "+tableName+"_dt2 where mainid="+mainid;
		rs.execute(sql);
		sql = "select id,sl from "+tableName+"_dt1 where mainid="+mainid+" and zclx in (0,1)";
		rs.execute(sql);
		while(rs.next()) {
			dtid = Util.null2String(rs.getString("id"));
			sl = rs.getDouble("sl");
			insertDt2(dtid,sl,tableName);
		}
	
		return SUCCESS;
	}
	public void insertDt2(String dt1id,double sl,String tableName) {
		RecordSet rs = new RecordSet();
		String sql = "";
		for(int i=1;i<=sl;i++) {
			sql = "insert into "+tableName+"_dt2 (mainid,zcbh,hjkm,rzrq,rzje,zcmc,ggxh,zcxz,sjpm,lrzx) select mainid,zcbh,hjkm,rzrq,rzje,zcmc,ggxh,zclx,sjpm,lrzx from "+tableName+"_dt1 where id="+dt1id;
			rs.execute(sql);
		}
	}
}
