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


<%
	String applicant = request.getParameter("applicant");//申请人
	JSONObject json = new JSONObject();

	if("".equals(applicant)){
		applicant =  "0";	
	}
	

	String sql = " select field2, field3,(select mobile from hrmresource where id = '"+applicant+"') mobile "
		+" from cus_fielddata where scopeid=-1 and id = '"+applicant+"' ";
	rs.execute(sql);
	//out.print(sql);
	if(rs.next()){

		String companyCode = Util.null2String(rs.getString("field2"));//公司代码
		String hrmScope = Util.null2String(rs.getString("field3"));//人事范围
		String mobile = Util.null2String(rs.getString("mobile"));//联系方式

		json.put("companyCode",companyCode);
		json.put("hrmScope",hrmScope);
		json.put("mobile",mobile);



	}
	out.print(json.toString());	
	
%>