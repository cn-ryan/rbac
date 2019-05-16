package cn.ryan.rbac.service;

import cn.ryan.rbac.entity.SysRole;

import java.util.Map;

public interface ISysRoleService extends IBaseService<SysRole> {
    void doAssign(Map<String,Object> map);
}
