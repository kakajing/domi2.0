package com.domi.sso.service;

import com.domi.common.component.JedisClient;
import com.domi.common.utils.JsonUtils;
import com.domi.sso.mapper.UserMapper;
import com.domi.sso.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Author 卡卡
 * Created by jing on 2016/12/29.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Boolean check(String param, Integer type){
        User user = new User();
        switch (type){
            case 1 :
                user.setUsername(param);
                break;
            case 2 :
                user.setPhone(param);
                break;
            case 3 :
                user.setEmail(param);
                break;
            default:
                return null;
        }
        return this.userMapper.selectOne(user) == null;
    }

    public Boolean saveUser(User user) {
        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());

        //密码加密处理，使用md5
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));

        return this.userMapper.insert(user) == 1;
    }

    public String doLogin(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        User selectOne = this.userMapper.selectOne(user);
        if (selectOne == null){
            return null;
        }
        // 对比密码是否正确
        if (!StringUtils.equals(DigestUtils.md5Hex(password), selectOne.getPassword())){
            return null;
        }

        // 登录成功
        //生成token
        String token = DigestUtils.md5Hex(System.currentTimeMillis() + username);


        jedisClient.set("REDIS_SESSION_TOKEN:" + token, JsonUtils.objectToJson(selectOne));
        return token;
    }

    public User queryByToken(String token){

        String key = "TOKEN_" + token;
        String jsonData = this.jedisClient.get(key);
        if (StringUtils.isEmpty(jsonData)){
            return null;
        }
        try {
            // 刷新用户的生存时间(非常重要)
            this.jedisClient.expire(key, 60 * 30);
            return MAPPER.readValue(jsonData, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
