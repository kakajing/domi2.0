package com.domi.sso.service;

import com.domi.sso.mapper.UserMapper;
import com.domi.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author 卡卡
 * Created by jing on 2016/12/29.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

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
}
