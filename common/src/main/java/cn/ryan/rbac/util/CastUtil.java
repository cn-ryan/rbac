package cn.ryan.rbac.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 系统类型转换工具类
 *
 * @author ryan
 * @create 2019-04-26 10:04
 **/
public final class CastUtil {
    /*
     *@Description obj转为String型
     *@Param [obj]
     *@Return java.lang.String
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:06
     */
    public static String castString(Object obj) {
        return castString(obj,"");
    }

    /*
     *@Description
     *@Param [obj, defaultValue]
     *@Return java.lang.String
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:06
     */
    public static String castString(Object obj,String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /*
     *@Description object转成double类型
     *@Param [obj]
     *@Return double
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:07
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /*
     *@Description object转成double类型
     *@Param [obj, defaultValue]
     *@Return double
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:07
     */
    public static double castDouble(Object obj, double defaultValue) {
        double value = defaultValue;
        if (obj == null) {
            return value;
        }
        String strValue = castString(obj);
        if (StringUtils.isNotEmpty(strValue)) {
            try {
                value = Double.parseDouble(strValue);
            } catch (NumberFormatException e) {
                value = defaultValue;
            }
        }
        return value;
    }

    /*
     *@Description Object转成long型
     *@Param [obj]
     *@Return long
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:07
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /*
     *@Description Object转成long型
     *@Param [obj, defaultValue]
     *@Return long
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:08
     */
    public static long castLong(Object obj, long defaultValue) {
        long value = defaultValue;
        if (obj == null) {
            return value;
        }
        String strValue = castString(obj);
        if (StringUtils.isNotEmpty(strValue)) {
            try {
                value = Long.parseLong(strValue);
            } catch (NumberFormatException e) {
                value = defaultValue;
            }
        }
        return value;
    }

    /*
     *@Description Object转成int型
     *@Param [obj]
     *@Return int
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:08
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }
    /*
     *@Description Object转成int型
     *@Param [obj, defaultValue]
     *@Return int
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:08
     */
    public static int castInt(Object obj, int defaultValue) {
        int value = defaultValue;
        if (obj == null) {
            return value;
        }
        String strValue = castString(obj);
        if (StringUtils.isNotEmpty(strValue)) {
            try {
                value = Integer.parseInt(strValue);
            } catch (NumberFormatException e) {
                value = defaultValue;
            }
        }
        return value;
    }
    /*
     *@Description Object转成boolean型
     *@Param [obj]
     *@Return boolean
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:07
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /*
     *@Description Object转成boolean型
     *@Param [obj, defaultValue]
     *@Return boolean
     *@Author ryan
     *@Date 2019/4/26
     *@Time 10:08
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean value = defaultValue;
        if (obj != null) {
            value = Boolean.parseBoolean(castString(obj));
        }
        return value;
    }
}
