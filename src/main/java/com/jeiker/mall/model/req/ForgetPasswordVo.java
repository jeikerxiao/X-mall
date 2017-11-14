package com.jeiker.mall.model.req;

import java.io.Serializable;

/**
 * @author : xiao
 * @date : 17/11/14 下午4:41
 * @description :
 */
public class ForgetPasswordVo implements Serializable{

    private String username;
    private String passwordNew;
    private String forgetToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    public String getForgetToken() {
        return forgetToken;
    }

    public void setForgetToken(String forgetToken) {
        this.forgetToken = forgetToken;
    }

    @Override
    public String toString() {
        return "ForgetPasswordVo{" +
                "username='" + username + '\'' +
                ", passwordNew='" + passwordNew + '\'' +
                ", forgetToken='" + forgetToken + '\'' +
                '}';
    }
}
