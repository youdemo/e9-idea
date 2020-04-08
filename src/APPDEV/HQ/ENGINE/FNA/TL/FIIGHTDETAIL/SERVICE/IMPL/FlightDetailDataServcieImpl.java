package APPDEV.HQ.ENGINE.FNA.TL.FIIGHTDETAIL.SERVICE.IMPL;

import APPDEV.HQ.ENGINE.FNA.TL.FIIGHTDETAIL.CMD.WeaTableCmd;
import APPDEV.HQ.ENGINE.FNA.TL.FIIGHTDETAIL.CMD.WeatableConditonCmd;
import APPDEV.HQ.ENGINE.FNA.TL.FIIGHTDETAIL.SERVICE.FlightDetailDataServcie;
import com.engine.core.impl.Service;

import java.util.Map;


public class FlightDetailDataServcieImpl extends Service implements FlightDetailDataServcie {

    @Override
    public Map<String, Object> weatableDemo(Map<String, Object> params) {
        return commandExecutor.execute(new WeaTableCmd(user,params));
    }

    @Override
    public Map<String, Object> weatableConditonDemo(Map<String, Object> params) {
        return commandExecutor.execute(new WeatableConditonCmd(user,params));
    }

}
