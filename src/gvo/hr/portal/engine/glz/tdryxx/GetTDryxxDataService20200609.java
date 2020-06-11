package gvo.hr.portal.engine.glz.tdryxx;


import com.engine.common.util.ParamUtil;
import gvo.hr.portal.util.HrUtil;
import gvo.hr.portal.util.InsertUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.HrmUserVarify;
import weaver.hrm.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取团队人员信息
 */
public class GetTDryxxDataService20200609 {
	@POST
	@Path("/getTdryxxDataByUser")
	@Produces({MediaType.TEXT_PLAIN})
	public String getData(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		//new BaseBean().writeLog("GetTDryxxDataService","getData:");
		User user= HrmUserVarify.getUser(request,response);
		Map<String,Object> params = ParamUtil.request2Map(request);
		HrUtil hrUtil=new HrUtil();
		Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
		String workcode=personBaseinfo.get("workcode");
		String recordid=personBaseinfo.get("recordid");

        RecordSet rs=new RecordSet();
        String orgcodes=hrUtil.getOrgcodes(workcode,recordid);
		String sql = "";
		String orgcode = "";
		String channel = "";
		String identitytype = "";
		try{
			orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")),"UTF-8");
			channel = URLDecoder.decode(Util.null2String(params.get("channel")),"UTF-8");
			identitytype = URLDecoder.decode(Util.null2String(params.get("identitytype")),"UTF-8");

		}catch (Exception e){

		}
		String orgcodezz = Util.null2String(params.get("orgcodezz"));
        String sfbhxjzz =  Util.null2String(params.get("sfbhxjzz"));
		//String channel = Util.null2String(params.get("channel"));
		//String identitytype = Util.null2String(params.get("identitytype"));
		String zdstart = Util.null2String(params.get("zdstart"));
		String zdend = Util.null2String(params.get("zdend"));
		String rqstart = Util.null2String(params.get("rqstart"));
		String rqend = Util.null2String(params.get("rqend"));
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		Integer startIndex=(page-1)*limit+1;
		Integer endIndex=page*limit;
		sql = "select * from (select t.*,rownum as num from (";
		sql+="  select id,workcode,name,companyname,centername,deptname,groupname,jobtitlename,channel,sequence,joblevel,identitytype,employeetype,worktype,rjtrq,syqjzrq,jtgl,shgl,birthday,age,sex,nativeplace,educationlevel,school,major,bysj,ahtc,contact," +
				"case when (select count(1) from uf_hr_baby where WORK_CODE=t.workcode)>0 then '三期人员' else '' end as ygbs,rsxxk,(select jlxx from uf_hr_personrecord where workcode='"+workcode+"' and bjlzgh=t.workcode) as ygjl from uf_hr_persondata t where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and status = '有效' and currdeptcode in ("+orgcodes+") ";

		if(!"".equals(orgcode)){
			if("false".equals(sfbhxjzz)) {
				sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=t.currdeptcode)) like Upper('%" + orgcode + "%')";
			}else{
				sql = sql + " and (Upper(t.companyname) like Upper('%" + orgcode + "%') or Upper(t.centername) like Upper('%" + orgcode + "%') or Upper(t.deptname) like Upper('%" + orgcode + "%') or Upper(t.groupname) like Upper('%" + orgcode + "%'))";

			}
		}
		if(!"".equals(orgcodezz)){
			orgcodezz = "'"+orgcodezz+"'";
			sql += " and currdeptcode in (select distinct orgcode from uf_hr_orgdata where status = '0' start with orgcode="+orgcodezz+" connect by prior orgcode = suporgcode) ";
		}
		if(!"".equals(channel)){
			sql=sql+" and t.channel = '"+channel+"'";
		}
		if(!"".equals(identitytype)){
			sql=sql+" and t.identitytype = '"+identitytype+"'";
		}
		if(!"".equals(zdstart)){
			sql=sql+" and joblevel <> '99' and to_number(joblevel)>=to_number('"+zdstart+"')";
		}
		if(!"".equals(zdend)){
			sql=sql+" and joblevel <> '99' and to_number(joblevel)<=to_number('"+zdend+"')";
		}
		if(!"".equals(rqstart)){
			sql=sql+" and t.rjtrq >='"+rqstart+"'";
		}
		if(!"".equals(rqend)){
			sql=sql+" and t.rjtrq <= '"+rqend+"'";
		}

		sql+=" order by t.companyname asc,t.centername asc,t.deptname asc,t.groupname asc) t) where num>="+startIndex+" and num<="+endIndex;


		//new BaseBean().writeLog("testaaaa tdryxx","sql:"+sql);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray=new JSONArray();
		rs.execute(sql);
		while(rs.next()) {
			try {
				JSONObject object = new JSONObject();
				object.put("workcode", Util.null2String(rs.getString("workcode")));
				object.put("name",Util.null2String(rs.getString("name")));
				object.put("centername",Util.null2String(rs.getString("centername")));
				object.put("companyname",Util.null2String(rs.getString("companyname")));
				object.put("deptname",Util.null2String(rs.getString("deptname")));
				object.put("groupname",Util.null2String(rs.getString("groupname")));
				object.put("jobtitlename",Util.null2String(rs.getString("jobtitlename")));
				object.put("channel",Util.null2String(rs.getString("channel")));
				object.put("sequence",Util.null2String(rs.getString("sequence")));
				object.put("joblevel",Util.null2String(rs.getString("joblevel")));
				object.put("identitytype",Util.null2String(rs.getString("identitytype")));
				object.put("employeetype",Util.null2String(rs.getString("employeetype")));
				object.put("worktype",Util.null2String(rs.getString("worktype")));
				object.put("rjtrq",Util.null2String(rs.getString("rjtrq")));
				object.put("syqjzrq",Util.null2String(rs.getString("syqjzrq")));
				object.put("jtgl",Util.null2String(rs.getString("jtgl")));
				object.put("shgl",Util.null2String(rs.getString("shgl")));
				object.put("birthday",Util.null2String(rs.getString("birthday")));
				object.put("age",Util.null2String(rs.getString("age")));
				object.put("sex",Util.null2String(rs.getString("sex")));
				object.put("nativeplace",Util.null2String(rs.getString("nativeplace")));
				object.put("educationlevel",Util.null2String(rs.getString("educationlevel")));
				object.put("school",Util.null2String(rs.getString("school")));
				object.put("major",Util.null2String(rs.getString("major")));
				object.put("bysj",Util.null2String(rs.getString("bysj")));
				object.put("ahtc",Util.null2String(rs.getString("ahtc")));
				object.put("contact",Util.null2String(rs.getString("contact")));
				object.put("ygbs",Util.null2String(rs.getString("ygbs")));
				object.put("rsxxk",Util.null2String(rs.getString("rsxxk")));
				object.put("ygjl",Util.null2String(rs.getString("ygjl")));

				jsonArray.put(object);
			} catch (JSONException e) {
				new BaseBean().writeLog("测试",e);
			}
		}
		String totalNum = null;
		sql="  select  count(1) as totalnum from uf_hr_persondata t where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and t.status = '有效' and  currdeptcode in ("+orgcodes+") ";

		if(!"".equals(orgcode)){
			if("false".equals(sfbhxjzz)) {
				sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=t.currdeptcode)) like Upper('%" + orgcode + "%')";
			}else{
				sql = sql + " and (Upper(t.companyname) like Upper('%" + orgcode + "%') or Upper(t.centername) like Upper('%" + orgcode + "%') or Upper(t.deptname) like Upper('%" + orgcode + "%') or Upper(t.groupname) like Upper('%" + orgcode + "%'))";

			}
		}
		if(!"".equals(orgcodezz)){
			sql += " and currdeptcode in ("+orgcodezz+") ";
		}
		if(!"".equals(channel)){
			sql=sql+" and t.channel = '"+channel+"'";
		}
		if(!"".equals(identitytype)){
			sql=sql+" and t.identitytype = '"+identitytype+"'";
		}
		if(!"".equals(zdstart)){
			sql=sql+" and joblevel <> '99' and to_number(joblevel)>=to_number('"+zdstart+"')";
		}
		if(!"".equals(zdend)){
			sql=sql+" and joblevel <> '99' and to_number(joblevel)<=to_number('"+zdend+"')";
		}
		if(!"".equals(rqstart)){
			sql=sql+" and t.rjtrq >='"+rqstart+"'";
		}
		if(!"".equals(rqend)){
			sql=sql+" and t.rjtrq <= '"+rqend+"'";
		}
		//new BaseBean().writeLog("testaaaa","sql:"+sql);
		rs.execute(sql);
		if(rs.next()) {
			totalNum=Util.null2String(rs.getString("totalnum"));
		}
        
		try {
			jsonObject.put("code",0);
			jsonObject.put("msg","success");
			jsonObject.put("count",totalNum);
			jsonObject.put("data",jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	@POST
	@Path("/updateygjl")
	@Produces({MediaType.TEXT_PLAIN})
	public String updateYgjl(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		new BaseBean().writeLog("GetTDryxxDataService","updateYgjl:");
		User user= HrmUserVarify.getUser(request,response);
		RecordSet rs = new RecordSet();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HrUtil hrUtil=new HrUtil();
		InsertUtil iu = new InsertUtil();
		Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
		String workcode=personBaseinfo.get("workcode");
		String jlxx = Util.null2String(request.getParameter("jlxx"));
		String yggh = Util.null2String(request.getParameter("yggh"));
		Map<String,String> map = new HashMap<String,String>();
		map.put("workcode",workcode);
		map.put("bjlzgh",yggh);
		map.put("jlxx",jlxx);
		map.put("gxrq",sf.format(new Date()));
		String billid = "";
		String sql = "select id from uf_hr_personrecord where workcode='"+workcode+"' and bjlzgh='"+yggh+"'";
		rs.execute(sql);
		if(rs.next()){
			billid = Util.null2String(rs.getString("id"));
		}
		if("".equals(billid)){
			iu.insert(map,"uf_hr_personrecord");
		}else{
			iu.updateGen(map,"uf_hr_personrecord","id",billid);
		}
		return "S";
	}

	//导出报表
	@GET
	@Path("/OutExcel")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response weaReportOutExcel(@Context HttpServletRequest request, @Context HttpServletResponse response){
		try {
			User user = HrmUserVarify.getUser(request, response);
			InputStream input = OutExcel(request,response);
			String filename="团队人员信息";
			filename = java.net.URLEncoder.encode(filename, "UTF-8");
			filename = StringUtils.replace(filename, "/", "");
			filename = StringUtils.replace(filename, "%2F", "");
			filename = StringUtils.replace(filename, "+", "%20");
			filename = Util.formatMultiLang(filename, user.getLanguage()+"") + ".xls";

			return Response
					.ok(input)
					.header("Content-disposition", "attachment;filename=" + filename)
					.header("Cache-Control", "no-cache").build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public InputStream OutExcel(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params= ParamUtil.request2Map(request);

		RecordSet rs = new RecordSet();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setColor(HSSFFont.COLOR_NORMAL);
		HSSFCellStyle sheetStyle = wb.createCellStyle();
		sheetStyle.setAlignment(HorizontalAlignment.CENTER);
		sheetStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		sheetStyle.setFont(font);
		sheetStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		sheetStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());

		//sheetStyle.setWrapText(true);
		sheetStyle.setBorderBottom(BorderStyle.THIN);
		sheetStyle.setBorderRight(BorderStyle.THIN);
		sheetStyle.setBorderTop(BorderStyle.THIN);
		sheetStyle.setBorderLeft(BorderStyle.THIN);
		HSSFCellStyle sheetStyle2 = wb.createCellStyle();
		sheetStyle2.setAlignment(HorizontalAlignment.CENTER);
		sheetStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		sheetStyle2.setFont(font);

		sheetStyle2.setBorderBottom(BorderStyle.THIN);
		sheetStyle2.setBorderRight(BorderStyle.THIN);
		sheetStyle2.setBorderTop(BorderStyle.THIN);
		sheetStyle2.setBorderLeft(BorderStyle.THIN);

		try{
			//创建标题
			//因为这里是前端固定的表头,所以后台手动添加
			HSSFRow row0=sheet.createRow((short)0);

			cell=row0.createCell(0);
			cell.setCellValue("工号");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(1);
			cell.setCellValue("姓名");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(2);
			cell.setCellValue("公司");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(3);
			cell.setCellValue("中心");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(4);
			cell.setCellValue("部");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(5);
			cell.setCellValue("组");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(6);
			cell.setCellValue("岗位名称");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(7);
			cell.setCellValue("通道");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(8);
			cell.setCellValue("序列");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(9);
			cell.setCellValue("职等");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(10);
			cell.setCellValue("身份类别");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(11);
			cell.setCellValue("员工类别");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(12);
			cell.setCellValue("工时类别");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(13);
			cell.setCellValue("入集团日期");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(14);
			cell.setCellValue("试用期截止日");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(15);
			cell.setCellValue("集团工龄");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(16);
			cell.setCellValue("社会工龄");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(17);
			cell.setCellValue("出生日期");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(18);
			cell.setCellValue("年龄");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(19);
			cell.setCellValue("性别");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(20);
			cell.setCellValue("籍贯省");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(21);
			cell.setCellValue("学历");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(22);
			cell.setCellValue("学校");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(23);
			cell.setCellValue("专业");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(24);
			cell.setCellValue("毕业时间");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(25);
			cell.setCellValue("爱好特长");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(26);
			cell.setCellValue("联系方式");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(27);
			cell.setCellValue("员工标识");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(28);
			cell.setCellValue("人事信息卡");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(29);
			cell.setCellValue("员工记录");
			cell.setCellStyle(sheetStyle);
//			CellRangeAddress aa = new CellRangeAddress(0, 1, 0, 0);
//			sheet.addMergedRegion(aa);


			User user= HrmUserVarify.getUser(request,response);
			HrUtil hrUtil=new HrUtil();
			Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
			String workcode=personBaseinfo.get("workcode");
			String recordid=personBaseinfo.get("recordid");
			String sql = "";
			String orgcodes=hrUtil.getOrgcodes(workcode,recordid);
			String orgcodezz = Util.null2String(params.get("orgcodezz"));
			String orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")),"UTF-8");
			String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
			String channel = URLDecoder.decode(Util.null2String(params.get("channel")),"UTF-8");
			String identitytype = URLDecoder.decode(Util.null2String(params.get("identitytype")),"UTF-8");
			String zdstart = Util.null2String(params.get("zdstart"));
			String zdend = Util.null2String(params.get("zdend"));
			String rqstart = Util.null2String(params.get("rqstart"));
			String rqend = Util.null2String(params.get("rqend"));

			sql = "select * from (select t.*,rownum as num from (";
			sql+="  select id,workcode,name,companyname,centername,deptname,groupname,jobtitlename,channel,sequence,joblevel,identitytype,employeetype,worktype,rjtrq,syqjzrq,jtgl,shgl,birthday,age,sex,nativeplace,educationlevel,school,major,bysj,ahtc,contact," +
					"case when (select count(1) from uf_hr_baby where WORK_CODE=t.workcode)>0 then '三期人员' else '' end as ygbs,rsxxk,(select jlxx from uf_hr_personrecord where workcode='"+workcode+"' and bjlzgh=t.workcode) as ygjl from uf_hr_persondata t where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and status = '有效' and currdeptcode in ("+orgcodes+") ";


			if(!"".equals(orgcode)){
				if("false".equals(sfbhxjzz)) {
					sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=t.currdeptcode)) like Upper('%" + orgcode + "%')";
				}else{
					sql = sql + " and (Upper(t.companyname) like Upper('%" + orgcode + "%') or Upper(t.centername) like Upper('%" + orgcode + "%') or Upper(t.deptname) like Upper('%" + orgcode + "%') or Upper(t.groupname) like Upper('%" + orgcode + "%'))";

				}
			}
			if(!"".equals(orgcodezz)){
				orgcodezz = "'"+orgcodezz+"'";
				sql += " and currdeptcode in (select distinct orgcode from uf_hr_orgdata where status = '0' start with orgcode="+orgcodezz+" connect by prior orgcode = suporgcode) ";
			}
			if(!"".equals(channel)){
				sql=sql+" and t.channel = '"+channel+"'";
			}
			if(!"".equals(identitytype)){
				sql=sql+" and t.identitytype = '"+identitytype+"'";
			}
			if(!"".equals(zdstart)){
				sql=sql+" and joblevel <> '99' and to_number(joblevel)>=to_number('"+zdstart+"')";
			}
			if(!"".equals(zdend)){
				sql=sql+" and joblevel <> '99' and to_number(joblevel)<=to_number('"+zdend+"')";
			}
			if(!"".equals(rqstart)){
				sql=sql+" and t.rjtrq >='"+rqstart+"'";
			}
			if(!"".equals(rqend)){
				sql=sql+" and t.rjtrq <= '"+rqend+"'";
			}
			sql+=" order by t.companyname asc,t.centername asc,t.deptname asc,t.groupname asc) t) ";
			rs.execute(sql);
			int indexrow = 1;
			while(rs.next()){

				HSSFRow rowdt=sheet.createRow(indexrow);
				cell=rowdt.createCell(0);
				cell.setCellValue(Util.null2String(rs.getString("workcode")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(1);
				cell.setCellValue(Util.null2String(rs.getString("name")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(2);
				cell.setCellValue(Util.null2String(rs.getString("companyname")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(3);
				cell.setCellValue(Util.null2String(rs.getString("centername")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(4);
				cell.setCellValue(Util.null2String(rs.getString("deptname")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(5);
				cell.setCellValue(Util.null2String(rs.getString("groupname")));
				cell.setCellStyle(sheetStyle2);


				cell=rowdt.createCell(6);
				cell.setCellValue(Util.null2String(rs.getString("jobtitlename")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(7);
				cell.setCellValue(Util.null2String(rs.getString("channel")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(8);
				cell.setCellValue(Util.null2String(rs.getString("sequence")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(9);
				cell.setCellValue(Util.null2String(rs.getString("joblevel")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(10);
				cell.setCellValue(Util.null2String(rs.getString("identitytype")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(11);
				cell.setCellValue(Util.null2String(rs.getString("employeetype")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(12);
				cell.setCellValue(Util.null2String(rs.getString("worktype")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(13);
				cell.setCellValue(Util.null2String(rs.getString("rjtrq")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(14);
				cell.setCellValue(Util.null2String(rs.getString("syqjzrq")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(15);
				cell.setCellValue(Util.null2String(rs.getString("jtgl")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(16);
				cell.setCellValue(Util.null2String(rs.getString("shgl")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(17);
				cell.setCellValue(Util.null2String(rs.getString("birthday")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(18);
				cell.setCellValue(Util.null2String(rs.getString("age")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(19);
				cell.setCellValue(Util.null2String(rs.getString("sex")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(20);
				cell.setCellValue(Util.null2String(rs.getString("nativeplace")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(21);
				cell.setCellValue(Util.null2String(rs.getString("educationlevel")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(22);
				cell.setCellValue(Util.null2String(rs.getString("school")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(23);
				cell.setCellValue(Util.null2String(rs.getString("major")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(24);
				cell.setCellValue(Util.null2String(rs.getString("bysj")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(25);
				cell.setCellValue(Util.null2String(rs.getString("ahtc")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(26);
				cell.setCellValue(Util.null2String(rs.getString("contact")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(27);
				cell.setCellValue(Util.null2String(rs.getString("ygbs")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(28);
				cell.setCellValue(Util.null2String(rs.getString("rsxxk")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(29);
				cell.setCellValue(Util.null2String(rs.getString("ygjl")));
				cell.setCellStyle(sheetStyle2);
				indexrow++;
			}



			//将workbook转换为流的形式
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			InputStream input = new ByteArrayInputStream(os.toByteArray());
			return input;

		}catch (Exception e){
			new BaseBean().writeLog("导出excel报错,错误信息为:"+e.getMessage());
		}


		return null;
	}

}
