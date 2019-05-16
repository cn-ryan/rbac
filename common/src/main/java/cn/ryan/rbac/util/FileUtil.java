package cn.ryan.rbac.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件流公共工具类
 *
 * @author ryan
 * @create 2019-04-17 11:04
 **/
@Slf4j
public class FileUtil {
    private static volatile FileUtil instance;

    private FileUtil() {

    }

    /**
     * @Description 获取FileUtil单例对象
     * @Param
     * @Return
     * @Author ryan
     * @Date 2019/4/17
     * @Time 11:33
     */
    public static FileUtil getInstance() {
        if (null == instance) {
            synchronized (FileUtil.class) {
                if (null == instance) {
                    instance = new FileUtil();
                }
            }
        }
        return instance;
    }

    /**
     * @Description 把一个（文件）文件夹下的所有文件复制到指定的文件夹（文件）
     * @Param
     * @Return
     * @Author ryan
     * @Date 2019/4/17
     * @Time 14:22
     */
    public void copyFile(String oldpath, String newPath) {
        List<File> list = new ArrayList<File>();
        File oldfile = new File(oldpath);
        getFiles(oldfile, list);
        File newFile = new File(newPath);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        copy(newFile, list);
    }

    /**
     * @Description 递归获取文件夹下所有的文件
     * @Param [file, list]
     * @Return java.util.List<java.io.File>
     * @Author ryan
     * @Date 2019/4/17
     * @Time 14:41
     */
    private List<File> getFiles(File file, List<File> list) {
        if (null != file) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    getFiles(f, list);
                }
            } else {
                list.add(file);
            }
        }
        return list;
    }

    /**
     * @Description
     * @Param [file, list]
     * @Return void
     * @Author ryan
     * @Date 2019/4/17
     * @Time 14:31
     */
    private void copy(File file, List<File> list) {
        log.error("开始复制");
        for (File f : list) {
            new Thread(
                    () -> {
                        BufferedReader br = null;
                        BufferedWriter bw = null;
                        try {
                            br = new BufferedReader(new FileReader(f));
                            bw = new BufferedWriter(new FileWriter(new File(file, f.getName())));
                            String line = "";
                            while (null != (line = br.readLine())) {
                                bw.write(line);
                            }
                        } catch (Exception e) {
                            log.error("复制失败");
                            throw new RuntimeException(e);
                        } finally {
                            try {
                                if(null != br){
                                    br.close();
                                }
                                if(null != bw){
                                    bw.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
        }
        log.error("复制结束");
    }
}
