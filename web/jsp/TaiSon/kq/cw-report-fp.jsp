<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.util.*,weaver.hrm.appdetach.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ taglib uri="/browserTag" prefix="brow"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page" />
<%
Integer lg=(Integer)user.getLanguage();
weaver.general.AccountType.langId.set(lg);
%>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="AccountType" class="weaver.general.AccountType" scope="page" />
<jsp:useBean id="LicenseCheckLogin" class="weaver.login.LicenseCheckLogin" scope="page" />
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
	
	int userid = user.getUID();
	String imagefilename = "/images/hdReport_wev8.gif";
	String titlename =SystemEnv.getHtmlLabelName(20536,user.getLanguage());
	String needfav ="1";
	String needhelp ="";
	boolean flagaccount = weaver.general.GCONST.getMOREACCOUNTLANDING();
    String out_pageId = "cwreportfp_1";
	String sql = "";
	
	String fplx = Util.null2String(request.getParameter("fplx"));
	String beginDate = Util.null2String(request.getParameter("beginDate"));
	String endDate = Util.null2String(request.getParameter("endDate"));
	String cbzx = Util.null2String(request.getParameter("cbzx"));
	String cbzxspan = "";
	 if(!"".equals(cbzx)){
      sql = "select cbzxbmmc from uf_cbzx where id="+cbzx;
      rs.executeSql(sql);
      if(rs.next()){
          cbzxspan = Util.null2String(rs.getString("cbzxbmmc"));
      }
  }
	String djh = Util.null2String(request.getParameter("djh"));
	String ztzt = Util.null2String(request.getParameter("ztzt"));
	String ywlx = Util.null2String(request.getParameter("ywlx"));

	%>
	<BODY>
		<div id="tabDiv">
			<span class="toggleLeft" id="toggleLeft" title="<%=SystemEnv.getHtmlLabelName(32814,user.getLanguage()) %>"><%=SystemEnv.getHtmlLabelName(20536,user.getLanguage()) %></span>
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
		
		RCMenuHeight += RCMenuHeightStep ; 
		%>
		<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>
		<FORM id=report name=report action="/goodbaby/gb2019/cw-report-fp.jsp" method=post>
			<input type="hidden" name="requestid" value="">
			<table id="topTitle" cellpadding="0" cellspacing="0">
				<tr>
					<td></td>
					<td class="rightSearchSpan" style="text-align:right;">
					<span id="advancedSearch" class="advancedSearch"><%=SystemEnv.getHtmlLabelName(21995,user.getLanguage())%></span>
						<span title="<%=SystemEnv.getHtmlLabelName(23036,user.getLanguage())%>" class="cornerMenu"></span>
				</tr>
			</table>
			
		
			<div class="advancedSearchDiv" id="advancedSearchDiv" style="display:none;">
				<wea:layout type="4col">
				<wea:group context="查询条件">
			
				<wea:item>单据号</wea:item>
				<wea:item>
                 <input name="djh" id="djh" class="InputStyle" type="text" value="<%=djh%>"/>
        </wea:item>
			 	<wea:item>费用承担部门</wea:item>
				<wea:item>
					<brow:browser name='cbzx'
					viewType='0'
					browserValue='<%=cbzx%>'
					isMustInput='1'
					browserSpanValue='<%=cbzxspan%>'
					hasInput='true'
					linkUrl=''
					completeUrl=''
					width='60%'
					isSingle='true'
					hasAdd='false'
					browserUrl='/systeminfo/BrowserMain.jsp?url=/interface/CommonBrowser.jsp?type=browser.cbzx'>
					</brow:browser>
			 </wea:item>
					<wea:item>发票类型</wea:item>
					<wea:item>
						<select class="e8_btn_top middle" name="fplx" id="fplx">
									<option value="" <%if("".equals(fplx)){%> selected<%} %>></option>
						 			<option value="1" <%if("1".equals(fplx)){%> selected<%} %>>增值税普通发票</option>
                
                    <option value="2" <%if("2".equals(fplx)){%> selected<%} %>>增值税专用发票</option>
                
                    <option value="3" <%if("3".equals(fplx)){%> selected<%} %>>通用机打发票</option>
                
                    <option value="4" <%if("4".equals(fplx)){%> selected<%} %>>卷式发票</option>
                
                    <option value="5" <%if("5".equals(fplx)){%> selected<%} %>>定额发票</option>
                
                    <option value="6" <%if("6".equals(fplx)){%> selected<%} %>>地铁票</option>
                
                    <option value="7" <%if("7".equals(fplx)){%> selected<%} %>>出租发票</option>
                
                    <option value="8" <%if("8".equals(fplx)){%> selected<%} %>>动车/高铁票</option>
                
                    <option value="9" <%if("9".equals(fplx)){%> selected<%} %>>过路费发票</option>
                
                    <option value="10" <%if("10".equals(fplx)){%> selected<%} %>>客运汽车发票</option>
                
                    <option value="11" <%if("11".equals(fplx)){%> selected<%} %>>二手车销售统一发票</option>
                
                    <option value="12" <%if("12".equals(fplx)){%> selected<%} %>>机动车销售统一发票</option>
                
                    <option value="13" <%if("13".equals(fplx)){%> selected<%} %>>国际小票</option>
                
                    <option value="14" <%if("14".equals(fplx)){%> selected<%} %>>航空运输电子客票行程单</option>
                
                    <option value="15" <%if("15".equals(fplx)){%> selected<%} %>>增值税电子发票</option>
                
                    <option value="16" <%if("16".equals(fplx)){%> selected<%} %>>增值税普通发票（卷票）</option>


						</select>
					</wea:item>
			
				<wea:item>日期</wea:item>
                <wea:item>
                    <button type="button" class=Calendar id="selectBeginDate" onclick="onshowPlanDate('beginDate','selectBeginDateSpan')"></BUTTON>
                        <SPAN id=selectBeginDateSpan ><%=beginDate%></SPAN>
                        <INPUT type="hidden" name="beginDate" id="beginDate" value="<%=beginDate%>">
                    &nbsp;-&nbsp;
                    <button type="button" class=Calendar id="selectEndDate" onclick="onshowPlanDate('endDate','endDateSpan')"></BUTTON>
                        <SPAN id=endDateSpan><%=endDate%></SPAN>
                        <INPUT type="hidden" name="endDate" id="endDate" value="<%=endDate%>">
        </wea:item>
				<wea:item>主体账套</wea:item>
				<wea:item>
                 <input name="ztzt" id="ztzt" class="InputStyle" type="text" value="<%=ztzt%>"/>
        </wea:item>
				<wea:item>业务类型</wea:item>
				<wea:item>
                 <input name="ywlx" id="ywlx" class="InputStyle" type="text" value="<%=ywlx%>"/>
        </wea:item>

				</wea:group>
				<wea:group context="">
				<wea:item type="toolbar">
				<input type="button" value="<%=SystemEnv.getHtmlLabelName(30947,user.getLanguage())%>" class="e8_btn_submit" onclick="onBtnSearchClick();"/>
				<input type="button" value="<%=SystemEnv.getHtmlLabelName(31129,user.getLanguage())%>" class="e8_btn_cancel" id="cancel"/>
				</wea:item>
				</wea:group>
				</wea:layout>
			</div>
		</FORM>
		
		<%
		String backfields = "requestid,dtid,keyid,workflowid,(select workflowname from workflow_base where id=a.workflowid) as workflowname,lastoperatedate,requestmark,userid_new,sqr,sqryx,cbzx1,cbzxbmmc,gszt,invoicetype,km,ywlx,je,se,kdkje,bz"+
		",case when invoicetype='1' then '增值税普通发票' when invoicetype='2' then '增值税专用发票' when invoicetype='3' then '通用机打发票' when invoicetype='4' then '卷式发票' when invoicetype='5' then '定额发票' when invoicetype='6' then '地铁票' when invoicetype='7' then '出租发票' when invoicetype='8' then '动车/高铁票' when invoicetype='9' then '过路费发票' when invoicetype='10' then '客运汽车发票' when invoicetype='11' then '二手车销售统一发票' when invoicetype='12' then '机动车销售统一发票' when invoicetype='13' then '国际小票' when invoicetype='14' then '航空运输电子客票行程单' when invoicetype='15' then '增值税电子发票' when invoicetype='16' then '增值税普通发票（卷票）' else '' end as fplxmc ";
		String fromSql  =  " from v_cw_fpshst a";
		String sqlWhere =  " 1=1 ";
	 if(!"".equals(cbzx)){
		 sqlWhere +=" and cbzx1 = '"+cbzx+"'";
	 }
	  if(!"".equals(fplx)){
		 sqlWhere +=" and invoicetype = '"+fplx+"'";
	 }
	 if(!"".equals(beginDate)){
		 sqlWhere +=" and lastoperatedate >= '"+beginDate+"'";
	 }
	 if(!"".equals(endDate)){
		 sqlWhere +=" and lastoperatedate <= '"+endDate+"'";
	 }
	if(!"".equals(djh)){
		 sqlWhere +=" and requestmark like'%"+djh+"%'";
	 }
	 if(!"".equals(ztzt)){
		 sqlWhere +=" and gszt like'%"+ztzt+"%'";
	 }	
	 if(!"".equals(ywlx)){
		 sqlWhere +=" and ywlx like'%"+ywlx+"%'";
	 }		
	

		
		
		//out.print("select "+backfields+fromSql+" where "+sqlWhere);
		String orderby =  "requestid desc "  ;
		String tableString = "";
		String operateString= "";
		tableString =" <table tabletype=\"none\" pagesize=\""+ PageIdConst.getPageSize(out_pageId,user.getUID(),PageIdConst.HRM)+"\" pageId=\""+out_pageId+"\" >"+         
				   "	   <sql backfields=\""+backfields+"\" sqlform=\""+fromSql+"\" sqlwhere=\""+Util.toHtmlForSplitPage(sqlWhere)+"\"  sqlorderby=\""+orderby+"\"  sqlprimarykey=\"keyid\" sqlsortway=\"desc\" sqlisdistinct=\"false\" />"+
		operateString+
		"			<head>";
		tableString +="<col width=\"220px\" text=\"流程类型\" column=\"workflowname\" orderkey=\"workflowname\"  />"+
		  "<col width=\"100px\" text=\"日期\" column=\"lastoperatedate\" orderkey=\"lastoperatedate\"  />"+
		  "<col width=\"100px\" text=\"单据号\" column=\"requestmark\" orderkey=\"requestmark\"  />"+ 
			"<col width=\"100px\" text=\"扫描人\" column=\"userid_new\" orderkey=\"userid_new\" transmethod='weaver.proj.util.ProjectTransUtil.getResourceNamesWithLink'/>"+ 
			"<col width=\"100px\" text=\"申请人\" column=\"sqr\" orderkey=\"sqr\"  transmethod='weaver.proj.util.ProjectTransUtil.getResourceNamesWithLink'/>"+ 
			"<col width=\"100px\" text=\"申请人部门\" column=\"sqryx\" orderkey=\"sqryx\"  transmethod=\"weaver.hrm.company.DepartmentComInfo.getDepartmentname\"/>"+ 
			"<col width=\"150px\" text=\"费用承担部门\" column=\"cbzxbmmc\" orderkey=\"cbzxbmmc\"  />"+ 
			"<col width=\"100px\" text=\"主体账套\" column=\"gszt\" orderkey=\"gszt\"  />"+ 
			"<col width=\"120px\" text=\"发票类型\" column=\"fplxmc\" orderkey=\"fplxmc\"  />"+ 
			"<col width=\"100px\" text=\"科目\" column=\"km\" orderkey=\"km\"  />"+ 
			"<col width=\"100px\" text=\"业务类型\" column=\"ywlx\" orderkey=\"ywlx\"  />"+ 
			"<col width=\"100px\"  text=\"金额\" column=\"je\" orderkey=\"je\"  />"+
			"<col width=\"100px\"  text=\"税额\" column=\"se\" orderkey=\"se\" />"+
			"<col width=\"100px\" text=\"可抵扣税金\" column=\"kdkje\" orderkey=\"kdkje\"  />"+ 
			"<col width=\"200px\" text=\"备注\" column=\"bz\" orderkey=\"bz\"  />"+ 
						"</head>"+
		 "</table>";
		
	//showExpExcel="false"
	%>
	<TABLE width="100%">
	  <tr>
		<td>
			<wea:SplitPageTag isShowTopInfo="false" tableString="<%=tableString%>" mode="run" showExpExcel="false"/>
		</td>
	  </tr>
	</TABLE>
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