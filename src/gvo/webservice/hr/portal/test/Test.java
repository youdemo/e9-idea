package gvo.webservice.hr.portal.test;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test {
//	public static void main(String[] args) throws Exception {
//		String aaa = "100014,100759,100043,100745,100015,100518,100748,100750,100513,100516,100760,100749,100041,100042,100001,100510,100517,100519,100037,100044,100511,100046,100038,100045,100016,100512,100514,100758";
//		System.out.println("'"+aaa.replace(",","','")+"'");
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nowdate = "2020-04";
//		String aa = nowdate.substring(0,4);
//		System.out.println(aa);
//		System.out.println(nowdate.substring(5,7));
//		System.out.println(URLDecoder.decode("%7B\"showNoReply\"%3A\"-1\"%2C\"isExclude\"%3A\"1\"%2C\"perpage\"%3A\"6\"%2C\"orderby\"%3A\"%20operatedate%20%20desc%20%2C%20operatetime%20desc%20\"%2C\"isall\"%3A\"1\"%2C\"flowCount\"%3A\"2\"%2C\"showView\"%3A\"\"%2C\"showForward\"%3A\"-1\"%2C\"flowids\"%3A%5B\"1\"%2C\"441\"%2C\"83763\"%2C\"84762\"%2C\"84764\"%2C\"9401\"%5D%2C\"showCopy\"%3A\"0\"%2C\"showTimeout\"%3A\"-1\"%2C\"viewType\"%3A\"1\"%2C\"showCY\"%3A\"-1\"%2C\"typeids\"%3A%5B\"1\"%2C\"2\"%2C\"3\"%2C\"641\"%5D%2C\"fromhp\"%3A\"1\"%2C\"nodeCount\"%3A24%2C\"hpwhere\"%3A\"\"%2C\"showReject\"%3A\"-1\"%2C\"typeCount\"%3A4%2C\"nodeids\"%3A%5B\"1\"%2C\"1406\"%2C\"1407\"%2C\"1408\"%2C\"2\"%2C\"3\"%2C\"432098\"%2C\"432099\"%2C\"437110\"%2C\"437111\"%2C\"437112\"%2C\"437113\"%2C\"437114\"%2C\"437115\"%2C\"437117\"%2C\"437118\"%2C\"437119\"%2C\"437120\"%2C\"437121\"%2C\"58123\"%2C\"58124\"%2C\"631602\"%2C\"631603\"%2C\"631604\"%5D%7D","UTF-8"));
//		String uploadBuffer="";
//		FileInputStream fi = new FileInputStream(new File("D:\\test\\1234.jpg"));
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		byte[] buffer = new byte[1024];
//		int count = 0;
//		while((count = fi.read(buffer)) >= 0){
//			baos.write(buffer, 0, count);
//		}
//		uploadBuffer = new String(Base64.encode(baos.toByteArray()));
//		baos.close();
//		System.out.println(uploadBuffer);

//	}
    //组织同步信息
	public static void main(String[] args) throws Exception {
		String aaa="\\u2162";
		System.out.println(aaa);
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("workcode", "V1000001");
		jo.put("month", "2020-05");
		jo.put("psjb", "60");//平时加班
		jo.put("zmjb", "60");//周末加班
		jo.put("jjrjb", "60");//节假日加班
		jo.put("jbhj", "180");//加班合计
		jo.put("bzjbsxzss", "200");//标准加班受限总时数
		jo.put("dzhjbsxzss", "200");//调整后加班受限总时数
		jo.put("bzbfb", "50");//标准百分比
		jo.put("dzhbfb","50");//调整后百分比
		String aa[]= new String[2];
		aa[0]="已";
		aa[1]="已文档";
		jo.put("aaa",aa);//调整后百分比
		ja.put(jo);
		ja.put(jo);
		ja.put("123");
		System.out.println(ja.toString());

	}
	//人员信息
//	public static void main(String[] args) throws Exception {
//		JSONObject json = new JSONObject();
//		JSONArray  maininfo = new JSONArray();
//		JSONArray  jobinfo = new JSONArray();
//		JSONArray  changeinfo = new JSONArray();
//		json.put("maininfo", maininfo);
//		json.put("jobinfo", jobinfo);
//		json.put("changeinfo", changeinfo);
//		JSONObject jo = new JSONObject();
//		jo.put("workcode", "工号");
//		jo.put("recordid", "员工记录号");
//		jo.put("changedate", "调动日期");
//		jo.put("changetype", "异动类型");
//		jo.put("beforedept", "调动前部门");
//		jo.put("afterdept", "调动后部门");
//		changeinfo.put(jo);
//		changeinfo.put(jo);
//		jo = new JSONObject();
//		jo.put("workcode","工号");
//		jo.put("recordid","员工记录号");
//		jo.put("status","状态");
//		jo.put("companyname","公司");
//		jo.put("companycode","公司编码");
//		jo.put("centername","中心");
//		jo.put("centercode","中心编码");
//		jo.put("deptname","部门");
//		jo.put("deptcode","部门编码");
//		jo.put("groupname","组");
//		jo.put("groupcode","组编码");
//		jo.put("jobtitlename","岗位");
//		jo.put("jobtitlecode","岗位代码");
//		jo.put("channel","通道");
//		jo.put("sequence","序列");
//		jo.put("joblevel","职等");
//		jobinfo.put(jo);
//		jobinfo.put(jo);
//		jo = new JSONObject();jo.put("workcode","工号");
//		jo.put("recordid","员工记录号");
//		jo.put("name","姓名");
//		jo.put("status","状态");
//		jo.put("companyname","公司");
//		jo.put("companycode","公司编码");
//		jo.put("centername","中心");
//		jo.put("centercode","中心编码");
//		jo.put("deptname","部门");
//		jo.put("deptcode","部门编码");
//		jo.put("groupname","组");
//		jo.put("groupcode","组编码");
//		jo.put("jobtitlename","岗位");
//		jo.put("jobtitlecode","岗位代码");
//		jo.put("channel","通道");
//		jo.put("sequence","序列");
//		jo.put("joblevel","职等");
//		jo.put("identitytype","身份类别");
//		jo.put("employeetype","员工类别");
//		jo.put("worktype","工时类别");
//		jo.put("rjtrq","入集团日期");
//		jo.put("syqjzrq","试用期截止日期");
//		jo.put("jtgl","集团工龄");
//		jo.put("shgl","社会工龄");
//		jo.put("birthday","出生日期");
//		jo.put("age","年龄");
//		jo.put("sex","性别");
//		jo.put("nativeplace","籍贯省");
//		jo.put("educationlevel","学历");
//		jo.put("school","学校");
//		jo.put("major","专业");
//		jo.put("bysj","毕业时间");
//		jo.put("contact","联系方式");
//		jo.put("rsxxk","人事信息卡");
//		jo.put("joindate","入职日期");
//		jo.put("leavedate","离职日期");
//		maininfo.put(jo);
//		maininfo.put(jo);
//		System.out.println(json.toString());
//
//
//	}
//	//员工照片
//	public static void main(String[] args) throws JSONException {
//		JSONArray  ja = new JSONArray();
//		JSONObject jo = new JSONObject();
//		jo.put("workcode","工号");
//		jo.put("photo","照片(base64位字符串)");
//		ja.put(jo);
//		ja.put(jo);
//		System.out.println(ja.toString());
//
//	}
	
	//员工照片
//		public static void main(String[] args) throws JSONException {
//			JSONArray  ja = new JSONArray();
//			JSONObject jo = new JSONObject();
//			jo.put("workcode","工号");
//			jo.put("recordid","员工记录号");
//			jo.put("jobname","认证岗位");
//			jo.put("joblevel","岗位等级");
//			jo.put("jobtype","岗位属性");
//			jo.put("jobstarttime","岗位认证时间");
//			jo.put("jobendtime","岗位认证失效日期");
//			jo.put("jobtimes","认证时长");
//			jo.put("skillname1","技能名称1");
//			jo.put("skilllevel1","技能等级1");
//			jo.put("skillstarttime1","技能认证时间1");
//			jo.put("skillendtime1","技能有效截止时间1");
//			jo.put("skillname2","技能名称2");
//			jo.put("skilllevel2","技能等级2");
//			jo.put("skillstarttime2","技能认证时间2");
//			jo.put("skillendtime2","技能有效截止时间2");
//			ja.put(jo);
//			ja.put(jo);
//			System.out.println(ja.toString());
//
//		}
	//排班
//	public static void main(String[] args) throws JSONException {
//		JSONArray  jarr = new JSONArray();
//		JSONObject json = new JSONObject();
//		JSONArray  ja = new JSONArray();
//		JSONObject jo = new JSONObject();
//		json.put("workcode","工号");
//		json.put("datas", ja);
//		jo.put("date","日期");
//		jo.put("type","班别");
//		ja.put(jo);
//		ja.put(jo);
//		jarr.put(json);
//		jarr.put(json);
//		System.out.println(jarr.toString());
//
//	}
	//请休假情况
//	public static void main(String[] args) throws JSONException {
//		JSONArray  jarr = new JSONArray();
//		JSONObject json = new JSONObject();
//		JSONArray  ja = new JSONArray();
//		JSONObject jo = new JSONObject();
//		jo.put("workcode","工号");
//		jo.put("months", ja);
//		json.put("month","月份");
//		JSONArray  jaa = new JSONArray();
//		json.put("datas",jaa);
//		JSONObject joo = new JSONObject();
//		joo.put("belongdate","考勤归属日期");
//		joo.put("bcmc","班次名称");
//		joo.put("bcmc","班次");
//		joo.put("type","异常类型");
//
//		jaa.put(joo);
//		jaa.put(joo);
//		ja.put(json);
//		ja.put(json);
//		jarr.put(jo);
//		jarr.put(jo);
//		System.out.println(jarr.toString());
//
//	}
	
//	public static void main(String[] args) throws JSONException {
//	JSONArray  ja = new JSONArray();
//	JSONObject jo = new JSONObject();
//	jo.put("workcode","工号");
//	jo.put("year","奖惩年度");
//	jo.put("date","奖惩日期");
//	jo.put("level","奖惩级别");
//	jo.put("type","奖惩类型");
//	jo.put("desc","事件描述");
//	jo.put("status","状态");
//	jo.put("keyid","主键");
//	ja.put(jo);
//	ja.put(jo);
//	System.out.println(ja.toString());
//
//}

}
