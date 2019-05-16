package cn.ryan.rbac.service.serviceImpl;

import cn.ryan.rbac.dao.BaseMapper;
import cn.ryan.rbac.entity.IdEntity;
import cn.ryan.rbac.entity.SysUser;
import cn.ryan.rbac.service.IBaseService;
import cn.ryan.rbac.util.CastUtil;
import cn.ryan.rbac.util.DateUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统service基类
 * @author ryan
 * @create 2019-04-12 17:01
 **/
@Service
@Slf4j
public class BaseService<T extends IdEntity> implements IBaseService<T> {
    protected DateUtils instance = DateUtils.getInstance();
    @Autowired
    protected BaseMapper<T> baseMapper;

    public boolean save(T t) {
        try {
            t.setCreateDate(instance.getCurrentDate());
            t.setCreateTime(instance.getCurrentTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        t.setCreateUser(user.getUsername());
        return baseMapper.save(t);
    }

    public boolean update(T t) {
        try {
            t.setLastDate(instance.getCurrentDate());
            t.setLastTime(instance.getCurrentTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        t.setLastUser(user.getUsername());
        return baseMapper.update(t);
    }

    public boolean delete(T t) {
        return baseMapper.delete(t);
    }

    public T getById(Integer id){
        return baseMapper.getById(id);
    }

    public List<T> getAll(Map<String,Object> map) {
        if(map.get("pageNo") != null && map.get("pageSize") != null){
            int pageNo = CastUtil.castInt(map.get("pageNo"));
            int pageSize = CastUtil.castInt(map.get("pageSize"));
            PageHelper.startPage(pageNo,pageSize);
        }
        return baseMapper.getAll(map);
    }

    @Override
    public boolean deleteByIds(Integer[] ids) {
        List<Integer> idList = new ArrayList<Integer>();
        for (int i = 0; i < ids.length; i++) {
            idList.add(ids[i]);
        }
        return baseMapper.deleteByIds(idList);
    }
}
