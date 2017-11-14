package com.jeiker.mall.util;

import com.jeiker.mall.model.User;

/**
 * @author : xiao
 * @date : 17/11/14 下午2:14
 * @description : 系统环境上下文：保存用户登录信息，拦截器中写入，BaseController中读取。
 */
public class XmallContext {

    private User loginUser;

    private static final ThreadLocal<XmallContext> contexts = new ThreadLocal<XmallContext>();

    /**
     * 获取登录上下文对象
     * @return
     */
    public static XmallContext getContext() {
        XmallContext context = contexts.get();
        if (context == null) {
            context = new XmallContext();
            contexts.set(context);
        }
        return context;
    }

    /**
     * 清除上下文对象
     */
    public static void clear() {
        contexts.remove();
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }
}
