package APPDEV.HQ.ENGINE.FNA.TL.FIIGHT.CMD;


import weaver.hrm.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Test {
    public static void main(String[] args){
        //SettlemenFlightOrderCron;
        //SettlementHotelOrderCron
       // SetApprovalAction
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        String accDate = format.format(date);
        System.out.println(accDate);
        System.out.println(UUID.randomUUID().toString());
        User  user = new User();
        user.getLoginid();
    }
}
