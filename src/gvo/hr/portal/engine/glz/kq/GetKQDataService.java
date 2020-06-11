package gvo.hr.portal.engine.glz.kq;

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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 * @date 2020/4/24 0024 - 下午 2:32
 * 获取考勤数据类
 */
public class GetKQDataService {
    @GET
    @Path("/getKqData")
    @Produces({MediaType.TEXT_PLAIN})
    /*获取考勤数据方法
    * */
    public String getKqPersonData(@Context HttpServletRequest request, @Context HttpServletResponse response){
        User user= HrmUserVarify.getUser(request,response);
        HrUtil hrUtil=new HrUtil();
        Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
        String workcode=personBaseinfo.get("workcode");//获取当前登录人的工号
        String recordid=personBaseinfo.get("recordid");
        RecordSet rs = new RecordSet();
        String orgcodes=null;
        orgcodes = hrUtil.getOrgcodes(workcode, recordid);
        String orgcode = Util.null2String(request.getParameter("orgcode"));//获取搜索条件组织编码
        workcode = Util.null2String(request.getParameter("workcode"));//获取搜索条件工号
        String identitytype = Util.null2String(request.getParameter("identitytype"));//获取搜索条件身份类别
        String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
        try {
            orgcode = URLDecoder.decode(orgcode, "UTF-8");
            workcode = URLDecoder.decode(workcode, "UTF-8");
            identitytype = URLDecoder.decode(identitytype, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Integer page=Integer.valueOf(request.getParameter("page"));
        Integer limit = Integer.valueOf(request.getParameter("limit"));
        Integer startIndex=(page-1)*limit+1;//计算分页第几条开始
        Integer endIndex=page*limit;//计算分页第几条结束
        String flag=Util.null2String(request.getParameter("flag"));//区分部门和个人考勤的标志
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray=new JSONArray();
        if(flag.equals("gr")){//个人考勤
            Calendar calendar=Calendar.getInstance();
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String sql="select * from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,currdeptcode,identitytype,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,currdeptcode,identitytype,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
            if(!"".equals(orgcode)){
                if("false".equals(sfbhxjzz)) {
                    sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                }else{
                    sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                }
            }
            sql = sql + ") order by companyname,centername,deptname,groupname asc) where nf='"+year+"'";
            if(!"".equals(workcode)){
                sql=sql+" and name like '%"+workcode+"%'";
            }
            if(!"".equals(identitytype)){
                sql=sql+" and identitytype='"+identitytype+"'";
            }
            sql = sql + ")";
            rs.execute(sql);
            Map<String,Person> map=new HashMap<>();
            while(rs.next()) {//将记录添加到map中，工号为key，如果工号一样在对应的值中追加缺勤类型
                String wc = Util.null2String(rs.getString("workcode"));
                if(map.containsKey(wc)){
                    Person person = map.get(wc);
                    List<String> list = person.getList();
                    list.add(Util.null2String(rs.getString("type")));
                }else{
                    Person person=new Person();
                    person.setWorkcode(wc);
                    person.setName(Util.null2String(rs.getString("name")));
                    person.setCompanyname(Util.null2String(rs.getString("companyname")));
                    person.setCentername(Util.null2String(rs.getString("centername")));
                    person.setDeptname(Util.null2String(rs.getString("deptname")));
                    person.setGroupname(Util.null2String(rs.getString("groupname")));
                    person.setIdentitytype(Util.null2String(rs.getString("identitytype")));
                    List<String> list=new ArrayList<>();
                    list.add(Util.null2String(rs.getString("type")));
                    person.setList(list);
                    map.put(wc,person);
                }
            }
            List<Person> people=new ArrayList<>();
            for(Person p:map.values()){
                people.add(p);
            }

            for(int i=0;i<(people.size()<limit?people.size():limit);i++){//根据分页找出要展示的记录
                try {
                    Integer late = 0;
                    Integer unpunched = 0;
                    Integer Absenteeism = 0;
                    JSONObject object = new JSONObject();
                    object.put("workcode", people.get(i).getWorkcode());
                    object.put("name",people.get(i).getName());
                    object.put("companyname",people.get(i).getCompanyname());
                    object.put("centername",people.get(i).getCentername());
                    object.put("deptname",people.get(i).getDeptname());
                    object.put("groupname",people.get(i).getGroupname());
                    object.put("identitytype",people.get(i).getIdentitytype());
                    List<String> list = people.get(i).getList();
                    for(String m:list){
                        Map<String, Integer> count = getCount(m, late, unpunched, Absenteeism);
                        late=count.get("late");
                        unpunched=count.get("unpunched");
                        Absenteeism=count.get("Absenteeism");
                    }
                    object.put("late",late);
                    object.put("unpunched",unpunched);
                    object.put("Absenteeism",Absenteeism);
                    jsonArray.put(object);
                } catch (JSONException e) {
                    new BaseBean().writeLog(e);
                }
            }
            String totalNum = String.valueOf(map.values().size());//分页总页数
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
        else{
            String string = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] split = string.split("-");
            String year= split[0];
            String month=split[1];
            String day=split[2];
            String sql = "";
            String preMonth = String.valueOf(Integer.parseInt(month)-1);
            if(!preMonth.equals("10")&&!preMonth.equals("11")&&!preMonth.equals("12")){
                preMonth="0"+preMonth;
            }
            if(Integer.parseInt(day)<4){
                if(day.equals("1")){
                    sql="select * from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                    if(!"".equals(orgcode)){
                        if("false".equals(sfbhxjzz)) {
                            sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                        }else{
                            sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                        }
                    }
                    sql = sql + ") order by companyname,centername,deptname,groupname asc) where (nf='"+year+"' and yf='"+month+"') or (nf='"+(Integer.parseInt(year)-1)+"' and yf='12')";
                }else{
                    sql="select * from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                    if(!"".equals(orgcode)){
                        if("false".equals(sfbhxjzz)) {
                            sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                        }else{
                            sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                        }
                    }
                    sql=sql+") order by companyname,centername,deptname,groupname asc) where nf='"+year+"' and (yf='"+month+"' or yf='"+preMonth+"')";
                }
            }else{
                sql="select * from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                if(!"".equals(orgcode)){
                    if("false".equals(sfbhxjzz)) {
                        sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                    }else{
                        sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                    }
                }
                sql=sql+") order by companyname,centername,deptname,groupname asc) where nf='"+year+"' and yf='"+month+"'";
            }
            if(!"".equals(workcode)){
                sql=sql+" and name like '%"+workcode+"%'";
            }
            if(!"".equals(identitytype)){
                sql=sql+" and identitytype='"+identitytype+"'";
            }
            sql=sql+") where rn>="+startIndex+" and rn<="+endIndex;
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
                    object.put("identitytype",Util.null2String(rs.getString("identitytype")));
                    object.put("belongdate",Util.null2String(rs.getString("belongdate")));
                    object.put("bcmc",Util.null2String(rs.getString("bcmc")));
                    object.put("bc",Util.null2String(rs.getString("bc")));
                    object.put("type",Util.null2String(rs.getString("type")));
                    jsonArray.put(object);
                } catch (JSONException e) {
                    new BaseBean().writeLog(e);
                }
            }
            String totalNum=null;//分页总页数
            if(Integer.parseInt(day)<4){
                if(day.equals("1")){
                    sql="select count(did) totalnum from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                    if(!"".equals(orgcode)){
                        if("false".equals(sfbhxjzz)) {
                            sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                        }else{
                            sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                        }
                    }
                    sql=sql+") order by companyname,centername,deptname,groupname asc) where (nf='"+year+"' and yf='"+month+"') or (nf='"+(Integer.parseInt(year)-1)+"' and yf='12')";
                }else{
                    sql="select count(did) totalnum from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                    if(!"".equals(orgcode)){
                        if("false".equals(sfbhxjzz)) {
                            sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                        }else{
                            sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                        }
                    }
                    sql=sql+") order by companyname,centername,deptname,groupname asc) where nf='"+year+"' and (yf='"+month+"' or yf='"+preMonth+"')";
                }
            }else{
                sql="select count(did) totalnum from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                if(!"".equals(orgcode)){
                    if("false".equals(sfbhxjzz)) {
                        sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                    }else{
                        sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                    }
                }
                sql=sql+") order by companyname,centername,deptname,groupname asc) where nf='"+year+"' and yf='"+month+"'";
            }

            if(!"".equals(workcode)){
                sql=sql+" and name like '%"+workcode+"%'";
            }
            if(!"".equals(identitytype)){
                sql=sql+" and identitytype='"+identitytype+"'";
            }
            sql=sql+")";
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
    }
