package sino.workflow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import sino.util.AutoRequestService;
import sino.util.TransUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

/**
 * 创建采购订单流程
 * @author tangj
 *
 */
public class CreatePurchaseOrder {
	/**
	 * 创建方法
	 * @param jsonStr 参数格式如下
	 * 
	   {
		"djbh": "P-20191012-001",			//单据编号
		"djlx": "9001",					//单据类型
		"cgzz": "9001",					//采购组织编码
		"cgbm":"9001",					//采购部门编码
		"cgyggh": "9001",			//采购员工工号
		"cgrq": "2019-10-12",		//采购日期
		"hth": "11111",				//合同号
		"htmc": "测试1111",					//合同名称
		"xmmc": "项目1111",				//项目名称
		"cjrgh": "9001",			//创建人工号
		"cjrq": "2019-10-12",				//采创建日期
		"shrgh": "供应商111",					//审核人工号
		"shrq": "摘要",					//审核日期
		"bb": "人民币",		//币别
		"gysst": "供应商",		//供应商
	    "erpid": "123",		//erpid
		"Detail": [{
				"erpid": "123",		//erpid
				"wldm": "SN0001",	//物料代码
				"wlmc": "竹原料",		//物料名称
				"ggxh": "100cm*10cm",		//规格型号
				"cgdw": "根",				//采购单位
	   	        "cgsl": "1.00",				//采购数量
				"jbdw": "根",				//基本单位
				"jbdwsl": "1.00",				//基本单位数量
				"jskc": "1.00",				//即时库存
				"jjdj": "1.00",	//计价单价
				"jjsl": "2.00",					//计价数量
				"jhrq": "2019-10-24",				//交货日期
				"dj": "2.00",		//单价
				"hsdj": "2.00",	//含税单价
				"sccgjg": "2.00",			//上次采购价格
				"sl": "1.00",				//税率
				"se": "2.00",				//税额
				"je": "2.00",				//金额
				"jshj": "2.00",				//价税合计
				"sfzp": "11111",				//是否赠品
				"bz": "备注",				//备注
				"ydh": "1002000",				//源单号
				"xqzz": "需求组织"		//需求组织

			},
			{
				"erpid": "123",		//erpid
				"wldm": "SN0001",	//物料代码
				"wlmc": "竹原料",		//物料名称
				"ggxh": "100cm*10cm",		//规格型号
				"cgdw": "根",				//采购单位
				"cgsl": "1.00",				//采购数量
				"jbdw": "根",				//基本单位
				"jbdwsl": "1.00",				//基本单位数量
				"jskc": "1.00",				//即时库存
				"jjdj": "1.00",	//计价单价
				"jjsl": "2.00",					//计价数量
				"jhrq": "2019-10-24",				//交货日期
				"dj": "2.00",		//单价
				"hsdj": "2.00",	//含税单价
				"sccgjg": "2.00",			//上次采购价格
				"sl": "1.00",				//税率
				"se": "2.00",				//税额
				"je": "2.00",				//金额
				"jshj": "2.00",				//价税合计
				"sfzp": "11111",				//是否赠品
				"bz": "备注",				//备注
				"ydh": "1002000",				//源单号
				"xqzz": "需求组织"		//需求组织
			}
		]
	} 
	 * @return
	 * @throws Exception 
	 */
	public String createPR(String jsonStr) throws Exception {
		String result = "";
		BaseBean log = new BaseBean();
		TransUtil tu = new TransUtil();
		String workflowCode = tu.getWfid("CGDD"); // 流程Workflowid
		String tableName = tu.getTbName("CGDD");
		log.writeLog("CreatePurchaseOrder----" + jsonStr);
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
        tu.writeLog("CreatePurchaseOrder", mainMap.toString());

        JSONObject json = new JSONObject(); // 流程信息
		JSONObject header = new JSONObject(); // 主表信息
		JSONObject details = new JSONObject(); // 明细
		header.put("erpid", Util.null2String(mainMap.get("erpid")));
		header.put("djbh", Util.null2String(mainMap.get("djbh")));
		header.put("djlx", Util.null2String(mainMap.get("djlx")));
		header.put("cgzz", Util.null2String(mainMap.get("cgzz")));
		header.put("cgbm", Util.null2String(mainMap.get("cgbm")));
		header.put("cgyggh", Util.null2String(mainMap.get("cgyggh")));
		header.put("cgrq", Util.null2String(mainMap.get("cgrq")));
		header.put("hth", Util.null2String(mainMap.get("hth")));
		header.put("htmc", Util.null2String(mainMap.get("htmc")));
		header.put("xmmc", Util.null2String(mainMap.get("xmmc")));
		header.put("cjrgh", Util.null2String(mainMap.get("cjrgh")));
		header.put("cjrq", Util.null2String(mainMap.get("cjrq")));
		header.put("shrgh", Util.null2String(mainMap.get("shrgh")));
		header.put("shrq", Util.null2String(mainMap.get("shrq")));
		header.put("bb", Util.null2String(mainMap.get("bb")));
		header.put("gysst", Util.null2String(mainMap.get("gysst")));
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
	        dtjo.put("wldm", Util.null2String(dtMap.get("wldm")));
	        dtjo.put("wlmc", Util.null2String(dtMap.get("wlmc")));
	        dtjo.put("ggxh", Util.null2String(dtMap.get("ggxh")));
	        dtjo.put("cgdw", Util.null2String(dtMap.get("cgdw")));
	        dtjo.put("cgsl", Util.null2String(dtMap.get("cgsl")));
	        dtjo.put("jbdw", Util.null2String(dtMap.get("jbdw")));
	        dtjo.put("jbdwsl", Util.null2String(dtMap.get("jbdwsl")));
	        dtjo.put("jskc", Util.null2String(dtMap.get("jskc")));
	        dtjo.put("jjdj", Util.null2String(dtMap.get("jjdj")));
	        dtjo.put("jjsl", Util.null2String(dtMap.get("jjsl")));
	        String jhrq = Util.null2String(dtMap.get("jhrq"));
	        if(jhrq.length()>10){
	        	jhrq = jhrq.substring(0,10);
			}
	        dtjo.put("jhrq", jhrq);
	        dtjo.put("dj", Util.null2String(dtMap.get("dj")));
	        dtjo.put("hsdj", Util.null2String(dtMap.get("hsdj")));
	        dtjo.put("sccgjg", Util.null2String(dtMap.get("sccgjg")));
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
		tu.writeLog("CreatePurchaseOrder", "json:"+json.toString());
		AutoRequestService wf = new AutoRequestService();
		result = wf.createRequest(workflowCode, json.toString(), tu.getResourceId(Util.null2String(mainMap.get("cjrgh"))), "1");
		tu.writeLog("CreatePurchaseOrder", "result:"+result.toString());
		return result;
	}
}
