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
    String out_pageId = "gnsreportlld_1";
	String sql = "";
	
	String wlmc = Util.null2String(request.getParameter("wlmc"));

	String flag1 =Util.null2String(request.getParameter("flag1"));

	String yjcbzx = Util.null2String(request.getParameter("yjcbzx"));
	String beginDate = Util.null2String(request.getParameter("beginDate"));
	String endDate = Util.null2String(request.getParameter("endDate"));
	String zt = Util.null2String(request.getParameter("zt"));
	String bm = Util.null2String(request.getParameter("bm"));
	String year = Util.null2String(request.getParameter("year"));
	String yf = Util.null2String(request.getParameter("yf"));
	String nppdl = Util.null2String(request.getParameter("nppdl"));
	String nppxl = Util.null2String(request.getParameter("nppxl"));
	String month = "";
	if(!"".equals(yf)&&!"".equals(year)){
		if(yf.length()<=1) {
			month=year+"0"+yf;
		}else{
			month=year+yf;
		}
	}
	String cbzx = Util.null2String(request.getParameter("cbzx"));
	String cbzxspan = "";
	 if(!"".equals(cbzx)){
      sql = "select cbzxbmmc from uf_cbzx where id="+cbzx;
      rs.executeSql(sql);
      if(rs.next()){
          cbzxspan = Util.null2String(rs.getString("cbzxbmmc"));
      }
  }
	GetGNSTableName gg = new GetGNSTableName();
	String lrktablename = gg.getTableName("RKD");
	String cgdtablename = gg.getTableName("CGDD");
	String cgsqtablename = gg.getTableName("CGSQ");
	String lldtablename = gg.getTableName("LXLL");
	//out.print("begindate"+begindate+" enddate"+enddate);
		if("".equals(flag1)&&userid!=1){
		sql = " select departmentid from hrmresource where id=dbo.emp_leaps_fuc_6("+userid+",50,1)";
		rs.executeSql(sql);
		if(rs.next()){
			bm = Util.null2String(rs.getString("departmentid"));
		}

	}
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
		<FORM id=report name=report action="/goodbaby/gnsbb/gns-report-lld.jsp" method=post>
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
				<wea:item>50部门</wea:item>
				<wea:item>
					<brow:browser name='bm'
					viewType='0'
					browserValue='<%=bm%>'
					isMustInput='1'
					browserSpanValue='<%=Util.toScreen(DepartmentComInfo.getDepartmentname(bm),user.getLanguage())%>'
					hasInput='true'
					linkUrl='/hrm/company/HrmDepartmentDsp.jsp?id='
					completeUrl='/data.jsp?type=4'
					width='60%'
					isSingle='true'
					hasAdd='false'
					browserUrl='/systeminfo/BrowserMain.jsp?url=/hrm/company/DepartmentBrowser.jsp?resourceids=#id#'>
					</brow:browser>
				</wea:item>
				<wea:item>NPP大类</wea:item>
				<wea:item>
					<brow:browser name='nppdl'
					viewType='0'
					browserValue='<%=nppdl%>'
					isMustInput='1'
					browserSpanValue='<%=nppdl%>'
					hasInput='true'
					linkUrl=''
					completeUrl=''
					width='60%'
					isSingle='true'
					hasAdd='false'
					browserUrl='/systeminfo/BrowserMain.jsp?url=/interface/CommonBrowser.jsp?type=browser.nppBIG'>
					</brow:browser>
			 </wea:item>
			 <wea:item>NPP小类</wea:item>
				<wea:item>
					<brow:browser name='nppxl'
					viewType='0'
					browserValue='<%=nppxl%>'
					isMustInput='1'
					browserSpanValue='<%=nppxl%>'
					hasInput='true'
					linkUrl=''
					completeUrl=''
					width='60%'
					isSingle='true'
					hasAdd='false'
					browserUrl='/systeminfo/BrowserMain.jsp?url=/interface/CommonBrowser.jsp?type=browser.NPP'>
					</brow:browser>
			 </wea:item>
				 
				<wea:item>一级成本中心</wea:item>
				<wea:item>
					<brow:browser name='yjcbzx'
					viewType='0'
					browserValue='<%=yjcbzx%>'
					isMustInput='1'
					browserSpanValue='<%=yjcbzx%>'
					hasInput='true'
					linkUrl=''
					completeUrl=''
					width='60%'
					isSingle='true'
					hasAdd='false'
					browserUrl='/systeminfo/BrowserMain.jsp?url=/interface/CommonBrowser.jsp?type=browser.yjcbzx'>
					</brow:browser>
			 </wea:item>
					<wea:item>成本中心</wea:item>
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
			
				<wea:item>物料名称</wea:item>
				<wea:item>
                 <input name="wlmc" id="wlmc" class="InputStyle" type="text" value="<%=wlmc%>"/>
        </wea:item>
				<wea:item>领料日期</wea:item>
                <wea:item>
                    <button type="button" class=Calendar id="selectBeginDate" onclick="onshowPlanDate('beginDate','selectBeginDateSpan')"></BUTTON>
                        <SPAN id=selectBeginDateSpan ><%=beginDate%></SPAN>
                        <INPUT type="hidden" name="beginDate" id="beginDate" value="<%=beginDate%>">
                    &nbsp;-&nbsp;
                    <button type="button" class=Calendar id="selectEndDate" onclick="onshowPlanDate('endDate','endDateSpan')"></BUTTON>
                        <SPAN id=endDateSpan><%=endDate%></SPAN>
                        <INPUT type="hidden" name="endDate" id="endDate" value="<%=endDate%>">
        </wea:item>
				<wea:item>年份</wea:item>
					<wea:item>
						<brow:browser name='year'
						viewType='0'
						browserValue='<%=year%>'
						isMustInput='1'
						browserSpanValue='<%=year%>'
						hasInput='true'
						linkUrl=''
						completeUrl='/data.jsp?type=178'
						width='60%'
						isSingle='true'
						hasAdd='false'
						browserUrl='/systeminfo/BrowserMain.jsp?url=/workflow/field/Workflow_FieldYearBrowser.jsp?resourceids=#id#'>
						</brow:browser>
					</wea:item>
					<wea:item>月份</wea:item>
					<wea:item>
						<select class="e8_btn_top middle" name="yf" id="yf">
							<option value="" <%if("".equals(yf)){%> selected<%} %>>
								<%=""%></option>
							<option value="1" <%if("1".equals(yf)){%> selected<%} %>>
								<%="1月"%></option>
							<option value="2" <%if("2".equals(yf)){%> selected<%} %>>
								<%="2月"%></option>
							<option value="3" <%if("3".equals(yf)){%> selected<%} %>>
								<%="3月"%></option>
							<option value="4" <%if("4".equals(yf)){%> selected<%} %>>
								<%="4月"%></option>
							<option value="5" <%if("5".equals(yf)){%> selected<%} %>>
								<%="5月"%></option>						
							<option value="6" <%if("6".equals(yf)){%> selected<%} %>>
								<%="6月"%></option>
							<option value="7" <%if("7".equals(yf)){%> selected<%} %>>
								<%="7月"%></option>
							<option value="8" <%if("8".equals(yf)){%> selected<%} %>>
								<%="8月"%></option>
							<option value="9" <%if("9".equals(yf)){%> selected<%} %>>
								<%="9月"%></option>
							<option value="10" <%if("10".equals(yf)){%> selected<%} %>>
								<%="10月"%></option>
							<option value="11" <%if("11".equals(yf)){%> selected<%} %>>
								<%="11月"%></option>
							<option value="12" <%if("12".equals(yf)){%> selected<%} %>>
								<%="12月"%></option>


						</select>
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
		String backfields = "a.SLR,a.zjlbm,(SUBSTRING(b.createdate,0,5)+SUBSTRING(b.createdate,6,2)) as cwny,(select CKMC from uf_stocks where id=a.CK) as shckmc,a.lcbh,(select t1.cgsqdbh from "+cgsqtablename+" t1,"+cgsqtablename+"_dt1 t2 where t1.id=t2.mainid and t2.id=(select dtid from "+cgdtablename+" where requestid=g.cgsqd )) as cgsqdbh,f.RKDH,(select cbzxbmmc from uf_cbzx where id=a.cbzx) as CBZX,(select yjcbzx from uf_cbzx where id=a.cbzx) as yjcbzx,c.WLBH_1,h.wlmc,h.xh,h.GG,(select dw from uf_unitForms where id=h.dw) as dw,d.rkjg as dj,d.rksl,d.rate,cast(d.rkjg*d.rksl as numeric(18,2)) as je,b.createdate,case when b.currentnodetype=3 then '审批完成' when b.currentnodetype=0 then '发起' else '审批中' end as zt,dbo.get_gns_fykm(isnull(f.fykm,''),f.cbzx,g.WLMC_1,'0') as fymc,dbo.get_gns_fykm(isnull(f.fykm,''),f.cbzx,g.WLMC_1,'1') as fydm";
		String fromSql  =  " from "+lldtablename+" a,workflow_requestbase b,"+lldtablename+"_dt1 c ,uf_stock d ,uf_stock e,"+lrktablename+" f,"+lrktablename+"_dt1 g,uf_materialDatas h";
		String sqlWhere =  " a.requestid=b.requestid and a.id=c.mainid  and a.requestid=d.lch and c.id=d.dtid and d.glrk=e.id and e.lch=f.requestid and f.id=g.mainid and c.WLMC_2=h.id and d.rkly=1 and b.currentnodetype=3  ";
		if(!"".equals(bm)){
			sqlWhere +=" and a.zjlbm = '"+bm+"' ";
		}
		if(!"".equals(yjcbzx)){
			sqlWhere +=" and (select yjcbzx from uf_cbzx where id=a.cbzx) = '"+yjcbzx+"' ";
		}
		if(!"".equals(cbzx)){
			sqlWhere +=" and a.cbzx = '"+cbzx+"' ";
		}
		if(!"".equals(nppxl)){
			sqlWhere +=" and (select wllx from uf_materialDatas where id=c.WLMC_2) ='"+nppxl+"' ";
		}
		if(!"".equals(nppdl)){
			sqlWhere +=" and (select (select zml from uf_NPP where id=wllx)  from uf_materialDatas where id=c.WLMC_2) ='"+nppdl+"' ";
		}
		
		if(!"".equals(wlmc)){
			sqlWhere +=" and (select wlmc from uf_materialDatas where id=c.WLMC_2) like '%"+wlmc+"%' ";
		}
		if(!"".equals(beginDate)){
			sqlWhere +=" and b.createdate >='"+beginDate+"' ";
		}
		if(!"".equals(endDate)){
			sqlWhere +=" and b.createdate <='"+endDate+"' ";
		}

		if(!"".equals(month)){
			sqlWhere +=" and (SUBSTRING(b.createdate,0,5)+SUBSTRING(b.createdate,6,2)) ='"+month+"' ";
		}
		
		
	

		
		
		
		

		out.print("select "+backfields+fromSql+" where "+sqlWhere);
		String orderby =  "a.requestid "  ;
		String tableString = "";
		String operateString= "";
		tableString =" <table tabletype=\"none\" pagesize=\""+ PageIdConst.getPageSize(out_pageId,user.getUID(),PageIdConst.HRM)+"\" pageId=\""+out_pageId+"\" >"+         
				   "	   <sql backfields=\""+backfields+"\" sqlform=\""+fromSql+"\" sqlwhere=\""+Util.toHtmlForSplitPage(sqlWhere)+"\"  sqlorderby=\""+orderby+"\"  sqlprimarykey=\"a.requestid\" sqlsortway=\"desc\" sqlisdistinct=\"false\" />"+
		operateString+
		"			<head>";
		tableString +="<col width=\"200px\" text=\"部门\" column=\"SLR\" orderkey=\"SLR\"  transmethod=\"goodbaby.gns.bb.BbTransUtil.getPersonAllDepartmentName\"/>"+
		  "<col width=\"100px\" text=\"50部门\" column=\"zjlbm\" orderkey=\"zjlbm\"  transmethod=\"weaver.hrm.company.DepartmentComInfo.getDepartmentname\"/>"+
		  "<col width=\"100px\" text=\"财务年月\" column=\"cwny\" orderkey=\"cwny\"  />"+ 
			"<col width=\"100px\" text=\"领料仓库\" column=\"shckmc\" orderkey=\"shckmc\" />"+ 
			"<col width=\"100px\" text=\"领料单号\" column=\"lcbh\" orderkey=\"lcbh\" />"+ 
			"<col width=\"100px\" text=\"采购申请编号/合同编号\" column=\"cgsqdbh\" orderkey=\"cgsqdbh\"  />"+ 
			"<col width=\"100px\" text=\"入库单编号\" column=\"rkdh\" orderkey=\"rkdh\"  />"+ 
			"<col width=\"100px\" text=\"一级成本中心\" column=\"yjcbzx\" orderkey=\"yjcbzx\"  />"+
			"<col width=\"100px\" text=\"一级成本中心\" column=\"cbzx\" orderkey=\"cbzx\"  />"+
			"<col width=\"100px\" text=\"物料代码\" column=\"WLBH_1\" orderkey=\"WLBH_1\"  />"+ 
			"<col width=\"100px\"  text=\"物料名称\" column=\"WLMC\" orderkey=\"WLMC\"  />"+
			"<col width=\"100px\"  text=\"型号\" column=\"xh\" orderkey=\"xh\" />"+
			"<col width=\"100px\" text=\"规格\" column=\"GG\" orderkey=\"GG\"  />"+ 
			"<col width=\"100px\" text=\"单位\" column=\"dw\" orderkey=\"dw\"  />"+ 	
			"<col width=\"100px\" text=\"P0/单价\" column=\"dj\" orderkey=\"dj\"  />"+ 		
			"<col width=\"100px\" text=\"领料数量\" column=\"rksl\" orderkey=\"rksl\"  />"+ 
			"<col width=\"100px\" text=\"税率\" column=\"rate\" orderkey=\"rate\"  />"+
			"<col width=\"100px\" text=\"金额\" column=\"je\" orderkey=\"je\"  />"+ 
			"<col width=\"100px\" text=\"领料日期\" column=\"createdate\" orderkey=\"createdate\"  />"+ 
			"<col width=\"100px\" text=\"申请人\" column=\"SLR\" orderkey=\"SLR\"  />"+ 
			"<col width=\"100px\" text=\"状态\" column=\"zt\" orderkey=\"zt\"  />"+ 	 
			"<col width=\"100px\" text=\"费用名称\" column=\"fymc\" orderkey=\"fymc\"  />"+ 
			"<col width=\"100px\" text=\"费用代码\" column=\"fydm\" orderkey=\"fydm\"  />"+ 
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