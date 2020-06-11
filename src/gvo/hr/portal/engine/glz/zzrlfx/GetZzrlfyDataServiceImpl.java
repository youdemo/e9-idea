package gvo.hr.portal.engine.glz.zzrlfx;

import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * createby jianyong.tang
 * createTime 2020/5/8 16:06
 * version v1
 * desc
 */
public class GetZzrlfyDataServiceImpl {
    /**
     * 获取人员流动数据
     * @param orgcode
     * @param type identitytype='间接人员' 直接人员
     * @return
     */
    public String getRyldsj(String orgcode,String type){
        RecordSet rs = new RecordSet();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String nowdate = sf.format(date);//当前日期
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastmonthday = sf.format(calendar.getTime());//上月末
        String symrs = "";//上月末人数
        String dyrzrs = "";//当月入职人数
        String dydrzrs = "";//当月调入总人数
        String dydczrs = "";//当月调出总人数
        String dylzrs = "";//当月离职人事

        orgcode = "'"+orgcode+"'";
        String sql = "";
        if("间接人员".equals(type)){
            sql="select " +
                    "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate<='"+lastmonthday+"' and f_hr_glz_sfgydr(workcode,recordid,'"+lastmonthday+"',currdeptcode)='0' and currdeptcode in("+orgcode+") )+" +
                    "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate<='"+lastmonthday+"' and f_hr_glz_getdcbm(workcode,recordid,'"+lastmonthday+"') in("+orgcode+")  and currdeptcode not in("+orgcode+"))" +
                    " as symrs," +
                    "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate>'"+lastmonthday+"' and f_hr_glz_sfgydr(workcode,recordid,'"+lastmonthday+"',currdeptcode)='0' and currdeptcode in("+orgcode+") )+" +
                    "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate>'"+lastmonthday+"' and f_hr_glz_getdcbm(workcode,recordid,'"+lastmonthday+"') in("+orgcode+")  and currdeptcode not in("+orgcode+") )" +
                    " as dyrzrs," +
                    "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效')  and exists(select 1 from uf_hr_persondata_dt2 where changetype = '部门调动' and beforedept <> afterdept and mainid = a.id  and workcode = a.workcode and recordid = a.recordid and changedate > '"+lastmonthday+"'  and afterdept in("+orgcode+") ))" +
                    " as dydrzrs," +
                    "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效')  and exists(select 1 from uf_hr_persondata_dt2 where changetype = '部门调动' and beforedept <> afterdept and mainid = a.id  and workcode = a.workcode and recordid = a.recordid and changedate > '"+lastmonthday+"'  and beforedept in("+orgcode+") ))" +
                    " as dydczrs," +
                    "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='间接人员' and status='无效' and leavedate>'"+lastmonthday+"' and currdeptcode in("+orgcode+") " +
                    ") as dylzrs " +
                    
                    " from dual";
        }else if("直接人员".equals(type)){
            sql="select " +
                    "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate<='"+lastmonthday+"' and f_hr_glz_sfgydr(workcode,recordid,'"+lastmonthday+"',currdeptcode)='0' and currdeptcode in("+orgcode+") )" +
                    "+" +
                    "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate<='"+lastmonthday+"' and f_hr_glz_getdcbm(workcode,recordid,'"+lastmonthday+"') in("+orgcode+")  and currdeptcode not in("+orgcode+") )" +
                    " as symrs," +
                    "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate>'"+lastmonthday+"' and f_hr_glz_sfgydr(workcode,recordid,'"+lastmonthday+"',currdeptcode)='0' and currdeptcode in("+orgcode+") )" +
                    "+" +
                    "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效') and joindate>'"+lastmonthday+"' and f_hr_glz_getdcbm(workcode,recordid,'"+lastmonthday+"') in("+orgcode+")  and currdeptcode not in("+orgcode+") )" +
                    " as dyrzrs," +
                    "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效')  and exists(select 1 from uf_hr_persondata_dt2 where changetype = '部门调动' and beforedept <> afterdept and mainid = a.id  and workcode = a.workcode and recordid = a.recordid and changedate > '"+lastmonthday+"'  and afterdept in("+orgcode+") ))" +
                    " as dydrzrs," +
                    "(select count(1) as num from uf_hr_persondata a where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and ( (status='无效' and leavedate>'"+lastmonthday+"') or  status='有效')  and exists(select 1 from uf_hr_persondata_dt2 where changetype = '部门调动' and beforedept <> afterdept and mainid = a.id  and workcode = a.workcode and recordid = a.recordid and changedate > '"+lastmonthday+"'  and beforedept in("+orgcode+") ))" +
                    " as dydczrs," +
                    "(select count(1) as num from uf_hr_persondata a where  (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and identitytype='直接人员' and status='无效' and leavedate>'"+lastmonthday+"' and currdeptcode in("+orgcode+") )" +
                    " as dylzrs" +
                    " from dual";
        }


        rs.execute(sql);
        if(rs.next()){
            symrs = Util.null2String(rs.getString("symrs"));//上月末人数
            dyrzrs = Util.null2String(rs.getString("dyrzrs"));//当月入职人数
            dydrzrs = Util.null2String(rs.getString("dydrzrs"));//当月调入总人数
            dydczrs = Util.null2String(rs.getString("dydczrs"));//当月调出总人数
            dylzrs = Util.null2String(rs.getString("dylzrs"));//当月离职人事
        }
        // new BaseBean().writeLog("bbbbb",System.currentTimeMillis());
        JSONObject json = new JSONObject();
        try {
            json.put("symrs", symrs);
            json.put("dyrzrs", dyrzrs);
            json.put("dydrzrs", dydrzrs);
            json.put("dydczrs", dydczrs);
            json.put("dylzrs", dylzrs);


        }catch (Exception e){

        }


        return json.toString();
    }

