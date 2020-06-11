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
 *@Description: 考勤异常情况同步
 *@version:
* @author: jianyong.tang
* @date: 2020年4月15日 上午09：33
 */
public class SysnPersonAttendExpInfoImpl {
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
		String tablename = "uf_hr_attendexpInfo";
		String sql = "";
		String nowtime = "";
		String workcode = "";//工号
		JSONArray data = new JSONArray(json);
		boolean result = true;
		for(int i=0;i<data.length();i++) {
			JSONObject jo = data.getJSONObject(i);
			workcode = jo.getString("workcode");
			nowtime = sf.format(new Date());
			//判断主表数据是否存在不存在新增
			String billid = "";
			sql = "select id from "+tablename+" where workcode='"+workcode+"' ";
			rs.execute(sql);
			if(rs.next()){
				billid = Util.null2String(rs.getString("id"));
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("workcode",workcode);
			map.put("gxsj",nowtime);
			if("".equals(billid)){
				result=iu.insert(map,tablename);
			}else{
				result=iu.updateGen(map,tablename,"id",billid);
			}
			if(!result){
				log.writeLog("SysnPersonAttendExpInfoImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败员工编码："+workcode+"\"}";
			}
			sql = "select id from "+tablename+" where workcode='"+workcode+"' ";
			rs.execute(sql);
			if(rs.next()){
				billid = Util.null2String(rs.getString("id"));
			}
			//处理明细班别信息
			JSONArray months = jo.getJSONArray("months");
			for(int j=0;j<months.length();j++) {
				JSONObject dtjo = months.getJSONObject(j);
				String month = dtjo.getString("month");
				sql = "delete from "+tablename+"_dt1 where mainid="+billid+"and month='"+month+"'";
				rs.execute(sql);
				JSONArray datas = dtjo.getJSONArray("datas");
				for(int t=0;t<datas.length();t++) {
					JSONObject datajo = datas.getJSONObject(t);
					String belongdate = datajo.getString("belongdate");//考勤归属日期
					String bcmc = datajo.getString("bcmc");//班次名称
					String bc = datajo.getString("bc");//班次
					String type = datajo.getString("type");//异常类型
					String nf = "";
					String yf = "";
					if(!"".equals(month)){
						nf = month.substring(0,4);
						yf = month.substring(5,7);
					}
					map = new HashMap<String,String>();
					map.put("month",month);//日期
					map.put("belongdate",belongdate);//考勤归属日期
					map.put("bcmc",bcmc);//班次名称
					map.put("bc",bc);//班次
					map.put("type",type);//异常类型 【旷工】，【缺卡】，【迟到】，【迟到，缺卡】、【迟到，旷工】
					map.put("nf",nf);//年份
					map.put("yf",yf);//月份
					map.put("mainid",billid);
					map.put("gxsj",nowtime);

					result=iu.insert(map,tablename+"_dt1");

					if(!result){
						log.writeLog("SysnPersonAttendExpInfoImpl",jo.toString());
						return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败员工编码："+workcode+"\"}";
					}
				}

			}
			
		}
		
		
		return "{\"result\":\"S\",\"msg\":\"成功\"}";
	}
}
