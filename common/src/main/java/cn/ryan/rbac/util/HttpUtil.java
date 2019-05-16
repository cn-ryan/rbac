package cn.ryan.rbac.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http工具类
 *
 * @author ryan
 * @create 2019-05-09 11:56
 **/
@Slf4j
public class HttpUtil {

    public static String getResult(String urlStr, String content,String encoding){
        long startTime = System.currentTimeMillis();
        log.info("----------------开始调用地址------------------:" + urlStr + "开始时间:"+ startTime);
        URL url = null;
        HttpURLConnection connection = null;
        DataOutputStream dos = null;
        BufferedReader br = null;
        try{
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();
            dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(content);
            dos.flush();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
            StringBuffer buffer = new StringBuffer();
            String result = "";
            while((result = br.readLine()) != null){
                buffer.append(result);
            }
            return buffer.toString();
        }catch (Exception e){
            log.error("调用接口异常，异常信息为:" + e.getMessage());
        }finally {
            connection.disconnect();
            try {
                dos.close();
                br.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            log.info("调用接口结束，耗时"+ (System.currentTimeMillis() - startTime) + "ms");
        }
        return null;
    }
}
