package sino.workflow;

import org.json.JSONObject;


import sino.k3cloud.webapi.K3CloudApiClient;
import sino.util.K3CloudUtil;
import sino.util.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 采购申请退回
 */
public class OaToCloudTHForPR implements Action{
	public String execute(RequestInfo info) {
		String workflowid = info.getWorkflowid();
		String requestid = info.getRequestid();
		TransUtil tu = new TransUtil();
		String tableName = tu.getTableName(workflowid);
		int currentid = info.getRequestManager().getUserId();
		String workcode = tu.getWorkcode(currentid+"");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDate = sf.format(new Date());
		RecordSet rs = new RecordSet();
		Map<String,String> map = new HashMap<String,String>();
		String lcbh = "";
		String erpid = "";
		String djlx = "";
		String sql = "select djbh,erpid,djlx from "+tableName+" where requestid="+requestid;
		rs.execute(sql);
		if(rs.next()) {
			lcbh = Util.null2String(rs.getString("djbh"));
			erpid = Util.null2String(rs.getString("erpid"));
			djlx = Util.null2String(rs.getString("djlx"));
		}

		map.put("FID",erpid);
		map.put("FBILLNO",lcbh);
		map.put("FBILLTYPE",djlx);
		map.put("FAPPROVERNO",workcode);
		map.put("FAPPROVERDATE",nowDate);
		map.put("F_OA_ID",requestid);
		map.put("FDOCUMENTSTATUS","Z");
		String K3CloudURL = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "K3CloudURL"));
		String dbId = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "dbId"));
		String uid = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "uid"));
		String pwd = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "pwd"));
		int lang = Util.getIntValue(Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "K3CloudURL")),2052);
		K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
		try {
		    Boolean resultLogin = client.login(dbId, uid, pwd, lang);
		    if (resultLogin) {
		        String content = "{\"CreateOrgId\":0,\"Numbers\":[\""+lcbh+"\"],\"Ids\":\"\"}";
		        tu.writeLog("OaToCloudTYForPR requestid："+requestid+" sContent:"+content);
		        String result = client.excuteOperation("PUR_Requisition","Cancel",content);
		        tu.writeLog("OaToCloudTYForPR requestid："+requestid+" sResult:"+result);
		        JSONObject jo = new JSONObject(result);
		    	String IsSUCCESS = jo.getJSONObject("Result").getJSONObject("ResponseStatus").getString("IsSuccess");
		    	//String Errors = jo.getJSONObject("Result").getJSONObject("ResponseStatus").getJSONArray("Errors").toString();
		    	if("false".equals(IsSUCCESS)) {
		    		info.getRequestManager().setMessagecontent("推送cloud接口成功，cloud处理失败，请联系系统管理员");        
			    	info.getRequestManager().setMessageid("ERRORMSG:");
			    	return FAILURE_AND_CONTINUE;
		    	}
		    }else {
		    	info.getRequestManager().setMessagecontent("推送cloud接口失败，请联系系统管理员");        
		    	info.getRequestManager().setMessageid("ERRORMSG:");
		    	return FAILURE_AND_CONTINUE;
		    }
	    }catch(Exception e) {
	    	 tu.writeLog(e);
	    	info.getRequestManager().setMessagecontent("推送cloud接口失败，请联系系统管理员");        
	    	info.getRequestManager().setMessageid("ERRORMSG:");
	    	return FAILURE_AND_CONTINUE;
	    }
		K3CloudUtil ku = new K3CloudUtil();
		ku.insertK3PurchaseMidTB(map);
		return SUCCESS;
	}
}
