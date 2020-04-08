<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="com.higer.oa.service.report.GetReportToken" %>
<%@ page import="java.lang.*" %>
<%@ page import="weaver.hrm.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />

<%
 User user = HrmUserVarify.getUser (request , response) ;
 int userid = user.getUID();
 String type = Util.null2String(request.getParameter("type"));
 String url = "";
 String sql = "select urldz from uf_reportjc_mt where bs='"+type+"'";
 rs.execute(sql);
 if(rs.next()){
  url = Util.null2String(rs.getString("urldz"));
 }
 if("".equals(url)){
  out.print("单点地址获取失败，请联系系统管理员");
  return;
 }
 GetReportToken grt = new GetReportToken();
 String result = grt.getReportToken(userid+"");
 if("E".equals(result)){
  out.print("单点登陆失败，请联系系统管理员");
  return;
 }else{

  String reportUrl = url.replaceAll("##token##",result);
  response.sendRedirect(reportUrl);
 }


 %>
