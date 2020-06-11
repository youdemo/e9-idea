package APPDEV.HQ.ENGINE.FNA.TL.HOTEL.SERVICE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

public interface HotelOrderDataServcie {
    /**
     * 获取form表单
     * @param params
     * @return
     */
    Map<String, Object> weatableDemo(Map<String, Object> params);

    /**
     * 获取高级搜索条件
     * @param params
     * @return
     */
    Map<String,Object> weatableConditonDemo(Map<String, Object> params);

    InputStream WeaReportOutExcel(HttpServletRequest request, HttpServletResponse response);
    String sendToSap(Map<String, Object> params);
}
