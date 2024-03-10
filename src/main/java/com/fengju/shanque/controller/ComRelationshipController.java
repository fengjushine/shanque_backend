package com.fengju.shanque.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.common.exception.ApiAsserts;
import com.fengju.shanque.model.entity.ComFollow;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.service.ComFollowService;
import com.fengju.shanque.service.ComUserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static com.fengju.shanque.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/relationship")
public class ComRelationshipController {

    @Resource
    private ComFollowService comFollowService;

    @Resource
    private ComUserService comUserService;

    @GetMapping("subscribe/{userId}")
    public ApiResult<Object> handleFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("userId") String parentId) {
        ComUser comUser = comUserService.getUserByUsername(userName);
        if (parentId.equals(comUser.getId())) {
            ApiAsserts.fail("不可关注自己");
        }
        ComFollow one = comFollowService.getOne(
                new LambdaQueryWrapper<ComFollow>()
                        .eq(ComFollow::getParentId, parentId)
                        .eq(ComFollow::getFollowerId, comUser.getId())
        );
        if (!ObjectUtils.isEmpty(one)) {
            ApiAsserts.fail("已关注");
        }
        //增加关系
        ComFollow follow = new ComFollow();
        follow.setParentId(parentId);
        follow.setFollowerId(comUser.getId());
        comFollowService.save(follow);
        return ApiResult.success(null, "关注成功");
    }

    @GetMapping("/unsubscribe/{userId}")
    public ApiResult<Object> handleUnFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("userId") String parentId) {
        ComUser comUser = comUserService.getUserByUsername(userName);
        //条件构造器
        LambdaQueryWrapper<ComFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ComFollow::getParentId, parentId).eq(ComFollow::getFollowerId, comUser.getId());
        //校验是否关注
        ComFollow one = comFollowService.getOne(queryWrapper);
        if (ObjectUtils.isEmpty(one)) {
            ApiAsserts.fail("未关注！");
        }
        //删除关系
        comFollowService.remove(queryWrapper);
        return ApiResult.success(null, "取关成功");
    }

    /*
    * 判断是否关注
    * */
    @GetMapping("/validate/{topicUserId}")
    public ApiResult<Map<String, Object>> isFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("topicUserId") String topicUserId) {
        ComUser comUser = comUserService.getUserByUsername(userName);
        Map<String, Object> map = new HashMap<>(16);
        map.put("hasFollow", false);
        if (!ObjectUtils.isEmpty(comUser)) {
            ComFollow one = comFollowService.getOne(new LambdaQueryWrapper<ComFollow>()
                    .eq(ComFollow::getParentId, topicUserId)
                    .eq(ComFollow::getFollowerId, comUser.getId()));
            if (!ObjectUtils.isEmpty(one)) {
                map.put("hasFollow", true);
            }
        }
        return ApiResult.success(map);
    }

}
