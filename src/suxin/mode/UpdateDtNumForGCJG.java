package suxin.mode;

import java.util.*;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.soa.workflow.request.RequestInfo;
import weaver.formmode.customjavacode.AbstractModeExpandJavaCode;

import java.util.Map;

public class UpdateDtNumForGCJG extends AbstractModeExpandJavaCode {
    @Override
    /**
     * 执行模块扩展动作
     * @param param
     *  param包含(但不限于)以下数据
     *  user 当前用户
     */
    public void doModeExpand(Map<String, Object> param) throws Exception {
        new BaseBean().writeLog("UpdateDtNumForGCJG start");
        User user = (User)param.get("user");
        int  billid = -1;//数据id
        int modeid = -1;//模块id
        RequestInfo requestInfo = (RequestInfo)param.get("RequestInfo");
        if(requestInfo!=null){
            billid = Util.getIntValue(requestInfo.getRequestid());
            modeid = Util.getIntValue(requestInfo.getWorkflowid());
            if(billid>0&&modeid>0){
                RecordSet rs =new RecordSet();

                String tableName = "";//建模表名
                String sql = "select b.tablename from modeinfo a,workflow_bill b where a.formid=b.id and a.id="
                        + modeid;
                rs.execute(sql);
                if (rs.next()) {
                    tableName = Util.null2String(rs.getString("tablename"));
                }

                sql = "update "+tableName+"_dt1 set clxxjbm=a.clbm+clxxjbm from "+tableName+" a where a.id=uf_EMC_CLBMJGXX_dt1.mainid and a.id="+billid;
                new BaseBean().writeLog(sql);
                rs.execute(sql);


            }
        }
    }


}
