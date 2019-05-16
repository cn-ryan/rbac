package cn.ryan.rbac.controller;

import cn.ryan.rbac.entity.AjaxResult;
import cn.ryan.rbac.entity.SysPremission;
import cn.ryan.rbac.service.ISysPremissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统权限控制器类
 *
 * @author ryan
 * @create 2019-04-26 15:26
 **/
@Slf4j
@Controller
@RequestMapping(value = "/premission")
public class SysPremissionController {
    @Autowired
    private ISysPremissionService sysPremissionService;

    @RequestMapping(value = "/index")
    public String index(){
        return "premission/premissionIndex";
    }

    @RequestMapping(value = "/loadData")
    @ResponseBody
    public List<SysPremission> loadData(Integer role_id){
        return sysPremissionService.getAll(role_id);
    }

    @RequestMapping(value = "/input")
    public String input(@RequestParam(required = false) Integer pId,
                      @RequestParam(required = false) Integer id,
                      Model model){
        if(id == null){
            model.addAttribute("pId",pId);
        }else{
            SysPremission sysPremission = sysPremissionService.getById(id);
            model.addAttribute("vo",sysPremission);
        }
        return "premission/premissionAdd";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public AjaxResult save(SysPremission sysPremission){
        AjaxResult ajaxResult = new AjaxResult();
        if(sysPremission.getId() == null){
            sysPremissionService.save(sysPremission);
        }else {
            sysPremissionService.update(sysPremission);
        }
        return ajaxResult.success();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public AjaxResult delete(@RequestParam Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        this.sysPremissionService.deleteByIds(new Integer[]{id});
        return ajaxResult.success();
    }
}
