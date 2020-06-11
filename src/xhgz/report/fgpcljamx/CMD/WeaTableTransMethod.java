package xhgz.report.fgpcljamx.CMD;


import weaver.general.Util;

public class WeaTableTransMethod {


    public static String fundFormat(String num) {
        if ("".equals(num)) {
            num = "0";
        }
        double number = Util.getDoubleValue(num);
        return String.format("%,.2f", number);
    }


}
