package cn.ryan.rbac.service.serviceImpl;

import cn.ryan.rbac.dao.SysPremissionMapper;
import cn.ryan.rbac.entity.SysPremission;
import cn.ryan.rbac.service.ISysPremissionService;
import cn.ryan.rbac.util.TreeParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统权限控制service类
 *
 * @author ryan
 * @create 2019-04-26 16:10
 **/
@Service
@Transactional
public class SysPremissionService extends BaseService<SysPremission> implements ISysPremissionService {

    @Autowired
    private SysPremissionMapper sysPremissionMapper;

    @Override
    public List<SysPremission> getAll(Integer role_id) {
        List<SysPremission> db_list = sysPremissionMapper.getAll();
        List<SysPremission> list = new ArrayList<SysPremission>();
        Map<Integer,SysPremission> map = new HashMap<>();
        Integer[] premissionIds = null;
        if(role_id != null){
            premissionIds = sysPremissionMapper.getPremissionsByRoleId(role_id);
        }
        for(SysPremission premission : db_list){
            map.put(premission.getId(),premission);
        }
        for (SysPremission child : db_list){
            if(premissionIds != null && Arrays.asList(premissionIds).contains(child.getId())){
                child.setChecked(Boolean.TRUE);
            }
            if(child.getPId() == null){
                list.add(child);
            }else {
                SysPremission parent = map.get(child.getPId());
                parent.getChildren().add(child);
            }
        }
        return list;
        //return TreeParserUtil.getTreeList(db_list);
    }
}
