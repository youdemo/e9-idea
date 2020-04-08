<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.util.*,weaver.hrm.appdetach.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="goodbaby.pz.*"%>
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
  String out_pageId = "gnsreportxjd_1";
	String sql = "";
	String cgdl = Util.null2String(request.getParameter("cgdl"));
	String cgdlspan = "";
  if(!"".equals(cgdl)){
      sql = "select buydl from uf_buydl where id="+cgdl;
      rs.executeSql(sql);
      if(rs.next()){
          cgdlspan = Util.null2String(rs.getString("buydl"));
      }
  }
	String xjdh = Util.null2String(request.getParameter("xjdh"));
	String wlmc = Util.null2String(request.getParameter("wlmc"));
	String wlbm = Util.null2String(request.getParameter("wlbm"));
	String beginDate = Util.null2String(request.getParameter("beginDate"));
	String endDate = Util.null2String(request.getParameter("endDate"));
	String beginDate1 = Util.null2String(request.getParameter("beginDate1"));
	String endDate1 = Util.null2String(request.getParameter("endDate1"));
	GetGNSTableName gg = new GetGNSTableName();
	String lrktablename = gg.getTableName("RKD");
	//out.print("begindate"+begindate+" enddate"+enddate);
		
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
		//RCMenu += "{导出凭证,javascript:createpz(),_self} " ;
		RCMenu += "{导出Excel,javascript:_xtable_getAllExcel(),_self} " ;
		RCMenuHeight += RCMenuHeightStep ;
		
		RCMenuHeight += RCMenuHeightStep ; 
		%>
		<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>
		<FORM id=report name=report action="/goodbaby/gnsbb/gns-report-xjd.jsp" method=post>
			<input type="hidden" name="requestid" value="">
			<input type="hidden" name="flag1" value="1">
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
				<wea:item>询价单号</wea:item>
				<wea:item>
                 <input name="xjdh" id="xjdh" class="InputStyle" type="text" value="<%=xjdh%>"/>
        </wea:item>
			  <wea:item>物料类型</wea:item>
                <wea:item>
                    <brow:browser viewType="0" name="cgdl" id="cgdl"
                                  browserValue="<%=cgdl%>"
                                  browserUrl="/systeminfo/BrowserMain.jsp?url=/interface/CommonBrowser.jsp?type=browser.btn_buydl01"
                                  hasInput="true" hasBrowser="true" isMustInput='1'
                                  isSingle="false"
                                  width="165px"
                                  linkUrl=""
                                  browserSpanValue="<%=cgdlspan%>">
                    </brow:browser>
          </wea:item>
			  <wea:item>物料编码</wea:item>
				<wea:item>
                 <input name="wlbm" id="wlbm" class="InputStyle" type="text" value="<%=wlbm%>"/>
        </wea:item>
				<wea:item>物料名称</wea:item>
				<wea:item>
                 <input name="wlmc" id="wlmc" class="InputStyle" type="text" value="<%=wlmc%>"/>
        </wea:item>
				<wea:item>询价日期</wea:item>
                <wea:item>
                    <button type="button" class=Calendar id="selectBeginDate" onclick="onshowPlanDate('beginDate','selectBeginDateSpan')"></BUTTON>
                        <SPAN id=selectBeginDateSpan ><%=beginDate%></SPAN>
                        <INPUT type="hidden" name="beginDate" id="beginDate" value="<%=beginDate%>">
                    &nbsp;-&nbsp;
                    <button type="button" class=Calendar id="selectEndDate" onclick="onshowPlanDate('endDate','endDateSpan')"></BUTTON>
                        <SPAN id=endDateSpan><%=endDate%></SPAN>
                        <INPUT type="hidden" name="endDate" id="endDate" value="<%=endDate%>">
        </wea:item>
				<wea:item>截至日期</wea:item>
                <wea:item>
                    <button type="button" class=Calendar id="selectBeginDate1" onclick="onshowPlanDate('beginDate1','selectBeginDateSpan1')"></BUTTON>
                        <SPAN id=selectBeginDateSpan1 ><%=beginDate1%></SPAN>
                        <INPUT type="hidden" name="beginDate1" id="beginDate1" value="<%=beginDate1%>">
                    &nbsp;-&nbsp;
                    <button type="button" class=Calendar id="selectEndDate1" onclick="onshowPlanDate('endDate1','endDateSpan1')"></BUTTON>
                        <SPAN id=endDateSpan1><%=endDate1%></SPAN>
                        <INPUT type="hidden" name="endDate1" id="endDate1" value="<%=endDate1%>">
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
		String backfields = "a.requestid,XJDH,(select buydl from uf_buydl where id=a.CGDL) as CGDL,WLBM,WLMC,PP,XH,GG,SL,(select dw from uf_unitForms where id=a.DW)as dw,(select taxname from uf_tax_rate where id=a.rate) as rate,YT,FJ,(select count(1) from formtable_main_228_dt2 where mainid=a.id ) as gyssl,case when b.currentnodetype=3 then b.lastoperatedate  else '' end as wcbj,MRBJSJ,XJRQ";
		String fromSql  =  " from formtable_main_228 a,workflow_requestbase b";
		String sqlWhere =  " a.requestid=b.requestid ";
		if(!"".equals(xjdh)){
			sqlWhere +=" and XJDH like '%"+xjdh+"%' ";
		}
	  if(!"".equals(cgdl)){
			sqlWhere +=" and a.cgdl = '"+cgdl+"' ";
		}

		if(!"".equals(wlmc)){
			sqlWhere +=" and WLMC like '%"+wlmc+"%' ";
		}
		if(!"".equals(wlbm)){
			sqlWhere +=" and WLBM like '%"+wlbm+"%' ";
		}

		if(!"".equals(beginDate)){
			sqlWhere +=" and a.XJRQ >='"+beginDate+"' ";
		}
		if(!"".equals(endDate)){
			sqlWhere +=" and a.XJRQ <='"+endDate+"' ";
		}
		if(!"".equals(beginDate1)){
			sqlWhere +=" and a.MRBJSJ >='"+beginDate1+"' ";
		}
		if(!"".equals(endDate1)){
			sqlWhere +=" and a.MRBJSJ <='"+endDate1+"' ";
		}
		//out.print("select "+backfields+fromSql+" where "+sqlWhere);
		String orderby =  "a.requestid "  ;
		String tableString = "";
		String operateString= "";
		tableString =" <table tabletype=\"none\" pagesize=\""+ PageIdConst.getPageSize(out_pageId,user.getUID(),PageIdConst.HRM)+"\" pageId=\""+out_pageId+"\" >"+         
				   "	   <sql backfields=\""+backfields+"\" sqlform=\""+fromSql+"\" sqlwhere=\""+Util.toHtmlForSplitPage(sqlWhere)+"\"  sqlorderby=\""+orderby+"\"  sqlprimarykey=\"a.requestid\" sqlsortway=\"desc\" sqlisdistinct=\"false\" />"+
		operateString+
		"			<head>";
		tableString +=
		  "<col width=\"100px\" text=\"询价单号\" column=\"XJDH\" orderkey=\"XJDH\"  linkvaluecolumn=\"requestid\" linkkey=\"requestid\" href=\"/workflow/request/ViewRequest.jsp\" target=\"_fullwindow\"/>"+ 
			"<col width=\"100px\" text=\"物料类型\" column=\"CGDL\" orderkey=\"CGDL\" />"+ 
			"<col width=\"100px\" text=\"物料编号\" column=\"WLBM\" orderkey=\"WLBM\" />"+ 
			"<col width=\"100px\" text=\"物料名称\" column=\"WLMC\" orderkey=\"WLMC\"  />"+ 
			"<col width=\"100px\" text=\"品牌\" column=\"PP\" orderkey=\"PP\"  />"+
			"<col width=\"100px\" text=\"型号\" column=\"XH\" orderkey=\"XH\"  />"+ 
			"<col width=\"100px\"  text=\"规格\" column=\"GG\" orderkey=\"GG\"  />"+
			"<col width=\"100px\"  text=\"数量\" column=\"SL\" orderkey=\"SL\" />"+
			"<col width=\"100px\" text=\"单位\" column=\"dw\" orderkey=\"dw\"  />"+ 	
			"<col width=\"100px\" text=\"税率\" column=\"rate\" orderkey=\"rate\"  />"+ 		
			"<col width=\"100px\" text=\"用途及类别\" column=\"YT\" orderkey=\"YT\"  />"+ 
			"<col width=\"200px\" text=\"相关附件\" column=\"FJ\" orderkey=\"FJ\"   transmethod='goodbaby.gns.bb.BbTransUtil.getAttachLink'/>"+ 
			"<col width=\"100px\" text=\"供应商数量\" column=\"gyssl\" orderkey=\"gyssl\"  />"+ 
			"<col width=\"100px\" text=\"完成报价\" column=\"wcbj\" orderkey=\"wcbj\"  />"+ 
			"<col width=\"100px\" text=\"询价日期\" column=\"XJRQ\" orderkey=\"XJRQ\"  />"+ 	 
			"<col width=\"100px\" text=\"截至日期\" column=\"MRBJSJ\" orderkey=\"MRBJSJ\"  />"+ 
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