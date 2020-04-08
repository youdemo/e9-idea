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
  String out_pageId = "gnsreportrkwkpzg_1";
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
		<FORM id=report name=report action="/goodbaby/gnsbb/gns-report-rkwkpzg.jsp" method=post>
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
		String backfields = "gsmc,gsdm,fydm,kmmc,yjcbzx,xtgys,gysbh,gysmc,shckmc,shck,yygfbh,yygfmc,qcje,qmje,bqzj,je1,je2,je3,je4,je5,je6,je7,je8,je9,je10";
		String fromSql  =  " from (select (select gsmc from uf_yjcbzxdzb where yjcbzxmc=a.yjcbzx) as gsmc,(select ksdm from uf_yjcbzxdzb where yjcbzxmc=a.yjcbzx) as gsdm,fydm,(select kmmc from uf_fykm  where kmbm1=a.fydm) as kmmc,yjcbzx,xtgys,gysbh,gysmc,shckmc,shck,(select YYGFBM from uf_suppmessForm where GYSBM=a.gysbh)  as yygfbh,(select YYGFMC from uf_suppmessForm where GYSBM=a.gysbh)  as yygfmc"+
",dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'0','') as qcje,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'1','') as qmje,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'2','') as bqzj,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','1') as je1,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','2') as je2,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','3') as je3,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','4') as je4,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','5') as je5,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','6') as je6,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','7') as je7,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','8') as je8,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','9') as je9,dbo.f_get_gnsbb_rkwkpje(fydm,yjcbzx,xtgys,shck,'3','10') as je10"+
" from v_gnsbb_rkdwkp a group by fydm,yjcbzx,xtgys,gysbh,shckmc,shck,gysmc) a";
		String sqlWhere =  " 1=1 ";
		
		//out.print("select "+backfields+fromSql+" where "+sqlWhere);
		String orderby =  "a.gysbh"  ;
		String tableString = "";
		String operateString= "";
		tableString =" <table tabletype=\"none\" pagesize=\""+ PageIdConst.getPageSize(out_pageId,user.getUID(),PageIdConst.HRM)+"\" pageId=\""+out_pageId+"\" >"+         
				   "	   <sql backfields=\""+backfields+"\" sqlform=\""+fromSql+"\" sqlwhere=\""+Util.toHtmlForSplitPage(sqlWhere)+"\"  sqlorderby=\""+orderby+"\"  sqlprimarykey=\"a.gysbh\" sqlsortway=\"desc\" sqlisdistinct=\"false\" />"+
		operateString+
		"			<head>";
		tableString +=
		  "<col width=\"100px\" text=\"NC公司代码\" column=\"gsmc\" orderkey=\"gsmc\" />"+ 
			"<col width=\"100px\" text=\"NC公司名称\" column=\"gsdm\" orderkey=\"gsdm\" />"+ 
			"<col width=\"100px\" text=\"NC科目代码\" column=\"fydm\" orderkey=\"fydm\" />"+ 
			"<col width=\"100px\" text=\"NC科目名称\" column=\"kmmc\" orderkey=\"kmmc\"  />"+ 
			"<col width=\"100px\" text=\"一级成本中心\" column=\"yjcbzx\" orderkey=\"yjcbzx\"  />"+
			"<col width=\"100px\" text=\"供方编号\" column=\"gysbh\" orderkey=\"gysbh\"  />"+ 
			"<col width=\"100px\"  text=\"供方名称\" column=\"gysmc\" orderkey=\"gysmc\"  />"+
			"<col width=\"100px\"  text=\"用友供方编号\" column=\"yygfbh\" orderkey=\"yygfbh\" />"+
			"<col width=\"100px\" text=\"用友供方名称\" column=\"yygfmc\" orderkey=\"yygfmc\"  />"+ 	
			"<col width=\"100px\" text=\"仓库名称\" column=\"shckmc\" orderkey=\"shckmc\"  />"+ 		
			"<col width=\"100px\" text=\"期初未回报金额\" column=\"qcje\" orderkey=\"qcje\"  />"+ 
			"<col width=\"200px\" text=\"期末未回报金额\" column=\"qmje\" orderkey=\"qmje\" />"+ 
			"<col width=\"100px\" text=\"本期增减\" column=\"bqzj\" orderkey=\"bqzj\"  />"+ 
			"<col width=\"100px\" text=\"<=30天\" column=\"je1\" orderkey=\"je1\"  />"+ 
			"<col width=\"100px\" text=\"31-60天\" column=\"je2\" orderkey=\"je2\"  />"+ 	 
			"<col width=\"100px\" text=\"61-90天\" column=\"je3\" orderkey=\"je3\"  />"+ 
			"<col width=\"100px\" text=\"91-120天\" column=\"je4\" orderkey=\"je4\"  />"+ 
			"<col width=\"100px\" text=\"121-180天\" column=\"je5\" orderkey=\"je5\"  />"+ 
			"<col width=\"100px\" text=\"181-240天\" column=\"je6\" orderkey=\"je6\"  />"+ 
			"<col width=\"100px\" text=\"241天-1年\" column=\"je7\" orderkey=\"je7\"  />"+ 
			"<col width=\"100px\" text=\"1-2年\" column=\"je8\" orderkey=\"je8\"  />"+ 
			"<col width=\"100px\" text=\"2-3年\" column=\"je9\" orderkey=\"je9\"  />"+ 
			"<col width=\"100px\" text=\"3年以上\" column=\"je10\" orderkey=\"je10\"  />"+ 
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