package APPDEV.HQ.ENGINE.ITOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * createby jianyong.tang
 * createTime 2020/6/10 21:41
 * version v1
 * desc
 */
public class ITOperation_CheckReqRecord_Service {
    /**
     * 获取weatable的值
     * @param request
     * @param response
     * @return
     */
    @GET
    @Path("/getresult")
    @Produces({MediaType.TEXT_PLAIN})
    public String checkReqRecord(@Context HttpServletRequest request, @Context HttpServletResponse response){
        String result = "0";
        return result;
    }
}
