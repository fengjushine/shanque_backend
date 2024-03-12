package com.fengju.shanque.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengju.shanque.model.dto.CommentDTO;
import com.fengju.shanque.model.entity.ComComment;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.model.vo.CommentVO;

import java.util.List;

public interface ComCommentService extends IService<ComComment> {
    List<CommentVO> getCommentByTopicID(String topicid);

    ComComment create(CommentDTO dto, ComUser user);
}
