package com.jeiker.mall.controller.frontend;

import com.github.pagehelper.PageInfo;
import com.jeiker.mall.common.BaseController;
import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.req.IdVo;
import com.jeiker.mall.model.vo.ProductDetailVo;
import com.jeiker.mall.service.IProductService;
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
@RequestMapping("/app/product")
@Api("前台-产品管理")
public class ProductController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService iProductService;

    @ApiOperation("产品详情")
    @PostMapping("/detail")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(@RequestBody IdVo productId) {
        return iProductService.getProductDetail(productId.getId());
    }

    @ApiOperation("产品列表")
    @PostMapping("/list")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
