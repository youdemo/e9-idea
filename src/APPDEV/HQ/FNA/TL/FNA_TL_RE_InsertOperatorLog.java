package APPDEV.HQ.FNA.TL;

import APPDEV.HQ.FNA.UTIL.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 更新酒店机票预订类型
 */
public class FNA_TL_RE_InsertOperatorLog implements Action {
    @Override
    public String execute(RequestInfo info) {
        TransUtil tu = new TransUtil();
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        RecordSet rs_dt = new RecordSet();
        String mainId = "";
        String submittype = info.getRequestManager().getSrc();
        String submitnodeid = String.valueOf(info.getRequestManager().getNodeid());
        String nowremark = info.getRequestManager().getRemark();//当前用户提交时的签字意见
        String tjr = info.getRequestManager().getUser().getLastname();
        String nowlogtype="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String nowDate = sdf.format(new Date());
        String nowTime = sdf1.format(new Date());
        String  sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            mainId = Util.null2String(rs.getString("id"));

        }

        rs.execute("delete from "+tableName+"_dt9 where mainid="+mainId);
        //判断是否是创建节点
        String isstart = "";
        sql = "select isstart from workflow_nodebase where id="+submitnodeid;
        rs.execute(sql);
        if(rs.next()){
            isstart = Util.null2String(rs.getString("isstart"));
        }
        if("reject".equals(submittype)){
            nowlogtype="退回";
        }else{
            if("1".equals(isstart)){
                nowlogtype="提交";
            }else{
                nowlogtype="审批";
            }
        }
        nowremark = removeHtmlTag(nowremark);
        sql = "select (select LASTNAME from hrmresource where id=a.OPERATOR) as operator ,OPERATEDATE,OPERATETIME,remark,logtype from workflow_requestlog a where requestid="+requestid+" and LOGTYPE in('0','2','3','a','b','h','j')   order by  logid asc";
        rs.execute(sql);
        while(rs.next()){
            String operator = Util.null2String(rs.getString("operator"));
            String OPERATEDATE = Util.null2String(rs.getString("OPERATEDATE"));
            String OPERATETIME = Util.null2String(rs.getString("OPERATETIME"));
            String remark = removeHtmlTag(Util.null2String(rs.getString("remark")));
            String logtype = getState(Util.null2String(rs.getString("logtype")),7);
            rs_dt.execute("insert into "+tableName+"_dt9(mainid,operator,opdate,optime,optype,appcom) values("+mainId+",'"+operator+"','"+OPERATEDATE+"','"+OPERATETIME+"','"+logtype+"','"+remark+"')");

        }
        rs.execute("insert into "+tableName+"_dt9(mainid,operator,opdate,optime,optype,appcom) values("+mainId+",'"+tjr+"','"+nowDate+"','"+nowTime+"','"+nowlogtype+"','"+nowremark+"')");


        return SUCCESS;
    }
    /**
     * 签字意见html格式化
     * @param content 签字意见remark字段文本
     * @return
     */
    private String removeHtmlTag(String content) {
        Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>");
        Matcher m = p.matcher(content);
        if (m.find()) {
            content = content
                    .replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
            content = removeHtmlTag(content);
        }
        return content.replaceAll("<br/>"," ").replaceAll("<br>"," ").replaceAll("</br>"," ").replaceAll("<BR/>"," ").replaceAll("<BR>"," ").replaceAll("</BR>"," ").replaceAll("&NBSP;"," ").replaceAll("&nbsp;"," ").replace("'","''").replace("\\n"," ").replace("\\r"," ");
    }
    /**
     * 获取流程操作类型
     * @param state
     * @param saplan
     * @return
     */
    private String getState(String state,int saplan) {
        String statename = "";
        if(saplan==8){
            if ("0".equals(state)) {
                statename = "Approval";
            } else if ("2".equals(state)) {
                statename = "Submit";
            } else if ("3".equals(state)) {
                statename = "Return";
            } else if ("4".equals(state)) {
                statename = "Reopen";
            } else if ("5".equals(state)) {
                statename = "Delete";
            } else if ("6".equals(state)) {
                statename = "Activation";
            } else if ("7".equals(state)) {
                statename = "Retransmission";
            } else if ("9".equals(state)) {
                statename = "Comment";
            } else if ("a".equals(state)) {
                statename = "Opinion inquiry";
            } else if ("b".equals(state)) {
                statename = "Opinion inquiry reply";
            } else if ("e".equals(state)) {
                statename = "filing";
            } else if ("h".equals(state)) {
                statename = "Transfer";
            } else if ("i".equals(state)) {
                statename = "intervene";
            } else if ("j".equals(state)) {
                statename = "Transfer feedback";
            } else if ("t".equals(state)) {
                statename = "CC";
            } else if ("s".equals(state)) {
                statename = "Supervise";
            } else if ("i".equals(state)) {
                statename = "Process intervention";
            } else if ("1".equals(state)) {
                statename = "Save";
            } else if ("new".equals(state)) {
                statename = "Create";
            }else {
                statename = "";
            }
        }
        else{
            if ("0".equals(state)) {
                statename = "批准";
            } else if ("2".equals(state)) {
                statename = "提交";
            } else if ("3".equals(state)) {
                statename = "退回";
            } else if ("4".equals(state)) {
                statename = "重新打开";
            } else if ("5".equals(state)) {
                statename = "删除";
            } else if ("6".equals(state)) {
                statename = "激活";
            } else if ("7".equals(state)) {
                statename = "转发";
            } else if ("9".equals(state)) {
                statename = "批注";
            } else if ("a".equals(state)) {
                statename = "意见征询";
            } else if ("b".equals(state)) {
                statename = "意见征询回复";
            } else if ("e".equals(state)) {
                statename = "归档";
            } else if ("h".equals(state)) {
                statename = "转办";
            } else if ("i".equals(state)) {
                statename = "干预";
            } else if ("j".equals(state)) {
                statename = "转办反馈";
            } else if ("t".equals(state)) {
                statename = "抄送";
            } else if ("s".equals(state)) {
                statename = "督办";
            } else if ("i".equals(state)) {
                statename = "流程干预";
            } else if ("1".equals(state)) {
                statename = "保存";
            }else if ("new".equals(state)) {
                statename = "创建";
            } else {
                statename = "";
            }
        }
        return statename;
    }
}
