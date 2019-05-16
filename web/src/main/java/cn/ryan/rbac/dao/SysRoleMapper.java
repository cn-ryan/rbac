package cn.ryan.rbac.dao;


import cn.ryan.rbac.entity.SysRole;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SysRoleMapper extends BaseMapper<SysRole>{
    void deleteByRoleId(Integer role_id);

    void saveAssign(Map<String,Object> map);
}
