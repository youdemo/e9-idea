package APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD;



public class WeaTableTransMethod {
    public static String getRequestUrl(String param,String param2){
        String result = param;
        if(!"".equals(param)){
            result = "<a href='/workflow/request/ViewRequestForwardSPA.jsp?requestid="+param2+"&ismonitor=1' target='_blank'>"+param+"</a>";
        }
        return result;
    }

    public static String getjpctUrl(String param,String param2){
        String result = param;
        if(!"".equals(param)){
            result = "<a href='/wui/index.html?hideHeader=true&hideAside=true#/main/cs/app/d86ff9a6c3f9499885b75626593dd3db_flightorderDetail?rqid="+param2+"' target='_blank'>"+param+"</a>";
        }
        return result;
    }

}
