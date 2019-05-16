package cn.ryan.rbac.util;

import cn.ryan.rbac.entity.IdEntity;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.portable.IDLEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统树形菜单转换工具类
 * @author ryan
 * @create 2019-04-26 16:37
 **/
@Slf4j
public class TreeParserUtil {

    public static <E extends IdEntity<E>> List<E> getTreeList(List<E> treeList){
        List<E> resultList = new ArrayList<>();
        for (E entity : treeList) {
            if(entity.getPId() == null){
                resultList.add(entity);
            }
        }
        for (E entity : resultList) {
            entity.setChildren(getSubTreeList(entity.getId(),treeList));
        }
        return resultList;
    }

    private static <E extends IdEntity> List<E> getSubTreeList(Integer id, List<E> treeList) {
        List<E> childList = new ArrayList<>();
        for (E entity : treeList) {
            if(entity.getPId() == id){
                childList.add(entity);
            }
        }
        //递归遍历子树是否还有下面的节点
        if(!childList.isEmpty()){
            for(E e : childList){
                e.setChildren(getSubTreeList(e.getId(),treeList));
            }
        }
        return childList;
    }
}
