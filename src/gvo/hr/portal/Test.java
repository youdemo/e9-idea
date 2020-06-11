package gvo.hr.portal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * createby jianyong.tang
 * createTime 2020/4/22 17:08
 * version v1
 * desc
 */
public class Test {
//    public static void main(String[] args) throws JSONException {
//        String orgcodes = "101530,101535,100000,100002,100278,100001,100279";
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//        String enddate = sf.format(new Date());
//        String begindate = enddate.substring(0,7)+"-01";
//        JSONObject parajson = new JSONObject();
//        JSONArray ja = new JSONArray();
//        String orgarr[] = orgcodes.split(",");
//        for(String orgcode:orgarr){
//            JSONObject jo = new JSONObject();
//            jo.put("deptid",orgcode);
//            jo.put("begindate",begindate);
//            jo.put("enddate",enddate);
//            ja.put(jo);
//        }
//        parajson.put("depts",ja);
//        System.out.println(parajson.toString());
//    }

    public static void main(String[] args) throws JSONException {
        String orgcodes = "101530,101535,100000,100002,100278,100001,100279";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = sf.format(new Date());
        String begindate = enddate.substring(0,7)+"-01";
        JSONObject parajson = new JSONObject();
        JSONArray ja = new JSONArray();
        String orgarr[] = orgcodes.split(",");
        for(String orgcode:orgarr){
            JSONObject jo = new JSONObject();
            jo.put("deptid",orgcode);
            ja.put(jo);
        }
        parajson.put("depts",ja);
        parajson.put("datetime","2020-06-10");
        System.out.println(parajson.toString());
    }
}
