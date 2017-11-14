package com.jeiker.mall.model.req;

import java.io.Serializable;

/**
 * @author : xiao
 * @date : 17/11/14 下午4:11
 * @description :
 */
public class LongIdVo implements Serializable{
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LongIdVo{" +
                "id=" + id +
                '}';
    }
}
