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
 *@Description: 排班信息同步
* @version: 
* @author: jianyong.tang
* @date: 2020年4月14日 上午10：50
 */
public class SysnPersonSchedulingImpl {
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
		String tablename = "uf_hr_personpbxx";
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
			if("".equals(billid)){
				result=iu.insert(map,tablename);
			}
			if(!result){
				log.writeLog("SysnPersonSchedulingImpl",jo.toString());
				return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败员工编码："+workcode+"\"}";
			}
			sql = "select id from "+tablename+" where workcode='"+workcode+"' ";
			rs.execute(sql);
			if(rs.next()){
				billid = Util.null2String(rs.getString("id"));
			}
			//处理明细班别信息
			JSONArray dtdatas = jo.getJSONArray("datas");
			for(int j=0;j<dtdatas.length();j++) {
				JSONObject dtjo = dtdatas.getJSONObject(j);
				String rq = dtjo.getString("date");
				String type = dtjo.getString("type");
				String year = "";
				String month = "";
				if(!"".equals(rq)){
					year = rq.substring(0,4);
					month = rq.substring(5,7);
				}
				map = new HashMap<String,String>();
				map.put("rq",rq);//日期
				map.put("type",type);//班别
				map.put("year",year);//年份
				map.put("month",month);//月份
				map.put("mainid",billid);
				map.put("gxsj",nowtime);
				String dtid = "";
				sql ="select id from "+tablename+"_dt1 where mainid="+billid+" and rq='"+rq+"'";
				rs.execute(sql);
				if(rs.next()){
					dtid = Util.null2String(rs.getString("id"));
				}
				if("".equals(dtid)){
					result=iu.insert(map,tablename+"_dt1");
				}else{
					result=iu.updateGen(map,tablename+"_dt1","id",dtid);
				}
				if(!result){
					log.writeLog("SysnPersonSchedulingImpl",jo.toString());
					return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败员工编码："+workcode+"\"}";
				}
			}
			
		}
		
		
		return "{\"result\":\"S\",\"msg\":\"成功\"}";
	}
}
