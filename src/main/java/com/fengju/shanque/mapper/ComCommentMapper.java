package com.fengju.shanque.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fengju.shanque.model.entity.ComComment;
import com.fengju.shanque.model.vo.CommentVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComCommentMapper extends BaseMapper<ComComment> {
    List<CommentVO> getCommentsByTopicID(String topicid);
}
