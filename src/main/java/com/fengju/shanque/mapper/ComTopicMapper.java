package com.fengju.shanque.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.vo.PostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComTopicMapper extends BaseMapper<ComPost> {
    /**
     * 分页查询首页话题列表
     * <p>
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> selectListAndPage(@Param("page") Page<PostVO> page, @Param("tab") String tab);

    List<ComPost> selectRecommend(@Param("id") String id);

    /*
    * 全文检索
    * */
    Page<PostVO> searchByKey(@Param("page") Page<PostVO> page, @Param("keyword") String keyword);
}
