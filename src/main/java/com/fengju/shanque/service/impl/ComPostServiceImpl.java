package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.mapper.ComTopicMapper;
import com.fengju.shanque.mapper.ComTagMapper;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.entity.ComTag;
import com.fengju.shanque.model.entity.ComTopicTag;
import com.fengju.shanque.model.vo.PostVO;
import com.fengju.shanque.service.ComPostService;
import com.fengju.shanque.service.ComTopicTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComPostServiceImpl extends ServiceImpl<ComTopicMapper, ComPost> implements ComPostService {

    @Resource
    private ComTagMapper comTagMapper;

    @Autowired
    private ComTopicTagService comTopicTagService;

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        //查询话题，得到iPage对象
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        //对iPage对象进行遍历，查询话题的标签
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
