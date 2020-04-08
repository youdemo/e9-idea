package APPDEV.HQ.FNA.TL;

import java.util.HashMap;
import java.util.Map;

import APPDEV.HQ.FNA.UTIL.TransUtil;
import APPDEV.HQ.UTIL.GetMachineUtil;
import net.sf.json.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

import APPDEV.HQ.UTIL.BringMainAndDetailByMain;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

/**
	获取人员请假调休信息
*/
public class FNA_TL_AP_GetSapLeaveDays {
	 public String geLeaveDays(String clh){
	        BaseBean log = new BaseBean();
	        RecordSet rs = new RecordSet();
	        RecordSet rs_dt = new RecordSet();
	        String sql_dt = "";
	        String workflowid = "";
	        String ryid = "";//出差人
		 	String gsdm = "";//公司代码
	        String sql = "select workflowid from workflow_requestbase where requestid="+clh;
	        rs.execute(sql);
	        if(rs.next()){
	        	workflowid = Util.null2String(rs.getString("workflowid"));
	        }
	        TransUtil tu = new TransUtil();
	        String tableName = tu.getTableName(workflowid);
	        sql = "select BtrPER,BtrBukrs from "+tableName+" where requestid="+clh;
	        rs.execute(sql);
	        if(rs.next()){
				ryid = Util.null2String(rs.getString("BtrPER"));
				gsdm = Util.null2String(rs.getString("BtrBukrs"));
			}
	        JSONObject jo = new JSONObject();
		 	JSONArray ja = new JSONArray();
	        log.writeLog("FNA_TL_AP_GetSapLeaveDays geLeaveDays","ryid:"+ryid+",gsdm:"+gsdm+",clh:"+clh);
	        String result = getBaseSapData(clh,ryid,gsdm,"41");
	        log.writeLog("FNA_TL_AP_GetSapLeaveDays geLeaveDays","result:"+result);
	        try {

	        	String EV_STATU = "";//状态
	            JSONObject json = new JSONObject(result);
	            JSONObject data = json.getJSONObject("type");

				EV_STATU = data.getString("EV_STATU");
				jo.put("EV_STATU",EV_STATU);
				JSONArray dt = json.getJSONObject("table").getJSONArray("Detail");
				for(int i=0;i<dt.length();i++){
					String BEGDA = dt.getJSONObject(i).getJSONObject("dt").getString("BEGDA");
					JSONObject jo1 = new JSONObject();
					jo1.put("BEGDA",BEGDA);
					ja.put(jo1);
				}
				jo.put("dt",ja);


	        } catch (Exception e) {
	            log.writeLog(e);
	            log.writeLog("解析json格式异常：" + result);
	        }
	        return jo.toString();

	    }

	/**
	 *
	 * @param IV_REINR  差旅号
	 * @param IV_PERNR 人员号
	 * @param IV_BUKRS 公司代码
	 * @param workflowId
	 * @return
	 */
	    public String getBaseSapData(String IV_REINR, String IV_PERNR, String IV_BUKRS,
	                                String workflowId) {
	        Map<String, String> oaDatas = new HashMap<String, String>();
	        oaDatas.put("IV_REINR", IV_REINR);
	        oaDatas.put("IV_BUKRS", IV_BUKRS);
	        oaDatas.put("IV_PERNR", IV_PERNR);
	        String dataSourceId = "";
			String machine = GetMachineUtil.getMachine();
			if ("PRO".equals(machine)) {//正式
				dataSourceId = "41";
			}else {//96
				dataSourceId = "141";
			}
			BringMainAndDetailByMain bmb = new BringMainAndDetailByMain(dataSourceId);//正式 41 测试141
	        String result = bmb.getReturn(oaDatas, workflowId, "", null);
	        return result;
	    }
}
