package org.chenyu.service;

import org.chenyu.dao.UserDao;
import org.chenyu.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by chenyu on 17-3-10.
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public boolean login(String username, String password) {
        User user = userDao.getUser(username, password);
        if (user == null) {
            return false;
        }else{
            return true;
        }
    }
}
