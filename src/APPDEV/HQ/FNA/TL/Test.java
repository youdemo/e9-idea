package APPDEV.HQ.FNA.TL;

import APPDEV.HQ.UTIL.BringMainAndDetailByMain;
import APPDEV.HQ.UTIL.GetMachineUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * createby jianyong.tang
 * createTime 2020/6/9 15:47
 * version v1
 * desc
 */
public class Test {
    /**
     *
     * @param IV_FBID 公司代码
     * @param IV_FISTL 基金中心代码
     * @param workflowId  默认50
     * @return
     */
    public String getBaseSapData(String IV_FBID, String IV_FISTL,String workflowId) {
        Map<String, String> oaDatas = new HashMap<String, String>();
        oaDatas.put("IV_FBID", IV_FBID);
        oaDatas.put("IV_FISTL", IV_FISTL);
        String dataSourceId = "";
        String machine = GetMachineUtil.getMachine();
        if ("PRO".equals(machine)) {//正式
            dataSourceId = "41";
        }else {//96
            dataSourceId = "141";
        }
        BringMainAndDetailByMain bmb = new BringMainAndDetailByMain(dataSourceId);//正式 41 测试141
        String result = bmb.getReturn(oaDatas, workflowId, "", null);
        return result;
    }
}
