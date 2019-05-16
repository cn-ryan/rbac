package cn.ryan.rbac.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 系统用户类
 * @author ryan
 * @create 2019-04-12 15:24
 **/
@Data
@Alias("user")
public class SysUser extends IdEntity<SysUser>{
    private String username;
    private String password;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String wechat;
    private String email;
}
