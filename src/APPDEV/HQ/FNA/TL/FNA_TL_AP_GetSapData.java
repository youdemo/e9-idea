package APPDEV.HQ.FNA.TL;



import APPDEV.HQ.UTIL.BringMainAndDetailByMain;
import APPDEV.HQ.UTIL.GetMachineUtil;
import org.json.JSONObject;
import weaver.general.BaseBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取差旅人员基本信息
 */
public class FNA_TL_AP_GetSapData {

    public String getPersonTravelBaseInfo(String ryid,String gsdm,String startdate,String gw){
        BaseBean log = new BaseBean();
        JSONObject jo = new JSONObject();
        log.writeLog("FNA_TL_AP_GetSapData getPersonTravelBaseInfo","ryid:"+ryid+",gsdm:"+gsdm+",startdate:"+startdate+",gw:"+gw+"");
        String result = getBaseSapData(ryid,gsdm,startdate,gw,"36");
        log.writeLog("FNA_TL_AP_GetSapData getPersonTravelBaseInfo","result:"+result);
        try {
            String EV_ERGRU = "";//国内等级
            String EV_ERKLA = "";//国外等级
            String EV_FICTR = "";//基金中心
            String EV_KOSTL = "";//成本中心
            String EV_MOREI = "";//差旅组
            String EV_MESSAGE = "";//消息
            String EV_STATU = "";//状态
            String EV_BEZEICH = "";//基金中心显示名
            String EV_KTEXT = "";//成本中心描述
            String EV_ERGRU_TEXT= "";//国内差旅等级描述
            String EV_ERKLA_TEXT= "";//国外差旅等级描述
            String EV_APLUS = "";//1A+,0非A+
            JSONObject json = new JSONObject(result);
            JSONObject data = json.getJSONObject("type");
            EV_ERGRU = data.getString("EV_ERGRU");
            EV_ERKLA = data.getString("EV_ERKLA");
            EV_FICTR = data.getString("EV_FICTR");
            EV_KOSTL = data.getString("EV_KOSTL");
            EV_MOREI = data.getString("EV_MOREI");
            EV_MESSAGE = data.getString("EV_MESSAGE");
            EV_STATU = data.getString("EV_STATU"); 
            EV_BEZEICH = data.getString("EV_BEZEICH");
            EV_KTEXT = data.getString("EV_KTEXT");
            EV_ERGRU_TEXT = data.getString("EV_ERGRU_TEXT");
            EV_ERKLA_TEXT = data.getString("EV_ERKLA_TEXT");
            EV_APLUS = data.getString("EV_APLUS");

            jo.put("EV_ERGRU",EV_ERGRU);
            jo.put("EV_ERKLA",EV_ERKLA);
            jo.put("EV_FICTR",EV_FICTR);
            jo.put("EV_KOSTL",EV_KOSTL);
            jo.put("EV_MOREI",EV_MOREI);
            jo.put("EV_MESSAGE",EV_MESSAGE);
            jo.put("EV_STATU",EV_STATU);//1 成功 0 失败
            jo.put("EV_BEZEICH",EV_BEZEICH);
            jo.put("EV_KTEXT",EV_KTEXT);
            jo.put("EV_ERGRU_TEXT",EV_ERGRU_TEXT);
            jo.put("EV_ERKLA_TEXT",EV_ERKLA_TEXT);
            jo.put("EV_APLUS",EV_APLUS);
        } catch (Exception e) {
            log.writeLog(e);
            log.writeLog("解析json格式异常：" + result);
        }
        return jo.toString();

    }

    public String getBaseSapData(String ryid, String gsdm, String startdate, String gw,
                                String workflowId) {
        Map<String, String> oaDatas = new HashMap<String, String>();
        oaDatas.put("IV_BEGDA", startdate);
        oaDatas.put("IV_BUKRS", gsdm);
        oaDatas.put("IV_PERNR", ryid);
        oaDatas.put("IV_PLANS", gw);
        String dataSourceId = "";
        String machine = GetMachineUtil.getMachine();
        if ("PRO".equals(machine)) {//正式
            dataSourceId = "41";
        }else {//96
            dataSourceId = "141";
        }
        BringMainAndDetailByMain bmb = new BringMainAndDetailByMain(dataSourceId);//正式 41 测试141
        String result = bmb.getReturn(oaDatas, workflowId, "", null);
        return result;
    }
}
