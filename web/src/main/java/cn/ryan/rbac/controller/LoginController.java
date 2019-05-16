package cn.ryan.rbac.controller;

import cn.ryan.rbac.entity.AjaxResult;
import cn.ryan.rbac.entity.SysLog;
import cn.ryan.rbac.entity.SysPremission;
import cn.ryan.rbac.entity.SysUser;
import cn.ryan.rbac.service.ISysLogService;
import cn.ryan.rbac.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统登录控制器
 * @author ryan
 * @create 2019-04-16 9:18
 **/
@Controller
public class LoginController {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/doLogin")
    public Object doLogin(SysUser user, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(Boolean.FALSE);
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
            token.setRememberMe(Boolean.TRUE);
            try {
                currentUser.login(token);
                ajaxResult.setSuccess(Boolean.TRUE);
                SysLog sysLog = new SysLog();
                sysLog.setUser_name(user.getUsername());
                sysLog.setOp_url(request.getRequestURI());
                sysLog.setIp(request.getRemoteAddr());
                sysLog.setContent(user.getUsername()+"登录系统");
                this.sysLogService.save(sysLog);
            }catch (AuthenticationException e){
                e.printStackTrace();
            }
        }else{
            ajaxResult.setSuccess(Boolean.TRUE);
        }
        return ajaxResult;
    }

    @RequestMapping(value = "/main")
    public String main(Model model){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPremission> list = userService.getPremissions(sysUser.getId());
        SysPremission rootPermission = null;
        Map<Integer,SysPremission> map = new HashMap<>();
        for(SysPremission sysPremission : list){
            map.put(sysPremission.getId(),sysPremission);
        }
        for(SysPremission sysPremission : list){
            if(sysPremission.getPId() == null){
                rootPermission = sysPremission;
            }else{
                SysPremission parent = map.get(sysPremission.getPId());
                parent.getChildren().add(sysPremission);
            }
        }
        model.addAttribute("rootPermission",rootPermission);
        return "main";
    }
}
