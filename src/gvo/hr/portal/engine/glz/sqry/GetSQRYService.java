package gvo.hr.portal.engine.glz.sqry;

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
import javax.ws.rs.POST;
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
import java.util.Map;

/**
 * @author
 * @date 2020/5/7 0007 - 下午 1:38
 */
public class GetSQRYService {
    @POST
    @Path("/getSQRYData")
    @Produces({MediaType.TEXT_PLAIN})
    public String getSQRYData(@Context HttpServletRequest request, @Context HttpServletResponse response){
        User user= HrmUserVarify.getUser(request,response);
        HrUtil hrUtil=new HrUtil();
        Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode=personBaseinfo.get("workcode");
        String recordid=personBaseinfo.get("recordid");
        String orgcodes=null;
        orgcodes = hrUtil.getOrgcodes(workcode, recordid);

        String orgcode = Util.null2String(request.getParameter("orgcode"));
        String realname= Util.null2String(request.getParameter("realname"));
        String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Integer startIndex=(page-1)*limit+1;
        Integer endIndex=page*limit;
        try {
            orgcode=URLDecoder.decode(orgcode,"UTF-8");
            realname=URLDecoder.decode(realname,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sql="select * from(select t.*,ROWNUM rn from(SELECT a.workcode," +
                "       a.name," +
                "       a.companyname," +
                "       a.centername," +
                "       a.deptname," +
                "       a.groupname," +
                "       a.jobtitlename," +
                "       a.identitytype," +
                "       (select max(num)" +
                "          from gvogroup.UF_HR_BABY k" +
                "         where a.workcode = k.work_code" +
                "           and type = 1) as yc," +
                "       (select max(num)" +
                "          from gvogroup.UF_HR_BABY k" +
                "         where a.workcode = k.work_code" +
                "           and type = 0) as yr," +
                "       (select max(begindate)" +
                "          from gvogroup.formtable_main_763_dt1" +
                "         where work_code = a.workcode" +
                "           and absence_type = '11') as begindate," +
                "       (select max(enddate)" +
                "          from gvogroup.formtable_main_763_dt1" +
                "         where work_code = a.workcode" +
                "           and absence_type = '11') as endate,"+
                "       (select to_char(to_date(max(num), 'yyyy-mm-dd') + 365, 'yyyy-mm-dd')" +
                "          from gvogroup.UF_HR_BABY k" +
                "         where a.workcode = k.work_code" +
                "           and type = 0) as br_end," +
                "       (select max(num)" +
                "          from gvogroup.UF_HR_BABY k" +
                "         where a.workcode = k.work_code" +
                "           and type = 2) as db" +
                "  FROM uf_hr_persondata A WHERE A.CURRDEPTCODE in ("+orgcodes+") and A.status='有效' and (A.employeetype in ('正式员工','实习员工') or (A.employeetype='劳务人员' and A.joblevel <>'99')) and A.WORKCODE in (select work_code from gvogroup.UF_HR_BABY)";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=A.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(A.companyname) like Upper('%" + orgcode + "%') or Upper(A.centername) like Upper('%" + orgcode + "%') or Upper(A.deptname) like Upper('%" + orgcode + "%') or Upper(A.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        if(!"".equals(realname)){
            sql=sql+" and A.name like '%"+realname+"%'";
        }
        sql=sql+" order by a.companyname,a.centername,a.deptname,a.groupname asc)t)where rn>="+startIndex+" and rn<="+endIndex;
       // new BaseBean().writeLog("GetSQRYService sql"+sql);
        RecordSet rs=new RecordSet();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray=new JSONArray();
        rs.execute(sql);
        while(rs.next()){
            JSONObject object = new JSONObject();
            try {
                object.put("workcode",Util.null2String(rs.getString("workcode")));
                object.put("name",Util.null2String(rs.getString("name")));
                object.put("companyname",Util.null2String(rs.getString("companyname")));
                object.put("centername",Util.null2String(rs.getString("centername")));
                object.put("deptname",Util.null2String(rs.getString("deptname")));
                object.put("groupname",Util.null2String(rs.getString("groupname")));
                object.put("jobtitlename",Util.null2String(rs.getString("jobtitlename")));
                object.put("identitytype",Util.null2String(rs.getString("identitytype")));
                object.put("yc",Util.null2String(rs.getString("yc")));
                object.put("yr",Util.null2String(rs.getString("yr")));
                object.put("begindate",Util.null2String(rs.getString("begindate")));
                object.put("endate",Util.null2String(rs.getString("endate")));
                object.put("br_end",Util.null2String(rs.getString("br_end")));
                object.put("db",Util.null2String(rs.getString("db")));
                jsonArray.put(object);
            } catch (Exception e) {
                new BaseBean().writeLog(e);
            }
        }
        String totalNum=null;
        sql="select count(workcode) totalnum from(select t.*,ROWNUM rn from(SELECT a.workcode," +
                "       a.name," +
                "       a.companyname," +
                "       a.centername," +
                "       a.deptname," +
                "       a.groupname," +
                "       a.jobtitlename," +
                "       a.identitytype," +
                "       (select max(num)" +
                "          from gvogroup.UF_HR_BABY k" +
                "         where a.workcode = k.work_code" +
                "           and type = 1) as yc," +
                "       (select max(num)" +
                "          from gvogroup.UF_HR_BABY k" +
                "         where a.workcode = k.work_code" +
                "           and type = 0) as yr," +
                "       (select max(begindate)" +
                "          from gvogroup.formtable_main_763_dt1" +
                "         where work_code = a.workcode" +
                "           and absence_type = '11') as begindate," +
                "       (select max(enddate)" +
                "          from gvogroup.formtable_main_763_dt1" +
                "         where work_code = a.workcode" +
                "           and absence_type = '11') as endate,"+
                "       (select to_char(to_date(max(num), 'yyyy-mm-dd') + 365, 'yyyy-mm-dd')" +
                "          from gvogroup.UF_HR_BABY k" +
                "         where a.workcode = k.work_code" +
                "           and type = 0) as br_end," +
                "       (select max(num)" +
                "          from gvogroup.UF_HR_BABY k" +
                "         where a.workcode = k.work_code" +
                "           and type = 2) as db" +
                "  FROM uf_hr_persondata A WHERE A.CURRDEPTCODE in ("+orgcodes+") and A.status='有效' and (A.employeetype in ('正式员工','实习员工') or (A.employeetype='劳务人员' and A.joblevel <>'99')) and A.WORKCODE in (select work_code from gvogroup.UF_HR_BABY)";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=A.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(A.companyname) like Upper('%" + orgcode + "%') or Upper(A.centername) like Upper('%" + orgcode + "%') or Upper(A.deptname) like Upper('%" + orgcode + "%') or Upper(A.groupname) like Upper('%" + orgcode + "%'))";
            }
        }
        if(!"".equals(realname)){
            sql=sql+" and A.name like '%"+realname+"%'";
        }
        sql=sql+" ORDER BY A.id ASC )t) order by companyname,centername,deptname,groupname asc";
        rs.execute(sql);
       // new BaseBean().writeLog("GetSQRYService sql"+sql);
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
            String filename="三期人员信息";
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
            cell.setCellValue("身份类别");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("预产期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("婴儿出生日期");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("产假开始日");
            cell.setCellStyle(sheetStyle);

//            cell=row0.createCell(11);
//            cell.setCellValue("改签费");
//            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("产假结束日");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(12);
            cell.setCellValue("哺乳期结束日");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(13);
            cell.setCellValue("多胞胎数");
            cell.setCellStyle(sheetStyle);

            User user= HrmUserVarify.getUser(request,response);
            HrUtil hrUtil=new HrUtil();
            Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
            String workcode=personBaseinfo.get("workcode");
            String recordid=personBaseinfo.get("recordid");
            String orgcodes=null;
            orgcodes = hrUtil.getOrgcodes(workcode, recordid);

            String orgcode = Util.null2String(request.getParameter("orgcode"));
            String realname= Util.null2String(request.getParameter("realname"));
            String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
          //  new BaseBean().writeLog("GetSQRYService  derealname"+realname);
            orgcode=URLDecoder.decode(orgcode,"UTF-8");
            realname=URLDecoder.decode(realname,"UTF-8");
           // new BaseBean().writeLog("GetSQRYService  realname"+realname);
            String sql="select * from(select t.*,ROWNUM rn from(SELECT a.workcode," +
                    "       a.name," +
                    "       a.companyname," +
                    "       a.centername," +
                    "       a.deptname," +
                    "       a.groupname," +
                    "       a.jobtitlename," +
                    "       a.identitytype," +
                    "       (select max(num)" +
                    "          from gvogroup.UF_HR_BABY k" +
                    "         where a.workcode = k.work_code" +
                    "           and type = 1) as yc," +
                    "       (select max(num)" +
                    "          from gvogroup.UF_HR_BABY k" +
                    "         where a.workcode = k.work_code" +
                    "           and type = 0) as yr," +
                    "       (select max(begindate)" +
                    "          from gvogroup.formtable_main_763_dt1" +
                    "         where work_code = a.workcode" +
                    "           and absence_type = '11') as begindate," +
                    "       (select max(enddate)" +
                    "          from gvogroup.formtable_main_763_dt1" +
                    "         where work_code = a.workcode" +
                    "           and absence_type = '11') as endate,"+
                    "       (select to_char(to_date(max(num), 'yyyy-mm-dd') + 365, 'yyyy-mm-dd')" +
                    "          from gvogroup.UF_HR_BABY k" +
                    "         where a.workcode = k.work_code" +
                    "           and type = 0) as br_end," +
                    "       (select max(num)" +
                    "          from gvogroup.UF_HR_BABY k" +
                    "         where a.workcode = k.work_code" +
                    "           and type = 2) as db" +
                    "  FROM uf_hr_persondata A WHERE A.CURRDEPTCODE in ("+orgcodes+") and A.status='有效' and A.WORKCODE in (select work_code from gvogroup.UF_HR_BABY)";
            if(!"".equals(orgcode)){
                if("false".equals(sfbhxjzz)) {
                    sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=A.currdeptcode)) like Upper('%" + orgcode + "%')";
                }else{
                    sql = sql + " and (Upper(A.companyname) like Upper('%" + orgcode + "%') or Upper(A.centername) like Upper('%" + orgcode + "%') or Upper(A.deptname) like Upper('%" + orgcode + "%') or Upper(A.groupname) like Upper('%" + orgcode + "%'))";
                }
            }
            if(!"".equals(realname)){
                sql=sql+" and A.name like '%"+realname+"%'";
            }
            sql=sql+" order by a.companyname,a.centername,a.deptname,a.groupname asc)t)";
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
                cell.setCellValue(Util.null2String(rs.getString("identitytype")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("yc")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("yr")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("begindate")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("endate")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(12);
                cell.setCellValue(Util.null2String(rs.getString("br_end")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(13);
                cell.setCellValue(Util.null2String(rs.getString("db")));
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
