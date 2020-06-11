package rrd.costcenter;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * createby jianyong.tang
 * createTime 2020/5/13 10:45
 * version v1
 * desc
 */
public class GetCostCenterCodeAction implements Action {
    @Override
    public String execute(RequestInfo info) {
        BaseBean log = new BaseBean();//记录日志表 /log/ecology
        log.writeLog("GetCostCenterCodeAction","start");
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        String tablename = info.getRequestManager().getBillTableName();
        RecordSet rs = new RecordSet();
        String cbzxlb = "";//成本中心类型
        String cbzxdm = "";
        String sql = "select cbzxlb,cbzxdm from "+tablename+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            cbzxlb = Util.null2String(rs.getString("cbzxlb"));
            cbzxdm = Util.null2String(rs.getString("cbzxdm"));
        }
        if(!"".equals(cbzxdm)){
            return SUCCESS;
        }
        String code = getCenterCode(cbzxlb);
        log.writeLog("GetCostCenterCodeAction","code:"+code);
        sql = "update "+tablename+" set cbzxdm='"+code+"' where requestid="+requestid;
        boolean result = rs.execute(sql);
        if(!result) {//逻辑错误提示
            info.getRequestManager().setMessageid("ERROE:");
            info.getRequestManager().setMessagecontent("流程提交不了，请联系管理员");
            return FAILURE_AND_CONTINUE;
        }

        return SUCCESS;
    }

    public String getCenterCode(String cbzxlb){
       // RecordSetDataSource rsd = new RecordSetDataSource("RRD_Test_AP");
        RecordSet rs = new RecordSet();
        String code = "";
        String head = "";
        int  nextseqno = 0;
        int lsws = 0;
        String sql = "select head,seqno+1 as nextseqno,lsws from uf_costcenter_code where cbzxlx='"+cbzxlb+"'";
        rs.execute(sql);
        if(rs.next()){
            head = rs.getString("head");
            nextseqno = rs.getInt("nextseqno");
            lsws = rs.getInt("lsws");
        }
        code = head+getStrNum(nextseqno,lsws);
        //其他系统判断

        sql = "update uf_costcenter_code set seqno="+nextseqno+" where head='"+head+"'";
        rs.execute(sql);



        return code;
    }

    public String getStrNum(int num,int len){
        String buff = String.valueOf(num);
        int max = len - buff.length();
        for(int index = 0; index < max;index++){
            buff = "0" + buff;
        }
        return buff;
    }

}
