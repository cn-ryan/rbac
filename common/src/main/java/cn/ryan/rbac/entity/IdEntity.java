package cn.ryan.rbac.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 系统实体基类
 *
 * @author ryan
 * @create 2019-04-12 15:32
 **/
@Data
public class IdEntity<E> implements Serializable {
    private static final long serialVersionUID = -2124493276357363367L;
    private Integer id;
    private String createUser;
    private String lastUser;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Date lastDate;
    private Date lastTime;
    private Integer pId;
    private List<E> children = new ArrayList<E>();

    /**
     *@Description
     *@Param [obj]
     *@Return boolean
     *@Author ryan
     *@Date 2019/4/12
     *@Time 16:41
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof IdEntity)) {
            return false;
        } else {
            IdEntity other = (IdEntity)obj;
            return this.getId().equals(other.getId());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
