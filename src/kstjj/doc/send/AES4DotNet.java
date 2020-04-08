package kstjj.doc.send;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 支持Microsoft .net 的AES加密解密类
 *
 * 项目名称：Fe-Pt5.5DOV 类名称：AES4DotNet 类描述： 创建人：Eddie 创建时间：Aug 2, 2012 11:38:56 AM
 * 修改人：Eddie 修改时间：Aug 2, 2012 11:38:56 AM 修改备注：
 *
 * @version
 *
 */
public class AES4DotNet {

	private final static String TRANSFORMATION = "AES/CBC/PKCS5Padding";

	/**
	 * 密钥（256位）
	 */
	private final static byte[] keybytes = { 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,
            0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,
            0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38,
            0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38};

    private final static byte[] iv = { 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31, 0x38,
            0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31 };

    public static byte[] getKeyBytes() {
    	return keybytes;
    }

    public static byte[] getIV() {
    	return iv;
    }

    /**
     * 加密
     *
     * @param content
     * @return
     * @throws Exception
     */
	public static byte[] Encrypt(byte[] content) throws Exception {
		Key key = new SecretKeySpec(keybytes, "AES");
		Cipher in = Cipher.getInstance(TRANSFORMATION);
		in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
		return in.doFinal(content);
	}

	/**
	 * 解密
	 *
	 */
	public static byte[] Decrypt(byte[] content, byte[] pArrKey, byte[] pArrIV)
			throws Exception {
		if (pArrKey == null)
			return null;
		if (pArrIV == null)
			return null;
		Cipher cip = Cipher.getInstance(TRANSFORMATION);// Cipher.getInstance("AES");
		cip.init(Cipher.DECRYPT_MODE, new SecretKeySpec(pArrKey, "AES"),
				new IvParameterSpec(pArrIV));
		return cip.doFinal(content);
	}

	public static void main(String[] args) {

		System.out.println(new String(keybytes));

	}

}
