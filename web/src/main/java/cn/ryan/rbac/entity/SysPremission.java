package cn.ryan.rbac.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统权限实体类
 *
 * @author ryan
 * @create 2019-04-26 15:21
 **/
@Data
public class SysPremission extends IdEntity<SysPremission> {
    private String name;
    private boolean open = true;
    private String icon;
    private boolean checked = false;
    private String url;
}
