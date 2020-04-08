<%@page import="com.higer.oa.service.report.GetReportToken"%>
<%@page import="weaver.general.TimeUtil"%>
<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.conn.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="java.lang.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %> 
<%@ include file="/systeminfo/init_wev8.jsp" %>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<html>
<%
int userid = user.getUID();
GetReportToken aa = new GetReportToken();
String result = aa.getToken("123");
out.print("123:"+result);

 %>
<head>
	<title></title>
</head>


<body>

	<script type="text/javascript">
		alert("123");
		var ryid = "<%=userid%>";
		jQuery(document).ready(function(){
			  $.ajax({
             type: "GET",
             url: "http://ebiapp.klsz.com/portalbasicapi/SSO/LoginSso/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJMb2dpblRpbWUiOiIyMDE5LTEyLTEzIDE3OjM1OjEwIiwiVXNlck5hbWUiOiJ0YW5naiJ9.O7_OLkqgBUMZPw8FPITyDVAo5oN0IyuRr1oaHoLWo2g",
             data: {},
             dataType: "text",
             async:true,//同步   true异步
             success: function(data){
            	data=data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
			alert(data);
                      }
         	});
		})
	</script>
</body>
</html>