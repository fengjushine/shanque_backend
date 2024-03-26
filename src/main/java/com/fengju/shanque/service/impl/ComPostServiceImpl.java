package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.mapper.ComTopicMapper;
import com.fengju.shanque.mapper.ComTagMapper;
import com.fengju.shanque.mapper.ComUserMapper;
import com.fengju.shanque.model.dto.CreateTopicDTO;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.entity.ComTag;
import com.fengju.shanque.model.entity.ComTopicTag;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.model.vo.PostVO;
import com.fengju.shanque.model.vo.ProfileVO;
import com.fengju.shanque.service.ComPostService;
import com.fengju.shanque.service.ComTagService;
import com.fengju.shanque.service.ComTopicTagService;
import com.fengju.shanque.service.ComUserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComPostServiceImpl extends ServiceImpl<ComTopicMapper, ComPost> implements ComPostService {

    @Resource
    private ComTagMapper comTagMapper;

    @Resource
    private ComUserMapper comUserMapper;

    @Autowired
    private ComTopicTagService comTopicTagService;

    @Autowired
    @Lazy
    private ComTagService comTagService;

    @Autowired
    private ComUserService comUserService;

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        //查询话题，得到iPage对象
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        //对iPage对象进行遍历，查询话题的标签
        setTopicTags(iPage);
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ComPost create(CreateTopicDTO dto, ComUser user) {
        ComPost comPost = this.baseMapper.selectOne(new LambdaQueryWrapper<ComPost>().eq(ComPost::getTitle, dto.getTitle()));
        Assert.isNull(comPost, "话题已存在，请修改");

        //封装，保存帖子
        ComPost topic = ComPost.builder()
                .userId(user.getId())
                .title(dto.getTitle())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .createTime(new Date())
                .build();
        this.baseMapper.insert(topic);

        //用户积分增加
        int newScore = user.getScore() + 1;
        comUserMapper.updateById(user.setScore(newScore));

        //标签
        if (!ObjectUtils.isEmpty(dto.getTags())) {
            //保存标签并返回标签集合
            List<ComTag> tags = comTagService.insertTags(dto.getTags());
            //处理标签与话题的关联
            comTopicTagService.createTopicTag(topic.getId(), tags);
        }

        return topic;
    }

    @Override
    public Map<String, Object> viewTopic(String id) {
        Map<String, Object> map = new HashMap<>(16);
        ComPost topic = this.baseMapper.selectById(id);
        Assert.notNull(topic, "话题不存在，或已被作者删除");
        //帖子浏览数加一
        topic.setView(topic.getView() + 1);
        this.baseMapper.updateById(topic);
        //emoji转码
        topic.setContent(EmojiParser.parseToUnicode(topic.getContent()));
        map.put("topic", topic);
        //获取标签id
        LambdaQueryWrapper<ComTopicTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ComTopicTag::getTopicId, id);
        Set<String> set = new HashSet<>();
        for (ComTopicTag comTopicTag : comTopicTagService.list(lambdaQueryWrapper)) {
            set.add(comTopicTag.getTagId());
        }
        //获取标签集合
        List<ComTag> comTags = comTagService.listByIds(set);
        map.put("tags", comTags);

        //作者
        ProfileVO user = comUserService.getUserProfile(topic.getUserId());
        map.put("user", user);
        return map;
    }

    @Override
    public List<ComPost> getRecommend(String id) {
        return this.baseMapper.selectRecommend(id);
    }

    @Override
    public Page<PostVO> searchByKey(String keyword, Page<PostVO> page) {
        //查询话题
        Page<PostVO> iPage = this.baseMapper.searchByKey(page, keyword);
        //设置话题的标签
        setTopicTags(iPage);

        return iPage;
    }

    private void setTopicTags(Page<PostVO> iPage) {
        iPage.getRecords().forEach(topic -> {
            List<ComTopicTag> topicTags = comTopicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                List<String> tagIds = topicTags.stream().map(ComTopicTag::getTagId).collect(Collectors.toList());
                List<ComTag> tags = comTagMapper.selectBatchIds(tagIds);
                topic.setTags(tags);
            }
        });
    }
}
