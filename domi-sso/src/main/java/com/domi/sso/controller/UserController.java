package com.domi.sso.controller;

import com.domi.sso.pojo.User;
import com.domi.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;

/**
 * Author 卡卡
 * Created by jing on 2016/12/29.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册页面
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String toRedister(){
        return "register";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String toLogin(){
        return "login";
    }

    /**
     * 数据校验
     * @param param
     * @param type
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable("param") String param,
                                         @PathVariable("type") Integer type){

        try {
            Boolean bool = this.userService.check(param, type);
            if (bool == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            //为了兼容前端的业务逻辑，做出妥协处理
            return ResponseEntity.ok(!bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(@Valid User user, BindingResult bindingResult){
        Map<String,Object> result = new HashMap<String, Object>();
       if (bindingResult.hasErrors()){
           // 校验有错误
           List<String> msgs = new ArrayList<String>();
           List<ObjectError> allErrors = bindingResult.getAllErrors();
           for (ObjectError allError : allErrors) {
               String msg = allError.getDefaultMessage();
               msgs.add(msg);
           }
           result.put("status", "400");
           result.put("data", StringUtils.join(msgs, '|'));
       }

        Boolean bool = this.userService.saveUser(user);
        if (bool){
            //注册成功
            result.put("status", "200");
        }else {
            result.put("status","300");
            result.put("data", "注册失败！");
        }
        return result;
    }
}
