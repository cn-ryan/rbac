package cn.ryan.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/json")
    public Object json(){
        Map map = new HashMap<String,String>();
        map.put("username","ryan");
        return map;
    }
}
