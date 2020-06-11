package APPDEV.HQ.FNA.TL;

import APPDEV.HQ.FNA.UTIL.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * createby jianyong.tang
 * createTime 2020/3/2 16:46
 * version v1
 * desc 提交更新发票状态
 */
public class FNA_TL_RE_UpdateFpStatusForSubmit implements Action {
    @Override
    public String execute(RequestInfo info) {
        TransUtil tu = new TransUtil();
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        String mainId = "";
        String  sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            mainId = Util.null2String(rs.getString("id"));

        }
        String fphms = "";
        String flag = "";
        //发票使用校验 如果存在状态为已报销的发票则阻断提交并提示
        //交通明细
        sql = "select (select INVOICENUMBER from fnaInvoiceLedger where id=a.fp) as INVOICENUMBER from uf_jyb a where fp in(select Selinv from "+tableName+"_dt2 where mainid="+mainId+" and SELINV is not null ) and zt='1'";
        rs.execute(sql);
        while(rs.next()){
            String INVOICENUMBER = Util.null2String(rs.getString("INVOICENUMBER"));
            if(!"".equals(INVOICENUMBER)){
                fphms = fphms + flag + INVOICENUMBER;
                flag = ",";
            }
        }
        //其他明细
        sql = "select (select INVOICENUMBER from fnaInvoiceLedger where id=a.fp) as INVOICENUMBER from uf_jyb a where fp in(select selinv from "+tableName+"_dt3 where mainid="+mainId+" and selinv is not null ) and zt='1'";
        rs.execute(sql);
        while(rs.next()){
            String INVOICENUMBER = Util.null2String(rs.getString("INVOICENUMBER"));
            if(!"".equals(INVOICENUMBER)){
                fphms = fphms + flag + INVOICENUMBER;
                flag = ",";
            }
        }
        //住宿明细
        sql = "select (select INVOICENUMBER from fnaInvoiceLedger where id=a.fp) as INVOICENUMBER from uf_jyb a where fp in(select selinv from "+tableName+"_dt5 where mainid="+mainId+" and selinv is not null ) and zt='1'";
        rs.execute(sql);
        while(rs.next()){
            String INVOICENUMBER = Util.null2String(rs.getString("INVOICENUMBER"));
            if(!"".equals(INVOICENUMBER)){
                fphms = fphms + flag + INVOICENUMBER;
                flag = ",";
            }
        }
        if(!"".equals(fphms)){
            info.getRequestManager().setMessagecontent("发票号："+fphms+" 已被其他流程使用，请检查");
            info.getRequestManager().setMessageid("ErrorMessage");
            return FAILURE_AND_CONTINUE;
        }
        //交通
        rs.execute("update uf_jyb set zt='1' where fp in(select Selinv from "+tableName+"_dt2 where mainid="+mainId+" and SELINV is not null )  and fp<>'{fpid}'");
        //其他
        rs.execute("update uf_jyb set zt='1' where fp in(select selinv from "+tableName+"_dt3 where mainid="+mainId+" and selinv is not null )  and fp<>'{fpid}'");
        //住宿
        rs.execute("update uf_jyb set zt='1' where fp in(select selinv from "+tableName+"_dt5 where mainid="+mainId+" and selinv is not null )  and fp<>'{fpid}'");

        return SUCCESS;
    }
}
