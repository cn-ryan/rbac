package cn.ryan.rbac.service.serviceImpl;

import cn.ryan.rbac.dao.SysRoleMapper;
import cn.ryan.rbac.entity.SysRole;
import cn.ryan.rbac.service.ISysRoleService;
import cn.ryan.rbac.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统角色service
 *
 * @author ryan
 * @create 2019-04-25 15:33
 **/
@Service
@Transactional
public class SysRoleService extends BaseService<SysRole> implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public void doAssign(Map<String, Object> map) {
        Integer role_id = CastUtil.castInt(map.get("role_id"));
        sysRoleMapper.deleteByRoleId(role_id);
        sysRoleMapper.saveAssign(map);
    }
}
