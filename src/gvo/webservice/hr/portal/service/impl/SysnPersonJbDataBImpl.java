package gvo.webservice.hr.portal.service.impl;

import gvo.webservice.hr.portal.util.InsertUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/5/15 13:12
 * version v1
 * desc
 */
public class SysnPersonJbDataBImpl {

    /**
     * 同步处理数据
     * @param json
     * @return
     * @throws Exception
     */
    public String sysnInfo(String json) throws Exception {
        BaseBean log = new BaseBean();
        if("".equals(json)) {
            return "{\"result\":\"E\",\"msg\":\"请传递正确的json格式\"}";
        }
        RecordSet rs = new RecordSet();
        InsertUtil iu = new InsertUtil();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tablename = "uf_hr_jbsj_b";
        String sql = "";
        String nowtime = "";
        String workcode = "";//工号
        String month = "";//月份
        JSONArray data = new JSONArray(json);
        boolean result = true;
        for(int i=0;i<data.length();i++) {
            JSONObject jo = data.getJSONObject(i);
            workcode = jo.getString("workcode");
            month = jo.getString("month");
            nowtime = sf.format(new Date());
            //判断主表数据是否存在不存在新增
            String billid = "";
            sql = "select id from " + tablename + " where workcode='" + workcode + "' and month='" + month + "'";
            rs.execute(sql);
            if (rs.next()) {
                billid = Util.null2String(rs.getString("id"));
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("workcode", workcode);
            map.put("month", month);
            map.put("psjb", jo.getString("psjb"));//平时加班
            map.put("zmjb", jo.getString("zmjb"));//周末加班
            map.put("jjrjb", jo.getString("jjrjb"));//节假日加班
            map.put("jbhj", jo.getString("jbhj"));//加班合计
            map.put("bzjbsxzss", jo.getString("bzjbsxzss"));//标准加班受限总时数
            map.put("dzhjbsxzss", jo.getString("dzhjbsxzss"));//调整后加班受限总时数
            map.put("bzbfb", jo.getString("bzbfb"));//标准百分比
            map.put("dzhbfb", jo.getString("dzhbfb"));//调整后百分比
            String year = "";
            String yf = "";
            if (!"".equals(month)) {
                year = month.substring(0, 4);
                yf = month.substring(5, 7);
            }
            map.put("year", year);//
            map.put("yf", yf);//
            map.put("gxsj", nowtime);
            if ("".equals(billid)) {
                result = iu.insert(map, tablename);
            } else {
                result = iu.updateGen(map, tablename, "id", billid);
            }
            if (!result) {
                log.writeLog("SysnPersonSchedulingImpl", jo.toString());
                return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败员工编码：" + workcode + "\"}";
            }
        }
        return "{\"result\":\"S\",\"msg\":\"成功\"}";
    }
}
