package cn.ryan.rbac.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 公共工具类
 *
 * @author ryan
 * @create 2019-05-09 10:28
 **/
@Slf4j
public class CommUtil {

    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1"))
        {
            InetAddress addr = null;
            try
            {
                addr = InetAddress.getLocalHost();
            }
            catch (UnknownHostException e)
            {
                log.error(e.getMessage());
            }
            ip = CastUtil.castString(addr.getHostAddress());
        }
        return ip;
    }

    public static boolean isIp(String IP)
    {
        boolean b = false;
        IP = trimSpaces(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"))
        {
            String[] s = IP.split("\\.");
            if ((Integer.parseInt(s[0]) < 255) &&
                    (Integer.parseInt(s[1]) < 255) &&
                    (Integer.parseInt(s[2]) < 255) &&
                    (Integer.parseInt(s[3]) < 255)) {
                b = true;
            }
        }
        return b;
    }

    public static String trimSpaces(String IP)
    {
        while (IP.startsWith(" ")) {
            IP = IP.substring(1, IP.length()).trim();
        }
        while (IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }
        return IP;
    }
}
