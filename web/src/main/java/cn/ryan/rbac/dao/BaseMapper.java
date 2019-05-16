package cn.ryan.rbac.dao;

import cn.ryan.rbac.entity.IdEntity;
import cn.ryan.rbac.exeception.BusinessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
 public interface BaseMapper<T extends IdEntity> {
    boolean save(T t);

    boolean update(T t);

    boolean delete(T t);

    T getById(Integer id);

    List<T> getAll(Map<String,Object> map);

    Map<String,Object> getForMap(String id);

    boolean deleteByIds(List list);

}
