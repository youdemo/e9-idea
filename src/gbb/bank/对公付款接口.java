package gbb.bank;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
* @author 作者  张瑞坤
* @date 创建时间：2020年4月1日 上午11:00:44 
* @version 1.0 
*/
public class 对公付款接口 {

	public String postConnection(String url, String param) throws Exception {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;

		StringBuffer responseResult = new StringBuffer();

		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			httpURLConnection.setConnectTimeout(10000);
			httpURLConnection.setReadTimeout(10000);
			// 设置通用的请求属性
			httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			//httpURLConnection.setRequestProperty("Content-Length", String.valueOf(param.length()));
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-type", "application/xml;charset=UTF-8");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			//printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			DataOutputStream out =new DataOutputStream (httpURLConnection.getOutputStream());
			out.write(param.toString().getBytes("UTF-8"));
			// 发送请求参数
			//printWriter.write(new String(param.toString().getBytes(), "UTF-8"));
			// flush输出流的缓冲
			out.flush();
			out.close();
			// 根据ResponseCode判断连接是否成功
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode == httpURLConnection.HTTP_OK) {
				// 定义BufferedReader输入流来读取URL的ResponseData
				bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					responseResult.append(line);
				}
				return  responseResult.toString();
			}
			return null;
		} catch (ConnectException e) {
			throw new Exception(e);
		} catch (MalformedURLException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpURLConnection.disconnect();
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	/**
	 * 对公付款
	 * @return
	 */
	public static String zzsj() {
		String str  ="<?xml   version=\"1.0\" encoding=\"GBK\"?>\r\n" + 
				"<trn-b2e0009-rq>\r\n" + 
				"    <ceitinfo> </ceitinfo>\r\n" + 
				"    <transtype>1</transtype> \r\n" + 
				"    <b2e0009-rq>\r\n" + 
				"        <insid>10001</insid>\r\n" + 
				"        <obssid></obssid>\r\n" + 
				"        <fractn> \r\n" + 
				"            <fribkn>40303</fribkn> \r\n" + 
				"            <actacn>452058887515</actacn>\r\n" + 
				"            <actnam >漫漫朔雪冷渔翁</actnam >\r\n" + 
				"        </fractn>\r\n" + 
				"        <toactn>\r\n" + 
				"            <toibkn>40303</toibkn> \r\n" + 
				"            <actacn>446858888004</actacn>\r\n" + 
				"            <toname>托克投资（中国）有限公司</toname> \r\n" + 
				"            <toaddr>中国银行上海市分行</toaddr> \r\n" + 
				"            <tobknm>中国银行上海市分行</tobknm >\r\n" + 
				"        </toactn>\r\n" + 
				"        <trnamt>100</trnamt>\r\n" + 
				"        <trncur>CNY</trncur> \r\n" + 
				"        <priolv>0</priolv>\r\n" + 
				"        <furinfo>test</furinfo>\r\n" + 
				"        <trfdate>20200401</trfdate>\r\n" + 
				"        <trftime>20200401</trftime>\r\n" + 
				"        <comacn></comacn>\r\n" + 
				"    </b2e0009-rq>\r\n" + 
				"</trn-b2e0009-rq>";
		return str;
		
	}
	public static String cx() {
		String str = "<?xml version = \"1.0\" encoding = \"utf-8\"?>\r\n" + 
				"<bocb2e version=\"100\" security=\"true\" lang=\"chs\">\r\n" + 
				"<head>\r\n" + 
				"<termid>E180167111253</termid>\r\n" + 
				"<custid>133723595</custid>\r\n" + 
				"<cusopr>135988154</cusopr>\r\n" + 
				"<token>k4AWqk7R</token>\r\n" + 
				"<trnid>20060704001</trnid>\r\n" + 
				"<trncod>b2e0005</trncod>\r\n" + 
				"\r\n" + 
				"</head>\r\n" + 
				"<trans>\r\n" + 
				"<trn-b2e0005-rq>\r\n" + 
				"<b2e0005-rq>\r\n" + 
				"<account>\r\n" + 
				"<ibknum>40303</ibknum>\r\n" + 
				"<actacn>452058887515</actacn>\r\n" + 
				"</account>\r\n" + 
				"</b2e0005-rq>\r\n" + 
				"\r\n" + 
				"</trn-b2e0005-rq>\r\n" + 
				"</trans>\r\n" + 
				"</bocb2e>";
		return str;
		
	}
	
	public static void main(String[] args) {
		String url = "http://101.231.206.205:81/B2EPCSPJ/XmlServlet";
		String  str = zzsj();
		String cx =  cx();
		HttpRequest hrt = new HttpRequest();
//		System.out.println(hrt.sendPost(url, str));
		System.out.println(hrt.sendPost(url, cx));
//		PostToBank pt = new PostToBank();
//		try {
//			System.out.println(pt.postConnection(url, str));
//		} catch (Exception e) {
//			System.out.println(e.getCause()+"----"+e.getMessage());
//			e.printStackTrace();
//		}
		
		
		
		
	}
	
	
	
	
	

}
