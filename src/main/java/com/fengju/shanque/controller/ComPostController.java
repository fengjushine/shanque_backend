package com.fengju.shanque.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.dto.CreateTopicDTO;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.model.vo.PostVO;
import com.fengju.shanque.service.ComPostService;
import com.fengju.shanque.service.ComUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.fengju.shanque.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/post")
public class ComPostController extends BaseController {

    @Resource
    private ComPostService comPostService;

    @Resource
    private ComUserService comUserService;

    /*
    * 帖子的分页查询
    * */
    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = comPostService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }

    /*
    * 发表帖子
    * */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<ComPost> create(@RequestHeader(value = USER_NAME) String userName, @RequestBody CreateTopicDTO dto) {
        //根据用户名查找用户
        ComUser user = comUserService.getUserByUsername(userName);
        //创建帖子
        ComPost topic = comPostService.create(dto, user);
        return ApiResult.success(topic);
    }


}
