package APPDEV.HQ.ENGINE.FNA.TL.HOTELDETAIL.WEB;

import APPDEV.HQ.ENGINE.FNA.TL.HOTELDETAIL.SERVICE.HotelDetailDataServcie;
import APPDEV.HQ.ENGINE.FNA.TL.HOTELDETAIL.SERVICE.IMPL.HotelDetailDataServcieImpl;
import com.alibaba.fastjson.JSONObject;
import com.engine.common.util.ParamUtil;
import com.engine.common.util.ServiceUtil;
import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * 酒店穿透明细
 */
public class HotelDetailDataAction {

    private HotelDetailDataServcie getService(User user) {
        return (HotelDetailDataServcieImpl) ServiceUtil.getService(HotelDetailDataServcieImpl.class, user);
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
}
