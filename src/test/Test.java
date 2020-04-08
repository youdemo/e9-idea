package test;

import weaver.general.Util;
import weaver.ofs.interfaces.SendRequestStatusDataImplForE8;
import weaver.ofs.interfaces.SendRequestStatusDataImplForE9;

import java.text.SimpleDateFormat;
import java.util.*;

public class Test {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(sdf.format(new Date()));
		String urlpara = "DPLXSSO#DP100021#20200303154023";
		System.out.println(Util.getEncrypt(urlpara));
		//?key=89872485E5C5D38008DC36B5D725BE65&paramstr=DP10002,20200303154023
		//key是将 标识（DPLXSSO）+#+工号+#+时间（yyyyMMddHHmmss）MD5加密（例如：DPLXSSO#DP100021#20200303154023）
		//paramstr是传递的工号和时间用逗号分隔（例如DP10002,20200303154023）

	}

}
