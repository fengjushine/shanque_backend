package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.mapper.ComFollowMapper;
import com.fengju.shanque.model.entity.ComFollow;
import com.fengju.shanque.service.ComFollowService;
import org.springframework.stereotype.Service;

@Service
public class ComFollowServiceImpl extends ServiceImpl<ComFollowMapper, ComFollow> implements ComFollowService {
}
