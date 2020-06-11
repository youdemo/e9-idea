package gvo.hr.portal.engine.glz.jc;


import com.engine.common.util.ParamUtil;
import gvo.hr.portal.util.HrUtil;
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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Map;

public class GetTDJCDataService {
	@GET
	@Path("/getJCDataByUser")
	@Produces({MediaType.TEXT_PLAIN})
	public String getAllJCData(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		User user= HrmUserVarify.getUser(request,response);
		HrUtil hrUtil=new HrUtil();
		Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
		String workcode=personBaseinfo.get("workcode");
		String recordid=personBaseinfo.get("recordid");
		RecordSet rs = new RecordSet();
		String orgcodes=null;
		orgcodes = hrUtil.getOrgcodes(workcode, recordid);
		String orgcode = Util.null2String(request.getParameter("orgcode"));
		workcode = Util.null2String(request.getParameter("workcode"));
		String sfbhxjzz = Util.null2String(request.getParameter("sfbhxjzz"));
		try {
			orgcode=URLDecoder.decode(orgcode,"UTF-8");
			workcode=URLDecoder.decode(workcode,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String year=Util.null2String(request.getParameter("year"));
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer limit = Integer.valueOf(request.getParameter("limit"));
		String downloadFlag=Util.null2String(request.getParameter("downloadFlag"));//是否下载功能标志
		Integer startIndex=(page-1)*limit+1;
		Integer endIndex=page*limit;
		String sql = "select * from (select t.*,rownum as num from (";
		sql+="  select  p.id,p.workcode,p.name,p.companyname,p.centername,p.centercode,p.deptname,p.deptcode,p.groupname,p.groupcode,p.jobtitlename,p.currdeptcode,j.year,j.jcrq,j.jcjb,j.type,j.description  from UF_HR_TDJCXX j,uf_hr_persondata p where j.workcode=p.workcode and p.status = '有效' and (p.employeetype in ('正式员工','实习员工') or (p.employeetype='劳务人员' and p.joblevel <>'99')) and  currdeptcode in ("+orgcodes+") ";
		if(!"".equals(year)){
			sql=sql+" and year='"+year+"'";
		}else{
			Calendar date = Calendar.getInstance();
			year=String.valueOf(date.get(Calendar.YEAR));
			sql=sql+" and year='"+year+"'";
		}
		if(!"".equals(workcode)){
			sql=sql+" and p.name like '%"+workcode+"%'";
		}
		if(!"".equals(orgcode)){
			if("false".equals(sfbhxjzz)){
				sql=sql+" and Upper((select orgname from uf_hr_orgdata where orgcode=p.currdeptcode)) like Upper('%" + orgcode + "%')";
			}else{
				sql = sql + " and (Upper(p.companyname) like Upper('%" + orgcode + "%') or Upper(p.centername) like Upper('%" + orgcode + "%') or Upper(p.deptname) like Upper('%" + orgcode + "%') or Upper(p.groupname) like Upper('%" + orgcode + "%'))";
			}
		}
		sql+="  order by p.companyname,p.centername,p.deptname,p.groupname asc) t) where num>="+startIndex+" and num<="+endIndex;

		new BaseBean().writeLog("GetTDJCDataService","sql:"+sql);
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
				object.put("year",Util.null2String(rs.getString("year")));
				object.put("jcrq",Util.null2String(rs.getString("jcrq")));
				object.put("jcjb",Util.null2String(rs.getString("jcjb")));
				object.put("type",Util.null2String(rs.getString("type")));
				object.put("description",Util.null2String(rs.getString("description")));
				jsonArray.put(object);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		String totalNum = null;
		sql="  select  count(1) as totalnum from UF_HR_TDJCXX j,uf_hr_persondata p where j.workcode=p.workcode and p.status = '有效' and (p.employeetype in ('正式员工','实习员工') or (p.employeetype='劳务人员' and p.joblevel <>'99')) and  currdeptcode in ("+orgcodes+") ";
		if(!"".equals(year)){
			sql=sql+" and year='"+year+"'";
		}else{
			Calendar date = Calendar.getInstance();
			year=String.valueOf(date.get(Calendar.YEAR));
			sql=sql+" and year='"+year+"'";
		}
		if(!"".equals(workcode)){
			sql=sql+" and p.name like '%"+workcode+"%'";
		}
		if(!"".equals(orgcode)){
			if("false".equals(sfbhxjzz)){
				sql=sql+" and Upper((select orgname from uf_hr_orgdata where orgcode=p.currdeptcode)) like Upper('%" + orgcode + "%')";
			}else{
				sql = sql + " and (Upper(p.companyname) like Upper('%" + orgcode + "%') or Upper(p.centername) like Upper('%" + orgcode + "%') or Upper(p.deptname) like Upper('%" + orgcode + "%') or Upper(p.groupname) like Upper('%" + orgcode + "%'))";
			}
		}
		sql = sql + " order by p.companyname,p.centername,p.deptname,p.groupname asc";
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

	//导出报表
	@GET
	@Path("/OutExcel")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response weaReportOutExcel(@Context HttpServletRequest request, @Context HttpServletResponse response){
		try {
			User user = HrmUserVarify.getUser(request, response);
			InputStream input = OutExcel(request,response);
			String filename="团队奖惩";
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

//            cell=row0.createCell(6);
//            cell.setCellValue("携程预定事由");
//            cell.setCellStyle(sheetStyle);

			cell=row0.createCell(7);
			cell.setCellValue("奖惩年度");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(8);
			cell.setCellValue("奖惩日期");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(9);
			cell.setCellValue("奖惩级别");
			cell.setCellStyle(sheetStyle);

			cell=row0.createCell(10);
			cell.setCellValue("奖惩类型");
			cell.setCellStyle(sheetStyle);

//            cell=row0.createCell(11);
//            cell.setCellValue("改签费");
//            cell.setCellStyle(sheetStyle);

			cell=row0.createCell(11);
			cell.setCellValue("事件描述");
			cell.setCellStyle(sheetStyle);

			User user= HrmUserVarify.getUser(request,response);
			HrUtil hrUtil=new HrUtil();
			Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
			String workcode=personBaseinfo.get("workcode");
			String recordid=personBaseinfo.get("recordid");

			String orgcodes=null;
			orgcodes = hrUtil.getOrgcodes(workcode, recordid);
			String orgcode = Util.null2String(request.getParameter("orgcode"));
			workcode = Util.null2String(request.getParameter("workcode"));
			String sfbhxjzz = Util.null2String(request.getParameter("sfbhxjzz"));
			String year=Util.null2String(request.getParameter("year"));
			try {
				orgcode=URLDecoder.decode(orgcode,"UTF-8");
				workcode=URLDecoder.decode(workcode,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String sql = "select * from (select t.*,rownum as num from (";
			sql+="  select  p.id,p.workcode,p.name,p.companyname,p.centername,p.centercode,p.deptname,p.deptcode,p.groupname,p.groupcode,p.jobtitlename,p.currdeptcode,j.year,j.jcrq,j.jcjb,j.type,j.description  from UF_HR_TDJCXX j,uf_hr_persondata p where j.workcode=p.workcode and p.status = '有效' and (p.employeetype in ('正式员工','实习员工') or (p.employeetype='劳务人员' and p.joblevel <>'99')) and  currdeptcode in ("+orgcodes+") ";
//			if(!"".equals(year)){
//				sql=sql+" and year='"+year+"'";
//			}else{
//				Calendar date = Calendar.getInstance();
//				year=String.valueOf(date.get(Calendar.YEAR));
//				sql=sql+" and year='"+year+"'";
//			}
			if(!"".equals(workcode)){
				sql=sql+" and p.name like '%"+workcode+"%'";
			}
			if(!"".equals(orgcode)){
				if("false".equals(sfbhxjzz)){
					sql=sql+" and Upper((select orgname from uf_hr_orgdata where orgcode=p.currdeptcode)) like Upper('%" + orgcode + "%')";
				}else{
					sql = sql + " and (Upper(p.companyname) like Upper('%" + orgcode + "%') or Upper(p.centername) like Upper('%" + orgcode + "%') or Upper(p.deptname) like Upper('%" + orgcode + "%') or Upper(p.groupname) like Upper('%" + orgcode + "%'))";
				}
			}
			sql+="   order by p.companyname,p.centername,p.deptname,p.groupname asc) t)  order by companyname,centername,deptname,groupname asc";
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
				cell.setCellValue(Util.null2String(rs.getString("centername")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(3);
				cell.setCellValue(Util.null2String(rs.getString("companyname")));
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
				cell.setCellValue(Util.null2String(rs.getString("year")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(8);
				cell.setCellValue(Util.null2String(rs.getString("jcrq")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(9);
				cell.setCellValue(Util.null2String(rs.getString("jcjb")));
				cell.setCellStyle(sheetStyle2);

				cell=rowdt.createCell(10);
				cell.setCellValue(Util.null2String(rs.getString("type")));
				cell.setCellStyle(sheetStyle2);
				cell=rowdt.createCell(11);
				cell.setCellValue(Util.null2String(rs.getString("description")));
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
