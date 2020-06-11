package gvo.hr.portal.engine.glz.jbqk;

import weaver.conn.RecordSet;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/5/22 14:12
 * version v1
 * desc
 */
public class GetJbqkDataHzImpl {
    /**
     * 获取部门标准人数
     * @param orgcode
     * @param identitytype
     * @return
     */
    public String getBZrs(String orgcode,String identitytype) {
        RecordSet rs = new RecordSet();
        String zrs = "";
        String sql = "select count(1) as zrs from uf_hr_persondata a" +
                "                 where (employeetype in ('正式员工', '实习员工') or " +
                "                       (employeetype = '劳务人员' and joblevel <> '99')) " +
                "                   and status = '有效' " +
                "                   and identitytype = '" + identitytype + "' and worktype='标准工时' and currdeptcode in(" + orgcode + ")";
        rs.execute(sql);
        if (rs.next()) {
            zrs = Util.null2String(rs.getString("zrs"));
        }
        return zrs;
    }
    /**
     * 获取部门加班受限总时数和当月加班总时数
     * @param orgcode
     * @param identitytype
     * @return
     */
    public Map<String,String> getBmsxzss(String orgcode,String identitytype){
        Map<String,String> map = new HashMap<String,String>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = sf.format(new Date());
        String begindate = enddate.substring(0,7)+"-01";
        RecordSet rs = new RecordSet();
        String st_tops = "";
        String up_tops = "";
        String jbzsh = "";
        String sql = "select sum(nvl(st_tops,0)) as st_tops,sum(nvl(up_tops,0)) as up_tops,sum(nvl(jbzsh,0)) as jbzsh from (select (select nvl(TOP_HOURS, 0) " +
                "                  from uf_companytop_hours ch " +
                "                 where ch.company = b.SUBCOMPANYID1 " +
                "                   and PEOPLE_TYPE = '1' " +
                "                   and ch.BEGINDATE <= '"+enddate+"' " +
                "                   and ch.ENDDATE >= '"+enddate+"' " +
                "                   and rownum <= 1) as st_tops, " +
                "                   gvogroup.F_getovertime('"+enddate+"', a.workcode, 'G') as up_tops,  " +
                " (SELECT nvl(SUM(nvl(a_hours, 0)), 0) " +
                "                  from formtable_main_744_dt1 aa " +
                "                 where cday >= '"+begindate+"' " +
                "                   and cday <= '"+enddate+"' " +
                "                   and aa.work_code = a.WORKCODE " +
                "                   and (overtime_type like '%法定%' or overtime_type like '%周末%' or overtime_type like '%平时%')) as jbzsh "+
                "                 from uf_hr_persondata a,hrmresource b " +
                "                 where a.workcode=b.workcode " +
                "                   and (employeetype in ('正式员工', '实习员工') or " +
                "                       (employeetype = '劳务人员' and a.joblevel <> '99')) " +
                "                   and a.status = '有效' " +
                "                   and identitytype = '"+identitytype+"' and worktype='标准工时' and currdeptcode in("+orgcode+")) t";
        rs.execute(sql);
        if (rs.next()) {
            st_tops = Util.null2String(rs.getString("st_tops"));
            up_tops = Util.null2String(rs.getString("up_tops"));
            jbzsh = Util.null2String(rs.getString("jbzsh"));

        }
        map.put("st_tops",st_tops);
        map.put("up_tops",up_tops);
        map.put("jbzsh",jbzsh);
        return map;
    }

    /**
     * 获取汇总图表区间数
     * @param orgcode
     * @param identitytype
     * @return
     */
    public Map<String,String> getHzRsqj(String orgcode,String identitytype){
        Map<String,String> map = new HashMap<String,String>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = sf.format(new Date());
        String begindate = enddate.substring(0,7)+"-01";
        RecordSet rs = new RecordSet();
        String leve1 = "0";
        String leve2 = "0";
        String leve3 = "0";
        String leve4 = "0";
        String leve5 = "0";
        String leve6 = "0";
        String sql = "   select leve,count(1) as num from (                " +
                "   select case when num <=30 then 'leve1' " +
                "   when num>30 and num<=50 then 'leve2' " +
                "   when num>50 and num<=60 then 'leve3' " +
                "   when num>60 and num<=80 then 'leve4' " +
                "   when num>80 and num<=110 then 'leve5' " +
                "   when num>110 then 'leve6'end leve " +
                "   from (                 " +
                "   select (SELECT nvl(SUM(nvl(a_hours, 0)), 0) " +
                "                  from formtable_main_744_dt1 aa " +
                "                 where cday >= '"+begindate+"' " +
                "                   and cday <= '"+enddate+"' " +
                "                   and aa.work_code = a.WORKCODE " +
                "                   and (overtime_type like '%法定%' or overtime_type like '%周末%' or overtime_type like '%平时%')) as num from uf_hr_persondata a " +
                "    where (employeetype in ('正式员工', '实习员工') or (employeetype = '劳务人员' and joblevel <> '99')) " +
                "      and status = '有效' and identitytype = '"+identitytype+"' and worktype='标准工时' and currdeptcode in("+orgcode+")) t) group by leve";
        rs.execute(sql);
        while(rs.next()){
            String leve = Util.null2String(rs.getString("leve"));
            String num = Util.null2String(rs.getString("num"));
            if("leve1".equals(leve)){
                leve1 = num;
            }else if("leve2".equals(leve)){
                leve2 = num;
            }else if("leve3".equals(leve)){
                leve3 = num;
            }else if("leve4".equals(leve)){
                leve4 = num;
            }else if("leve5".equals(leve)){
                leve5 = num;
            }else if("leve6".equals(leve)){
                leve6 = num;
            }

        }
        map.put("leve1",leve1);
        map.put("leve2",leve2);
        map.put("leve3",leve3);
        map.put("leve4",leve4);
        map.put("leve5",leve5);
        map.put("leve6",leve6);
        return map;
    }
}
