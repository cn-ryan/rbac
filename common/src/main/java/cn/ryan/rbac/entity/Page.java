package cn.ryan.rbac.entity;

import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 系统分页实体类
 * @author ryan
 * @create 2019-04-19 17:14
 **/
@Getter
public class Page {
    private final List list;
    private final Integer pageNo;
    private final Integer totalNo;

    public static class PageBuilder<T extends IdEntity>{
        private List<T> pageList;
        private Integer pageNo;
        private Integer totalNo;
        public PageBuilder(){

        }
        public PageBuilder(List<T> pageList, Integer pageNo, Integer totalNo) {
            this.pageList = pageList;
            this.pageNo = pageNo;
            this.totalNo = totalNo;
        }
        public PageBuilder pageList(List<T> pageList){
            this.pageList = pageList;
            return this;
        }
        public PageBuilder pageNo(Integer pageNo){
            this.pageNo = pageNo;
            return this;
        }
        public PageBuilder totalNo(Integer totalNo){
            this.totalNo = totalNo;
            return this;
        }

        public Page build(){
            return new Page(this);
        }
    }

    private Page(PageBuilder pageBuilder){
        list = pageBuilder.pageList;
        pageNo = pageBuilder.pageNo;
        totalNo = pageBuilder.totalNo;
    }
}
