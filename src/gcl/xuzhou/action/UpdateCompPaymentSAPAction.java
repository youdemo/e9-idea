package gcl.xuzhou.action;

import gcl.xuzhou.util.XuzhouSAPPIBackUtil;
import gcl.xuzhou.util.XuzhouWorkflowUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 回传对公报销数据到SAP PI
 *
 * @author : adore
 * @version : v1.0
 * @since : 2019-09-25 14:06
 */

public class UpdateCompPaymentSAPAction extends BaseBean implements Action {
    public String status;

    @Override
    public String execute(RequestInfo info) {
        RecordSet rs = new RecordSet();
        XuzhouSAPPIBackUtil backUtil = new XuzhouSAPPIBackUtil();
        XuzhouWorkflowUtil workflowUtil = new XuzhouWorkflowUtil();
        String requestid = Util.null2String(info.getRequestid());
        this.writeLog("回传对公报销数据到SAP PI Action开始：" + requestid);
        String tableName = workflowUtil.getTableName(requestid);
        String workflowid = workflowUtil.getWorkflowid(requestid);
        String sql;
        String sqdh = ""; // 申请单号
        String bxsqzt = ""; // 报销申请单状态
        if (!"".equals(tableName)) {
            sql = " select * from " + tableName + " where requestid= " + requestid;
            this.writeLog("流程主数据：" + sql);
            rs.execute(sql);
            if (rs.next()) {
                sqdh = Util.null2String(rs.getString("ZRCNO"));
            }

            Map<String, String> map = new HashMap<>();
            map.put("workflowCode", workflowid);
            map.put("docId", TimeUtil.getCurrentDateString().replaceAll("-",""));
            map.put("docNumber", sqdh);
            map.put("status", status);
            map.put("otherData", "");
            this.writeLog("map:" + map);
            String result = backUtil.xuzhouSAPPIBack(map);
            this.writeLog("result:" + result);
            sql = " update " + tableName + " set writeSAPlog='" + result + "' where requestid= " + requestid;
            rs.execute(sql);
        }
        this.writeLog("回传对公报销数据到SAP PI Action完成：" + requestid);
        return Action.SUCCESS;
    }
}
