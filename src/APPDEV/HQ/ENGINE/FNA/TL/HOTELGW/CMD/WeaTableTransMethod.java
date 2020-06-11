package APPDEV.HQ.ENGINE.FNA.TL.HOTELGW.CMD;


import weaver.general.Util;

public class WeaTableTransMethod {
    public static String getRequestUrl(String param,String param2){
        String showvalue = param;
        if("拒绝".equals(param2)){
            showvalue = "<font color=\"red\">"+showvalue+"</font>";
        }

        String result = showvalue;
        if(!"".equals(param)){
            result = "<a href='/workflow/request/ViewRequestForwardSPA.jsp?requestid="+param2+"&ismonitor=1' target='_blank'>"+showvalue+"</a>";
        }
        return result;
    }

    public static String getjpctUrl(String param,String param2){
        String showvalue = param;
        String params[]=Util.TokenizerString2(param2,"+");
        String WFStatus= params[2];
        if("拒绝".equals(WFStatus)){
            showvalue = "<font color=\"red\">"+showvalue+"</font>";
        }
        String result = showvalue;
        if(!"".equals(param)){
            result = "<a href='/wui/index.html?hideHeader=true&hideAside=true#/main/cs/app/bcec8ce439084c3c9fa235d9a3ccab7c_hotelorderDetail?rqid="+params[0]+"&IsDomestic=false&lastmonth="+params[1]+"' target='_blank'>"+showvalue+"</a>";
        }
        return result;
    }

    public static String setcolor(String showvalue,String WFStatus){

        if("拒绝".equals(WFStatus)){
            return "<font color=\"red\">"+showvalue+"</font>";
        }
        return showvalue;

    }

    public static String canCheck(String tjcs){
        if(Util.getIntValue(tjcs,0)<=0){
            return "true";
        }else{
            return  "false";
        }
    }

}
