package cn.ryan.rbac.entity;

import lombok.Data;

/**
 * 系统角色类
 *
 * @author ryan
 * @create 2019-04-25 15:30
 **/
@Data
public class SysRole extends IdEntity<SysRole> {
    private String rolename;
}
