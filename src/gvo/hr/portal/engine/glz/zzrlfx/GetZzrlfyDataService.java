package gvo.hr.portal.engine.glz.zzrlfx;

import com.engine.common.util.ParamUtil;
import gvo.hr.portal.util.HrUtil;
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
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/5/8 16:04
 * version v1
 * desc 过去在职人力数据
 */
public class GetZzrlfyDataService {
    @GET
    @Path("/getbasedata")
    @Produces({MediaType.TEXT_PLAIN})
    /**
     * 获取在职人力数据
     */
    public String getSyShowData(@Context HttpServletRequest request, @Context HttpServletResponse response)  {
        RecordSet rs = new RecordSet();
        String result = "";
        User user = HrmUserVarify.getUser(request, response);
        HrUtil hrutil = new HrUtil();
        Map<String,String> personMap = hrutil.getPersonBaseinfo(user.getUID()+"");
        String workcode = personMap.get("workcode");
        String recordid = personMap.get("recordid");
        Map<String,Object> params = ParamUtil.request2Map(request);
        String orgcode = "";
        try {
            orgcode = URLDecoder.decode(Util.null2String(params.get("orgcode")), "UTF-8");
        }catch(Exception e){
            new BaseBean().writeLog(this.getClass().getName(),e);
        }
        String sfbhxjzz = Util.null2String(params.get("sfbhxjzz"));
        String type = Util.null2String(params.get("type"));//0间接人员 1 直接人员
        String orgcodes = hrutil.getOrgcodes(workcode,recordid);
        Map<String,String> map = new HashMap<String,String>();
        Map<String,String> jgfbmap = new HashMap<String,String>();
        String jjjson = "";
        String zjjson = "";
        if("0".equals(type)) {//间接
            map.put("dylzrs", "0");///当月入职人数
            map.put("symrs", "0");//上月末人数
            map.put("dydrzrs", "0");//当月调入总人数
            map.put("dyrzrs", "0");//当月调出总人数
            map.put("dydczrs", "0");//当月离职人事
            map.put("female", "0");//女
            map.put("male", "0");//男
            map.put("agefb1", "0");//25岁以下
            map.put("agefb2", "0");//25到35岁
            map.put("agefb3", "0");//35到45岁
            map.put("agefb4", "0");//45岁以上
            map.put("zdfb1", "0");//1-3等
            map.put("zdfb2", "0");//4-6等
            map.put("zdfb3", "0");//7-12等
            map.put("zdfb4", "0");//13等以上
            map.put("xlfn1", "0");//大专及以下
            map.put("xlfn2", "0");//本科
            map.put("xlfn3", "0");//硕士
            map.put("xlfn4", "0");//博士
            map.put("jtglfb1", "0");//一年以下
            map.put("jtglfb2", "0");//一到三年
            map.put("jtglfb3", "0");//三到五年
            map.put("jtglfb4", "0");//五年以上
            map.put("shglfb1", "0");//一年以下
            map.put("shglfb2", "0");//一到三年
            map.put("shglfb3", "0");//三到五年
            map.put("shglfb4", "0");//五年以上
        }else if("1".equals(type)){//直接
            map.put("dylzrs", "0");//上月末人数
            map.put("symrs", "0");//当月入职人数
            map.put("dydrzrs", "0");//当月调入总人数
            map.put("dyrzrs", "0");//当月调出总人数
            map.put("dydczrs", "0");//当月离职人事
            map.put("female", "0");//女
            map.put("male", "0");//男
            map.put("agefb1", "0");//25岁以下
            map.put("agefb2", "0");//25到35岁
            map.put("agefb3", "0");//35到45岁
            map.put("agefb4", "0");//45岁以上
            map.put("yglbfb1", "0");//正式员工
            map.put("yglbfb2", "0");//劳务人员
            map.put("yglbfb3", "0");//实习员工
            map.put("xlfn1", "0");//中专及以下
            map.put("xlfn2", "0");//高中
            map.put("xlfn3", "0");//大专及以上
            map.put("jtglfb1", "0");//7天及以下
            map.put("jtglfb2", "0");//一个月及以下
            map.put("jtglfb3", "0");//1-6个月含
            map.put("jtglfb4", "0");//6个月-1年含
            map.put("jtglfb5", "0");//1-2年含
            map.put("jtglfb6", "0");//2-3年含
            map.put("jtglfb7", "0");//3年以上

        }

        String sql = "select * from uf_hr_zzrlfx_data t where zzbm in ("+orgcodes+")";
        if(!"".equals(orgcode)){
            if("false".equals(sfbhxjzz)) {
                sql = sql + " and Upper((select orgname from uf_hr_orgdata where orgcode=t.zzbm)) like Upper('%" + orgcode + "%')";
            }else{
                String subcode = hrutil.getOrgcodes3(orgcode);
                sql = sql + " and zzbm in ("+subcode+")";

            }
        }
            rs.execute(sql);
            while (rs.next()) {
                jjjson = Util.null2String(rs.getString("jjjson"));
                zjjson = Util.null2String(rs.getString("zjjson"));
                if("0".equals(type)){
                    try {
                        getDataJJ(map, jjjson);
                    }catch (Exception e){
                        new BaseBean().writeLog("GetZzrlfyDataService",e);
                    }
                }else if("1".equals(type)){
                    try {
                        getDataZJ(map,zjjson);
                    }catch (Exception e){
                        new BaseBean().writeLog("GetZzrlfyDataService",e);
                    }
                }
            }
            //籍贯分布重新计算
            if("1".equals(type)){
                GetZzrlfyDataServiceImpl gzds = new GetZzrlfyDataServiceImpl();
                JSONObject jgfbjson = new JSONObject();
                try{
                    if("".equals(orgcode)){
                        jgfbjson = new JSONObject(gzds.getjGFBZJ(orgcodes,"直接人员"));
                    }else{
                        jgfbjson = new JSONObject(gzds.getjGFBZJ(orgcode,"直接人员"));
                    }
                    Iterator it = jgfbjson.keys();
                    while(it.hasNext()){
                        String key = it.next().toString();
                        String value = jgfbjson.getString(key);
                        jgfbmap.put(key,value);
                    }
                }catch (Exception e){

                }

            }
            result = getJsonResult(type,map,jgfbmap);



        return result;
    }

