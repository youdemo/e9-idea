package akkqcl.workflow;

import org.h2.command.dml.Insert;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/3/31 15:37
 * version v1
 * desc 项目报备流程添加客户
 */
public class AddCustomer implements Action {
    @Override
    public String execute(RequestInfo info) {
        String workflowid = info.getWorkflowid();
        String requestid = info.getRequestid();
        TransUtil tu = new TransUtil();
        InsertUtil iu = new InsertUtil();
        String tableName = tu.getTableName(workflowid);
        RecordSet rs = new RecordSet();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nowdate = sf.format(new Date());
        SimpleDateFormat sf2 = new SimpleDateFormat("HH:mm:ss");
        String nowtime = sf2.format(new Date());
        String xmhm = "";//xmhm
        String xmmc = "";//项目名称
        String xmdz = "";//项目地址
        String khmc = "";//客户名称
        String xmzylxr = "";//项目主要联系人
        String szhy = "";//所属行业
        String xsqd1 = "";//销售渠道
        String yzgb = "";//业主国别
        String cgl = "";//成功率
        String yjxsje = "";//预计销售金额
        String yjddsj = "";//预计得单时间
        String jsfzr = "";//技术负责人
        String qxxrcpd = "";//其它信息（如产品，等）
        String xmjbfl = "";//项目级别分类：
        String creater = "";
        tu.writeLog("AddCustomer","start "+requestid);
        String sql = "select * from "+tableName+" where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            xmhm = Util.null2String(rs.getString("xmhm"));
            xmmc = Util.null2String(rs.getString("xmmc"));
            xmdz = Util.null2String(rs.getString("xmdz"));
            khmc = Util.null2String(rs.getString("khmc"));
            xmzylxr = Util.null2String(rs.getString("xmzylxr"));
            szhy = Util.null2String(rs.getString("szhy"));
            xsqd1 = Util.null2String(rs.getString("xsqd1"));
            yzgb = Util.null2String(rs.getString("yzgb"));
            if(!"".equals(yzgb)){
                yzgb = String.valueOf(Util.getIntValue(yzgb)+1);
            }
            cgl = Util.null2String(rs.getString("cgl"));
            yjxsje = Util.null2String(rs.getString("yjxsje"));
            yjddsj = Util.null2String(rs.getString("yjddsj"));
            jsfzr = Util.null2String(rs.getString("jsfzr"));
            qxxrcpd = Util.null2String(rs.getString("qxxrcpd"));//.replaceAll("<br>","\\\\n")
            xmjbfl = Util.null2String(rs.getString("xmjbfl"));
        }
        sql = "select creater from workflow_requestbase where requestid="+requestid;
        rs.execute(sql);
        if(rs.next()){
            creater = Util.null2String(rs.getString("creater"));
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("crmcode",xmhm);
        map.put("name",xmmc);
        map.put("address1",xmdz);
        map.put("khmc",khmc);
        map.put("sector",szhy);
        map.put("source",xsqd1);
        map.put("yzgb",yzgb);
        map.put("cgl",cgl);
        map.put("yjxsje",yjxsje);
        map.put("yjddsj",yjddsj);
        map.put("jsfzr",jsfzr);
        map.put("qxxrcpd",qxxrcpd);
        map.put("xmjbfl",xmjbfl);


        map.put("language","7");
        map.put("manager",creater);
        map.put("type","1");
        map.put("typebegin",nowdate);
        map.put("status","2");
        map.put("deleted","0");
        map.put("seclevel","2");
        map.put("createrid",creater);
        map.put("createdate",nowdate);
        map.put("createtime",nowtime);
        map.put("lastupdatedby",creater);
        map.put("lastupdateddate",nowdate);
        map.put("lastupdatedtime",nowtime);

        iu.insert(map,"CRM_CustomerInfo");

        String relateditemid = "";
        sql = "select id from CRM_CustomerInfo where crmcode = '" + xmhm + "'";
        rs.execute(sql);
        if(rs.next()){
            relateditemid = Util.null2String(rs.getString("id"));
        }
        if("".equals(relateditemid)){
            info.getRequestManager().setMessagecontent("创建客户失败，请联系系统管理员");
            info.getRequestManager().setMessageid("ErrorMessage");
            return FAILURE_AND_CONTINUE;
        }
        map = new HashMap<>();
        map.put("CUSTOMERID",relateditemid);
        map.put("fullname",xmzylxr);
        map.put("firstname",xmzylxr);
        map.put("createrid",creater);
        map.put("createdate",nowdate);
        map.put("createtime",nowtime);
        map.put("lastupdatedby",creater);
        map.put("lastupdateddate",nowdate);
        map.put("lastupdatedtime",nowtime);
        iu.insert(map,"crm_customercontacter");

        Map<String,String> maps = new HashMap<String, String>();
        maps.put("relateditemid", relateditemid);
        maps.put("sharetype", "1");
        maps.put("seclevel", "0");
        maps.put("rolelevel", "0");
        maps.put("sharelevel", "3");
        maps.put("userid", creater);
        maps.put("departmentid", "");
        maps.put("roleid", "");
        maps.put("foralluser", "");
        maps.put("crmid", "0");
        maps.put("isdefault", "1");
        maps.put("deptorcomid", "");
        maps.put("contents",creater);
        maps.put("subcompanyid", "");//分部
        maps.put("deleted", "0");
        maps.put("seclevelMax", "100");
        maps.put("jobtitleid", "0");
        maps.put("joblevel", "0");
        maps.put("scopeid", "0");
        iu.insert(maps, "crm_shareinfo");

        return SUCCESS;
    }
}
