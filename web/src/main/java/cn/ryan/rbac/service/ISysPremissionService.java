package cn.ryan.rbac.service;

import cn.ryan.rbac.entity.SysPremission;

import java.util.List;

public interface ISysPremissionService extends IBaseService<SysPremission> {

    List<SysPremission> getAll(Integer role_id);
}
