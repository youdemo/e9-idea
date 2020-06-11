package gvo.hr.portal.engine.glz.qxjhz;

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
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author
 * @date 2020/5/12 0012 - 上午 10:21
 */
public class GetQxjhzService {
    @GET
    @Path("/getQxjhzData")
    @Produces({MediaType.TEXT_PLAIN})
    public String getQxjhzData(@Context HttpServletRequest request, @Context HttpServletResponse response){
        User user= HrmUserVarify.getUser(request,response);
        HrUtil hrUtil=new HrUtil();
        Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode=personBaseinfo.get("workcode");
        String recordid=personBaseinfo.get("recordid");
        String orgcodes=null;
        orgcodes = hrUtil.getOrgcodes(workcode, recordid);

        String orgcode = Util.null2String(request.getParameter("orgcode"));
        workcode= Util.null2String(request.getParameter("workcode"));
        String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
        try {
            orgcode=URLDecoder.decode(orgcode,"UTF-8");
            workcode=URLDecoder.decode(workcode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Integer startIndex=(page-1)*limit+1;
        Integer endIndex=page*limit;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray=new JSONArray();
        RecordSet rs = new RecordSet();
        String sql="select workcode aworkcode," +
                " name," +
                " companyname," +
                " centername," +
                " deptname," +
                " groupname," +
                " JOBTITLENAME," +
                " syss," +
                " DNXJZSS," +
                " nf," +
                " yf," +
                " leavetype," +
                " leavetimes," +
                " currdeptcode" +
                "  from" +
                "((select * from(select ROWNUM rn,pd.* from(select * from UF_HR_PERSONDATA where CURRDEPTCODE in ("+orgcodes+") and STATUS='有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))  order by companyname,centername,deptname,groupname asc) pd where 1=1 ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=pd.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(pd.companyname) like Upper('%" + orgcode + "%') or Upper(pd.centername) like Upper('%" + orgcode + "%') or Upper(pd.deptname) like Upper('%" + orgcode + "%') or Upper(pd.groupname) like Upper('%" + orgcode + "%'))";
            }
        }
        if(!"".equals(workcode)){
            sql=sql+" and pd.name like '%"+workcode+"%'";
        }
        sql=sql+") where rn>="+startIndex+" and rn<="+endIndex+") a left join (select b.WORKCODE bworkcode,b.syss,b.dnxjzss,c.nf,c.yf,c.leavetype,c.leavetimes from uf_hr_leaveinfo b,uf_hr_leaveinfo_dt1 c where b.id=c.mainid)d on a.workcode=d.bworkcode)";
//        new BaseBean().writeLog("GetQxjhzService sql"+sql);
        rs.execute(sql);
        Map<String,QxjhzData> qxjhz=new HashMap<>();//key：工号年份
        while(rs.next()){
            String wc=Util.null2String(rs.getString("aworkcode"));
            String nf=Util.null2String(rs.getString("nf"));
            if(qxjhz.containsKey(wc+"-"+nf)){
                QxjhzData qxjhzData = qxjhz.get(wc + "-" + nf);
                Map<String, List<String>> map = qxjhzData.getMap();
                String yf = Util.null2String(rs.getString("yf"));
                String leavetype = Util.null2String(rs.getString("leavetype"));
                String leavetimes = Util.null2String(rs.getString("leavetimes"));
                if(!"".equals(yf)&&map.containsKey(yf)){
                    List<String> list= map.get(yf);
                    list.add(leavetype+"&&"+leavetimes);
                }else{
                    List<String> list = new ArrayList<>();
                    list.add(leavetype+"&&"+leavetimes);
                    map.put(yf,list);
                }
            }else{
                QxjhzData qxjhzData = new QxjhzData();
                qxjhzData.setWorkcode(wc);
                qxjhzData.setNf(nf);
                qxjhzData.setName(Util.null2String(rs.getString("name")));
                qxjhzData.setCompanyname(Util.null2String(rs.getString("companyname")));
                qxjhzData.setCentername(Util.null2String(rs.getString("centername")));
                qxjhzData.setDeptname(Util.null2String(rs.getString("deptname")));
                qxjhzData.setGroupname(Util.null2String(rs.getString("groupname")));
                qxjhzData.setJobtitlename(Util.null2String(rs.getString("jobtitlename")));
                qxjhzData.setSanqi(isSanQi(wc));
                qxjhzData.setYxjhj(Util.null2String(rs.getString("syss")));
                qxjhzData.setDnxjzss(Util.null2String(rs.getString("dnxjzss")));
                Map<String, List<String>> map=new HashMap<>();
                String yf = Util.null2String(rs.getString("yf"));
                String leavetype = Util.null2String(rs.getString("leavetype"));
                String leavetimes = Util.null2String(rs.getString("leavetimes"));
                List<String> list = new ArrayList<>();
                list.add(leavetype+"&&"+leavetimes);
                map.put(yf,list);
                qxjhzData.setMap(map);
                qxjhz.put(wc+"-"+nf,qxjhzData);
            }
        }

        List<QxjhzData> qxjhzData=new ArrayList<>();
        for(QxjhzData q:qxjhz.values()){
            qxjhzData.add(q);
       //     new BaseBean().writeLog("GetQxjhzService  map value"+q.toString());
        }
        for(int i=0;i<(qxjhzData.size()<limit?qxjhzData.size():limit);i++){
            try {
                JSONObject object = new JSONObject();
                BigDecimal dnxjzss = new BigDecimal(0);
                object.put("workcode", qxjhzData.get(i).getWorkcode());
                object.put("name",qxjhzData.get(i).getName());
                object.put("companyname",qxjhzData.get(i).getCompanyname());
                object.put("centername",qxjhzData.get(i).getCentername());
                object.put("deptname",qxjhzData.get(i).getDeptname());
                object.put("groupname",qxjhzData.get(i).getGroupname());
                object.put("jobtitlename",qxjhzData.get(i).getJobtitlename());
                object.put("sanqi",qxjhzData.get(i).getSanqi());
                object.put("yxjhj",qxjhzData.get(i).getYxjhj());
                Map<String, List<String>> map = qxjhzData.get(i).getMap();
                Set<Map.Entry<String, List<String>>> entries = map.entrySet();
                for(Map.Entry<String, List<String>> e:entries){
                    String month = e.getKey();
                    List<String> value = e.getValue();
                    if (!"".equals(month)) {
                        Map<String, String> map1 = judgeLeaveType(value,dnxjzss);
                        String yxj = map1.get("yxj");
                        String wxj = map1.get("wxj");
                        String qt = map1.get("qt");
                        String total = map1.get("total");
                        String dnxjzssStr=map1.get("dnxjzss");
                        if(!"".equals(dnxjzssStr)){
                            dnxjzss = BigDecimal.valueOf(Double.parseDouble(dnxjzssStr));
                        }
                        object.put(month+"yxj","<a href='javascript:jumpYxjxq(&quot;"+qxjhzData.get(i).getWorkcode()+"&quot;,&quot;"+qxjhzData.get(i).getNf()+"&quot;,&quot;"+month+"&quot;)'>"+yxj+"</a>");
                        object.put(month+"wxj","<a href='javascript:jumpYxjxq(&quot;"+qxjhzData.get(i).getWorkcode()+"&quot;,&quot;"+qxjhzData.get(i).getNf()+"&quot;,&quot;"+month+"&quot;)'>"+wxj+"</a>");
                        object.put(month+"qt","<a href='javascript:jumpYxjxq(&quot;"+qxjhzData.get(i).getWorkcode()+"&quot;,&quot;"+qxjhzData.get(i).getNf()+"&quot;,&quot;"+month+"&quot;)'>"+qt+"</a>");
                        object.put(month+"hj","<a href='javascript:jumpYxjxq(&quot;"+qxjhzData.get(i).getWorkcode()+"&quot;,&quot;"+qxjhzData.get(i).getNf()+"&quot;,&quot;"+month+"&quot;)'>"+total+"</a>");
                        //                new BaseBean().writeLog("GetQxjhzService  object"+object.toString());
                    }
                }
                object.put("dnxjzss",dnxjzss);
                jsonArray.put(object);
            } catch (JSONException e) {
               new BaseBean().writeLog(e);
            }
        }
        String totalNum = null;
        sql="select count(id) totalnum from(select ROWNUM rn,pd.* from(select * from UF_HR_PERSONDATA where CURRDEPTCODE in ("+orgcodes+") and STATUS='有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) order by id asc) pd where 1=1";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=pd.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(pd.companyname) like Upper('%" + orgcode + "%') or Upper(pd.centername) like Upper('%" + orgcode + "%') or Upper(pd.deptname) like Upper('%" + orgcode + "%') or Upper(pd.groupname) like Upper('%" + orgcode + "%'))";
            }
        }
        if(!"".equals(workcode)){
            sql=sql+" and pd.name like '%"+workcode+"%'";
        }
        sql=sql+") order by companyname,centername,deptname,groupname asc";
        rs.execute(sql);
        while(rs.next()){
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
    //    new BaseBean().writeLog("GetQxjhzService json"+jsonObject.toString());
        return jsonObject.toString();
    }

    //导出报表
    @GET
    @Path("/qxjhzOutExcel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response weaReportOutExcel(@Context HttpServletRequest request, @Context HttpServletResponse response){
        try {
            User user = HrmUserVarify.getUser(request, response);
            InputStream input = OutExcel(request,response);
            String filename="请休假汇总";
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
            cell.setCellValue("是否为三期人员");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("有薪假余额合计");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("当年休假总时数");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("1月");
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
            CellRangeAddress ll = new CellRangeAddress(0, 0, 10, 13);
            sheet.addMergedRegion(ll);

            cell=row0.createCell(14);
            cell.setCellValue("2月");
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
            CellRangeAddress qq = new CellRangeAddress(0, 0, 14, 17);
            sheet.addMergedRegion(qq);

            cell=row0.createCell(18);
            cell.setCellValue("3月");
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
            CellRangeAddress ww = new CellRangeAddress(0, 0, 18, 21);
            sheet.addMergedRegion(ww);

            cell=row0.createCell(22);
            cell.setCellValue("4月");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(23);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(24);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(25);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress rr = new CellRangeAddress(0, 0, 22, 25);
            sheet.addMergedRegion(rr);

            cell=row0.createCell(26);
            cell.setCellValue("5月");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(27);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(28);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(29);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress tt = new CellRangeAddress(0, 0, 26, 29);
            sheet.addMergedRegion(tt);

            cell=row0.createCell(30);
            cell.setCellValue("6月");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(31);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(32);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(33);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress yy = new CellRangeAddress(0, 0, 30, 33);
            sheet.addMergedRegion(yy);

            cell=row0.createCell(34);
            cell.setCellValue("7月");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(35);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(36);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(37);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress uu = new CellRangeAddress(0, 0, 34, 37);
            sheet.addMergedRegion(uu);

            cell=row0.createCell(38);
            cell.setCellValue("8月");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(39);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(40);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(41);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress ii = new CellRangeAddress(0, 0, 38, 41);
            sheet.addMergedRegion(ii);

            cell=row0.createCell(42);
            cell.setCellValue("9月");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(43);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(44);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(45);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress oo = new CellRangeAddress(0, 0, 42, 45);
            sheet.addMergedRegion(oo);

            cell=row0.createCell(46);
            cell.setCellValue("10月");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(47);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(48);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(49);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress pp = new CellRangeAddress(0, 0, 46, 49);
            sheet.addMergedRegion(pp);

            cell=row0.createCell(50);
            cell.setCellValue("11月");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(51);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(52);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(53);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress zz = new CellRangeAddress(0, 0, 50, 53);
            sheet.addMergedRegion(zz);

            cell=row0.createCell(54);
            cell.setCellValue("12月");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(55);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(56);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(57);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress vv = new CellRangeAddress(0, 0, 54, 57);
            sheet.addMergedRegion(vv);

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
            cell=row1.createCell(8);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(9);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress aa = new CellRangeAddress(0, 1, 0, 0);
            sheet.addMergedRegion(aa);
            CellRangeAddress bb = new CellRangeAddress(0, 1, 1, 1);
            sheet.addMergedRegion(bb);
            CellRangeAddress cc = new CellRangeAddress(0, 1, 2, 2);
            sheet.addMergedRegion(cc);
            CellRangeAddress dd = new CellRangeAddress(0, 1, 3, 3);
            sheet.addMergedRegion(dd);
            CellRangeAddress ee = new CellRangeAddress(0, 1, 4, 4);
            sheet.addMergedRegion(ee);
            CellRangeAddress ff = new CellRangeAddress(0, 1, 5, 5);
            sheet.addMergedRegion(ff);
            CellRangeAddress gg = new CellRangeAddress(0, 1, 6, 6);
            sheet.addMergedRegion(gg);
            CellRangeAddress hh = new CellRangeAddress(0, 1, 7, 7);
            sheet.addMergedRegion(hh);
            CellRangeAddress jj = new CellRangeAddress(0, 1, 8, 8);
            sheet.addMergedRegion(jj);
            CellRangeAddress kk = new CellRangeAddress(0, 1, 9, 9);
            sheet.addMergedRegion(kk);
            //1
            cell=row1.createCell(10);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(11);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(12);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(13);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//2
            cell=row1.createCell(14);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(15);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(16);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(17);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//3
            cell=row1.createCell(18);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(19);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(20);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(21);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//4
            cell=row1.createCell(22);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(23);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(24);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(25);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//5
            cell=row1.createCell(26);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(27);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(28);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(29);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//6
            cell=row1.createCell(30);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(31);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(32);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(33);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//7
            cell=row1.createCell(34);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(35);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(36);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(37);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//8
            cell=row1.createCell(38);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(39);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(40);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(41);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//9
            cell=row1.createCell(42);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(43);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(44);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(45);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//10
            cell=row1.createCell(46);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(47);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(48);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(49);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//11
            cell=row1.createCell(50);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(51);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(52);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(53);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);
//12
            cell=row1.createCell(54);
            cell.setCellValue("有薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(55);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(56);
            cell.setCellValue("其他");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(57);
            cell.setCellValue("合计");
            cell.setCellStyle(sheetStyle);

            User user= HrmUserVarify.getUser(request,response);
            HrUtil hrUtil=new HrUtil();
            Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
            String workcode=personBaseinfo.get("workcode");
            String recordid=personBaseinfo.get("recordid");
            String orgcodes=null;
            orgcodes = hrUtil.getOrgcodes(workcode, recordid);

            String orgcode = Util.null2String(request.getParameter("orgcode"));
            workcode= Util.null2String(request.getParameter("workcode"));
            String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
            orgcode=URLDecoder.decode(orgcode,"UTF-8");
            workcode=URLDecoder.decode(workcode,"UTF-8");
            String sql="select workcode aworkcode," +
                    " name," +
                    " companyname," +
                    " centername," +
                    " deptname," +
                    " groupname," +
                    " JOBTITLENAME," +
                    " syss," +
                    " DNXJZSS," +
                    " nf," +
                    " yf," +
                    " leavetype," +
                    " leavetimes," +
                    " currdeptcode" +
                    "  from" +
                    "((select * from(select ROWNUM rn,pd.* from(select * from UF_HR_PERSONDATA where CURRDEPTCODE in ("+orgcodes+") and STATUS='有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))  order by companyname,centername,deptname,groupname asc) pd where 1=1 ";
            if(!"".equals(orgcode)){
                if("false".equals(sfbhxjzz)) {
                    sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=pd.currdeptcode)) like Upper('%" + orgcode + "%')";
                }else{
                    sql = sql + " and (Upper(pd.companyname) like Upper('%" + orgcode + "%') or Upper(pd.centername) like Upper('%" + orgcode + "%') or Upper(pd.deptname) like Upper('%" + orgcode + "%') or Upper(pd.groupname) like Upper('%" + orgcode + "%'))";
                }
            }
            if(!"".equals(workcode)){
                sql=sql+" and pd.name like '%"+workcode+"%'";
            }
            sql=sql+")) a left join (select b.WORKCODE bworkcode,b.syss,b.dnxjzss,c.nf,c.yf,c.leavetype,c.leavetimes from uf_hr_leaveinfo b,uf_hr_leaveinfo_dt1 c where b.id=c.mainid)d on a.workcode=d.bworkcode)";
            rs.execute(sql);
            Map<String,QxjhzData> qxjhz=new HashMap<>();//key：工号年份
            //讲sql查询结果放入qxjhz的map中，工号年份为key
            while(rs.next()){
                String wc=Util.null2String(rs.getString("aworkcode"));
                String nf=Util.null2String(rs.getString("nf"));
                //根据工号年份判断map中是否有数据，如果有获取QXJHZData实体类的map,存放的是一个人某一年所有的请假情况，属性key是月份，value是leavetype-leavetimes,
                if(qxjhz.containsKey(wc+"-"+nf)){
                    QxjhzData qxjhzData = qxjhz.get(wc + "-" + nf);
                    Map<String, List<String>> map = qxjhzData.getMap();
                    String yf = Util.null2String(rs.getString("yf"));
                    String leavetype = Util.null2String(rs.getString("leavetype"));
                    String leavetimes = Util.null2String(rs.getString("leavetimes"));
                    if(!"".equals(yf)&&map.containsKey(yf)){//判断实例类中的map是否有当前月份的数据，如果有则在list中追加leavetype-leavetimes
                        List<String> list= map.get(yf);
                        list.add(leavetype+"&&"+leavetimes);
                    }else{//没有当月数据就新建
                        List<String> list = new ArrayList<>();
                        list.add(leavetype+"&&"+leavetimes);
                        map.put(yf,list);
                    }
                }else{
                    QxjhzData qxjhzData = new QxjhzData();
                    qxjhzData.setWorkcode(wc);
                    qxjhzData.setNf(nf);
                    qxjhzData.setName(Util.null2String(rs.getString("name")));
                    qxjhzData.setCompanyname(Util.null2String(rs.getString("companyname")));
                    qxjhzData.setCentername(Util.null2String(rs.getString("centername")));
                    qxjhzData.setDeptname(Util.null2String(rs.getString("deptname")));
                    qxjhzData.setGroupname(Util.null2String(rs.getString("groupname")));
                    qxjhzData.setJobtitlename(Util.null2String(rs.getString("jobtitlename")));
                    qxjhzData.setSanqi(isSanQi(wc));
                    qxjhzData.setYxjhj(Util.null2String(rs.getString("syss")));
                    qxjhzData.setDnxjzss(Util.null2String(rs.getString("dnxjzss")));
                    Map<String, List<String>> map=new HashMap<>();
                    String yf = Util.null2String(rs.getString("yf"));
                    String leavetype = Util.null2String(rs.getString("leavetype"));
                    String leavetimes = Util.null2String(rs.getString("leavetimes"));
                    List<String> list = new ArrayList<>();
                    list.add(leavetype+"&&"+leavetimes);
                    map.put(yf,list);
                    qxjhzData.setMap(map);
                    qxjhz.put(wc+"-"+nf,qxjhzData);
                }
            }

            List<QxjhzData> qxjhzData=new ArrayList<>();
            //讲sql中查询出来放入map的数据的值放入上面list中
            for(QxjhzData q:qxjhz.values()){
                qxjhzData.add(q);
                //     new BaseBean().writeLog("GetQxjhzService  map value"+q.toString());
            }

            int indexrow = 2;
            for(int i=0;i<qxjhzData.size();i++){//对上面的list遍历

                BigDecimal dnxjzss = new BigDecimal(0);//当年休假时长

                HSSFRow rowdt=sheet.createRow(indexrow);
                cell=rowdt.createCell(0);
                cell.setCellValue(Util.null2String(qxjhzData.get(i).getWorkcode()));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(Util.null2String(qxjhzData.get(i).getName()));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(Util.null2String(qxjhzData.get(i).getCompanyname()));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(Util.null2String(qxjhzData.get(i).getCentername()));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(Util.null2String(qxjhzData.get(i).getDeptname()));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(Util.null2String(qxjhzData.get(i).getGroupname()));
                cell.setCellStyle(sheetStyle2);


                cell=rowdt.createCell(6);
                cell.setCellValue(Util.null2String(qxjhzData.get(i).getJobtitlename()));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(Util.null2String(qxjhzData.get(i).getSanqi()));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(Util.null2String(qxjhzData.get(i).getYxjhj()));
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(9);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(11);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(12);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(13);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(14);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(15);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(16);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(17);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(18);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(19);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(20);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(21);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(22);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(23);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(24);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(25);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(26);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(27);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(28);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(29);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(30);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(31);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(32);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(33);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(34);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(35);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(36);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(37);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(38);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(39);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(40);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(41);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(42);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(43);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(44);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(45);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(46);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(47);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(48);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(49);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(50);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(51);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(52);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(53);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(54);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(55);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(56);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);
                cell=rowdt.createCell(57);
                cell.setCellValue("");
                cell.setCellStyle(sheetStyle2);

                Map<String, List<String>> map = qxjhzData.get(i).getMap();//对一个人某年的所有请假信息遍历，key月份，value请假类型-请假时长
                Set<Map.Entry<String, List<String>>> entries = map.entrySet();
                for(Map.Entry<String, List<String>> e:entries){
                    String month = e.getKey();//月份
                    List<String> value = e.getValue();//请假类型-请假时长
                    if (!"".equals(month)) {
                        Map<String, String> map1 = judgeLeaveType(value,dnxjzss);//计算当前月有薪假无薪假合计其他,传入的第二个参数是计算当年休假时长
                        String yxj = map1.get("yxj");
                        String wxj = map1.get("wxj");
                        String qt = map1.get("qt");
                        String total = map1.get("total");
                        String dnxjzssStr=map1.get("dnxjzss");
                        if(!"".equals(dnxjzssStr)){
                            dnxjzss = BigDecimal.valueOf(Double.parseDouble(dnxjzssStr));
                        }
                        if(month.contains("01")){
                            cell=rowdt.getCell(10);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(11);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(12);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(13);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("02")){
                            cell=rowdt.getCell(14);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(15);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(16);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(17);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("03")){
                            cell=rowdt.getCell(18);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(19);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(20);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(21);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("04")){
                            cell=rowdt.getCell(22);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(23);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(24);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(25);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("05")){
                            cell=rowdt.getCell(26);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(27);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(28);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(29);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("06")){
                            cell=rowdt.getCell(30);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(31);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(32);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(33);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("07")){
                            cell=rowdt.getCell(34);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(35);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(36);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(37);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("08")){
                            cell=rowdt.getCell(38);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(39);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(40);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(41);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("09")){
                            cell=rowdt.getCell(42);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(43);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(44);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(45);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("10")){
                            cell=rowdt.getCell(46);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(47);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(48);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(49);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("11")){
                            cell=rowdt.getCell(50);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(51);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(52);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(53);
                            cell.setCellValue(Util.null2String(total));
                        }else if(month.contains("12")){
                            cell=rowdt.getCell(54);
                            cell.setCellValue(Util.null2String(yxj));
                            cell=rowdt.getCell(55);
                            cell.setCellValue(Util.null2String(wxj));
                            cell=rowdt.getCell(56);
                            cell.setCellValue(Util.null2String(qt));
                            cell=rowdt.getCell(57);
                            cell.setCellValue(Util.null2String(total));
                        }else{

                        }
                    }
                }

                cell=rowdt.getCell(9);//当年所有月份的休假时长计算后取出当年休假时长
                cell.setCellValue(Util.null2String(dnxjzss.toString()));

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


    public String isSanQi(String wc){//判断是否三期人员
        RecordSet recordSet=new RecordSet();
        String sql="select work_code from uf_hr_baby where WORK_CODE='"+wc+"'";
        recordSet.execute(sql);
        if(recordSet.next()){
            return "是";
        }else{
            return "否";
        }
    }

    //
    public Map<String,String> judgeLeaveType(List<String> list,BigDecimal dnxjzss){
        Map<String,String> map=new HashMap<>();
        Map<String, String> stringMap = computeLeaveTimes(list);
        String yxj = stringMap.get("yxj");
        String wxj = stringMap.get("wxj");
        String qt = stringMap.get("qt");
        String total = stringMap.get("total");
        String dnxjzssStr = stringMap.get("dnxjzss");
        dnxjzss=dnxjzss.add(BigDecimal.valueOf(Double.parseDouble(dnxjzssStr)));
        map.put("yxj",yxj);
        map.put("wxj",wxj);
        map.put("qt",qt);
        map.put("total",total);
        map.put("dnxjzss",dnxjzss.toString());
        return map;
    }


    @GET
    @Path("/getYxjxqData")
    @Produces({MediaType.TEXT_PLAIN})
    public String getYxjxqData(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        User user = HrmUserVarify.getUser(request, response);
        HrUtil hrUtil = new HrUtil();
        Map<String, String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode = personBaseinfo.get("workcode");
        String recordid = personBaseinfo.get("recordid");
        String orgcodes = null;
        orgcodes = hrUtil.getOrgcodes(workcode, recordid);

        String orgcode = Util.null2String(request.getParameter("orgcode"));
        workcode = Util.null2String(request.getParameter("workcode"));
        try {
            orgcode=URLDecoder.decode(orgcode,"UTF-8");
            workcode=URLDecoder.decode(workcode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String currNf = Util.null2String(request.getParameter("currNf"));
        String currYf = Util.null2String(request.getParameter("currYf"));
        String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
        String currWc = Util.null2String(request.getParameter("currWc"));
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Integer startIndex = (page - 1) * limit + 1;
        Integer endIndex = page * limit;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        RecordSet rs = new RecordSet();

        String sql="select workcode aworkcode," +
                " name," +
                " companyname," +
                " centername," +
                " deptname," +
                " groupname," +
                " JOBTITLENAME," +
                " syss," +
                " DNXJZSS," +
                " nf," +
                " yf," +
                " leavetype," +
                " leavetimes," +
                " currdeptcode" +
                " from" +
                "((select * from(select ROWNUM rn,pd.* from(select * from UF_HR_PERSONDATA where CURRDEPTCODE in ("+orgcodes+") and STATUS='有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))  order by companyname,centername,deptname,groupname asc) pd where 1=1";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=pd.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(pd.companyname) like Upper('%" + orgcode + "%') or Upper(pd.centername) like Upper('%" + orgcode + "%') or Upper(pd.deptname) like Upper('%" + orgcode + "%') or Upper(pd.groupname) like Upper('%" + orgcode + "%'))";
            }
        }
        if(!"".equals(workcode)){
            sql=sql+" and pd.name like '%"+workcode+"%'";
        }
        if(!"".equals(currWc)){
            sql=sql+" and pd.workcode='"+currWc+"'";
        }
        sql=sql+") where rn>="+startIndex+" and rn<="+endIndex+") a left join (select b.WORKCODE bworkcode,b.syss,b.dnxjzss,c.nf,c.yf,c.leavetype,c.leavetimes from uf_hr_leaveinfo b,uf_hr_leaveinfo_dt1 c where b.id=c.mainid";
        if(!"".equals(currNf)){
            sql=sql+" and c.nf='"+currNf+"'";
        }
        if(!"".equals(currYf)){
            sql=sql+" and c.yf='"+currYf+"'";
        }
        sql=sql+")d on a.workcode=d.bworkcode)";

