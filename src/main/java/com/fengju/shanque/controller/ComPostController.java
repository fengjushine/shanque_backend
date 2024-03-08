package com.fengju.shanque.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.vo.PostVO;
import com.fengju.shanque.service.ComPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/post")
public class ComPostController extends BaseController {

    @Resource
    private ComPostService comPostService;

    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = comPostService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }

}
