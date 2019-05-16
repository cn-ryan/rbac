package cn.ryan.rbac.dao;

import cn.ryan.rbac.entity.SysPremission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPremissionMapper extends BaseMapper<SysPremission> {

    List<SysPremission> getAll();

    List<SysPremission> getByPID(Integer pId);

    Integer[] getPremissionsByRoleId(Integer role_id);
}
