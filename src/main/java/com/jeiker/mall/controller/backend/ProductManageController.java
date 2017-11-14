package com.jeiker.mall.controller.backend;

import com.google.common.collect.Maps;
import com.jeiker.mall.common.BaseController;
import com.jeiker.mall.common.ResponseCode;
import com.jeiker.mall.common.ServerResponse;
import com.jeiker.mall.model.Product;
import com.jeiker.mall.model.User;
import com.jeiker.mall.model.req.IdVo;
import com.jeiker.mall.model.req.PageVo;
import com.jeiker.mall.model.req.ProductSearchVo;
import com.jeiker.mall.model.req.StatusVo;
import com.jeiker.mall.service.IFileService;
import com.jeiker.mall.service.IProductService;
import com.jeiker.mall.service.IUserService;
import com.jeiker.mall.util.PropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by geely
 */

@Controller
@RequestMapping("/manage/product")
@Api(value = "后台-产品接口")
public class ProductManageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProductManageController.class);

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    @ApiOperation("新增产品")
    @PostMapping("save")
    @ResponseBody
    public ServerResponse productSave(@RequestBody Product product) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充我们增加产品的业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation("产品上下架")
    @PostMapping("set_sale_status")
    @ResponseBody
    public ServerResponse setSaleStatus(@RequestBody StatusVo statusVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setSaleStatus(statusVo.getId(), statusVo.getStatus());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation("产品详情")
    @PostMapping("detail")
    @ResponseBody
    public ServerResponse getDetail(@RequestBody IdVo productId) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iProductService.manageProductDetail(productId.getId());

        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation("产品列表")
    @PostMapping("list")
    @ResponseBody
    public ServerResponse getList(@RequestBody PageVo pageVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iProductService.getProductList(pageVo.getPageNum(), pageVo.getPageSize());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation("产品搜索")
    @PostMapping("search")
    @ResponseBody
    public ServerResponse productSearch(@RequestBody ProductSearchVo productVo) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");

        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务
            return iProductService.searchProduct(productVo.getName(), productVo.getId(), productVo.getPageNum(), productVo.getPageSize());
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation("图片上传")
    @PostMapping("upload")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        User user = getUser();
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @ApiOperation("富文本上传")
    @PostMapping("richtext_img_upload")
    @ResponseBody
    public Map richtextImgUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        User user = getUser();
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员");
            return resultMap;
        }
        //富文本中对于返回值有自己的要求,我们使用是simditor所以按照simditor的要求进行返回
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            if (StringUtils.isBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        } else {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }
    }

}
