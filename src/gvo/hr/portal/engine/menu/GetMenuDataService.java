package gvo.hr.portal.engine.menu;

import com.engine.common.util.ParamUtil;
import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/5/26 13:47
 * version v1
 * desc
 */
public class GetMenuDataService {
    @GET
    @Path("/getmenudata")
    @Produces({MediaType.TEXT_PLAIN})
    public String getSyShowData(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        RecordSet rs = new RecordSet();
        User user = HrmUserVarify.getUser(request, response);
        Map<String,Object> params = ParamUtil.request2Map(request);
        String modemenubs = Util.null2String(params.get("modemenubs"));
        String mkcd = "";//模块菜单
        String sql = "select id from uf_hr_modemenu where cdbs='"+modemenubs+"'";
        rs.execute(sql);
        if(rs.next()){
            mkcd = Util.null2String(rs.getString("id"));
        }
        String yjcds = "";
        String ejcds = "";
        String flag = "";
        String flag2 = "";
        //获取 所有的一级和二级菜单
        sql = "select * " +
                "  from uf_hr_menuauth_mt " +
                " where mkcd = "+mkcd+" " +
                "   and (',' || to_char(gxry) || ',' like '%,"+user.getUID()+",%' or " +
                "       id in " +
                "       (select b.id " +
                "           from hrmrolemembers a, " +
                "                (SELECT id, REGEXP_SUBSTR(gxjs, '[^,]+', 1, LEVEL, 'i') item " +
                    "                   FROM (select id,gxjs from uf_hr_menuauth_mt " +
                "                  where mkcd = "+mkcd+"） " +
                "                 CONNECT BY rownum <= regexp_count(gxjs, ',') + 1) b " +
                "          where a.roleid = b.item " +
                "            and a.resourceid = "+user.getUID()+") or " +
                "       ',' || to_char(gxbm) || ',' like '%,"+user.getUserDepartment()+",%' or syrkck='1')";
        rs.execute(sql);
        while(rs.next()){
            String  yjcd = Util.null2String(rs.getString("yjcd"));
            String  ejcd = Util.null2String(rs.getString("ejcd"));
            if((","+yjcds+",").indexOf(","+yjcd+",")<0 & !"".equals(yjcd)){
                yjcds = yjcds + flag + yjcd;
                flag = ",";
            }

            if((","+ejcds+",").indexOf(","+ejcd+",")<0 && !"".equals(ejcd)){
                ejcds = ejcds + flag2 + ejcd;
                flag2 = ",";
            }
        }

        JSONArray menu = new JSONArray();
        if("".equals(yjcds)){
            return menu.toString();
        }
        sql = "select id,cdmc,cddz from uf_hr_firestmenu where id in("+yjcds+") and sfqy='0' order by sx asc";
        rs.execute(sql);
        while(rs.next()){
            String yjcd = Util.null2String(rs.getString("id"));
            try {
                JSONObject jo = new JSONObject();
                jo.put("cdmc", Util.null2String(rs.getString("cdmc")));
                jo.put("cddz", Util.null2String(rs.getString("cddz")));
                jo.put("secondmenu",getSecondMenu(yjcd,ejcds));
                menu.put(jo);
            }catch (Exception e){
                new BaseBean().writeLog(this.getClass().getName(),e);
            }
        }


        return menu.toString();
    }

    /**
     * 获取二级菜单
     * @param yjcd
     * @param ejcds
     * @return
     */
    public JSONArray getSecondMenu(String yjcd,String ejcds) throws JSONException {
        JSONArray ja = new JSONArray();
        RecordSet rs = new RecordSet();
        String sql = "select * from uf_hr_secondmenu where yjcd="+yjcd+" and id in("+ejcds+") and sfqy='0' order by cdsx asc";
        rs.execute(sql);
        while(rs.next()){
            JSONObject jo = new JSONObject();
            jo.put("cdmc",Util.null2String(rs.getString("cdmc")));
            jo.put("cddz",Util.null2String(rs.getString("cddz")));
            ja.put(jo);
        }
        return  ja;
    }
}
