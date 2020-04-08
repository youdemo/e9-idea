package APPDEV.HQ.FNA.TL;



import APPDEV.HQ.UTIL.BringMainAndDetailByMain;
import APPDEV.HQ.UTIL.BringMainAndDetailByMain_MoreTable;
import APPDEV.HQ.UTIL.GetMachineUtil;
import jxl.read.biff.Record;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SAP模拟过账需求
 */
public class FNA_TL_AP_SapMIGZ {

    public String doSapMNGZ(Map<String,String> map){
        BaseBean log = new BaseBean();
        JSONObject jo = new JSONObject();
        log.writeLog("FNA_TL_AP_SapMIGZ start："+com.alibaba.fastjson.JSONObject.toJSONString(map).toString());
        String tablename=getTableNameByRequestid(map.get("rqid"));
        RecordSet rs = new RecordSet();
        String sql = "select * from "+tablename+" where requestid="+map.get("rqid");
        rs.execute(sql);
        if(rs.next()){
            map.put("IV_CBZJE", Util.null2String(rs.getString("MEEXP")));
            map.put("IV_REINR",  Util.null2String(rs.getString("traappli")));
            map.put("IV_LOCATION",  Util.null2String(rs.getString("TavelDestCityALL")));

            map.put("IV_BUKRS",  Util.null2String(rs.getString("BtrBukrs")));
            map.put("IV_USD",  Util.null2String(rs.getString("PayInDoll")));
            map.put("IV_UKURS",  Util.null2String(rs.getString("USDExchRate")));
            map.put("IV_PERNR",  Util.null2String(rs.getString("BtrPER")));

            map.put("IV_KOSTL",  Util.null2String(rs.getString("COSTCENTER")));
            map.put("IV_FISTL",  Util.null2String(rs.getString("FundCenterCODE")));
            map.put("IV_GZ_PERNR", Util.null2String(rs.getString("Posting")));

        }
        log.writeLog("FNA_TL_AP_SapMIGZ start："+com.alibaba.fastjson.JSONObject.toJSONString(map).toString());
        String result = getBaseSapData(map,"38");
        log.writeLog("FNA_TL_AP_SapMIGZ doSapMNGZ","result:"+result);
        try {
            String EV_BELNR = "";//会计凭证号码
            String EV_BLDAT = "";//记账日期
            String EV_GJAHR = "";//财年
            String EV_MESSAGE = "";//消息
            String EV_STATU = "";//差旅组
            String EV_STR = "";//消息
            JSONObject json = new JSONObject(result);
            JSONObject data = json.getJSONObject("type");
            EV_BELNR = data.getString("EV_BELNR");
            EV_BLDAT = data.getString("EV_BLDAT");
            EV_GJAHR = data.getString("EV_GJAHR");
            EV_MESSAGE = data.getString("EV_MESSAGE");
            EV_STATU = data.getString("EV_STATU");
            EV_STR = data.getString("EV_STR");

            jo.put("EV_BELNR",EV_BELNR);
            jo.put("EV_BLDAT",EV_BLDAT);
            jo.put("EV_GJAHR",EV_GJAHR);
            jo.put("EV_MESSAGE",EV_MESSAGE);
            jo.put("EV_STATU",EV_STATU);
            jo.put("EV_STR",EV_STR);
        } catch (Exception e) {
            log.writeLog(e);
            log.writeLog("解析json格式异常：" + result);
        }
        return jo.toString();

    }

