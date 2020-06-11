package gvo.hr.portal.engine.glz;

import gvo.hr.portal.util.HrUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/4/24 10:15
 * version v1
 * desc
 */
public class GetGLZOrgDataImpl {
    /**
     * 拼接组织架构图的json
     * @param workcode
     * @param recordid
     */
     public String getOrgdataJson(String workcode,String recordid){
         BaseBean log = new BaseBean();
         String result = "";
         RecordSet rs = new RecordSet();
         HrUtil hrutil = new HrUtil();
         String currdeptcode = "";
         String supdeptcode = "";
         String sql = "";
         //log.writeLog("GetGLZOrgDataImpl","workcode:"+workcode+" recordid:"+recordid);
         if("0".equals(recordid)){
             sql = "select currdeptcode from uf_hr_persondata where workcode='"+workcode+"' and recordid='"+recordid+"'";
             rs.execute(sql);
             if(rs.next()){
                 currdeptcode = Util.null2String(rs.getString("currdeptcode"));
             }
         }else{
             sql = "select currdeptcode from uf_hr_persondata_dt1 where workcode='"+workcode+"' and recordid='"+recordid+"'";
             rs.execute(sql);
             if(rs.next()){
                 currdeptcode = Util.null2String(rs.getString("currdeptcode"));
             }
         }
         sql = "select suporgcode from uf_hr_orgdata where orgcode='"+currdeptcode+"'";
         rs.execute(sql);
         if(rs.next()){
             supdeptcode = Util.null2String(rs.getString("suporgcode"));
         }
         String orgcodes = hrutil.getOrgcodes2(workcode,recordid);
         if(orgcodes.indexOf("'"+currdeptcode+"'")<0){
             if(!"".equals(orgcodes)){
                 orgcodes = orgcodes+",'"+currdeptcode+"'";
             }else{
                 orgcodes = "'"+currdeptcode+"'";
             }
         }
         if(!"".equals(supdeptcode)) {
             orgcodes = orgcodes + ",'" + supdeptcode + "'";
         }
         String firstcode = supdeptcode;
         if("".equals(supdeptcode)){
             firstcode = currdeptcode;
         }
         //log.writeLog("GetGLZOrgDataImpl","firstcode:"+firstcode+" orgcodes:"+orgcodes+" currdeptcode:"+currdeptcode+" supdeptcode:"+supdeptcode);

         Map<String, Map<String,String>> orgdataMap = getorgDataInfo(orgcodes);
         //log.writeLog(com.alibaba.fastjson.JSONObject.toJSONString(orgdataMap).toString());
         JSONObject orgdataJson = null;
         try {
             orgdataJson =getOrgJson(firstcode,orgdataMap,currdeptcode,orgcodes);
             result =  orgdataJson.toString();
         } catch (Exception e) {
             new BaseBean().writeLog("hr门户获取组织结构数据失败：",e);
             result = "{\"data\":{},\"children\":[]}";
         }
         return result;


     }

    /**
     * 递归获取组织架构json串数据
     * @param orgcode
     * @param orgdataMap
     * @param currdeptcode
     * @return
     * @throws Exception
     */
     public JSONObject getOrgJson(String orgcode,Map<String, Map<String,String>> orgdataMap,String currdeptcode,String orgcodes) throws Exception{
         //new BaseBean().writeLog("orgcode:"+orgcode);
         RecordSet rs = new RecordSet();
         JSONObject json = new JSONObject();
         Map<String,String> map = orgdataMap.get(orgcode);
         JSONObject data = new JSONObject();
         data.put("id",Util.null2String(map.get("id")));
         data.put("department",Util.null2String(map.get("department")));
         data.put("departmentlabel","");
         data.put("manager",Util.null2String(map.get("manager")));
         data.put("managerlabel",Util.null2String(map.get("managerlabel")));
         data.put("zz",Util.null2String(map.get("zz")));
         data.put("zzlabel","在职");
         data.put("llfs",Util.null2String(map.get("llfs")));
         data.put("llfslabel","联系方式");
         if(currdeptcode.equals(orgcode)){
             data.put("rgb","124,167,174");
         }else{
             data.put("rgb","79,129,189");
         }
         data.put("imageid",Util.null2String(map.get("imageid")));
         json.put("data",data);
         JSONArray ja = new JSONArray();
         String sql = "select orgcode,case when orgcode='"+currdeptcode+"' then '1' else '2' end as sx from uf_hr_orgdata where suporgcode='"+orgcode+"' and status='0' order by sx asc,orgcode asc";
         rs.execute(sql);
         while(rs.next()){

             String orgcodedt = Util.null2String(rs.getString("orgcode"));
             if(orgcodes.indexOf("'"+orgcodedt+"'")<0){
                 continue;
             }
             JSONObject jo = getOrgJson(orgcodedt,orgdataMap,currdeptcode,orgcodes);
             ja.put(jo);
         }
         json.put("children",ja);
         return json;

     }

    /**
     * 获取部门信息
     * @param orgcodes
     * @return
     */
     public  Map<String, Map<String,String>> getorgDataInfo(String orgcodes){
         Map<String, Map<String,String>> resultMap  = new HashMap<String, Map<String,String>>();
        RecordSet rs = new RecordSet();
        String sql = "select orgcode,orgname,b.name,b.contact,a.orglevel,case when a.fzrrcd='0' then b.jobtitlename else (select jobtitlename from uf_hr_persondata_dt1 where workcode=a.fzrcode and recordid=a.fzrrcd) end as jobtitlename,(select count(1) from uf_hr_persondata where (employeetype in ('正式员工','实习员工') or (employeetype='劳务人员' and joblevel <>'99')) and status='有效' and currdeptcode=a.orgcode) as zzrs,(select (select imagefileid from docimagefile where docid=t.photo) as imageid from uf_hr_personphoto t where workcode=a.fzrcode) as imageid from uf_hr_orgdata a left join uf_hr_persondata b on a.fzrcode=b.workcode where orgcode in ("+orgcodes+")";
        rs.execute(sql);
        while(rs.next()){
            String orgcode = Util.null2String(rs.getString("orgcode"));
            Map<String,String> map = new HashMap<String,String>();
            map.put("id",orgcode);//组织编码
            map.put("department",Util.null2String(rs.getString("orgname")));//部门名称
            map.put("manager",Util.null2String(rs.getString("name")));//部门负责人
            String jobtitlename = Util.null2String(rs.getString("jobtitlename"));
            if("".equals(jobtitlename)){
                jobtitlename = "&nbsp;";
            }
            map.put("managerlabel",jobtitlename);//部门负责人岗位

            map.put("zz",getZzrl(orgcode));//在职人数
            map.put("llfs",Util.null2String(rs.getString("contact")));//联系方式
            map.put("orglevel",Util.null2String(rs.getString("orglevel")));//组织层级
            map.put("imageid",Util.null2String(rs.getString("imageid")));//组织层级
            resultMap.put(orgcode,map);
        }
        return resultMap;
     }

     public String getZzrl(String orgcode){
         RecordSet rs = new RecordSet();
         String zzrl = "";
         String sql = "select count(1) as zzrl from uf_hr_persondata a where (a.employeetype in ('正式员工','实习员工') or (a.employeetype='劳务人员' and a.joblevel <>'99')) and a.status = '有效' and currdeptcode in (select distinct orgcode from uf_hr_orgdata where status = '0' start with orgcode='"+orgcode+"' connect by prior orgcode = suporgcode)";
         rs.execute(sql);
         if(rs.next()){
             zzrl = Util.null2String(rs.getString("zzrl"));
         }
         return zzrl;
     }


}
