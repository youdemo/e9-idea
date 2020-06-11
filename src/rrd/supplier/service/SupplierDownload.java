package rrd.supplier.service;

import weaver.general.BaseBean;
import weaver.general.Util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

@SuppressWarnings("serial")
public class SupplierDownload extends HttpServlet{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	 public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		BaseBean log = new BaseBean();
		log.writeLog("doPost-----");
		String urlKey = URLDecoder.decode(Util.null2String(request.getParameter("urlKey")),"UTF-8");
		String type = Util.null2String(request.getParameter("type"));

		SupplierInfoServiceImpl sisi = new SupplierInfoServiceImpl();
		String rqid = sisi.getRqid(urlKey);
		if("".equals(rqid)){
			return;
		}
		String xzwd1 = new String(weaver.file.Prop.getPropValue("rrdsupplierzr","xzwd1").getBytes("ISO-8859-1"),"UTF-8");
		String xzwd2 = new String(weaver.file.Prop.getPropValue("rrdsupplierzr","xzwd2").getBytes("ISO-8859-1"),"UTF-8");
        String xzwd3 = new String(weaver.file.Prop.getPropValue("rrdsupplierzr","xzwd3").getBytes("ISO-8859-1"),"UTF-8");

        String xzdj = new String(weaver.file.Prop.getPropValue("rrdsupplierzr","xzdj").getBytes("ISO-8859-1"),"UTF-8");

		String fileName = "";
        if("0".equals(type)){
			fileName = xzwd1;
		}else if("1".equals(type)){
			fileName = xzwd2;
		}else if("2".equals(type)){
            fileName = xzwd3;
        }
        new BaseBean().writeLog("testaaa","url:"+xzdj+fileName);
        File file = new File(xzdj+fileName);
		 //log.writeLog(map.toString());

		 exportFile(response,file,fileName);
	 }
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request,response);
	} 
	/** 
	 	* 文件输出
		* @param file 文件
		* @param fileName 文件名
	 */
	 public static void exportFile(HttpServletResponse response, File file, String fileName) throws IOException {
		 response.setContentType("application/octet-stream");
		 response.setHeader("content-disposition", "attachment; filename=\"" +  new String(fileName.replaceAll("<", "").replaceAll(">", "").replaceAll("&lt;", "").replaceAll("&gt;", "").getBytes("UTF-8"),"ISO-8859-1"));
		 ServletOutputStream out = null;
		 out = response.getOutputStream();
		 InputStream imagefile = null;
		 int byteread;
		 byte data[] = new byte[1024];
		 try {
			 imagefile = new FileInputStream(file);
			 response.setCharacterEncoding("UTF-8");
			 while ((byteread = imagefile.read(data)) != -1) {
				 out.write(data, 0, byteread);
				 out.flush();
			 }
		 } catch (FileNotFoundException e) {
		 } finally {
			 if (imagefile != null) {
				 try {
					 imagefile.close();
				 } catch (Exception e) {
					 throw new RuntimeException(e);
				 }
			 }
		 }
	 }
	 

}
