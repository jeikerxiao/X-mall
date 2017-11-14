package com.jeiker.mall.model.req;

import java.io.Serializable;

/**
 * @author : xiao
 * @date : 17/11/14 下午4:51
 * @description :
 */
public class UserNameVo implements Serializable{

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserNameVo{" +
                "username='" + username + '\'' +
                '}';
    }
}
