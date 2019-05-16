package cn.ryan.rbac.service;

import cn.ryan.rbac.entity.IdEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IBaseService<T extends IdEntity>{
    boolean save(T t) ;

    boolean update(T t) ;

    boolean delete(T t);

    T getById(Integer id);

    List<T> getAll(Map<String,Object> map);

    boolean deleteByIds(Integer[] ids);

}
