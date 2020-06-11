package gvo.hr.portal.engine.glz;

import com.engine.common.util.ParamUtil;
import gvo.hr.portal.util.HrUtil;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.net.URLDecoder;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/5/12 17:52
 * version v1
 * desc 上岗证获取情况
 */
public class GetGLZSgzDataService {
    @GET
    @Path("/getsgzfbqk")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 上岗证分布情况
     */
    public String getSgzfbqk(@Context HttpServletRequest request, @Context HttpServletResponse response){
        RecordSet rs = new RecordSet();
        User user = HrmUserVarify.getUser(request, response);
        GetGLZSYDataImpl ggdi = new GetGLZSYDataImpl();
        HrUtil hrutil = new HrUtil();
        Map<String,String> personMap = hrutil.getPersonBaseinfo(user.getUID()+"");
        String workcode = personMap.get("workcode");
        String recordid = personMap.get("recordid");
        Map<String,Object> params = ParamUtil.request2Map(request);
        String orgcode = "";
        try {
            orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")), "UTF-8");
        }catch(Exception e){
            new BaseBean().writeLog(this.getClass().getName(),e);
        }
        String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
        String orgcodes = hrutil.getOrgcodes(workcode,recordid);
        String result = "";
        String online = "0";
        String offline = "0";
        String nothave = "0";//未取得
        String allnum = "0";
        String sql = "select count(a.id) as num from uf_hr_persondata a where (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status='有效' and a.sequence='生产' and a.currdeptcode in("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        //new BaseBean().writeLog("GetGLZSgzDataService allnum:"+sql);
        rs.execute(sql);
        if(rs.next()){
            allnum = Util.null2String(rs.getString("num"));
        }
        if(!"0".equals(allnum)){
            sql = "select round(count(b.id)/"+allnum+",4)*100 as num, 'On-line' as type from uf_hr_persondata a,uf_hr_personCert b where a.workcode=b.workcode and (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status='有效' and a.sequence='生产' and b.jobtype='On-line' and to_char(sysdate,'yyyy-MM-dd')<=nvl(b.jobendtime,'9999-12-31') and a.currdeptcode in("+orgcodes+") ";
            if(!"".equals(orgcode)){
                if("false".equals(sfbhxjzz)) {
                    sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
                }else{
                    sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

                }
            }
            sql += "union all ";
            sql += "select round(count(b.id)/"+allnum+",4)*100, 'Off-line' as type from uf_hr_persondata a,uf_hr_personCert b where a.workcode=b.workcode and (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status='有效' and a.sequence='生产' and b.jobtype='Off-line' and to_char(sysdate,'yyyy-MM-dd')<=nvl(b.jobendtime,'9999-12-31') and a.currdeptcode in("+orgcodes+") ";
            if(!"".equals(orgcode)){
                if("false".equals(sfbhxjzz)) {
                    sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
                }else{
                    sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

                }
            }
            //new BaseBean().writeLog("GetGLZSgzDataService other:"+sql);
            rs.execute(sql);
            while(rs.next()){
                String num = Util.null2String(rs.getString("num"));
                String type = Util.null2String(rs.getString("type"));
                if("On-line".equals(type)){
                    online = num;
                }else if("Off-line".equals(type)){
                    offline = num;
                }

            }
            sql = "select 100-"+online+"-"+offline+" as qt from dual";
            rs.execute(sql);
            if(rs.next()){
                nothave = Util.null2String(rs.getString("qt"));
            }
        }
        JSONObject jo = new JSONObject();
        try {
            jo.put("online", online);
            jo.put("offline", offline);
            jo.put("nothave", nothave);
        }catch (Exception e){
            new BaseBean().writeLog(this.getClass().getName(),e);
        }
        return jo.toString();
    }

