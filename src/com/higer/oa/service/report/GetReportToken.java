package com.higer.oa.service.report;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

public class GetReportToken {


    public String getReportToken(String ryid)  {
        String result = "";
        try {
            String loginid = "";
            RecordSet rs = new RecordSet();
            rs.execute("select loginid from hrmresource where id="+ryid);
            if(rs.next()){
                loginid = Util.null2String(rs.getString("loginid"));
            }
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String loginTime = sf.format(new Date());
            String loginSecrt = createToken(loginid, "0123456789abcdefg", loginTime);
            new BaseBean().writeLog("GetReportToken loginid:"+loginid+":" + loginSecrt);
            String tokenUrl = "http://ebiapp.klsz.com/portalbasicapi/SSO/LoginSso/" + loginSecrt;
            result = sendGet(tokenUrl);
            new BaseBean().writeLog("GetReportToken result:" + result);
            JSONObject jo = new JSONObject(result);
            String isSUccess = jo.getString("isSuccess");
            String tokenAccess = "";
            String code = "";
            if("true".equals(isSUccess)){
                tokenAccess = jo.getJSONObject("data").getString("tokenAccess");
                code = jo.getJSONObject("data").getString("code");
            }
            if("".equals(tokenAccess)||"".equals(code)){
                result =  "E";
            }else{
                result =  tokenAccess;
            }

        }catch(Exception e){
            new BaseBean().writeLog("GetReportToken:",e);
            result = "E";
      }

        return result;

    }


    public static String createToken(String UserName,String secret,String loginTime) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map)
                .withClaim("UserName", UserName)
                .withClaim("LoginTime", loginTime)
                .withIssuedAt(null)
                .withExpiresAt(null)
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    public  String sendGet(String url) {
        new BaseBean().writeLog("GetReportToken:"+url);
        String result="";
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数

            String full_url = url;
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(full_url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取

            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            new BaseBean().writeLog(e);
            e.printStackTrace();
        }finally{
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                new BaseBean().writeLog(ex);
                ex.printStackTrace();
            }
        }
        return result ;
    }

}
