<%@page language="java" contentType="application/msword;charset=utf8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="rrd.supplier.service.SupplierInfoServiceImpl" %>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.io.*" %>
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
    response.setContentType("application/msword");
    String fileName ="供应商财务关键信息清单_中文.docx";
    String fileUrl = "D:\\weaver9new\\ecology\\rrd\\supplier\\供应商财务关键信息清单_中文.docx";
    fileName = URLEncoder.encode(fileName, "UTF-8");
    response.setHeader("Content-Disposition","attachment; filename="+fileName);
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
        //bis = new BufferedInputStream(new FileInputStream(getServletContext().getRealPath("" + filename)));
        bis = new BufferedInputStream(new FileInputStream(fileUrl));
        bos = new BufferedOutputStream(response.getOutputStream());

        byte[] buff = new byte[2048];
        int bytesRead;

        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff,0,bytesRead);
        }

    } catch(FileNotFoundException fe){
        System.out.println("文件没找到");
    } catch(final IOException e) {
        System.out.println ( "出现IOException." + e );
    } finally {
        if (bis != null)
            bis.close();
        if (bos != null)
            bos.close();
        out.clear();
        out = pageContext.pushBody();
    }
    return;
    
%>