package org.chenyu.dao;

import org.apache.ibatis.annotations.Param;
import org.chenyu.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by chenyu on 17-3-10.
 */
@Repository
public interface UserDao {
    public User getUser(@Param("username") String username, @Param("password") String password);
}
