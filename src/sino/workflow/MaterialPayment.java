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
 * 生产物料付款创建流程
 * @author tangj
 *
 */
public class MaterialPayment {
	/**
	 * 创建方法
	 * @param jsonStr 参数格式如下
	 *
	{
	"djbh": "P-20191012-001",			//单据编号
	"djlx": "a",					//单据类型
	"sqzz": "组织",					//申请组织
	"jszz":"组织",					//结算组织
	"fkzz": "组织",			//付款组织
	"wldw": "组织",		//往来单位
	"skdw": "组织",				//收款单位
	"bmbh": "uuu111",					//部门编号
	"cgygh": "2001",				//采购员工号
	"bb": "人民币",			//币别
	"xmmc": "项目名称",				//项目名称
	"fkly": "付款理由",					//付款理由
	"sqfkje": "1.00",					//申请付款金额
	"cjrgh": "1001",		//创建人工号
	"shrgh": "1001",		//审核人工号
	"shrq": "2019-12-03",		//审核日期
	"cjrq": "2019-12-03",		//创建日期
	"erpid": "123",		//erpid
	"Detail": [{
	"erpid": "123",		//erpid
	"wldm": "SN0001",	//物料代码
	"wlmc": "竹原料",		//物料名称
	"ggxh": "100cm*10cm",		//规格型号
	"dw": "根",				//单位
	"sl": "1.00",				//数量
	"hsdj": "1.00",				//含税单价
	"yfje": "1.00",				//应付金额
	"sqfkje": "1.00",				//申请付款金额
	"wfkje": "1.00",	//未付款金额
	"dfyxzh": "111111",					//对方银行账户
	"dfzhmc": "账户名称",				//对方账户名称
	"dfkhx": "对方开户行",		//对方开户行
	"fkyt": "aaa",	//付款用途
	"dqr": "2019-12-03",			//到期日
	"qwfkrq": "2019-12-03",				//期望付款日期
	"fyxm": "费用项目",				//费用项目
	"ydlx": "a",				//源单类型
	"ydh": "1111",				//源单号
	"ddh": "11111",				//订单号
	"hth": "111111",				//合同号
	"htmc": "合同名称",				//合同名称
	"cgsqdh": "aa1111",		//采购申请单号
	"bz": "备注"		//备注
	},
	{
	"erpid": "123",		//erpid
	"wldm": "SN0001",	//物料代码
	"wlmc": "竹原料",		//物料名称
	"ggxh": "100cm*10cm",		//规格型号
	"dw": "根",				//单位
	"sl": "1.00",				//数量
	"hsdj": "1.00",				//含税单价
	"yfje": "1.00",				//应付金额
	"sqfkje": "1.00",				//申请付款金额
	"wfkje": "1.00",	//未付款金额
	"dfyxzh": "111111",					//对方银行账户
	"dfzhmc": "账户名称",				//对方账户名称
	"dfkhx": "对方开户行",		//对方开户行
	"fkyt": "aaa",	//付款用途
	"dqr": "2019-12-03",			//到期日
	"qwfkrq": "2019-12-03",				//期望付款日期
	"fyxm": "费用项目",				//费用项目
	"ydlx": "a",				//源单类型
	"ydh": "1111",				//源单号
	"ddh": "11111",				//订单号
	"hth": "111111",				//合同号
	"htmc": "合同名称",				//合同名称
	"cgsqdh": "aa1111",		//采购申请单号
	"bz": "备注"		//备注
	}
	]
	}
	 * @return
	 * @throws Exception
	 */
	public String createSCWL(String jsonStr) throws Exception {
		String result = "";
		BaseBean log = new BaseBean();
		TransUtil tu = new TransUtil();
		String workflowCode = tu.getWfid("SCWLFK"); // 流程Workflowid
		String tableName = tu.getTbName("SCWLFK");
		log.writeLog("MaterialPayment----" + jsonStr);
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
		tu.writeLog("MaterialPayment", mainMap.toString());

		JSONObject json = new JSONObject(); // 流程信息
		JSONObject header = new JSONObject(); // 主表信息
		JSONObject details = new JSONObject(); // 明细
		header.put("erpid", Util.null2String(mainMap.get("erpid")));
		header.put("djbh", Util.null2String(mainMap.get("djbh")));
		header.put("djlx", Util.null2String(mainMap.get("djlx")));
		header.put("sqzz", Util.null2String(mainMap.get("sqzz")));
		header.put("jszz", Util.null2String(mainMap.get("jszz")));
		header.put("fkzz", Util.null2String(mainMap.get("fkzz")));
		header.put("wldw", Util.null2String(mainMap.get("wldw")));
		header.put("skdw", Util.null2String(mainMap.get("skdw")));
		header.put("bmbh", Util.null2String(mainMap.get("bmbh")));
		header.put("cgygh", Util.null2String(mainMap.get("cgygh")));
		header.put("bb", Util.null2String(mainMap.get("bb")));
		header.put("xmmc", Util.null2String(mainMap.get("xmmc")));
		header.put("fkly", Util.null2String(mainMap.get("fkly")));
		header.put("sqfkje", Util.null2String(mainMap.get("sqfkje")));
		header.put("cjrgh", Util.null2String(mainMap.get("cjrgh")));
		header.put("shrgh", Util.null2String(mainMap.get("shrgh")));
		header.put("shrq", Util.null2String(mainMap.get("shrq")));
		header.put("cjrq", Util.null2String(mainMap.get("cjrq")));
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
			dtjo.put("dw", Util.null2String(dtMap.get("dw")));
			dtjo.put("sl", Util.null2String(dtMap.get("sl")));
			dtjo.put("hsdj", Util.null2String(dtMap.get("hsdj")));
			dtjo.put("yfje", Util.null2String(dtMap.get("yfje")));
			dtjo.put("sqfkje", Util.null2String(dtMap.get("sqfkje")));
			dtjo.put("wfkje", Util.null2String(dtMap.get("wfkje")));
			dtjo.put("dfyxzh", Util.null2String(dtMap.get("dfyxzh")));
			dtjo.put("dfzhmc", Util.null2String(dtMap.get("dfzhmc")));
			dtjo.put("dfkhx", Util.null2String(dtMap.get("dfkhx")));
			dtjo.put("fkyt", Util.null2String(dtMap.get("fkyt")));
			dtjo.put("dqr", Util.null2String(dtMap.get("dqr")));
			dtjo.put("qwfkrq", Util.null2String(dtMap.get("qwfkrq")));
			dtjo.put("fyxm", Util.null2String(dtMap.get("fyxm")));
			dtjo.put("ydlx", Util.null2String(dtMap.get("ydlx")));
			dtjo.put("ydh", Util.null2String(dtMap.get("ydh")));
			dtjo.put("ddh", Util.null2String(dtMap.get("ddh")));
			dtjo.put("hth", Util.null2String(dtMap.get("hth")));
			dtjo.put("htmc", Util.null2String(dtMap.get("htmc")));
			dtjo.put("cgsqdh", Util.null2String(dtMap.get("cgsqdh")));
			dtjo.put("bz", Util.null2String(dtMap.get("bz")));
			dt1.put(dtjo);
		}
		details.put("DT1", dt1);
		json.put("DETAILS", details);
		tu.writeLog("MaterialPayment", "json:"+json.toString());
		AutoRequestService wf = new AutoRequestService();
		result = wf.createRequest(workflowCode, json.toString(), tu.getResourceId(Util.null2String(mainMap.get("cjrgh"))), "1");
		tu.writeLog("MaterialPayment", "result:"+result.toString());
		return result;
	}
}
