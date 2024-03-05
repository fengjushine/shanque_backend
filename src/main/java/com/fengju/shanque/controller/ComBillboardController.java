package com.fengju.shanque.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.entity.ComBillboard;
import com.fengju.shanque.service.ComBillboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/billboard")
public class ComBillboardController extends BaseController {

    @Resource
    private ComBillboardService comBillboardService;

    LambdaQueryWrapper<ComBillboard> wrapper = new LambdaQueryWrapper<ComBillboard>();

    //查询show为1的最后一条公告
    @GetMapping("/show")
    public ApiResult<ComBillboard> getNotices() {
        List<ComBillboard> list = comBillboardService.list(wrapper.eq(ComBillboard::isShow, true));
        return ApiResult.success(list.get(list.size() - 1));
    }
}
