package com.higer.oa.service.report;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GetReportToken111 {

    public void showReport(){

    }

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginTime = sf.format(new Date());
         System.out.println(createToken("yuzhen","0123456789abcdefg",loginTime));
        //System.out.println(loginTime);
        String aaa = "{\"data\":{\"tokenAccess\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VyTmFtZSI6InRhbmdqIiwiRnVsbE5hbWUiOiJ0YW5naiIsIlVzZXJJZGVudGl0eUlkIjoiMTEzNmE0MmEtYWNjMi00YmQxLTliODItMjZjOGVkNGQxNDMzIiwiVG9rZW5UaW1lIjoiMjAxOS0xMi0xM1QxODoxMDoyOS41MTUwMzA3IiwiTGFuZ3VhZ2UiOiJ6aCIsIklzQWRtaW4iOnRydWUsIklkIjozLCJPcmRlck51bWJlciI6MCwiZXhwIjoxNjA3NzY3ODMwLjB9.GQQwjg7jkDDWCcOlVoOPrNgJek7mK2rcbXS4BwA7Yq0\",\"isSuccess\":true,\"code\":\"1136a42a-acc2-4bd1-9b82-26c8ed4d1433\",\"message\":null,\"orderNumber\":0},\"isSuccess\":true,\"errorCode\":null,\"message\":\"\",\"metaData\":null}";
        JSONObject jo = new JSONObject(aaa);
        String isSUccess = jo.getString("isSuccess");
        String tokenAccess = "";
        String code = "";
        System.out.println(isSUccess);
        if("true".equals(isSUccess)){
            tokenAccess = jo.getJSONObject("data").getString("tokenAccess");
            code = jo.getJSONObject("data").getString("code");
        }
        System.out.println(tokenAccess);
        System.out.println(code);


        //System.out.println(URLEncoder.encode("http://ebiapp.klsz.com/client/#/find"));
    }
    public String getToken(String ryid)  {
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
                result =  "http://ebiapp.klsz.com/client/#/?token="+tokenAccess;
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
