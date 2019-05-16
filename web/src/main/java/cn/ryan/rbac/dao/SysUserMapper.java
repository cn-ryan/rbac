package cn.ryan.rbac.dao;

import cn.ryan.rbac.entity.SysPremission;
import cn.ryan.rbac.entity.SysRole;
import cn.ryan.rbac.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserByName(SysUser user);

    List<SysRole> getRoles(Integer id);

    boolean deleteRoles(@Param("id") Integer user_id);

    boolean saveRoles(@Param("user_id")Integer user_id, @Param("list") List<Integer> role_ids);

    List<SysPremission> getPremissions(@Param("id") Integer id);
}
