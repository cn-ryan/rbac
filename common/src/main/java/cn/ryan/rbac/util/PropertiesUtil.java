package cn.ryan.rbac.util;

import cn.ryan.rbac.exeception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.*;

/**
 * 配置文件公共读取类
 * @author ryan
 * @create 2019-04-17 11:05
 **/
@Slf4j
public class PropertiesUtil {
    private static volatile PropertiesUtil propertiesUtil;
    private static final String FILE_NAME = "common.properties";
    private static final Map<String,Object> map = new HashMap<String,Object>();
    private PropertiesUtil(){

    }

    public static PropertiesUtil getInstance(){
        if(null == propertiesUtil){
            synchronized (PropertiesUtil.class){
                if(null == propertiesUtil){
                    propertiesUtil = new PropertiesUtil();
                }
            }
        }
        return propertiesUtil;
    }

    private void loadProperties(){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_NAME);
        Properties properties = new Properties();
        try {
            properties.load(is);
            Set<Object> keys = properties.keySet();
            Iterator<Object> iterator = keys.iterator();
            while (iterator.hasNext()){
                Object key = iterator.next();
                map.put(CastUtil.castString(key),properties.getProperty(CastUtil.castString(key)));
            }
        }catch (Exception e){
            log.error("加载配置文件"+FILE_NAME+"异常");
            throw new BusinessException(e);
        }
    }
    /**
    *@Description 获取String类型
    *@Param
    *@Return 
    *@Author ryan
    *@Date 2019/4/17
    *@Time 11:30
    */
    public String getStringValue(String key){
        if(map.isEmpty()){
            loadProperties();
        }
        if(null != map.get(key)){
            return CastUtil.castString(map.get(key));
        }
        return null;
    }
}
