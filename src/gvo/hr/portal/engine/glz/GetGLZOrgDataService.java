package gvo.hr.portal.engine.glz;

import com.engine.common.util.ParamUtil;
import gvo.ScheduleTask.hr.portal.SaveGLZPortalDataAction;
import gvo.hr.portal.util.HrUtil;
import oracle.sql.CLOB;
import org.json.JSONArray;
import org.json.JSONObject;
import weaver.conn.ConnStatement;
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
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/4/23 16:10
 * version v1
 * desc  获取组织架构数据
 */
public class GetGLZOrgDataService {
    @GET
    @Path("/getOrgdata")
    @Produces({MediaType.TEXT_PLAIN})
    public String getOrgdata(@Context HttpServletRequest request, @Context HttpServletResponse response){
        RecordSet rs = new RecordSet();
        String result = "";
        HrUtil hrutil = new HrUtil();
        User user = HrmUserVarify.getUser(request, response);
        GetGLZOrgDataImpl ggzoi = new GetGLZOrgDataImpl();
        Map<String,Object> params = ParamUtil.request2Map(request);
        String workcode = Util.null2String(params.get("workcode"));
        String recordid = Util.null2String(params.get("recordid"));
        if("".equals(workcode)){
            Map<String,String> personMap = hrutil.getPersonBaseinfo(user.getUID()+"");
            workcode = personMap.get("workcode");
            recordid = personMap.get("recordid");
        }
        ConnStatement cn = null;
        String sql = "select orgjson from uf_hr_mhdata where workcode='"+workcode+"' and recordid='"+recordid+"'";
        try {
            CLOB cb = null;
            cn = new ConnStatement();
            cn.setStatementSql(sql);
            cn.executeQuery();
            if(cn.next()){
                cb = cn.getClob("orgjson");
            }
            result = cb.getSubString(1, (int) cb.length());
            cn.close();
        } catch (Exception e) {
            cn.close();
        }
        if("".equals(result)){
            SaveGLZPortalDataAction sd = new SaveGLZPortalDataAction();
            sd.insertOrUpdateOrgData(workcode,recordid);
            try {
                CLOB cb = null;
                cn = new ConnStatement();
                cn.setStatementSql(sql);
                cn.executeQuery();
                if(cn.next()){
                    cb = cn.getClob("orgjson");
                }
                result = cb.getSubString(1, (int) cb.length());
                cn.close();
            } catch (Exception e) {
                cn.close();
            }
        }
        return result;
    }

    @GET
    @Path("/getorgsy")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 获取水印信息
     */
    public String getOrgSy(@Context HttpServletRequest request, @Context HttpServletResponse response){
        RecordSet rs = new RecordSet();
        String result = "";
        User user = HrmUserVarify.getUser(request, response);
        String sql = "select lastname||' '||workcode||' '||to_char(sysdate,'yyyy-MM-dd') as sy from hrmresource  where id="+user.getUID();
        rs.execute(sql);
        if(rs.next()){
            result = Util.null2String(rs.getString("sy"));
        }
        return result;
    }

    @GET
    @Path("/getcansee")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 判断能否点击查看团队人员信息
     */
    public String getCanseeTdry(@Context HttpServletRequest request, @Context HttpServletResponse response){
        BaseBean log  = new BaseBean();
        RecordSet rs = new RecordSet();
        String result = "";
        User user = HrmUserVarify.getUser(request, response);
        HrUtil hrutil=new HrUtil();
        Map<String,Object> params = ParamUtil.request2Map(request);
        String orgcode = Util.null2String(params.get("orgcode"));
        String workcode = Util.null2String(params.get("workcode"));
        String recordid = Util.null2String(params.get("recordid"));
        if("".equals(workcode)){
            Map<String,String> personMap = hrutil.getPersonBaseinfo(user.getUID()+"");
            workcode = personMap.get("workcode");
            recordid = personMap.get("recordid");
        }

        String orgcodes = hrutil.getOrgcodes(workcode,recordid);
        String currdeptdode = hrutil.getCurrentdeptcode(workcode,recordid);
        if(orgcodes.indexOf("'"+orgcode+"'")>=0){
            result = "1";
        }else{
//            if(currdeptdode.equals(orgcode)){
//                result = "1";
//            }else{
//                result = "0";
//            }
            result = "0";
        }

        return result;
    }

    @GET
    @Path("/getzhxx")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 获取账号信息
     */
    public String getzhxx(@Context HttpServletRequest request, @Context HttpServletResponse response){
        BaseBean log  = new BaseBean();

        RecordSet rs = new RecordSet();
        User user = HrmUserVarify.getUser(request, response);
        HrUtil hrutil=new HrUtil();
        Map<String,Object> params = ParamUtil.request2Map(request);
        Map<String,String> personMap = hrutil.getPersonBaseinfo(user.getUID()+"");
        String workcode = personMap.get("workcode");
        String recordid = personMap.get("recordid");
        JSONArray ja = new JSONArray();
        if(!"0".equals(recordid)){
            return ja.toString();
        }
        int count = 0;
        String sql = "select count(1) as count from uf_hr_persondata_dt1 where workcode='" + workcode + "' and status='有效' order by id asc";
        rs.execute(sql);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count == 0){
            return ja.toString();
        }
        try {
            sql = "select workcode,recordid,jobtitlename from uf_hr_persondata where workcode='" + workcode + "' and status='有效'";
            rs.execute(sql);
            if (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("workcode", Util.null2String(rs.getString("workcode")));
                jo.put("recordid", Util.null2String(rs.getString("recordid")));
                jo.put("jobtitlename", Util.null2String(rs.getString("jobtitlename")));
                ja.put(jo);
            }
            sql = "select workcode,recordid,jobtitlename from uf_hr_persondata_dt1 where workcode='" + workcode + "' and status='有效' order by id asc";
            rs.execute(sql);
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                jo.put("workcode", Util.null2String(rs.getString("workcode")));
                jo.put("recordid", Util.null2String(rs.getString("recordid")));
                jo.put("jobtitlename", Util.null2String(rs.getString("jobtitlename")));
                ja.put(jo);
            }
        }catch (Exception e){
            log.writeLog("GetGLZOrgDataService",e);
        }
        return ja.toString();
    }


}
