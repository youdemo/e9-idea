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
    String checkids = Util.null2String(request.getParameter("checkids"));

	 JSONArray ja = new JSONArray();
	 String sql=" select fp,INVOICENUMBER,(select COSTTYPETYPE from uf_fna_Inv_coty where  COSTTYPE=a.fykm) as Costtypetype from uf_jyb a,fnaInvoiceLedger b where a.fp=b.id and a.id in("+checkids+") order by a.id desc";
    //out.print(sql);
	rs.execute(sql);
	while(rs.next()){
		JSONObject jo = new JSONObject();
		jo.put("fp",Util.null2String(rs.getString("fp")));
		jo.put("INVOICENUMBER",Util.null2String(rs.getString("INVOICENUMBER")));
		jo.put("Costtypetype",Util.null2String(rs.getString("Costtypetype")));
		ja.put(jo);
	}

	out.print(ja.toString());
%>