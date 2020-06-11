<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="weaver.hrm.HrmUserVarify" %>
<%@ page import="weaver.hrm.User" %>
<%@ page import="gcl.sjjk.SjUtil" %>
<%@ taglib uri="/WEB-INF/weaver.tld" prefix="wea" %>
<%@ include file="/systeminfo/init.jsp" %>
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo"
             scope="page"/>
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo"
             scope="page"/>
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo"
             scope="page"/>
<jsp:useBean id="WorkflowComInfo" class="weaver.workflow.workflow.WorkflowComInfo" scope="page" />
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page"/>
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page"/>
<jsp:useBean id="log" class="weaver.general.BaseBean" scope="page"/>
<html>
<head>
    <script type="text/javascript" src="/js/weaver.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/Weaver.css">
</head>
<%
    int emp_id = user.getUID();
    String userlang = ""+user.getLanguage();
    int no = 0;
    int num = 0;
    String sqlqx = "";
//    sqlqx = " select count(1) as no from hrmroles where id in (select roleid from hrmrolemembers where resourceid " + " = (select id from hrmresourcemanager where id=" + emp_id + " and id!=1))";
//    rs.executeSql(sqlqx);
//    if(rs.next()){
//        num = rs.getInt("no");
//    }
//
//
////    out.println("no = " + no);
//    if(!HrmUserVarify.checkUserRight("OfficialDoc:Oper",user) && no == 0){
//        response.sendRedirect("/notice/noright.jsp");
//        return;
//    }
//    if ((!HrmUserVarify.checkUserRight("OfficialDoc:Oper", user) || no == 0) || num == 0) {
//        response.sendRedirect("/notice/noright.jsp");
//        return;
//    }
    String sub_com = ResourceComInfo.getSubCompanyID("" + emp_id);
    int pagenum = Util.getIntValue(request.getParameter("pagenum"),1);
    int perpage = 10;
    String imagefilename = "/images/hdDOC.gif";
    String titlename = "流程审计查询";
    String needfav = "1";
    String needhelp = "";
    SjUtil sjUtil = new SjUtil();
    String tablename_sz = sjUtil.getBillTableName("LCSJSZ");
    String tablename_jl = sjUtil.getBillTableName("SJJKJLB");
    String sql = "select max(id) as id from "+tablename_sz+" where ','||to_char(sjry)||',' like '%,"+emp_id+",%'";
    String pzids="";
    rs.executeSql(sql);
    while (rs.next()){
        pzids = Util.null2String(rs.getString("id"));

    }
    if("".equals(pzids)){
        response.sendRedirect("/notice/noright.jsp");
        return;
    }
    String startdate = "";
    String enddate = "";
    sql = "select * from "+tablename_sz+" where id="+pzids;
    rs.executeSql(sql);
    if(rs.next()){
        startdate = Util.null2String(rs.getString("ksrq"));
        enddate = Util.null2String(rs.getString("jsrq"));
    }
    String workflowid = "" ;
    String nodetype ="" ;
    String fromdate ="" ;
    String todate ="" ;
    String creatertype ="" ;
    String createrid ="" ;
    String requestlevel ="" ;
    String requestname ="" ;//added xwj for for td2903  20051019
    String sjdxryids="";
    int requestid = -1;
    workflowid = Util.null2String(request.getParameter("workflowid"));
    nodetype = Util.null2String(request.getParameter("nodetype"));
    fromdate = Util.null2String(request.getParameter("fromdate"));
    todate = Util.null2String(request.getParameter("todate"));
    creatertype = Util.null2String(request.getParameter("creatertype"));
    createrid = Util.null2String(request.getParameter("createrid"));
    requestlevel = Util.null2String(request.getParameter("requestlevel"));
    requestname = Util.null2String(request.getParameter("requestname"));//added xwj for for td2903  20051019
    requestid = Util.getIntValue(Util.null2String(request.getParameter("requestid")),-1);
    sjdxryids = Util.null2String(request.getParameter("sjdxryids"));
    String requestidvalue = "";
    if(requestid!=-1){
        requestidvalue = ""+requestid;
    }


%>
<BODY>
<%@ include file="/systeminfo/TopTitle.jsp" %>
<%@ include file="/systeminfo/RightClickMenuConent.jsp" %>
<%
    RCMenu += "{"+SystemEnv.getHtmlLabelName(197,user.getLanguage())+",javascript:OnChangePage(),_self}" ;
    RCMenuHeight += RCMenuHeightStep ;
    RCMenu += "{刷新,javascript:refresh(),_top} ";
    RCMenuHeight += RCMenuHeightStep;
