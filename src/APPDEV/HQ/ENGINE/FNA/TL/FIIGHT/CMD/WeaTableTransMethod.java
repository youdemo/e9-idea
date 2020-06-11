package APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD;


import weaver.general.Util;

public class WeaTableTransMethod {
    public static String getRequestUrl(String param,String param2){
        String showvalue=param;
        String params[]=Util.TokenizerString2(param2,"+");
        String REFUND = params[0];
        String WFStatus = params[1];

        if(Util.getDoubleValue(REFUND,0)>0 || "拒绝".equals(WFStatus)){
            showvalue = "<font color=\"red\">"+showvalue+"</font>";
        }
        String result = showvalue;
        if(!"".equals(param)){
            result = "<a href='/workflow/request/ViewRequestForwardSPA.jsp?requestid="+param+"&ismonitor=1' target='_blank'>"+showvalue+"</a>";
        }
        return result;
    }

    public static String getjpctUrl(String param,String param2){
        String showvalue=param;
        String params[]=Util.TokenizerString2(param2,"+");
        String REFUND = params[2];
        String WFStatus = params[3];

        if(Util.getDoubleValue(REFUND,0)>0 || "拒绝".equals(WFStatus)){
            showvalue = "<font color=\"red\">"+showvalue+"</font>";
        }
        String result = showvalue;
        if(!"".equals(param)){
            result = "<a href='/wui/index.html?hideHeader=true&hideAside=true#/main/cs/app/d86ff9a6c3f9499885b75626593dd3db_flightorderDetail?rqid="+params[0]+"&FlightClass=N&lastmonth="+params[1]+"' target='_blank'>"+showvalue+"</a>";
        }
        return result;
    }

    public static String setcolor(String showvalue,String param2){
        String params[]=Util.TokenizerString2(param2,"+");
        String REFUND = params[0];
        String WFStatus = params[1];

        if(Util.getDoubleValue(REFUND,0)>0 || "拒绝".equals(WFStatus)){
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
