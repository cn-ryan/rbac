package cn.ryan.rbac.controller;

import cn.ryan.rbac.entity.AjaxResult;
import cn.ryan.rbac.entity.Page;
import cn.ryan.rbac.entity.SysRole;
import cn.ryan.rbac.exeception.BusinessException;
import cn.ryan.rbac.service.ISysRoleService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色类控制器
 * @author ryan
 * @create 2019-04-25 15:40
 **/
@Slf4j
@Controller
@RequestMapping(value = "/role")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;

    @RequestMapping(value = "/index")
    public String index(){
        return "role/roleIndex";
    }

    @RequestMapping(value = "/pageQuery")
    @ResponseBody
    public AjaxResult pageQuery(@RequestParam(defaultValue = "1") Integer pageNo
            , @RequestParam(defaultValue = "2") Integer pageSize
            ,String queryText) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageNo", pageNo);
            map.put("pageSize", pageSize);
            map.put("rolename", queryText);
            List<SysRole> roles = sysRoleService.getAll(map);
            PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(roles);
            int totalNo = pageInfo.getPages();
            Page page = new Page.PageBuilder<SysRole>()
                    .pageList(roles)
                    .totalNo(totalNo).build();
            ajaxResult.setPage(page);
            return ajaxResult.success();
        } catch (Exception e) {
            log.error("角色分页查询出错" + e.getMessage());
            throw new BusinessException("角色分页查询出错");
        }
    }

    @RequestMapping(value = "add")
    public String add(){
        return "role/roleAdd";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AjaxResult save(SysRole sysRole){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            sysRoleService.save(sysRole);
        }catch (Exception e){
            log.error("保存角色失败" + e.getMessage());
            throw new BusinessException("保存角色失败");
        }
        return ajaxResult.success();
    }

    @RequestMapping(value = "/assign")
    public String assign(@RequestParam Integer id, Model model){
        model.addAttribute("role_id",id);
        return "/role/roleAssign";
    }

    @RequestMapping(value = "/doAssign")
    @ResponseBody
    public AjaxResult doAssign(Integer role_id, Integer[] premissionIds){
        AjaxResult ajaxResult = new AjaxResult();
        Map<String,Object> map = new HashMap<>();
        map.put("role_id",role_id);
        map.put("premissionIds",premissionIds);
        sysRoleService.doAssign(map);
        return ajaxResult.success();
    }
}
