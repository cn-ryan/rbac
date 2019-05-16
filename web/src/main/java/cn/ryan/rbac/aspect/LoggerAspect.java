package cn.ryan.rbac.aspect;

import cn.ryan.rbac.anotation.SecurityMapping;
import cn.ryan.rbac.constant.Global;
import cn.ryan.rbac.entity.SysLog;
import cn.ryan.rbac.entity.SysUser;
import cn.ryan.rbac.exeception.BusinessException;
import cn.ryan.rbac.service.ISysLogService;
import cn.ryan.rbac.shiro.utils.ShiroUtils;
import cn.ryan.rbac.util.CastUtil;
import cn.ryan.rbac.util.CommUtil;
import cn.ryan.rbac.util.DateUtils;
import cn.ryan.rbac.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class LoggerAspect {
    @Autowired
    private ISysLogService sysLogService;

    @Pointcut("execution(* cn.ryan.rbac.controller.*.*(..))")
    public void declareJointPointExpression(){}

    @Before(value = "declareJointPointExpression()")
    public void before(){
        log.error("before");
    }

    @AfterThrowing(value = "declareJointPointExpression()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Exception e){
        if(e instanceof BusinessException){
            System.out.println("11112");
        }
    }

    // 记录管理员操作日志
    @After(value = "declareJointPointExpression() && @annotation(annotation) && args(request,..)")
    public void admin_op_log(JoinPoint joinPoint, SecurityMapping annotation,
                             HttpServletRequest request) {
        if(Global.SAVE_LOG){
            saveLog(joinPoint, annotation, request);
        }
    }

    private void saveLog(JoinPoint joinPoint, SecurityMapping annotation, HttpServletRequest request) {
        //这里只记录操作日志，不记录列表访问日志
        if(annotation.value().indexOf(Global.SAVE) >= 0
                || annotation.value().indexOf(Global.EDIT) >= 0
                || annotation.value().indexOf(Global.UPDATE) >= 0
                || annotation.value().indexOf(Global.DEL) >= 0){
            String username = ShiroUtils.getUserName();
            String content = username + "于"
                                      + DateUtils.getInstance().dateTimeFormat(new Date())
                                      + Global.OP_BEG
                                      + annotation.title()
                                      + Global.OP_END;
            Integer source_bill = null;
            if(request.getParameter("id") != null){
                source_bill = CastUtil.castInt(request.getParameter("id"));
                content += "。操作数据id为："+source_bill;
            }
            String ipAddr = CommUtil.getIpAddr(request);
            String ip_content = "ip="+ ipAddr;
            String ip_info = IpUtil.getAddresses(ip_content);
            String op_url = annotation.value();
            SysLog log = new SysLog();
            log.setUser_name(username);
            log.setContent(content);
            log.setIp(ipAddr);
            log.setIp_info(ip_info);
            log.setOp_url(op_url);
            log.setSource_bill(source_bill);
            this.sysLogService.save(log);
        }
    }

}
