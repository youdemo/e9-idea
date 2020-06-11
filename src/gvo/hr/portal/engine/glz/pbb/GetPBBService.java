package gvo.hr.portal.engine.glz.pbb;

import com.engine.common.util.ParamUtil;
import gvo.hr.portal.engine.glz.kq.Person;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 * @date 2020/5/9 0009 - 下午 2:31
 */
public class GetPBBService {
    @POST
    @Path("/getPbbDataByUser")
    @Produces({MediaType.TEXT_PLAIN})
    public String getPbbData(@Context HttpServletRequest request, @Context HttpServletResponse response){
        User user= HrmUserVarify.getUser(request,response);
        HrUtil hrUtil=new HrUtil();
        Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode=personBaseinfo.get("workcode");
        String recordid=personBaseinfo.get("recordid");
        String orgcodes=null;
        orgcodes = hrUtil.getOrgcodes(workcode, recordid);

        String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] splits = date.split("-");
        String currMonth=splits[1];
        Integer currYear=Integer.parseInt(splits[0]);
        String orgcode = Util.null2String(request.getParameter("orgcode"));
        workcode= Util.null2String(request.getParameter("workcode"));
        String channel= Util.null2String(request.getParameter("channel"));
        String identitytype= Util.null2String(request.getParameter("identitytype"));
        String month= Util.null2String(request.getParameter("month"));
        String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
        if(!"".equals(month)){
            if(!month.contains("10")&&!month.contains("11")&&!month.contains("12")){
                if(!month.startsWith("0")){
                    month="0"+month;
                }
            }
        }
        try {
            orgcode= URLDecoder.decode(orgcode,"UTF-8");
            workcode= URLDecoder.decode(workcode,"UTF-8");
            channel= URLDecoder.decode(channel,"UTF-8");
            identitytype= URLDecoder.decode(identitytype,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String year= Util.null2String(request.getParameter("year"));
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
                " joblevel," +
                " identitytype," +
                " channel," +
                " year," +
                " month," +
                "   type," +
                " rq," +
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
        if(!"".equals(channel)){
            sql=sql+" and pd.channel='"+channel+"'";
        }
        if(!"".equals(identitytype)){
            sql=sql+" and pd.identitytype='"+identitytype+"'";
        }
        sql=sql+") where rn>="+startIndex+" and rn<="+endIndex+") a left join (select b.WORKCODE bworkcode,c.year,c.month,c.type,c.rq from uf_hr_personpbxx b,UF_HR_PERSONPBXX_DT1 c where b.id=c.mainid" ;
        if(!"".equals(month)){
            sql=sql+" and c.month='"+month+"'";
        }else{
            sql=sql+" and c.month='"+currMonth+"'";
        }
        if(!"".equals(year)){
            sql=sql+" and c.year='"+year+"'";
        }else{
            sql=sql+" and c.year='"+currYear+"'";
        }
        sql=sql+")d on a.workcode=d.bworkcode)";
//        new BaseBean().writeLog("GetPBBService  currYear: "+currYear+"-"+currMonth);
//        new BaseBean().writeLog("GetPBBService  Year: "+year+"-"+month);
 //       new BaseBean().writeLog("GetPBBService  sql"+sql);
        rs.execute(sql);
        Map<String, Person> map=new HashMap<>();
        while (rs.next()){
            String wc=Util.null2String(rs.getString("aworkcode"));
            String nian=Util.null2String(rs.getString("year"));
            String yue=Util.null2String(rs.getString("month"));
            if(map.containsKey(wc+"-"+nian+"-"+yue)){
                Person person = map.get(wc+"-"+nian+"-"+yue);
                List<Map<String, String>> map1 = person.getMap();
                Map<String, String> m=new HashMap<>();
                String type=Util.null2String(rs.getString("type"));
                String rq=Util.null2String(rs.getString("rq"));
                m.put(rq,type);
                map1.add(m);
            }else{
                List<Map<String, String>> map1 = new ArrayList<>();
                Person p=new Person();
                p.setWorkcode(wc);
                p.setName(Util.null2String(rs.getString("name")));
                p.setCompanyname(Util.null2String(rs.getString("companyname")));
                p.setCentername(Util.null2String(rs.getString("centername")));
                p.setDeptname(Util.null2String(rs.getString("deptname")));
                p.setGroupname(Util.null2String(rs.getString("groupname")));
                p.setJoblevel(Util.null2String(rs.getString("joblevel")));
                p.setIdentitytype(Util.null2String(rs.getString("identitytype")));
                p.setChannel(Util.null2String(rs.getString("channel")));
                p.setYear(nian);
                p.setMonth(yue);
                Map<String, String> m=new HashMap<>();
                String type=Util.null2String(rs.getString("type"));
                String rq=Util.null2String(rs.getString("rq"));
                m.put(rq,type);
                map1.add(m);
                p.setMap(map1);
                map.put(wc+"-"+nian+"-"+yue,p);
            }
        }
        List<Person> people=new ArrayList<>();
        for(Person p:map.values()){
            people.add(p);
//            new BaseBean().writeLog("GetPBBService  map-value"+p.toString());
        }
        for(int i=0;i<(people.size()<limit?people.size():limit);i++){//根据分页找出要展示的记录
            try {
                Integer day = 0;
                Integer night = 0;
                JSONObject object = new JSONObject();
                object.put("workcode", people.get(i).getWorkcode());
                object.put("name",people.get(i).getName());
                object.put("companyname",people.get(i).getCompanyname());
                object.put("centername",people.get(i).getCentername());
                object.put("deptname",people.get(i).getDeptname());
                object.put("groupname",people.get(i).getGroupname());
                object.put("identitytype",people.get(i).getIdentitytype());
                object.put("identitytype",people.get(i).getIdentitytype());
                List<Map<String, String>> mapList = people.get(i).getMap();

                for(Map<String, String> m:mapList){
                    Set<Map.Entry<String, String>> entries = m.entrySet();
                    for(Map.Entry<String, String> e:entries){
                        String rq = e.getKey();
                        String type = e.getValue();
                        if (!"".equals(rq)&&!"".equals(type)) {
                            String[] split = rq.split("-");
                            String daytime = split[2];
                            if(daytime.startsWith("0")){
                                daytime=daytime.substring(1);
                            }
                            object.put(daytime,type);
                            Map<String, Integer> count = getCount(type,day,night);
                            night=count.get("night");
                            day=count.get("day");
                        }
                    }
                }
                object.put("day",day);
                object.put("night",night);
                jsonArray.put(object);
 //               new BaseBean().writeLog("GetPBBService  jsonobj"+object.toString());
            } catch (JSONException e) {
                new BaseBean().writeLog(e);
            }
        }

        String totalNum = null;//分页总页数
        sql="  select  count(1) as totalnum from uf_hr_persondata t where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and t.status = '有效' and  currdeptcode in ("+orgcodes+") ";

        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=t.currdeptcode)) like Upper('%" + orgcode + "%')";
            }else{
                sql = sql + " and (Upper(t.companyname) like Upper('%" + orgcode + "%') or Upper(t.centername) like Upper('%" + orgcode + "%') or Upper(t.deptname) like Upper('%" + orgcode + "%') or Upper(t.groupname) like Upper('%" + orgcode + "%'))";
            }
        }
        if(!"".equals(workcode)){
            sql=sql+" and t.name like '%"+workcode+"%'";
        }
        if(!"".equals(channel)){
            sql=sql+" and t.channel='"+channel+"'";
        }
        if(!"".equals(identitytype)){
            sql=sql+" and t.identitytype='"+identitytype+"'";
        }
       //new BaseBean().writeLog("GetPBBService sql"+sql);
        rs.execute(sql);
        while(rs.next()){
            totalNum=Util.null2String(rs.getString("totalnum"));
        }
        try {
            if(!"".equals(year)){
                currYear=Integer.parseInt(year);
            }
            if(!"".equals(month)){
                currMonth=month;
            }
            if(currYear % 4 == 0 && currYear % 100 != 0 || currYear % 400 == 0){
                if(currMonth.contains("02")){
                    jsonObject.put("colnum",29);
                }else{
                    if(currMonth.contains("01")||currMonth.contains("03")||currMonth.contains("05")||currMonth.contains("07")||currMonth.contains("08")||currMonth.contains("10")||currMonth.contains("12")){
                        jsonObject.put("colnum",31);
                    }else{
                        jsonObject.put("colnum",30);
                    }
                }
            }else{
                if(currMonth.contains("02")){
                    jsonObject.put("colnum",28);
                }else{
                    if(currMonth.contains("01")||currMonth.contains("03")||currMonth.contains("05")||currMonth.contains("07")||currMonth.contains("08")||currMonth.contains("10")||currMonth.contains("12")){
                        jsonObject.put("colnum",31);
                    }else{
                        jsonObject.put("colnum",30);
                    }
                }
            }
 //           new BaseBean().writeLog("GetPBBService  json"+jsonObject.toString());
            jsonObject.put("code",0);
            jsonObject.put("msg","success");
            jsonObject.put("count",totalNum);
            jsonObject.put("data",jsonArray);
        } catch (JSONException e) {
            new BaseBean().writeLog(e);
        }

        return jsonObject.toString();
    }

    public Map<String, Integer> getCount(String m, Integer day, Integer night) {
//        new BaseBean().writeLog("GetPBBService  m:"+m+" day:"+day+" night:"+night);
        Map<String, Integer> map=new HashMap<>();
        if(m.contains("夜")){
            night++;
        }else if(m.contains("白")){
            day++;
        }else{

        }
        map.put("night",night);
        map.put("day",day);
        return  map;
    }
    //导出报表
    @GET
    @Path("/OutExcel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response weaReportOutExcel(@Context HttpServletRequest request, @Context HttpServletResponse response){
        try {
            User user = HrmUserVarify.getUser(request, response);
            InputStream input = OutExcel(request,response);
            String filename="排班表";
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


        User user= HrmUserVarify.getUser(request,response);
        HrUtil hrUtil=new HrUtil();
        Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode=personBaseinfo.get("workcode");
        String recordid=personBaseinfo.get("recordid");
        String orgcodes=null;
        orgcodes = hrUtil.getOrgcodes(workcode, recordid);

        String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] splits = date.split("-");
        String currMonth=splits[1];
        Integer currYear=Integer.parseInt(splits[0]);
        String orgcode = Util.null2String(request.getParameter("orgcode"));
        workcode= Util.null2String(request.getParameter("workcode"));
        String channel= Util.null2String(request.getParameter("channel"));
        String identitytype= Util.null2String(request.getParameter("identitytype"));
        String month= Util.null2String(request.getParameter("month"));
        String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
        if(!"".equals(month)){
            if(!month.contains("10")&&!month.contains("11")&&!month.contains("12")){
                if(!month.startsWith("0")){
                    month="0"+month;
                }
            }
        }
        String year= Util.null2String(request.getParameter("year"));
        try {
            orgcode= URLDecoder.decode(orgcode,"UTF-8");
            workcode= URLDecoder.decode(workcode,"UTF-8");
            channel= URLDecoder.decode(channel,"UTF-8");
            identitytype= URLDecoder.decode(identitytype,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int flag=0;
        if(!"".equals(year)){
            currYear=Integer.parseInt(year);
        }
        if(!"".equals(month)){
            currMonth=month;
        }
        if(currYear % 4 == 0 && currYear % 100 != 0 || currYear % 400 == 0){
            if(currMonth.contains("02")){
                flag=29;
            }else{
                if(currMonth.contains("01")||currMonth.contains("03")||currMonth.contains("05")||currMonth.contains("07")||currMonth.contains("08")||currMonth.contains("10")||currMonth.contains("12")){
                    flag=31;
                }else{
                    flag=30;
                }
            }
        }else {
            if (currMonth.contains("02")) {
                flag = 28;
            } else {
                if (currMonth.contains("01") || currMonth.contains("03") || currMonth.contains("05") || currMonth.contains("07") || currMonth.contains("08") || currMonth.contains("10") || currMonth.contains("12")) {
                    flag = 31;
                } else {
                    flag = 30;
                }
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
            cell.setCellValue("职等");
            cell.setCellStyle(sheetStyle);

//            cell=row0.createCell(6);
//            cell.setCellValue("携程预定事由");
//            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(7);
            cell.setCellValue("通道");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(8);
            cell.setCellValue("身份类别");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(9);
            cell.setCellValue("白班总天数");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(10);
            cell.setCellValue("夜班总天数");
            cell.setCellStyle(sheetStyle);

//            cell=row0.createCell(11);
//            cell.setCellValue("改签费");
//            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(11);
            cell.setCellValue("1");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(12);
            cell.setCellValue("2");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(13);
            cell.setCellValue("3");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(14);
            cell.setCellValue("4");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(15);
            cell.setCellValue("5");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(16);
            cell.setCellValue("6");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(17);
            cell.setCellValue("7");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(18);
            cell.setCellValue("8");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(19);
            cell.setCellValue("9");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(20);
            cell.setCellValue("10");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(21);
            cell.setCellValue("11");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(22);
            cell.setCellValue("12");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(23);
            cell.setCellValue("13");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(24);
            cell.setCellValue("14");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(25);
            cell.setCellValue("15");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(26);
            cell.setCellValue("16");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(27);
            cell.setCellValue("17");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(28);
            cell.setCellValue("18");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(29);
            cell.setCellValue("19");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(30);
            cell.setCellValue("20");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(31);
            cell.setCellValue("21");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(32);
            cell.setCellValue("22");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(33);
            cell.setCellValue("23");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(34);
            cell.setCellValue("24");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(35);
            cell.setCellValue("25");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(36);
            cell.setCellValue("26");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(37);
            cell.setCellValue("27");
            cell.setCellStyle(sheetStyle);


        if(flag==28){
            cell=row0.createCell(38);
            cell.setCellValue("28");
            cell.setCellStyle(sheetStyle);
        }else if(flag==29){
            cell=row0.createCell(38);
            cell.setCellValue("28");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(39);
            cell.setCellValue("29");
            cell.setCellStyle(sheetStyle);
        }else if(flag==30){
            cell=row0.createCell(38);
            cell.setCellValue("28");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(39);
            cell.setCellValue("29");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(40);
            cell.setCellValue("30");
            cell.setCellStyle(sheetStyle);
        }else if(flag==31){
            cell=row0.createCell(38);
            cell.setCellValue("28");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(39);
            cell.setCellValue("29");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(40);
            cell.setCellValue("30");
            cell.setCellStyle(sheetStyle);

            cell=row0.createCell(41);
            cell.setCellValue("31");
            cell.setCellStyle(sheetStyle);
        }


            String sql="select workcode aworkcode," +
                    " name," +
                    " companyname," +
                    " centername," +
                    " deptname," +
                    " groupname," +
                    " joblevel," +
                    " identitytype," +
                    " channel," +
                    " year," +
                    " month," +
                    "   type," +
                    " rq," +
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
            if(!"".equals(channel)){
                sql=sql+" and pd.channel='"+channel+"'";
            }
            if(!"".equals(identitytype)){
                sql=sql+" and pd.identitytype='"+identitytype+"'";
            }
            sql=sql+")) a left join (select b.WORKCODE bworkcode,c.year,c.month,c.type,c.rq from uf_hr_personpbxx b,UF_HR_PERSONPBXX_DT1 c where b.id=c.mainid" ;
            if(!"".equals(month)){
                sql=sql+" and c.month='"+month+"'";
            }else{
                sql=sql+" and c.month='"+currMonth+"'";
            }
            if(!"".equals(year)){
                sql=sql+" and c.year='"+year+"'";
            }else{
                sql=sql+" and c.year='"+currYear+"'";
            }
            sql=sql+")d on a.workcode=d.bworkcode)";
            rs.execute(sql);
            int indexrow = 1;
            Map<String, Person> map=new HashMap<>();
            while (rs.next()){
                String wc=Util.null2String(rs.getString("aworkcode"));
                String nian=Util.null2String(rs.getString("year"));
                String yue=Util.null2String(rs.getString("month"));
                if(map.containsKey(wc+"-"+nian+"-"+yue)){
                    Person person = map.get(wc+"-"+nian+"-"+yue);
                    List<Map<String, String>> map1 = person.getMap();
                    Map<String, String> m=new HashMap<>();
                    String type=Util.null2String(rs.getString("type"));
                    String rq=Util.null2String(rs.getString("rq"));
                    m.put(rq,type);
                    map1.add(m);
                }else{
                    List<Map<String, String>> map1 = new ArrayList<>();
                    Person p=new Person();
                    p.setWorkcode(wc);
                    p.setName(Util.null2String(rs.getString("name")));
                    p.setCompanyname(Util.null2String(rs.getString("companyname")));
                    p.setCentername(Util.null2String(rs.getString("centername")));
                    p.setDeptname(Util.null2String(rs.getString("deptname")));
                    p.setGroupname(Util.null2String(rs.getString("groupname")));
                    p.setJoblevel(Util.null2String(rs.getString("joblevel")));
                    p.setIdentitytype(Util.null2String(rs.getString("identitytype")));
                    p.setChannel(Util.null2String(rs.getString("channel")));
                    p.setYear(nian);
                    p.setMonth(yue);
                    Map<String, String> m=new HashMap<>();
                    String type=Util.null2String(rs.getString("type"));
                    String rq=Util.null2String(rs.getString("rq"));
                    m.put(rq,type);
                    map1.add(m);
                    p.setMap(map1);
                    map.put(wc+"-"+nian+"-"+yue,p);
                }
            }
            List<Person> people=new ArrayList<>();
            for(Person p:map.values()){
                people.add(p);
//            new BaseBean().writeLog("GetPBBService  map-value"+p.toString());
            }
            for(int i=0;i<people.size();i++){
                HSSFRow rowdt=sheet.createRow(indexrow);
                Integer day = 0;
                Integer night = 0;
                cell=rowdt.createCell(0);
                cell.setCellValue(people.get(i).getWorkcode());
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(1);
                cell.setCellValue(people.get(i).getName());
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(2);
                cell.setCellValue(people.get(i).getCompanyname());
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(3);
                cell.setCellValue(people.get(i).getCentername());
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(4);
                cell.setCellValue(people.get(i).getDeptname());
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(5);
                cell.setCellValue(people.get(i).getGroupname());
                cell.setCellStyle(sheetStyle2);


                cell=rowdt.createCell(6);
                cell.setCellValue(people.get(i).getJoblevel());
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(7);
                cell.setCellValue(people.get(i).getChannel());
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(8);
                cell.setCellValue(people.get(i).getIdentitytype());
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

                if(flag==28){

                    cell=rowdt.createCell(38);
                    cell.setCellValue("");
                    cell.setCellStyle(sheetStyle2);
                }else if(flag==29){
                    cell=rowdt.createCell(38);
                    cell.setCellValue("");
                    cell.setCellStyle(sheetStyle2);
                    cell=rowdt.createCell(39);
                    cell.setCellValue("");
                    cell.setCellStyle(sheetStyle2);
                }else if(flag==30){
                    cell=rowdt.createCell(38);
                    cell.setCellValue("");
                    cell.setCellStyle(sheetStyle2);
                    cell=rowdt.createCell(39);
                    cell.setCellValue("");
                    cell.setCellStyle(sheetStyle2);
                    cell=rowdt.createCell(40);
                    cell.setCellValue("");
                    cell.setCellStyle(sheetStyle2);
                }else{
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
                }

                List<Map<String, String>> mapList = people.get(i).getMap();
                for(int j=0;j<mapList.size();j++){
                    Set<Map.Entry<String, String>> entries = mapList.get(j).entrySet();
                    for(Map.Entry<String, String> str:entries){
                        Map<String, Integer> count = getCount(str.getValue(),day,night);
                        night=count.get("night");
                        day=count.get("day");
                        cell=rowdt.createCell(11+j);
                        cell.setCellValue(str.getValue());
                        cell.setCellStyle(sheetStyle2);
                    }
                }
                cell=rowdt.createCell(9);
                cell.setCellValue(day);
                cell.setCellStyle(sheetStyle2);

                cell=rowdt.createCell(10);
                cell.setCellValue(night);
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
