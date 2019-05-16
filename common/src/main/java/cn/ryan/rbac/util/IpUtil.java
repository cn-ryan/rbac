package cn.ryan.rbac.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 解析ip域名、地址等信息
 *
 * @author ryan
 * @create 2019-05-09 11:38
 **/
@Slf4j
public class IpUtil {

    public static String getAddresses(String content){
        return getAddresses(content,"UTF-8");
    }

    public static String getAddresses(String content, String encodingString){
        String urlStr = PropertiesUtil.getInstance().getStringValue("ipUrl");
        String returnStr = HttpUtil.getResult(urlStr, content, encodingString);
        if (returnStr != null) {
            returnStr = decodeUnicode(returnStr);
            String[] temp = returnStr.split(",");
            if (temp.length < 3) {
                return "0";
            }
            return returnStr;
        }
        return null;
    }

    /**
     *
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    public static String getRealAddressByIP(String ip) {
        String address = "";
        try {
            address = getAddresses("ip=" + ip, "utf-8");
            ////把JSON文本parse成JSONObject,通俗就是把json文本转为json对象
            JSONObject json= JSONObject.parseObject(address);

            //通过其get的方法来获取data的value由于返回的是object对象，而data的value本身又是json字符串，所以我们可以进行强转
            JSONObject object = (JSONObject)json.get("data");
            String country=object.getString("country");
            String region = object.getString("region");
            String city = object.getString("city");
            String isp = object.getString("isp");
            address = country + region + city + isp;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return address;
    }

    public static void main(String[] args) {
        System.out.println(getAddresses("ip=49.77.58.221","utf-8"));
        System.out.println(getRealAddressByIP("111.85.32.37"));
    }
}
