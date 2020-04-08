<%@ page import="weaver.general.Util" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", -10);
%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page"/>
<%
    String sfmyzf = Util.null2String(request.getParameter("sfmyzf"));//是否美元支付
	String bz = Util.null2String(request.getParameter("bz"));
	 String rate = "";
	 String sql="select rate from uf_fna_Curr where curr='"+bz+"' and tcurr=case when '"+sfmyzf+"'='1' then 'USD' else 'RMB' end";
    //out.print(sql);
	rs.execute(sql);
	if(rs.next()){
		rate = Util.null2String(rs.getString("rate"));
	}

	out.print(rate);
%>