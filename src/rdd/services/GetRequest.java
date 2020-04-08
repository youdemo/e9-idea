package rdd.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

/**
	@author:glt
	@date:2019年10月21日 上午9:58:23
	@function: 获取流程待办接口
*/

public class GetRequest {
	
	public String  doservice(String loginid,String workflowidV){
		BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		JSONArray ArchivesValue = new JSONArray();
		
		String userid = "";
		String sql = "select id from hrmresource where loginid = '"+loginid+"'";
		rs.execute(sql);
		if(rs.next()) {
			userid = Util.null2String(rs.getString("id"));
		}
		sql = " select a.requestid, a.workflowid,a.requestname,(select loginid from hrmresource where id=a.creater) as creater,CONCAT_WS(' ',a.createdate,a.createtime)  as  createdate "
				+" from workflow_requestbase a,workflow_currentoperator b where a.requestid = b.requestid and b.isremark not in(2,4) and b.islasttimes=1 " 
				+" and b.userid ='"+userid+"' and a.workflowid in ("+workflowidV+") order by a.requestid desc";
		log.writeLog("testaaa:"+sql);
		rs.execute(sql);
		while(rs.next()) {
			JSONObject json = new JSONObject();
			
			String requestid  = Util.null2String(rs.getString("requestid"));//流程编号
			String workflowid  = Util.null2String(rs.getString("workflowid"));//工作id
			String requestname  = Util.null2String(rs.getString("requestname"));//流程名字
			String createdate  = Util.null2String(rs.getString("createdate"));//创建时间
			String creater  = Util.null2String(rs.getString("creater"));//创建人
			
			try {
				
				json.put("requestid", requestid);
				json.put("workflowid", workflowid);
				json.put("requestname", requestname);
				json.put("createdate", createdate);
				json.put("creater", creater);

				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ArchivesValue.put(json);
		}	
		return ArchivesValue.toString();
	}	

}
