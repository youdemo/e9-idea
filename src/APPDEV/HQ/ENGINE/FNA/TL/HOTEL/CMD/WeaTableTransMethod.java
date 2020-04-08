package APPDEV.HQ.ENGINE.FNA.TL.HOTEL.CMD;



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
            result = "<a href='/wui/index.html?hideHeader=true&hideAside=true#/main/cs/app/bcec8ce439084c3c9fa235d9a3ccab7c_hotelorderDetail?rqid="+param2+"' target='_blank'>"+param+"</a>";
        }
        return result;
    }

}
