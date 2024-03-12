package com.fengju.shanque.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.entity.ComTag;
import com.fengju.shanque.service.ComTagService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tag")
public class ComTagController {

    @Resource
    private ComTagService comTagService;

    @GetMapping("/{name}")
    public ApiResult<Map<String, Object>> getTopicByTag(
            @PathVariable("name") String tagName,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> map = new HashMap<>(16);

        //查询对应标签
        LambdaQueryWrapper<ComTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ComTag::getName, tagName);
        ComTag one = comTagService.getOne(wrapper);
        Assert.notNull(one, "话题不存在，或已被管理员删除");
        //查找标签下帖子
        Page<ComPost> topics = comTagService.selectTopicsByTagId(new Page<>(page, size), one.getId());
        //获取热门标签
        Page<ComTag> hotTags = comTagService.page(new Page<>(1, 10),
                new LambdaQueryWrapper<ComTag>()
                        .notIn(ComTag::getName, tagName)
                        .orderByDesc(ComTag::getTopicCount));
        map.put("topics", topics);
        map.put("hotTags", hotTags);

        return ApiResult.success(map);
    }

}
