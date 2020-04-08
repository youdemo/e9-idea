package rdd.services;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONArray;
import org.json.JSONObject;

import rdd.services.GetRequestStub.Doservice;
import rdd.services.GetRequestStub.DoserviceResponse;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;

/**
	@author:glt
	@date:2019年10月22日 下午1:27:49
	@function:获取流程待办数据
*/
public class GetRequestClient {

	public List getRequestDetail(User user, Map map, HttpServletRequest httpservletrequest,
			HttpServletResponse httpservletresponse) throws Exception {
		RecordSet rs = new RecordSet();
		BaseBean log = new BaseBean();
		ArrayList<Map> arraylist = new ArrayList<Map>();
		String loginid = Util.null2String(map.get("loginid"));
		log.writeLog("testaaa:"+loginid);
		GetRequestStub tws = new GetRequestStub();
		Doservice ds =new GetRequestStub.Doservice();
		ds.setLoginid(loginid);
		ds.setWorkflowidV("45,46");
		
		DoserviceResponse dr = tws.doservice(ds);
		
		String result = dr.getOut().toString();
		JSONArray jsonArray = new JSONArray(result);
		for(int i=0;i<jsonArray.length();i++){
			HashMap<String,Object> datamap = new HashMap<String,Object>();
			JSONObject job = jsonArray.getJSONObject(i);   
			String creater = "";
			String sql = "select id from hrmresource where loginid='"+job.get("creater")+"'";
			rs.execute(sql);
			if(rs.next()) {
				creater = Util.null2String(rs.getString("id"));
			}
			datamap.put("requestname",job.get("requestname"));
			datamap.put("workflowid",job.get("workflowid"));
			datamap.put("requestid",job.get("requestid"));
			datamap.put("createdate",job.get("createdate"));
			datamap.put("creater",creater);
			
			String url= "/rrd/sendKsbbUrl.jsp?requestid="+job.get("requestid");
			String showname = "<a href=\""+url+"\" target=\"_blank\">"+job.get("requestname")+"</a>";
			
			datamap.put("showname",showname);
			
			arraylist.add(datamap);
		}
		return arraylist;
	}
}
