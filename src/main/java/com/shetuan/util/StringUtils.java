package com.shetuan.util;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/2/3 20:58
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class StringUtils {
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

}
