﻿<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.util.*,weaver.hrm.appdetach.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ page import="rdd.services.GetRequestClient" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ taglib uri="/browserTag" prefix="brow"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page" />
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<HTML>
	<HEAD>
		<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
		<script type="text/javascript" src="/appres/hrm/js/mfcommon_wev8.js"></script>
		<script language=javascript src="/js/ecology8/hrm/HrmSearchInit_wev8.js"></script>
		<script type='text/javascript' src='/js/jquery-autocomplete/lib/jquery.bgiframe.min_wev8.js'></script>
		<script type='text/javascript' src='/js/jquery-autocomplete/jquery.autocomplete_wev8.js'></script>
		<script type='text/javascript' src='/js/jquery-autocomplete/browser_wev8.js'></script>
		<link rel="stylesheet" type="text/css" href="/js/jquery-autocomplete/jquery.autocomplete_wev8.css" />
		<link rel="stylesheet" type="text/css" href="/js/jquery-autocomplete/browser_wev8.css" />
		<SCRIPT language="JavaScript" src="/js/weaver_wev8.js"></SCRIPT>
		<link rel="stylesheet" href="/css/ecology8/request/requestTopMenu_wev8.css" type="text/css" />
		<link rel="stylesheet" href="/wui/theme/ecology8/jquery/js/zDialog_e8_wev8.css" type="text/css" />
		<style>
		.checkbox {
			display: none
		}
		</style>
	</head>
	<%
	int language =user.getLanguage();
	Calendar now = Calendar.getInstance();
	
	String sql = "";
	int userid = user.getUID();

	String imagefilename = "/images/hdReport_wev8.gif";
	String titlename =SystemEnv.getHtmlLabelName(20536,user.getLanguage());
	String needfav ="1";
	String needhelp ="";
    String out_pageId = "agency20191021";

    //操作人工号
    String loginid = "";
    sql = "select  loginid  from hrmresource where id = '"+userid+"' ";
    rs.execute(sql);
    while(rs.next()){
		loginid = Util.null2String(rs.getString("loginid"));
	}


	%>
	<BODY>
		<div id="tabDiv">
			<span class="toggleLeft" id="toggleLeft" title="<%=SystemEnv.getHtmlLabelName(32814,user.getLanguage()) %>">
				<%=SystemEnv.getHtmlLabelName(20536,user.getLanguage()) %></span>
		</div>
		<div id="dialog">
			<div id='colShow'></div>
		</div>
	    <input type="hidden" name="pageId" id="pageId" value="<%=out_pageId%>"/>
		<%@ include file="/systeminfo/TopTitle_wev8.jsp" %>
		<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp" %>
		
		<%
			RCMenu += "{刷新,javascript:refersh(),_self} " ;
			RCMenuHeight += RCMenuHeightStep ; 
		%>
		
		<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>
		<FORM id=report name=report action="agency.jsp" method=post>
			<input type="hidden" name="requestid" value="">
			<table id="topTitle" cellpadding="0" cellspacing="0">
				<tr>
					<td></td>
					<td class="rightSearchSpan" style="text-align:right;"></td>
				</tr>
			</table>
			
		
			<div class="advancedSearchDiv" id="advancedSearchDiv" style="display:none;">
				
			</div>
		</FORM>
		
		<%
			String tableString = "";	
			String operateString= "";
		tableString =" <table tabletype=\"none\" pagesize=\""+ PageIdConst.getPageSize(out_pageId,user.getUID(),PageIdConst.HRM)+"\" pageId=\""+out_pageId+"\" datasource=\"rdd.services.GetRequestClient\" sourceparams=\"loginid:"+loginid+"\">"+         
				   "	   <sql backfields=\"*\" sqlform=\"tmpTable\" sqlwhere=\" \"  sqlorderby=\"requestid\"  sqlprimarykey=\"requestid\" sqlsortway=\"asc\" sqlisdistinct=\"false\" />"+
		operateString+
		"			<head>";
				tableString +="<col width=\"20%\" text=\"请求标题\" column=\"showname\" orderkey=\"showname\" />"+ 
					   		"<col width=\"20%\" text=\"创建人\" column=\"creater\" orderkey=\"creater\" />"+ 
					   		"<col width=\"20%\" text=\"创建日期\" column=\"createdate\" orderkey=\"createdate\"/>"+ 
					   "</head>"+
					   "</table>";      
		

	%>
	<wea:SplitPageTag isShowTopInfo="false" tableString="<%=tableString%>" mode="run" />
	<script type="text/javascript">
		 function onBtnSearchClick() {
			report.submit();
		}

		function refersh() {
  			window.location.reload();
  		}
		</script>
		<SCRIPT language="javascript" src="/js/datetime_wev8.js"></script>
	<SCRIPT language="javascript" src="/js/selectDateTime_wev8.js"></script>
	<SCRIPT language="javascript" src="/js/JSDateTime/WdatePicker_wev8.js"></script>
	
</BODY>
</HTML>