package com.jeiker.mall.controller.frontend;

import com.github.pagehelper.PageInfo;
import com.jeiker.mall.common.BaseController;
import com.jeiker.mall.common.ResponseCode;
import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.Shipping;
import com.jeiker.mall.model.User;
import com.jeiker.mall.model.req.PageVo;
import com.jeiker.mall.service.IShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by geely
 */
@Controller
@RequestMapping("/app/shipping/")
@Api("前台-收货地址管理")
public class ShippingController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ShippingController.class);

    @Autowired
    private IShippingService iShippingService;

    @ApiOperation("新增收货地址")
    @PostMapping("add")
    @ResponseBody
    public ServerResponse add( Shipping shipping) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(getUserId(), shipping);
    }

    @ApiOperation("删除收货地址")
    @PostMapping("del")
    @ResponseBody
    public ServerResponse del( Integer shippingId) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(getUserId(), shippingId);
    }

    @ApiOperation("修改收货地址")
    @PostMapping("update")
    @ResponseBody
    public ServerResponse update( Shipping shipping) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(getUserId(), shipping);
    }

    @ApiOperation("选择")
    @PostMapping("select")
    @ResponseBody
    public ServerResponse<Shipping> select( Integer shippingId) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(getUserId(), shippingId);
    }

    @ApiOperation("收货地址列表")
    @PostMapping("list")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestBody PageVo pageVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(getUserId(), pageVo.getPageNum(), pageVo.getPageSize());
    }

}
