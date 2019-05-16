import cn.ryan.rbac.util.FileUtil;

/**
 * @author ryan
 * @create 2019-04-17 13:57
 **/
public class TestFileUtil {
    public static void main(String[] args) {
        FileUtil fileUtil = FileUtil.getInstance();
        fileUtil.copyFile("D:\\LOG","D:\\LOG1\\2.html");
    }
}
