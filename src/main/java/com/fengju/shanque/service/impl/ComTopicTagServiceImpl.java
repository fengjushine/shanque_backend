package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.mapper.ComTopicTagMapper;
import com.fengju.shanque.model.entity.ComTopicTag;
import com.fengju.shanque.service.ComTopicTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ComTopicTagServiceImpl extends ServiceImpl<ComTopicTagMapper, ComTopicTag> implements ComTopicTagService {
    @Override
    public List<ComTopicTag> selectByTopicId(String topicId) {
        QueryWrapper<ComTopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ComTopicTag::getTopicId, topicId);
        return this.baseMapper.selectList(wrapper);
    }
}
