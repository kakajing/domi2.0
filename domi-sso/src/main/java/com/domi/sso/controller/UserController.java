package com.domi.sso.controller;

import com.domi.common.utils.CookieUtils;
import com.domi.sso.pojo.User;
import com.domi.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author 卡卡
 * Created by jing on 2016/12/29.
 */
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final String COOKIE_NAME = "DM_TOKEN";

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

    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(@RequestParam("username") String username,
                                       @RequestParam("password") String password,
                                       HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            String token = this.userService.doLogin(username, password);
            if (token == null){
                // 登录失败
                result.put("status", 400);
            }else {
                // 登录成功，需要将token写入到cookie中
                result.put("status", 200);
                CookieUtils.setCookie(request, response, COOKIE_NAME, token);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 登录失败
            result.put("status", 500);
        }
        return result;
    }

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    @RequestMapping(value = "{token}", method = RequestMethod.GET)
    public ResponseEntity<User> queryByToken(@PathVariable String token){

        try {
            User user =this.userService.queryByToken(token);
            if (user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
