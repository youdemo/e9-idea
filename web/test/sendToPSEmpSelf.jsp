<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="net.sf.json.*"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page import="weaver.general.*" %>
<%@ page import="weaver.file.*" %>
<%@ page import="weaver.hrm.*" %>
<%@ page import="testdemo.TestMobile" %>
<%@ page import="weaver.mobile.plugin.ecology.service.*" %>
<jsp:useBean id="ps" class="weaver.mobile.plugin.ecology.service.PluginServiceImpl" scope="page" />
<%
User user = HrmUserVarify.getUser (request , response) ;
String loginid = user.getLoginid();
String url = "http://oa.visionox.com:1513/distApp/?loginid="+loginid+"&stamp=xxx&token=xxx";
response.sendRedirect(url);
%>