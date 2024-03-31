package com.fengju.shanque.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.dto.CreateTopicDTO;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.model.vo.PostVO;
import com.fengju.shanque.service.ComPostService;
import com.fengju.shanque.service.ComUserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public ApiResult<ComPost> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        //根据用户名查找用户
        ComUser user = comUserService.getUserByUsername(userName);
        //创建帖子
        ComPost topic = comPostService.create(dto, user);
        return ApiResult.success(topic);
    }

    /*
    * 帖子详情展示
    * */
    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        Map<String, Object> map = comPostService.viewTopic(id);
        return ApiResult.success(map);
    }

    /*
    * 推荐帖子
    * */
    @GetMapping("/recommend")
    public ApiResult<List<ComPost>> getRecommend(@RequestParam("topicId") String id) {
        List<ComPost> topics = comPostService.getRecommend(id);
        return ApiResult.success(topics);
    }

    /*
    * 修改帖子
    * */
    @PostMapping("/update")
    public ApiResult<ComPost> update(@RequestHeader(value = USER_NAME) String userName
            , @Valid @RequestBody ComPost post) {
        ComUser comUser = comUserService.getUserByUsername(userName);
        Assert.isTrue(comUser.getId().equals(post.getUserId()), "非本人无权修改");
        post.setModifyTime(new Date());
        post.setContent(EmojiParser.parseToUnicode(post.getContent()));
        comPostService.updateById(post);
        return ApiResult.success(post);
    }

    /*
    * 删除帖子
    * */
    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("id") String id) {
        ComUser comUser = comUserService.getUserByUsername(userName);
        ComPost comPost = comPostService.getById(id);
        Assert.notNull(comPost, "话题已被删除");
        Assert.isTrue(comPost.getUserId().equals(comUser.getId()), "非本人无权删除");
        comPostService.removeById(id);
        return ApiResult.success(null, "删除成功");
    }

}
