package APPDEV.HQ.FNA.TL;

import APPDEV.HQ.FNA.UTIL.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 更新酒店机票预订类型
 */
public class FNA_TL_AP_UpdateOrderType implements Action {
    @Override
    public String execute(RequestInfo info) {
        TransUtil tu = new TransUtil();
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        RecordSet rs_dt = new RecordSet();
        String mainId = "";

        String  sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            mainId = Util.null2String(rs.getString("id"));

        }
        //更新酒店预订类型
        sql = "update "+tableName+"_dt2 a set CtripType=(select case when countrychina='中国' then '3' else '4' end  from uf_fna_LOC where citykey=a.CtripCity) where mainid="+mainId;
        rs.execute(sql);
        //更新机票预订类型
        sql = "select id,(select distinct COUNTRYNAME  from ctrip_aircity where citykey=a.AirDepCity and COUNTRYNAME is not null) as countrycf,(select distinct COUNTRYNAME  from ctrip_aircity where citykey=a.AirDestCity and COUNTRYNAME is not null) as countrydd,(select count(1) from uf_fna_aircity_spec where speforcity=a.AirDepCity) as countcf,(select count(1) from uf_fna_aircity_spec where speforcity=a.AirDestCity) as countdd from "+tableName+"_dt3 a where mainid="+mainId;
        rs.execute(sql);
        while(rs.next()){
            String jplx="";//机票类型
            String countrycf = Util.null2String(rs.getString("countrycf"));//出发国家
            String countrydd = Util.null2String(rs.getString("countrydd"));//到达国家
            int countcf = rs.getInt("countcf");//判断出发城市是否在特殊城市列表中
            int countdd = rs.getInt("countdd");//判断到达城市是否在特殊城市列表中
            String dtid = rs.getString("id");
            if(!"中国".equals(countrycf) || !"中国".equals(countrydd) || countcf > 0 || countdd > 0){
                jplx = "2";
            }else{
                jplx = "1";
            }
            rs_dt.execute("update "+tableName+"_dt3 set TicketType='"+jplx+"' where id="+dtid);
        }


        return SUCCESS;
    }
}