    @GET
    @Path("/getsgzdjfb")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 上岗证等级分布情况
     */
    public String getSgzdjfb(@Context HttpServletRequest request, @Context HttpServletResponse response){
        RecordSet rs = new RecordSet();
        RecordSet rs_dt = new RecordSet();
        User user = HrmUserVarify.getUser(request, response);
        GetGLZSYDataImpl ggdi = new GetGLZSYDataImpl();
        HrUtil hrutil = new HrUtil();
        Map<String,String> personMap = hrutil.getPersonBaseinfo(user.getUID()+"");
        String workcode = personMap.get("workcode");
        String recordid = personMap.get("recordid");
        Map<String,Object> params = ParamUtil.request2Map(request);
        String orgcode = "";
        try {
            orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")), "UTF-8");
        }catch(Exception e){
            new BaseBean().writeLog(this.getClass().getName(),e);
        }
        String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
        String type = Util.null2String(params.get("type"));

        String orgcodes = hrutil.getOrgcodes(workcode,recordid);
        String result = "";
        JSONObject jo = new JSONObject();
        String sql = "";
        String sql_dt = "";
        String jobtype=""; //岗证类型
        String joblevel = "";//岗证级别
        String num = "";//岗证数量
        String allnum = "";
        int count = 0;
        if("online".equals(type)){
            jobtype= "On-line";
        }else if("offline".equals(type)){
            jobtype= "Off-line";
        }
        //获取总人数
        sql = "select count(1) as num from uf_hr_persondata a,uf_hr_personCert b where a.workcode=b.workcode and (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status='有效' and a.sequence='生产' and b.jobtype='"+jobtype+"' and to_char(sysdate,'yyyy-MM-dd')<=nvl(b.jobendtime,'9999-12-31') and a.currdeptcode in("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        rs.execute(sql);
        if(rs.next()){
            allnum = Util.null2String(rs.getString("num"));
        }
        //获取类别总数
        sql = "select count(1) as count from (select distinct b.joblevel from uf_hr_persondata a,uf_hr_personCert b where a.workcode=b.workcode and (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status='有效' and a.sequence='生产' and b.jobtype='"+jobtype+"' and to_char(sysdate,'yyyy-MM-dd')<=nvl(b.jobendtime,'9999-12-31') and a.currdeptcode in("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        sql +=") a";
        rs.execute(sql);
        if(rs.next()){
            count = rs.getInt("count");
        }
        sql = "select round(count(b.id)/"+allnum+",4)*100 as num, b.joblevel from uf_hr_persondata a,uf_hr_personCert b where a.workcode=b.workcode and (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status='有效' and a.sequence='生产' and b.jobtype='"+jobtype+"' and to_char(sysdate,'yyyy-MM-dd')<=nvl(b.jobendtime,'9999-12-31') and a.currdeptcode in("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        sql += " group by b.joblevel ";
        rs.execute(sql);
        int index =1;
        String numattr = "0";
        String flag = "-";
        while(rs.next()){

            joblevel = Util.null2String(rs.getString("joblevel"));
            num = Util.null2String(rs.getString("num"));
            if(index<count) {
                numattr = numattr + flag + num;
            }else{
                sql_dt = "select 100-"+numattr+" as other from dual";
                rs_dt.execute(sql_dt);
                if(rs_dt.next()){
                    num = Util.null2String(rs_dt.getString("other"));
                }

            }
            try {
                jo.put(joblevel, num);
            }catch (Exception e){
                new BaseBean().writeLog(this.getClass().getName(),e);
            }
            index++;
        }


        return jo.toString();
    }

    @GET
    @Path("/getjnhqqkfb")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 技能获取情况分布
     */
    public String getJnhqqkfb(@Context HttpServletRequest request, @Context HttpServletResponse response){
        RecordSet rs = new RecordSet();
        User user = HrmUserVarify.getUser(request, response);
        GetGLZSYDataImpl ggdi = new GetGLZSYDataImpl();
        HrUtil hrutil = new HrUtil();
        Map<String,String> personMap = hrutil.getPersonBaseinfo(user.getUID()+"");
        String workcode = personMap.get("workcode");
        String recordid = personMap.get("recordid");
        Map<String,Object> params = ParamUtil.request2Map(request);
        String orgcode = "";
        try {
            orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")), "UTF-8");
        }catch(Exception e){
            new BaseBean().writeLog(this.getClass().getName(),e);
        }
        String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
        String orgcodes = hrutil.getOrgcodes(workcode,recordid);
        String result = "";
        String one = "";
        String two = "";
        String zero = "";//未取得
        String allnum = "";
        String sql = "select count(a.id) as num from uf_hr_persondata a where (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status='有效' and a.sequence='生产' and a.currdeptcode in("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        rs.execute(sql);
        if(rs.next()){
            allnum = Util.null2String(rs.getString("num"));
        }
        sql = "select round(count(b.id)/"+allnum+",4)*100 as num, 'one' as type from uf_hr_persondata a,uf_hr_personCert b where a.workcode=b.workcode and (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status='有效' and a.sequence='生产' and ((to_char(sysdate,'yyyy-MM-dd')<=nvl(skillendtime1,'2020-01-01') and to_char(sysdate,'yyyy-MM-dd')>nvl(skillendtime2,'2020-01-01')) or (to_char(sysdate,'yyyy-MM-dd')<=nvl(skillendtime2,'2020-01-01') and to_char(sysdate,'yyyy-MM-dd')>nvl(skillendtime1,'2020-01-01'))) and a.currdeptcode in("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        sql += "union all ";
        sql += "select round(count(b.id)/"+allnum+",4)*100 as num, 'two' as type from uf_hr_persondata a,uf_hr_personCert b where a.workcode=b.workcode and (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status='有效' and a.sequence='生产' and to_char(sysdate,'yyyy-MM-dd')<=nvl(skillendtime1,'2020-01-01') and to_char(sysdate,'yyyy-MM-dd')<=nvl(skillendtime2,'2020-01-01') and a.currdeptcode in("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

            }
        }

        rs.execute(sql);
        while(rs.next()){
            String num = Util.null2String(rs.getString("num"));
            String type = Util.null2String(rs.getString("type"));
            if("one".equals(type)){
                one = num;
            }else if("two".equals(type)){
                two = num;
            }

        }
        sql = "select 100-"+one+"-"+two+" as qt from dual";
        rs.execute(sql);
        if(rs.next()){
            zero = Util.null2String(rs.getString("qt"));
        }
        JSONObject jo = new JSONObject();
        try {
            jo.put("one", one);
            jo.put("two", two);
            jo.put("zero", zero);
        }catch (Exception e){
            new BaseBean().writeLog(this.getClass().getName(),e);
        }
        return jo.toString();
    }


}