    /**
     * 获取男女人数
     * @param orgcode 组织编码
     * @param type identitytype='间接人员' 直接人员
     * @return
     */
    public String getGenderDstribution(String orgcode,String type){

        String male = "0";//男
        String female = "0";//女
        RecordSet rs = new RecordSet();
        orgcode = "'"+orgcode+"'";
        String sql = "select sex,count(1) as num from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' group by sex";
        rs.execute(sql);
        while(rs.next()){
            String sex = Util.null2String(rs.getString("sex"));
            if("男".equals(sex)){
                male = Util.null2String(rs.getString("num"));
            }else if("女".equals(sex)){
                female = Util.null2String(rs.getString("num"));
            }
        }

        return "{\"male\":\""+male+"\",\"female\":\""+female+"\"}";
    }

    /**
     * 获取年龄分布
     * @param orgcode 组织范围
     * @param type identitytype='间接人员' 直接人员
     * @return
     */
    public String getNLFB(String orgcode,String type){

        String leve1 = "0";//25岁以下
        String leve2 = "0";//25到35岁
        String leve3 = "0";//35到45岁
        String leve4 = "0";//45岁以上
        orgcode = "'"+orgcode+"'";
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"'  and age<=25" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"'  and age>25 and age<=35" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"'  and age>35 and age<=45" +
                "union all " +
                "select count(1) as num,'leve4' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"'  and age>45 ";
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
     * @param orgcode 组织范围
     * @param type identitytype='间接人员' 直接人员
     * @return
     */
    public String getZDFB(String orgcode,String type){

        String leve1 = "0";//1-3等
        String leve2 = "0";//4-6等
        String leve3 = "0";//7-12等
        String leve4 = "0";//13等以上
        orgcode = "'"+orgcode+"'";
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and joblevel <> '99' and currdeptcode in("+orgcode+")  and identitytype='"+type+"' and to_number(joblevel)<=3" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and joblevel <> '99' and currdeptcode in("+orgcode+") and identitytype='"+type+"'  and to_number(joblevel)>=4 and to_number(joblevel)<=6" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and joblevel <> '99' and currdeptcode in("+orgcode+") and identitytype='"+type+"'  and to_number(joblevel)>=7 and to_number(joblevel)<=12" +
                "union all " +
                "select count(1) as num,'leve4' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and joblevel <> '99' and currdeptcode in("+orgcode+") and identitytype='"+type+"'  and to_number(joblevel)>=13 ";
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
     * @param orgcode 组织范围
     * @return
     */
    public String getXLFBJJ(String orgcode,String type){

        String leve1 = "0";//大专及以下
        String leve2 = "0";//本科
        String leve3 = "0";//硕士
        String leve4 = "0";//博士
        orgcode = "'"+orgcode+"'";
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and educationlevel in('大专','高中','小学','初中','中专/技术')" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and educationlevel in('本科')" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and educationlevel in('硕士','MBA','EMBA')" +
                "union all " +
                "select count(1) as num,'leve4' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and educationlevel in('博士')";
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
     * 获取集团工龄分布间接
     * @param orgcode 组织范围
     * @param type 类型
     * @return
     */
    public String getJTGLFBJJ(String orgcode,String type){

        String leve1 = "0";//一年以下
        String leve2 = "0";//一到三年
        String leve3 = "0";//三到五年
        String leve4 = "0";//五年以上
        orgcode = "'"+orgcode+"'";
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)<=1" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)>1 and to_number(jtgl)<=3" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)>3 and to_number(jtgl)<=5" +
                "union all " +
                "select count(1) as num,'leve4' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)>5 ";
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
     * 获取社会工龄分布间接
     * @param orgcode 组织范围
     * @param type 类型
     * @return
     */
    public String getSHGLFBJJ(String orgcode,String type){

        String leve1 = "0";//一年以下
        String leve2 = "0";//一到三年
        String leve3 = "0";//三到五年
        String leve4 = "0";//五年以上
        orgcode = "'"+orgcode+"'";
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(shgl)<=1" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(shgl)>1 and to_number(shgl)<=3" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(shgl)>3 and to_number(shgl)<=5" +
                "union all " +
                "select count(1) as num,'leve4' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(shgl)>5 ";
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
     * 获取员工类别分布直接
     * @param orgcode 组织范围
     * @param type 类型
     * @return
     */
    public String getYGLBFBZJ(String orgcode,String type){

        String leve1 = "0";//正式员工
        String leve2 = "0";//劳务人员
        String leve3 = "0";//实习员工
        orgcode = "'"+orgcode+"'";
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and employeetype='正式员工'" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and employeetype='劳务人员'" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and employeetype='实习员工'";
        rs.execute(sql);
        while(rs.next()){
            String dj = Util.null2String(rs.getString("dj"));
            if("leve1".equals(dj)){
                leve1 = Util.null2String(rs.getString("num"));
            }else if("leve2".equals(dj)){
                leve2 = Util.null2String(rs.getString("num"));
            }else if("leve3".equals(dj)){
                leve3 = Util.null2String(rs.getString("num"));
            }
        }

        return "{\"leve1\":\""+leve1+"\",\"leve2\":\""+leve2+"\",\"leve3\":\""+leve3+"\"}";
    }

    /**
     * 获取学历分布直接
     * @param orgcode 组织范围
     * @return
     */
    public String getXLFBZJ(String orgcode,String type){

        String leve1 = "0";//中专及以下
        String leve2 = "0";//高中
        String leve3 = "0";//大专及以上
        orgcode = "'"+orgcode+"'";
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and educationlevel in('小学','初中','中专/技术')" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and educationlevel in('高中')" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and educationlevel in('本科','硕士','MBA','EMBA','博士','大专')";
        rs.execute(sql);
        while(rs.next()){
            String dj = Util.null2String(rs.getString("dj"));
            if("leve1".equals(dj)){
                leve1 = Util.null2String(rs.getString("num"));
            }else if("leve2".equals(dj)){
                leve2 = Util.null2String(rs.getString("num"));
            }else if("leve3".equals(dj)){
                leve3 = Util.null2String(rs.getString("num"));
            }
        }

        return "{\"leve1\":\""+leve1+"\",\"leve2\":\""+leve2+"\",\"leve3\":\""+leve3+"\"}";
    }

    /**
     * 获取集团工龄分布直接
     * @param orgcode 组织范围
     * @return
     */
    public String getJTGLFBZJ(String orgcode,String type){

        String leve1 = "0";//7天及以下
        String leve2 = "0";//一个月及以下
        String leve3 = "0";//1-6个月含
        String leve4 = "0";//6个月-1年含
        String leve5 = "0";//1-2年含
        String leve6 = "0";//2-3年含
        String leve7 = "0";//3年以上
        orgcode = "'"+orgcode+"'";
        RecordSet rs = new RecordSet();
        String sql = "select count(1) as num,'leve1' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)<=0.5 and (to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')-to_date(rjtrq,'yyyy-mm-dd'))<=7" +
                "union all " +
                "select count(1) as num,'leve2' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)<=0.5 and months_between(to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'),to_date(rjtrq,'yyyy-mm-dd'))<=1" +
                "union all " +
                "select count(1) as num,'leve3' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)<=0.5 and months_between(to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd'),to_date(rjtrq,'yyyy-mm-dd'))>1" +
                "union all " +
                "select count(1) as num,'leve4' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)>0.5 and to_number(jtgl)<=1" +
                "union all " +
                "select count(1) as num,'leve5' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)>1 and to_number(jtgl)<=2" +
                "union all " +
                "select count(1) as num,'leve6' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)>2 and to_number(jtgl)<=3" +
                "union all " +
                "select count(1) as num,'leve7' as dj from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in("+orgcode+") and identitytype='"+type+"' and to_number(jtgl)>3";
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
            }else if("leve5".equals(dj)){
                leve5 = Util.null2String(rs.getString("num"));
            }else if("leve6".equals(dj)){
                leve6 = Util.null2String(rs.getString("num"));
            }else if("leve7".equals(dj)){
                leve7 = Util.null2String(rs.getString("num"));
            }
        }

        return "{\"leve1\":\""+leve1+"\",\"leve2\":\""+leve2+"\",\"leve3\":\""+leve3+"\",\"leve4\":\""+leve4+"\",\"leve5\":\""+leve5+"\",\"leve6\":\""+leve6+"\",\"leve7\":\""+leve7+"\"}";
    }

    /**
     * 获取籍贯分布直接
     * @param orgcode 组织编码
     * @param type 类型
     * @return
     */
    public String getjGFBZJ(String orgcode,String type){
        JSONObject json = new JSONObject();
        RecordSet rs = new RecordSet();
        try {
            String sql = "select count(1) as num,nativeplace from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in(" + orgcode + ") and identitytype='" + type + "'  group by nativeplace having count(1)>=20 " +
                    "union all " +
                    "select sum(num) as num,'其他' as nativeplace from( " +
                    "select count(1) as num from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in(" + orgcode + ") and identitytype='" + type + "' group by nativeplace having count(1)<20) ";
            //new BaseBean().writeLog("testaaaaa","select count(1) as num from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and  status='有效' and currdeptcode in(" + orgcode + ") and identitytype='" + type + "' group by nativeplace having count(1)<20");
            rs.execute(sql);
            while (rs.next()) {
                String num = Util.null2String(rs.getString("num"));
                if("0".equals(num)||"".equals(num)){
                    continue;
                }
                json.put(Util.null2String(rs.getString("nativeplace")), num);
            }
        }catch (Exception e){
            new BaseBean().writeLog("GetZzrlfyDataServiceImpl",e);
        }
        return json.toString();
    }
}
