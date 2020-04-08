<%@page language="java" contentType="application/x-msdownload;" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="rrd.supplier.service.SupplierInfoServiceImpl" %>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.OutputStream"%>
<%
    request.setCharacterEncoding("UTF-8");
    String urlKey = URLDecoder.decode(Util.null2String(request.getParameter("urlKey")),"UTF-8");
    SupplierInfoServiceImpl sisi = new SupplierInfoServiceImpl();
    String rqid = sisi.getRqid(urlKey);
    if("".equals(rqid)){
        out.print("请打开正确的连接地址");
        return;
    }

    //response.setContentType("application/x-msdownload");
    response.reset();
    out.clear();
    response.setContentType("application/octet-stream;charset=utf-8");
    String fileName ="供应商财务关键信息清单_中文.docx";
    String fileUrl = "D:\\weaver9new\\ecology\\rrd\\supplier\\供应商财务关键信息清单_中文.docx";
    fileName = URLEncoder.encode(fileName, "UTF-8");
    response.setHeader("Content-Disposition","attachment; filename="+fileName);
    OutputStream os = response.getOutputStream();
    File file =  new File(fileUrl);
    //读取文件流 读取文件有多种方式这是其中一种按字节读取
    FileInputStream fi = new FileInputStream(file);
    //按1024字节大写一次次读取文件
    byte[] buffer = new byte[1024];
    int count = 0;
    while((count = fi.read(buffer)) >= 0){
        os.write(buffer, 0, count);
    }

    os.flush();
    os.close();
    
%>