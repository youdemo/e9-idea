<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.util.*,weaver.hrm.appdetach.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.*"%>
<%@ page import="weaver.hrm.resource.ResourceComInfo" %>
<%@ include file="/systeminfo/init_wev8.jsp" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea"%>
<%@ taglib uri="/browserTag" prefix="brow"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs_dt" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />

<%
Integer lg=(Integer)user.getLanguage();
int user_id = user.getUID();
String tmc_pageId = "carreport001";
weaver.general.AccountType.langId.set(lg);
SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
String nowDate = sf.format(new Date());
String searchday = Util.null2String(request.getParameter("searchday"));
if("".equals(searchday)){
	searchday = nowDate;
}
String year =searchday.substring(0,4);
String month =searchday.substring(5,7);
if("0".equals(month.substring(0,1))){
	month = month.substring(1,2);
}
String day =searchday.substring(8,10);
if("0".equals(day.substring(0,1))){
	day = day.substring(1,2);
}


%>
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="AccountType" class="weaver.general.AccountType" scope="page" />
<jsp:useBean id="LicenseCheckLogin" class="weaver.login.LicenseCheckLogin" scope="page" />
<HTML>
	<HEAD>
		<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
		<script type="text/javascript" src="/appres/hrm/js/mfcommon_wev8.js"></script>
		<script language=javascript src="/js/ecology8/hrm/HrmSearchInit_wev8.js"></script>
		<link rel="stylesheet" href="/css/init_wev8.css" type="text/css" />
		<style>
		.checkbox {
			display: none
		}
		TABLE.ViewForm1 {
			WIDTH: 1100px;
			border:0;margin:0;
			border-collapse:collapse;
		
	   }
		TABLE.ViewForm1 TD {
			padding:0 0 0 5px;
		}
		TABLE.ViewForm1 TR {
			height: 30px;
		}
		.table-head{padding-right:20px}
         .table-body{width:100%;overflow-y:auto;overflow-x: hidden}
		</style>
		
	</head>
	<LINK href="/css/Weaver_wev8.css" type=text/css rel=STYLESHEET>
    <link href="/js/swfupload/default_wev8.css" rel="stylesheet" type="text/css" />
  <link type="text/css" rel="stylesheet" href="/css/crmcss/lanlv_wev8.css" />
  <link type="text/css" rel="stylesheet" href="/wui/theme/ecology8/skins/default/wui_wev8.css" />
	<BODY >
		<div id="tabDiv">
			<span class="toggleLeft" id="toggleLeft" title="<%=SystemEnv.getHtmlLabelName(32814,user.getLanguage()) %>"><%=SystemEnv.getHtmlLabelName(20536,user.getLanguage()) %></span>
		</div>
		<div id="dialog">
			<div id='colShow'></div>
		</div>
		<%@ include file="/systeminfo/RightClickMenuConent_wev8.jsp" %>
		<%
			RCMenu += "{刷新,javascript:refersh(),_self} " ;
		//RCMenu += "{导出,javascript:_xtable_getAllExcel(),_self} " ;
		RCMenuHeight += RCMenuHeightStep ;
		RCMenu += "{导出,javascript:daochu(),_self} " ;		
		RCMenuHeight += RCMenuHeightStep ;
		%>
		<%@ include file="/systeminfo/RightClickMenu_wev8.jsp" %>
		<FORM id=report name=report action="/xhgz/carreport/carreport.jsp" method=post>
			<input type="hidden" name="requestid" value="">
			<table id="topTitle" cellpadding="0" cellspacing="0">
				<tr>
					<td></td>
					<td class="rightSearchSpan" style="text-align:right;">
					<input type="button"  value="导出" class="e8_btn_top" onClick="daochu()" />
					<span id="advancedSearch" class="advancedSearch"><%=SystemEnv.getHtmlLabelName(21995,user.getLanguage())%></span>
						<span title="<%=SystemEnv.getHtmlLabelName(23036,user.getLanguage())%>" class="cornerMenu"></span>
					</td>
				</tr>
			</table>
			
	
			<div class="advancedSearchDiv" id="advancedSearchDiv" style="display:none;">
				<wea:layout type="4col">
				<wea:group context="查询条件">
				<wea:item>日期</wea:item>
					<wea:item>
						<button type="button" class=Calendar id="selectBeginDate" onclick="onshowPlanDate('searchday','selectBeginDateSpan')"></BUTTON>
						<SPAN id=selectBeginDateSpan ><%=searchday%></SPAN>
						<INPUT type="hidden" name="searchday" id="searchday" value="<%=searchday%>">

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
	<br/><br/>
	<div style="width:1100px;  margin:0 auto;"  >
	<div class="table-head" style="width:1100px;  margin:0 auto;">
			<table class="ViewForm1  outertable" scrolling="auto">
    <tbody>		
        <tr>			
            <td>
            <table class="ViewForm1  maintable" scrolling="auto">
                <colgroup>  <col width="100px"></col><col width="200px"></col><col width="200px"></col><col width="200px"></col><col width="200px"></col><col width="200px"></col>
				 </colgroup>
                <tbody>
				<tr>

					<td class="fname"  align="center" colspan="6" ><%=year%>年<%=month%>月<%=day%>日车辆使用情况</td>
				</tr>
				<tr>
	                    <td class="fvalue"  align="center" rowspan="2" >姓名/工号</td>
						<td class="fvalue"  align="center" colspan="2">范炳旺</td>
						<td class="fvalue"  align="center" >李永红</td>
						<td class="fvalue"  align="center" >谢江平</td>
					    <td class="fvalue"  align="center" >林小东</td>
					</tr>	
					 <tr>
						<td class="fvalue"  align="center" colspan="2">A1101001</td>
						<td class="fvalue"  align="center" >A0708003</td>
						<td class="fvalue"  align="center" >A1907019</td>
						<td class="fvalue"  align="center" >A1507010</td>
					</tr>
				<tr>
					<td class="fvalue"  align="center" >所开车辆</td>
					<td class="fvalue"  align="center" >苏E03T8V</td>
                    <td class="fvalue"  align="center" >苏EG83X0</td>
					<td class="fvalue"  align="center" >沪DPG881</td>
					<td class="fvalue"  align="center" >苏EA83B7</td>
					<td class="fvalue"  align="center" >苏E7L01H</td>
				</tr>
				<tr>
					<td class="fvalue"  align="center" >上班时间</td>
					<td class="fvalue"  align="center" >8:30~17:30</td>
                    <td class="fvalue"  align="center" >8:30~17:30</td>
					<td class="fvalue"  align="center" >8:30~17:30//7:30~16:30</td>
					<td class="fvalue"  align="center" >8:30~17:30//7:30~16:30</td>
					<td class="fvalue"  align="center" >5:30~14:30</td>
				</tr>
                <%
                ArrayList<String> list1 = new ArrayList<String>();
                ArrayList<String> list2 = new ArrayList<String>();
                ArrayList<String> list3 = new ArrayList<String>();
                ArrayList<String> list4 = new ArrayList<String>();
                ArrayList<String> list5 = new ArrayList<String>();
                String ccrymd = "";
                String ch = "";
                String sql = "select yjccsj1,yjfhsj1,ccrymd,mdd,(select lastname from hrmresource where id=a.jsy) as jsy,(select t2.selectname from workflow_billfield t, workflow_bill t1,workflow_selectitem t2 where t.billid=t1.id and t2.fieldid=t.id  and t1.tablename='formtable_main_68' and t.fieldname='ch' and t2.selectvalue=a.ch) as ch from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype<=3 and yjccrq1='"+searchday+"' order by yjccsj asc";
                rs.execute(sql);
                while(rs.next()){
                    String ccrynames ="";
                    ccrymd =  Util.null2String(rs.getString("ccrymd"));
                    String ccrymdarr[] = ccrymd.split(",");
                    String flag = "";
                    for(String ryid:ccrymdarr){
                        String name = ResourceComInfo.getResourcename(ryid);
                        if(!"".equals(name)){
                            ccrynames = ccrynames+flag+name;
                            flag="、";
                        }
                    }
                    String ycqk = Util.null2String(rs.getString("yjccsj1"))+" "+ccrynames+" "+Util.null2String(rs.getString("mdd"))+" "+Util.null2String(rs.getString("yjfhsj1"));
                    ch = Util.null2String(rs.getString("ch"));
                    if("苏E03T8V".equals(ch)){
                        list1.add(ycqk);
                    }else if("苏EG83X0".equals(ch)){
                        list2.add(ycqk);
                    }else if("沪DPG881".equals(ch)){
                        list3.add(ycqk);
                    }else if("苏EA83B7".equals(ch)){
                        list4.add(ycqk);
                    }else if("苏E7L01H".equals(ch)){
                        list5.add(ycqk);
                    }
                }
                int size1 = list1.size();
                int size2 = list2.size();
                int size3 = list3.size();
                int size4 = list4.size();
                int size5 = list4.size();
                int maxsize = size1;
                if(maxsize<size2){
                    maxsize = size2;
                }
                if(maxsize<size3){
                    maxsize = size3;
                }
                if(maxsize<size4){
                    maxsize = size4;
                }
                if(maxsize<size5){
                    maxsize = size5;
                }
                for(int i=0;i<maxsize;i++){
                    String ycqk1= "";
                    String ycqk2 = "";
                    String ycqk3 = "";
                    String ycqk4 = "";
                    String ycqk5 = "";
                    if(size1>i){
                        ycqk1 = list1.get(i);
                    }
                    if(size2>i){
                        ycqk2 = list2.get(i);
                    }
                    if(size3>i){
                        ycqk3 = list3.get(i);
                    }
                    if(size4>i){
                        ycqk4 = list4.get(i);
                    }
                    if(size5>i){
                        ycqk5 = list5.get(i);
                    }
                %>
                    <tr>
                        <td class="fvalue"  align="center" >用车情况</td>
                        <td class="fvalue"  align="center" ><%=ycqk1%></td>
                        <td class="fvalue"  align="center" ><%=ycqk2%></td>
                        <td class="fvalue"  align="center" ><%=ycqk3%></td>
                        <td class="fvalue"  align="center" ><%=ycqk4%></td>
                        <td class="fvalue"  align="center" ><%=ycqk5%></td>
                    </tr>
                <%
                }

                %>


				</tbody>
            </table>
            </td>
        </tr>
    </tbody>
</table>
	</div>

</div>	

	<script type="text/javascript">

	  
		var parentWin;
		try{
		parentWin = parent.getParentWindow(window);
		}catch(e){}
		function onBtnSearchClick() {
			report.submit();
		}
        	var parentWin="";
		function refersh() {
  			window.location.reload();
  		}
		  function onBtnSaveClick() {	
			$('#report1').submit();	
			window.close();
		}
	     function daochu(){
		   var searchday="<%=searchday%>";
		   window.open("/xhgz/carreport/exportexcel.jsp?searchday="+searchday);
		}

	</script>
		<SCRIPT language="javascript" src="/js/datetime_wev8.js"></script>
		<SCRIPT language="javascript" src="/js/selectDateTime_wev8.js"></script>
		<SCRIPT language="javascript" src="/js/JSDateTime/WdatePicker_wev8.js"></script>

	</BODY>
</HTML>