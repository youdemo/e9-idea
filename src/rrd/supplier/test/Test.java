package rrd.supplier.test;

import com.engine.workflow.biz.requestList.GenerateDataInfoBiz;
import com.engine.workflow.entity.RequestListDataInfoEntity;
import com.engine.workflow.entity.WorkflowDimensionEntity;
import com.engine.workflow.util.WorkflowDimensionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import rrd.supplier.util.DESCoder;
import sun.misc.BASE64Decoder;
import weaver.filter.XssUtil;
import weaver.interfaces.workflow.action.WorkflowToDoc;
import weaver.systeminfo.SystemEnv;
import weaver.workflow.request.todo.OfsSettingObject;
import weaver.workflow.request.todo.RequestUtil;
import weaver.workflow.workflow.WorkflowDoingDimension;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * createby jianyong.tang
 * createTime 2020/2/26 11:16
 * version v1
 * desc
 */
public class Test {
//    public static void main(String[] args) throws Exception {
//        String aaa = "RGZ1Pm1JoLWuBfQqqUJoZ1Wba7OAngdD";
//        aaa=URLEncoder.encode(aaa,"UTF-8");
//        System.out.println(aaa);
//        aaa=URLDecoder.decode(aaa,"UTF-8");
//        System.out.println(aaa);
//
//    }

    public static void test11(String aaa){
        String key = "ddpYENbNURw=QED";
        String result = "";
        try {
            byte[] inputData = new BASE64Decoder().decodeBuffer(aaa);
            byte[] outputData = DESCoder.decrypt(inputData, key);
            result = new String(outputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
    public static void main(String[] args) throws Exception {
        String aaa = "pW2ccRvXRzKjh23jCJtwp58keigMl4zy%250A";
        aaa = URLDecoder.decode(aaa,"UTF-8");
        System.out.println(aaa);
        aaa = URLDecoder.decode(aaa,"UTF-8");
        System.out.println(aaa);
        System.out.println("cccc");
        test11(aaa);
        aaa = "pW2ccRvXRzKjh23jCJtwp58keigMl4zy%250B";
        aaa = URLDecoder.decode(aaa,"UTF-8");
        System.out.println(aaa);
        aaa = URLDecoder.decode(aaa,"UTF-8");
        System.out.println(aaa);
        test11(aaa);
        test11("pW2ccRvXRzKjh23jCJtwp58keigMl4zy\n");
        //WorkflowToDoc
       // RequestListDataInfoEntity bean = new GenerateDataInfoBiz().generateEntity(request, user);

//        String aaa = "~`~`7 港澳台企业`~`8 HK&Macao&Taiwan owned`
//        WorkflowDimensionUtils.getToDoSqlWhere~`~";
//        System.out.println(aaa.replace("~`~`7 ","").replace("`~`8 ","/").replace("`~`~",""));
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowtime = sf.format(new Date());
        String jmstre=getEncryptBASE64("259264",nowtime);
        System.out.println(jmstre);
        jmstre = URLEncoder.encode(jmstre,"UTF-8");
        System.out.println(URLEncoder.encode(jmstre,"UTF-8"));
        System.out.println(URLEncoder.encode(jmstre,"UTF-8"));
        System.out.println(URLEncoder.encode(jmstre,"UTF-8"));
        //String jm= getEncryptBASE64JM(jmstre);
        System.out.println(System.currentTimeMillis());
        //String jmattr[] = jm.split(",");
        //if(jmattr.length==2){
        //    System.out.println(jmattr[0]);
        //}
    }
    /**
     * 获取加密的数据
     * @return
     */
    public static String getEncryptBASE64(String rid,String nowtime) {
        String key = "ddpYENbNURw=QED";
        String inputstr = rid+","+nowtime;
        String result = "";
        byte[] inputData = inputstr.getBytes();
        try {
            inputData = DESCoder.encrypt(inputData, key);
            result = DESCoder.encryptBASE64(inputData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 解密数据  并且判断是否超时
     * @param str
     * @return
     */
    public static String getEncryptBASE64JM(String str) {
        String key = "ddpYENbNURw=QED";
        String result = "";
        boolean  flag = false;
        try {
            byte[] inputData = new BASE64Decoder().decodeBuffer(str);
            byte[] outputData = DESCoder.decrypt(inputData, key);
            result = new String(outputData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }


}
