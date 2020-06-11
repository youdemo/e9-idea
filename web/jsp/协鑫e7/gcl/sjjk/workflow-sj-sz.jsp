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
    String titlename = "流程审计设置";
    String needfav = "1";
    String needhelp = "";
    SjUtil sjUtil = new SjUtil();
    String tablename_sz = sjUtil.getBillTableName("LCSJSZ");
    String tablename_jl = sjUtil.getBillTableName("SJJKJLB");
    String jsid = sjUtil.getWfid("LCSJSZJS");
    int count = 0;
    if(emp_id != 1){
        sqlqx = "select count(1) as count from hrmrolemembers where roleid="+jsid+" and resourceid="+emp_id;
        rs.executeSql(sqlqx);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count<=0){
            response.sendRedirect("/notice/noright.jsp");
            return;
        }

    }



%>
<BODY>
<%@ include file="/systeminfo/TopTitle.jsp" %>
<%@ include file="/systeminfo/RightClickMenuConent.jsp" %>
<%

    RCMenu += "{刷新,javascript:refresh(),_top} ";
    RCMenuHeight += RCMenuHeightStep;
    RCMenu += "{新增,javascript:xz(),_top} ";
    RCMenuHeight += RCMenuHeightStep;
%>
<%@ include file="/systeminfo/RightClickMenu.jsp" %>
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
                                  action="" method="post">
                                <input type="hidden" name="multiRequestIds"
                                       value="">
                                <input type="hidden" name="operation" value="">

<TABLE width="100%">
    <tr>
        <td valign="top">
            <%
                //测试机表名
                String backfields = "id,sjry,sjdx,ksrq||'~'||jsrq as sjzq,(select count(1) from "+tablename_jl+" where sjszid=a.id) as lcsl  ";
                String fromSql = " from "+tablename_sz+" a";
                String sqlWhere = " 1=1 ";
//                out.println("select " + backfields + fromSql + " where " + sqlWhere);
                //out.println(sqlWhere);
                String orderby = " id desc ";
                String tableString = "";

                tableString = " <table  tabletype=\"none\" pagesize=\"" + perpage + "\" >" + "	   <sql backfields=\"" + backfields + "\" sqlform=\"" + fromSql + "\" " + "sqlwhere=\"" + Util.toHtmlForSplitPage(sqlWhere) + "\" sqlorderby=\"" + orderby + "\" " + "sqlprimarykey=\"id\" sqlsortway=\"desc\" />" + "			<head>" +
                        " 	<col width=\"20%\" text=\"审计人员\" column=\"sjry\" orderkey=\"sjry\" transmethod=\"weaver.hrm.resource.ResourceComInfo.getLastnames\"/>" +
                        " 	<col width=\"15%\" text=\"流程数量\" column=\"lcsl\" orderkey=\"lcsl\"  />" +
                        "   <col width=\"20%\" text=\"详细列表\" column=\"id\" orderkey=\"id\"  transmethod=\"gcl.sjjk.SjUtil.getXxlb\"/>" +
                        " 	<col width=\"20%\" text=\"审计对象\" column=\"sjdx\" orderkey=\"sjdx\" transmethod=\"gcl.sjjk.SjUtil.getJsName\" />" +
                        " 	<col width=\"15%\" text=\"审计周期\" column=\"sjzq\" orderkey=\"sjzq\"  />" +
                        " 	<col width=\"10%\" text=\"操作\" column=\"id\" orderkey=\"id\"  transmethod=\"gcl.sjjk.SjUtil.getCzButton\" />" +
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
    function refresh() {
        window.location.reload();
    }

    function sc(id) {
       if(confirm("是否确认删除")){
           var result = "";
           $.ajax({
               type: "POST",
               url: "/gcl/sjjk/systemMonitorOperation.jsp",
               data: {'pzid': id,'actionKey':'del'},
               dataType: "text",
               async: false,//同步   true异步
               success: function (data) {
                   data = data.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
                   result = data;
               }
           });
               alert("删除成功");
               window.location.reload();

       }
       return;
    }
    function bj(id) {

            openFullWindowForXtable('/gcl/sjjk/workflowsjMonitorSet.jsp?actionKey=edit&pzid='+id);
            return;

    }
    function xz() {

        openFullWindowForXtable('/gcl/sjjk/workflowsjMonitorSet.jsp?actionKey=add');
        return;

    }
    function xxlb(id) {

        openFullWindowForXtable('/gcl/sjjk/workflow-sj-detail.jsp?pzid='+id);
        return;

    }


</script>
<SCRIPT language="javascript" defer="defer" src="/js/datetime.js"></script>
<SCRIPT language="javascript" defer="defer"
        src="/js/JSDateTime/WdatePicker.js"></script>
</BODY>
</HTML>