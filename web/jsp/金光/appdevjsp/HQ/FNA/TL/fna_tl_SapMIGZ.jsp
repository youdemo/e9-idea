<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="APPDEV.HQ.FNA.TL.FNA_TL_AP_SapMIGZ" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%
    String IV_BLDAT = Util.null2String(request.getParameter("IV_BLDAT"));

    String rqid = Util.null2String(request.getParameter("rqid"));
    Map<String, String> map = new HashMap<String, String>();
    map.put("rqid",rqid);
    map.put("IV_BLDAT", IV_BLDAT);

    FNA_TL_AP_SapMIGZ ftas = new FNA_TL_AP_SapMIGZ();
    String result = ftas.doSapMNGZ(map);
    out.print(result);



%>