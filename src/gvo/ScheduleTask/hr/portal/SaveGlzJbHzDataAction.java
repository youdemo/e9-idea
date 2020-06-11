package gvo.ScheduleTask.hr.portal;

import gvo.hr.portal.engine.glz.jbqk.GetJbqkDataHzImpl;
import gvo.hr.portal.util.InsertUtil;
import org.json.JSONException;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/5/22 14:55
 * version v1
 * desc 更新加班汇总图表数据
 */
public class SaveGlzJbHzDataAction extends BaseCronJob {
    @Override
    public void execute() {
        BaseBean log = new BaseBean();
        log.writeLog("开始定时更新加班汇总数据");
        updateData();
        log.writeLog("定时更新加班汇总数据结束");

    }

    /**
     *
     */
    public void updateData() {
        RecordSet rs = new RecordSet();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String gxsj = sf.format(new Date());
        try {
            String orgcode = "";
            String sql = "select orgcode from uf_hr_orgdata where status=0";
            rs.execute(sql);
            while(rs.next()){
                orgcode = Util.null2String(rs.getString("orgcode"));
                orgcode = "'"+orgcode+"'";
                insertOrUpdateData(orgcode,gxsj);
            }
        }catch(Exception e){
            new BaseBean().writeLog("SaveGlzJbHzDataAction",e);
        }

    }

    /**
     *
     * @param orgcode
     * @param gxsj
     */
    public void insertOrUpdateData(String orgcode,String gxsj) throws JSONException {
        RecordSet rs = new RecordSet();
        String bmbm = orgcode.replaceAll("'","");
        String sql = "";
        GetJbqkDataHzImpl gds = new GetJbqkDataHzImpl();
        InsertUtil iu = new InsertUtil();
        String identarr[] = new String[]{"间接人员","直接人员"};
        for(String identitytype:identarr) {
            String zrs = gds.getBZrs(orgcode,identitytype);
            Map<String,String> jbss = gds.getBmsxzss(orgcode,identitytype);
            Map<String,String> leve = gds.getHzRsqj(orgcode,identitytype);
            Map<String,String> map = new HashMap<String, String>();
            map.put("orgcode", bmbm);//组织代码
            map.put("identitytype", identitytype);//员工类型
            map.put("zrs", zrs);//标准工时人数
            map.put("bzjbsxzss", jbss.get("st_tops"));//标准加班受限总时数
            map.put("dzhjbsxzss",jbss.get("up_tops"));//调整后加班受限总时数
            map.put("dysqjbzss",jbss.get("jbzsh"));//当月申请加班总时数
            map.put("leve1", leve.get("leve1"));//
            map.put("leve2", leve.get("leve2"));//
            map.put("leve3", leve.get("leve3"));//
            map.put("leve4", leve.get("leve4"));//
            map.put("leve5", leve.get("leve5"));//
            map.put("leve6", leve.get("leve6"));//
            map.put("gxsj", gxsj);
            String billid = "";
            sql = "select id from uf_hr_jbhz_data where orgcode='" + bmbm + "' and identitytype='"+identitytype+"'";
            rs.execute(sql);
            if (rs.next()) {
                billid = Util.null2String(rs.getString("id"));
            }
            if ("".equals(billid)) {
                iu.insert(map, "uf_hr_jbhz_data");
            } else {
                iu.updateGen(map, "uf_hr_jbhz_data", "id", billid);
            }
        }

    }
}
