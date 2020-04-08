package gbb.workflow.action;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * createby jianyong.tang
 * createTime 2020/2/11 10:46
 * version v1
 * desc 通过会议回执 系统提醒工作流 删除会议成员
 */
public class UpdateMeetingMembers implements Action {
    @Override
    public String execute(RequestInfo info) {
        String requestid = info.getRequestid();
        RecordSet rs = new RecordSet();
        String creater = "";
        String hrmmembers = "";
        String meetingid = "";//会议id
        String isattend = "";
        String sql = "select creater from workflow_requestbase where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            creater = Util.null2String(rs.getString("creater"));
        }
        sql = "select relatmeeting from workflow_form where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            meetingid = Util.null2String(rs.getString("relatmeeting"));
        }
        sql = "select isattend from Meeting_Member2 where meetingid="+meetingid+" and memberid="+creater;
        rs.execute(sql);
        if(rs.next()){
            isattend = Util.null2String(rs.getString("isattend"));
        }
        if(!"".equals(meetingid) && "2".equals(isattend)) {
            sql = "select hrmmembers from meeting where id=" + meetingid;
            rs.execute(sql);
            if (rs.next()) {
                hrmmembers = Util.null2String(rs.getString("hrmmembers"));
            }
            hrmmembers = ("," + hrmmembers + ",").replace("," + creater + ",", ",");
            if (hrmmembers.length() > 1) {
                hrmmembers = hrmmembers.substring(1, hrmmembers.length() - 1);
            }
            sql = "update meeting set hrmmembers='" + hrmmembers + "',totalmember=totalmember-1 where id=" + meetingid;
            rs.execute(sql);
            sql = "delete from Meeting_ShareDetail where meetingid="+meetingid+" and userid="+creater;
            rs.execute(sql);
            sql = "delete from meeting_sign where meetingid="+meetingid+" and userid="+creater;
            rs.execute(sql);

            //处理日程
            String workid="";
            String resourceid = "";
            sql = " select id,resourceid from workplan where meetingid=" + meetingid;
            rs.execute(sql);
            if (rs.next()) {
                workid = Util.null2String(rs.getString("id"));
                resourceid = Util.null2String(rs.getString("resourceid"));
            }
            resourceid = ("," + resourceid + ",").replace("," + creater + ",", ",");
            if (resourceid.length() > 1) {
                resourceid = resourceid.substring(1, resourceid.length() - 1);
            }
            sql="update workplan set resourceid='"+resourceid+"' where meetingid="+meetingid;
            rs.execute(sql);
            sql="delete from WorkPlanShareDetail where workid="+workid+" and userid="+creater;
            rs.execute(sql);


        }
        return SUCCESS;
    }
}
