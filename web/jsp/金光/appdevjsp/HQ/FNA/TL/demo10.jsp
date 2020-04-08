<%@ page import="weaver.general.Util" %>
<%@ page import="APPDEV.HQ.Contract.ConCommonClass" %>
<%@ page import="weaver.conn.RecordSet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", -10);
%>
<%!
public String filerManager3(String incPro, String manager1,String manager3){
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
%>

<%!
public String getRole(String roleIDName,String userIDs){	
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
%>
<%!
public String getValStr(String tableName,String keyField,String key,String valueField){
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
%>
<%!
public Boolean iSTop200(String usrID){
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
%>
<%!
public String getManagerIDs(String userID,int index){
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
	
%>
<%!
public boolean isGetTravelDays(int travelDays,String id) {
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
%>

<%
		String workflowID = "5605";// 获取工作流程Workflowid的值
		String requestid = "821290";
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String tableName = "";
		String sql = " Select tablename From Workflow_bill Where id in ( "
				+"	Select formid From workflow_base Where id= " + workflowID + ")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		out.println("sql = " + sql + "<br>");
		sql = "select BTRBEGDA,BTRENDDA,to_date(BTRENDDA,'yyyy-mm-dd')-to_date(BTRBEGDA,'yyyy-mm-dd')+1 Tripdays,BTRCon,BtrLevel, "
		    +" BtrPER,BTRAREA,manager1,IncPro,AGROUP,AAim "
		    + " from " + tableName + " where requestid = '" + requestid + "'";
		rs.execute(sql);
		out.println("sql = " + sql + "<br>");
		if(rs.next()) {
			String btrPER = Util.null2String(rs.getString("BtrPER"));//出差人 
			int tripDays = rs.getInt("Tripdays");//出差天数 
			String bTRCon = Util.null2String(rs.getString("BTRCon"));//差旅国内外  
		//	bTRCon = "1";
			int btrLevel = rs.getInt("BtrLevel");//职级
			String btrArea = Util.null2String(rs.getString("BTRAREA"));// 集团内外
			String incPro = Util.null2String(rs.getString("IncPro"));  // 项目类型
		
			String aGroup = Util.null2String(rs.getString("AGROUP")); // A+组别
			String aAim = Util.null2String(rs.getString("AAim"));    // 目的
			
			String manager1 = getValStr("uf_APROJECT_GROUP","VALUE",aGroup,"VALUEP");
			String manager2 = getValStr("uf_fna_aim","id",aAim,"AimPerson");
			
			if(("," + manager1 + ",").contains("," + btrPER + ",")) manager1 = "";
			if(("," + manager2 + ",").contains("," + btrPER + ",")) manager2 = "";
			
			sql = "update " + tableName + " set manager1 = '" + manager1 + "',manager2 = '" + manager2 + "' where requestid = " + requestid;
			rs.executeSql(sql);
			out.println("sql(-1) = " + sql + "<br>");		
			// 先清空 345 的值
			sql = "update " + tableName + " set manager3 = null,manager4 = null,manager5 = null where requestid = " + requestid ;
			rs.executeSql(sql);
			out.println("sql(0) = " + sql + "<br>");
			//特殊签核4
			String signature4_4 = "";
			sql = "select  spr  from uf_fna_SpeAppro4 where BtrPER = '" + btrPER + "'";
			rs1.execute(sql);
			out.println("sql = " + sql + "<br>");
			if(rs1.next()) {
				signature4_4 = Util.null2String(rs1.getString("spr"));//签核人	
			}
			signature4_4 = filerManager3(incPro, manager1, signature4_4); // 条件过滤
			if(signature4_4.length() > 0){
				sql = "update " + tableName + " set manager4 = '" + signature4_4 + "' where requestid = " + requestid ;
				rs.executeSql(sql);
				out.println("sql(4)  = " + sql + "<br>");
				return ;
			}
			
			//特殊签核3
			String signature3_4 = "";
			String signature3_5 = "";
			sql = "select  *  from uf_fna_SpeAppro3 where BtrPER = '" + btrPER + "'";
			out.println("sql = " + sql + "<br>");
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
					out.println("sql(3)  = " + sql + "<br>");
					return ;
				}
			}
			
			if("1".equals(bTRCon)){
				//特殊签核2
				String signature2_4 = "";
				String signature2_5 = "";
				sql = "select  *  from uf_fna_SpeAppro2 where BtrPER = '" + btrPER + "' and BTRAREA = '" + btrArea + "'";
				rs1.execute(sql);
				out.println("sql = " + sql + "<br>");
				while(rs1.next()) {
					signature2_4 = Util.null2String(rs1.getString("spr"));//签核人	
					signature2_4 = filerManager3(incPro, manager1, signature2_4); // 条件过滤
					
					signature2_5 = Util.null2String(rs1.getString("spr2"));//签核人	
					signature2_5 = filerManager3(incPro, manager1, signature2_5); // 条件过滤
					
					String btrID = Util.null2String(rs1.getString("btrdays"));// 出差条件ID
					out.println("sql(2)  signature2_4 = " + signature2_4 +" ; signature2_5 = tripDays->" + tripDays + " ; btrID ->" + btrID +" ; " + "<br>");
					out.println("sql(2)  isGetTravelDays = " +  isGetTravelDays(tripDays,btrID) + "<br>");
					// 条件满足存，整个方法就结束
					if((signature2_4.length() > 0 || signature2_5.length() > 0) && isGetTravelDays(tripDays,btrID)){
						sql = "update " + tableName + " set manager4 = '" + signature2_4 + "',manager5 = '" + signature2_5 + "' where requestid = " + requestid ;
						rs.executeSql(sql);
						out.println("sql(2)   = " + sql + "<br>");
						return ;
					}
				}
			}else{
				//特殊签核1
				String signature1_4 = "";
				String signature1_5 = "";
				sql = "select * from uf_fna_SpeAppro1 where BtrPER = '" + btrPER + "'";
				out.println("sql = " + sql + "<br>");
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
						out.println("sql(1)   = " + sql + "<br>");
						return ;
					}
				}
			}
			out.println("bTRCon = " + bTRCon + "<br>");
			// 国内出差 
			if("0".equals(bTRCon)) { 
				//小于等于14个自然日 
				if(tripDays <= 14 ) {
					String manager3 = getManagerIDs(btrPER,1);
					manager3 = filerManager3(incPro, manager1, manager3); // 条件过滤
					String manager4 = getRole("ROLE_ID_1",manager3);
					sql = "update " + tableName + " set manager3 = '" + manager3 + "',manager4 = '" + manager4 
							+ "' where requestid = " + requestid;
					out.println("sql(7)  = " + sql + "<br>");
					rs1.execute(sql);
					return ;
				}else if (tripDays > 14 ){//大于14个自然日 
					String manager3 = getManagerIDs(btrPER,2);
					manager3 = filerManager3(incPro, manager1, manager3); // 条件过滤
					String manager4 = getRole("ROLE_ID_2",manager3);
					
					sql = "update " + tableName + " set manager3 = '" + manager3 + "',manager4 = '" + manager4 
								+ "' where requestid = " + requestid;
					out.println("sql(8)  = " + sql + "<br>");
					rs1.execute(sql);
					return ;
				}	
			}else if ("1".equals(bTRCon)) {//国际
				if(btrLevel >=12 || iSTop200(btrPER)){
					String manager3 = getManagerIDs(btrPER,10);
					manager3 = filerManager3(incPro, manager1, manager3); // 条件过滤
		
					ConCommonClass com = new ConCommonClass();
		String formmodeid = com.getformodeid("FNA_TravelSQ", "ROLE_ID_3");
		out.println("formmodeid = " + formmodeid + "<br>");
		sql = " select  resourceid  from hrmrolemembers  where roleid = '" + formmodeid + "'";
		rs.execute(sql);
		while(rs.next()) {
			String resourceid = Util.null2String(rs.getString("resourceid")); 
			out.println("resourceid = " + resourceid + "<br>");	
		}
				
					String manager4 = getRole("ROLE_ID_4",manager3); //角色ID
					sql = "update " + tableName + " set manager3 = '" + manager3 + "',manager4 = '" + manager4 
							+ "' where requestid = " + requestid;
					out.println("sql(9)  = " + sql + "<br>");
					rs1.execute(sql);
					return ;
				}else{ // 其他的
					String manager3 = getManagerIDs(btrPER,10);
					manager3 = filerManager3(incPro, manager1, manager3); // 条件过滤
					String manager4 = getRole("ROLE_ID_3",manager3);  //角色ID
					sql = "update " + tableName + " set manager3 = '" + manager3 + "',manager4 = '" + manager4 
							+ "' where requestid = " + requestid;
					out.println("sql(10)  = " + sql + "<br>");
					rs1.execute(sql);
					return ;
				}
			}
			
		}
		
		
%>