    /**
     * 合并多组织数据 间接人员
     * @param map
     * @param jjjson
     * @throws JSONException
     */
    public void getDataJJ(Map<String,String> map,String jjjson) throws JSONException {
        JSONObject json = new JSONObject(jjjson);
        Iterator itjson = json.keys();
        while(itjson.hasNext()){
            String key = itjson.next().toString();
            JSONObject jo = json.getJSONObject(key);
            Iterator it = jo.keys();
            while(it.hasNext()){
                String jokey = it.next().toString();
                String value = jo.getString(jokey);
                addNum(map,jokey.replaceAll("leve",key),value);
            }

        }

    }

    /**
     * 合并多组织数据 间接人员
     * @param map
     * @param zjjson
     * @throws JSONException
     */
    public void getDataZJ(Map<String,String> map,String zjjson) throws JSONException {
        JSONObject json = new JSONObject(zjjson);
        Iterator itjson = json.keys();
        while(itjson.hasNext()){
            String key = itjson.next().toString();
            if("jhfb".equals(key)){
                continue;
        }
            JSONObject jo = json.getJSONObject(key);
            Iterator it = jo.keys();
            while(it.hasNext()){
                String jokey = it.next().toString();
                String value = jo.getString(jokey);
                addNum(map,jokey.replaceAll("leve",key), value);

            }

        }


    }

    public void addNum(Map<String,String> map,String key,String value){
        String num = Util.null2String(map.get(key));
        if("".equals(num)){
            num = "0";
        }
        num = String.valueOf(Util.getIntValue(value,0)+Util.getIntValue(num,0));
        map.put(key,num);
    }

