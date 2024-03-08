package com.fengju.shanque.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.vo.PostVO;

public interface ComPostService extends IService<ComPost> {
    /**
     * 获取首页话题列表
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> getList(Page<PostVO> page, String tab);
}