//       new BaseBean().writeLog("YxjxqData   sql: "+sql);
        rs.execute(sql);
        Map<String,QxjhzData> qxjhz=new HashMap<>();//key：工号年份月份
        while(rs.next()){
            String wc=Util.null2String(rs.getString("aworkcode"));
            String nf=Util.null2String(rs.getString("nf"));
            String yf=Util.null2String(rs.getString("yf"));
            if(qxjhz.containsKey(wc+"-"+nf+"-"+yf)){
                QxjhzData qxjhzData = qxjhz.get(wc + "-" + nf+"-"+yf);
                Map<String, List<String>> map = qxjhzData.getMap();
                Set<Map.Entry<String, List<String>>> entries = map.entrySet();
                for(Map.Entry<String, List<String>> e:entries){
                    String key = e.getKey();
                    String leavetype = Util.null2String(rs.getString("leavetype"));
                    String leavetimes = Util.null2String(rs.getString("leavetimes"));
                    if(!yf.equals("")&&!key.equals(yf)){
                        List<String> list = new ArrayList<>();
                        list.add(leavetype+"&&"+leavetimes);
                        map.put(yf,list);
                    }else{
                        List<String> list = map.get(yf);
                        list.add(leavetype+"&&"+leavetimes);
                    }
                }
            }else{
                QxjhzData qxjhzData = new QxjhzData();
                qxjhzData.setWorkcode(wc);
                qxjhzData.setName(Util.null2String(rs.getString("name")));
                qxjhzData.setCompanyname(Util.null2String(rs.getString("companyname")));
                qxjhzData.setCentername(Util.null2String(rs.getString("centername")));
                qxjhzData.setDeptname(Util.null2String(rs.getString("deptname")));
                qxjhzData.setGroupname(Util.null2String(rs.getString("groupname")));
                qxjhzData.setJobtitlename(Util.null2String(rs.getString("jobtitlename")));
                qxjhzData.setSanqi(isSanQi(wc));
                qxjhzData.setYxjhj(Util.null2String(rs.getString("syss")));
                qxjhzData.setDnxjzss(Util.null2String(rs.getString("dnxjzss")));
                Map<String, List<String>> map=new HashMap<>();
                String leavetype = Util.null2String(rs.getString("leavetype"));
                String leavetimes = Util.null2String(rs.getString("leavetimes"));
                List<String> list = new ArrayList<>();
                list.add(leavetype+"&&"+leavetimes);
                map.put(yf,list);
                qxjhzData.setMap(map);
                qxjhz.put(wc+"-"+nf+"-"+yf,qxjhzData);
            }
        }
        List<QxjhzData> qxjhzData=new ArrayList<>();
        for(QxjhzData q:qxjhz.values()){
            qxjhzData.add(q);
        }
        for(int i=0;i<(qxjhzData.size()<limit?qxjhzData.size():limit);i++){
            try {
                JSONObject object = new JSONObject();
                object.put("workcode", qxjhzData.get(i).getWorkcode());
                object.put("name",qxjhzData.get(i).getName());
                object.put("companyname",qxjhzData.get(i).getCompanyname());
                object.put("centername",qxjhzData.get(i).getCentername());
                object.put("deptname",qxjhzData.get(i).getDeptname());
                object.put("groupname",qxjhzData.get(i).getGroupname());
                object.put("jobtitlename",qxjhzData.get(i).getJobtitlename());
                object.put("sanqi",qxjhzData.get(i).getSanqi());

                Map<String, List<String>> map = qxjhzData.get(i).getMap();
                Set<Map.Entry<String, List<String>>> entries = map.entrySet();
                for(Map.Entry<String, List<String>> e:entries){
                    String month = e.getKey();
                    List<String> value = e.getValue();
                    if (!"".equals(month)) {
                        object.put("currYf",month);
                        Map<String, String> map1 = computeLeaveTimes(value);
                        object.put("nj",map1.get("nj"));
                        object.put("ewtxj",map1.get("ewtxj"));
                        object.put("txj",map1.get("txj"));
                        object.put("tqj",map1.get("tqj"));
                        object.put("brj",map1.get("brj"));
                        object.put("hj",map1.get("hj"));
                        object.put("cjj",map1.get("cjj"));
                        object.put("cj",map1.get("cj"));
                        object.put("pcj",map1.get("pcj"));
                        object.put("ncj",map1.get("ncj"));
                        object.put("lcj",map1.get("lcj"));
                        object.put("bbtj",map1.get("bbtj"));
                        object.put("jyssj",map1.get("jyssj"));
                        object.put("gsj",map1.get("gsj"));
                        object.put("sj",map1.get("sj"));
                        object.put("zj",map1.get("zj"));
                        object.put("bj",map1.get("bj"));
                        object.put("fxj",map1.get("fxj"));
                        object.put("gj",map1.get("gj"));
                        object.put("gc",map1.get("gc"));
                        object.put("ccjtw",map1.get("ccjtw"));
                        object.put("ccjtn",map1.get("ccjtn"));
                    }
                }
                jsonArray.put(object);
            } catch (JSONException e) {
                new BaseBean().writeLog(e);
            }
        }
        String totalNum = null;
        sql="select count(workcode) totalnum"+
                " from" +
                "((select * from(select ROWNUM rn,pd.* from(select * from UF_HR_PERSONDATA where CURRDEPTCODE in ("+orgcodes+") and STATUS='有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))  order by companyname,centername,deptname,groupname asc) pd where 1=1";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=pd.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(pd.companyname) like Upper('%" + orgcode + "%') or Upper(pd.centername) like Upper('%" + orgcode + "%') or Upper(pd.deptname) like Upper('%" + orgcode + "%') or Upper(pd.groupname) like Upper('%" + orgcode + "%'))";
            }
        }
        if(!"".equals(workcode)){
            sql=sql+" and pd.name like '%"+workcode+"%'";
        }
        if(!"".equals(currWc)){
            sql=sql+" and pd.workcode='"+currWc+"'";
        }
        sql=sql+")) a left join (select b.WORKCODE bworkcode,b.syss,b.dnxjzss,c.nf,c.yf,c.leavetype,c.leavetimes from uf_hr_leaveinfo b,uf_hr_leaveinfo_dt1 c where b.id=c.mainid";
        if(!"".equals(currNf)){
            sql=sql+" and c.nf='"+currNf+"'";
        }
        if(!"".equals(currYf)){
            sql=sql+" and c.yf='"+currYf+"'";
        }
        sql=sql+")d on a.workcode=d.bworkcode)";
        rs.execute(sql);
        while(rs.next()){
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

    public Map<String,String> computeLeaveTimes(List<String> list){
        BigDecimal nj = new BigDecimal(0);
        BigDecimal ewtxj = new BigDecimal(0);
        BigDecimal txj = new BigDecimal(0);
        BigDecimal tqj = new BigDecimal(0);
        BigDecimal brj = new BigDecimal(0);
        BigDecimal hj = new BigDecimal(0);
        BigDecimal cjj = new BigDecimal(0);
        BigDecimal cj = new BigDecimal(0);
        BigDecimal pcj = new BigDecimal(0);
        BigDecimal ncj = new BigDecimal(0);
        BigDecimal lcj = new BigDecimal(0);
        BigDecimal bbtj = new BigDecimal(0);
        BigDecimal jyssj = new BigDecimal(0);
        BigDecimal gsj = new BigDecimal(0);
        BigDecimal sj = new BigDecimal(0);
        BigDecimal zj = new BigDecimal(0);
        BigDecimal bj = new BigDecimal(0);
        BigDecimal fxj = new BigDecimal(0);
        BigDecimal gj = new BigDecimal(0);
        BigDecimal gc = new BigDecimal(0);
        BigDecimal ccjtw = new BigDecimal(0);
        BigDecimal ccjtn = new BigDecimal(0);
        BigDecimal yxj = new BigDecimal(0);
        BigDecimal wxj = new BigDecimal(0);
        BigDecimal qt = new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);
        BigDecimal dnxjzss = new BigDecimal(0);
        Map<String,String> map=new HashMap<>();
        for(String s:list){
            String[] split = s.split("&&");
            String leavetype = split[0];
            BigDecimal leavetimes = new BigDecimal(split[1]);
            if (!"".equals(leavetype)&&!"".equals(leavetimes)) {
                if(leavetype.contains("年")){
                    nj=nj.add(leavetimes);
                }else if(leavetype.contains("额外调休")){
                    ewtxj=ewtxj.add(leavetimes);
                }else if(leavetype.contains("调休")){
                    txj=txj.add(leavetimes);
                }else if(leavetype.contains("探亲")){
                    tqj=tqj.add(leavetimes);
                }else if(leavetype.contains("哺乳")){
                    brj=brj.add(leavetimes);
                }else if(leavetype.contains("婚")){
                    hj=hj.add(leavetimes);
                }else if(leavetype.contains("产检")){
                    cjj=cjj.add(leavetimes);
                }else if(leavetype.contains("产")){
                    cj=cj.add(leavetimes);
                }else if(leavetype.contains("陪产")){
                    pcj=pcj.add(leavetimes);
                }else if(leavetype.contains("难产")){
                    ncj=ncj.add(leavetimes);
                }else if(leavetype.contains("流产")){
                    lcj=lcj.add(leavetimes);
                }else if(leavetype.contains("多胞胎")){
                    bbtj=bbtj.add(leavetimes);
                }else if(leavetype.contains("节育手术")){
                    jyssj=jyssj.add(leavetimes);
                }else if(leavetype.contains("工伤")){
                    gsj=gsj.add(leavetimes);
                }else if(leavetype.contains("丧")){
                    sj=sj.add(leavetimes);
                }else if(leavetype.contains("事")){
                    zj=zj.add(leavetimes);
                }else if(leavetype.contains("病")){
                    bj=bj.add(leavetimes);
                }else if(leavetype.contains("返校")){
                    fxj=fxj.add(leavetimes);
                }else if(leavetype.contains("公")){
                    gj=gj.add(leavetimes);
                }else if(leavetype.contains("公出")){
                    gc=gc.add(leavetimes);
                }else if(leavetype.contains("集团外")){
                    ccjtw=ccjtw.add(leavetimes);
                }else if(leavetype.contains("集团内")){
                    ccjtn=ccjtn.add(leavetimes);
                }else{

                }
            }
        }
        map.put("nj",nj.toString());
        map.put("ewtxj",ewtxj.toString());
        map.put("txj",txj.toString());
        map.put("tqj",tqj.toString());
        map.put("brj",brj.toString());
        map.put("hj",hj.toString());
        map.put("cjj",cjj.toString());
        map.put("cj",cj.toString());
        map.put("pcj",pcj.toString());
        map.put("ncj",ncj.toString());
        map.put("lcj",lcj.toString());
        map.put("bbtj",bbtj.toString());
        map.put("jyssj",jyssj.toString());
        map.put("gsj",gsj.toString());
        map.put("sj",sj.toString());

        map.put("zj",zj.toString());
        map.put("bj",bj.toString());
        map.put("fxj",fxj.toString());

        map.put("gj",gj.toString());
        map.put("gc",gc.toString());
        map.put("ccjtw",ccjtw.toString());
        map.put("ccjtn",ccjtn.toString());
        yxj = yxj.add(nj).add(ewtxj).add(txj).add(tqj).add(brj).add(hj).add(cjj).add(cj).add(pcj).add(ncj).add(lcj).add(bbtj).add(jyssj).add(gsj).add(sj);
        wxj = wxj.add(zj).add(bj).add(fxj);
        qt = qt.add(gj).add(gc).add(ccjtw).add(ccjtn);
        dnxjzss = dnxjzss.add(yxj).add(wxj);
        total = total.add(yxj).add(wxj).add(qt);
        map.put("yxj",yxj.toString());
        map.put("wxj",wxj.toString());
        map.put("qt",qt.toString());
        map.put("dnxjzss",dnxjzss.toString());
        map.put("total",total.toString());
        return map;
    }


    //导出报表
    @GET
    @Path("/yxjxqOutExcel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response weaReportOutExcel1(@Context HttpServletRequest request, @Context HttpServletResponse response){
        try {
            User user = HrmUserVarify.getUser(request, response);
            InputStream input = OutExcel1(request,response);
            String filename="已休假详情";
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
    public InputStream OutExcel1(HttpServletRequest request, HttpServletResponse response) {
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


        User user= HrmUserVarify.getUser(request,response);
        HrUtil hrUtil=new HrUtil();
        Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode=personBaseinfo.get("workcode");
        String recordid=personBaseinfo.get("recordid");

        String currNf = Util.null2String(request.getParameter("currNf"));
        String currYf = Util.null2String(request.getParameter("currYf"));
        String currWc = Util.null2String(request.getParameter("currWc"));
        String orgcodes=null;
        orgcodes = hrUtil.getOrgcodes(workcode, recordid);

        String orgcode = Util.null2String(request.getParameter("orgcode"));
        workcode= Util.null2String(request.getParameter("workcode"));
        String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
        try {
            orgcode=URLDecoder.decode(orgcode,"UTF-8");
            workcode=URLDecoder.decode(workcode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sql="select workcode aworkcode," +
                " name," +
                " companyname," +
                " centername," +
                " deptname," +
                " groupname," +
                " JOBTITLENAME," +
                " syss," +
                " DNXJZSS," +
                " nf," +
                " yf," +
                " leavetype," +
                " leavetimes," +
                " currdeptcode" +
                " from" +
                "((select * from(select ROWNUM rn,pd.* from(select * from UF_HR_PERSONDATA where CURRDEPTCODE in ("+orgcodes+") and STATUS='有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))  order by companyname,centername,deptname,groupname asc) pd where 1=1 ";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=pd.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(pd.companyname) like Upper('%" + orgcode + "%') or Upper(pd.centername) like Upper('%" + orgcode + "%') or Upper(pd.deptname) like Upper('%" + orgcode + "%') or Upper(pd.groupname) like Upper('%" + orgcode + "%'))";
            }
        }
        if(!"".equals(workcode)){
            sql=sql+" and pd.name like '%"+workcode+"%'";
        }
        if(!"".equals(currWc)){
            sql=sql+" and pd.workcode='"+currWc+"'";
        }
        sql=sql+")) a left join (select b.WORKCODE bworkcode,b.syss,b.dnxjzss,c.nf,c.yf,c.leavetype,c.leavetimes from uf_hr_leaveinfo b,uf_hr_leaveinfo_dt1 c where b.id=c.mainid";
        if(!"".equals(currNf)){
            sql=sql+" and c.nf='"+currNf+"'";
        }
        if(!"".equals(currYf)){
            sql=sql+" and c.yf='"+currYf+"'";
        }
        sql=sql+")d on a.workcode=d.bworkcode)";
        //       new BaseBean().writeLog("YxjxqData   sql: "+sql);
        rs.execute(sql);
        Map<String,QxjhzData> qxjhz=new HashMap<>();//key：工号年份月份
        while(rs.next()){
            String wc=Util.null2String(rs.getString("aworkcode"));
            String nf=Util.null2String(rs.getString("nf"));
            String yf=Util.null2String(rs.getString("yf"));
            if(qxjhz.containsKey(wc+"-"+nf+"-"+yf)){
                QxjhzData qxjhzData = qxjhz.get(wc + "-" + nf+"-"+yf);
                Map<String, List<String>> map = qxjhzData.getMap();
                Set<Map.Entry<String, List<String>>> entries = map.entrySet();
                for(Map.Entry<String, List<String>> e:entries){
                    String key = e.getKey();
                    String leavetype = Util.null2String(rs.getString("leavetype"));
                    String leavetimes = Util.null2String(rs.getString("leavetimes"));
                    if(!yf.equals("")&&!key.equals(yf)){
                        List<String> list = new ArrayList<>();
                        list.add(leavetype+"&&"+leavetimes);
                        map.put(yf,list);
                    }else{
                        List<String> list = map.get(yf);
                        list.add(leavetype+"&&"+leavetimes);
                    }
                }
            }else{
                QxjhzData qxjhzData = new QxjhzData();
                qxjhzData.setWorkcode(wc);
                qxjhzData.setName(Util.null2String(rs.getString("name")));
                qxjhzData.setCompanyname(Util.null2String(rs.getString("companyname")));
                qxjhzData.setCentername(Util.null2String(rs.getString("centername")));
                qxjhzData.setDeptname(Util.null2String(rs.getString("deptname")));
                qxjhzData.setGroupname(Util.null2String(rs.getString("groupname")));
                qxjhzData.setJobtitlename(Util.null2String(rs.getString("jobtitlename")));
                qxjhzData.setSanqi(isSanQi(wc));
                qxjhzData.setYxjhj(Util.null2String(rs.getString("syss")));
                qxjhzData.setDnxjzss(Util.null2String(rs.getString("dnxjzss")));
                Map<String, List<String>> map=new HashMap<>();
                String leavetype = Util.null2String(rs.getString("leavetype"));
                String leavetimes = Util.null2String(rs.getString("leavetimes"));
                List<String> list = new ArrayList<>();
                list.add(leavetype+"&&"+leavetimes);
                map.put(yf,list);
                qxjhzData.setMap(map);
                qxjhz.put(wc+"-"+nf+"-"+yf,qxjhzData);
            }
        }
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
            cell.setCellValue("是否为三期人员");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("月份");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("有薪假");
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
            cell=row0.createCell(14);
            cell.setCellValue("");
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
            cell=row0.createCell(18);
            cell.setCellValue("");
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
            cell=row0.createCell(22);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(23);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress kk = new CellRangeAddress(0, 0, 9, 23);
            sheet.addMergedRegion(kk);

            cell=row0.createCell(24);
            cell.setCellValue("无薪假");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(25);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(26);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress ll = new CellRangeAddress(0, 0, 24, 26);
            sheet.addMergedRegion(ll);

            cell=row0.createCell(27);
            cell.setCellValue("其他假");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(28);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(29);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            cell=row0.createCell(30);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress qq = new CellRangeAddress(0, 0, 27, 30);
            sheet.addMergedRegion(qq);

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
            cell=row1.createCell(8);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle);
            CellRangeAddress aa = new CellRangeAddress(0, 1, 0, 0);
            sheet.addMergedRegion(aa);
            CellRangeAddress bb = new CellRangeAddress(0, 1, 1, 1);
            sheet.addMergedRegion(bb);
            CellRangeAddress cc = new CellRangeAddress(0, 1, 2, 2);
            sheet.addMergedRegion(cc);
            CellRangeAddress dd = new CellRangeAddress(0, 1, 3, 3);
            sheet.addMergedRegion(dd);
            CellRangeAddress ee = new CellRangeAddress(0, 1, 4, 4);
            sheet.addMergedRegion(ee);
            CellRangeAddress ff = new CellRangeAddress(0, 1, 5, 5);
            sheet.addMergedRegion(ff);
            CellRangeAddress gg = new CellRangeAddress(0, 1, 6, 6);
            sheet.addMergedRegion(gg);
            CellRangeAddress hh = new CellRangeAddress(0, 1, 7, 7);
            sheet.addMergedRegion(hh);
            CellRangeAddress jj = new CellRangeAddress(0, 1, 8, 8);
            sheet.addMergedRegion(jj);
            cell=row1.createCell(9);
            cell.setCellValue("年假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(10);
            cell.setCellValue("额外调休假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(11);
            cell.setCellValue("调休假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(12);
            cell.setCellValue("探亲假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(13);
            cell.setCellValue("哺乳假");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(14);
            cell.setCellValue("婚假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(15);
            cell.setCellValue("产检假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(16);
            cell.setCellValue("产假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(17);
            cell.setCellValue("陪产假");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(18);
            cell.setCellValue("难产假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(19);
            cell.setCellValue("流产假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(20);
            cell.setCellValue("多胞胎假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(21);
            cell.setCellValue("节育手术假");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(22);
            cell.setCellValue("工伤假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(23);
            cell.setCellValue("丧假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(24);
            cell.setCellValue("事假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(25);
            cell.setCellValue("病假");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(26);
            cell.setCellValue("返校假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(27);
            cell.setCellValue("公假");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(28);
            cell.setCellValue("公出");
            cell.setCellStyle(sheetStyle);
            cell=row1.createCell(29);
            cell.setCellValue("出差(集团外)");
            cell.setCellStyle(sheetStyle);

            cell=row1.createCell(30);
            cell.setCellValue("出差(集团内)");
            cell.setCellStyle(sheetStyle);

            int indexrow = 2;
        List<QxjhzData> qxjhzData=new ArrayList<>();
        for(QxjhzData q:qxjhz.values()){
            qxjhzData.add(q);
        }
        for(int i=0;i<qxjhzData.size();i++){
            HSSFRow rowdt=sheet.createRow(indexrow);
            cell=rowdt.createCell(0);
            cell.setCellValue(qxjhzData.get(i).getWorkcode());
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(1);
            cell.setCellValue(qxjhzData.get(i).getName());
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(2);
            cell.setCellValue(qxjhzData.get(i).getCompanyname());
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(3);
            cell.setCellValue(qxjhzData.get(i).getCentername());
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(4);
            cell.setCellValue(qxjhzData.get(i).getDeptname());
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(5);
            cell.setCellValue(qxjhzData.get(i).getGroupname());
            cell.setCellStyle(sheetStyle2);


            cell=rowdt.createCell(6);
            cell.setCellValue(qxjhzData.get(i).getJobtitlename());
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(7);
            cell.setCellValue(qxjhzData.get(i).getSanqi());
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(8);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);


            cell=rowdt.createCell(9);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(10);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(11);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(12);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(13);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(14);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(15);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(16);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(17);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(18);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(19);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(20);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(21);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(22);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(23);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(24);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(25);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(26);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(27);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(28);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(29);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            cell=rowdt.createCell(30);
            cell.setCellValue("");
            cell.setCellStyle(sheetStyle2);

            Map<String, List<String>> map = qxjhzData.get(i).getMap();
            Set<Map.Entry<String, List<String>>> entries = map.entrySet();
            for(Map.Entry<String, List<String>> e:entries){
                String month = e.getKey();
                if (!"".equals(month)) {
                    cell=rowdt.getCell(8);
                    cell.setCellValue(month);

                    List<String> value = e.getValue();
                    Map<String, String> map1 = computeLeaveTimes(value);
                    cell=rowdt.getCell(9);
                    cell.setCellValue(map1.get("nj"));

                    cell=rowdt.getCell(10);
                    cell.setCellValue(map1.get("ewtxj"));

                    cell=rowdt.getCell(11);
                    cell.setCellValue(map1.get("txj"));

                    cell=rowdt.getCell(12);
                    cell.setCellValue(map1.get("tqj"));

                    cell=rowdt.getCell(13);
                    cell.setCellValue(map1.get("brj"));

                    cell=rowdt.getCell(14);
                    cell.setCellValue(map1.get("hj"));

                    cell=rowdt.getCell(15);
                    cell.setCellValue(map1.get("cjj"));

                    cell=rowdt.getCell(16);
                    cell.setCellValue(map1.get("cj"));

                    cell=rowdt.getCell(17);
                    cell.setCellValue(map1.get("pcj"));

                    cell=rowdt.getCell(18);
                    cell.setCellValue(map1.get("ncj"));

                    cell=rowdt.getCell(19);
                    cell.setCellValue(map1.get("lcj"));

                    cell=rowdt.getCell(20);
                    cell.setCellValue(map1.get("bbtj"));

                    cell=rowdt.getCell(21);
                    cell.setCellValue(map1.get("jyssj"));

                    cell=rowdt.getCell(22);
                    cell.setCellValue(map1.get("gsj"));

                    cell=rowdt.getCell(23);
                    cell.setCellValue(map1.get("sj"));

                    cell=rowdt.getCell(24);
                    cell.setCellValue(map1.get("zj"));

                    cell=rowdt.getCell(25);
                    cell.setCellValue(map1.get("bj"));

                    cell=rowdt.getCell(26);
                    cell.setCellValue(map1.get("fxj"));

                    cell=rowdt.getCell(27);
                    cell.setCellValue(map1.get("gj"));

                    cell=rowdt.getCell(28);
                    cell.setCellValue(map1.get("gc"));

                    cell=rowdt.getCell(29);
                    cell.setCellValue(map1.get("ccjtw"));

                    cell=rowdt.getCell(30);
                    cell.setCellValue(map1.get("ccjtn"));
                }
            }
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