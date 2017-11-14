package com.jeiker.mall.model.req;

import java.io.Serializable;

/**
 * @author : xiao
 * @date : 17/11/14 下午4:39
 * @description :
 */
public class ResetPasswordVo implements Serializable {

    private String passwordOld;
    private String passwordNew;

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    @Override
    public String toString() {
        return "ResetPasswordVo{" +
                "passwordOld='" + passwordOld + '\'' +
                ", passwordNew='" + passwordNew + '\'' +
                '}';
    }
}
