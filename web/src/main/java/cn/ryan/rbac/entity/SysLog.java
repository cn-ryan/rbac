package cn.ryan.rbac.entity;

import cn.ryan.rbac.exeception.BusinessException;
import lombok.Data;

/**
 * 系统日志记录类
 * @author ryan
 * @create 2019-04-12 15:23
 **/
@Data
public class SysLog extends IdEntity<SysLog>{
    private String op_url;
    private String user_name;
    private String ip;
    private String ip_info;
    private String content;
    private Integer source_bill;
}
