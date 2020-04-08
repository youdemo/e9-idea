<%@ page import="gvo.doc.pdf.WorkflowToPdf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>


<%
    WorkflowToPdf aa = new WorkflowToPdf();
    aa.createPdf("2965605","http://10.1.96.39:8082","3385");
    out.print("123");
%>