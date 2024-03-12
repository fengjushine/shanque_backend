package com.fengju.shanque.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.vo.PostVO;
import com.fengju.shanque.service.ComPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/search")
public class ComSearchController extends BaseController {

    @Resource
    private ComPostService comPostService;

    @GetMapping
    public ApiResult<Page<PostVO>> searchList(@RequestParam("keyword") String keyword,
                                              @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize) {
        Page<PostVO> results = comPostService.searchByKey(keyword, new Page<>(pageNum, pageSize));
        return ApiResult.success(results);
    }

}
