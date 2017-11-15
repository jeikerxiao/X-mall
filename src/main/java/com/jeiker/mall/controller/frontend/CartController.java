package com.jeiker.mall.controller.frontend;

import com.jeiker.mall.common.BaseController;
import com.jeiker.mall.common.Const;
import com.jeiker.mall.common.ResponseCode;
import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.User;
import com.jeiker.mall.model.req.CountVo;
import com.jeiker.mall.model.req.IdVo;
import com.jeiker.mall.model.req.ProductIdsVo;
import com.jeiker.mall.model.vo.CartVo;
import com.jeiker.mall.service.ICartService;
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
@RequestMapping("/app/cart")
@Api("前台-类别管理")
public class CartController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private ICartService iCartService;

    @ApiOperation("类别列表")
    @GetMapping("/list")
    @ResponseBody
    public ServerResponse<CartVo> list() {
        return iCartService.list(getUserId());
    }

    @ApiOperation("增加类别")
    @PostMapping("/add")
    @ResponseBody
    public ServerResponse<CartVo> add(@RequestBody CountVo countVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(getUserId(), countVo.getProductId(), countVo.getCount());
    }

    @ApiOperation("修改类别")
    @PostMapping("/update")
    @ResponseBody
    public ServerResponse<CartVo> update(@RequestBody CountVo countVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), countVo.getProductId(), countVo.getCount());
    }

    @ApiOperation("删除产品")
    @PostMapping("/delete")
    @ResponseBody
    public ServerResponse<CartVo> deleteProduct(@RequestBody ProductIdsVo productIds) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(), productIds.getIds());
    }

    @ApiOperation("选择所有类别")
    @GetMapping("/selected_all")
    @ResponseBody
    public ServerResponse<CartVo> selectAll() {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    @ApiOperation("全不选")
    @GetMapping("/unselected_all")
    @ResponseBody
    public ServerResponse<CartVo> unSelectAll() {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    @ApiOperation("选择")
    @PostMapping("/selected")
    @ResponseBody
    public ServerResponse<CartVo> select(@RequestBody IdVo productId) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId.getId(), Const.Cart.CHECKED);
    }

    @ApiOperation("不选择")
    @PostMapping("/unselected")
    @ResponseBody
    public ServerResponse<CartVo> unSelect(@RequestBody IdVo productId) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId.getId(), Const.Cart.UN_CHECKED);
    }

    @ApiOperation("获取产品计数")
    @GetMapping("/count")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount() {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }

    //全选
    //全反选

    //单独选
    //单独反选

    //查询当前用户的购物车里面的产品数量,如果一个产品有10个,那么数量就是10.

}
