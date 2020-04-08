<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="APPDEV.HQ.FNA.TL.FNA_TL_AP_GetSapData" %>
<%
    String ryid = Util.null2String(request.getParameter("ryid"));
    String gsdm = Util.null2String(request.getParameter("gsdm"));
    String startdate = Util.null2String(request.getParameter("startdate"));
    String gw = Util.null2String(request.getParameter("gw"));
    FNA_TL_AP_GetSapData ftagsd = new FNA_TL_AP_GetSapData();
    String result = ftagsd.getPersonTravelBaseInfo(ryid,gsdm,startdate,gw);
    out.print(result);



%>