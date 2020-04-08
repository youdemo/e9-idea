package kstjj.doc.send;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.itextpdf.text.log.SysoCounter;

import sun.misc.BASE64Decoder;

/**
 * 支持Microsoft .net 的RSA加密类
 * <p>
 * 项目名称：Fe-Pt5.5DOV 类名称：RSA4DotNet 类描述： 创建人：Eddie 创建时间：Aug 2, 2012 11:18:15 AM
 * 修改人：Eddie 修改时间：Aug 2, 2012 11:18:15 AM 修改备注：
 */
public class RSA4DotNet {

    private KeyPairGenerator keyPairGen;
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    private final static String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    /**
     * 构造方法
     *
     * @throws NoSuchAlgorithmException
     */
    public RSA4DotNet() throws NoSuchAlgorithmException {
        this.init();
    }

    /**
     * 初始化
     *
     * @throws NoSuchAlgorithmException
     */
    private void init() throws NoSuchAlgorithmException {
        keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        keyPair = keyPairGen.generateKeyPair();
        publicKey = (RSAPublicKey) keyPair.getPublic();
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    /**
     * 获取支持.net 的私钥XML字符串
     *
     * @return
     */
    public String getPriKeyXMLString() {
        if (this.privateKey == null)
            return null;
        return privatekeyinfoToXMLRSAPriKey(this.privateKey.getEncoded());
    }

    /**
     * 获取支持.net 的公钥XML字符串
     *
     * @return
     */
    public String getPubKeyXMLString() {
        if (this.privateKey == null)
            return null;
        return privatekeyinfoToXMLRSAPubKey(this.privateKey.getEncoded());
    }

    /**
     * 解密
     *

     * @throws Exception
     */
    public byte[] Encrypt(byte[] sourceBytes, String dotNetKey) throws Exception {
    	System.out.println(dotNetKey);
        if (dotNetKey == null) return null;
        PublicKey publicK = xmlRSAPubKeyToPublicKeyInfo(dotNetKey);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        return cipher.doFinal(sourceBytes);
    }

    /**
     * 解密
     *
     * @throws Exception
     */
    public byte[] Decrypt(byte[] sourceBytes) throws Exception {
        if (sourceBytes == null)
            return null;
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(sourceBytes);
    }

    private static final String b64encode(byte[] data) { // Use internal sun
        // class for B64
        // encoding
        sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
        String b64str = enc.encodeBuffer(data).trim(); // removing any
        // leading/trailing
        // whitespace
        return b64str;
    }

    // 把 .net的公钥xml转回java的公钥字符串
    private PublicKey xmlRSAPubKeyToPublicKeyInfo(String xmlPublicKey) throws Exception {
        Document doc = DocumentHelper.parseText(xmlPublicKey);
        Node modulesNode = doc.selectSingleNode("//PublicKey/RSAKeyValue/Modulus");
        Node exponentNode = doc.selectSingleNode("//PublicKey/RSAKeyValue/Exponent");

        byte[] modulusBytes = (new BASE64Decoder()).decodeBuffer(modulesNode.getText());
        byte[] exponentBytes = (new BASE64Decoder()).decodeBuffer(exponentNode.getText());
        BigInteger modulus = new BigInteger(1, modulusBytes);
        BigInteger exponent = new BigInteger(1, exponentBytes);

        RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PublicKey pubKey = fact.generatePublic(rsaPubKey);

        return pubKey;
    }

    // --- Returns XML encoded RSA private key string suitable for .NET
    // CryptoServiceProvider.FromXmlString(true) ------
    // --- Leading zero bytes (most significant) must be removed for XML
    // encoding for .NET; otherwise format error ---

    private String privatekeyinfoToXMLRSAPriKey(byte[] encodedPrivkey) {
        try {
            StringBuffer buff = new StringBuffer(1024);

            PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(
                    encodedPrivkey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory
                    .generatePrivate(pvkKeySpec);

            buff.append("<RSAKeyValue>");
            buff
                    .append("<Modulus>"
                            + b64encode(removeMSZero(pvkKey.getModulus()
                            .toByteArray())) + "</Modulus>");

            buff.append("<Exponent>"
                    + b64encode(removeMSZero(pvkKey.getPublicExponent()
                    .toByteArray())) + "</Exponent>");

            buff.append("<P>"
                    + b64encode(removeMSZero(pvkKey.getPrimeP().toByteArray()))
                    + "</P>");

            buff.append("<Q>"
                    + b64encode(removeMSZero(pvkKey.getPrimeQ().toByteArray()))
                    + "</Q>");

            buff.append("<DP>"
                    + b64encode(removeMSZero(pvkKey.getPrimeExponentP()
                    .toByteArray())) + "</DP>");

            buff.append("<DQ>"
                    + b64encode(removeMSZero(pvkKey.getPrimeExponentQ()
                    .toByteArray())) + "</DQ>");

            buff.append("<InverseQ>"
                    + b64encode(removeMSZero(pvkKey.getCrtCoefficient()
                    .toByteArray())) + "</InverseQ>");

            buff.append("<D>"
                    + b64encode(removeMSZero(pvkKey.getPrivateExponent()
                    .toByteArray())) + "</D>");
            buff.append("</RSAKeyValue>");

            return buff.toString();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    // --- Returns XML encoded RSA public key string suitable for .NET
    // CryptoServiceProvider.FromXmlString(true) ------
    // --- Leading zero bytes (most significant) must be removed for XML
    // encoding for .NET; otherwise format error ---

    private String privatekeyinfoToXMLRSAPubKey(byte[] encodedPrivkey) {
        try {
            StringBuffer buff = new StringBuffer(1024);

            PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(
                    encodedPrivkey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory
                    .generatePrivate(pvkKeySpec);
            buff.append("<RSAKeyValue>");
            buff
                    .append("<Modulus>"
                            + b64encode(removeMSZero(pvkKey.getModulus()
                            .toByteArray())) + "</Modulus>");
            buff.append("<Exponent>"
                    + b64encode(removeMSZero(pvkKey.getPublicExponent()
                    .toByteArray())) + "</Exponent>");
            buff.append("</RSAKeyValue>");
            return buff.toString();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    // --------- remove leading (Most Significant) zero byte if present
    // ----------------
    private byte[] removeMSZero(byte[] data) {
        byte[] data1;
        int len = data.length;
        if (data[0] == 0) {
            data1 = new byte[data.length - 1];
            System.arraycopy(data, 1, data1, 0, len - 1);
        } else
            data1 = data;

        return data1;
    }

}
