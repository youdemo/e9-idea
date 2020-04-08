package kstjj.doc.workflow;

import jxl.read.biff.Record;
import kstjj.doc.send.KunShanDTAdapter;
import kstjj.doc.send.TransUtil;
import org.json.JSONException;
import org.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CreateSWWorkFlow extends BaseCronJob {
    @Override
    public void execute() {
        BaseBean log = new BaseBean();
        log.writeLog("CreateSWWorkFlow","开始获取收文信息");
        getDocList();
        log.writeLog("CreateSWWorkFlow","开始获取收文信息");
    }

    public void getDocList()  {
        BaseBean log = new BaseBean();
        RecordSet rs = new RecordSet();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TransUtil tu = new TransUtil();
        String modeid = tu.getModeId("uf_doc_reciver_log");

        KunShanDTAdapter ksda = null;
        try {
            ksda = new KunShanDTAdapter();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String password = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "password"));
        String username = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "username"));

        List<String> recordList = null;
        try {
            recordList = ksda.getReceiveDocument(username,password);
            for(String recordid : recordList){
                if(!checkExist(recordid)){
                    Map<String,String> docMap = ksda.getDocInfo(recordid);
                    String rqid=createrq(docMap);
                    String status = "";
                    if(Integer.valueOf(rqid)>0){
                        status = "S";
                        log.writeLog("CreateSWWorkFlow","确认收文recordid"+recordid);
                        ksda.incept(recordid);//确认收文
                    }else{
                        status = "E";
                    }
                    String sql = "insert into uf_doc_reciver_log(recordid,cjdlcid,bt,jssj,zt,modedatacreatedate,modedatacreater,modedatacreatertype,formmodeid) values('" + recordid + "','" + rqid + "','" + docMap.get("docTitle") + "','" + sdf1.format(new Date()) + "','"+status+"','" + sdf.format(new Date()) + "','1','0','" + modeid + "')";
                    rs.execute(sql);
                    String billid = "";
                    sql = "select max(id) as billid from uf_doc_reciver_log where recordid='" + recordid + "'";
                    rs.execute(sql);
                    if (rs.next()) {
                        billid = Util.null2String(rs.getString("billid"));
                    }
                    if (!"".equals(billid)) {
                        ModeRightInfo ModeRightInfo = new ModeRightInfo();
                        ModeRightInfo.setNewRight(true);
                        ModeRightInfo.editModeDataShare(
                                Integer.valueOf("1"),
                                Util.getIntValue(modeid),
                                Integer.valueOf(billid));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String createrq(Map<String,String> docMap) throws Exception {
        BaseBean log = new BaseBean();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String createrid = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "swryid"));
        String wfid = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "swwfid"));

        JSONObject json = new JSONObject();
        JSONObject header = new JSONObject();
        JSONObject details = new JSONObject();
        json.put("HEADER", header);
        json.put("DETAILS", details);
        header.put("lwh",docMap.get("docCode") );//来文号
        header.put("lwrq", sdf.format(new Date()));//来文日期
        header.put("lwdw", docMap.get("docSendDeptName"));//来文单位
        header.put("bt", docMap.get("docTitle"));//来文日期
        header.put("zw", docMap.get("zw"));
        header.put("fj", docMap.get("fj"));
        header.put("gwfl",docMap.get("gwfl"));
        AutoRequestService ars = new AutoRequestService();
        log.writeLog("CreateSWWorkFlow ","createrqjson:"+json.toString());
        String result = ars.createRequest(wfid, json.toString(), createrid, "0");
        log.writeLog("CreateSWWorkFlow ","createrqresult:"+result);
        String rqid = new JSONObject(result).getString("OA_ID");
        return  rqid;
    }

    public boolean checkExist(String recordid){
        RecordSet rs = new RecordSet();
        int count = 0;
        String sql= "select count(1) as count from uf_doc_reciver_log where recordid='"+recordid+"'";
        rs.execute(sql);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count>0){
            return true;
        }
        return false;

    }
}
