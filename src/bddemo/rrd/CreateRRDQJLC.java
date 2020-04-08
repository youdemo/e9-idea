package bddemo.rrd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import weaver.conn.RecordSet;
import weaver.formmode.customjavacode.AbstractModeExpandJavaCode;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.soa.workflow.request.RequestInfo;

public class CreateRRDQJLC extends AbstractModeExpandJavaCode {

	@Override
	public void doModeExpand(Map<String, Object> param) throws Exception {
		BaseBean log = new BaseBean();
		User user = (User)param.get("user");
		RequestInfo info = (RequestInfo)param.get("RequestInfo");
		String modeId = info.getWorkflowid();
		String billid = info.getRequestid();
		String tableName = "";
		String sqr = "";
		String sqbm = "";
		String sqrq = "";
		String qjjsrq = "";
		String sqsy = "";
		RecordSet rs = new RecordSet();
		String sql = "select b.tablename from modeinfo a,workflow_bill b where a.formid=b.id and a.id="
				+ modeId;
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
		JSONObject jo = new JSONObject();
		sql = "select * from "+tableName+" where id="+billid;
		log.writeLog("testaaa:"+sql);
		rs.execute(sql);
		if(rs.next()) {
			sqr = Util.null2String(rs.getString("qjr"));
			sqbm = Util.null2String(rs.getString("qjbm"));
			sqrq = Util.null2String(rs.getString("qjksrq"));
			qjjsrq = Util.null2String(rs.getString("qjjsrq"));
			sqsy = Util.null2String(rs.getString("qjsy"));
		}
		
		sql = "select loginid from hrmresource where id="+sqr;
		rs.execute(sql);
		if(rs.next()) {
			sqr = Util.null2String(rs.getString("loginid"));
		}
		
		sql = "select departmentcode from hrmdepartment where id="+sqbm;
		rs.execute(sql);
		if(rs.next()) {
			sqbm = Util.null2String(rs.getString("departmentcode"));
		}
		try {
			jo.put("sqr", sqr);
			jo.put("sqbm", sqbm);
			jo.put("sqrq", sqrq);
			jo.put("qjjsrq", qjjsrq);
			jo.put("sqsy", sqsy);
			String result=postConnection("http://10.188.39.66:8080/api/rrd/demo/qjsq?json="+jo.toString(),"");
			JSONObject rjo = new JSONObject(result);
			String rqid=rjo.getString("OA_ID");
			sql = "update "+tableName+" set rqid='"+rqid+"' where id="+billid;
			rs.execute(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public  String postConnection(String url, String param) throws Exception {
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

}
