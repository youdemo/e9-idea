package APPDEV.HQ.ENGINE.FNA.TL.HOTELDETAIL.SERVICE.IMPL;

import APPDEV.HQ.ENGINE.FNA.TL.HOTELDETAIL.CMD.WeaTableCmd;
import APPDEV.HQ.ENGINE.FNA.TL.HOTELDETAIL.CMD.WeatableConditonCmd;
import APPDEV.HQ.ENGINE.FNA.TL.HOTELDETAIL.SERVICE.HotelDetailDataServcie;
import com.engine.core.impl.Service;

import java.util.Map;


public class HotelDetailDataServcieImpl extends Service implements HotelDetailDataServcie {

    @Override
    public Map<String, Object> weatableDemo(Map<String, Object> params) {
        return commandExecutor.execute(new WeaTableCmd(user,params));
    }

    @Override
    public Map<String, Object> weatableConditonDemo(Map<String, Object> params) {
        return commandExecutor.execute(new WeatableConditonCmd(user,params));
    }
}
