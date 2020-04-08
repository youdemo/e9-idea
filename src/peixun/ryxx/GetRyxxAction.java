package peixun.ryxx;

import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * createby jianyong.tang
 * createTime 2020/3/30 10:21
 * version v1
 * desc
 */
public class GetRyxxAction {
    @GET
    @Path("/getlastname")
    @Produces({MediaType.TEXT_PLAIN})
    public String getLastname(@Context HttpServletRequest request, @Context HttpServletResponse response){
        String lastname = "";
        String workcode = Util.null2String(request.getParameter("workcode"));
        JSONObject jo = new JSONObject();
        RecordSet rs = new RecordSet();
        String sql = "";
        try{
            if("".equals(workcode)){
                jo.put("status","fail");
                jo.put("result","");
                jo.put("msg","请传入参数工号");
            }else{
                sql = "select lastname from hrmresource where workcode='"+workcode+"'";
                rs.execute(sql);
                if(rs.next()){
                    lastname = Util.null2String(rs.getString("lastname"));
                }
                if("".equals(lastname)){
                    jo.put("status","fail");
                    jo.put("result","");
                    jo.put("msg","改工号不存在");
                }else{
                    jo.put("status","success");
                    jo.put("result",lastname);
                    jo.put("msg","成功");
                }

            }
        }catch(Exception e){

        }
        return jo.toString();
    }

    @POST
    @Path("/getlastname2")
    @Produces({MediaType.TEXT_PLAIN})
    public String getLastname2(@Context HttpServletRequest request, @Context HttpServletResponse response){
        String lastname = "";
        String workcode = Util.null2String(request.getParameter("workcode"));
        JSONObject jo = new JSONObject();
        RecordSet rs = new RecordSet();
        String sql = "";
        try{
            if("".equals(workcode)){
                jo.put("status","fail");
                jo.put("result","");
                jo.put("msg","请传入参数工号");
            }else{
                sql = "select lastname from hrmresource where workcode='"+workcode+"'";
                rs.execute(sql);
                if(rs.next()){
                    lastname = Util.null2String(rs.getString("lastname"));
                }
                if("".equals(lastname)){
                    jo.put("status","fail");
                    jo.put("result","");
                    jo.put("msg","改工号不存在");
                }else{
                    jo.put("status","success");
                    jo.put("result",lastname);
                    jo.put("msg","成功");
                }

            }
        }catch(Exception e){

        }
        return jo.toString();
    }
}
