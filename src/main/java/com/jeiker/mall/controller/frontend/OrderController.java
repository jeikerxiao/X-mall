package com.jeiker.mall.controller.frontend;

//import com.alipay.api.AlipayApiException;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.alipay.demo.trade.config.Configs;

import com.google.common.collect.Maps;
import com.jeiker.mall.common.BaseController;
import com.jeiker.mall.common.Const;
import com.jeiker.mall.common.ResponseCode;
import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.User;
import com.jeiker.mall.model.req.IdVo;
import com.jeiker.mall.model.req.LongIdVo;
import com.jeiker.mall.model.req.PageVo;
import com.jeiker.mall.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by geely
 */

@Controller
@RequestMapping("/app/order")
@Api("前台-订单管理")
public class OrderController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService iOrderService;

    @ApiOperation("创建订单")
    @PostMapping("/create")
    @ResponseBody
    public ServerResponse create(@RequestBody IdVo shippingId) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.createOrder(getUserId(), shippingId.getId());
    }

    @ApiOperation("取消")
    @PostMapping("/cancel")
    @ResponseBody
    public ServerResponse cancel(@RequestBody LongIdVo orderNo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.cancel(getUserId(), orderNo.getId());
    }

    @ApiOperation("获取订单")
    @GetMapping("/")
    @ResponseBody
    public ServerResponse getOrderCartProduct() {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderCartProduct(getUserId());
    }

    @ApiOperation("订单详情")
    @PostMapping("/detail")
    @ResponseBody
    public ServerResponse detail(@RequestBody LongIdVo orderNo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderDetail(getUserId(), orderNo.getId());
    }

    @ApiOperation("订单列表")
    @PostMapping("/list")
    @ResponseBody
    public ServerResponse list(@RequestBody PageVo pageVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderList(getUserId(), pageVo.getPageNum(), pageVo.getPageSize());
    }

    @ApiOperation("支付")
    @PostMapping("/pay")
    @ResponseBody
    public ServerResponse pay(@RequestBody LongIdVo orderNo, HttpServletRequest request) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(orderNo.getId(), getUserId(), path);
    }

    @ApiOperation("支付宝回调")
    @PostMapping("/alipay/callback")
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();

        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {

                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝回调,sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

        //非常重要,验证回调的正确性,是不是支付宝发的.并且呢还要避免重复通知.

        params.remove("sign_type");
        /**
         try {
         boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());

         if(!alipayRSACheckedV2){
         return ServerResponse.createByErrorMessage("非法请求,验证不通过,再恶意请求我就报警找网警了");
         }
         } catch (AlipayApiException e) {
         logger.error("支付宝验证回调异常",e);
         }
         */
        //todo 验证各种数据


        //
        ServerResponse serverResponse = iOrderService.aliCallback(params);
        if (serverResponse.isSuccess()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    @ApiOperation("查询订单状态")
    @PostMapping("/query/status")
    @ResponseBody
    public ServerResponse<Boolean> queryOrderPayStatus(@RequestBody LongIdVo orderNo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        ServerResponse serverResponse = iOrderService.queryOrderPayStatus(getUserId(), orderNo.getId());
        if (serverResponse.isSuccess()) {
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }

}
