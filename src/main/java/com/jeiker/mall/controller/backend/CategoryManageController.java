package com.jeiker.mall.controller.backend;

import com.jeiker.mall.common.BaseController;
import com.jeiker.mall.common.ResponseCode;
import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.User;
import com.jeiker.mall.model.req.CategoryReqVo;
import com.jeiker.mall.model.req.IdVo;
import com.jeiker.mall.service.ICategoryService;
import com.jeiker.mall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by geely
 */
@Controller
@RequestMapping("/manage/category")
@Api("后台-类别管理")
public class CategoryManageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryManageController.class);

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    @ApiOperation("增加类别节点")
    @PostMapping("add_category")
    @ResponseBody
    public ServerResponse addCategory(@RequestBody CategoryReqVo categoryReqVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        //校验一下是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //增加我们处理分类的逻辑
            return iCategoryService.addCategory(categoryReqVo.getName(), categoryReqVo.getId());

        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @ApiOperation("修改类别名称")
    @PostMapping("set_category_name")
    @ResponseBody
    public ServerResponse setCategoryName(@RequestBody CategoryReqVo categoryReqVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //更新categoryName
            return iCategoryService.updateCategoryName(categoryReqVo.getId(), categoryReqVo.getName());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @ApiOperation("获取类别子节点")
    @PostMapping("get_category")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(@RequestBody IdVo categoryId) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //查询子节点的category信息,并且不递归,保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId.getId());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @ApiOperation("获取当前分类id并获取子节点")
    @PostMapping("get_deep_category")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(@RequestBody IdVo categoryId) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //查询当前节点的id和递归子节点的id
//            0->10000->100000
            return iCategoryService.selectCategoryAndChildrenById(categoryId.getId());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
}
