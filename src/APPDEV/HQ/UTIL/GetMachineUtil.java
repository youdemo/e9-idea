package APPDEV.HQ.UTIL;

import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 获取当前环境
 *
 * @author : adore
 * @version : v1.0
 * @since : 2019-02-22 15:15
 */

public class GetMachineUtil {
    /**
     * 获取当前环境
     *
     * @return machine DEV 开发环境 95；PRO 生产环境 237||238；QAS 测试环境 96
     */
    public static String getMachine() {
        RecordSet rs = new RecordSet();
        rs.execute("select machine from uf_machine");
        String machine = "";
        if (rs.next()) {
            machine = Util.null2String(rs.getString("machine"));
        }
        return machine;
    }
}
