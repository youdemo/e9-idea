package APPDEV.HQ.FNA.TL;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
	@author:glt
	@date:2020年1月15日 上午8:47:12
	@function: 查找 40节点  50节点 审批操作者
*/
public class FNA_TL_AP_FinalNodeOperator implements Action{

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		log.writeLog("查询出差审批节点操作者开始==========");
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
		
		sql = "select BTRBEGDA,BTRENDDA,to_date(BTRENDDA,'yyyy-mm-dd')-to_date(BTRBEGDA,'yyyy-mm-dd')+1 Tripdays,BTRCon,BtrLevel, "
		    +" (select managerid from hrmresource where id = "+tableName+".BtrPER) managerid "
		    + " from "+tableName+" where requestid = '"+requestid+"'";
		rs.execute(sql);
		if(rs.next()) {
			String btrPER = Util.null2String(rs.getString("BtrPER"));//出差人 
			String bTRBEGDA = Util.null2String(rs.getString("BTRBEGDA"));//出差开始时间 
			String bTRENDDA = Util.null2String(rs.getString("BTRENDDA"));//出差结束时间 
			int tripDays = rs.getInt("Tripdays");//出差天数 
			String bTRCon = Util.null2String(rs.getString("BTRCon"));//差旅国内外  
			int btrLevel = rs.getInt("BtrLevel");//职级
			String managerid = Util.null2String(rs.getString("managerid"));//出差人上级主管
			
			//特殊人签核1
			sql = "select * from uf_fna_SpeAppro1 where BtrPER = '"+btrPER+"' and ";
			rs1.execute(sql);
			
			
		
			//特殊签核4
			String signature4 = "";
			sql = "select  spr  from uf_fna_SpeAppro4 where BtrPER = '"+btrPER+"'";
			rs1.execute(sql);
			if(rs1.next()) {
				signature4 = Util.null2String(rs1.getString("spr"));//签核人
			
			}
			
			/*
			 * if() {
			 * 
			 * }
			 */
			//特殊人签核1
			sql = "select * from uf_fna_SpeAppro1 where BtrPER = '"+btrPER+"' and ";
			rs1.execute(sql);
			
			//一般人员
			if("0".equals(bTRCon)) { //国内
				if(tripDays <= 14 ) {//小于14个自然日 
					
					//40审批
					sql = "update "+tableName+" set a = '"+managerid+"'";
					rs1.execute(sql);
					log.writeLog("###国内出差小于14个工作日："+sql);
					
				
				}else if (tripDays > 14 ){//大于于14个自然日 
					
					//40审批
					String isExit = "0";
					sql = " select count(*) isExit  from uf_fna_TOP200 where a in ("+btrPER+") ";
					rs1.execute(sql);
					if(rs1.next()) {
						isExit = Util.null2String(rs1.getString("isExit"));//职级  
					}
					
					if(btrLevel>= 12 || !"0".equals(isExit)) {//不在top 200内  职级 大于 12
						sql = "";
					}
					
					//50审批
					String operatorStr = "";
					sql = " select  resourceid  from hrmrolemembers  where roleid = '3486'";
					rs1.execute(sql);
					while(rs1.next()) {
						String resourceid = Util.null2String(rs1.getString("resourceid"));//职级  
					}
					
					
					
				
				}	
			}else if ("1".equals(bTRCon)) {//国际
				//if() {
					
				//}
			}
		}
		
		
		
		log.writeLog("查询出差审批节点操作者结束==========");
		return null;
	}
	
	
	
	//获取关系审批 最高获取到审批人（N-1）
	public String getApproverOperator(String userid) {
		RecordSet rs = new RecordSet();
		String managerStr = "";
		String managerid = "";
		
		while(!"".equals(managerid)) {
			String sql = "select managerid from hrmresource where id = '"+userid+"'";
			rs.execute(sql);
			while(rs.next()) {
				managerid = Util.null2String(rs.getString("managerid"))+",";//审批人直接上级
			}
			managerStr = managerid+",";
		}
		
		
		return managerStr;	
	}
	
	
	//返回出差天数
	public String getTravelDays(int travelDays,String id) {
		String travelideneit = "";
		RecordSet rs = new RecordSet();
		String isTrue = "0";
		String sql = "select id from uf_fna_BTRDAYS where id = '"+id+"' and BTRBEGDAYS <= '"+travelDays+"'  and  BTRENDDAYS >= '"+travelDays+"'";
		rs.execute(sql);
		if(rs.next()) {
			isTrue = "1";
		}
		return isTrue;
	}

}



















