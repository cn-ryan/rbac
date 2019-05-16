package cn.ryan.rbac.controller;

import cn.ryan.rbac.anotation.SecurityMapping;
import cn.ryan.rbac.entity.AjaxResult;
import cn.ryan.rbac.entity.Page;
import cn.ryan.rbac.entity.SysRole;
import cn.ryan.rbac.entity.SysUser;
import cn.ryan.rbac.exeception.BusinessException;
import cn.ryan.rbac.service.ISysRoleService;
import cn.ryan.rbac.service.ISysUserService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器类
 * @author ryan
 * @create 2019-04-18 16:52
 **/
@Slf4j
@Controller
@RequestMapping(value = "/user")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;

    @RequestMapping(value = "/index")
    public String index() {
        return "user/userIndex";
    }

    @RequestMapping(value = "/pageQuery")
    @ResponseBody
    public AjaxResult pageQuery(@RequestParam(defaultValue = "1") Integer pageNo
            , @RequestParam(defaultValue = "2") Integer pageSize
            ,String queryText){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("pageNo",pageNo);
            map.put("pageSize",pageSize);
            map.put("username",queryText);
            List<SysUser> users = sysUserService.getAll(map);
            PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(users);
            int totalNo = pageInfo.getPages();
            Page page = new Page.PageBuilder<SysUser>()
                                .pageList(users)
                                .totalNo(totalNo).build();
            ajaxResult.setPage(page);
            return ajaxResult.success();
        }catch (Exception e){
            e.printStackTrace();
            log.error("用户分页查询出错");
        }
        return ajaxResult;
    }

    @RequestMapping(value = "/add")
    public String add(){
        return "user/userAdd";
    }

    @RequestMapping(value = "/edit")
    public String edit(@RequestParam(required = true) Integer id, Model model){
        SysUser user = sysUserService.getById(id);
        model.addAttribute("user",user);
        return "user/userEdit";
    }

    @ModelAttribute
    public void getSysUser(SysUser user,Model model){
        if(user.getId() != null){
            SysUser db_user = null;
            try {
                db_user = sysUserService.getById(user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("user",db_user);
        }
    }

    @RequestMapping(value = "/save")
    @SecurityMapping(value = "/user/save",mname = "用户保存",title = "用户保存")
    @ResponseBody
    public AjaxResult save(HttpServletRequest request, @ModelAttribute(name = "user") SysUser user){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            if(null == user.getId()){
                sysUserService.save(user);
            }else{
                sysUserService.update(user);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException("保存用户失败");
        }
        return ajaxResult.success();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public AjaxResult delete(Integer[] ids){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            sysUserService.deleteByIds(ids);
        }catch (Exception e){
            log.error("删除用户失败" + e.getMessage());
            throw new BusinessException("删除用户失败");
        }
        return ajaxResult.success();
    }

    @RequestMapping(value = "/assign")
    public String assign(@RequestParam(required = true) Integer id,Model model){
        SysUser user = sysUserService.getById(id);
        List<SysRole> roles = sysRoleService.getAll(new HashMap<>());
        List<SysRole> hasRoles = sysUserService.getRoles(id);
        roles.removeAll(hasRoles);
        model.addAttribute("user",user);
        model.addAttribute("hasRoles",hasRoles);
        model.addAttribute("otherRoles",roles);
        return "user/userAssign";
    }

    @RequestMapping(value = "/saveAssign")
    @ResponseBody
    public AjaxResult saveAssign(Integer[] role_ids,Integer user_id){
        AjaxResult ajaxResult = new AjaxResult();
        //清除所有的角色信息
        try {
            sysUserService.saveAssign(role_ids,user_id);
        }catch (Exception e){
            log.error("用户授权角色保存失败" + e.getMessage());
            throw new BusinessException("用户授权角色保存失败");
        }
        return ajaxResult.success();
    }
}
