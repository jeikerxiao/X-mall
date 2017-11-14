package com.jeiker.mall.controller.frontend;

import com.jeiker.mall.common.BaseController;
import com.jeiker.mall.common.ResponseCode;
import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.User;
import com.jeiker.mall.model.req.LoginVo;
import com.jeiker.mall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by geely
 */
@Controller
@RequestMapping("/app/user/")
@Api("前台-用户管理")
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     */
    @ApiOperation("用户登录")
    @PostMapping("login")
    @ResponseBody
    public ServerResponse<User> login(@RequestBody LoginVo loginVo) {
        ServerResponse<User> response = iUserService.login(loginVo.getUsername(), loginVo.getPassword());
        return response;
    }

    /**
     * 用户登出
     *
     * @return
     */
    @ApiOperation("用户退出")
    @PostMapping("logout")
    @ResponseBody
    public ServerResponse<String> logout() {
        logoutUser();
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("register")
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 用户校验
     *
     * @param str
     * @param type
     * @return
     */
    @ApiOperation("用户校验")
    @PostMapping("check_valid")
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取用户信息
     */
    @ApiOperation("用户信息")
    @PostMapping("get_user_info")
    @ResponseBody
    public ServerResponse<User> getUserInfo() {
        User user = getUser();
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

    /**
     * 获取忘记密码问题
     *
     * @param username
     * @return
     */
    @ApiOperation("忘记密码问题")
    @PostMapping("forget_get_question")
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 忘记密码提交答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @ApiOperation("忘记密码问题答案")
    @PostMapping("forget_check_answer")
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 忘记密码，重置密码
     *
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @ApiOperation("忘记密码重置密码")
    @PostMapping("forget_reset_password")
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * 重置密码
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @ApiOperation("重置密码")
    @PostMapping("reset_password")
    @ResponseBody
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

    /**
     * 更新个人信息
     *
     * @param user
     * @return
     */
    @ApiOperation("修改个人信息")
    @PostMapping("update_information")
    @ResponseBody
    public ServerResponse<User> update_information(User user) {
        User currentUser = getUser();
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            response.getData().setUsername(currentUser.getUsername());
            setUser(response.getData());
        }
        return response;
    }

    /**
     * 获取个人信息
     *
     * @return
     */
    @ApiOperation("获取个人信息")
    @PostMapping("get_information")
    @ResponseBody
    public ServerResponse<User> get_information() {
        User currentUser = getUser();
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }

    /**
     * 获取个人信息
     */
    @ApiOperation("个人信息")
    @PostMapping("info")
    @ResponseBody
    public ServerResponse<User> getInfo() {
        User currentUser = getUser();
        if (currentUser == null) {
            logger.error("===> getInfo : 用户未登录.");
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }

}
