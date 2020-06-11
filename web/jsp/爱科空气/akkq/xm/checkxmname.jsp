<%@ page import="weaver.general.Util" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", -10);
%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page"/>
<%
    String xmmc = Util.null2String(request.getParameter("xmmc")).replaceAll("'","''");
	String sql = "";
	String result = "0";//0不重复 1重复
	int count = 0;
	sql = "select count(1) as count from (" +
			"select xmmc as name from formtable_main_16 a,workflow_requestbase b where a.requestid=b.requestid and a.xmmc='"+xmmc+"' and b.currentnodetype>0 " +
			"union all " +
			"select name from CRM_CustomerInfo where name ='"+xmmc+"') a";
    rs.execute(sql);
    if(rs.next()){
    	count = rs.getInt("count");
	}
    if(count>0){
		result = "1";
	}
	out.print(result);
%>