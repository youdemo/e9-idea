package sino.workflow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sino.util.AutoRequestService;
import sino.util.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MaterialPurchaseWFCreate {
	/**
	 * 物料采购申请流程触发
	 * @throws JSONException 
	 **/
	/*{
		"erpid": "P-20191012-001",			//erpid
		"djbh": "P-20191012-001",			//单据编号
		"djlx": "a",					//单据类型
		"sqzz": "组织",					//申请组织
		"sqbm":"组织",					//申请部门
		"sqr": "组织",			//申请人
		"sqrq": "2019-10-12",			//申请日期
		"hth": "CS0001",				//合同号
		"htmc": "委外订单",					//合同名称
		"xmmc": "委外订单",				//项目名称
		"bz": "标准",				//备注
		"cjrgh": "1111",				//创建人工号
		"cjrq": "2019-10-10",					//创建日期
		"shrgh": "22222",		//审核人工号
		"shrq": "2019-10-10",						//审核日期
		"Detail": [{
				"erpid": "P-20191012-001",			//erpid
				"wlbm": "物料编码",		//物料编码
				"wlmc": "竹原料",		//物料名称
				"ggxh": "100cm*10cm",		//规格型号
				"sqdw": "暂无",				//申请单位
				"sqsl": "11",				//申请数量
				"jbdwmc": "根",				//基本单位
				"jbdwsl": "11",				//基本单位数量
				"kcsl": "1000",				//即时库存
				"dhrq": "2019-11-20",		//到货日期
				"jycgrq": "2019-11-10",	//建议采购日期
				"jhqrscrq": "2",					//计划确认生产日期
				"ydlx": "aa",	//源单类型
				"yddh": "S020191012001",	//源单编号
				"xqly": "5000",				//需求来源
				"xqdbh": "5000",				//需求单编号
				"xqzz": "5000"			//需求组织
			},
			{
				erpid": "P-20191012-001",			//erpid
				"wlbm": "物料编码",		//物料编码
				"wlmc": "竹原料",		//物料名称
				"ggxh": "100cm*10cm",		//规格型号
				"sqdw": "暂无",				//申请单位
				"sqsl": "11",				//申请数量
				"jbdwmc": "根",				//基本单位
				"jbdwsl": "11",				//基本单位数量
				"kcsl": "1000",				//即时库存
				"dhrq": "2019-11-20",		//到货日期
				"jycgrq": "2019-11-10",	//建议采购日期
				"jhqrscrq": "2",					//计划确认生产日期
				"ydlx": "aa",	//源单类型
				"yddh": "S020191012001",	//源单编号
				"xqly": "5000",				//需求来源
				"xqdbh": "5000",				//需求单编号
				"xqzz": "5000"			//需求组织
			}
		]
	} */
	public String doCreate(String jsonStr) throws JSONException {
		BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		TransUtil tu = new TransUtil();
		String result = "";
		String workflowCode = tu.getWfid("CGSQ"); // 流程Workflowid
		String tbName = tu.getTbName("CGSQ");
		log.writeLog("MaterialPurchaseWFCreate----" + jsonStr);
		// 解析json
		JSONObject json_str = new JSONObject(jsonStr);
		Map<String,String> mainMap = new HashMap<String ,String>();
		Iterator it = json_str.keys();
		while (it.hasNext()) {
			String key = it.next().toString();
			String value = json_str.getString(key);
			if("Detail".equals(key)) continue;
			mainMap.put(key, value);

		}
		tu.writeLog("MaterialPayment", mainMap.toString());

		JSONObject json = new JSONObject(); // 流程信息
		JSONObject header = new JSONObject(); // 主表信息
		JSONObject details = new JSONObject(); // 明细
		header.put("erpid", Util.null2String(mainMap.get("erpid")));
		header.put("djbh", Util.null2String(mainMap.get("djbh")));
		header.put("djlx", Util.null2String(mainMap.get("djlx")));
		header.put("sqzz", Util.null2String(mainMap.get("sqzz")));
		header.put("sqbm", Util.null2String(mainMap.get("sqbm")));
		header.put("sqr", Util.null2String(mainMap.get("sqr")));
		header.put("sqrq", Util.null2String(mainMap.get("sqrq")));
		header.put("hth", Util.null2String(mainMap.get("hth")));
		header.put("htmc", Util.null2String(mainMap.get("htmc")));
		header.put("xmmc", Util.null2String(mainMap.get("xmmc")));
		header.put("bz", Util.null2String(mainMap.get("bz")));
		header.put("sqrgh", Util.null2String(mainMap.get("cjrgh")));
		header.put("cjrq", Util.null2String(mainMap.get("cjrq")));
		header.put("shrgh", Util.null2String(mainMap.get("shrgh")));
		header.put("shrq", Util.null2String(mainMap.get("shrq")));
		header.put("cjr", tu.getResourceId(Util.null2String(mainMap.get("cjrgh"))));
		header.put("shr", tu.getResourceId(Util.null2String(mainMap.get("shrgh"))));
		json.put("HEADER", header);
		JSONArray ja = json_str.getJSONArray("Detail");
		JSONArray dt1 = new JSONArray();
		for(int i=0;i<ja.length();i++) {
			JSONObject dtjo = new JSONObject();
			JSONObject jo = ja.getJSONObject(i);
			Map<String,String> dtMap = new HashMap<String ,String>();
			Iterator itdt = jo.keys();
			while (itdt.hasNext()) {
				String key = itdt.next().toString();
				String value = jo.getString(key);
				dtMap.put(key, value);

			}
			dtjo.put("erpid", Util.null2String(dtMap.get("erpid")));
			dtjo.put("wlbm", Util.null2String(dtMap.get("wlbm")));
			dtjo.put("wlmc", Util.null2String(dtMap.get("wlmc")));
			dtjo.put("ggxh", Util.null2String(dtMap.get("ggxh")));
			dtjo.put("sqdw", Util.null2String(dtMap.get("sqdw")));
			dtjo.put("sqsl", Util.null2String(dtMap.get("sqsl")));
			dtjo.put("jbdwmc", Util.null2String(dtMap.get("jbdwmc")));
			dtjo.put("jbdwsl", Util.null2String(dtMap.get("jbdwsl")));
			dtjo.put("kcsl", Util.null2String(dtMap.get("kcsl")));
			dtjo.put("dhrq", Util.null2String(dtMap.get("dhrq")));
			dtjo.put("jycgrq", Util.null2String(dtMap.get("jycgrq")));
			dtjo.put("jhqrscrq", Util.null2String(dtMap.get("jhqrscrq")));
			dtjo.put("ydlx", Util.null2String(dtMap.get("ydlx")));
			dtjo.put("yddh", Util.null2String(dtMap.get("yddh")));
			dtjo.put("xqly", Util.null2String(dtMap.get("xqly")));
			dtjo.put("xqdbh", Util.null2String(dtMap.get("xqdbh")));
			dtjo.put("xqzz", Util.null2String(dtMap.get("xqzz")));
			dt1.put(dtjo);
		}
		details.put("DT1", dt1);
		json.put("DETAILS", details);
		tu.writeLog("MaterialPurchaseWFCreate", "json:"+json.toString());
		AutoRequestService wf = new AutoRequestService();
		result = wf.createRequest(workflowCode, json.toString(), tu.getResourceId(Util.null2String(mainMap.get("cjrgh"))), "1");
		tu.writeLog("MaterialPurchaseWFCreate", "result:"+result.toString());
		return result;
	}

}
