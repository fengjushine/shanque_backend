package com.fengju.shanque.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.entity.ComTag;

import java.util.List;

public interface ComTagService extends IService<ComTag> {
    List<ComTag> insertTags(List<String> tagNames);

    Page<ComPost> selectTopicsByTagId(Page<ComPost> topicPage, String id);
}
