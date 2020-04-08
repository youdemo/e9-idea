<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.conn.RecordSet" %>
<%@ page import="weaver.general.BaseBean" %>
<%@ page import="weaver.hrm.*" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page"/>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
	
<html>
<head>
<script type="text/javascript" src="/js/weaver.js"></script>
<link rel="stylesheet" type="text/css" href="/css/Weaver.css">
</head>

<%

    try{
        User user = (User)request.getSession(true).getAttribute("weaver_user@bean");
        out.print("test1123:"+user.getUID());
    }catch(Exception e){
        new BaseBean().writeLog(e);
        out.print("test2222:");
    }


int pagenum = Util.getIntValue(request.getParameter("pagenum"),1);
int	perpage = 10;
%>

<BODY>
<div class="waikuang" style="width:100%; height:auto; border:0; margin:0; padding:0;">
  <div class="top" style="width:100%; font-size:40px; padding:0; height:auto; margin-top:60px;">
     <div class="logo" style=" width:35%; float:left; text-align:right; padding:0; margin:0;"><img src="/gvo/visitor/logoff.png"></img></div>
	 <div class="wenzi" style="width:64%; float:right; text-align:left; padding:0; margin:0;"><span>昆山访客登记、物品放行</span></div>
  </div>
  <div class="rukou" style="width:100%; height:40px; margin:0; margin-top:20px; padding:0;">
    <div class="fangke" style=" width:44%; float:left;text-align:right;  padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/visitor/GroupForVisitor.jsp">访客登记入口</a></div>
	<div class="fangxing" style=" width:44%; float:right; text-align:left; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/release/GroupForRelease.jsp">物品放行入口</a></div>
  </div>
  <div class="imgrukou" style="width:100%; height:200px; margin:0; margin-top:20px; padding:0;">
    <!--<div class="imgfangke" style=" width:49%; float:left;text-align:right; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/visitor/visitorInfo.jsp"><img style="border:0;" src="/gvo/visitor/fangke.png"</img></a></div>-->
	<div class="imgfangke" style=" width:49%; float:left;text-align:right; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/visitor/GroupForVisitor.jsp"><img style="border:0;" src="/gvo/visitor/fangke.png"</img></a></div>
	
	<!--<div class="imgfangxing" style=" width:49%; float:right; text-align:left; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/release/releaseInfo.jsp"><img style="border:0;" src="/gvo/visitor/fangxing.png"</img></a></div>-->
	<div class="imgfangxing" style=" width:49%; float:right; text-align:left; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/release/GroupForRelease.jsp"><img style="border:0;" src="/gvo/visitor/fangxing.png"</img></a></div>
  
  </div>

</div>
<div class="waikuang" style="width:100%; height:auto; border:0; margin:0; padding:0;">
  <div class="top" style="width:100%; font-size:40px; padding:0; height:auto; margin-top:60px;">
     <div class="logo" style=" width:35%; float:left; text-align:right; padding:0; margin:0;"><img src="/gvo/v2visitor/logoff.png"></img></div>
	 <div class="wenzi" style="width:64%; float:right; text-align:left; padding:0; margin:0;"><span>固安访客登记、物品放行</span></div>
  </div>
  <div class="rukou" style="width:100%; height:40px; margin:0; margin-top:20px; padding:0;">
    <div class="fangke" style=" width:44%; float:left;text-align:right;  padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/v2visitor/GroupForVisitor.jsp">访客登记入口</a></div>
	<div class="fangxing" style=" width:44%; float:right; text-align:left; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/v2release/GroupForRelease.jsp">物品放行入口</a></div>
  </div>
  <div class="imgrukou" style="width:100%; height:100%; margin:0; margin-top:20px; padding:0;">
    <!--<div class="imgfangke" style=" width:49%; float:left;text-align:right; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/visitor/visitorInfo.jsp"><img style="border:0;" src="/gvo/visitor/fangke.png"</img></a></div>-->
	<div class="imgfangke" style=" width:49%; float:left;text-align:right; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/v2visitor/GroupForVisitor.jsp"><img style="border:0;" src="/gvo/v2visitor/fangke.png"</img></a></div>
	
	<!--<div class="imgfangxing" style=" width:49%; float:right; text-align:left; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/release/releaseInfo.jsp"><img style="border:0;" src="/gvo/visitor/fangxing.png"</img></a></div>-->
	<div class="imgfangxing" style=" width:49%; float:right; text-align:left; padding:0; margin:0; border:none; text-decoration:none;"><a href="/gvo/v2release/GroupForRelease.jsp"><img style="border:0;" src="/gvo/v2visitor/fangxing.png"</img></a></div>
  
  </div>

</div>






<SCRIPT language="javascript" defer="defer" src="/js/datetime.js"></script>
<SCRIPT language="javascript" defer="defer" src="/js/JSDateTime/WdatePicker.js"></script>
</BODY>
</HTML>
