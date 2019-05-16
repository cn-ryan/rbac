package cn.ryan.rbac.shiro.realm;

import cn.ryan.rbac.entity.SysLog;
import cn.ryan.rbac.entity.SysUser;
import cn.ryan.rbac.service.ISysLogService;
import cn.ryan.rbac.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * shiro用户控制realm
 * @author ryan
 * @create 2019-04-17 15:24
 **/
@Slf4j
public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    private ISysUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        SimpleHash sha1 = new SimpleHash("SHA1", token.getPassword(), ByteSource.Util.bytes(username), 2);
        SysUser user = new SysUser();
        user.setUsername(username);
        SysUser currentUser = userService.getUserByName(user);
        if(null != currentUser){
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(currentUser,currentUser.getPassword(),ByteSource.Util.bytes(username),getName());
            //this.sysUserService.update(sysUser);
            return authcInfo;
        }else
        {
            log.warn("User [{}] password is wrong:{}");
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        SimpleHash sha1 = new SimpleHash("SHA1", "admin", ByteSource.Util.bytes("admin"), 2);
        System.out.println(sha1.toString());;
    }
}
