package gbb.bank;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * createby jianyong.tang
 * createTime 2020/4/3 15:14
 * version v1
 * desc
 */
public class Testjk {
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
                return new String(responseResult.toString().getBytes("UTF-8"),"UTF-8").toString();
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
        String xml="<?xml version = \"1.0\" encoding = \"utf-8\"?>\n" +
                "<bocb2e version=\"120\" security=\"true\" locale=\"zh_CN\">\n" +
                "<head>\n" +
                "<termid>E127000000001</termid>\n" +
                "<custid>133723595</custid>\n" +
                "<cusopr>135988154</cusopr>\n" +
                "<token>k4AWqk7R</token>\n" +
                "<trnid>20200403001</trnid>\n" +
                "<trncod>b2e0005</trncod>\n" +
                "\n" +
                "</head>\n" +
                "<trans>\n" +
                "<trn-b2e0005-rq>\n" +
                "<b2e0005-rq>\n" +
                "<account>\n" +
                "<ibknum>40303</ibknum>\n" +
                "<actacn>452058887515</actacn>\n" +
                "</account>\n" +
                "</b2e0005-rq>\n" +
                "\n" +
                "</trn-b2e0005-rq>\n" +
                "</trans>\n" +
                "</bocb2e>";
        String result = postConnection("http://180.167.111.253:8080/B2EC/E2BServlet",xml);
        System.out.println(result);
    }
}
