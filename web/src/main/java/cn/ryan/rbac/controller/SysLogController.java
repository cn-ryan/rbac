package cn.ryan.rbac.controller;

import cn.ryan.rbac.anotation.SecurityMapping;
import cn.ryan.rbac.entity.SysLog;
import cn.ryan.rbac.exeception.BusinessException;
import cn.ryan.rbac.service.ISysLogService;
import cn.ryan.rbac.service.serviceImpl.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统日志控制器类
 *
 * @author ryan
 * @create 2019-04-12 17:35
 **/
@Controller
@RequestMapping(value = "/syslog")
public class SysLogController {
    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping(value = "/test")
    public void test(){
        SysLog byId = sysLogService.getById(1);
    }

    @RequestMapping(value = "/testTran")
    @SecurityMapping(value = "日志控制",mname = "日志测试")
    public void testTransaction(){
        SysLog sysLog = new SysLog();
        try {
            sysLogService.a(sysLog);
            throw new BusinessException("异常");
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }
}
