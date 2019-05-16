package cn.ryan.rbac.execeptionHandler;

import cn.ryan.rbac.entity.AjaxResult;
import cn.ryan.rbac.entity.SysLog;
import cn.ryan.rbac.entity.SysUser;
import cn.ryan.rbac.exeception.BusinessException;
import cn.ryan.rbac.service.ISysLogService;
import cn.ryan.rbac.shiro.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统自定义异常处理器
 * @author ryan
 * @create 2019-04-23 15:45
 **/
@ControllerAdvice
public class MyBusinessExeceptionHandler {
    @Autowired
    private ISysLogService sysLogService;
    
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public AjaxResult handleBusinessException(HttpServletRequest request,BusinessException ex) {
        AjaxResult ajaxResult = null;
        String url = request.getRequestURI();
        String msg = ex.getMessage();
        SysLog sysLog = createSysLog(request, msg);
        this.sysLogService.save(sysLog);
        ajaxResult = new AjaxResult(Boolean.FALSE , msg , url);
        ex.printStackTrace();
        return ajaxResult;
    }

    /*
    *@Description 构建syslog对象
    *@Param [request, username, msg]
    *@Return cn.ryan.rbac.entity.SysLog
    *@Author ryan
    *@Date 2019/5/8
    *@Time 17:10
    */
    private SysLog createSysLog(HttpServletRequest request,String msg){
        SysLog sysLog = new SysLog();
        sysLog.setContent("执行"
                + request.getRequestURI() == null ? "" : request
                .getRequestURI()
                + "时出现异常，异常代码为:"
                + msg);
        sysLog.setOp_url(request.getRequestURI());
        sysLog.setIp(request.getRemoteAddr());
        sysLog.setUser_name(ShiroUtils.getUserName());
        return sysLog;
    }
}
