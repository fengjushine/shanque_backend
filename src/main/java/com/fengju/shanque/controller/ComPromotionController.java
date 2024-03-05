package com.fengju.shanque.controller;

import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.entity.ComPromotion;
import com.fengju.shanque.service.ComPromotionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/promotion")
public class ComPromotionController extends BaseController {

    @Resource
    private ComPromotionService comPromotionService;

    @GetMapping("/all")
    public ApiResult<List<ComPromotion>> list() {
        List<ComPromotion> list = comPromotionService.list();
        return ApiResult.success(list);
    }

}
