package com.jeiker.mall.controller.backend;

import com.jeiker.mall.common.BaseController;
import com.jeiker.mall.common.Const;
import com.jeiker.mall.common.ResponseCode;
import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.User;
import com.jeiker.mall.model.req.LoginVo;
import com.jeiker.mall.model.vo.UserVo;
import com.jeiker.mall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by geely
 */

@Controller
@RequestMapping("/manage/user")
@Api("后台-用户管理")
public class UserManageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserManageController.class);

    @Autowired
    private IUserService iUserService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    @ResponseBody
    public ServerResponse<User> login(@RequestBody LoginVo loginVo) {
        ServerResponse<User> response = iUserService.login(loginVo.getUsername(), loginVo.getPassword());
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                //说明登录的是管理员
                setUser(user);
                return response;
            } else {
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }

    @ApiOperation("用户列表")
    @GetMapping("/list")
    @ResponseBody
    public ServerResponse<List<UserVo>> list() {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (user.getRole() != Const.Role.ROLE_ADMIN) {
            //说明登录的不是管理员
            return ServerResponse.createByErrorMessage("不是管理员,无法登录");
        }
        ServerResponse<List<UserVo>> response = iUserService.getUsers();
        return response;
    }

}
