package com.digiwin.ecims.core.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private static MD5 md = null;

    /**
     * 此类使用单例模式，方法取得单例类对象
     */
    public static MD5 getInstance() {
        if (md == null) {
            md = new MD5();
        }
        return md;
    }

    /**
     * @param args
     * @category MD5
     */
    MessageDigest m;
    BASE64Encoder b;
    String n;

    public String complie(String s) {
        try {
            s = s.trim();
            m = MessageDigest.getInstance("MD5");
            b = new BASE64Encoder();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            n = b.encode(m.digest(s.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return n;
    }

    /**
     * @param s
     * @return
     * @author 维杰
     * @since 2015.09.08
     */
    public String complieToBase64WithUrlEncode(String s) {
        try {
            s = s.trim();
            b = new BASE64Encoder();
            //			n = URLEncoder.encode(b.encodeBuffer(s.getBytes("UTF-8")), "UTF-8");
            n = b.encodeBuffer(s.getBytes("UTF-8"));
            n = URLEncoder.encode(n, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return n;
    }

    public String complieToBase64WithUrlEncodeNew(String s) {
        //        try {
        //            s = s.trim();
        //            byte[] tByte = Base64.getEncoder().encode(s.getBytes("UTF-8"));
        //            n = URLEncoder.encode(new String(tByte, "UTF-8"), "UTF-8");
        //        } catch (UnsupportedEncodingException e) {
        //            e.printStackTrace();
        //        }
        return n;
    }

    public static String encode(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getEncode32(digest);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 32位加密
     *
     * @param digest
     * @return
     */
    private static String getEncode32(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString().toUpperCase(); // 大写
    }

    public static void main(String[] args) {
        String str = new MD5().encode(
            "5FDD35D3A94D73514CD6F1A5B58AED53app_key2100000826formatxmlmethoddangdang.item.itemid.getsession4ECBED3068CB782DC287FACCD52E06AB6E6F1CB4C63EF3372AC7CD88FE34C063sign_methodmd5timestamp2013-05-06 13:52:03v1.05FDD35D3A94D73514CD6F1A5B58AED53");
        //        System.out.println(str);

        String s1 = "aaaddd";
        String encode1 = new MD5().complieToBase64WithUrlEncode(s1);
        System.out.println(encode1);
        try {
            System.out.println(URLDecoder.decode(encode1, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encode2 = new MD5().complieToBase64WithUrlEncodeNew(s1);
        System.out.println(encode2);
        try {
            System.out.println(URLDecoder.decode(encode2, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