    /**
     *多部门时获取json串
     * @param type
     * @param map
     * @param jgfbmap
     * @return
     */
  public String getJsonResult(String type,Map<String,String> map,Map<String,String> jgfbmap ){

            JSONObject jo = new JSONObject();
            try{

                if("0".equals(type)){
                    JSONObject ryldsj = new JSONObject();
                    JSONObject sexfb = new JSONObject();
                    JSONObject agefb = new JSONObject();
                    JSONObject zdfb = new JSONObject();
                    JSONObject xlfn = new JSONObject();
                    JSONObject jtglfb = new JSONObject();
                    JSONObject shglfb = new JSONObject();
                    ryldsj.put("dylzrs", map.get("dylzrs"));//上月末人数
                    ryldsj.put("symrs", map.get("symrs"));//当月入职人数
                    ryldsj.put("dydrzrs", map.get("dydrzrs"));//当月调入总人数
                    ryldsj.put("dyrzrs", map.get("dyrzrs"));//当月调出总人数
                    ryldsj.put("dydczrs", map.get("dydczrs"));//当月离职人事
                    sexfb.put("female", map.get("female"));//女
                    sexfb.put("male", map.get("male"));//男
                    agefb.put("leve1", map.get("agefb1"));//25岁以下
                    agefb.put("leve2", map.get("agefb2"));//25到35岁
                    agefb.put("leve3", map.get("agefb3"));//35到45岁
                    agefb.put("leve4", map.get("agefb4"));//45岁以上
                    zdfb.put("leve1", map.get("zdfb1"));//1-3等
                    zdfb.put("leve2", map.get("zdfb2"));//4-6等
                    zdfb.put("leve3", map.get("zdfb3"));//7-12等
                    zdfb.put("leve4", map.get("zdfb4"));//13等以上
                    xlfn.put("leve1", map.get("xlfn1"));//大专及以下
                    xlfn.put("leve2", map.get("xlfn2"));//本科
                    xlfn.put("leve3", map.get("xlfn3"));//硕士
                    xlfn.put("leve4", map.get("xlfn4"));//博士
                    jtglfb.put("leve1", map.get("jtglfb1"));//一年以下
                    jtglfb.put("leve2", map.get("jtglfb2"));//一到三年
                    jtglfb.put("leve3", map.get("jtglfb3"));//三到五年
                    jtglfb.put("leve4", map.get("jtglfb4"));//五年以上
                    shglfb.put("leve1", map.get("shglfb1"));//一年以下
                    shglfb.put("leve2", map.get("shglfb2"));//一到三年
                    shglfb.put("leve3", map.get("shglfb3"));//三到五年
                    shglfb.put("leve4", map.get("shglfb4"));//五年以上
                    jo.put("ryldsj",ryldsj);
                    jo.put("sexfb",sexfb);
                    jo.put("agefb",agefb);
                    jo.put("zdfb",zdfb);
                    jo.put("xlfn",xlfn);
                    jo.put("jtglfb",jtglfb);
                    jo.put("shglfb",shglfb);
                }else if("1".equals(type)){
                    JSONObject ryldsj = new JSONObject();
                    JSONObject sexfb = new JSONObject();
                    JSONObject agefb = new JSONObject();
                    JSONObject yglbfb = new JSONObject();
                    JSONObject xlfn = new JSONObject();
                    JSONObject jtglfb = new JSONObject();
                    JSONObject jhfb = new JSONObject();
                    ryldsj.put("dylzrs", map.get("dylzrs"));//上月末人数
                    ryldsj.put("symrs", map.get("symrs"));//当月入职人数
                    ryldsj.put("dydrzrs", map.get("dydrzrs"));//当月调入总人数
                    ryldsj.put("dyrzrs", map.get("dyrzrs"));//当月调出总人数
                    ryldsj.put("dydczrs", map.get("dydczrs"));//当月离职人事
                    sexfb.put("female", map.get("female"));//女
                    sexfb.put("male", map.get("male"));//男
                    agefb.put("leve1", map.get("agefb1"));//25岁以下
                    agefb.put("leve2", map.get("agefb2"));//25到35岁
                    agefb.put("leve3", map.get("agefb3"));//35到45岁
                    agefb.put("leve4", map.get("agefb4"));//45岁以上
                    yglbfb.put("leve1", map.get("yglbfb1"));//正式员工
                    yglbfb.put("leve2", map.get("yglbfb2"));//劳务人员
                    yglbfb.put("leve3", map.get("yglbfb3"));//实习员工
                    xlfn.put("leve1", map.get("xlfn1"));//中专及以下
                    xlfn.put("leve2", map.get("xlfn2"));//高中
                    xlfn.put("leve3", map.get("xlfn3"));//大专及以上
                    jtglfb.put("leve1", map.get("jtglfb1"));//7天及以下
                    jtglfb.put("leve2", map.get("jtglfb2"));//一个月及以下
                    jtglfb.put("leve3", map.get("jtglfb3"));//1-6个月含
                    jtglfb.put("leve4", map.get("jtglfb4"));//6个月-1年含
                    jtglfb.put("leve5", map.get("jtglfb5"));//1-2年含
                    jtglfb.put("leve6", map.get("jtglfb6"));//2-3年含
                    jtglfb.put("leve7", map.get("jtglfb7"));//3年以上
                    Iterator it = jgfbmap.keySet().iterator();
                    while(it.hasNext()){
                        String key = (String) it.next();
                        String value = jgfbmap.get(key);
                        jhfb.put(key, value);
                    }
                    jo.put("ryldsj",ryldsj);
                    jo.put("sexfb",sexfb);
                    jo.put("agefb",agefb);
                    jo.put("yglbfb",yglbfb);
                    jo.put("xlfn",xlfn);
                    jo.put("jtglfb",jtglfb);
                    jo.put("jhfb",jhfb);
                }
            }catch(Exception e){
                new BaseBean().writeLog("GetZzrlfyDataService",e);
            }
            return jo.toString();
    }

}
