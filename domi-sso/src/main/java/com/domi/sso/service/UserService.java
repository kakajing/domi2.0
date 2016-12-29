package com.domi.sso.service;

import com.domi.sso.mapper.UserMapper;
import com.domi.sso.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
