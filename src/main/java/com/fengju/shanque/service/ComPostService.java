package com.fengju.shanque.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fengju.shanque.model.dto.CreateTopicDTO;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.model.vo.PostVO;

import java.util.List;
import java.util.Map;

public interface ComPostService extends IService<ComPost> {
    /**
     * 获取首页话题列表
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> getList(Page<PostVO> page, String tab);

    /*
    * 新建帖子
    * */
    ComPost create(CreateTopicDTO dto, ComUser user);

    Map<String, Object> viewTopic(String id);

    List<ComPost> getRecommend(String id);

    Page<PostVO> searchByKey(String keyword, Page<PostVO> page);
}
