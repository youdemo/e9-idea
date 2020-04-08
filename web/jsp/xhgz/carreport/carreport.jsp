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
int jsrcount = 0;
String sql = "select count(1) as count from (select jsy from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype=3 and ddapccrq='"+searchday+"' and jsy is not null group by jsy)a";
rs.execute(sql);
if(rs.next()){
    jsrcount = rs.getInt("count");
}
int width = 100+jsrcount*200;
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
			WIDTH: <%=width%>px;
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
         .table-body{width:100%;overflow-y:auto;overflow-x: auto}
        .fvalue1{
            border:1px solid black !important;
        }
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
	<div style="width:<%=width%>px;  margin:0 auto;"  >
     <%if(jsrcount>0){%>
	<div class="table-head" style="width:<%=width%>px;  margin:0 auto;">
			<table class="ViewForm1  outertable" scrolling="auto">
    <tbody>		
        <tr>			
            <td>
            <table class="ViewForm1  maintable" scrolling="auto">
                <colgroup>  <col width="100px"></col>
                    <%for(int i=0;i<jsrcount;i++){%>
                    <col width="200px"></col>
                    <%}%>
				 </colgroup>
                <tbody>
				<tr style="background-color:#C0C0C0">

					<td class="fvalue1"  align="center" colspan="<%=1+jsrcount%>" ><%=year%>年<%=month%>月<%=day%>日车辆使用情况</td>
				</tr>
                <tr style="background-color:#B8CCE4">
                    <td class="fvalue1"  align="center">驾驶员</td>
                <%
                    String jsrarr[]=new String[jsrcount];
                    Map<String,Object> map = new HashMap<String,Object>();
                    sql = "select jsy,(select lastname from hrmresource where id=a.jsy) as jsyname from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype=3 and ddapccrq='"+searchday+"' and jsy is not null group by jsy";
                    rs.execute(sql);
                    int i =0;
                    while(rs.next()){
                        String jsy = Util.null2String(rs.getString("jsy"));
                        String jsyname = Util.null2String(rs.getString("jsyname"));
                        jsrarr[i]=jsy;
                        i++;
                %>

                    <td class="fvalue1"  align="center" ><%=jsyname%></td>

                <%
                    }

                %>
                </tr>
                <tr style="background-color:#B8CCE4">
                    <td class="fvalue1"  align="center">上班时间</td>
                    <%
                       for(String jsrid:jsrarr){
                           String sj = "";
                           sql="select sbsj+'~'+xbsj as sj from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype=3 and ddapccrq='"+searchday+"' and jsy="+jsrid+" and (sbsj is not null and sbsj <> '' )";
                           rs.execute(sql);
                           if(rs.next()){
                               sj = Util.null2String(rs.getString("sj"));
                           }
                    %>

                    <td class="fvalue1"  align="center" ><%=sj%></td>

                    <%
                        }

                    %>
                </tr>
                <%
                    int maxsize = 0;
                    for(String jsrid:jsrarr){
                        ArrayList<String> list = new ArrayList<String>();
                        sql="select ddapccsj,ddapfhsj,ccrymd,krmd1,mdd,(select t2.selectname from workflow_billfield t, workflow_bill t1,workflow_selectitem t2 where t.billid=t1.id and t2.fieldid=t.id  and t1.tablename='formtable_main_68' and t.fieldname='ch' and t2.selectvalue=a.ch) as ch from formtable_main_68 a,workflow_requestbase b where a.requestId=b.requestid and b.currentnodetype=3 and ddapccrq='"+searchday+"' and jsy="+jsrid+" order by ddapccsj asc";
                        rs.execute(sql);
                        while(rs.next()){
                            String ccrymd = "";
                            String ch = "";
                            String ccrynames ="";
                            String krmd =  Util.null2String(rs.getString("krmd1"));
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
                            if("".equals(ccrynames)){
                                ccrynames = krmd;
                            }else{
                                ccrynames =ccrynames+","+krmd;
                            }

                            String ycqk = Util.null2String(rs.getString("ddapccsj"))+"出发，乘车人："+ccrynames+"，目的地:"+Util.null2String(rs.getString("mdd"))+"，"+Util.null2String(rs.getString("ddapfhsj"))+"返回";
                            ch = Util.null2String(rs.getString("ch"));

                            list.add(ch+"###"+ycqk);
                        }
                        if(maxsize<list.size()){
                            maxsize = list.size();
                        }
                        map.put(jsrid,list);
                        map.put("size_"+jsrid,list.size());
                    }
                    for(int j =0 ;j<maxsize;j++){
                        if(j%2 ==0){
                    %>
                      <tr>
                    <%
                        }else{
                      %>
                         <tr style="background-color:#D8E4BC">
                    <%
                        }
                    %>

                       <td class="fvalue1"  align="center">车辆</td>
                    <%
                        for(String jsrid:jsrarr){
                           String cph = "";
                           if((int) map.get("size_"+jsrid)>j){
                               cph=((ArrayList<String>) map.get(jsrid)).get(j).split("###")[0];
                           }
                        %>

                           <td class="fvalue1"  align="center"><%=cph%></td>
                         <%
                        }
                        %>
                      </tr>
                        <%
                        if(j%2 ==0){
                        %>
                        <tr>
                                <%
                                }else{
                              %>
                        <tr style="background-color:#D8E4BC">
                            <%
                                }
                            %>
                    <td class="fvalue1"  align="center">用车情况</td>
                    <%
                            for(String jsrid:jsrarr){
                                String ycqk = "";
                                if((int) map.get("size_"+jsrid)>j){
                                    ycqk=((ArrayList<String>) map.get(jsrid)).get(j).split("###")[1];
                                }
                                %>

                                    <td class="fvalue1"  align="center"><%=ycqk%></td>
                                 <%
                            }
                     %>
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
        <%}%>

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