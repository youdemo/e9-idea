<%@page language="java" contentType="application/x-msdownload;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="rrd.supplier.service.SupplierInfoServiceImpl" %>
<%@ page import="weaver.general.Util" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%
    String urlKey = URLDecoder.decode(Util.null2String(request.getParameter("urlKey")),"UTF-8");
    SupplierInfoServiceImpl sisi = new SupplierInfoServiceImpl();
    String rqid = sisi.getRqid(urlKey);
    if("".equals(rqid)){
        out.print("请打开正确的连接地址");
        return;
    }


    response.reset();
    out.clear();
    response.setContentType("application/x-download");
    //response.setContentType("application/x-msdownload");
    String fileName ="test.doc";
    String fileUrl = "D:\\weaver9new\\ecology\\rrd\\supplier\\test.doc";
    response.reset();
    out.clear();
    String uploadBuffer="";
    //根据路径 创建file对象
    File file =  new File(fileUrl);
    //读取文件流 读取文件有多种方式这是其中一种按字节读取
    FileInputStream fi = new FileInputStream(file);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //按1024字节大写一次次读取文件
    byte[] buffer = new byte[1024];
    int count = 0;
    while((count = fi.read(buffer)) >= 0){
        baos.write(buffer, 0, count);
    }
    uploadBuffer =  baos.toString();
    baos.close();

//response.setContentType("application/x-download");
    response.setContentType("application/x-msdownload");
    fileName = URLEncoder.encode(fileName,"UTF-8");
    response.addHeader("Content-Disposition","attachment;filename=" + fileName);
    response.setCharacterEncoding("UTF-8");
    response.getOutputStream().write(uploadBuffer.getBytes("UTF-8"));
//response.getOutputStream().write("aaaaaa".getBytes("UTF-8"));
    response.getOutputStream().flush();
    out.clear();
    out = pageContext.pushBody();
%>