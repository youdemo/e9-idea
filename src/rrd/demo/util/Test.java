package rrd.demo.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import weaver.general.WorkFlowTransMethod;

public class Test {
	public static String postConnection(String url, String param) throws Exception {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		HttpURLConnection httpURLConnection = null;

		StringBuffer responseResult = new StringBuffer();

		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			httpURLConnection.setConnectTimeout(60000);
			httpURLConnection.setReadTimeout(60000);
			// 设置通用的请求属性
			httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			// httpURLConnection.setRequestProperty("Content-Length",
			// String.valueOf(param.length()));
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			//httpURLConnection.setRequestProperty("Content-type", "application/xml;charset=UTF-8");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			// printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
			out.write(param.toString().getBytes("UTF-8"));
			// 发送请求参数
			// printWriter.write(new String(param.toString().getBytes(), "UTF-8"));
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
				return responseResult.toString();
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
	
	public static void main(String[] args) throws Exception {

		//String aaa = "{\"sqr\":\"rr220046\",\"sqbm\":\"it001\",\"sqrq\":\"2019-10-22\",\"qjjsrq\":\"2019-10-22\",\"sqsy\":\"123\"}";
		//JSONObject jo = new JSONObject(aaa);
		
			//String result = postConnection("http://testoa.sino-telecom.com:8080/api/sino/purchaseOrder?info={\"hthm\":\"ht0001\",\"bb\":\"人民币\",\"hl\":\"1.00\",\"bdbh\":\"P-20191012-001\",\"gysst\":\"供应商111\",\"zdr\":\"9001\",\"zdzke\":\"22.22\",\"CreateDate\":\"2019-10-12\",\"ydlx\":\"采购申请单\",\"htmc\":\"测试合同0001\",\"xmmc\":\"项目名称\",\"cgy\":\"9001\",\"sqr\":\"9001\",\"zdzkl\":\"0.853221\",\"cgfsst\":\"直运采购\",\"xdh\":\"cg123123\",\"xqly\":\"生产备货\",\"Detail\":[{\"ggxh\":\"100cm*10cm\",\"hsdj\":\"2.00\",\"fzzx\":\"暂无\",\"kcsl\":\"2.00\",\"sjhsd\":\"2.00\",\"dj\":\"2.00\",\"wldm\":\"SN0001\",\"jhrq\":\"2019-10-24\",\"sccgjg\":\"2.00\",\"jbdwsl\":\"1.00\",\"dw\":\"个\",\"jbdwmc\":\"根\",\"zdzqje\":\"2.00\",\"zke\":\"2.00\",\"xyfs\":\"检验方式\",\"yddh\":\"1002000\",\"bz\":\"备注\",\"sl\":\"1.00\",\"sl1\":\"1.00\",\"jshj\":\"2.00\",\"je\":\"2.00\",\"wlmc\":\"竹原料\"},{\"ggxh\":\"100cm*10cm\",\"hsdj\":\"2.00\",\"fzzx\":\"暂无\",\"kcsl\":\"2.00\",\"sjhsd\":\"2.00\",\"dj\":\"2.00\",\"wldm\":\"SN0001\",\"jhrq\":\"2019-10-24\",\"sccgjg\":\"2.00\",\"jbdwsl\":\"1.00\",\"dw\":\"个\",\"jbdwmc\":\"根\",\"zdzqje\":\"2.00\",\"zke\":\"2.00\",\"xyfs\":\"检验方式\",\"yddh\":\"1002000\",\"bz\":\"备注\",\"sl\":\"1.00\",\"sl1\":\"1.00\",\"jshj\":\"2.00\",\"je\":\"2.00\",\"wlmc\":\"竹原料\"}],\"zy\":\"摘要\"}","");
			//System.out.println(result);
	}
}
