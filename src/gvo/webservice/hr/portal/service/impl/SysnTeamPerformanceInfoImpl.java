package gvo.webservice.hr.portal.service.impl;

import gvo.webservice.hr.portal.util.InsertUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 *@Description: 同步直接团队绩效信息
* @version: 
* @author: jianyong.tang
* @date: 2020年4月15日 上午09：50
 */
public class SysnTeamPerformanceInfoImpl {
	/**
	 * 同步处理数据
	 * @param json
	 * @return
	 * @throws Exception 
	 */
	public String sysnInfo(String json) throws Exception {
		BaseBean log = new BaseBean();
		if("".equals(json)) {
			return "{\"result\":\"E\",\"msg\":\"请传递正确的json格式\"}";
		}
		RecordSet rs = new RecordSet();
		InsertUtil iu = new InsertUtil();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tablename = "uf_hr_tdjx_zj";
		String sql = "";
		String nowtime = "";
		String workcode = "";//工号
		String khnf = "";//考核年份
		JSONArray data = new JSONArray(json);
		boolean result = true;
		for(int i=0;i<data.length();i++) {
			JSONObject jo = data.getJSONObject(i);
			workcode = jo.getString("workcode");
			khnf = jo.getString("khnf");

			nowtime = sf.format(new Date());
			Map<String,String> map = new HashMap<String,String>();
			map.put("workcode",workcode);
			map.put("khnf",khnf);
			map.put("halfyear",jo.getString("halfyear"));//上半年
			map.put("year",jo.getString("year"));//年度
			map.put("syqzzjg",jo.getString("syqzzjg"));//年度
			map.put("january",jo.getString("january"));//1月
			map.put("february",jo.getString("february"));//2月
			map.put("march",jo.getString("march"));//3月
			map.put("april",jo.getString("april"));//4月
			map.put("may",jo.getString("may"));//5月
			map.put("june",jo.getString("june"));//6月
			map.put("july",jo.getString("july"));//7月
			map.put("august",jo.getString("august"));//8月
			map.put("aeptember",jo.getString("aeptember"));//9月
			map.put("october",jo.getString("october"));//10月
			map.put("november",jo.getString("november"));//11月
			map.put("december",jo.getString("december"));//12月
			map.put("gxsj",nowtime);
			String billid = "";
			sql = "select id from "+tablename+" where workcode='"+workcode+"' and khnf='"+khnf+"'";
			rs.execute(sql);
			if(rs.next()){
				billid = Util.null2String(rs.getString("id"));
			}
			if("".equals(billid)){
				result=iu.insert(map,tablename);
			}else{
				result=iu.updateGen(map,tablename,"id",billid);
			}
			if(!result){
				log.writeLog("SysnTeamPerformanceInfoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败员工编码："+workcode+"\"}";
			}
			
		}
		
		
		return "{\"result\":\"S\",\"msg\":\"成功\"}";
	}
}