    /**
     * 解析返回值
     * @param map
     * @param workflowId
     * @return
     */
    public String getBaseSapData(Map<String,String> map,String workflowId) {
        Map<String, String> oaDatas = new HashMap<String, String>();
        oaDatas.put("IV_CBZJE", map.get("IV_CBZJE"));
        oaDatas.put("IV_BLDAT", map.get("IV_BLDAT"));
        oaDatas.put("IV_REINR", map.get("IV_REINR"));
        oaDatas.put("IV_LOCATION", map.get("IV_LOCATION"));
        oaDatas.put("IV_BUKRS", map.get("IV_BUKRS"));
        oaDatas.put("IV_USD", map.get("IV_USD"));
        oaDatas.put("IV_UKURS", map.get("IV_UKURS"));
        oaDatas.put("IV_PERNR", map.get("IV_PERNR"));
        oaDatas.put("IV_KOSTL", map.get("IV_KOSTL"));
        oaDatas.put("IV_FISTL", map.get("IV_FISTL"));
        oaDatas.put("IV_GZ_PERNR", map.get("IV_GZ_PERNR"));
        oaDatas.put("IV_STATUS", "A");
        String rqid = map.get("rqid");
        List<String> inTableName = new ArrayList<>();
        inTableName.add("FEE");
        inTableName.add("PFEE");
        inTableName.add("HFEE");
        inTableName.add("TFEE");
        inTableName.add("OFEE");
        Map<String,List<Map<String, String>>> datamaps = getDataMaps(rqid);
        String dataSourceId = "";
        String machine = GetMachineUtil.getMachine();
        if ("PRO".equals(machine)) {//正式
            dataSourceId = "41";
        }else {//96
            dataSourceId = "141";
        }
        BringMainAndDetailByMain_MoreTable bmb = new BringMainAndDetailByMain_MoreTable(dataSourceId);//正式 2 测试141
        String result = bmb.getReturn(oaDatas, workflowId, inTableName, datamaps);

        return result;
    }

