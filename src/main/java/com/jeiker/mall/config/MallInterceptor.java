package com.jeiker.mall.config;

import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.User;
import com.jeiker.mall.service.IUserService;
import com.jeiker.mall.util.XmallContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jeiker.mall.common.Const.XMALL_TOKEN;

/**
 * Created by Administrator on 2017/11/13 0013.
 * 自定义拦截器
 */
public class MallInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MallInterceptor.class);

    @Autowired
    private IUserService iUserService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("===> MallInterceptor : preHandle.");
        try {
            // 保存用户登录信息
            saveXmallContext(httpServletRequest);
            return true;
        } catch (Exception e) {
            logger.error("====> preHandle error ", e);
            // 清除 XmallContext 信息
            return clearAndReturn();
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("===> MallInterceptor : postHandle.");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("===> MallInterceptor : afterCompletion.");
        // 清除 XmallContext 信息
        clearAndReturn();
    }

    /**
     * 保存 XmallContext 信息
     *
     * @param request
     */
    private void saveXmallContext(HttpServletRequest request) {
        // 1.从 request 的 Header 头中读出 token
        String token = request.getHeader(XMALL_TOKEN);
        if (StringUtils.isBlank(token)) {
            logger.error("===> Request Header no token : xmall-Token");
            return;
        }
        logger.info("===> xmall-Token : {}", token);

        if (iUserService == null) { //解决service为null无法注入问题
            logger.error("===> iUserService is null!!!");
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            iUserService = (IUserService) factory.getBean("iUserService");
        }

        // 2.将 token 取出 user 登录对象
        ServerResponse<User> response = iUserService.getUser(token);
        if (response.isSuccess()) {
            User user = response.getData();
            // 3.将 user 对象写入 context
            XmallContext context = XmallContext.getContext();
            context.setLoginUser(user);
        }
    }

    /**
     * 清除 XmallContext 信息
     *
     * @return
     */
    private boolean clearAndReturn() {
        // 清除上下文信息，防止内存溢出。
        XmallContext.clear();
        return false;
    }
}
