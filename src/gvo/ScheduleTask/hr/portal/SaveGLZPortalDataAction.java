package gvo.ScheduleTask.hr.portal;

import gvo.hr.portal.engine.glz.GetGLZOrgDataImpl;
import gvo.hr.portal.engine.glz.GetGLZSYDataImpl;
import gvo.hr.portal.engine.glz.psservice.GetPortalLzl;
import gvo.hr.portal.util.HrUtil;
import gvo.hr.portal.util.InsertUtil;
import org.json.JSONObject;
import weaver.conn.ConnStatement;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/4/28 15:18
 * version v1
 * desc
 */
public class SaveGLZPortalDataAction extends BaseCronJob {

    @Override
    public void execute() {
        BaseBean log = new BaseBean();
        log.writeLog("开始定时更新管理者门户数据");
        updateData();
        log.writeLog("定时更新管理者门户数据结束");
    }

    public void updateData(){
        RecordSet rs = new RecordSet();
        String workcode = "";
        String recordid = "";
        String sql = "select distinct workcode,recordid from (" +
                "select distinct fzrcode as workcode,fzrrcd as recordid from uf_hr_orgdata where status=0 and fzrcode is not null " +
                " union all " +
                "select distinct fgldcode as workcode,fgldrcd as recordid from uf_hr_orgdata where status=0 and fgldcode is not null " +
                " union all " +
                "select distinct b.workcode,'0' as recordid from uf_hr_bmfzr_mt a,hrmresource b where a.ry=b.id "+
                ") a";
        rs.execute(sql);
        while(rs.next()){
            workcode = Util.null2String(rs.getString("workcode"));
            recordid = Util.null2String(rs.getString("recordid"));
            insertOrUpdateData(workcode,recordid);
            insertOrUpdateOrgData(workcode,recordid);
        }
    }

    public void insertOrUpdateOrgData(String workcode,String recordid){
        InsertUtil iu = new InsertUtil();
        RecordSet rs = new RecordSet();
        String billid = "";
        String sql = "select id from uf_hr_mhdata where workcode='"+workcode+"' and recordid='"+recordid+"'";
        rs.execute(sql);
        if(rs.next()){
            billid = Util.null2String(rs.getString("id"));
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("workcode",workcode);
        map.put("recordid",recordid);
        if("".equals(billid)){
            iu.insert(map,"uf_hr_mhdata");
            rs.execute(sql);
            if(rs.next()){
                billid = Util.null2String(rs.getString("id"));
            }
        }

        GetGLZOrgDataImpl ggzoi = new GetGLZOrgDataImpl();
        String orgjson = ggzoi.getOrgdataJson(workcode,recordid);
        ConnStatement cn = null;
        try {
            cn = new ConnStatement();
            sql = "update uf_hr_mhdata set orgjson=? where  id='"+billid+"'";
            cn.setStatementSql(sql);
            StringReader reader = new StringReader(orgjson);
            cn.setCharacterStream(1, reader, orgjson.length());
            cn.executeUpdate();
            cn.close();
        } catch (Exception e) {
            cn.close();
        }


    }

    public void insertOrUpdateData(String workcode,String recordid){
        GetGLZSYDataImpl ggdi = new GetGLZSYDataImpl();
        GetPortalLzl gpl = new GetPortalLzl();
        HrUtil hrutil = new HrUtil();
        InsertUtil iu = new InsertUtil();
        RecordSet rs = new RecordSet();
        String billid = "";
        String sql = "select id from uf_hr_mhdata where workcode='"+workcode+"' and recordid='"+recordid+"'";
        rs.execute(sql);
        if(rs.next()){
            billid = Util.null2String(rs.getString("id"));
        }
        String orgcodes = hrutil.getOrgcodes(workcode,recordid);
        //new BaseBean().writeLog("testbbbb:"+orgcodes);
        //String orgcodeps = hrutil.getOrgcodesps(workcode,recordid);
        JSONObject jo = new JSONObject();
        try {
            jo.put("gender", new JSONObject(ggdi.getGenderDstribution(orgcodes)));
            jo.put("agefb", new JSONObject(ggdi.getNLFB(orgcodes)));
            jo.put("zdfb", new JSONObject(ggdi.getZDFB(orgcodes)));
            jo.put("xlfb", new JSONObject(ggdi.getXLFB(orgcodes)));
            jo.put("ryldsj", new JSONObject(ggdi.getRyldsj(orgcodes)));
            jo.put("cql", new JSONObject(gpl.getCql(workcode,recordid)));
            jo.put("lzl", new JSONObject(gpl.getLzl(workcode,recordid)));
        }catch(Exception e){
            new BaseBean().writeLog(e);
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("workcode",workcode);
        map.put("recordid",recordid);
        map.put("syjson",jo.toString());
        if("".equals(billid)){
            iu.insert(map,"uf_hr_mhdata");
        }else{
            iu.updateGen(map,"uf_hr_mhdata","id",billid);
        }
    }
}
