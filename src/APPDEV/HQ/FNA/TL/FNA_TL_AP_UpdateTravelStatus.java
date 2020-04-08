package APPDEV.HQ.FNA.TL;


import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
	@author:glt
	@date:2020年1月9日 上午10:41:43
	@function:更新出差流程状态
*/
public class FNA_TL_AP_UpdateTravelStatus implements Action {
	
	public String operateType;
	
	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		log.writeLog("出差流程状态更新开始==========");
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String tableName = "";
		String sql = " Select tablename From Workflow_bill Where id in ( Select formid From workflow_base Where id= " + workflowID + ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		
		sql = "update " + tableName + " set Status = '" + operateType + "' where requestid = '" + requestid + "'";
		rs1.execute(sql);
		log.writeLog("###差旅流程状态更新"+sql);

//		log.writeLog("出差流程状态更新结束==========");
		return SUCCESS;
	}
}