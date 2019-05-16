package cn.ryan.rbac.service;

import cn.ryan.rbac.entity.SysPremission;
import cn.ryan.rbac.entity.SysRole;
import cn.ryan.rbac.entity.SysUser;

import java.util.List;

public interface ISysUserService extends IBaseService<SysUser> {

    void a(SysUser user);

    SysUser getUserByName(SysUser user);

    List<SysRole> getRoles(Integer id);

    boolean saveAssign(Integer[] role_ids, Integer user_id);

    List<SysPremission> getPremissions(Integer id);
}
