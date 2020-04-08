package APPDEV.HQ.FNA.TL;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
	@author:glt
	@date:2020年1月15日 上午8:47:12
	@function: 查找20节点  30节点 审批操作者
*/
public class FNA_TL_AP_NodeOperator12Action implements Action{

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		log.writeLog("查询出差审批节点操作者开始==========");
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		String tableName = "";
		String sql = " Select tablename From Workflow_bill Where id in ( "
				+"	Select formid From workflow_base Where id= " + workflowID + ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		
		sql = "select  BtrPER,AGROUP,AAim "
		    + " from " + tableName + " where requestid = '" + requestid + "'";
		rs.execute(sql);
		if(rs.next()) {
			//
			String btrPER = Util.null2String(rs.getString("BtrPER")); // 出差人
			String aGroup = Util.null2String(rs.getString("AGROUP")); // A+组别
			String aAim = Util.null2String(rs.getString("AAim"));    // 项目
			
			String manager1 = getValStr("uf_APROJECT_GROUP",aGroup,"VALUEP");
			String manager2 = getValStr("uf_fna_aim",aAim,"AimPerson");
			
			if(("," + manager1 + ",").contains("," + btrPER + ",")) manager1 = "";
			if(("," + manager2 + ",").contains("," + btrPER + ",")) manager2 = "";
			
			sql = "update " + tableName + " set manager1 = '" + manager1 + "',manager2 = '" + manager2 + "' where requestid = " + requestid;
			rs.executeSql(sql);
		}
		log.writeLog("查询出差审批节点操作者结束==========");
		return SUCCESS;
	}
	
	/**
	 * 获取单字段信息
	 * @param tableName  需要获取字段的所属表名称
	 * @param key	唯一标示值 （这里为id）
	 * @param valueField 需要获取的值的字段标示
	 * @return
	 */
	private String getValStr(String tableName,String key,String valueField){
		RecordSet rs = new RecordSet();
		String sql = "select " + valueField + " from " +  tableName + " where id = '" + key +"'";
		rs.executeSql(sql);
		String result = "";
		if(rs.next()){
			result = Util.null2String(rs.getString(valueField));
		}
		return result;
	}

}
