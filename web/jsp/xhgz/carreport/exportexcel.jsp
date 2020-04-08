<%@page language="java" contentType="application/x-msdownload" pageEncoding="UTF-8"%>
<%@ page import="com.fr.third.v2.org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import="weaver.general.Util" %>

<%@ page import="java.io.OutputStream" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="xhgz.car.CarReportGetExcel" %>

<%
    String searchday = Util.null2String(request.getParameter("searchday"));

    String year =searchday.substring(0,4);
    String month =searchday.substring(5,7);
    if("0".equals(month.substring(0,1))){
        month = month.substring(1,2);
    }
    String day =searchday.substring(8,10);
    if("0".equals(day.substring(0,1))){
        day = day.substring(1,2);
    }
    String fileName = year+"年"+month+"月"+day+"日车辆使用情况.xls";
    HSSFWorkbook workbook = null;

    CarReportGetExcel ce = new CarReportGetExcel();
    workbook = ce.createExcel(searchday);

  
    response.reset();
    out.clear();
    String file_name = URLEncoder.encode(fileName, "UTF-8");
    response.setContentType("application/octet-stream;charset=ISO8859-1");
//    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
    response.setHeader("Content-disposition", "attachment;filename="+file_name);

    OutputStream os = response.getOutputStream();
    workbook.write(os);
    os.flush();
    os.close();

%>
