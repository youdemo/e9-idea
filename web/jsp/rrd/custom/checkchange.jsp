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
	String khdm = Util.null2String(request.getParameter("khdm")).replaceAll("'","''");//客户代码
	String khmc=  Util.null2String(request.getParameter("khmc")).replaceAll("'","''");//客户名称
	String szgj=  Util.null2String(request.getParameter("szgj")).replaceAll("'","''");//客户国家
	String szsf=  Util.null2String(request.getParameter("szsf")).replaceAll("'","''");//省份
	String khdz=  Util.null2String(request.getParameter("khdz")).replaceAll("'","''");//客户地址
	String fplx=  Util.null2String(request.getParameter("fplx")).replaceAll("'","''");//发票类型
	String khyhmc= Util.null2String(request.getParameter("khyhmc")).replaceAll("'","''");//开户银行名称
	String khyhzh= Util.null2String(request.getParameter("khyhzh")).replaceAll("'","''");//开户银行账号
	String fptt=  Util.null2String(request.getParameter("fptt")).replaceAll("'","''");//发票抬头
	String fpjsdz= Util.null2String(request.getParameter("fpjsdz")).replaceAll("'","''");//发票寄送地址
	String kpdh=  Util.null2String(request.getParameter("kpdh")).replaceAll("'","''");//开票电话
	String kpdz=  Util.null2String(request.getParameter("kpdz")).replaceAll("'","''");//开票地址
	String sql = "";
	String result = "0";//0不重复 1重复
	int count = 0;
	sql = " select count(1) as count from uf_xzkhsp  where IFNULL(khmc,'')='"+khmc+"' and IFNULL(szgj,'')='"+szgj+"' and IFNULL(szsf,'')='"+szsf+"' " +
			"and IFNULL(khdz,'')='"+khdz+"' and IFNULL(fplx,'')='"+fplx+"' and IFNULL(khyhmc,'')='"+khyhmc+"' and IFNULL(khyhzh,'')='"+khyhzh+"' and IFNULL(fptt,'')='"+fptt+"' " +
			"and IFNULL(fpjsdz,'')='"+fpjsdz+"' and IFNULL(kpdh,'')='"+kpdh+"' and IFNULL(kpdz,'')='"+kpdz+"' and khdm='"+khdm+"'";
    rs.execute(sql);
    if(rs.next()){
    	count = rs.getInt("count");
	}
    if(count>0){
		result = "1";
	}
	out.print(result);
%>