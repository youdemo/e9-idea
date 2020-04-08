package kstjj.doc.send;

import org.apache.axis.Message;
import org.apache.axis.attachments.AttachmentPart;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import javax.activation.DataHandler;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 同步政府oa平台的收发文部门信息
 */
public class GetDocOrgInfo extends BaseCronJob {
    @Override
    public void execute() {
        BaseBean log = new BaseBean();
        log.writeLog("GetDocOrgInfo","开始同步收发文部门信息");
        getDocOrgInfo();
        log.writeLog("GetDocOrgInfo","结束同步收发文部门信息");
    }

    public void getDocOrgInfo(){
        BaseBean log = new BaseBean();
        String password = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "password"));
        String username = Util.null2o(weaver.file.Prop.getPropValue("tjjdocconfig", "username"));
        try {
            KunShanDTAdapter ksd = new KunShanDTAdapter();
            List<String> result = ksd.getSelectOrg(password,username);
            for(String deptGuidText:result){
                insertMode(deptGuidText);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public void insertMode(String deptGuidText){
        TransUtil tu = new TransUtil();
        RecordSet rs = new RecordSet();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if("".equals(deptGuidText)){
            return;
        }
        String modeid = tu.getModeId("uf_inforesrdepet");
        String deptinfo[] = deptGuidText.split("x_a_b");
        String guid = "";//GUid
        String bmmc = "";//部门名称
        String bmlx = "";//部门类型
        if(deptinfo.length>=3){
            guid = deptinfo[0];//GUid
            bmmc = deptinfo[1];
            bmlx = deptinfo[2];
        }else if(deptinfo.length==2){
            guid = deptinfo[0];//GUid
            bmmc = deptinfo[1];
        }else{
            new BaseBean().writeLog("deptinfo:"+deptinfo.toString());
        }
        bmmc.replaceAll("'","''");
        int count =0;
        String sql = "select count(1) as count from uf_inforesrdepet where guid='"+guid+"'";
        rs.execute(sql);
        if(rs.next()){
            count = rs.getInt("count");
        }
        if(count<=0) {
            sql = "insert into uf_inforesrdepet(guid,bmmc,bmlx,modedatacreatedate,modedatacreater,modedatacreatertype,formmodeid) values('" + guid + "','" + bmmc + "','" + bmlx + "','" + sdf.format(new Date()) + "','1','0','" + modeid + "')";
            rs.execute(sql);
            String billid = "";
            sql = "select max(id) as billid from uf_inforesrdepet where guid='" + guid + "'";
            rs.execute(sql);
            if (rs.next()) {
                billid = Util.null2String(rs.getString("billid"));
            }
            if (!"".equals(billid)) {
                ModeRightInfo ModeRightInfo = new ModeRightInfo();
                ModeRightInfo.editModeDataShare(
                        Integer.valueOf("1"),
                        Util.getIntValue(modeid),
                        Integer.valueOf(billid));
            }
        }



    }
}
