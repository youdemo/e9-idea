package kstjj.doc.workflow;

import kstjj.doc.send.KunShanDTAdapter;
import kstjj.doc.send.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 统计分析发文流程 推送公文到政府oa
 */
public class SendDocForTJFXFW implements Action {
    @Override
    public String execute(RequestInfo info) {
        BaseBean log = new BaseBean();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TransUtil tu = new TransUtil();
        RecordSet rs = new RecordSet();
        String workflowID = info.getWorkflowid();
        String requestid = info.getRequestid();
        String tableName = tu.getTableName(workflowID);
        String title = "";//标题
        String code = "";//发文号
        String sendUnitCode = "";//发文部门内码
        String sendUnitName = "昆山市统计局";//发文部门名称
        String fwsj = sdf.format(new Date());//发文时间
        String swbm = "";//收文部门
        String swbmmc = "";//收文部门名称
        String gwlbxx = "";//公文类别信息
        String gwfl = "";//公文分类
        String zw = "";//正文
        String fj = "";//附件
        String wsfboa = "";//是否oa内网

        String fsfw = "";//主送单位
        String password = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "password"));
        String username = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "username"));
        log.writeLog("SendDocForTJFXFW","开始推送公文 requestid："+requestid);
        String sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            title = Util.null2String(rs.getString("bt"));
            code = Util.null2String(rs.getString("qh"));
            wsfboa = Util.null2String(rs.getString("wsfboa"));
            zw = Util.null2String(rs.getString("zw"));
            fj = Util.null2String(rs.getString("fj"));
            fsfw = Util.null2String(rs.getString("fsdw"));
            gwfl = Util.null2String(rs.getString("gwfl"));
        }
        if(code.indexOf("第")<0){
            code = "第"+code+"期";
        }
        sql="select guid from uf_inforesrdepet where bmmc='"+sendUnitName+"'";
        rs.execute(sql);
        if(rs.next()){
            sendUnitCode = Util.null2String(rs.getString("guid"));
        }
        String flag = "";
        if(!"".equals(fsfw)){
            sql = "select guid,bmmc from uf_inforesrdepet where bmmc in(select dwmc from uf_oabill where id in("+getFwdw(fsfw)+"))";
            rs.execute(sql);
            while(rs.next()){
                swbm = swbm + flag + Util.null2String(rs.getString("guid"));
                swbmmc = swbmmc + flag + Util.null2String(rs.getString("bmmc"));
                flag = ";";
            }
        }

        if("1".equals(wsfboa)){
            Map<String,String> map = new HashMap<String,String>();
            map.put("title",title);
            map.put("code",code);
            map.put("sendUnitCode",sendUnitCode);
            map.put("sendUnitName",sendUnitName);
            map.put("fwsj",fwsj);
            map.put("swbm",swbm);
            map.put("swbmmc",swbmmc);
            map.put("gwlbxx",gwlbxx);
            map.put("gwfl",gwfl);
            map.put("zw",zw);
            map.put("fj",fj);
            try {
                KunShanDTAdapter ksda = new KunShanDTAdapter();
                Map<String,String>  resultmap = ksda.SendDocumentOne(map,username,password);
                String status = resultmap.get("status");
                String desc = resultmap.get("desc");
                if(desc.length()>300){
                    desc = desc.substring(0,300);
                }
                String fRecordId = resultmap.get("fRecordId");
                sql = "update "+tableName+" set status='"+status+"',desc1='"+desc+"',frecordid='"+fRecordId+"' where requestid="+requestid;
                rs.execute(sql);
                if(!"Success".equals(status)){
                    info.getRequestManager().setMessagecontent("推送公文失败，请联系系统管理员");
                    info.getRequestManager().setMessageid("ErrorMsg:");
                    return FAILURE_AND_CONTINUE;

                }
            } catch (NoSuchAlgorithmException e) {
                log.writeLog("SendDocForTJFXFW",e);
                info.getRequestManager().setMessagecontent("推送公文失败，请联系系统管理员");
                info.getRequestManager().setMessageid("ErrorMsg:");
                return FAILURE_AND_CONTINUE;

            }

        }
        return SUCCESS;
    }

    public String getFwdw(String dwids){
        String result = "";
        String flag = "";
        String arr[]=dwids.split(",");
        for(String aid:arr){
            result=result+flag+aid.substring(aid.lastIndexOf("_")+1);
            flag = ",";
        }
        return result;
    }
}