/*
* 计算考勤类型次数方法
* */
    public  Map<String,Integer> getCount(String str,int late,int unpunched,int Absenteeism){
        if(str.contains("，")){
            String[] split = str.split("，");
            for(String s:split){
                if(s.contains("迟到")){
                    late++;
                }else if(s.contains("缺卡")){
                    unpunched++;
                }else if(s.contains("旷工")){
                    Absenteeism++;
                }else{

                }
            }
        }else{
            if(str.contains("迟到")){
                late++;
            }else if(str.contains("缺卡")){
                unpunched++;
            }else if(str.contains("旷工")){
                Absenteeism++;
            }else{

            }
        }
        HashMap<String, Integer> map = new HashMap<>();
        map.put("late",late);
        map.put("unpunched",unpunched);
        map.put("Absenteeism",Absenteeism);
        return map;
    }

    //导出报表
    @GET
    @Path("/OutExcel")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response weaReportOutExcel(@Context HttpServletRequest request, @Context HttpServletResponse response){
        try {
            User user = HrmUserVarify.getUser(request, response);
            InputStream input = OutExcel(request,response);
            String filename="考勤";
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

        String flag=Util.null2String(request.getParameter("flag"));
        try{
            //创建标题
            //因为这里是前端固定的表头,所以后台手动添加

            HSSFRow row0=sheet.createRow((short)0);

            if (flag.equals("gr")) {
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

//            cell=row0.createCell(6);
//            cell.setCellValue("携程预定事由");
//            cell.setCellStyle(sheetStyle);

                cell=row0.createCell(7);
                cell.setCellValue("迟到累计次数");
                cell.setCellStyle(sheetStyle);

                cell=row0.createCell(8);
                cell.setCellValue("未打卡累计次数");
                cell.setCellStyle(sheetStyle);

                cell=row0.createCell(9);
                cell.setCellValue("旷工累计次数");
                cell.setCellStyle(sheetStyle);

            } else {
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

//            cell=row0.createCell(6);
//            cell.setCellValue("携程预定事由");
//            cell.setCellStyle(sheetStyle);

                cell=row0.createCell(7);
                cell.setCellValue("考勤归属日期");
                cell.setCellStyle(sheetStyle);

                cell=row0.createCell(8);
                cell.setCellValue("班次名称");
                cell.setCellStyle(sheetStyle);

                cell=row0.createCell(9);
                cell.setCellValue("班次");
                cell.setCellStyle(sheetStyle);

                cell=row0.createCell(10);
                cell.setCellValue("异常类型");
                cell.setCellStyle(sheetStyle);
            }

            User user= HrmUserVarify.getUser(request,response);
            HrUtil hrUtil=new HrUtil();
            Map<String,String> personBaseinfo = hrUtil.getPersonBaseinfo(user.getUID() + "");
            String workcode=personBaseinfo.get("workcode");
            String recordid=personBaseinfo.get("recordid");

            String orgcodes=null;
            orgcodes = hrUtil.getOrgcodes(workcode, recordid);
            String orgcode = Util.null2String(request.getParameter("orgcode"));//获取搜索条件组织编码
            workcode = Util.null2String(request.getParameter("workcode"));//获取搜索条件工号
            String identitytype = Util.null2String(request.getParameter("identitytype"));//获取搜索条件身份类别
            String sfbhxjzz =  Util.null2String(request.getParameter("sfbhxjzz"));
            orgcode = URLDecoder.decode(orgcode,"UTF-8");
            workcode = URLDecoder.decode(workcode,"UTF-8");
            identitytype=URLDecoder.decode(identitytype,"UTF-8");
            String sql="";
            if (flag.equals("gr")) {
                Calendar calendar=Calendar.getInstance();
                String year = String.valueOf(calendar.get(Calendar.YEAR));
                sql="select * from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,currdeptcode,identitytype,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,currdeptcode,identitytype,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                if(!"".equals(orgcode)){
                    if("false".equals(sfbhxjzz)) {
                        sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                    }else{
                        sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                    }
                }
                sql = sql + ") order by companyname,centername,deptname,groupname asc) where nf='"+year+"'";
                if(!"".equals(workcode)){
                    sql=sql+" and name like '%"+workcode+"%'";
                }
                if(!"".equals(identitytype)){
                    sql=sql+" and identitytype='"+identitytype+"'";
                }
                sql = sql + ")";
                rs.execute(sql);
                int indexrow = 1;
                Map<String,Person> map=new HashMap<>();
                while(rs.next()) {//将记录添加到map中，工号为key，如果工号一样在对应的值中追加缺勤类型
                    String wc = Util.null2String(rs.getString("workcode"));
                    if(map.containsKey(wc)){
                        Person person = map.get(wc);
                        List<String> list = person.getList();
                        list.add(Util.null2String(rs.getString("type")));
                    }else{
                        Person person=new Person();
                        person.setWorkcode(wc);
                        person.setName(Util.null2String(rs.getString("name")));
                        person.setCompanyname(Util.null2String(rs.getString("companyname")));
                        person.setCentername(Util.null2String(rs.getString("centername")));
                        person.setDeptname(Util.null2String(rs.getString("deptname")));
                        person.setGroupname(Util.null2String(rs.getString("groupname")));
                        person.setIdentitytype(Util.null2String(rs.getString("identitytype")));
                        List<String> list=new ArrayList<>();
                        list.add(Util.null2String(rs.getString("type")));
                        person.setList(list);
                        map.put(wc,person);
                    }
                }
                for(Person p:map.values()){
                    HSSFRow rowdt=sheet.createRow(indexrow);
                    cell=rowdt.createCell(0);
                    cell.setCellValue(p.getWorkcode());
                    cell.setCellStyle(sheetStyle2);

                    cell=rowdt.createCell(1);
                    cell.setCellValue(p.getName());
                    cell.setCellStyle(sheetStyle2);

                    cell=rowdt.createCell(2);
                    cell.setCellValue(p.getCompanyname());
                    cell.setCellStyle(sheetStyle2);

                    cell=rowdt.createCell(3);
                    cell.setCellValue(p.getCentername());
                    cell.setCellStyle(sheetStyle2);

                    cell=rowdt.createCell(4);
                    cell.setCellValue(p.getDeptname());
                    cell.setCellStyle(sheetStyle2);

                    cell=rowdt.createCell(5);
                    cell.setCellValue(p.getGroupname());
                    cell.setCellStyle(sheetStyle2);


                    cell=rowdt.createCell(6);
                    cell.setCellValue(p.getIdentitytype());
                    cell.setCellStyle(sheetStyle2);
                    Integer late = 0;
                    Integer unpunched = 0;
                    Integer Absenteeism = 0;
                    List<String> list = p.getList();
                    for(String m:list){
                        Map<String, Integer> count = getCount(m, late, unpunched, Absenteeism);
                        late=count.get("late");
                        unpunched=count.get("unpunched");
                        Absenteeism=count.get("Absenteeism");
                    }
                    cell=rowdt.createCell(7);
                    cell.setCellValue(Util.null2String(late));
                    cell.setCellStyle(sheetStyle2);

                    cell=rowdt.createCell(8);
                    cell.setCellValue(Util.null2String(unpunched));
                    cell.setCellStyle(sheetStyle2);
                    cell=rowdt.createCell(9);
                    cell.setCellValue(Util.null2String(Absenteeism));
                    cell.setCellStyle(sheetStyle2);

                    indexrow++;
                }
            } else {
                String string = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String[] split = string.split("-");
                String year= split[0];
                String month=split[1];
                String day=split[2];
                String preMonth = String.valueOf(Integer.parseInt(month)-1);
                if(!preMonth.equals("10")&&!preMonth.equals("11")&&!preMonth.equals("12")){
                    preMonth="0"+preMonth;
                }
                if(Integer.parseInt(day)<4){
                    if(day.equals("1")){
                        sql="select * from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                        if(!"".equals(orgcode)){
                            if("false".equals(sfbhxjzz)) {
                                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                            }else{
                                sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                            }
                        }
                        sql = sql + ") order by companyname,centername,deptname,groupname asc) where (nf='"+year+"' and yf='"+month+"') or (nf='"+(Integer.parseInt(year)-1)+"' and yf='12')";
                    }else{
                        sql="select * from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                        if(!"".equals(orgcode)){
                            if("false".equals(sfbhxjzz)) {
                                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                            }else{
                                sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                            }
                        }
                        sql=sql+") order by companyname,centername,deptname,groupname asc) where nf='"+year+"' and (yf='"+month+"' or yf='"+preMonth+"')";
                    }
                }else{
                    sql="select * from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf,ROWNUM rn from(select did,name,companyname,centername,centercode,deptname,deptcode,groupname,groupcode,identitytype,currdeptcode,workcode,belongdate,bcmc,bc,type,nf,yf from(select d.id did,d.name,d.companyname,d.centername,d.centercode,d.deptname,d.deptcode,d.groupname,d.groupcode,d.identitytype,d.currdeptcode,d.workcode,c.belongdate,c.bcmc,c.bc,c.type,c.nf,c.yf from((select a.workcode,b.belongdate,b.bcmc,b.bc,b.type,b.nf,b.yf from (uf_hr_attendexpInfo a inner join uf_hr_attendexpInfo_dt1 b on a.id=b.mainid)) c INNER JOIN (select * from uf_hr_persondata where (currdeptcode in ("+orgcodes+")) and status = '有效' and (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99'))) d on c.workcode=d.workcode) where 1=1 ";
                    if(!"".equals(orgcode)){
                        if("false".equals(sfbhxjzz)) {
                            sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=d.currdeptcode)) like Upper('%" + orgcode + "%')";
                        }else{
                            sql = sql + " and (Upper(d.companyname) like Upper('%" + orgcode + "%') or Upper(d.centername) like Upper('%" + orgcode + "%') or Upper(d.deptname) like Upper('%" + orgcode + "%') or Upper(d.groupname) like Upper('%" + orgcode + "%'))";
                        }
                    }
                    sql=sql+") order by companyname,centername,deptname,groupname asc) where nf='"+year+"' and yf='"+month+"'";
                }
                if(!"".equals(workcode)){
                    sql=sql+" and name like '%"+workcode+"%'";
                }
                if(!"".equals(identitytype)){
                    sql=sql+" and identitytype='"+identitytype+"'";
                }
                sql=sql+")";
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
                    cell.setCellValue(Util.null2String(rs.getString("belongdate")));
                    cell.setCellStyle(sheetStyle2);
                    cell=rowdt.createCell(8);
                    cell.setCellValue(Util.null2String(rs.getString("bcmc")));
                    cell.setCellStyle(sheetStyle2);
                    cell=rowdt.createCell(9);
                    cell.setCellValue(Util.null2String(rs.getString("bc")));
                    cell.setCellStyle(sheetStyle2);
                    cell=rowdt.createCell(10);
                    cell.setCellValue(Util.null2String(rs.getString("type")));
                    cell.setCellStyle(sheetStyle2);
                    indexrow++;
                }
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
