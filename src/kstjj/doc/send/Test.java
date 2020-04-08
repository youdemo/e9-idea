package kstjj.doc.send;

import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String aaa = "附件.xls";
        System.out.println(aaa.substring(aaa.lastIndexOf("."),aaa.length()));
    }
}
