package test;

import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		String workcode= "V00007893";
		String orgcodes="100014";
		System.out.println("'"+orgcodes.replace(",","','")+"'");
		System.out.println(workcode.substring(0,workcode.length()-1));
		System.out.println(workcode.substring(workcode.length()-1));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(sdf.format(new Date()));
		String urlpara = "DPLXSSO#DP100021#20200303154023";
		System.out.println(Util.getEncrypt(urlpara));
		//SettlemenFlightOrderCron
		//?key=89872485E5C5D38008DC36B5D725BE65&paramstr=DP10002,20200303154023
		//key是将 标识（DPLXSSO）+#+工号+#+时间（yyyyMMddHHmmss）MD5加密（例如：DPLXSSO#DP100021#20200303154023）
		//paramstr是传递的工号和时间用逗号分隔（例如DP10002,20200303154023）

	}

}
