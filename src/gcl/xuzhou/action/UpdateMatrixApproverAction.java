package gcl.xuzhou.action;

import gcl.xuzhou.util.XuzhouWorkflowUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 获取矩阵审批人
 *
 * @author : adore
 * @version : v1.0
 * @since : 2019-12-03 9:21 下午
 */

public class UpdateMatrixApproverAction extends BaseBean implements Action {
    public String cbzx;

    @Override
    public String execute(RequestInfo info) {
        XuzhouWorkflowUtil workflowUtil = new XuzhouWorkflowUtil();
        RecordSet rs = new RecordSet();
        String requestid = Util.null2String(info.getRequestid());
        this.writeLog("获取矩阵审批人 Action开始：" + requestid);
        String tableName = workflowUtil.getTableName(requestid);
        String sql;
        String COSTMANAGER = "";
        String COSTLEADER = "";
        String otherMatri = "0";
        if (!"".equals(tableName)) {
            String cbzxcode = "";
            if("KOSTL".equals(cbzx)){
                sql = "select " + cbzx + " as cbzxcode from " + tableName + " where requestid=" + requestid + "";
                rs.execute(sql);
                if(rs.next()){
                    cbzxcode = Util.null2String(rs.getString("cbzxcode"));
                }
            }else{
                String xmh = "";
                String wbsys = "";
                sql = "select " + cbzx + " as cbzxcode,xmh,wbsys from " + tableName + " where requestid=" + requestid + "";
                rs.execute(sql);
                if(rs.next()){
                    cbzxcode = Util.null2String(rs.getString("cbzxcode"));
                    xmh = Util.null2String(rs.getString("xmh"));
                    wbsys = Util.null2String(rs.getString("wbsys"));
                }
                if("".equals(cbzxcode)){
                    if("".equals(xmh)){
                        cbzxcode = wbsys;
                    }else{
                        this.writeLog("xmh：" + xmh);
                        if(xmh.length()>6 &&xmh.substring(0,6).equals("000000")){
                            xmh = xmh.substring(6,xmh.length());
                            if(xmh.substring(0,1).equals("5")){
                                otherMatri = "1";
                            }else{
                                cbzxcode = xmh;
                            }

                        }else{
                            cbzxcode = xmh;
                        }
                    }
                }
            }
            if("1".equals(otherMatri)){
                String cjrdp = "";
                sql = "select a.departmentid from hrmresource a,workflow_requestbase b where a.id=b.creater and b.requestid="+requestid;
                this.writeLog("矩阵信息1：" + sql);
                rs.execute(sql);
                if(rs.next()){
                    cjrdp = Util.null2String(rs.getString("departmentid"));
                }
                sql = " select bmfzr,fgld from " + getMatrixName("半导体部门矩阵") + " where xjbmwd = '"+cjrdp+"'";
                this.writeLog("矩阵信息2：" + sql);
                rs.execute(sql);
                if (rs.next()) {
                    COSTMANAGER = Util.null2String(rs.getString("bmfzr"));
                    COSTLEADER = Util.null2String(rs.getString("fgld"));
                }

            }else {
                sql = " select CBZX,COSTMANAGER,COSTLEADER from " + getMatrixName("半导体成本中心矩阵") + " where CBZX in( " +
                        " select ID from HRMDEPARTMENT where DEPARTMENTCODE ='" + cbzxcode + "') ";
                this.writeLog("矩阵信息：" + sql);
                rs.execute(sql);
                if (rs.next()) {
                    COSTMANAGER = Util.null2String(rs.getString("COSTMANAGER"));
                    COSTLEADER = Util.null2String(rs.getString("COSTLEADER"));
                }
            }
            sql = " update " + tableName + " set COSTMANAGER='" + COSTMANAGER + "',COSTLEADER='" + COSTLEADER + "' " +
                    " where requestid= " + requestid;
            rs.execute(sql);
            this.writeLog("update sql:" + sql);
        }
        this.writeLog("获取矩阵审批人 Action完成：" + requestid);
        return Action.SUCCESS;
    }

    private String getMatrixName(String name) {
        RecordSet rs = new RecordSet();
        String tabName = "";
        String sql = "select * from MATRIXINFO where NAME='" + name + "'";
        rs.execute(sql);
        if (rs.next()) {
            String tabid = Util.null2String(rs.getString("id"));
            if (!"".equals(tabid)) {
                tabName = "MATRIXTABLE_" + tabid;
            }
        }
        return tabName;
    }
}
