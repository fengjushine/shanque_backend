package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.mapper.ComTagMapper;
import com.fengju.shanque.mapper.ComTopicTagMapper;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.entity.ComTag;
import com.fengju.shanque.service.ComPostService;
import com.fengju.shanque.service.ComTagService;
import com.fengju.shanque.service.ComTopicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ComTagServiceImpl extends ServiceImpl<ComTagMapper, ComTag> implements ComTagService {

    @Autowired
    private ComTopicTagService comTopicTagService;

    @Autowired
    private ComPostService comPostService;

    @Resource
    private ComTopicTagMapper comTopicTagMapper;

    @Override
    public List<ComTag> insertTags(List<String> tagNames) {
        List<ComTag> tagList = new ArrayList<>();
        for (String tagName : tagNames) {
            ComTag tag = this.baseMapper.selectOne(new LambdaQueryWrapper<ComTag>().eq(ComTag::getName, tagName));
            //添加标签为空，则新建tag对象，否则计数加一
            if (tag == null) {
                tag = ComTag.builder().name(tagName).build();
                this.baseMapper.insert(tag);
            } else {
                tag.setTopicCount(tag.getTopicCount() + 1);
            }
            tagList.add(tag);
        }
        return tagList;
    }

    @Override
    public Page<ComPost> selectTopicsByTagId(Page<ComPost> topicPage, String id) {
        //获取关联的话题id
        Set<String> ids = comTopicTagMapper.getTopicIdsByTagId(id);
        LambdaQueryWrapper<ComPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ComPost::getId, ids);
        return comPostService.page(topicPage, wrapper);
    }
}
