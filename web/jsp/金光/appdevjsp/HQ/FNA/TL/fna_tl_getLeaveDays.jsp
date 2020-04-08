<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="APPDEV.HQ.FNA.TL.FNA_TL_AP_GetSapLeaveDays" %>
<%

    String clh = Util.null2String(request.getParameter("clh"));

    FNA_TL_AP_GetSapLeaveDays ftas = new FNA_TL_AP_GetSapLeaveDays();
    String result = ftas.geLeaveDays(clh);
    out.print(result);



%>