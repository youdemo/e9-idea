package APPDEV.HQ.FNA.TL;

import APPDEV.HQ.Contract.ConCommonClass;
import com.api.integration.Base;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
	@author:glt
	@date:2020年1月15日 上午8:47:12
	@function: 差旅申请 查找 40节点  50节点 审批操作者
*/
public class FNA_TL_AP_NodeOperator34Action implements Action{

	@Override
	public String execute(RequestInfo info) {
		BaseBean log = new BaseBean();
		log.writeLog("查询出差申请 审批节点操作者开始==========");
		String workflowID = info.getWorkflowid();// 获取工作流程Workflowid的值
		String requestid = info.getRequestid();
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String FundCenterCODE = "";//基金中心key
		String FICTR = "";//基金中心SAP
		String FundCenterP = "";//基金中心负责人
		String tableName = "";
		String sql = " Select tablename From Workflow_bill Where id in ( "
				+"	Select formid From workflow_base Where id= " + workflowID + ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		
		sql = "select BTRBEGDA,BTRENDDA,to_date(BTRENDDA,'yyyy-mm-dd')-to_date(BTRBEGDA,'yyyy-mm-dd')+1 Tripdays,BTRCon,BtrLevel, "
		    +" BtrPER,BTRAREA,manager1,IncPro,AGROUP,AAim,FundCenterCODE,FICTR,FundCenterP "
		    + " from " + tableName + " where requestid = '" + requestid + "'";
		rs.execute(sql);
		if(rs.next()) {
			String btrPER = Util.null2String(rs.getString("BtrPER"));//出差人 
			// String bTRBEGDA = Util.null2String(rs.getString("BTRBEGDA"));//出差开始时间 
			// String bTRENDDA = Util.null2String(rs.getString("BTRENDDA"));//出差结束时间 
			int tripDays = rs.getInt("Tripdays");//出差天数 
			String bTRCon = Util.null2String(rs.getString("BTRCon"));//差旅国内外  
			int btrLevel = rs.getInt("BtrLevel");//职级
			String btrArea = Util.null2String(rs.getString("BTRAREA"));// 集团内外
	//		String manager1 = Util.null2String(rs.getString("manager1"));  // manager1
			String incPro = Util.null2String(rs.getString("IncPro"));  // 项目类型
		
			String aGroup = Util.null2String(rs.getString("AGROUP")); // A+组别
			String aAim = Util.null2String(rs.getString("AAim"));    // 目的
			//获取manger6
			String manager6 = "";
			FundCenterCODE =  Util.null2String(rs.getString("FundCenterCODE"));//基金中心key
			FICTR =  Util.null2String(rs.getString("FICTR"));//基金中心SAP
			FundCenterP =  Util.null2String(rs.getString("FundCenterP"));//基金中心负责人
			if(!FundCenterCODE.equals(FICTR) &&!"".equals(FundCenterP)){
				String FundCenterPid = "";//负责人id
				sql = "select id from hrmresource where workcode='"+FundCenterP+"'";
				rs.execute(sql);
				if(rs.next()){
					FundCenterPid = Util.null2String(rs.getString("id"));
				}
				if(!"".equals(FundCenterPid)){
					manager6=getManager6(FundCenterPid);
					if(manager6.equals(FundCenterPid)){
						manager6 = "";
					}
				}
			}
			sql = "update "+tableName+" set manager6='"+manager6+"' where requestid="+requestid;
			rs.execute(sql);
			
			String manager1 = getValStr("uf_APROJECT_GROUP","VALUE",aGroup,"VALUEP");
			String manager2 = getValStr("uf_fna_aim","id",aAim,"AimPerson");
			
			if(("," + manager1 + ",").contains("," + btrPER + ",")) manager1 = "";
			if(("," + manager2 + ",").contains("," + btrPER + ",")) manager2 = "";
			
			sql = "update " + tableName + " set manager1 = '" + manager1 + "',manager2 = '" + manager2 + "' where requestid = " + requestid;
			rs.executeSql(sql);
			
			// 先清空 345 的值
			sql = "update " + tableName + " set manager3 = null,manager4 = null,manager5 = null where requestid = " + requestid ;
			rs.executeSql(sql);
			
			//特殊签核4
			String signature4_4 = "";
		//	String signature4_5 = "";
			sql = "select  spr  from uf_fna_SpeAppro4 where BtrPER = '" + btrPER + "'";
			rs1.execute(sql);
			if(rs1.next()) {
				signature4_4 = Util.null2String(rs1.getString("spr"));//签核人	
	//			signature4_5 = Util.null2String(rs1.getString("spr2"));//签核人
			}
			signature4_4 = filerManager3(incPro, manager1, signature4_4); // 条件过滤
	//		signature4_5 = filerManager3(incPro, manager1, signature4_5); // 条件过滤
			// 条件满足存，整个方法就结束
		//	if(signature4_4.length() > 0 || signature4_5.length() > 0){
			if(signature4_4.length() > 0){
			//	sql = "update " + tableName + " set manager4 = '" + signature4_4 + "',manager5 = '" + signature4_5 + "' where requestid = " + requestid ;
				sql = "update " + tableName + " set manager4 = '" + signature4_4 + "' where requestid = " + requestid ;
				rs.executeSql(sql);
				return SUCCESS;
			}
			
			//特殊签核3
			String signature3_4 = "";
			String signature3_5 = "";
			sql = "select  *  from uf_fna_SpeAppro3 where BtrPER = '" + btrPER + "'";
			rs1.execute(sql);
			while(rs1.next()) {
				signature3_4 = Util.null2String(rs1.getString("spr"));//签核人	
				signature3_5 = Util.null2String(rs1.getString("spr2"));//签核人	
				signature3_4 = filerManager3(incPro, manager1, signature3_4); // 条件过滤
				signature3_5 = filerManager3(incPro, manager1, signature3_5); // 条件过滤
				String btrID = Util.null2String(rs1.getString("btrdays"));// 出差条件ID
				// 条件满足存，整个方法就结束
				if((signature3_4.length() > 0||signature3_5.length() > 0) && isGetTravelDays(tripDays,btrID)){
					sql = "update " + tableName + " set manager4 = '" + signature3_4 + "',manager5 = '" + signature3_5 + "' where requestid = " + requestid ;
					rs.executeSql(sql);
					return SUCCESS;
				}
			}
			
			if("1".equals(bTRCon)){
				//特殊签核2
				String signature2_4 = "";
				String signature2_5 = "";
				sql = "select  *  from uf_fna_SpeAppro2 where BtrPER = '" + btrPER + "' and BTRAREA = '" + btrArea + "'";
				rs1.execute(sql);
				while(rs1.next()) {
					signature2_4 = Util.null2String(rs1.getString("spr"));//签核人	
					signature2_4 = filerManager3(incPro, manager1, signature2_4); // 条件过滤
					
					signature2_5 = Util.null2String(rs1.getString("spr2"));//签核人	
					signature2_5 = filerManager3(incPro, manager1, signature2_5); // 条件过滤
					
					String btrID = Util.null2String(rs1.getString("btrdays"));// 出差条件ID
					// 条件满足存，整个方法就结束
					if((signature2_4.length() > 0 || signature2_5.length() > 0) && isGetTravelDays(tripDays,btrID)){
						sql = "update " + tableName + " set manager4 = '" + signature2_4 + "',manager5 = '" + signature2_5 + "' where requestid = " + requestid ;
						rs.executeSql(sql);
						return SUCCESS;
					}
				}
			}else{
				//特殊签核1
				String signature1_4 = "";
				String signature1_5 = "";
				sql = "select * from uf_fna_SpeAppro1 where BtrPER = '" + btrPER + "'";
				rs1.execute(sql);
				while(rs1.next()) {
					signature1_4 = Util.null2String(rs1.getString("spr"));//签核人	
					signature1_4 = filerManager3(incPro, manager1, signature1_4); // 条件过滤
					
					signature1_5 = Util.null2String(rs1.getString("spr2"));//签核人	
					signature1_5 = filerManager3(incPro, manager1, signature1_5); // 条件过滤
					
					String btrID = Util.null2String(rs1.getString("btrdays"));// 出差条件ID
					// 存在的话就结束
					if((signature1_4.length() > 0 || signature1_5.length() > 0) && isGetTravelDays(tripDays,btrID)){
						sql = "update " + tableName + " set manager4 = '" + signature1_4 + "',manager5 = '" + signature1_5 + "' where requestid = " + requestid ;
						rs.executeSql(sql);
						return SUCCESS;
					}
				}
			}
			// 国内出差 
			if("0".equals(bTRCon)) { 
				//小于等于14个自然日 
				if(tripDays <= 14 ) {
					String manager3 = getManagerIDs(btrPER,1);
					manager3 = filerManager3(incPro, manager1, manager3); // 条件过滤
					manager3 = manager3.replace("88800309", "0");
					String manager4 = getRole("ROLE_ID_1",manager3);
					sql = "update " + tableName + " set manager3 = '" + manager3 + "',manager4 = '" + manager4 
							+ "' where requestid = " + requestid;
					rs1.execute(sql);
					return SUCCESS;
				}else if (tripDays > 14 ){//大于14个自然日 
					String manager3 = getManagerIDs(btrPER,2);
					manager3 = filerManager3(incPro, manager1, manager3); // 条件过滤
					manager3 = manager3.replace("88800309", "0");
					String manager4 = getRole("ROLE_ID_2",manager3);
					
					sql = "update " + tableName + " set manager3 = '" + manager3 + "',manager4 = '" + manager4 
								+ "' where requestid = " + requestid;
					rs1.execute(sql);
					return SUCCESS;
				}	
			}else if ("1".equals(bTRCon)) {//国际
				if(btrLevel >=12 || iSTop200(btrPER)){
					String manager3 = getManagerIDs(btrPER,10);
					manager3 = filerManager3(incPro, manager1, manager3); // 条件过滤
					manager3 = manager3.replace("88800309", "0");
					String manager4 = getRole("ROLE_ID_4",manager3); //角色ID
					sql = "update " + tableName + " set manager3 = '" + manager3 + "',manager4 = '" + manager4 
							+ "' where requestid = " + requestid;
					rs1.execute(sql);
					return SUCCESS;
				}else{ // 其他的
					String manager3 = getManagerIDs(btrPER,10);
					manager3 = filerManager3(incPro, manager1, manager3); // 条件过滤
					manager3 = manager3.replace("88800309", "0");
					String manager4 = getRole("ROLE_ID_3",manager3);  //角色ID
					sql = "update " + tableName + " set manager3 = '" + manager3 + "',manager4 = '" + manager4 
							+ "' where requestid = " + requestid;
					rs1.execute(sql);
					return SUCCESS;
				}
			}
			
		}
		log.writeLog("查询出差申请审批节点操作者结束==========");
		return SUCCESS;
	}

