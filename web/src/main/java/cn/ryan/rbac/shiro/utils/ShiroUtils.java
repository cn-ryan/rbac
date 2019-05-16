package cn.ryan.rbac.shiro.utils;

import cn.ryan.rbac.entity.SysUser;
import org.apache.shiro.SecurityUtils;

/**
 * shiro工具类
 * @author ryan
 * @create 2019-05-08 17:41
 **/
public class ShiroUtils {

    /*
    *@Description 获取shiro principle对象
    *@Param []
    *@Return cn.ryan.rbac.entity.SysUser
    *@Author ryan
    *@Date 2019/5/8
    *@Time 17:43
    */
    public static SysUser getCurentUser(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser  = (SysUser)principal;
        return sysUser;
    }
    /*
    *@Description 获取登录用户名
    *@Param []
    *@Return java.lang.String
    *@Author ryan
    *@Date 2019/5/8
    *@Time 17:43
    */
    public static String getUserName(){
        SysUser curentUser = getCurentUser();
        return curentUser.getUsername();
    }
}
