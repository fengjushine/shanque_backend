package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.mapper.ComCommentMapper;
import com.fengju.shanque.model.dto.CommentDTO;
import com.fengju.shanque.model.entity.ComComment;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.model.vo.CommentVO;
import com.fengju.shanque.service.ComCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ComCommentServiceImpl extends ServiceImpl<ComCommentMapper, ComComment> implements ComCommentService {
    @Override
    public List<CommentVO> getCommentByTopicID(String topicid) {
        List<CommentVO> list = new ArrayList<CommentVO>();
        try {
            list = this.baseMapper.getCommentsByTopicID(topicid);
        } catch (Exception e) {
            log.info("获取评论失败");
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ComComment create(CommentDTO dto, ComUser user) {
        ComComment comment = ComComment.builder()
                .userId(user.getId())
                .content(dto.getContent())
                .topicId(dto.getTopic_id())
                .createTime(new Date())
                .build();
        this.baseMapper.insert(comment);
        return comment;
    }
}
