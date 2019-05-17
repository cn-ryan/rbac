package cn.ryan.rbac.util;

import cn.ryan.rbac.exeception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 序列化工具类
 *
 * @author ryan
 * @create 2019-05-17 9:37
 **/
@Slf4j
public class SerializeUtil implements Serializable {
    private volatile SerializeUtil instance = null;

    public SerializeUtil getInstance(){
        if(instance == null){
            synchronized (SerializeUtil.class){
                if(instance == null){
                    instance = new SerializeUtil();
                }
            }
        }
        return instance;
    }
    /*
    *@Description 
    *@Param [object]
    *@Return void
    *@Author ryan
    *@Date 2019/5/17
    *@Time 9:39
    */
    private Object serialize(Object object){
        if(object == null){
            throw new BusinessException("传入的对象为空");
        }
        byte[] bytes = toBytes(object);
        if(bytes == null){
            throw new BusinessException("序列化失败");
        }
        Object obj = tObj(bytes);
        if(obj == null){
            throw new BusinessException("反序列化失败");
        }
        return obj;
    }
    /*
    *@Description 将对象输出到ByteArrayOutputStream流中
    *@Param [obj]
    *@Return byte[]
    *@Author ryan
    *@Date 2019/5/17
    *@Time 9:48
    */
    private byte[] toBytes(Object obj) {
        ByteArrayOutputStream bao = null;
        ObjectOutputStream oos = null;
        try{
            bao = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bao);
            oos.writeObject(obj);
            return bao.toByteArray();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            try {
                bao.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
    *@Description 将对象从流中读取出来
    *@Param [bytes]
    *@Return java.lang.Object
    *@Author ryan
    *@Date 2019/5/17
    *@Time 9:53
    */
    private Object tObj(byte[] bytes){
        ByteArrayInputStream bai = null;
        ObjectInputStream ois = null;
        try {
            bai = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bai);
            Object obj = ois.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bai.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