%>
<%@ include file="/systeminfo/RightClickMenu.jsp" %>
<div id='divshowreceivied' style='background:#FFFFFF;padding:3px;width:100%' valign='top'>
</div>
<table width=100% height=100% border="0" cellspacing="0" cellpadding="0">
    <colgroup>
        <col width="10">
        <col width="">
        <col width="10">
        <tr>
            <td height="10" colspan="3"></td>
        </tr>
        <tr>
            <td></td>
            <td valign="top">
                <TABLE class=Shadow>
                    <tr>
                        <td valign="top">
                            <FORM id=weaver name=weaver STYLE="margin-bottom:0"
                                  action="/gcl/sjjk/workflow-sj-jk.jsp" method="post">
                                <input type="hidden" name="multiRequestIds"
                                       value="">
                                <input type="hidden" name="operation" value="">
                                <table class=ViewForm>
    <colgroup>
        <col width="10%">
        <col width="20%">
        <col width="5">
        <col width="10%">
        <col width="20%">
        <col width="3%">
        <col width="6%">
        <col width="15">
    <tbody>
    <tr>
        <td><%=SystemEnv.getHtmlLabelName(63,user.getLanguage())%></td>
        <td class=field>
            <button class=Browser type="button" onClick="onShowBrowser2()"></button>
            <span id=workflowidspan><%=Util.toScreen(WorkflowComInfo.getWorkflowname(workflowid), user.getLanguage())%></span>
            <input class=inputstyle id=workflowid type=hidden name=workflowid value="<%=workflowid%>">

        </td>

        <td>&nbsp;</td>
        <td><%=SystemEnv.getHtmlLabelName(15536,user.getLanguage())%></td>
        <td class=field>
            <select size=1  class=InputStyle name=nodetype style=width:150>
                <option value="">&nbsp;</option>
                <option value="0" <% if(nodetype.equals("0")) {%>selected<%}%>><%=SystemEnv.getHtmlLabelName(125,user.getLanguage())%></option>
                <option value="1" <% if(nodetype.equals("1")) {%>selected<%}%>><%=SystemEnv.getHtmlLabelName(142,user.getLanguage())%></option>
                <option value="2" <% if(nodetype.equals("2")) {%>selected<%}%>><%=SystemEnv.getHtmlLabelName(725,user.getLanguage())%></option>
                <option value="3" <% if(nodetype.equals("3")) {%>selected<%}%>><%=SystemEnv.getHtmlLabelName(251,user.getLanguage())%></option>
            </select>
        </td>
        <td>&nbsp;</td>
        <td ><%=SystemEnv.getHtmlLabelName(603,user.getLanguage())%></td>
        <td class=field>
            <select name=requestlevel  class=InputStyle style=width:140 size=1>
                <option value=""> </option>
                <option value="0" <% if(requestlevel.equals("0")) {%>selected<%}%>><%=SystemEnv.getHtmlLabelName(225,user.getLanguage())%></option>
                <option value="1" <% if(requestlevel.equals("1")) {%>selected<%}%>><%=SystemEnv.getHtmlLabelName(15533,user.getLanguage())%></option>
                <option value="2" <% if(requestlevel.equals("2")) {%>selected<%}%>><%=SystemEnv.getHtmlLabelName(2087,user.getLanguage())%></option>
            </select>
        </td>
    </tr>
    <TR style="height:1px;">
        <TD class=Line colSpan=8></TD>
    </TR>
    <tr>

        <!--xwj for td2903  20051019 begin-->
        <td><%=SystemEnv.getHtmlLabelName(1334,user.getLanguage())%></td>
        <td class=field>
            <input type="text" name="requestname" value="<%=requestname%>">
        </td>
        <td>&nbsp;</td>
        <!--xwj for td2903  20051019 end-->

        <td><%=SystemEnv.getHtmlLabelName(97,user.getLanguage())%></td>
        <td class=field>
            <BUTTON type="button" class=calendar id=SelectDate1 onclick="gettheDate(fromdate,fromdatespan)"></BUTTON>
            <SPAN id=fromdatespan ><%=fromdate%></SPAN>
            <input type="hidden" name="fromdate" value="<%=fromdate%>">

            -&nbsp;&nbsp;<BUTTON type="button" class=calendar id=SelectDate2 onclick="gettheDate(todate,todatespan)"></BUTTON>
            <SPAN id=todatespan ><%=todate%></SPAN>
            <input type="hidden" name="todate" value="<%=todate%>">
        </td>

        <td>&nbsp;</td>
        <td >审计对象</td>
        <td class=field>
            <button class=Browser type="button" onClick="onShowBrowser1()"></button>
            <span id=sjdxryidsspan><%=Util.toScreen(ResourceComInfo.getLastnames(sjdxryids), user.getLanguage())%></span>
            <input class=inputstyle id=sjdxryids type=hidden name=sjdxryids value="<%=sjdxryids%>">


           </td>
    </tr>
    <TR style="height:1px;">
        <TD class=Line colSpan=8></TD>
    </TR>
    <tr>
        <td><%=SystemEnv.getHtmlLabelName(648,user.getLanguage())%>ID</td>
        <td class=field>
            <input type="text" id="requestid" name="requestid" value="<%=requestidvalue%>" onKeyPress="ItemCount_KeyPress()" onBlur="checkcount1(this)">
        </td>
        <td colSpan=6></td>
    </tr>
    <TR style="height:1px;"><TD class=Line colSpan=8></TD></TR>
    </tbody>
