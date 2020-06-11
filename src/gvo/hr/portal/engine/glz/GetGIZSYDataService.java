package gvo.hr.portal.engine.glz;

import com.engine.common.util.ParamUtil;
import gvo.ScheduleTask.hr.portal.SaveGLZPortalDataAction;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/4/20 15:16
 * version v1
 * desc 获取管理者组织架构数据
 */
public class GetGIZSYDataService {
    @GET
    @Path("/getbasedata")
    @Produces({MediaType.TEXT_PLAIN})
    public String getSyShowData(@Context HttpServletRequest request, @Context HttpServletResponse response){
        RecordSet rs = new RecordSet();
        User user = HrmUserVarify.getUser(request, response);
        GetGLZSYDataImpl ggdi = new GetGLZSYDataImpl();
        HrUtil hrutil = new HrUtil();
        Map<String,String> personMap = hrutil.getPersonBaseinfo(user.getUID()+"");
        String workcode = personMap.get("workcode");
        String recordid = personMap.get("recordid");
        String result = "";
        String sql = "select syjson from uf_hr_mhdata where workcode='"+workcode+"' and recordid='"+recordid+"'";
        rs.execute(sql);
        if(rs.next()){
            result = Util.null2String(rs.getString("syjson"));
        }
        if("".equals(result)){
            SaveGLZPortalDataAction sd = new SaveGLZPortalDataAction();
            sd.insertOrUpdateData(workcode,recordid);
            rs.execute(sql);
            if(rs.next()){
                result = Util.null2String(rs.getString("syjson"));
            }
        }
        return result;
    }

    @POST
    @Path("/getjbqkhzdata")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 月度累计加班情况明细
     */
    public String getMkData(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        User user = HrmUserVarify.getUser(request, response);
        Map<String, Object> params = ParamUtil.request2Map(request);
        HrUtil hrUtil = new HrUtil();
        Map<String, String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode = personBaseinfo.get("workcode");
        String recordid = personBaseinfo.get("recordid");

        RecordSet rs = new RecordSet();
        String orgcodes = hrUtil.getOrgcodes(workcode, recordid);

        JSONObject jo = new JSONObject();
        String zrs = "";
        String sql = "select t.*, case when dzhjbsxzss>0 then round(dysqjbzss/dzhjbsxzss*100,2) else 0 end yjbzsszb, case when zrs>0 then round(dysqjbzss/zrs,2) else 0 end rjss " +
                " from (select nvl(sum(nvl(zrs,0)),0) as zrs,nvl(sum(nvl(bzjbsxzss,0)),0) as bzjbsxzss,nvl(sum(nvl(dzhjbsxzss,0)),0) as dzhjbsxzss,nvl(sum(nvl(dysqjbzss,0)),0) as dysqjbzss, " +
                "nvl(sum(nvl(leve1,0)),0) as leve1,nvl(sum(nvl(leve2,0)),0) as leve2,nvl(sum(nvl(leve3,0)),0) as leve3,nvl(sum(nvl(leve4,0)),0) as leve4,nvl(sum(nvl(leve5,0)),0) as leve5,nvl(sum(nvl(leve6,0)),0) as leve6 " +
                "from uf_hr_jbhz_data  where orgcode in ("+orgcodes+") ";

        sql += " ) t";
        rs.execute(sql);
        if(rs.next()){
            try {
                jo.put("yjbzsszb", Util.null2String(rs.getString("yjbzsszb")));//0-30
                jo.put("leve1", Util.null2String(rs.getString("leve1")));//0-30
                jo.put("leve2", Util.null2String(rs.getString("leve2")));//31-50
                jo.put("leve3", Util.null2String(rs.getString("leve3")));//51-60
                jo.put("leve4", Util.null2String(rs.getString("leve4")));//61-80
                jo.put("leve5", Util.null2String(rs.getString("leve5")));//81-110
                jo.put("leve6", Util.null2String(rs.getString("leve6")));//110+

            }catch (Exception e){
                new BaseBean().writeLog(this.getClass().getName(),e);
            }
        }
        return jo.toString();
    }

    @POST
    @Path("/getjqedbdata")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 月度累计加班情况明细
     */
    public String getJqedb(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        User user = HrmUserVarify.getUser(request, response);
        Map<String, Object> params = ParamUtil.request2Map(request);
        HrUtil hrUtil = new HrUtil();
        Map<String, String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode = personBaseinfo.get("workcode");
        String recordid = personBaseinfo.get("recordid");

        RecordSet rs = new RecordSet();
        String orgcodes = hrUtil.getOrgcodes(workcode, recordid);

        JSONObject jo = new JSONObject();
        String yxss = "";
        String zss = "";
        String edb = "";
        String sql ="select nvl(sum(nvl(yxss,0)),0) as yxss,nvl(sum(nvl(yxss,0)),0)+nvl(sum(nvl(syss,0)),0) as zss  from uf_hr_persondata a,uf_hr_leaveinfo b where a.workcode=b.workcode and (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status = '有效' and currdeptcode in ("+orgcodes+") ";
        rs.execute(sql);
        if(rs.next()){
            yxss = Util.null2String(rs.getString("yxss"));
            zss = Util.null2String(rs.getString("zss"));
        }
        if("0".equals(zss)){
            edb = "0";
        }else{
            sql = "select round(("+yxss+"/"+zss+"*100),2) as edb from dual";
            rs.execute(sql);
            if(rs.next()){
                edb = Util.null2String(rs.getString("edb"));
            }
        }
        return edb;
    }
}
