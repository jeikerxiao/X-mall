package com.jeiker.mall.common;

import com.jeiker.mall.model.User;
import com.jeiker.mall.util.XmallContext;

/**
 * @author : xiao
 * @date : 17/11/14 下午2:45
 * @description :
 */
public abstract class BaseController {

    /**
     * 获取当前登录用户用户ID
     * @return
     */
    public Integer getUserId() {
        return XmallContext.getContext().getLoginUser().getId();
    }

    /**
     * 获取当前登录用户对象
     * @return
     */
    public User getUser() {
        return XmallContext.getContext().getLoginUser();
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void setUser(User user) {
        XmallContext.getContext().setLoginUser(user);
    }

    /**
     * 退出，清除上下文
     */
    public void logoutUser() {
        XmallContext.clear();
    }
}
