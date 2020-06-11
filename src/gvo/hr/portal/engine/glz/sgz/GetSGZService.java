package gvo.hr.portal.engine.glz.sgz;

import com.engine.common.util.ParamUtil;
import gvo.hr.portal.util.HrUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author
 * @date 2020/5/6 0006 - 下午 3:55
 * where to_date(JOBENDTIME,'yyyy-mm-dd')>sysdate
 */

public class GetSGZService {
    @GET
    @Path("/getSGZData")
    @Produces({MediaType.TEXT_PLAIN})
    public String getSGZData(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        User user= HrmUserVarify.getUser(request,response);
        HrUtil hrUtil=new HrUtil();
        Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode=personBaseinfo.get("workcode");
        String recordid=personBaseinfo.get("recordid");
        String orgcodes=null;
        orgcodes = hrUtil.getOrgcodes(workcode, recordid);

        String orgcode = Util.null2String(request.getParameter("orgcode"));
        String sfbhxjzz= Util.null2String(request.getParameter("sfbhxjzz"));
        try {
            orgcode=URLDecoder.decode(orgcode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Integer startIndex=(page-1)*limit+1;
        Integer endIndex=page*limit;
        String sql="select * from(select aworkcode,jobname,joblevel,jobtype,jobstarttime,jobendtime,skillname1,skilllevel1,skillstarttime1,skillendtime1,skillname2,skilllevel2,skillstarttime2,skillendtime2,name,companyname,centername,deptname,groupname,jobtitlename,rjtrq,ROWNUM rn from" +
                "(select a.id,b.workcode aworkcode,a.jobname,a.joblevel,a.jobtype,a.jobstarttime,a.jobendtime,a.skillname1,a.skilllevel1,a.skillstarttime1,a.skillendtime1,a.skillname2,a.skilllevel2,a.skillstarttime2,a.skillendtime2,b.name,b.companyname,b.centername,b.deptname,b.groupname,b.jobtitlename,b.rjtrq,b.currdeptcode from ((select * from UF_HR_PERSONCERT ) a right join (select * from UF_HR_PERSONDATA where currdeptcode in ("+orgcodes+") and status='有效' and sequence='生产' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) b on a.workcode=b.workcode)where (1=1)";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)){
                sql=sql+" and Upper((select orgname from uf_hr_orgdata where orgcode=b.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(b.companyname) like Upper('%" + orgcode + "%') or Upper(b.centername) like Upper('%" + orgcode + "%') or Upper(b.deptname) like Upper('%" + orgcode + "%') or Upper(b.groupname) like Upper('%" + orgcode + "%'))";
            }
        }
        sql+=" order by b.companyname,b.centername,b.deptname,b.groupname asc))where rn>="+startIndex+" and rn<="+endIndex;
        RecordSet rs=new RecordSet();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray=new JSONArray();
        rs.execute(sql);
        while(rs.next()) {
            try {
                JSONObject object = new JSONObject();
                String startTime = Util.null2String(rs.getString("jobstarttime"));
                Integer dayDiffence = null;
                if(!"".equals(startTime)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date startT=sdf.parse(startTime);
                        dayDiffence = daysBetween(startT);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                object.put("workcode", Util.null2String(rs.getString("aworkcode")));
                object.put("name",Util.null2String(rs.getString("name")));
                object.put("companyname",Util.null2String(rs.getString("companyname")));
                object.put("centername",Util.null2String(rs.getString("centername")));
                object.put("deptname",Util.null2String(rs.getString("deptname")));
                object.put("groupname",Util.null2String(rs.getString("groupname")));
                object.put("jobtitlename",Util.null2String(rs.getString("jobtitlename")));
                object.put("rjtrq",Util.null2String(rs.getString("rjtrq")));
                object.put("jobname",Util.null2String(rs.getString("jobname")));
                object.put("jobendtime",Util.null2String(rs.getString("jobendtime")));
                object.put("jobtimes",dayDiffence);
                object.put("joblevel",Util.null2String(rs.getString("joblevel")));
                object.put("jobtype",Util.null2String(rs.getString("jobtype")));
                object.put("jobstarttime",Util.null2String(rs.getString("jobstarttime")));
                object.put("skillname1",Util.null2String(rs.getString("skillname1")));
                object.put("skilllevel1",Util.null2String(rs.getString("skilllevel1")));
                object.put("skillstarttime1",Util.null2String(rs.getString("skillstarttime1")));
                object.put("skillendtime1",Util.null2String(rs.getString("skillendtime1")));
                object.put("skillname2",Util.null2String(rs.getString("skillname2")));
                object.put("skilllevel2",Util.null2String(rs.getString("skilllevel2")));
                object.put("skillstarttime2",Util.null2String(rs.getString("skillstarttime2")));
                object.put("skillendtime2",Util.null2String(rs.getString("skillendtime2")));
                jsonArray.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String totalNum = null;
        sql="select count(1) totalnum from UF_HR_PERSONDATA b left join UF_HR_PERSONCERT a on b.workcode=a.workcode where b.currdeptcode in ("+orgcodes+") and b.status='有效' and b.sequence='生产' and (b.employeetype in ('正式员工','实习员工') or (b.employeetype='劳务人员' and b.joblevel <>'99')) ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)){
                sql=sql+" and Upper((select orgname from uf_hr_orgdata where orgcode=b.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(b.companyname) like Upper('%" + orgcode + "%') or Upper(b.centername) like Upper('%" + orgcode + "%') or Upper(b.deptname) like Upper('%" + orgcode + "%') or Upper(b.groupname) like Upper('%" + orgcode + "%'))";
            }
        }
        sql=sql+" order by b.companyname,b.centername,b.deptname,b.groupname asc";
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

    public static int daysBetween(Date date1){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        long time2 = System.currentTimeMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    //导出报表
    @GET
    @Path("/OutExcel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response weaReportOutExcel(@Context HttpServletRequest request, @Context HttpServletResponse response){
        try {
            User user = HrmUserVarify.getUser(request, response);
            InputStream input = OutExcel(request,response);
            String filename="上岗证信息";
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
            cell.setCellValue("入集团日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("上岗证");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);


            cell=row0.createCell(11);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(12);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(13);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            CellRangeAddress ll = new CellRangeAddress(0, 0, 8, 13);
            sheet.addMergedRegion(ll);



            cell=row0.createCell(14);
            cell.setCellValue("技能1");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(15);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(16);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(17);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            CellRangeAddress aa = new CellRangeAddress(0, 0, 14, 17);
            sheet.addMergedRegion(aa);

            cell=row0.createCell(18);
            cell.setCellValue("技能2");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(19);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(20);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(21);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);

            CellRangeAddress bb = new CellRangeAddress(0, 0, 18, 21);
            sheet.addMergedRegion(bb);


            HSSFRow row1=sheet.createRow((short)1);
            cell=row1.createCell(0);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(1);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(2);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(3);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(4);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(5);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(6);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(7);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress cc = new CellRangeAddress(0, 1, 0, 0);
            sheet.addMergedRegion(cc);
            CellRangeAddress dd = new CellRangeAddress(0, 1, 1, 1);
            sheet.addMergedRegion(dd);
            CellRangeAddress ee = new CellRangeAddress(0, 1, 2, 2);
            sheet.addMergedRegion(ee);
            CellRangeAddress ff = new CellRangeAddress(0, 1, 3, 3);
            sheet.addMergedRegion(ff);
            CellRangeAddress gg = new CellRangeAddress(0, 1, 4, 4);
            sheet.addMergedRegion(gg);
            CellRangeAddress hh = new CellRangeAddress(0, 1, 5, 5);
            sheet.addMergedRegion(hh);
            CellRangeAddress mm = new CellRangeAddress(0, 1, 6, 6);
            sheet.addMergedRegion(mm);
            CellRangeAddress nn = new CellRangeAddress(0, 1, 7, 7);
            sheet.addMergedRegion(nn);

            cell=row1.createCell(8);
            cell.setCellValue("认证岗位");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(9);
            cell.setCellValue("岗位等级");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(10);
            cell.setCellValue("岗位属性");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(11);
            cell.setCellValue("岗位认证时间");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(12);
            cell.setCellValue("岗位认证失效日期");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(13);
            cell.setCellValue("认证时长");
            cell.setCellStyle(sheetStyle);


            cell=row1.createCell(14);
            cell.setCellValue("技能名称");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(15);
            cell.setCellValue("技能等级");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(16);
            cell.setCellValue("技能认证时间");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(17);
            cell.setCellValue("技能有效期截止时间");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(18);
            cell.setCellValue("技能名称");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(19);
            cell.setCellValue("技能等级");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(20);
            cell.setCellValue("技能认证时间");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(21);
            cell.setCellValue("技能有效期截止时间");
            cell.setCellStyle(sheetStyle);


            User user= HrmUserVarify.getUser(request,response);
            HrUtil hrUtil=new HrUtil();
            Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
            String workcode=personBaseinfo.get("workcode");
            String recordid=personBaseinfo.get("recordid");
            String orgcodes=null;
            orgcodes = hrUtil.getOrgcodes(workcode, recordid);

            String orgcode = Util.null2String(request.getParameter("orgcode"));
            String sfbhxjzz= Util.null2String(request.getParameter("sfbhxjzz"));
            try {
                orgcode=URLDecoder.decode(orgcode,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String sql="select * from(select aworkcode,jobname,joblevel,jobtype,jobstarttime,jobendtime,skillname1,skilllevel1,skillstarttime1,skillendtime1,skillname2,skilllevel2,skillstarttime2,skillendtime2,name,companyname,centername,deptname,groupname,jobtitlename,rjtrq,ROWNUM rn from" +
                    "(select a.id,b.workcode aworkcode,a.jobname,a.joblevel,a.jobtype,a.jobstarttime,a.jobendtime,a.skillname1,a.skilllevel1,a.skillstarttime1,a.skillendtime1,a.skillname2,a.skilllevel2,a.skillstarttime2,a.skillendtime2,b.name,b.companyname,b.centername,b.deptname,b.groupname,b.jobtitlename,b.rjtrq,b.currdeptcode from ((select * from UF_HR_PERSONCERT ) a right join (select * from UF_HR_PERSONDATA where currdeptcode in ("+orgcodes+") and status='有效' and sequence='生产' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) b on a.workcode=b.workcode)where (1=1)";
            if(!"".equals(orgcode)){
                if("false".equals(sfbhxjzz)){
                    sql=sql+" and Upper((select orgname from uf_hr_orgdata where orgcode=b.currdeptcode)) like Upper('%" + orgcode + "%')";
                }else{
                    sql = sql + " and (Upper(b.companyname) like Upper('%" + orgcode + "%') or Upper(b.centername) like Upper('%" + orgcode + "%') or Upper(b.deptname) like Upper('%" + orgcode + "%') or Upper(b.groupname) like Upper('%" + orgcode + "%'))";
                }
            }
            sql+=" order by b.companyname,b.centername,b.deptname,b.groupname asc))";
            rs.execute(sql);
            int indexrow = 2;
            while(rs.next()){
                String startTime = Util.null2String(rs.getString("jobstarttime"));
                Integer dayDiffence = null;
                if(!"".equals(startTime)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date startT=sdf.parse(startTime);
                        dayDiffence = daysBetween(startT);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("aworkcode")));
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
                cell.setCellValue(Util.null2String(rs.getString("rjtrq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("jobname")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("joblevel")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("jobtype")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("jobstarttime")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(12);
                cell.setCellValue(Util.null2String(rs.getString("jobendtime")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(13);
                cell.setCellValue(Util.null2String(dayDiffence));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(14);
                cell.setCellValue(Util.null2String(rs.getString("skillname1")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(15);
                cell.setCellValue(Util.null2String(rs.getString("skilllevel1")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(16);
                cell.setCellValue(Util.null2String(rs.getString("skillstarttime1")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(17);
                cell.setCellValue(Util.null2String(rs.getString("skillendtime1")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(18);
                cell.setCellValue(Util.null2String(rs.getString("skillname2")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(19);
                cell.setCellValue(Util.null2String(rs.getString("skilllevel2")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(20);
                cell.setCellValue(Util.null2String(rs.getString("skillstarttime2")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(21);
                cell.setCellValue(Util.null2String(rs.getString("skillendtime2")));
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
