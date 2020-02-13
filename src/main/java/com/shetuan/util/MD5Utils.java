package com.shetuan.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/13 13:49
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class MD5Utils {
    public static   String getMD5(String src) {
        // 需要加密的字符串
        //String src = "123456";
        try {
            // 加密对象，指定加密方式
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 准备要加密的数据
            byte[] b = src.getBytes();
            // 加密
            byte[] digest = md5.digest(b);
            // 十六进制的字符
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            StringBuffer sb = new StringBuffer();
            // 处理成十六进制的字符串(通常)
            for (byte bb : digest) {
                sb.append(chars[(bb >> 4) & 15]);
                sb.append(chars[bb & 15]);
            }
            // 打印加密后的字符串
            //System.out.println(sb);
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