</table>

<TABLE width="100%">
    <tr>
        <td valign="top">
            <%
                //测试机表名
                String backfields = "a.requestid, a.currentnodeid, a.createdate, a.createtime,a.lastoperatedate, a.lastoperatetime,a.creater, a.creatertype, a.workflowid, a.requestname, a.status, a.requestlevel,a.currentstatus,a.currentnodetype,b.isview ";
                String fromSql = " from workflow_requestbase a,(select t1.workflowid,t1.isview from "+tablename_sz+" t,"+tablename_jl+" t1 where t.id=t1.sjszid and t.id in("+pzids+")) b";
                String sqlWhere = " a.workflowid=b.workflowid and a.createdate>='"+startdate+"' and a.createdate<='"+enddate+"'";
               if(!workflowid.equals("")&&!workflowid.equals("0"))
                   sqlWhere+=" and a.workflowid in("+workflowid+")" ;

                if(!nodetype.equals(""))
                    sqlWhere += " and currentnodetype='"+nodetype+"'";


                if(!fromdate.equals(""))
                    sqlWhere += " and createdate>='"+fromdate+"'";

                if(!todate.equals(""))
                    sqlWhere += " and createdate<='"+todate+"'";



                if(!sjdxryids.equals("")){
                    sqlWhere += " and creater in("+sjdxryids+")";
                }

                if(!requestlevel.equals(""))
                    sqlWhere += " and requestlevel="+requestlevel;

//added by xwj for td2903  20051019
                if(!requestname.equals("")){
                    requestname = Util.toHtml(requestname);
                    requestname = Util.StringReplace(requestname,"'","''");
                    sqlWhere += " and requestname like '%"+requestname+"%' ";
                }
                if(requestid!=-1){
                    sqlWhere += " and requestid="+requestid;
                }
//                out.println("select " + backfields + fromSql + " where " + sqlWhere);
                //out.println(sqlWhere);
                String orderby = " a.requestid desc ";
                String tableString = "";
                String para1="column:requestid+column:isview";
                String para4=userlang+"+"+user.getUID();

                tableString = " <table  tabletype=\"none\" pagesize=\"" + perpage + "\" >" + "	   <sql backfields=\"" + backfields + "\" sqlform=\"" + fromSql + "\" " + "sqlwhere=\"" + Util.toHtmlForSplitPage(sqlWhere) + "\" sqlorderby=\"" + orderby + "\" " + "sqlprimarykey=\"a.requestid\" sqlsortway=\"desc\" />" + "			<head>" +
                        "           <col width=\"22%\"  text=\""+SystemEnv.getHtmlLabelName(1334,user.getLanguage())+"\" column=\"requestname\" orderkey=\"requestname\" linkvaluecolumn=\"requestid\" linkkey=\"requestid\" href=\"/workflow/request/ViewRequest.jsp?isfromsjjk=1\" target=\"_fullwindow\" />"+
                        "           <col width=\"12%\"  text=\""+SystemEnv.getHtmlLabelName(259,user.getLanguage())+"\" column=\"workflowid\" orderkey=\"a.workflowid,a.requestname\" transmethod=\"weaver.workflow.workflow.WorkflowComInfo.getWorkflowname\" />"+
                        "           <col width=\"10%\"  text=\""+SystemEnv.getHtmlLabelName(882,user.getLanguage())+"\" column=\"creater\" orderkey=\"creater\" otherpara=\"column:creatertype\" transmethod=\"weaver.general.WorkFlowTransMethod.getWFSearchResultName\" />"+
                        "           <col width=\"17%\"   text=\""+SystemEnv.getHtmlLabelName(1339,user.getLanguage())+"\" column=\"createdate\"  orderkey=\"createdate,createtime\" otherpara=\"column:createtime\" transmethod=\"weaver.general.WorkFlowTransMethod.getWFSearchResultCreateTime\" />"+
                        "           <col width=\"10%\"  text=\""+SystemEnv.getHtmlLabelName(18564,user.getLanguage())+"\" column=\"currentnodeid\" orderkey=\"currentnodeid\"  transmethod=\"weaver.general.WorkFlowTransMethod.getCurrentNode\" />"+
                        "           <col width=\"15%\"  text=\""+SystemEnv.getHtmlLabelName(18565,user.getLanguage())+"\" column=\"requestid\"  otherpara=\""+para4+"\" transmethod=\"weaver.general.WorkFlowTransMethod.getUnOperators\" />"+
                        "           <col width=\"10%\"  text=\""+SystemEnv.getHtmlLabelName(1335,user.getLanguage())+"\" column=\"status\" orderkey=\"status\" />"+
                        "			</head>" +
                        "</table>";
                //showExpExcel="true"
            %>

            <wea:SplitPageTag tableString="<%=tableString%>" mode="run"/>
        </td>
    </tr>
