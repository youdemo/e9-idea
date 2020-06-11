package gvo.hr.portal.engine.glz.psservice;

import gvo.hr.portal.util.HrUtil;
import gvo.webservice.hr.portal.ps.AttendanceServiceStub;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * createby jianyong.tang
 * createTime 2020/5/19 11:04
 * version v1
 * desc 获取离职率
 */
public class GetPortalLzl {

    /**
     * 获取离职率
     * @param workcode
     * @param recordid
     * @return
     */
    public  String getLzl(String workcode,String recordid) throws Exception{
        RecordSet rs = new RecordSet();
        String orgcodes = "";
        String otherOrgcodes = "";
        HrUtil hrutil = new HrUtil();
        if("0".equals(recordid)){
            otherOrgcodes = hrutil.getOtherOrgcode(workcode);
        }else{
            otherOrgcodes = "-1";
        }
        otherOrgcodes = "'"+otherOrgcodes.replace(",","','")+"'";
        String sql = "select wm_concat(orgcode) as orgcodes from uf_hr_orgdata where status = '0' and ((fgldcode = '"+workcode+"' and ('0'='"+recordid+"' or fgldrcd = '"+recordid+"')) or (fzrcode = '"+workcode+"' and ('0'='"+recordid+"' or fzrrcd = '"+recordid+"')) or orgcode in("+otherOrgcodes+"))";
        rs.execute(sql);
        if(rs.next()){
            orgcodes = Util.null2String(rs.getString("orgcodes"));
        }
        if("".equals(orgcodes)){
            return "{\"rate_sum_zj\":\"0.00\",\"rate_sum_jj\":\"0.00\",\"rate_zj\":\"0.00\",\"rate_jj\":\"0.00\"}";
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = sf.format(new Date());
        String begindate = enddate.substring(0,7)+"-01";
        JSONObject parajson = new JSONObject();
        JSONArray ja = new JSONArray();
        String orgarr[] = orgcodes.split(",");
        for(String orgcode:orgarr){
           JSONObject jo = new JSONObject();
           jo.put("deptid",orgcode);
           jo.put("begindate",begindate);
           jo.put("enddate",enddate);
           ja.put(jo);
        }
        parajson.put("depts",ja);
        String result = getDataPs(parajson.toString());
        return result;

    }

    /**
     * 调用离职率ps service
     * @param params
     * @return
     * @throws Exception
     */
    public  String  getDataPs(String params) throws Exception {
        new BaseBean().writeLog("GetPortalLzl lzl",params);
        String result = "";
        String soapRequestData = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:hps=\"http://xmlns.oracle.com/Enterprise/Tools/schemas/HPS_QUIT_REQUEST.V1\">" +
                "   <soapenv:Header/>" +
                "   <soapenv:Body>" +
                "      <hps:HPS_QUIT_REQUEST>" +
                "         <!--Optional:-->" +
                "         <DETAIL>"+params+"</DETAIL>" +
                "      </hps:HPS_QUIT_REQUEST>" +
                "   </soapenv:Body>" +
                "</soapenv:Envelope>";
        PostMethod postMethod = new PostMethod("http://10.1.32.25:80/PSIGW/PeopleSoftServiceListeningConnector/PSFT_HR");
        try {
            byte[] b = soapRequestData.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b, 0, b.length);
            org.apache.commons.httpclient.methods.RequestEntity re = new InputStreamRequestEntity(is, b.length,"text/xml;charset=utf-8");
            postMethod.setRequestEntity(re);
            postMethod.setRequestHeader("SOAPAction", "HPS_QUIT.v1");
            HttpClient httpClient = new HttpClient();
            int status = httpClient.executeMethod(postMethod);
            InputStream in = postMethod.getResponseBodyAsStream();

            DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = bf.newDocumentBuilder();
            Document document = db.parse(in);
            String res = document.getElementsByTagName("response").item(0)
                    .getTextContent();
            new BaseBean().writeLog("GetPortalLzl lzl",res);
            result = res;
        } catch (Exception e) {
            new BaseBean().writeLog("GetPortalLzl lzl",e);
        }
        return result;

    }

    /**
     * 获取出勤率
     * @param
     * @return
     * @throws Exception
     */
    public  String getCql(String workcode,String recordid)  {
        RecordSet rs = new RecordSet();
        String orgcodes = "";
        String otherOrgcodes = "";
        HrUtil hrutil = new HrUtil();
        if("0".equals(recordid)){
            otherOrgcodes = hrutil.getOtherOrgcode(workcode);
        }else{
            otherOrgcodes = "-1";
        }
        otherOrgcodes = "'"+otherOrgcodes.replace(",","','")+"'";
        String sql = "select wm_concat(orgcode) as orgcodes from uf_hr_orgdata where status = '0' and ((fgldcode = '"+workcode+"' and ('0'='"+recordid+"' or fgldrcd = '"+recordid+"')) or (fzrcode = '"+workcode+"' and ('0'='"+recordid+"' or fzrrcd = '"+recordid+"')) or orgcode in("+otherOrgcodes+"))";
        rs.execute(sql);
        if(rs.next()){
            orgcodes = Util.null2String(rs.getString("orgcodes"));
        }
        if("-1".equals(orgcodes)){
            return "{\"rate\":\"-1\",\"rate1\":\"-1\",\"avg\":\"-1\",\"avg1\":\"-1\"}";
        }
        String result = "";
        try {
            Calendar ca = Calendar.getInstance();
            ca.setTime(new Date());
            ca.add(Calendar.DATE, -1);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String lastday = sf.format(ca.getTime());
            JSONObject parajson = new JSONObject();
            JSONArray ja = new JSONArray();
            String orgarr[] = orgcodes.split(",");
            for (String orgcode : orgarr) {
                JSONObject jo = new JSONObject();
                jo.put("deptid", orgcode);
                ja.put(jo);
            }
            parajson.put("depts", ja);
            parajson.put("datetime", lastday);
            new BaseBean().writeLog("GetPortalLzl cql", parajson.toString());
            AttendanceServiceStub ass = new AttendanceServiceStub();
            AttendanceServiceStub.REQUESTE assre = new AttendanceServiceStub.REQUESTE();
            AttendanceServiceStub.REQUEST assr = new AttendanceServiceStub.REQUEST();
            assr.setDatainfo(parajson.toString());
            assre.setREQUEST(assr);
            AttendanceServiceStub.REQUESTResponseE assres = ass.REQUEST(assre);
            result = assres.getREQUESTResponse().getRESPONSE().toString();
            new BaseBean().writeLog("GetPortalLzl cql", result);
        }catch (Exception e){
            new BaseBean().writeLog(e);
            result="{\"rate\":\"-1\",\"rate1\":\"-1\",\"avg\":\"-1\",\"avg1\":\"-1\"}";
        }
        return result;
    }
}
