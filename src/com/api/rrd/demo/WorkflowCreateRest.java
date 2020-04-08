package com.api.rrd.demo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import rrd.demo.util.TransUtil;
import rrd.demo.workflow.CreateRequestService;
import weaver.general.Util;

@Path("/rrd/demo")
public class WorkflowCreateRest {
	@POST
	@Path("/qjsq")
	@Produces(MediaType.APPLICATION_JSON)
	public String createQjsq(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		CreateRequestService crs = new CreateRequestService();
		String result = "";
		try {
			new TransUtil().writeLog("json:"+request.getParameter("json"));
			result = crs.createQjsq(Util.null2String(request.getParameter("json")));
			new TransUtil().writeLog(result);
		} catch (Exception e) {
			new TransUtil().writeLog(e);
			 Map retMap = new HashMap<String,String>();
			 retMap.put("MSG_TYPE", "E");
	         retMap.put("MSG_CONTENT", "Json格式错误！");
	         retMap.put("OA_ID", "0");
	         return getJsonStr(retMap);
		}
		return result;		
	}
	
	private String getJsonStr(Map map) {
        JSONObject json = new JSONObject();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = (String) map.get(key);
            try {
                json.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return json.toString();
    }
}
