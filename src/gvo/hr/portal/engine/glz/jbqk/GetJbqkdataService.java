package gvo.hr.portal.engine.glz.jbqk;

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
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/5/15 14:41
 * version v1
 * desc 加班情况报表数据
 */
public class GetJbqkdataService {
    @POST
    @Path("/getYdljjbqkmx")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 月度累计加班情况明细
     */
    public String getData(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        User user= HrmUserVarify.getUser(request,response);
        Map<String,Object> params = ParamUtil.request2Map(request);
        HrUtil hrUtil=new HrUtil();
        Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode=personBaseinfo.get("workcode");
        String recordid=personBaseinfo.get("recordid");

        RecordSet rs=new RecordSet();
        String orgcodes=hrUtil.getOrgcodes(workcode,recordid);
        String orgcode = "";
        String worktype = "";
        String identitytype = "";
        String rygh = "";
        try{
           orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")),"UTF-8");
           identitytype = URLDecoder.decode(Util.null2String(params.get("identitytype")),"UTF-8");;
           worktype = URLDecoder.decode(Util.null2String(params.get("worktype")),"UTF-8");
           rygh = URLDecoder.decode(Util.null2String(params.get("rygh")),"UTF-8");
        }catch (Exception e){

        }
        String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
//        String orgcode = Util.null2String(params.get("orgcode"));
//        String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
//        String identitytype = Util.null2String(params.get("identitytype"));
//        String worktype = Util.null2String(params.get("worktype"));
//        String rygh = Util.null2String(params.get("rygh"));
        String jbsdqsstart = Util.null2String(params.get("jbsdqsstart"));
        String jbsdqsend = Util.null2String(params.get("jbsdqsend"));
        if("0".equals(identitytype)){
            identitytype = "间接人员";
        }else if("1".equals(identitytype)){
            identitytype = "直接人员";
        }

        String type = Util.null2String(params.get("type"));
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Integer startIndex=(page-1)*limit+1;
        Integer endIndex=page*limit;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = sf.format(new Date());
        String begindate = enddate.substring(0,7)+"-01";
        String sql = "select t.*,t.PS_HOURS + t.ZM_HOURS + t.FD_HOURS as total_hours," +
                "       case when st_tops is null then '' else round((t.PS_HOURS + t.ZM_HOURS + t.FD_HOURS) / t.st_tops, 4) * 100 || '%' end as rate," +
                "       case when st_tops is null then '' else round((t.PS_HOURS + t.ZM_HOURS + t.FD_HOURS) / t.up_tops, 4) * 100 || '%' end as rate1 " +
                " from (" +
                "select b.workcode," +
                "               b.name," +
                "               b.companyname," +
                "               b.centername," +
                "               b.deptname," +
                "               b.groupname," +
                "               b.identitytype," +
                "               b.worktype,          " +
                "               case when b.worktype='不定时' then " +
                "                   (select nvl(sum(psjb),0) from uf_hr_jbsj_b where workcode=b.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) " +
                "               else (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                "                  from formtable_main_744_dt1 aa" +
                "                 where cday >= '"+begindate+"'" +
                "                   and cday <= '"+enddate+"'" +
                "                   and aa.work_code = b.WORKCODE" +
                "                   and overtime_type like '%平时%')end AS PS_HOURS," +
                "               case when b.worktype='不定时' then " +
                "                   (select nvl(sum(zmjb),0) from uf_hr_jbsj_b where workcode=b.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) " +
                "               else" +
                "               (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                "                  from gvogroup.formtable_main_744_dt1 aa" +
                "                 where cday >= '"+begindate+"'" +
                "                   and cday <= '"+enddate+"'" +
                "                   and aa.work_code = b.WORKCODE" +
                "                   and overtime_type like '%周末%') end AS ZM_HOURS," +
                "               case when b.worktype='不定时' then " +
                "                   (select nvl(sum(jjrjb),0) from uf_hr_jbsj_b where workcode=b.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) " +
                "               else" +
                "               (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                "                  from gvogroup.formtable_main_744_dt1 aa" +
                "                 where cday >= '"+begindate+"'" +
                "                   and cday <= '"+enddate+"'" +
                "                   and aa.work_code = b.WORKCODE" +
                "                   and overtime_type like '%法定%') end AS FD_HOURS," +
                "               case when b.worktype='不定时' then " +
                "                 null" +
                "               else" +
                "                    (select nvl(TOP_HOURS, 0)" +
                "                  from uf_companytop_hours ch" +
                "                 where ch.company = a.SUBCOMPANYID1" +
                "                   and PEOPLE_TYPE = '1'" +
                "                   and ch.BEGINDATE <= '"+enddate+"'" +
                "                   and ch.ENDDATE >= '"+enddate+"'" +
                "                   and rownum <= 1) end as st_tops," +
                "               case when b.worktype='不定时' then " +
                "                 null" +
                "               else" +
                "                 F_getovertime('"+enddate+"', a.workcode, 'G') end AS up_tops " +
                "  from (select rownum as num, t.* " +
                "          from (select * " +
                "                  from uf_hr_persondata a" +
                "                 where (employeetype in ('正式员工', '实习员工') or " +
                "                       (employeetype = '劳务人员' and joblevel <> '99')) " +
                "                   and status = '有效' " +
                "                   and identitytype = '"+identitytype+"' and currdeptcode in("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

            }
        }

        if(!"".equals(worktype)){
            sql=sql+" and worktype ='"+worktype+"'";
        }

        if(!"".equals(rygh)){
            sql=sql+" and name like '%"+rygh+"%'";
        }

        if(!"".equals(jbsdqsstart)){
            sql=sql+" and case when worktype='不定时' then (select nvl(sum(jjrjb+zmjb+jjrjb),0) from uf_hr_jbsj_b where workcode=a.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) else (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                    "                        from formtable_main_744_dt1 aa" +
                    "                       where cday >= '"+begindate+"'" +
                    "                         and cday <= '"+enddate+"'" +
                    "                         and aa.work_code = a.WORKCODE" +
                    "                         and (overtime_type like '%法定%' or overtime_type like '%平时%' or overtime_type like '%周末%')) end >="+jbsdqsstart;
        }

        if(!"".equals(jbsdqsend)){
            sql=sql+" and case when worktype='不定时' then (select nvl(sum(jjrjb+zmjb+jjrjb),0) from uf_hr_jbsj_b where workcode=a.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) else (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                    "                        from formtable_main_744_dt1 aa" +
                    "                       where cday >= '"+begindate+"'" +
                    "                         and cday <= '"+enddate+"'" +
                    "                         and aa.work_code = a.WORKCODE" +
                    "                         and (overtime_type like '%法定%' or overtime_type like '%平时%' or overtime_type like '%周末%')) end<="+jbsdqsend;
        }

         sql += " order by a.worktype asc,a.companyname asc,a.centername asc,a.deptname asc,a.groupname asc) t ) b, " +
                "       hrmresource a " +
                " where b.workcode = a.workcode and  b.num>="+startIndex+" and b.num<="+endIndex+" ) t";
        //new BaseBean().writeLog("testbbbb:"+sql);
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
                object.put("identitytype",Util.null2String(rs.getString("identitytype")));
                object.put("worktype",Util.null2String(rs.getString("worktype")));
                object.put("PS_HOURS",Util.null2String(rs.getString("PS_HOURS")));
                object.put("ZM_HOURS",Util.null2String(rs.getString("ZM_HOURS")));
                object.put("FD_HOURS",Util.null2String(rs.getString("FD_HOURS")));
                object.put("total_hours",Util.null2String(rs.getString("total_hours")));
                object.put("st_tops",Util.null2String(rs.getString("st_tops")));
                object.put("up_tops",Util.null2String(rs.getString("up_tops")));

                object.put("rate",Util.null2String(rs.getString("rate")));
                object.put("rate1",Util.null2String(rs.getString("rate1")));

                jsonArray.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String totalNum = null;
        sql="  select  count(1) as totalnum from uf_hr_persondata t where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and t.status = '有效' and identitytype = '"+identitytype+"' and  currdeptcode in ("+orgcodes+") ";

        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=t.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(t.companyname) like Upper('%" + orgcode + "%') or Upper(t.centername) like Upper('%" + orgcode + "%') or Upper(t.deptname) like Upper('%" + orgcode + "%') or Upper(t.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        if(!"".equals(worktype)){
            sql=sql+" and worktype ='"+worktype+"'";
        }

        if(!"".equals(rygh)){
            sql=sql+" and workcode in("+rygh+")";
        }

        if(!"".equals(jbsdqsstart)){
            sql=sql+" and case when worktype='不定时' then (select nvl(sum(jjrjb+zmjb+jjrjb),0) from uf_hr_jbsj_b where workcode=t.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) else (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                    "                        from formtable_main_744_dt1 aa" +
                    "                       where cday >= '"+begindate+"'" +
                    "                         and cday <= '"+enddate+"'" +
                    "                         and aa.work_code = t.WORKCODE" +
                    "                         and (overtime_type like '%法定%' or overtime_type like '%平时%' or overtime_type like '%周末%')) end >="+jbsdqsstart;
        }

        if(!"".equals(jbsdqsend)){
            sql=sql+" and case when worktype='不定时' then (select nvl(sum(jjrjb+zmjb+jjrjb),0) from uf_hr_jbsj_b where workcode=t.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) else (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                    "                        from formtable_main_744_dt1 aa" +
                    "                       where cday >= '"+begindate+"'" +
                    "                         and cday <= '"+enddate+"'" +
                    "                         and aa.work_code = t.WORKCODE" +
                    "                         and (overtime_type like '%法定%' or overtime_type like '%平时%' or overtime_type like '%周末%')) end<="+jbsdqsend;
        }
        //new BaseBean().writeLog("getMkDatfyaaa:"+sql);
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
            String filename="部门月度";
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
            cell.setCellValue("身份类别");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("工时类别");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("平时加班");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("周末加班");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("国假日加班");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("加班合计");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(12);
            cell.setCellValue("标准加班受限总时数");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(13);
            cell.setCellValue("调整后加班受限总时数");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(14);
            cell.setCellValue("标准百分比");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(15);
            cell.setCellValue("调整后百分比");
            cell.setCellStyle(sheetStyle);

            User user= HrmUserVarify.getUser(request,response);
            HrUtil hrUtil=new HrUtil();
            Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
            String workcode=personBaseinfo.get("workcode");
            String recordid=personBaseinfo.get("recordid");
            String sql = "";
            String orgcodes=hrUtil.getOrgcodes(workcode,recordid);

            String orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")),"UTF-8");
            String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
            String identitytype = URLDecoder.decode(Util.null2String(params.get("identitytype")),"UTF-8");;
            String worktype = URLDecoder.decode(Util.null2String(params.get("worktype")),"UTF-8");
            String rygh = URLDecoder.decode(Util.null2String(params.get("rygh")),"UTF-8");
            String jbsdqsstart = Util.null2String(params.get("jbsdqsstart"));
            String jbsdqsend = Util.null2String(params.get("jbsdqsend"));
            if("0".equals(identitytype)){
                identitytype = "间接人员";
            }else if("1".equals(identitytype)){
                identitytype = "直接人员";
            }



            String type = Util.null2String(params.get("type"));

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String enddate = sf.format(new Date());
            String begindate = enddate.substring(0,7)+"-01";
            sql = "select t.*,t.PS_HOURS + t.ZM_HOURS + t.FD_HOURS as total_hours," +
                    "       case when st_tops is null then '' else round((t.PS_HOURS + t.ZM_HOURS + t.FD_HOURS) / t.st_tops, 4) * 100 || '%' end as rate," +
                    "       case when st_tops is null then '' else round((t.PS_HOURS + t.ZM_HOURS + t.FD_HOURS) / t.up_tops, 4) * 100 || '%' end as rate1 " +
                    " from (" +
                    "select b.workcode," +
                    "               b.name," +
                    "               b.companyname," +
                    "               b.centername," +
                    "               b.deptname," +
                    "               b.groupname," +
                    "               b.identitytype," +
                    "               b.worktype,          " +
                    "               case when b.worktype='不定时' then " +
                    "                   (select nvl(sum(psjb),0) from uf_hr_jbsj_b where workcode=b.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) " +
                    "               else (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                    "                  from formtable_main_744_dt1 aa" +
                    "                 where cday >= '"+begindate+"'" +
                    "                   and cday <= '"+enddate+"'" +
                    "                   and aa.work_code = b.WORKCODE" +
                    "                   and overtime_type like '%平时%')end AS PS_HOURS," +
                    "               case when b.worktype='不定时' then " +
                    "                   (select nvl(sum(zmjb),0) from uf_hr_jbsj_b where workcode=b.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) " +
                    "               else" +
                    "               (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                    "                  from gvogroup.formtable_main_744_dt1 aa" +
                    "                 where cday >= '"+begindate+"'" +
                    "                   and cday <= '"+enddate+"'" +
                    "                   and aa.work_code = b.WORKCODE" +
                    "                   and overtime_type like '%周末%') end AS ZM_HOURS," +
                    "               case when b.worktype='不定时' then " +
                    "                   (select nvl(sum(jjrjb),0) from uf_hr_jbsj_b where workcode=b.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) " +
                    "               else" +
                    "               (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                    "                  from gvogroup.formtable_main_744_dt1 aa" +
                    "                 where cday >= '"+begindate+"'" +
                    "                   and cday <= '"+enddate+"'" +
                    "                   and aa.work_code = b.WORKCODE" +
                    "                   and overtime_type like '%法定%') end AS FD_HOURS," +
                    "               case when b.worktype='不定时' then " +
                    "                 null" +
                    "               else" +
                    "                    (select nvl(TOP_HOURS, 0)" +
                    "                  from uf_companytop_hours ch" +
                    "                 where ch.company = a.SUBCOMPANYID1" +
                    "                   and PEOPLE_TYPE = '1'" +
                    "                   and ch.BEGINDATE <= '"+enddate+"'" +
                    "                   and ch.ENDDATE >= '"+enddate+"'" +
                    "                   and rownum <= 1) end as st_tops," +
                    "               case when b.worktype='不定时' then " +
                    "                 null" +
                    "               else" +
                    "                 F_getovertime('"+enddate+"', a.workcode, 'G') end AS up_tops " +
                    "  from (select rownum as num, t.* " +
                    "          from (select * " +
                    "                  from uf_hr_persondata a" +
                    "                 where (employeetype in ('正式员工', '实习员工') or " +
                    "                       (employeetype = '劳务人员' and joblevel <> '99')) " +
                    "                   and status = '有效' " +
                    "                   and identitytype = '"+identitytype+"' and currdeptcode in("+orgcodes+") ";
            if(!"".equals(orgcode)){
                if("false".equals(sfbhxjzz)) {
                    sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
                }else{
                    sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

                }
            }
            if(!"".equals(worktype)){
                sql=sql+" and worktype ='"+worktype+"'";
            }

            if(!"".equals(rygh)){
                sql=sql+" and name like '%"+rygh+"%'";
            }

            if(!"".equals(jbsdqsstart)){
                sql=sql+" and case when worktype='不定时' then (select nvl(sum(jjrjb+zmjb+jjrjb),0) from uf_hr_jbsj_b where workcode=a.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) else (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                        "                        from formtable_main_744_dt1 aa" +
                        "                       where cday >= '"+begindate+"'" +
                        "                         and cday <= '"+enddate+"'" +
                        "                         and aa.work_code = a.WORKCODE" +
                        "                         and (overtime_type like '%法定%' or overtime_type like '%平时%' or overtime_type like '%周末%')) end >="+jbsdqsstart;
            }

            if(!"".equals(jbsdqsend)){
                sql=sql+" and case when worktype='不定时' then (select nvl(sum(jjrjb+zmjb+jjrjb),0) from uf_hr_jbsj_b where workcode=a.workcode and month=to_char(to_date('"+begindate+"','yyyy-mm-dd'),'yyyy-mm')) else (SELECT nvl(SUM(nvl(a_hours, 0)), 0)" +
                        "                        from formtable_main_744_dt1 aa" +
                        "                       where cday >= '"+begindate+"'" +
                        "                         and cday <= '"+enddate+"'" +
                        "                         and aa.work_code = a.WORKCODE" +
                        "                         and (overtime_type like '%法定%' or overtime_type like '%平时%' or overtime_type like '%周末%')) end<="+jbsdqsend;
            }
            sql += " order by a.worktype asc,a.companyname asc,a.centername asc,a.deptname asc,a.groupname asc) t ) b, " +
                    "       hrmresource a " +
                    " where b.workcode = a.workcode) t";
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
                cell.setCellValue(Util.null2String(rs.getString("identitytype")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(rs.getString("worktype")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("PS_HOURS")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("ZM_HOURS")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("FD_HOURS")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("total_hours")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(12);
                cell.setCellValue(Util.null2String(rs.getString("st_tops")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(13);
                cell.setCellValue(Util.null2String(rs.getString("up_tops")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(14);
                cell.setCellValue(Util.null2String(rs.getString("rate")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(15);
                cell.setCellValue(Util.null2String(rs.getString("rate1")));
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

    @POST
    @Path("/getndljjbqk")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 年度累计加班情况明细
     */
    public String getNdljData(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        User user = HrmUserVarify.getUser(request, response);
        Map<String, Object> params = ParamUtil.request2Map(request);
        HrUtil hrUtil = new HrUtil();
        Map<String, String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode = personBaseinfo.get("workcode");
        String recordid = personBaseinfo.get("recordid");

        RecordSet rs = new RecordSet();
        String orgcodes = hrUtil.getOrgcodes(workcode, recordid);
        String orgcode = "";
        String worktype = "";
        String identitytype = "";
        String rygh = "";
        try{
            orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")),"UTF-8");
            identitytype = URLDecoder.decode(Util.null2String(params.get("identitytype")),"UTF-8");;
            worktype = URLDecoder.decode(Util.null2String(params.get("worktype")),"UTF-8");
            rygh = URLDecoder.decode(Util.null2String(params.get("rygh")),"UTF-8");
        }catch (Exception e){

        }
       // String orgcode = Util.null2String(params.get("orgcode"));
        String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
       // String identitytype = Util.null2String(params.get("identitytype"));
       // String worktype = Util.null2String(params.get("worktype"));
       // String rygh = Util.null2String(params.get("rygh"));

        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Integer startIndex=(page-1)*limit+1;
        Integer endIndex=page*limit;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nowdate = sf.format(new Date());
        String year = nowdate.substring(0,4);
        String month1 = year+"-01-01";
        String month2 = year+"-02-01";
        String month3 = year+"-03-01";
        String month4 = year+"-04-01";
        String month5 = year+"-05-01";
        String month6 = year+"-06-01";
        String month7 = year+"-07-01";
        String month8 = year+"-08-01";
        String month9 = year+"-09-01";
        String month10 = year+"-10-01";
        String month11 = year+"-11-01";
        String month12 = year+"-12-01";
        String sql = "select t.*,round((yue1+yue2+yue3+yue4+yue5+yue6+yue7+yue8+yue9+yue10+yue11+yue12)/(trunc(MONTHS_BETWEEN(to_date('"+nowdate+"','yyyy-mm-dd'),to_date('"+month1+"','yyyy-mm-dd')))+1),1) as yjjbss," +
                "(yue1+yue2+yue3+yue4+yue5+yue6+yue7+yue8+yue9+yue10+yue11+yue12) as yljjbss" +
                " from (" +
                "select b.workcode," +
                "               b.name," +
                "               b.companyname," +
                "               b.centername," +
                "               b.deptname," +
                "               b.groupname," +
                "               b.jobtitlename,"+
                "               b.identitytype," +
                "               b.worktype,          " +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month1+"',b.worktype) as yue1," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month2+"',b.worktype) as yue2," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month3+"',b.worktype) as yue3," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month4+"',b.worktype) as yue4," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month5+"',b.worktype) as yue5," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month6+"',b.worktype) as yue6," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month7+"',b.worktype) as yue7," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month8+"',b.worktype) as yue8," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month9+"',b.worktype) as yue9," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month10+"',b.worktype) as yue10," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month11+"',b.worktype) as yue11," +
                "               f_hr_glz_getydjbsj(b.workcode,'"+month12+"',b.worktype) as yue12" +
                "  from (select rownum as num, t.* " +
                "          from (select * " +
                "                  from uf_hr_persondata a" +
                "                 where (employeetype in ('正式员工', '实习员工') or " +
                "                       (employeetype = '劳务人员' and joblevel <> '99')) " +
                "                   and status = '有效' " +
                "                   and currdeptcode in("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        if(!"".equals(worktype)){
            sql=sql+" and worktype ='"+worktype+"'";
        }
        if(!"".equals(identitytype)){
            sql=sql+" and identitytype = '"+identitytype+"'";
        }

        if(!"".equals(rygh)){
            sql=sql+" and name like '%"+rygh+"%'";
        }



        sql += " order by a.worktype asc,a.companyname asc,a.centername asc,a.deptname asc,a.groupname asc) t ) b " +
                " where b.num>="+startIndex+" and b.num<="+endIndex+" ) t";
        //new BaseBean().writeLog("GetJbqkdataService",sql);
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
                object.put("identitytype",Util.null2String(rs.getString("identitytype")));
                object.put("worktype",Util.null2String(rs.getString("worktype")));
                object.put("yjjbss",Util.null2String(rs.getString("yjjbss")));
                object.put("yljjbss",Util.null2String(rs.getString("yljjbss")));
                object.put("yue1",Util.null2String(rs.getString("yue1")));
                object.put("yue2",Util.null2String(rs.getString("yue2")));
                object.put("yue3",Util.null2String(rs.getString("yue3")));
                object.put("yue4",Util.null2String(rs.getString("yue4")));
                object.put("yue5",Util.null2String(rs.getString("yue5")));
                object.put("yue6",Util.null2String(rs.getString("yue6")));
                object.put("yue7",Util.null2String(rs.getString("yue7")));
                object.put("yue8",Util.null2String(rs.getString("yue8")));
                object.put("yue9",Util.null2String(rs.getString("yue9")));
                object.put("yue10",Util.null2String(rs.getString("yue10")));
                object.put("yue11",Util.null2String(rs.getString("yue11")));
                object.put("yue12",Util.null2String(rs.getString("yue12")));


                jsonArray.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String totalNum = null;
        sql="  select  count(1) as totalnum from uf_hr_persondata t where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and t.status = '有效'  and  currdeptcode in ("+orgcodes+") ";

        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=t.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(t.companyname) like Upper('%" + orgcode + "%') or Upper(t.centername) like Upper('%" + orgcode + "%') or Upper(t.deptname) like Upper('%" + orgcode + "%') or Upper(t.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        if(!"".equals(worktype)){
            sql=sql+" and worktype ='"+worktype+"'";
        }
        if(!"".equals(identitytype)){
            sql=sql+" and identitytype = '"+identitytype+"'";
        }
        if(!"".equals(rygh)){
            sql=sql+" and workcode in("+rygh+")";
        }


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
    @Path("/ndOutExcel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response weaReportNdOutExcel(@Context HttpServletRequest request, @Context HttpServletResponse response){
        try {
            User user = HrmUserVarify.getUser(request, response);
            InputStream input = ndOutExcel(request,response);
            String filename="个人年度累计加班情况";
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
    public InputStream ndOutExcel(HttpServletRequest request, HttpServletResponse response) {
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
            cell.setCellValue("身份类别");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("工时类别");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("月均加班时数");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("加班累计合计");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("各月加班时数");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(12);
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(13);
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(14);
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(15);
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(16);
            cell.setCellStyle(sheetStyle);


            cell=row0.createCell(17);
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(18);
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(19);
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(20);
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(21);
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(22);
            cell.setCellStyle(sheetStyle);

            HSSFRow row1=sheet.createRow((short)1);
            cell=row1.createCell(0);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(1);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(2);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(3);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(4);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(5);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(6);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(7);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(8);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(9);
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(10);
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(11);
            cell.setCellValue("1月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(12);
            cell.setCellValue("2月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(13);
            cell.setCellValue("3月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(14);
            cell.setCellValue("4月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(15);
            cell.setCellValue("5月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(16);
            cell.setCellValue("6月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(17);
            cell.setCellValue("7月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(18);
            cell.setCellValue("8月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(19);
            cell.setCellValue("9月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(20);
            cell.setCellValue("10月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(21);
            cell.setCellValue("11月");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(22);
            cell.setCellValue("12月");
            cell.setCellStyle(sheetStyle);
            for(int i=0;i<=10;i++){
                CellRangeAddress cra = new CellRangeAddress(0,1,i,i);
                sheet.addMergedRegion(cra);
            }
            CellRangeAddress cra = new CellRangeAddress(0,0,11,22);
            sheet.addMergedRegion(cra);


            User user= HrmUserVarify.getUser(request,response);
            HrUtil hrUtil=new HrUtil();
            Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
            String workcode=personBaseinfo.get("workcode");
            String recordid=personBaseinfo.get("recordid");
            String sql = "";
            String orgcodes = hrUtil.getOrgcodes(workcode, recordid);
            String orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")),"UTF-8");
            String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
            String identitytype = URLDecoder.decode(Util.null2String(params.get("identitytype")),"UTF-8");;
            String worktype = URLDecoder.decode(Util.null2String(params.get("worktype")),"UTF-8");
            String rygh = URLDecoder.decode(Util.null2String(params.get("rygh")),"UTF-8");

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String nowdate = sf.format(new Date());
            String year = nowdate.substring(0,4);
            String month1 = year+"-01-01";
            String month2 = year+"-02-01";
            String month3 = year+"-03-01";
            String month4 = year+"-04-01";
            String month5 = year+"-05-01";
            String month6 = year+"-06-01";
            String month7 = year+"-07-01";
            String month8 = year+"-08-01";
            String month9 = year+"-09-01";
            String month10 = year+"-10-01";
            String month11 = year+"-11-01";
            String month12 = year+"-12-01";
            sql = "select t.*,round((yue1+yue2+yue3+yue4+yue5+yue6+yue7+yue8+yue9+yue10+yue11+yue12)/(trunc(MONTHS_BETWEEN(to_date('"+nowdate+"','yyyy-mm-dd'),to_date('"+month1+"','yyyy-mm-dd')))+1),1) as yjjbss," +
                    "(yue1+yue2+yue3+yue4+yue5+yue6+yue7+yue8+yue9+yue10+yue11+yue12) as yljjbss" +
                    " from (" +
                    "select b.workcode," +
                    "               b.name," +
                    "               b.companyname," +
                    "               b.centername," +
                    "               b.deptname," +
                    "               b.groupname," +
                    "               b.jobtitlename,"+
                    "               b.identitytype," +
                    "               b.worktype,          " +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month1+"',b.worktype) as yue1," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month2+"',b.worktype) as yue2," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month3+"',b.worktype) as yue3," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month4+"',b.worktype) as yue4," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month5+"',b.worktype) as yue5," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month6+"',b.worktype) as yue6," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month7+"',b.worktype) as yue7," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month8+"',b.worktype) as yue8," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month9+"',b.worktype) as yue9," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month10+"',b.worktype) as yue10," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month11+"',b.worktype) as yue11," +
                    "               f_hr_glz_getydjbsj(b.workcode,'"+month12+"',b.worktype) as yue12" +
                    "  from (select rownum as num, t.* " +
                    "          from (select * " +
                    "                  from uf_hr_persondata a" +
                    "                 where (employeetype in ('正式员工', '实习员工') or " +
                    "                       (employeetype = '劳务人员' and joblevel <> '99')) " +
                    "                   and status = '有效' " +
                    "                   and currdeptcode in("+orgcodes+") ";
            if(!"".equals(orgcode)){
                if("false".equals(sfbhxjzz)) {
                    sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=a.currdeptcode)) like Upper('%" + orgcode + "%')";
                }else{
                    sql = sql + " and (Upper(a.companyname) like Upper('%" + orgcode + "%') or Upper(a.centername) like Upper('%" + orgcode + "%') or Upper(a.deptname) like Upper('%" + orgcode + "%') or Upper(a.groupname) like Upper('%" + orgcode + "%'))";

                }
            }
            if(!"".equals(worktype)){
                sql=sql+" and worktype ='"+worktype+"'";
            }
            if(!"".equals(identitytype)){
                sql=sql+" and identitytype = '"+identitytype+"'";
            }

            if(!"".equals(rygh)){
                sql=sql+" and name like '%"+rygh+"%'";
            }

            sql += " order by a.worktype asc,a.companyname asc,a.centername asc,a.deptname asc,a.groupname asc) t ) b ) t";

            rs.execute(sql);
            int indexrow = 2;
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
                cell.setCellValue(Util.null2String(rs.getString("worktype")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("yjjbss")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("yljjbss")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("yue1")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(12);
                cell.setCellValue(Util.null2String(rs.getString("yue2")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(13);
                cell.setCellValue(Util.null2String(rs.getString("yue3")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(14);
                cell.setCellValue(Util.null2String(rs.getString("yue4")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(15);
                cell.setCellValue(Util.null2String(rs.getString("yue5")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(16);
                cell.setCellValue(Util.null2String(rs.getString("yue6")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(17);
                cell.setCellValue(Util.null2String(rs.getString("yue7")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(18);
                cell.setCellValue(Util.null2String(rs.getString("yue8")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(19);
                cell.setCellValue(Util.null2String(rs.getString("yue9")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(20);
                cell.setCellValue(Util.null2String(rs.getString("yue10")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(21);
                cell.setCellValue(Util.null2String(rs.getString("yue11")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(22);
                cell.setCellValue(Util.null2String(rs.getString("yue12")));
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

    @POST
    @Path("/getjbqkhzdata")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 月度累计加班情况明细
     */
    public String getMkData(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        User user = HrmUserVarify.getUser(request, response);
        Map<String, Object> params = ParamUtil.request2Map(request);
        HrUtil hrUtil = new HrUtil();
        Map<String, String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode = personBaseinfo.get("workcode");
        String recordid = personBaseinfo.get("recordid");

        RecordSet rs = new RecordSet();
        String orgcodes = hrUtil.getOrgcodes(workcode, recordid);
        String orgcode = Util.null2String(params.get("orgcode"));
        String sfbhxjzz =  Util.null2String(params.get("sfbhxjzz"));
        String identitytype = Util.null2String(params.get("identitytype"));
        if("0".equals(identitytype)){
            identitytype = "间接人员";
        }else if("1".equals(identitytype)){
            identitytype = "直接人员";
        }
        JSONObject jo = new JSONObject();
        String zrs = "";
        String sql = "select t.*, case when dzhjbsxzss>0 then round(dysqjbzss/dzhjbsxzss*100,2) else 0 end yjbzsszb, case when zrs>0 then round(dysqjbzss/zrs,2) else 0 end rjss " +
                " from (select nvl(sum(nvl(zrs,0)),0) as zrs,nvl(sum(nvl(bzjbsxzss,0)),0) as bzjbsxzss,nvl(sum(nvl(dzhjbsxzss,0)),0) as dzhjbsxzss,nvl(sum(nvl(dysqjbzss,0)),0) as dysqjbzss, " +
                "nvl(sum(nvl(leve1,0)),0) as leve1,nvl(sum(nvl(leve2,0)),0) as leve2,nvl(sum(nvl(leve3,0)),0) as leve3,nvl(sum(nvl(leve4,0)),0) as leve4,nvl(sum(nvl(leve5,0)),0) as leve5,nvl(sum(nvl(leve6,0)),0) as leve6 " +
                "from uf_hr_jbhz_data t where identitytype='"+identitytype+"' and orgcode in ("+orgcodes+") ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=t.orgcode)) like Upper('%" + orgcode + "%')";
            }else{
                String subcode = hrUtil.getOrgcodes3(orgcode);
                sql = sql + " and orgcode in ("+subcode+")";

            }
        }
        sql += " ) t";
        //new BaseBean().writeLog("getMkDataaa:"+sql);
        rs.execute(sql);
        if(rs.next()){
            try {
                jo.put("zrs", Util.null2String(rs.getString("zrs")));//总人数
                jo.put("bzjbsxzss", Util.null2String(rs.getString("bzjbsxzss")));//标准加班受限总时数
                jo.put("dzhjbsxzss", Util.null2String(rs.getString("dzhjbsxzss")));//调整后加班受限总时数
                jo.put("dysqjbzss", Util.null2String(rs.getString("dysqjbzss")));//当月申请加班总时数
                jo.put("yjbzsszb", Util.null2String(rs.getString("yjbzsszb"))+"%");//已加班总时数占比
                jo.put("rjss", Util.null2String(rs.getString("rjss")));//人均时数
                jo.put("leve1", Util.null2String(rs.getString("leve1")));//0-30
                jo.put("leve2", Util.null2String(rs.getString("leve2")));//31-50
                jo.put("leve3", Util.null2String(rs.getString("leve3")));//51-60
                jo.put("leve4", Util.null2String(rs.getString("leve4")));//61-80
                jo.put("leve5", Util.null2String(rs.getString("leve5")));//81-110
                jo.put("leve6", Util.null2String(rs.getString("leve6")));//110+

            }catch (Exception e){
                new BaseBean().writeLog(this.getClass().getName(),e);
            }
        }
        return jo.toString();
    }
}
