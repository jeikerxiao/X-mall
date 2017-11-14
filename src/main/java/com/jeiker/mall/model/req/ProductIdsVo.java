package com.jeiker.mall.model.req;

/**
 * @author : xiao
 * @date : 17/11/14 下午4:32
 * @description :
 */
public class ProductIdsVo {

    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "ProductIdsVo{" +
                "ids='" + ids + '\'' +
                '}';
    }
}
