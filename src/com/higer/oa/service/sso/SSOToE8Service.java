package com.higer.oa.service.sso;

import java.net.URLEncoder;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;
import weaver.conn.RecordSet;
import weaver.general.Util;

public class SSOToE8Service {
	public String encrypt(String sSrc) throws Exception {
		String sKey = "7227730012772198";
//		 if (sKey == null) {
//			 System.out.print("Key为空null");
//			 return null;
//		 }
//		 // 判断Key是否为16位
//		 if (sKey.length() != 16) {
//			 System.out.print("Key长度不是16位");
//			 return null;
//		 }
		 byte[] raw = sKey.getBytes("UTF-8");
		 SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		 Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
		 cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		 byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
		 return (new BASE64Encoder()).encodeBuffer(encrypted);
	}
	
	public String getLoginInfo(String userid) throws Exception {
		RecordSet rs = new RecordSet();
		String userCode = "";
		String sql = "select workcode from hrmresource where id = '" + userid + "'" ;
		rs.execute(sql);
		if(rs.next()){
			userCode = Util.null2String(rs.getString("workcode"));
		}
		String userXf = encrypt(userCode + "@" + userid + "#" + new Date().getTime());
		userXf = encrypt(userXf);
		String ulXf = "";
		try{
			ulXf = URLEncoder.encode(URLEncoder.encode(userXf,"UTF-8"));
		}catch(Exception e){
			e.printStackTrace(); 
		}
		return ulXf;
	}
}
