package APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.WEB;

import APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.SERVICE.FlightOrderDataServcie;
import APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.SERVICE.IMPL.FlightOrderDataServcieImpl;
import com.alibaba.fastjson.JSONObject;
import com.engine.common.util.ParamUtil;
import com.engine.common.util.ServiceUtil;
import org.apache.commons.lang.StringUtils;
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
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/*
 * 机票对账列表
 **/
public class FlightOrderDataAction {

    private FlightOrderDataServcie getService(User user) {
        return (FlightOrderDataServcieImpl) ServiceUtil.getService(FlightOrderDataServcieImpl.class, user);
    }

    /**
     * 获取weatable的值
     * @param request
     * @param response
     * @return
     */
    @GET
    @Path("/weatableDemo")
    @Produces({MediaType.TEXT_PLAIN})
    public String weatableDemo(@Context HttpServletRequest request, @Context HttpServletResponse response){
        Map<String, Object> apidatas = new HashMap<String, Object>();
        try {
            //获取当前用户
            User user = HrmUserVarify.getUser(request, response);
            apidatas.putAll(getService(user).weatableDemo(ParamUtil.request2Map(request)));
            apidatas.put("api_status", true);
        } catch (Exception e) {
            e.printStackTrace();
            apidatas.put("api_status", false);
            apidatas.put("api_errormsg", "catch exception : " + e.getMessage());
        }
        return JSONObject.toJSONString(apidatas);
    }


    /**
     * 获取高级搜索条件
     * @param request
     * @param response
     * @return
     */
    @GET
    @Path("/weatableConditonDemo")
    @Produces({MediaType.TEXT_PLAIN})
    public String weatableConditonDemo(@Context HttpServletRequest request, @Context HttpServletResponse response){
        Map<String, Object> apidatas = new HashMap<String, Object>();
        try {
            //获取当前用户
            User user = HrmUserVarify.getUser(request, response);
            apidatas.putAll(getService(user).weatableConditonDemo(ParamUtil.request2Map(request)));
            apidatas.put("api_status", true);
        } catch (Exception e) {
            e.printStackTrace();
            apidatas.put("api_status", false);
            apidatas.put("api_errormsg", "catch exception : " + e.getMessage());
        }
        return JSONObject.toJSONString(apidatas);
    }

    //导出报表
    @GET
    @Path("/weaReportOutExcel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response weaReportOutExcel(@Context HttpServletRequest request, @Context HttpServletResponse response){
        try {
            User user = HrmUserVarify.getUser(request, response);
            InputStream input = getService(user).WeaReportOutExcel(request,response);
            String filename="携程机票对账表";
            filename = java.net.URLEncoder.encode(filename, "UTF-8");
            filename = StringUtils.replace(filename, "/", "");
            filename = StringUtils.replace(filename, "%2F", "");
            filename = StringUtils.replace(filename, "+", "%20");
            filename = Util.formatMultiLang(filename, user.getLanguage()+"") + ".xls";

            return Response
                    .ok(input)
                    .header("Content-disposition", "attachment;filename=" + filename)
                    .header("Cache-Control", "no-cache").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
