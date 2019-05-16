package cn.ryan.rbac.entity;

import lombok.Data;

/**
 * ajax返回类
 * @author ryan
 * @create 2019-04-16 15:17
 **/
@Data
public class AjaxResult {
    private boolean success;
    private Page page;
    private String mes;
    private String reqUrl;

    public AjaxResult() {
    }

    public AjaxResult(boolean success, String mes, String reqUrl) {
        this.success = success;
        this.mes = mes;
        this.reqUrl = reqUrl;
    }

    public AjaxResult success(){
        this.setSuccess(Boolean.TRUE);
        return this;
    }

    public AjaxResult failure(String mes){
        this.setSuccess(Boolean.FALSE);
        this.setMes(mes);
        return this;
    }
}
