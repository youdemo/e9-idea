package gvo.hr.portal.engine.glz;

import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * createby jianyong.tang
 * createTime 2020/4/20 15:17
 * version v1
 * desc
 */
public class GetGLZSYDataImpl {
    /**
     * 获取男女比例
     * @param orgcodes 组织范围
     * @return
     */
    public String getGenderDstribution(String orgcodes){

        String male = "0%";//男比例
        String female = "0%";//女比例
        RecordSet rs = new RecordSet();
        String sql = "select sex,count(1) as num,ROUND(count(1)/(select count(1) from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and status='有效' and currdeptcode in("+orgcodes+") ),2)*100 as bl from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and status='有效' and currdeptcode in("+orgcodes+")  group by sex";
        rs.execute(sql);
        while(rs.next()){
            String sex = Util.null2String(rs.getString("sex"));
            if("男".equals(sex)){
                male = Util.null2String(rs.getString("bl"))+"%";
            }else if("女".equals(sex)){
                female = Util.null2String(rs.getString("bl"))+"%";
            }
        }

        return "{\"male\":\""+male+"\",\"female\":\""+female+"\"}";
    }

    /**
     * 获取年龄分布
     * @param orgcodes 组织范围
     * @return
     */
    public String getNLFB(String orgcodes){

        String leve1 = "0";//25岁以下
        String leve2 = "0";//25到35岁
        String leve3 = "0";//35到45岁
        String leve4 = "0";//45岁以上
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and status='有效' and currdeptcode in("+orgcodes+")  and age<=25" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcodes+")  and age>25 and age<=35" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcodes+")  and age>35 and age<=45" +
                "union all " +
                "select count(1) as num,'leve4' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcodes+")  and age>45 ";
        rs.execute(sql);
        while(rs.next()){
            String dj = Util.null2String(rs.getString("dj"));
            if("leve1".equals(dj)){
                leve1 = Util.null2String(rs.getString("num"));
            }else if("leve2".equals(dj)){
                leve2 = Util.null2String(rs.getString("num"));
            }else if("leve3".equals(dj)){
                leve3 = Util.null2String(rs.getString("num"));
            }else if("leve4".equals(dj)){
                leve4 = Util.null2String(rs.getString("num"));
            }
        }

        return "{\"leve1\":\""+leve1+"\",\"leve2\":\""+leve2+"\",\"leve3\":\""+leve3+"\",\"leve4\":\""+leve4+"\"}";
    }

    /**
     * 获取职等分布
     * @param orgcodes 组织范围
     * @return
     */
    public String getZDFB(String orgcodes){

        String leve1 = "0";//1-3等
        String leve2 = "0";//4-6等
        String leve3 = "0";//7-12等
        String leve4 = "0";//13等以上
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and joblevel <> '99' and currdeptcode in("+orgcodes+")  and to_number(joblevel)<=3" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and joblevel <> '99' and currdeptcode in("+orgcodes+")  and to_number(joblevel)>=4 and to_number(joblevel)<=6" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and joblevel <> '99' and currdeptcode in("+orgcodes+")  and to_number(joblevel)>=7 and to_number(joblevel)<=12" +
                "union all " +
                "select count(1) as num,'leve4' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and joblevel <> '99' and currdeptcode in("+orgcodes+")  and to_number(joblevel)>=13 ";
        rs.execute(sql);
        while(rs.next()){
            String dj = Util.null2String(rs.getString("dj"));
            if("leve1".equals(dj)){
                leve1 = Util.null2String(rs.getString("num"));
            }else if("leve2".equals(dj)){
                leve2 = Util.null2String(rs.getString("num"));
            }else if("leve3".equals(dj)){
                leve3 = Util.null2String(rs.getString("num"));
            }else if("leve4".equals(dj)){
                leve4 = Util.null2String(rs.getString("num"));
            }
        }

        return "{\"leve1\":\""+leve1+"\",\"leve2\":\""+leve2+"\",\"leve3\":\""+leve3+"\",\"leve4\":\""+leve4+"\"}";
    }

    /**
     * 获取学历分布
     * @param orgcodes 组织范围
     * @return
     */
    public String getXLFB(String orgcodes){

        String leve1 = "0";//1-3等
        String leve2 = "0";//4-6等
        String leve3 = "0";//7-12等
        String leve4 = "0";//13等以上
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcodes+")  and educationlevel in('大专','高中','小学','初中','中专/技术')" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcodes+")  and educationlevel in('本科')" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcodes+")  and educationlevel in('硕士','MBA','EMBA')" +
                "union all " +
                "select count(1) as num,'leve4' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcodes+")  and educationlevel in('博士')";
        rs.execute(sql);
        while(rs.next()){
            String dj = Util.null2String(rs.getString("dj"));
            if("leve1".equals(dj)){
                leve1 = Util.null2String(rs.getString("num"));
            }else if("leve2".equals(dj)){
                leve2 = Util.null2String(rs.getString("num"));
            }else if("leve3".equals(dj)){
                leve3 = Util.null2String(rs.getString("num"));
            }else if("leve4".equals(dj)){
                leve4 = Util.null2String(rs.getString("num"));
            }
        }

        return "{\"leve1\":\""+leve1+"\",\"leve2\":\""+leve2+"\",\"leve3\":\""+leve3+"\",\"leve4\":\""+leve4+"\"}";
    }

    /**
     * 获取人员流动数据
     * @param orgcodes 组织范围
     * @return
     */
    public String getRyldsj(String orgcodes){
        RecordSet rs = new RecordSet();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String nowdate = sf.format(date);//当前日期
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastmonthday = sf.format(calendar.getTime());//上月末
        String jjsymrs = "";//间接上月末人数
        String jjdyrzrs = "";//间接当月入职人数
        String jjdydrzrs = "";//间接当月调入总人数
        String jjdydczrs = "";//间接当月调出总人数
        String jjdylzrs = "";//间接当月离职人事
        String zjsymrs = "";//直接上月末人数
        String zjdyrzrs = "";//直接当月入职人数
        String zjdydrzrs = "";//直接当月调入总人数
        String zjdydczrs = "";//直接当月调出总人数
        String zjdylzrs = "";//直接当月离职人事
        String sql="select " +
                "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate<='"+lastmonthday+"' and f_hr_glz_sfgydr(workcode,recordid,'"+lastmonthday+"',currdeptcode)='0' and currdeptcode in("+orgcodes+") )+" +
                "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate<='"+lastmonthday+"' and f_hr_glz_getdcbm(workcode,recordid,'"+lastmonthday+"') in("+orgcodes+")  and currdeptcode not in("+orgcodes+"))" +
                " as jjsymrs," +
                "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate>'"+lastmonthday+"' and f_hr_glz_sfgydr(workcode,recordid,'"+lastmonthday+"',currdeptcode)='0' and currdeptcode in("+orgcodes+") )+" +
                "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate>'"+lastmonthday+"' and f_hr_glz_getdcbm(workcode,recordid,'"+lastmonthday+"') in("+orgcodes+")  and currdeptcode not in("+orgcodes+") )" +
                " as jjdyrzrs," +
                "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效')  and exists(select 1 from uf_hr_persondata_dt2 where changetype = '部门调动' and beforedept <> afterdept and mainid = a.id  and workcode = a.workcode and recordid = a.recordid and changedate > '"+lastmonthday+"'  and afterdept in("+orgcodes+") ))" +
                " as jjdydrzrs," +
                "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效')  and exists(select 1 from uf_hr_persondata_dt2 where changetype = '部门调动' and beforedept <> afterdept and mainid = a.id  and workcode = a.workcode and recordid = a.recordid and changedate > '"+lastmonthday+"'  and beforedept in("+orgcodes+") ))" +
                " as jjdydczrs," +
                "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and status='无效' and leavedate>'"+lastmonthday+"' and currdeptcode in("+orgcodes+") " +
                ") as jjdylzrs," +
                "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate<='"+lastmonthday+"' and f_hr_glz_sfgydr(workcode,recordid,'"+lastmonthday+"',currdeptcode)='0' and currdeptcode in("+orgcodes+") )" +
                "+" +
                "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate<='"+lastmonthday+"' and f_hr_glz_getdcbm(workcode,recordid,'"+lastmonthday+"') in("+orgcodes+")  and currdeptcode not in("+orgcodes+") )" +
                " as zjsymrs," +
                "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate>'"+lastmonthday+"' and f_hr_glz_sfgydr(workcode,recordid,'"+lastmonthday+"',currdeptcode)='0' and currdeptcode in("+orgcodes+") )" +
                "+" +
                "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate>'"+lastmonthday+"' and f_hr_glz_getdcbm(workcode,recordid,'"+lastmonthday+"') in("+orgcodes+")  and currdeptcode not in("+orgcodes+") )" +
                " as zjdyrzrs," +
                "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效')  and exists(select 1 from uf_hr_persondata_dt2 where changetype = '部门调动' and beforedept <> afterdept and mainid = a.id  and workcode = a.workcode and recordid = a.recordid and changedate > '"+lastmonthday+"'  and afterdept in("+orgcodes+") ))" +
                " as zjdydrzrs," +
                "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效')  and exists(select 1 from uf_hr_persondata_dt2 where changetype = '部门调动' and beforedept <> afterdept and mainid = a.id  and workcode = a.workcode and recordid = a.recordid and changedate > '"+lastmonthday+"'  and beforedept in("+orgcodes+") ))" +
                " as zjdydczrs," +
                "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and status='无效' and leavedate>'"+lastmonthday+"' and currdeptcode in("+orgcodes+") )" +
                " as zjdylzrs" +
                " from dual";
       // new BaseBean().writeLog("GetGLZSYDataImpl",sql);
       // new BaseBean().writeLog("GetGLZSYDataImpl aaaa",System.currentTimeMillis());
        rs.execute(sql);
        if(rs.next()){
            jjsymrs = Util.null2String(rs.getString("jjsymrs"));//间接上月末人数
            jjdyrzrs = Util.null2String(rs.getString("jjdyrzrs"));//间接当月入职人数
            jjdydrzrs = Util.null2String(rs.getString("jjdydrzrs"));//间接当月调入总人数
            jjdydczrs = Util.null2String(rs.getString("jjdydczrs"));//间接当月调出总人数
            jjdylzrs = Util.null2String(rs.getString("jjdylzrs"));//间接当月离职人事
            zjsymrs = Util.null2String(rs.getString("zjsymrs"));//直接上月末人数
            zjdyrzrs = Util.null2String(rs.getString("zjdyrzrs"));//直接当月入职人数
            zjdydrzrs = Util.null2String(rs.getString("zjdydrzrs")); //直接当月调入总人数
            zjdydczrs = Util.null2String(rs.getString("zjdydczrs")); //直接当月调出总人数
            zjdylzrs = Util.null2String(rs.getString("zjdylzrs")); //直接当月离职人事

        }
       // new BaseBean().writeLog("bbbbb",System.currentTimeMillis());
        JSONObject json = new JSONObject();
        try {
            json.put("jjsymrs", jjsymrs);
            json.put("jjdyrzrs", jjdyrzrs);
            json.put("jjdydrzrs", jjdydrzrs);
            json.put("jjdydczrs", jjdydczrs);
            json.put("jjdylzrs", jjdylzrs);
            json.put("zjsymrs", zjsymrs);
            json.put("zjdyrzrs", zjdyrzrs);
            json.put("zjdydrzrs", zjdydrzrs);
            json.put("zjdydczrs", zjdydczrs);
            json.put("zjdylzrs", zjdylzrs);

        }catch (Exception e){

        }


        return json.toString();
    }


}
