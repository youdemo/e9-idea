package com.api.gvo.hr.portal;

import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * createby jianyong.tang
 * createTime 2020/4/20 13:35
 * version v1
 * desc
 */
@Path("/gvo/hr/portal")
public class CheckLoginApi{
    @GET
    @Path("/checklogin")
    @Produces({MediaType.TEXT_PLAIN})
    public String weatableDemo(@Context HttpServletRequest request, @Context HttpServletResponse response){
        String result = "";
        User user = HrmUserVarify.getUser(request, response);
        if(user == null || user.getUID()<0){
            result = "{\"result\":\"fail\"}";
        }else{
            result = "{\"result\":\"success\"}";
        }

        return result;
    }
}
