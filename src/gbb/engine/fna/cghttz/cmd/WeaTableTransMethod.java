package gbb.engine.fna.cghttz.cmd;



public class WeaTableTransMethod {
    public static String getRequestUrl(String param,String param2){
        String result = param;
        if(!"".equals(param)){
            result = "<a href='/workflow/request/ViewRequestForwardSPA.jsp?requestid="+param2+"&ismonitor=1' target='_blank'>"+param+"</a>";
        }
        return result;
    }

}
