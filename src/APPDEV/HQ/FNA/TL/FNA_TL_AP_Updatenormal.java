package APPDEV.HQ.FNA.TL;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
	@author:glt
	@date:2020年1月9日 上午11:52:55
	@function: 差旅流程归档状态更新
*/
public class FNA_TL_AP_Updatenormal implements Action {

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		log.writeLog("出差流程状态更新开始==========");
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String tableName = "";
		String sql = " Select tablename From Workflow_bill Where id in ( Select formid From workflow_base Where id= " + workflowID+")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		
		sql = "select ApplyType,TRAREQ from "+tableName+" main where requestid = '"+requestid+"'";
		rs.execute(sql);
		if(rs.next()) {
			
			String ApplyType = Util.null2String(rs.getString("ApplyType"));//申请类型
			String TRAREQ = Util.null2String(rs.getString("TRAREQ"));//原差旅申请流程
			
			if ("0".equals(ApplyType)) { //新建
				sql = "update "+tableName+" set Status = '0' where requestid = '"+requestid+"'";
				rs1.execute(sql);
				log.writeLog("###差旅流程状态更新"+sql);
			}
			
			if("1".equals(ApplyType)) {//修改 
				sql = "update "+tableName+" set Status = '2' where requestid = '"+TRAREQ+"'";
				rs1.execute(sql);
				log.writeLog("###差旅流程状态更新"+sql);
				
				sql = "update "+tableName+" set Status = '0' where requestid = '"+requestid+"'";
				rs1.execute(sql);
				log.writeLog("###差旅流程状态"+sql);
			
			}
			
			if("2".equals(ApplyType)) {//取消
				sql = "update "+tableName+" set Status = '3' where requestid = '"+TRAREQ+"'";
				rs1.execute(sql);
				log.writeLog("###差旅流程状态更新"+sql);
			}

		}
		return SUCCESS;
	}

}
