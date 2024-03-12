package com.fengju.shanque.controller;

import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.dto.CommentDTO;
import com.fengju.shanque.model.entity.ComComment;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.model.vo.CommentVO;
import com.fengju.shanque.service.ComCommentService;
import com.fengju.shanque.service.ComUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.fengju.shanque.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/comment")
public class ComCommentController extends BaseController {

    @Resource
    private ComCommentService comCommentService;

    @Resource
    private ComUserService comUserService;

    /*
    * 获取评论
    * */
    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid", defaultValue = "1") String topicid) {
        List<CommentVO> list = comCommentService.getCommentByTopicID(topicid);
        System.out.println("sfdsdf" + list.toString());
        return ApiResult.success(list);
    }

    /*
    * 添加评论
    * */
    @PostMapping("/add_comment")
    public ApiResult<ComComment> add_comment(@RequestHeader(value = USER_NAME) String userName, @RequestBody CommentDTO dto) {
        ComUser user = comUserService.getUserByUsername(userName);
        ComComment comComment = comCommentService.create(dto, user);
        return ApiResult.success(comComment);
    }

}
