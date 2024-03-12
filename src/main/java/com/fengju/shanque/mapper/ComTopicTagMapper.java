package com.fengju.shanque.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fengju.shanque.model.entity.ComTopicTag;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ComTopicTagMapper extends BaseMapper<ComTopicTag> {
    Set<String> getTopicIdsByTagId(String id);
}
