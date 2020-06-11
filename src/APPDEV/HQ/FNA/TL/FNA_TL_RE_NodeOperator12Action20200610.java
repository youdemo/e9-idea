package APPDEV.HQ.FNA.TL;

import APPDEV.HQ.Contract.ConCommonClass;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 *
 @author:glt
 @date:2020年1月15日 上午8:47:12
 @function: 差旅报销 查找 40节点  50节点 审批操作者
 */
public class FNA_TL_RE_NodeOperator12Action20200610 implements Action{

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		log.writeLog("查询差旅审批 审批节点操作者开始==========");
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String tableName = "";
		String sql = " Select tablename From Workflow_bill Where id in ( "
				+"	Select formid From workflow_base Where id= " + workflowID + ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}

		// BtrPER 出差人
		// BTRCon  国内/国外字段
		// cygbjjpf  超预估标记(机票费)    AirExcMark
		// cygbjzsf  超预估标记(住宿费)   HoExcStaMark   HoExcMark  HoExcStaMark
		// cygbjqtclf 超预估标记(其他差旅费)  OthExcMark
		// BTRBEGDA 开始日期  BTRENDDA 结束日期

		sql = "select BtrPER,BTRCon,HoExcStaMark, "
				+ "to_date(BTRENDDA,'yyyy-mm-dd')-to_date(BTRBEGDA,'yyyy-mm-dd')+1 Tripdays "
				+ " from " + tableName + " where requestid = '" + requestid + "'";
		rs.execute(sql);
		if(rs.next()) {
			String btrPER = Util.null2String(rs.getString("BtrPER"));
			String bTRCon = Util.null2String(rs.getString("BTRCon"));
			String over2 = Util.null2String(rs.getString("HoExcStaMark"));
			int tripdays = rs.getInt("Tripdays");

			// 先清空 123 的值
			sql = "update " + tableName + " set manager1 = null,manager2 = null,manager3 = null where requestid = " + requestid ;
			rs.executeSql(sql);

			String isOver = "0";
			//	if("1".equals(over1) || "1".equals(over2) || "1".equals(over3) || "1".equals(over4)){
			if( "0".equals(over2)){
				isOver = "1";
			}

			String signature_2 = "";
			String signature_3 = "";
			// 特殊人签核5（差旅费用）
			sql = "select * from uf_fna_SpeAppro5 where BtrPER = '" + btrPER
					+ "' and Exstamark = '" + isOver + "'";
			rs.executeSql(sql);
			if(rs.next()){
				signature_2 =  Util.null2String(rs.getString("spr"));
				signature_3 =  Util.null2String(rs.getString("spr2"));
			}
			if(signature_2.length() > 0 || signature_3.length() > 0){
				sql = "update " + tableName + " set manager2 = '" + signature_2 + "',manager3 = '" + signature_3 + "' where requestid = " + requestid ;
				rs.executeSql(sql);
				return SUCCESS;
			}

			// 特殊人签核4（差旅费用）
			signature_2 = "";
			sql = "select  spr  from uf_fna_SpeAppro4 where BtrPER = '" + btrPER + "'";
			rs1.execute(sql);
			if(rs1.next()) {
				signature_2 = Util.null2String(rs1.getString("spr"));//签核人
			}
			if(signature_2.length() > 0){
				sql = "update " + tableName + " set manager2 = '" + signature_2 + "' where requestid = " + requestid ;
				rs.executeSql(sql);
				return SUCCESS;
			}

			//特殊签核3
			signature_2 = "";
			signature_3 = "";
			sql = "select  *  from uf_fna_SpeAppro3 where BtrPER = '" + btrPER + "'";
			rs1.execute(sql);
			while(rs1.next()) {
				signature_2 = Util.null2String(rs1.getString("spr"));//签核人
				signature_3 = Util.null2String(rs1.getString("spr2"));//签核人
				String btrID = Util.null2String(rs1.getString("btrdays"));// 出差条件ID
				if(isGetTravelDays(tripdays,btrID)&&(signature_2.length() > 0 || signature_3.length() > 0)){
					sql = "update " + tableName + " set manager2 = '" + signature_2 + "',manager3 = '" + signature_3 + "' where requestid = " + requestid ;
					rs.executeSql(sql);
					return SUCCESS;
				}
			}

			// 未超标
			if("0".equals(isOver)) {
				// 国内出差
				if("0".equals(bTRCon)) {
					String manager1 = getManagerIDs(btrPER,1);
					String manager2 = getRole("ROLE_ID_1",manager1);
					sql = "update " + tableName + " set manager1 = '" + manager1 + "',manager2 = '" + manager2
							+ "' where requestid = " + requestid;
					rs1.execute(sql);
					return SUCCESS;
				}else{ //国际
					String manager1 = getManagerIDs(btrPER,10);
					String manager2 = getRole("ROLE_ID_2",manager1);
					sql = "update " + tableName + " set manager1 = '" + manager1 + "',manager2 = '" + manager2
							+ "' where requestid = " + requestid;
					rs1.execute(sql);
					return SUCCESS;
				}
			}else{ // 超标
				String manager1 = getManagerIDs(btrPER,10);
				String manager2 = getRole("ROLE_ID_3",manager1);
				sql = "update " + tableName + " set manager1 = '" + manager1 + "',manager2 = '" + manager2
						+ "' where requestid = " + requestid;
				rs1.execute(sql);
				return SUCCESS;
			}
		}
		log.writeLog("查询差旅审批  审批节点操作者结束==========");
		return SUCCESS;
	}

	/**
	 * 获取某个角色的下的所有人员 组合成多人力资源字段
	 * @param roleIDName  在配置表的名称
	 * @param userIDs  重复检查（出现在此字符串中的值，将不会再添加到结果中）
	 * @return
	 */
	private String getRole(String roleIDName,String userIDs){
		ConCommonClass com = new ConCommonClass();
		String formmodeid = com.getformodeid("FNA_TravelBX", roleIDName);
		if(formmodeid.length() == 0) return "";
		RecordSet rs = new RecordSet();
		String roleStr = "";
		String sql = " select  resourceid  from hrmrolemembers  where roleid ='" + formmodeid + "'";
		rs.execute(sql);
		String flag = "";
		while(rs.next()) {
			String resourceid = Util.null2String(rs.getString("resourceid"));
			if(!userIDs.contains(resourceid)){
				roleStr = roleStr + flag + resourceid;
				flag = ",";
			}
		}
		return roleStr;
	}

	/**
	 * 获取所有层级人员上级（最高到N-1 查询 uf_fna_NJ1）
	 * @param userID
	 * @param index 获取上几级
	 * @return
	 */
	private String getManagerIDs(String userID,int index){
		RecordSet rs = new RecordSet();
		RecordSet rsDt = new RecordSet();
		String managerStr = "";
		String flag = ""; // 逗号标示
		int flagNum = 0;
		while(flagNum < index) {
			String managerid = "";
			String sql = "select managerid from hrmresource where id = '" + userID + "'";
			rs.execute(sql);
			if(rs.next()) {
				managerid = Util.null2String(rs.getString("managerid"));//审批人直接上级
				if(managerid.length() == 0) {
					flagNum = index + 1;
					break;
				}
				if(index - flagNum > 1){  // 取隔一级时,不需要判断了
					// 判断上级是否在N-1中
					rsDt.executeSql("select count(1) as ctFlag from uf_fna_NJ1 where nj1 = '" + managerid +"'");
					if(rsDt.next()){
						int ctFlag = rsDt.getInt("ctFlag");
						if(ctFlag > 0){
							flagNum = index + 1;
						}
					}
				}
			}
			managerStr = managerStr + flag + managerid;

			userID = managerid;
			flagNum++;
			flag = ",";
		}
		return managerStr;
	}

	/**
	 * 出差天数是否满足条件
	 * @param travelDays 出差天数
	 * @param id 条件ID
	 * @return
	 */
	private boolean isGetTravelDays(int travelDays,String id) {
		RecordSet rs = new RecordSet();
		String sql = "select * from uf_fna_BTRDAYS where id = '" + id + "'";
		rs.execute(sql);
		String btrBegDays = "";
		String btrEndDays = "";
		if(rs.next()) {
			btrBegDays = Util.null2String(rs.getString("BTRBEGDAYS"));
			btrEndDays = Util.null2String(rs.getString("BTRENDDAYS"));
		}
		if("".equals(btrBegDays) && "".equals(btrEndDays)){
			return true;
		}else if("".equals(btrBegDays)){
			// 小于或等于 大值
			if(travelDays <= Double.parseDouble(btrEndDays)){
				return true;
			}
		}else if("".equals(btrEndDays)){
			// 大于或等于小值
			if(travelDays >= Double.parseDouble(btrBegDays)){
				return true;
			}
		}else{
			// 小于或等于 大值 并且 大于或等于小值
			if(travelDays <= Double.parseDouble(btrEndDays) && travelDays >= Double.parseDouble(btrBegDays)){
				return true;
			}
		}
		return false;
	}


}
