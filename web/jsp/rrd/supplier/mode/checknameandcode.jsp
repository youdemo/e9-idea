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
    String gysmc = Util.null2String(request.getParameter("gysmc"));
	String tyshxydm = Util.null2String(request.getParameter("tyshxydm"));
	String billid = Util.null2String(request.getParameter("billid"));
	String sql = "";
	String result = "0";//0不重复 1重复
	int count = 0;
	if("".equals(billid)){
		billid = "-1";
	}
	if(!"".equals(gysmc)){
		sql = "select count(1) as count from( " +
				"select id from uf_gysxxsj where Upper(gysmc)=Upper('"+gysmc+"') and id not in("+billid+") " +
				"union all " +
				"select id from uf_gyslb where Upper(gysmc)=Upper('"+gysmc+"') " +
				"union all " +
				"select id from formtable_main_84 where Upper(gysmc)=Upper('"+gysmc+"') " +
				"union all " +
				"select id from formtable_main_68 where Upper(gysmc)=Upper('"+gysmc+"')) a";
		rs.execute(sql);
		if(rs.next()){
			count = rs.getInt("count");
		}
		if(count >0){
			result = "1";
		}

	}else if(!"".equals(tyshxydm)){
		sql = "select count(1) as count from( " +
				"select id from uf_gysxxsj where Upper(tyshxydm)=Upper('"+tyshxydm+"') and id not in("+billid+") " +
				"union all " +
				"select id from uf_gyslb where Upper(tyshxydm)=Upper('"+tyshxydm+"') " +
				"union all " +
				"select id from formtable_main_84 where Upper(tyshxydm)=Upper('"+tyshxydm+"') " +
				"union all " +
				"select id from formtable_main_68 where Upper(tyshxydm)=Upper('"+tyshxydm+"')) a";
		rs.execute(sql);
		if(rs.next()){
			count = rs.getInt("count");
		}
		if(count >0){
			result = "1";
		}

	}

	out.print(result);
%>