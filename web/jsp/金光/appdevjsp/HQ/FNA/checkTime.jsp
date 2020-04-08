<%@ page import="weaver.general.Util" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONException" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="weaver.conn.RecordSet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", -10);
%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page"/>
<jsp:useBean id="rs_dt" class="weaver.conn.RecordSet" scope="page" />

<%
	String ksrq = Util.null2String(request.getParameter("ksrq"));//开始日期
	String kssj = Util.null2String(request.getParameter("kssj"));//开始时间
	String jsrq = Util.null2String(request.getParameter("jsrq"));//结束日期
	String jssj = Util.null2String(request.getParameter("jssj"));//结束时间
	String ccr = Util.null2String(request.getParameter("ccr"));//出差人 
	String requestid = Util.null2String(request.getParameter("requestid"));//流程编号  
	String travelOldvVal = Util.null2String(request.getParameter("travelOld"));//原出差类型  
	String type = Util.null2String(request.getParameter("type"));//类型
	String sql = "";
	String countSum = "";

	if("".equals(travelOldvVal)){
		travelOldvVal = "0";
	}
	
	if("".equals(requestid)){
		requestid = "0";
	}

	if(!"".equals(type) && "0".equals(type)){
		if(!"".equals(ksrq)  &&!"".equals(jsrq)  && !"".equals(ccr) && !"".equals(requestid)){
			sql ="select  requestid   from v_usedRequest_list "
			+" where BtrPER = '"+ccr+"' and requestid not in ("+requestid+","+travelOldvVal+") "
			+" and ((to_char(beginDateTime,'yyyy-MM-dd') <= to_char(to_date('"+ksrq+"','yyyy-MM-dd'), 'yyyy-MM-dd') "
			+" and to_char(endDateTime,'yyyy-MM-dd') >= to_char(to_date('"+ksrq+"','yyyy-MM-dd'), 'yyyy-MM-dd')) "
			+" or (to_char(beginDateTime,'yyyy-MM-dd') <= to_char(to_date('"+jsrq+"','yyyy-MM-dd'), 'yyyy-MM-dd')"
			+" and to_char(endDateTime,'yyyy-MM-dd') >=to_char(to_date('"+jsrq+"','yyyy-MM-dd'), 'yyyy-MM-dd'))) "
			+" and (wfstatus = 'Y' or wfstatus = 'F')";

	//		out.print(sql + "<br>");
			rs.execute(sql);
			while(rs.next()){
				countSum = countSum + " " + Util.null2String(rs.getString("requestid"));
			}
		}
		if("".equals(countSum)) countSum = "0";
		out.print(countSum);
	}else if(!"".equals(type) && "1".equals(type)){
		
		if(!"".equals(requestid)){
			
			sql =" select wfstatus from  formtable_main_932 where requestid = '"+requestid+"'";
			
			rs.execute(sql);
			if(rs.next()){
				countSum = Util.null2String(rs.getString("wfstatus"));
			}
		}
		if("".equals(countSum)) countSum = "0";
		out.print(countSum);
	}
%>