<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="test.TestCreateRequest" %>
<%
    TestCreateRequest tcr = new TestCreateRequest();
    String result = tcr.Createtest();
    out.print(result);




%>