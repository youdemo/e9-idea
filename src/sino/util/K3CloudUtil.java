package sino.util;

import weaver.conn.RecordSetDataSource;
import weaver.general.BaseBean;
import weaver.general.Util;

import java.util.Map;

public class K3CloudUtil {

    public boolean insertK3PurchaseMidTB(Map<String,String> map){
        boolean result = false;
        String datasource = Util.null2o(weaver.file.Prop.getPropValue("k3cloudmt", "middb"));
        RecordSetDataSource rsd = new RecordSetDataSource(datasource);
        String FID = "";//ERPID
        String FBILLNO = "";//单据编号
        String FBILLTYPE = "";//单据类型
        String FAPPROVERNO = "";//审核人工号
        String FAPPROVERDATE = "";//审核日期
        String F_OA_ID = "";//F_OA_ID
        String FDOCUMENTSTATUS = "";//单据状态
        FID = Util.null2String(map.get("FID"));
        FBILLNO = Util.null2String(map.get("FBILLNO"));
        FBILLTYPE = Util.null2String(map.get("FBILLTYPE"));
        FAPPROVERNO = Util.null2String(map.get("FAPPROVERNO"));
        FAPPROVERDATE = Util.null2String(map.get("FAPPROVERDATE"));
        F_OA_ID = Util.null2String(map.get("F_OA_ID"));
        FDOCUMENTSTATUS = Util.null2String(map.get("FDOCUMENTSTATUS"));
        String sql = "insert into OA_CLOUD_MIDAPPROVER(FID,FBILLNO,FBILLTYPE,FAPPROVERNO,FAPPROVEDATE,F_OA_ID,FDOCUMENTSTATUS) " +
                "VALUES('"+FID+"','"+FBILLNO+"','"+FBILLTYPE+"','"+FAPPROVERNO+"','"+FAPPROVERDATE+"','"+F_OA_ID+"','"+FDOCUMENTSTATUS+"')";
        new BaseBean().writeLog("insertK3PurchaseMidTB",sql);
        result = rsd.execute(sql);
        return result;
    }
}
