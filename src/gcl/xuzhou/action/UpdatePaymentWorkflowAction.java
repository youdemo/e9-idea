package gcl.xuzhou.action;

import gcl.xuzhou.util.XuzhouSAPPIBackUtil;
import gcl.xuzhou.util.XuzhouWorkflowUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;


/**
 * 回传Payment数据到SAP PI
 *
 * @author : adore
 * @version : v1.0
 * @since : 2019-09-25 14:06
 */

public class UpdatePaymentWorkflowAction extends BaseBean implements Action {
    public String status;

    @Override
    public String execute(RequestInfo info) {
        RecordSet rs = new RecordSet();
        XuzhouWorkflowUtil workflowUtil = new XuzhouWorkflowUtil();
        XuzhouSAPPIBackUtil backUtil = new XuzhouSAPPIBackUtil();
        String requestid = Util.null2String(info.getRequestid());
        this.writeLog("回传Payment数据到SAP PI Action开始：" + requestid);
        String tableName = workflowUtil.getTableName(requestid);
        String workflowid = workflowUtil.getWorkflowid(requestid);
        String sql;
        String mainid = "";
        String REVNO = "";
        String EBELN = "";
        if (!"".equals(tableName)) {
            sql = " select * from " + tableName + " where requestid= " + requestid;
            this.writeLog("流程主数据：" + sql);
            rs.execute(sql);
            if (rs.next()) {
                mainid = Util.null2String(rs.getString("id"));
                REVNO = Util.null2String(rs.getString("REVNO"));
                EBELN = Util.null2String(rs.getString("EBELN"));
            }

            if (!"".equals(mainid)) {
                try {
                    JSONArray array = new JSONArray();
                    sql = " select * from " + tableName + "_dt1 where mainid= " + mainid;
                    this.writeLog("dt sql:" + sql);
                    rs.execute(sql);
                    while (rs.next()) {
                        JSONObject object = new JSONObject();
                        String sffk = Util.null2String(rs.getString("sffk"));
                        String guid = Util.null2String(rs.getString("guid"));
                        String SGTXT = Util.null2String(rs.getString("SGTXT"));
                        String status1;
                        if ("X".equals(status)) {
                            status1 = "X";
                        } else {
                            if ("1".equals(sffk)) {
                                status1 = "";
                            } else {
                                status1 = "X";
                            }
                        }
                        object.put("guid", guid);
                        object.put("status", status1);
                        object.put("otherData", SGTXT);
                        object.put("workflowCode", workflowid);
                        array.put(object);
                    }
                    this.writeLog("array:" + array.toString());
                    if (array.length() > 0) {
                        String result = backUtil.xuzhouSAPPayment(array.toString());
                        this.writeLog("result:" + result);
                        sql = " update " + tableName + " set writeSAPlog='" + result + "' where requestid= " + requestid;
                        rs.execute(sql);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.writeLog("回传Payment数据到SAP PI Action完成：" + requestid);
        return Action.SUCCESS;
    }
}