    /**
     * 获取多个表数据
     * @param rqid
     * @return
     */
    public  Map<String,List<Map<String, String>>> getDataMaps(String rqid){
        RecordSet rs = new RecordSet();
        String tablename=getTableNameByRequestid(rqid);
        Map<String,List<Map<String, String>>> dataMaps = new HashMap<>();
        String mainid = "";
        String sql = "select id from "+tablename+" where requestid="+rqid;
        rs.execute(sql);
        if(rs.next()){
            mainid = rs.getString("id");
        }

        List<Map<String, String>> feeList = new ArrayList<>();
        sql = "select othcost,othtaxm,othcosttype,othmwskz,othhkont,othcurr,othrate from "+tablename+"_dt3 where mainid="+mainid+" order by id asc";
        rs.execute(sql);
        while(rs.next()){
            Map<String, String> map = new HashMap<>();
            map.put("BETRG",Util.null2String(rs.getString("othcost")));//凭证货币金额
            map.put("BETRG_TAX",Util.null2String(rs.getString("othtaxm")));//凭证货币金额
            map.put("SPKZL",Util.null2String(rs.getString("othcosttype")));//差旅费用类型
            map.put("MWSKZ_LV",Util.null2String(rs.getString("othmwskz")));//税率(%)
            map.put("HKONT",Util.null2String(rs.getString("othhkont")));//总分类帐帐目
            map.put("WAERS",Util.null2String(rs.getString("othcurr")));//货币码
            map.put("UKURS",Util.null2String(rs.getString("othrate")));//汇率
            feeList.add(map);
        }
        dataMaps.put("FEE",feeList);

        List<Map<String, String>> PFEEList = new ArrayList<>();
        sql = "select pcosttype,pmwskz,pcost,ptaxm,phkont,prate,pcurr from "+tablename+"_dt6 where mainid="+mainid+" order by id asc";
        rs.execute(sql);
        while(rs.next()){
            Map<String, String> map = new HashMap<>();
            map.put("BETRG",Util.null2String(rs.getString("pcost")));//凭证货币金额
            map.put("BETRG_TAX",Util.null2String(rs.getString("ptaxm")));//凭证货币金额
            map.put("SPKZL",Util.null2String(rs.getString("pcosttype")));//差旅费用类型
            map.put("MWSKZ_LV",Util.null2String(rs.getString("pmwskz")));//税率(%)
            map.put("HKONT",Util.null2String(rs.getString("phkont")));//总分类帐帐目
            map.put("WAERS",Util.null2String(rs.getString("pcurr")));//货币码
            map.put("UKURS",Util.null2String(rs.getString("prate")));//汇率
            PFEEList.add(map);
        }
        dataMaps.put("PFEE",PFEEList);

        List<Map<String, String>> HFEEList = new ArrayList<>();
        sql = "select homwskz,hocost,hotaxm,hohkont,hocosttype,hocurr,horate from "+tablename+"_dt5 where mainid="+mainid+" order by id asc";
        rs.execute(sql);
        while(rs.next()){
            Map<String, String> map = new HashMap<>();
            map.put("BETRG",Util.null2String(rs.getString("hocost")));//凭证货币金额
            map.put("BETRG_TAX",Util.null2String(rs.getString("hotaxm")));//凭证货币金额
            map.put("SPKZL",Util.null2String(rs.getString("hocosttype")));//差旅费用类型
            map.put("MWSKZ_LV",Util.null2String(rs.getString("homwskz")));//税率(%)
            map.put("HKONT",Util.null2String(rs.getString("hohkont")));//总分类帐帐目
            map.put("WAERS",Util.null2String(rs.getString("hocurr")));//货币码
            map.put("UKURS",Util.null2String(rs.getString("horate")));//汇率
            HFEEList.add(map);
        }
        dataMaps.put("HFEE",HFEEList);

        List<Map<String, String>> TFEEList = new ArrayList<>();
        sql = "select acccosttype,accmwskz,acccostori,acctaxM,acchkont,acccurr,accrate from "+tablename+"_dt10 where mainid="+mainid+" order by id asc";
        rs.execute(sql);
        while(rs.next()){
            Map<String, String> map = new HashMap<>();
            map.put("BETRG",Util.null2String(rs.getString("acccostori")));//凭证货币金额
            map.put("BETRG_TAX",Util.null2String(rs.getString("acctaxM")));//凭证货币金额
            map.put("SPKZL",Util.null2String(rs.getString("acccosttype")));//差旅费用类型
            map.put("MWSKZ_LV",Util.null2String(rs.getString("accmwskz")));//税率(%)
            map.put("HKONT",Util.null2String(rs.getString("acchkont")));//总分类帐帐目
            map.put("WAERS",Util.null2String(rs.getString("acccurr")));//货币码
            map.put("UKURS",Util.null2String(rs.getString("accrate")));//汇率
            TFEEList.add(map);
        }
        dataMaps.put("TFEE",TFEEList);

        List<Map<String, String>> OFEEList = new ArrayList<>();
        sql = "select tramwskz,tracost,trataxm,tracurr,tracosttype,trahkont,trarate from "+tablename+"_dt2 where mainid="+mainid+" order by id asc";
        rs.execute(sql);
        while(rs.next()){
            Map<String, String> map = new HashMap<>();
            map.put("BETRG",Util.null2String(rs.getString("tracost")));//凭证货币金额
            map.put("BETRG_TAX",Util.null2String(rs.getString("trataxm")));//凭证货币金额
            map.put("SPKZL",Util.null2String(rs.getString("tracosttype")));//差旅费用类型
            map.put("MWSKZ_LV",Util.null2String(rs.getString("tramwskz")));//税率(%)
            map.put("HKONT",Util.null2String(rs.getString("trahkont")));//总分类帐帐目
            map.put("WAERS",Util.null2String(rs.getString("tracurr")));//货币码
            map.put("UKURS",Util.null2String(rs.getString("trarate")));//汇率
            OFEEList.add(map);
        }
        dataMaps.put("OFEE",OFEEList);
        return dataMaps;


    }
    public String getTableNameByRequestid(String requestid) {
        RecordSet rs = new RecordSet();
        String tableName = "";
        rs.execute("select tablename from workflow_bill where id=(select formid from workflow_base a," +
                "workflow_requestbase b where a.id=b.workflowid and b.requestid="+requestid+")");
        if (rs.next()) {
            tableName = Util.null2String(rs.getString("tablename"));
        }
        return tableName;
    }
}
