package com.fengju.shanque.controller;

import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.entity.ComTip;
import com.fengju.shanque.service.ComTipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tip")
public class BaseController {

    @Resource
    private ComTipService comTipService;

    @GetMapping("/today")
    public ApiResult<ComTip> getRandomTip() {
        ComTip tip = comTipService.getRandomTip();
        return ApiResult.success(tip);
    }

}
