package com.jeiker.mall.controller;

import com.jeiker.mall.mapper.CityMapper;
import com.jeiker.mall.model.City;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author : xiao
 * @Date : 17/3/21 下午6:18
 */
@Api("城市管理")
@RestController
@RequestMapping("/citys")
public class CityController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CityMapper cityMapper;

    @ApiOperation("获取城市信息")
    @GetMapping("/{id}")
    City findCityById(@PathVariable("id") String id) {

        logger.info("findCityById id = {}", id);

        City city = cityMapper.selectByPrimaryKey(Integer.parseInt(id));
        return city;
    }
}
