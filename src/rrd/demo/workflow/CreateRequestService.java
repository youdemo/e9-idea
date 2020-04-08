package rrd.demo.workflow;

import org.json.JSONException;
import org.json.JSONObject;

import rrd.demo.util.AutoRequestService;
import rrd.demo.util.TransUtil;

public class CreateRequestService {
	/**
	 * 创建请假申请流程
	 * @param json 
	 * @return
	 * @throws Exception 
	 */
	public String createQjsq(String json) throws Exception {
		String result = "";
		String workflowid = "46";
		TransUtil tu = new TransUtil();
		JSONObject jo = new JSONObject(json);
		JSONObject jsonOb = new JSONObject(); // 流程信息
		JSONObject header = new JSONObject(); // 主表信息
		JSONObject detail = new JSONObject();
		String sqr = "";
		String sqbm = "";
		String sqrq = "";
		String qjjsrq = "";
		String sqsy = "";
		sqr = jo.getString("sqr");
		sqbm = jo.getString("sqbm");
		sqrq = jo.getString("sqrq");
		qjjsrq = jo.getString("qjjsrq");
		sqsy = jo.getString("sqsy");
		sqr = tu.getResourceIdByLoginid(sqr);
		sqbm = tu.getResourceIdByLoginid(sqbm);
		header.put("sqr", sqr);
		header.put("sqbm", sqbm);
		header.put("sqrq", sqrq);
		header.put("qjjsrq", qjjsrq);
		header.put("sqsy", sqsy);
		jsonOb.put("HEADER", header);
		jsonOb.put("DETAILS", detail);
		AutoRequestService ar = new AutoRequestService();
		result = ar.createRequest(workflowid, jsonOb.toString(), sqr, "1");
		return result;
	}
	
}
