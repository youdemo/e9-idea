package rrd.sysn;

import rrd.util.TransUtil;
import weaver.conn.RecordSet;
import weaver.conn.RecordSetDataSource;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import java.text.SimpleDateFormat;
import java.util.*;

public class SysnBaseInfo extends BaseCronJob {
    @Override
    public void execute() {
        BaseBean log = new BaseBean();
        log.writeLog("SysnBaseInfo","开始同步客户基础信息");
        sysndata();
        log.writeLog("SysnBaseInfo","结束同步客户基础信息");
    }

    public void sysndata(){
        RecordSet rs = new RecordSet();
        RecordSetDataSource rsd = new RecordSetDataSource("PersonInfo");
        String sql = "select * from uf_cusinfo_sysnmt where sfqy='0'";
        rs.execute(sql);
        while(rs.next()){
            Map<String,String> mainMap = new HashMap<String,String>();
            mainMap.put("id", Util.null2String(rs.getString("id")));
            mainMap.put("type", Util.null2String(rs.getString("type")));
            mainMap.put("othertbname", Util.null2String(rs.getString("othertbname")));
            mainMap.put("oatbname", Util.null2String(rs.getString("oatbname")));
            mainMap.put("sxtj", Util.null2String(rs.getString("sxtj")));
            if("".equals(mainMap.get("othertbname"))||"".equals(mainMap.get("oatbname"))){
                continue;
            }
            writeLog(mainMap.get("othertbname"));
            systableDate(mainMap);

        }
    }

    public void systableDate(Map<String,String> mainMap){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String nowDate = sdf.format(new Date());
        String nowTime = sdf1.format(new Date());
        RecordSet rs = new RecordSet();
        TransUtil tu = new TransUtil();
        InsertUtil iu = new InsertUtil();
        ModeRightInfo ModeRightInfo = new ModeRightInfo();
        String oatbname = mainMap.get("oatbname");
        String modeid = tu.getModeId(oatbname);
        RecordSetDataSource rsd = new RecordSetDataSource("PersonInfo");
        String sql = "select * from uf_cusinfo_sysnmt_dt1 where mainid="+mainMap.get("id");
        writeLog(sql);
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        rs.execute(sql);
        while(rs.next()){
            Map<String,String> dtMap = new HashMap<String,String>();
            dtMap.put("qxtzd",Util.null2String(rs.getString("qxtzd")));
            dtMap.put("oaxtzd",Util.null2String(rs.getString("oaxtzd")));
            list.add(dtMap);
        }
        if(list.size()<=0){
            return;
        }
        String selectfield = "";
        String flag = "";
        for(Map<String,String> dtMap:list){
            selectfield = selectfield+flag+dtMap.get("qxtzd");
            flag = ",";
        }
        String rsdsql = "select distinct "+selectfield+" from "+mainMap.get("othertbname")+" where 1=1";
        if(!"".equals(mainMap.get("sxtj"))){
            rsdsql = rsdsql + " and "+mainMap.get("sxtj");
        }
        if("HL".equals(mainMap.get("type"))){
            rsdsql = rsdsql + " and VALID_FROM_DATE_ID < to_char(SYSDATE,'yyyyMMdd') AND to_char(SYSDATE,'yyyyMMdd') < VALID_TO_DATE_ID and CXCRDC = 'USD'";
        }
        writeLog(rsdsql);
        rsd.execute(rsdsql);
        while(rsd.next()){
            Map<String,String> dataMap = new HashMap<String,String>();
            for(Map<String,String> dtMap:list){
                dataMap.put(dtMap.get("oaxtzd"),Util.null2String(rsd.getString(dtMap.get("qxtzd"))));
            }
            if(checkExist(dataMap,oatbname)){
                continue;
            }
            dataMap.put("modedatacreatedate",nowDate);
            dataMap.put("modedatacreatetime",nowTime);
            dataMap.put("modedatacreater","1");
            dataMap.put("modedatacreatertype","0");
            dataMap.put("formmodeid",modeid);
            iu.insert(dataMap,oatbname);
            String billid = "";
            sql = "select max(id) as billid from "+oatbname+"";
            rs.execute(sql);
            if(rs.next()){
                billid = Util.null2String(rs.getString("billid"));
            }
            if(!"".equals(billid)){
                ModeRightInfo.editModeDataShare(
                        Integer.valueOf("1"),
                        Util.getIntValue(modeid),
                        Integer.valueOf(billid));
            }



        }
    }

    public boolean checkExist(Map<String,String> dataMap,String oatbname){
        RecordSet rs = new RecordSet();
        int count =0;
        String sql = "select count(1) as count from "+oatbname+" where 1=1 ";
        Iterator it = dataMap.keySet().iterator();
        while(it.hasNext()){
            String fieldname = (String) it.next();
            sql = sql + " and "+fieldname+"='"+dataMap.get(fieldname).replaceAll("'","''")+"'";
        }
        //writeLog("checkIsexist:"+sql);
        rs.execute(sql);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    private void writeLog(Object obj) {
        if (true) {
            new BaseBean().writeLog(this.getClass().getName(), obj);
        }
    }

}