	/**
	 *
	 * @param ryid 人员id
	 * @return
	 */
	public String getManager6(String ryid){
		RecordSet rs = new RecordSet();
		String managerid = "";
		int seclevel = 0;
		String sql = "select managerid,(select seclevel from hrmresource where id=a.managerid) as seclevel from hrmresource a where id="+ryid;
		rs.execute(sql);
		if(rs.next()){
			managerid = Util.null2String(rs.getString("managerid"));
			seclevel = Util.getIntValue(rs.getString("seclevel"),0);
		}
		if("".equals(managerid)||seclevel>=70){
			return ryid;
		}else{
			return getManager6(managerid);
		}

	}
	
	/**
	 * 当项目类型为 A+项目(0)时，manager1出现的人在manager3中就不需要出现了
	 * @param incPro 	项目类型
	 * @param manager1	 
	 * @param manager3
	 * @return
	 */
	private String filerManager3(String incPro, String manager1,String manager3){
		if(!"0".equals(incPro)) return manager3;
		if(manager1 == null || manager1.length() == 0) return manager3;
		if(manager3 == null || manager3.length() == 0) return "";
		String[] mangerArr = manager1.split(",");
		String manager3Str = "," + manager3 + ",";  // 为了查询和替换方便
		for(String manager : mangerArr){
			if(manager3Str.contains("," + manager + ",")){
				manager3Str = manager3Str.replace("," + manager + ",", ",");
			}
		}
		
		// 如果只要一个 逗号 处理
		if(manager3Str.length() < 2) return "";
		manager3 = manager3Str.substring(1,manager3Str.length()-1);
		return manager3;
	}
	
