package sino.workflow;

import org.json.JSONArray;
import org.json.JSONObject;
import sino.util.AutoRequestService;
import sino.util.TransUtil;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 创建采购订单修改流程
 * @author tangj
 *
 */
public class CreatePurchaseOrderModify {
	/**
	 * 创建方法
	 * @param jsonStr 参数格式如下
	 * 
	   {
		"djbh": "P-20191012-001",			//单据编号
		"djlx": "单据类型",					//单据类型
		"cgzz": "采购组织",					//采购组织
		"ygys":"原供应商",					//原供应商
		"xgys": "新供应商",			//新供应商
		"ybb": "人民币",		//原币别
		"xbb": "人民币",				//新币别
		"cgbmbh": "采购部门编号",					//采购部门编号
		"cgygh": "1111",				//采购员工号
		"cgbgrq": "2019-10-10",			//采购变更日期
		"hth": "1111",				//合同号
		"htmc": "供应商111",					//合同名称
		"xmmc": "摘要",					//项目名称
		"cjrgh": "11111",		//创建人工号
		"cjrq": "2019-10-10",		//创建日期
		"shrgh": "11111",		//审核人工号
		"shrq": "2019-10-10",		//审核日期
	    "erpid": "123",		//erpid
		"bgyy": "变更原因",				//变更原因
		"Detail": [{
				"erpid": "123",		//erpid
				"wlbm": "物料编码",	//物料编码
				"wlmc": "物料名称",		//物料名称
				"ggxh": "100cm*10cm",		//规格型号
				"bglx": "变更类型",				//变更类型
	   	        "cgdw": "采购单位",				//采购单位
				"ycgsl": "1.00",				//原采购数量
				"xcgsl": "1.00",				//新采购数量
				"jbdw": "基本单位",				//基本单位
				"yjbdwsl": "1.00",	//原基本单位数量
				"xjbdwsl": "2.00",					//新基本单位数量
				"jskc": "1.00",				//即时库存
				"jjdw": "计价单位",		//计价单位
				"yjjsl": "2.00",	//原计价数量
				"xjjsl": "2.00",			//新计价数量
				"yjhrq": "2019-10-01",				//原交货日期
				"xjhrq": "2019-10-01",				//新交货日期
				"ydj": "2.00",				//原单价
				"xdj": "2.00",				//新单价
				"yhsdj": "2.00",				//原含税单价
				"xhsdj": "2.00",				//新含税单价
				"sccgjg": "1002000",				//上次采购价格
				"sccgbb": "上次采购币别",	//上次采购币别
				"sl": "税率",	//税率
				"se": "2.00",				//税额
				"je": "2.00",				//金额
				"jshj": "2.00",				//价税合计
				"sfzp": "是否赠品",				//是否赠品
				"bz": "备注",				//备注
				"ydh": "源单号",				//源单号
				"xqzz": "需求组织"				//需求组织
			},
			{
			"erpid": "123",		//erpid
			"wlbm": "物料编码",	//物料编码
			"wlmc": "物料名称",		//物料名称
			"ggxh": "100cm*10cm",		//规格型号
			"bglx": "变更类型",				//变更类型
			"cgdw": "采购单位",				//采购单位
			"ycgsl": "1.00",				//原采购数量
			"xcgsl": "1.00",				//新采购数量
			"jbdw": "基本单位",				//基本单位
			"yjbdwsl": "1.00",	//原基本单位数量
			"xjbdwsl": "2.00",					//新基本单位数量
			"jskc": "1.00",				//即时库存
			"jjdw": "计价单位",		//计价单位
			"yjjsl": "2.00",	//原计价数量
			"xjjsl": "2.00",			//新计价数量
			"yjhrq": "2019-10-01",				//原交货日期
			"xjhrq": "2019-10-01",				//新交货日期
			"ydj": "2.00",				//原单价
			"xdj": "2.00",				//新单价
			"yhsdj": "2.00",				//原含税单价
			"xhsdj": "2.00",				//新含税单价
			"sccgjg": "1002000",				//上次采购价格
			"sccgbb": "上次采购币别",	//上次采购币别
			"sl": "税率",	//税率
			"se": "2.00",				//税额
			"je": "2.00",				//金额
			"jshj": "2.00",				//价税合计
			"sfzp": "是否赠品",				//是否赠品
			"bz": "备注",				//备注
			"ydh": "源单号",				//源单号
			"xqzz": "需求组织"				//需求组织
			}
		]
	} 
	 * @return
	 * @throws Exception 
	 */
	public String createPRModify(String jsonStr) throws Exception {
		String result = "";
		BaseBean log = new BaseBean();
		TransUtil tu = new TransUtil();
		String workflowCode = tu.getWfid("CGDDBG"); // 流程Workflowid
		String tableName = tu.getTbName("CGDDBG");
		log.writeLog("CreatePurchaseOrderModify----" + jsonStr);
//
		JSONObject json_str = new JSONObject(jsonStr);
		Map<String,String> mainMap = new HashMap<String ,String>();
		Iterator it = json_str.keys();
        while (it.hasNext()) {
            String key = it.next().toString();
            String value = json_str.getString(key);
            if("Detail".equals(key)) continue;
            mainMap.put(key, value);
            
        }
        tu.writeLog("CreatePurchaseOrderModify", mainMap.toString());

        JSONObject json = new JSONObject(); // 流程信息
		JSONObject header = new JSONObject(); // 主表信息
		JSONObject details = new JSONObject(); // 明细
		header.put("erpid", Util.null2String(mainMap.get("erpid")));
		header.put("djbh", Util.null2String(mainMap.get("djbh")));
		header.put("djlx", Util.null2String(mainMap.get("djlx")));
		header.put("cgzz", Util.null2String(mainMap.get("cgzz")));
		header.put("ygys", Util.null2String(mainMap.get("ygys")));
		header.put("xgys", Util.null2String(mainMap.get("xgys")));
		header.put("ybb", Util.null2String(mainMap.get("ybb")));
		header.put("xbb", Util.null2String(mainMap.get("xbb")));
		header.put("cgbmbh", Util.null2String(mainMap.get("cgbmbh")));
		header.put("cgygh", Util.null2String(mainMap.get("cgygh")));
		header.put("cgbgrq", Util.null2String(mainMap.get("cgbgrq")));
		header.put("hth", Util.null2String(mainMap.get("hth")));
		header.put("htmc", Util.null2String(mainMap.get("htmc")));
		header.put("xmmc", Util.null2String(mainMap.get("xmmc")));
		header.put("cjrgh", Util.null2String(mainMap.get("cjrgh")));
		header.put("cjrq", Util.null2String(mainMap.get("cjrq")));
		header.put("shrgh", Util.null2String(mainMap.get("shrgh")));
		header.put("shrq", Util.null2String(mainMap.get("shrq")));
		header.put("bgyy", Util.null2String(mainMap.get("bgyy")));
		header.put("cjr", tu.getResourceId(Util.null2String(mainMap.get("cjrgh"))));
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
	        dtjo.put("bglx", Util.null2String(dtMap.get("bglx")));
	        dtjo.put("cgdw", Util.null2String(dtMap.get("cgdw")));
	        dtjo.put("ycgsl", Util.null2String(dtMap.get("ycgsl")));
	        dtjo.put("xcgsl", Util.null2String(dtMap.get("xcgsl")));
	        dtjo.put("jbdw", Util.null2String(dtMap.get("jbdw")));
	        dtjo.put("yjbdwsl", Util.null2String(dtMap.get("yjbdwsl")));
	        dtjo.put("xjbdwsl", Util.null2String(dtMap.get("xjbdwsl")));
	        dtjo.put("jskc", Util.null2String(dtMap.get("jskc")));
	        dtjo.put("jjdw", Util.null2String(dtMap.get("jjdw")));
	        dtjo.put("yjjsl", Util.null2String(dtMap.get("yjjsl")));
	        dtjo.put("xjjsl", Util.null2String(dtMap.get("xjjsl")));
	        dtjo.put("yjhrq", Util.null2String(dtMap.get("yjhrq")));
	        dtjo.put("xjhrq", Util.null2String(dtMap.get("xjhrq")));
	        dtjo.put("ydj", Util.null2String(dtMap.get("ydj")));
	        dtjo.put("xdj", Util.null2String(dtMap.get("xdj")));
	        dtjo.put("yhsdj", Util.null2String(dtMap.get("yhsdj")));
	        dtjo.put("xhsdj", Util.null2String(dtMap.get("xhsdj")));
	        dtjo.put("sccgjg", Util.null2String(dtMap.get("sccgjg")));
	        dtjo.put("sccgbb", Util.null2String(dtMap.get("sccgbb")));
			dtjo.put("sl", Util.null2String(dtMap.get("sl")));
			dtjo.put("se", Util.null2String(dtMap.get("se")));
			dtjo.put("je", Util.null2String(dtMap.get("je")));
			dtjo.put("jshj", Util.null2String(dtMap.get("jshj")));
			dtjo.put("sfzp", Util.null2String(dtMap.get("sfzp")));
			dtjo.put("bz", Util.null2String(dtMap.get("bz")));
			dtjo.put("ydh", Util.null2String(dtMap.get("ydh")));
			dtjo.put("xqzz", Util.null2String(dtMap.get("xqzz")));
	        dt1.put(dtjo);        
		}
		details.put("DT1", dt1);
		json.put("DETAILS", details);
		tu.writeLog("CreatePurchaseOrderModify", "json:"+json.toString());
		AutoRequestService wf = new AutoRequestService();
		result = wf.createRequest(workflowCode, json.toString(), tu.getResourceId(Util.null2String(mainMap.get("cjrgh"))), "1");
		tu.writeLog("CreatePurchaseOrderModify", "result:"+result.toString());
		return result;
	}
}
