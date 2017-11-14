package com.jeiker.mall.model.req;

import java.io.Serializable;

/**
 * @author : xiao
 * @date : 17/11/14 下午4:20
 * @description :
 */
public class ProductSearchVo extends PageVo implements Serializable{

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductSearchVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
