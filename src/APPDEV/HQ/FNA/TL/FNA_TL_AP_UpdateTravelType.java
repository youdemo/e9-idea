package APPDEV.HQ.FNA.TL;

import APPDEV.HQ.FNA.UTIL.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class FNA_TL_AP_UpdateTravelType implements Action {
    @Override
    public String execute(RequestInfo info) {
        TransUtil tu = new TransUtil();
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        String mainId = "";
        String TravelType = "";
        String TavelDestCityALL = "";//目的地汇总
        String BTRCon = "0";//0国内 1国外
        String flag = "";
        String  sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            mainId = Util.null2String(rs.getString("id"));

        }


        sql = "select distinct TavelType from "+tableName+"_dt1 where mainid="+mainId;
        rs.execute(sql);
        while(rs.next()){
            TravelType = TravelType+flag+Util.null2String(rs.getString("TavelType"));
            flag = ",";
        }
        flag= "";
        sql = "select distinct b.CITYNAME from "+tableName+"_dt1 a,CTRIP_HOTELCITY b where  a.TavelDestCity=b.CITYKEY and mainid="+mainId;
        rs.execute(sql);
        while(rs.next()){
            TavelDestCityALL = TavelDestCityALL+flag+Util.null2String(rs.getString("CITYNAME"));
            flag = ",";
        }
        TavelDestCityALL = TavelDestCityALL.replaceAll("'","''");
        int count = 0;
        //判断是否是特殊城市 有就是国外
        sql = "select count(1)  as count from "+tableName+"_dt1 a,uf_fna_city_special b where a.TavelDestCity = b.SPEFORCITY and a.mainid="+mainId;
        rs.execute(sql);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count>0){
            BTRCon = "1";
        }
        //判断是否有国外的城市
        if("0".equals(BTRCon)) {
            count = 0;
            sql = "select count(1) as count from " + tableName + "_dt1 a,ctrip_hotelcity b where a.TavelDestCity = b.citykey and b.COUNTRYNAME <> '中国' and a.mainid=" + mainId;
            rs.execute(sql);
            if (rs.next()) {
                count = rs.getInt("count");
            }
            if(count>0){
                BTRCon = "1";
            }
        }
        sql = "update "+tableName+" set TavelType='"+TravelType+"',TavelDestCityALL='"+TavelDestCityALL+"',BTRCon='"+BTRCon+"' where requestid="+requestid;
        rs.execute(sql);
        return SUCCESS;
    }
}
