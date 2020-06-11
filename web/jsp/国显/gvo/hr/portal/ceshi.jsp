<%@ page import="gvo.ScheduleTask.hr.portal.SaveGLZPortalDataAction" %>
<%@ page import="weaver.general.BaseBean" %><%--
  Created by IntelliJ IDEA.
  User: tangj
  Date: 2020/5/19
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    out.print("123");
    SaveGLZPortalDataAction gpl = new SaveGLZPortalDataAction();
    try {
       gpl.insertOrUpdateData("V0009118","0");
    }catch (Exception e){
        new BaseBean().writeLog("test111",e);
        out.print("123222");
    }

%>
