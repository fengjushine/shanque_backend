package com.fengju.shanque.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengju.shanque.model.entity.ComTag;
import com.fengju.shanque.model.entity.ComTopicTag;

import java.util.List;

public interface ComTopicTagService extends IService<ComTopicTag> {
    /**
     * 获取Topic Tag 关联记录
     *
     * @param topicId TopicId
     * @return
     */
    List<ComTopicTag> selectByTopicId(String topicId);

    void createTopicTag(String id, List<ComTag> tags);
}
