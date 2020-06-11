package gvo.ScheduleTask.hr.portal;

import gvo.hr.portal.engine.glz.zzrlfx.GetZzrlfyDataServiceImpl;
import gvo.hr.portal.util.InsertUtil;
import org.json.JSONException;
import org.json.JSONObject;
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
 * createTime 2020/5/9 16:24
 * version v1
 * desc
 */
public class SaveGLZZZFYDataAction extends BaseCronJob {
    @Override
    public void execute() {
        BaseBean log = new BaseBean();
        log.writeLog("开始定时更新管理者在职人力分析数据");
        updateData();
        log.writeLog("定时更新管理者在职人力分析数据结束");

    }
    public void updateData() {
        RecordSet rs = new RecordSet();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String gxsj = sf.format(new Date());
        try {
            String zzbm = "";
            String sql = "select orgcode from uf_hr_orgdata where status=0";
            rs.execute(sql);
            while(rs.next()){
                zzbm = Util.null2String(rs.getString("orgcode"));
                insertOrUpdateData(zzbm,gxsj);
            }
        }catch(Exception e){
            new BaseBean().writeLog("SaveGLZZZFYDataAction",e);
        }

    }


    public void insertOrUpdateData(String zzbm,String gxsj) throws JSONException {
        RecordSet rs = new RecordSet();
        String sql = "";
        GetZzrlfyDataServiceImpl gds = new GetZzrlfyDataServiceImpl();
        InsertUtil iu = new InsertUtil();
        JSONObject jjjson = new JSONObject();
        jjjson.put("ryldsj",new JSONObject(gds.getRyldsj(zzbm,"间接人员")));
        jjjson.put("sexfb",new JSONObject(gds.getGenderDstribution(zzbm,"间接人员")));
        jjjson.put("agefb",new JSONObject(gds.getNLFB(zzbm,"间接人员")));
        jjjson.put("zdfb",new JSONObject(gds.getZDFB(zzbm,"间接人员")));
        jjjson.put("xlfn",new JSONObject(gds.getXLFBJJ(zzbm,"间接人员")));
        jjjson.put("jtglfb",new JSONObject(gds.getJTGLFBJJ(zzbm,"间接人员")));
        jjjson.put("shglfb",new JSONObject(gds.getSHGLFBJJ(zzbm,"间接人员")));

        JSONObject zzjson = new JSONObject();
        zzjson.put("ryldsj",new JSONObject(gds.getRyldsj(zzbm,"直接人员")));
        zzjson.put("sexfb",new JSONObject(gds.getGenderDstribution(zzbm,"直接人员")));
        zzjson.put("agefb",new JSONObject(gds.getNLFB(zzbm,"直接人员")));
        zzjson.put("yglbfb",new JSONObject(gds.getYGLBFBZJ(zzbm,"直接人员")));
        zzjson.put("xlfn",new JSONObject(gds.getXLFBZJ(zzbm,"直接人员")));
        zzjson.put("jtglfb",new JSONObject(gds.getJTGLFBZJ(zzbm,"直接人员")));
        zzjson.put("jhfb",new JSONObject(gds.getjGFBZJ(zzbm,"直接人员")));

        Map<String,String> map = new HashMap<String,String>();
        map.put("gxsj",gxsj);
        map.put("zzbm",zzbm);
        map.put("jjjson",jjjson.toString());
        map.put("zjjson",zzjson.toString());
        String billid = "";
        sql = "select id from uf_hr_zzrlfx_data where zzbm='"+zzbm+"'";
        rs.execute(sql);
        if(rs.next()){
            billid = Util.null2String(rs.getString("id"));
        }
        if("".equals(billid)){
            iu.insert(map,"uf_hr_zzrlfx_data");
        }else{
            iu.updateGen(map,"uf_hr_zzrlfx_data","id",billid);
        }

    }




}
