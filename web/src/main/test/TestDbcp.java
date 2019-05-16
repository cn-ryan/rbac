import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public class TestDbcp implements ApplicationContextAware
{
    private static ApplicationContext ctx ;
    //获取上下文
    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException
    {
// TODO Auto-generated method stub
        ctx = context;
    }
    /**
     * @param args
     */
    public static void main(String[] args)
    {
// TODO Auto-generated method stub
        if(ctx == null)
        {
            String path = System.getProperty("user.dir") + "\\web\\src\\main\\resources\\spring\\applicationContext-web.xml";
//获取上下文
            ctx = new FileSystemXmlApplicationContext(path);
//ClassPathXmlApplicationContext("classpath*:springmvc-servlet.xml");
        }
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("select 1 from dual");
        System.out.println(stringObjectMap);
    }
}