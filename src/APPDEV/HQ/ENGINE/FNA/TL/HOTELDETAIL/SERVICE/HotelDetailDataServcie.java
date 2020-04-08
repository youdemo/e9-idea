package APPDEV.HQ.ENGINE.FNA.TL.HOTELDETAIL.SERVICE;

import java.util.Map;

public interface HotelDetailDataServcie {
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
}
