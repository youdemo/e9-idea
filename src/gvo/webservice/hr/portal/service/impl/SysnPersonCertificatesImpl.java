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
 *@Description: 同步员工上岗证信息
* @version: 
* @author: jianyong.tang
* @date: 2020年4月14日 上午09：25
 */
public class SysnPersonCertificatesImpl {
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
		String tablename = "uf_hr_personCert";
		String sql = "";
		String nowtime = "";
		String workcode = "";//工号
		String recordid = "";//员工记录号
		JSONArray data = new JSONArray(json);
		boolean result = true;
		for(int i=0;i<data.length();i++) {
			JSONObject jo = data.getJSONObject(i);
			workcode = jo.getString("workcode");
			recordid = jo.getString("recordid");

			nowtime = sf.format(new Date());
			String joblevel = jo.getString("joblevel");
//			new BaseBean().writeLog("testaaa joblevel",joblevel);
//			if("\u2160".equals(joblevel)){
//				joblevel = "Ⅰ";
//			}else if("\u2161".equals(joblevel)){
//				joblevel = "Ⅱ";
//			}else if("\u2162".equals(joblevel)){
//				joblevel = "Ⅲ";
//			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("workcode",workcode);
			map.put("recordid",recordid);
			map.put("jobname",jo.getString("jobname"));//认证岗位
			map.put("joblevel",joblevel);//岗位等级  A/B/C/D/S/Ⅲ/Ⅱ/Ⅰ
			map.put("jobtype",jo.getString("jobtype"));//岗位属性
			map.put("jobstarttime",jo.getString("jobstarttime"));//岗位认证时间
			map.put("jobendtime",jo.getString("jobendtime"));//岗位认证失效日期
			//map.put("jobtimes",jo.getString("jobtimes"));//认证时长
			map.put("skillname1",jo.getString("skillname1"));//技能名称1
			map.put("skilllevel1",jo.getString("skilllevel1"));//技能等级1
			map.put("skillstarttime1",jo.getString("skillstarttime1"));//技能认证时间1
			map.put("skillendtime1",jo.getString("skillendtime1"));//技能有效截止时间1
			map.put("skillname2",jo.getString("skillname2"));//技能名称2
			map.put("skilllevel2",jo.getString("skilllevel2"));//技能等级2
			map.put("skillstarttime2",jo.getString("skillstarttime2"));//技能认证时间2
			map.put("skillendtime2",jo.getString("skillendtime2"));//技能有效截止时间2
			map.put("gxsj",nowtime);
			String billid = "";
			sql = "select id from "+tablename+" where workcode='"+workcode+"' and recordid='"+recordid+"'";
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
				log.writeLog("SysnPersonCertificatesImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败员工编码："+workcode+"\"}";
			}


			
		}
		
		
		return "{\"result\":\"S\",\"msg\":\"成功\"}";
	}
}
