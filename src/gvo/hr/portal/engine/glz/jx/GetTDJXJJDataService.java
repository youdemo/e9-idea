package gvo.hr.portal.engine.glz.jx;

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
import java.util.*;

/*
    获取团队绩效信息类
 */
public class GetTDJXJJDataService {
    @GET
    @Path("/getJJJXData")
    @Produces({MediaType.TEXT_PLAIN})
    /*
        获取团队绩效信息
     */
    public String getJXData(@Context HttpServletRequest request, @Context HttpServletResponse response){
        User user= HrmUserVarify.getUser(request,response);
        HrUtil hrUtil=new HrUtil();
        Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode=personBaseinfo.get("workcode");//获取当前登录人工号
        String recordid=personBaseinfo.get("recordid");
        String orgcodes= hrUtil.getOrgcodes(workcode,recordid);
        RecordSet rs = new RecordSet();
        String sql = "";
        Integer page = Integer.valueOf(request.getParameter("page"));//分页第几页
        Integer limit = Integer.valueOf(request.getParameter("limit"));//分页每页几条记录
        Integer startIndex=(page-1)*limit+1;//分页第几条开始
        Integer endIndex=page*limit;//分页第几条结束
        String hworkcode=Util.null2String(request.getParameter("workcode"));//获取搜索条件传过来的工号
        String orgcode = Util.null2String(request.getParameter("orgcode"));//获取搜索条件传过来的组织编号
        String sfbhxjzz = Util.null2String(request.getParameter("sfbhxjzz"));
        try {
            hworkcode=URLDecoder.decode(hworkcode,"UTF-8");
            orgcode=URLDecoder.decode(orgcode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String removefzrcodes = hrUtil.getFzrghs(workcode,orgcodes);

        String khnf = Util.null2String(request.getParameter("khnf"));//获取搜索条件传过来的考核年份
        sql="select * from (" +
                "select t.*,ROWNUM rn from " +
                "(select h.id hid,h.workcode hworkcode,h.name,h.companyname,h.centername,h.centercode,h.deptname,h.deptcode,h.groupname,h.groupcode,h.jobtitlename,h.channel,h.sequence,h.joblevel,h.rjtrq,h.currdeptcode,u.*" +
                " from uf_hr_tdjx_zj u ,uf_hr_persondata h where h.WORKCODE=u.workcode and h.currdeptcode in ("+orgcodes+") and h.status='有效' and h.identitytype='间接人员' and (h.employeetype in ('正式员工','实习员工') or (h.employeetype='劳务人员' and h.joblevel <>'99'))";
        if(!"".equals(removefzrcodes)){
            sql = sql + " and t.workcode not in("+removefzrcodes+") ";
        }
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=h.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(h.companyname) like Upper('%" + orgcode + "%') or Upper(h.centername) like Upper('%" + orgcode + "%') or Upper(h.deptname) like Upper('%" + orgcode + "%') or Upper(h.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        if(!"".equals(hworkcode)){
            sql=sql+" and h.name like '%"+hworkcode+"%'";
        }
        if("".equals(khnf)){
            Calendar date = Calendar.getInstance();
            khnf=String.valueOf(date.get(Calendar.YEAR));
            sql=sql+" and u.khnf='"+khnf+"'";
        }else {
            sql=sql+" and u.khnf='"+khnf+"'";
        }

        sql=sql+" order by h.id asc) t ) where (rn>="+startIndex+" and rn<="+endIndex+")";
        new BaseBean().writeLog(" GetTDJXJJDataService "+sql);
        rs.execute(sql);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        RecordSet rs1 = new RecordSet();
        while(rs.next()) {
            try {
                JSONObject object = new JSONObject();
                object.put("workcode", Util.null2String(rs.getString("hworkcode")));
                object.put("name",Util.null2String(rs.getString("name")));
                object.put("centername",Util.null2String(rs.getString("centername")));
                object.put("companyname",Util.null2String(rs.getString("companyname")));
                object.put("deptname",Util.null2String(rs.getString("deptname")));
                object.put("groupname",Util.null2String(rs.getString("groupname")));
                object.put("jobtitlename",Util.null2String(rs.getString("jobtitlename")));
                object.put("channel",Util.null2String(rs.getString("channel")));
                object.put("sequence",Util.null2String(rs.getString("sequence")));
                object.put("joblevel",Util.null2String(rs.getString("joblevel")));
                object.put("rjtrq",Util.null2String(rs.getString("rjtrq")));
                object.put("khnf",Util.null2String(rs.getString("khnf")));

                object.put("HALFYEAR",Util.null2String(rs.getString("HALFYEAR")));
                object.put("YEAR",Util.null2String(rs.getString("YEAR")));
                object.put("SYQZZJG",Util.null2String(rs.getString("SYQZZJG")));
                jsonArray.put(object);
            } catch (JSONException e) {
                new BaseBean().writeLog(e);
            }
        }
        String totalNum = null;//分页总记录数
        sql="select count(1) as totalnum from uf_hr_tdjx_zj u ,uf_hr_persondata h where h.WORKCODE=u.workcode and h.currdeptcode in ("+orgcodes+") and h.status='有效' and h.identitytype='间接人员' and (h.employeetype in ('正式员工','实习员工') or (h.employeetype='劳务人员' and h.joblevel <>'99'))";
        if(!"".equals(removefzrcodes)){
            sql = sql + " and t.workcode not in("+removefzrcodes+") ";
        }
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=h.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(h.companyname) like Upper('%" + orgcode + "%') or Upper(h.centername) like Upper('%" + orgcode + "%') or Upper(h.deptname) like Upper('%" + orgcode + "%') or Upper(h.groupname) like Upper('%" + orgcode + "%'))";

            }
        }
        if(!"".equals(hworkcode)){
            sql=sql+" and h.name like '%"+hworkcode+"%'";
        }
        if("".equals(khnf)){
            Calendar date = Calendar.getInstance();
            khnf=String.valueOf(date.get(Calendar.YEAR));
            sql=sql+" and u.khnf='"+khnf+"'";
        }else {
            sql=sql+" and u.khnf='"+khnf+"'";
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
            new BaseBean().writeLog(e);
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
            String filename="间接团队绩效";
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
            cell.setCellValue("通道");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("序列");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("职等");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("入集团日期");
            cell.setCellStyle(sheetStyle);

//            cell=row0.createCell(11);
//            cell.setCellValue("改签费");
//            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("考核年份");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(12);
            cell.setCellValue("上半年");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(13);
            cell.setCellValue("年度");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(14);
            cell.setCellValue("试用期最终评议结果");
            cell.setCellStyle(sheetStyle);



            User user= HrmUserVarify.getUser(request,response);
            HrUtil hrUtil=new HrUtil();
            Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
            String workcode=personBaseinfo.get("workcode");
            String recordid=personBaseinfo.get("recordid");
            String  sql = "";
            String orgcodes= hrUtil.getOrgcodes(workcode,recordid);
            String hworkcode=Util.null2String(request.getParameter("workcode"));//获取搜索条件传过来的工号
            String orgcode = Util.null2String(request.getParameter("orgcode"));//获取搜索条件传过来的组织编号
            String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
            try {
                hworkcode=URLDecoder.decode(hworkcode,"UTF-8");
                orgcode=URLDecoder.decode(orgcode,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String removefzrcodes = hrUtil.getFzrghs(workcode,orgcodes);

            sql="select * from (" +
                    "select t.*,ROWNUM rn from " +
                    "(select h.id hid,h.workcode hworkcode,h.name,h.companyname,h.centername,h.centercode,h.deptname,h.deptcode,h.groupname,h.groupcode,h.jobtitlename,h.channel,h.sequence,h.joblevel,h.rjtrq,h.currdeptcode,u.*" +
                    " from uf_hr_tdjx_zj u ,uf_hr_persondata h where h.WORKCODE=u.workcode and h.currdeptcode in ("+orgcodes+") and h.status='有效' and h.identitytype='间接人员' and (h.employeetype in ('正式员工','实习员工') or (h.employeetype='劳务人员' and h.joblevel <>'99')) ";
            if(!"".equals(removefzrcodes)){
                sql = sql + " and t.workcode not in("+removefzrcodes+") ";
            }
            if(!"".equals(orgcode)){
                if("false".equals(sfbhxjzz)) {
                    sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=h.currdeptcode)) like Upper('%" + orgcode + "%')";
                }else{
                    sql = sql + " and (Upper(h.companyname) like Upper('%" + orgcode + "%') or Upper(h.centername) like Upper('%" + orgcode + "%') or Upper(h.deptname) like Upper('%" + orgcode + "%') or Upper(h.groupname) like Upper('%" + orgcode + "%'))";

                }
            }
            if(!"".equals(hworkcode)){
                sql=sql+" and h.name like '%"+hworkcode+"%'";
            }

            sql=sql+" order by h.id asc) t )";
            rs.execute(sql);
            int indexrow = 1;
            RecordSet rs1 = new RecordSet();
            while(rs.next()){
                String workcode_=Util.null2String(rs.getString("hworkcode"));
                Integer khnf_=Integer.valueOf(Util.null2String(rs.getString("khnf")));
                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(rs.getString("hworkcode")));
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
                cell.setCellValue(Util.null2String(rs.getString("channel")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(rs.getString("sequence")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(9);
                cell.setCellValue(Util.null2String(rs.getString("joblevel")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(Util.null2String(rs.getString("rjtrq")));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(11);
                cell.setCellValue(Util.null2String(rs.getString("khnf")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(12);
                cell.setCellValue(Util.null2String(rs.getString("HALFYEAR")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(13);
                cell.setCellValue(Util.null2String(rs.getString("YEAR")));
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(14);
                cell.setCellValue(Util.null2String(rs.getString("SYQZZJG")));
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

