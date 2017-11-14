package com.jeiker.mall.model.req;

import java.io.Serializable;

/**
 * @author : xiao
 * @date : 17/11/14 下午4:45
 * @description :
 */
public class CheckValidVo implements Serializable{

    private String str;
    private String type;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CheckValidVo{" +
                "str='" + str + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