	/**
	 * 获取某个角色的下的所有人员 组合成多人力资源字段
	 * @param roleIDName  在配置表的名称
	 * @param userIDs  重复检查（出现在此字符串中的值，将不会再添加到结果中）
	 * @return
	 */
	private String getRole(String roleIDName,String userIDs){	
		String roleStr = "";
		ConCommonClass com = new ConCommonClass();
		String formmodeid = com.getformodeid("FNA_TravelSQ", roleIDName);
		if(formmodeid.length() == 0) return "";
		RecordSet rs = new RecordSet();
		
		String sql = " select  resourceid  from hrmrolemembers  where roleid = '" + formmodeid + "'";
		rs.execute(sql);
		String flag = "";
		while(rs.next()) {
			String resourceid = Util.null2String(rs.getString("resourceid")); 
			if(!("," + userIDs + ",").contains("," + resourceid + ",")){
				roleStr = roleStr + flag + resourceid;
				flag = ",";
			}
		}
		return roleStr;
	}
	
	/**
	 * 获取单字段信息
	 * @param tableName  需要获取字段的所属表名称
	 * @param keyField 唯一值字段
	 * @param key	唯一标示值 （默认为id）
	 * @param valueField 需要获取的值的字段标示
	 * @return
	 */
	private String getValStr(String tableName,String keyField,String key,String valueField){
		if(keyField == null || "".equals(keyField)) keyField = "id";
		RecordSet rs = new RecordSet();
		String sql = "select " + valueField + " from " +  tableName + " where " + keyField + " = '" + key +"'";
		rs.executeSql(sql);
		String result = "";
		if(rs.next()){
			result = Util.null2String(rs.getString(valueField));
		}
		return result;
	}
	
	/**
	 * 是否是top200人员
	 * @param usrID
	 * @return
	 */
	private Boolean iSTop200(String usrID){
		RecordSet rs = new RecordSet();
		String sql = "select count(1) ctFlag from uf_fna_TOP200 where top200 = '" + usrID + "'";
		rs.executeSql(sql);
		int ctFlag = 0;
		if(rs.next()){
			ctFlag = rs.getInt("ctFlag");
		}
		if(ctFlag > 0) return true;
		return false;
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
