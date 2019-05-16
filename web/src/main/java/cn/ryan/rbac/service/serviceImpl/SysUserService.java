package cn.ryan.rbac.service.serviceImpl;

import cn.ryan.rbac.dao.SysUserMapper;
import cn.ryan.rbac.entity.SysPremission;
import cn.ryan.rbac.entity.SysRole;
import cn.ryan.rbac.entity.SysUser;
import cn.ryan.rbac.exeception.BusinessException;
import cn.ryan.rbac.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * 系统用户service实现
 * @author ryan
 * @create 2019-04-16 9:28
 **/

@Service
@Transactional
public class SysUserService extends BaseService<SysUser> implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void a(SysUser user) {
        baseMapper.save(user);
    }

    @Override
    public SysUser getUserByName(SysUser user) {
        return sysUserMapper.getUserByName(user);
    }

    @Override
    public List<SysRole> getRoles(Integer id) {
        return sysUserMapper.getRoles(id);
    }

    @Override
    public boolean saveAssign( Integer[] role_ids, Integer user_id) {
        //清除所有的角色信息
        sysUserMapper.deleteRoles(user_id);
        //保存角色用户信息
        sysUserMapper.saveRoles(user_id,Arrays.asList(role_ids));
        return false;
    }

    @Override
    public List<SysPremission> getPremissions(Integer id) {
        return this.sysUserMapper.getPremissions(id);
    }

}
