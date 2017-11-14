package com.jeiker.mall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.jeiker.mall.common.BaseController;
import com.jeiker.mall.common.ResponseCode;
import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.User;
import com.jeiker.mall.model.req.LongIdVo;
import com.jeiker.mall.model.req.PageVo;
import com.jeiker.mall.model.req.SearchVo;
import com.jeiker.mall.model.vo.OrderVo;
import com.jeiker.mall.service.IOrderService;
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
@RequestMapping("/manage/order")
@Api("后台-订单管理")
public class OrderManageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderManageController.class);

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @ApiOperation("订单列表")
    @PostMapping("list")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(@RequestBody PageVo pageVo) {

        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充我们增加产品的业务逻辑
            return iOrderService.manageList(pageVo.getPageNum(), pageVo.getPageSize());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation("获取订单详情")
    @PostMapping("detail")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(@RequestBody LongIdVo orderNo) {

        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充我们增加产品的业务逻辑

            return iOrderService.manageDetail(orderNo.getId());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation("搜索订单")
    @PostMapping("search")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(@RequestBody SearchVo searchVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充我们增加产品的业务逻辑
            return iOrderService.manageSearch(searchVo.getId(), searchVo.getPageNum(), searchVo.getPageSize());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation("订单发货")
    @PostMapping("send_goods")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(@RequestBody LongIdVo orderNo) {

        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充我们增加产品的业务逻辑
            return iOrderService.manageSendGoods(orderNo.getId());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


}
