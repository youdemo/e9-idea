package APPDEV.HQ.FNA.TL;


import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 差旅申请Sao接口检测
 */
public class FNA_TL_AP_SapIntefaceCheck implements Action {
    public String checkType;
    @Override
    public String execute(RequestInfo info) {
       // TransUtil tu = new TransUtil();
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        RecordSet rs = new RecordSet();
        String tableName  = "";
        String sql = " Select tablename From Workflow_bill Where id in ( Select formid From workflow_base Where id= " + workflowid+")";
		rs.execute(sql);
		if (rs.next()) {
			tableName = Util.null2String(rs.getString("tablename"));
		}
       
        new BaseBean().writeLog("FNA_TL_AP_SapIntefaceCheck","start checkType:"+checkType);
        String EV_STATU = "";//
        String EV_MESSAGE = "";
        sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            EV_STATU = Util.null2String(rs.getString("EV_STATU"));
            EV_MESSAGE = Util.null2String(rs.getString("EV_MESSAGE"));
        }
        if(!"1".equals(EV_STATU)) {
            info.getRequestManager().setMessagecontent(EV_MESSAGE);
            info.getRequestManager().setMessageid("ErrorMessage");
            return FAILURE_AND_CONTINUE;
        }
//        if("0".equals(checkType)&&!"1".equals(EV_STATU)) {
//            info.getRequestManager().setMessagecontent("差旅申请合规检查失败 请联系系统管理员：" + EV_MESSAGE);
//            info.getRequestManager().setMessageid("ErrorMessage");
//            return FAILURE_AND_CONTINUE;
//        }else if("1".equals(checkType) && !"Y".equals(EV_STATU) ){
//            info.getRequestManager().setMessagecontent("差旅申请提交后回传SAP数据失败 请联系系统管理员：" + EV_MESSAGE);
//            info.getRequestManager().setMessageid("ErrorMessage");
//            return FAILURE_AND_CONTINUE;
//        }else if("2".equals(checkType)&&!"1".equals(EV_STATU) ){
//            info.getRequestManager().setMessagecontent("差旅申请审批结束更改自建表中状态失败 请联系系统管理员：" + EV_MESSAGE);
//            info.getRequestManager().setMessageid("ErrorMessage");
//            return FAILURE_AND_CONTINUE;
//        }


        return SUCCESS;
    }
}