</TABLE>
</FORM>
</td>
</tr>
</TABLE>
</td>
<td></td>
</tr>
<tr>
    <td height="10" colspan="3"></td>
</tr>
</table>
<script type="text/javascript">
    function OnChangePage(){
        document.weaver.method = "post";
            document.weaver.submit();
    }
    function showallreceived(requestid,returntdid){
        showreceiviedPopup("<%=SystemEnv.getHtmlLabelName(19205,user.getLanguage())%>");
        var ajax=ajaxinit();

        ajax.open("POST", "/workflow/search/WorkflowUnoperatorPersons.jsp", true);
        ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        ajax.send("requestid="+requestid+"&returntdid="+returntdid);
        //获取执行状态
        //alert(ajax.readyState);
        //alert(ajax.status);
        ajax.onreadystatechange = function() {
            //如果执行状态成功，那么就把返回信息写到指定的层里
            if (ajax.readyState==4&&ajax.status == 200) {
                try{
                    document.all(returntdid).innerHTML = ajax.responseText;
                }catch(e){}
                showTableDiv.style.display='none';
                oIframe.style.display='none';
            }
        }
    }
    function refresh() {
        window.location.reload();
    }

    var showTableDiv  = document.getElementById('divshowreceivied');
    var oIframe = document.createElement('iframe');
    function showreceiviedPopup(content){
        showTableDiv.style.display='';
        var message_Div = document.createElement("div");
        message_Div.id="message_Div";
        message_Div.className="xTable_message";
        showTableDiv.appendChild(message_Div);
        var message_Div1  = document.getElementById("message_Div");
        message_Div1.style.display="inline";
        message_Div1.innerHTML=content;
        var pTop= document.body.offsetHeight/2+document.body.scrollTop-50;
        var pLeft= document.body.offsetWidth/2-50;
        message_Div1.style.position="absolute"
        message_Div1.style.posTop=pTop;
        message_Div1.style.posLeft=pLeft;

        message_Div1.style.zIndex=1002;

        oIframe.id = 'HelpFrame';
        showTableDiv.appendChild(oIframe);
        oIframe.frameborder = 0;
        oIframe.style.position = 'absolute';
        oIframe.style.top = pTop;
        oIframe.style.left = pLeft;
        oIframe.style.zIndex = message_Div1.style.zIndex - 1;
        oIframe.style.width = parseInt(message_Div1.offsetWidth);
        oIframe.style.height = parseInt(message_Div1.offsetHeight);
        oIframe.style.display = 'block';
    }

    function onShowBrowser1() {
        data = window.showModalDialog("/systeminfo/BrowserMain.jsp?url=/interface/MultiCommonBrowser.jsp?type=browser.sj_ry&selectedids=" + jQuery("#sjdxryids").val());
        if (data != null) {
            if (data.id.substring(1) != "") {
                jQuery("#sjdxryidsspan").html(data.name.substring(1));
                jQuery("input[name=sjdxryids]").val(data.id.substring(1));
            } else {
                jQuery("#sjdxryidsspan").html("");
                jQuery("input[name=sjdxryids]").val("");
            }
        }
    }

    function onShowBrowser2() {
        data = window.showModalDialog("/systeminfo/BrowserMain.jsp?url=/interface/CommonBrowser.jsp?type=browser.sj_lctype&selectedids=" + jQuery("#workflowid").val());
        if (data != null) {
            if (data.id != "") {
                jQuery("#workflowidspan").html(data.name);
                jQuery("input[name=workflowid]").val(data.id);
            } else {
                jQuery("#workflowidspan").html("");
                jQuery("input[name=workflowid]").val("");
            }
        }
    }


</script>
<SCRIPT language="javascript" defer="defer" src="/js/datetime.js"></script>
<SCRIPT language="javascript" defer="defer"
        src="/js/JSDateTime/WdatePicker.js"></script>
</BODY>
</HTML>