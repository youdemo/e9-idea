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
 *@Description: 请休假情况
 *@version:
* @author: jianyong.tang
* @date: 2020年4月14日 上午11：30
 */
public class SysnPersonLeaveInfoImpl {
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
		String tablename = "uf_hr_leaveinfo";
		String sql = "";
		String nowtime = "";
		String workcode = "";//工号
		String yxjhj = "";//有薪假合计
		String dnxjzss = "";//当年休假总时数
		JSONArray data = new JSONArray(json);
		boolean result = true;
		for(int i=0;i<data.length();i++) {
			JSONObject jo = data.getJSONObject(i);
			workcode = jo.getString("workcode");
			yxjhj = jo.getString("hxjhj");
			dnxjzss = jo.getString("dnxjzss");
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
			map.put("yxjhj",yxjhj);
			map.put("dnxjzss",dnxjzss);
			map.put("yxss",jo.getString("yxss"));
			map.put("syss",jo.getString("syss"));
			map.put("gxsj",nowtime);
			if("".equals(billid)){
				result=iu.insert(map,tablename);
			}else{
				result=iu.updateGen(map,tablename,"id",billid);
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
			JSONArray months = jo.getJSONArray("months");
			for(int j=0;j<months.length();j++) {
				JSONObject dtjo = months.getJSONObject(j);
				String tbyf = dtjo.getString("month");
				sql = "delete from "+tablename+"_dt1 where mainid="+billid+"and tbyf='"+tbyf+"'";
				rs.execute(sql);
				JSONArray datas = dtjo.getJSONArray("datas");
				for(int t=0;t<datas.length();t++) {
					JSONObject datajo = datas.getJSONObject(t);
					String leavetype = datajo.getString("leavetype");
					String leavedate = datajo.getString("leavedate");
					String leavetimes = datajo.getString("leavetimes");
					String year = "";
					String month = "";
					if(!"".equals(tbyf)){
						year = tbyf.substring(0,4);
						month = tbyf.substring(5,7);
					}
					map = new HashMap<String,String>();
					map.put("tbyf",tbyf);//日期
					map.put("leavetype",leavetype);//请假类别
					map.put("leavedate",leavedate);//请假日期
					map.put("leavetimes",leavetimes);//请假时长
					map.put("nf",year);//年份
					map.put("yf",month);//月份
					map.put("mainid",billid);
					map.put("gxsj",nowtime);

					result=iu.insert(map,tablename+"_dt1");

					if(!result){
						log.writeLog("SysnPersonLeaveInfoImpl",jo.toString());
						return "{\"result\":\"E\",\"msg\":\"数据同步失败 当前失败员工编码："+workcode+"\"}";
					}
				}

			}
			
		}
		
		
		return "{\"result\":\"S\",\"msg\":\"成功\"}";
	}
}
