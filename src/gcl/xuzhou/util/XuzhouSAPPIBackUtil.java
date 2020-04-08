package gcl.xuzhou.util;

import gcl.xuzhou.webservice.SI_BPM_REPORT_Out_SyncServiceStub;
import gcl.xuzhou.webservice.SI_BPM_REPORT_Out_SyncServiceStub.*;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import weaver.general.BaseBean;

import java.util.HashMap;
import java.util.Map;

/**
 * BPM透过SAP PI 回报审批结果
 *
 * @author : adore
 * @version : v1.0
 * @since : 2019-09-25 13:41
 */

public class XuzhouSAPPIBackUtil extends BaseBean {
    private static Logger logger = Logger.getLogger(XuzhouSAPPIBackUtil.class);

    public String xuzhouSAPPIBack(Map<String, String> map) {
        String result = "";
        try {
            JSONObject object = new JSONObject();
            SI_BPM_REPORT_Out_SyncServiceStub serviceStub = new SI_BPM_REPORT_Out_SyncServiceStub();
            HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
            auth.setUsername(SAPWebLogin.SAP_LOGIN_USER);
            auth.setPassword(SAPWebLogin.SAP_LOGIN_PASSWORD);
            serviceStub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
            MT_BPM_REPORT mt_bpm_report = new MT_BPM_REPORT();
            DT_BPM_REPORT dt_bpm_report = new DT_BPM_REPORT();
            String docId = map.get("docId");
            String docNumber = map.get("docNumber");
            String otherData = map.get("otherData");
            String status = map.get("status");
            String workflowCode = map.get("workflowCode");
            this.writeLog("workflowCode:" + workflowCode);
            dt_bpm_report.setDocId(docId);
            dt_bpm_report.setDocNumber(docNumber);
            dt_bpm_report.setOtherData(otherData);
            dt_bpm_report.setStatus(status);
            dt_bpm_report.setWorkflowCode(workflowCode);
            mt_bpm_report.setMT_BPM_REPORT(dt_bpm_report);
            MT_BPM_REPORT_RESPONSE report_response = serviceStub.sI_BPM_REPORT_Out_Sync(mt_bpm_report);
            DT_BPM_REPORT_RESPONSE report_response1 = report_response.getMT_BPM_REPORT_RESPONSE();
            String type = report_response1.getType();
            String id = report_response1.getId();
            String number = report_response1.getNumber();
            String message = report_response1.getMessage();
            this.writeLog("type:" + type);
            object.put("type", type);
            object.put("id", id);
            object.put("number", number);
            object.put("message", message);
            result = object.toString();
        } catch (Exception e) {
            this.writeLog(e);
        }
        return result;
    }

    public String xuzhouSAPPayment(String param) {
        String result = "";
        try {
            SI_BPM_REPORT_Out_SyncServiceStub serviceStub = new SI_BPM_REPORT_Out_SyncServiceStub();
            HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
            auth.setUsername(SAPWebLogin.SAP_LOGIN_USER);
            auth.setPassword(SAPWebLogin.SAP_LOGIN_PASSWORD);
            serviceStub._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);


            MT_BPM_REPORT mt_bpm_report = new MT_BPM_REPORT();
            DT_BPM_REPORT dt_bpm_report = new DT_BPM_REPORT();
//            dt_bpm_report.setDocId(docId);
//            dt_bpm_report.setDocNumber(docNumber);
//            dt_bpm_report.setOtherData(otherData);
//            dt_bpm_report.setStatus(status);
//            dt_bpm_report.setWorkflowCode("2527");
            String workflowCode = "";

            JSONArray array = new JSONArray(param);
            for (int i = 0; i < array.length(); i++) {
                DataSet_type1 type1 = new DataSet_type1();
                JSONObject object1 = array.getJSONObject(i);
                type1.setDocId(object1.getString("guid"));
                type1.setDocNumber("");
                type1.setOtherData(object1.getString("otherData"));
                type1.setStatus(object1.getString("status"));
                workflowCode = object1.getString("workflowCode");
                dt_bpm_report.addDataSet(type1);
            }

            DataSet_type1[] dataSet1 = dt_bpm_report.getDataSet();
            for (int i = 0; i < dataSet1.length; i++) {
                this.writeLog("guid:" + dataSet1[i].getDocId() + "|i=" + i);
                this.writeLog("status:" + dataSet1[i].getStatus() + "|i=" + i);
            }

            dt_bpm_report.setWorkflowCode(workflowCode);
            mt_bpm_report.setMT_BPM_REPORT(dt_bpm_report);

            MT_BPM_REPORT_RESPONSE report_response = serviceStub.sI_BPM_REPORT_Out_Sync(mt_bpm_report);
            DT_BPM_REPORT_RESPONSE report_response1 = report_response.getMT_BPM_REPORT_RESPONSE();
            DataSet_type0[] dataSet = report_response1.getDataSet();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < dataSet.length; i++) {
                JSONObject object = new JSONObject();
                String type = dataSet[i].getType();
                String id = dataSet[i].getId();
                String number = dataSet[i].getNumber();
                String message = dataSet[i].getMessage();
                object.put("type", type);
                object.put("id", id);
                object.put("number", number);
                object.put("message", message);
                object.put("num", i);
                this.writeLog("type:" + i + "|" + type);
                jsonArray.put(object);
            }
            result = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
            this.writeLog("异常：" + e);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        XuzhouSAPPIBackUtil backUtil = new XuzhouSAPPIBackUtil();
        String param = "";
        JSONArray array = new JSONArray();
        Map<String, String> object = new HashMap<>();
        // object.put("guid", "000C291546051ED9BEE0E24A5F764F4C");
        object.put("workflowCode", "3023");
        object.put("status", "04");
        object.put("otherData", "");
        object.put("docNumber", "");
        object.put("docId", "1000000186");
        array.put(object);
        param = array.toString();
        String result = backUtil.xuzhouSAPPIBack(object);
        System.out.println("result:" + result);
    }
}
