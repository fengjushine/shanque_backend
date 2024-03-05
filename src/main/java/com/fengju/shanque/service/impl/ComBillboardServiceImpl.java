package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.mapper.ComBillboardMapper;
import com.fengju.shanque.model.entity.ComBillboard;
import com.fengju.shanque.service.ComBillboardService;
import org.springframework.stereotype.Service;

@Service
public class ComBillboardServiceImpl extends ServiceImpl<ComBillboardMapper, ComBillboard> implements ComBillboardService {
}
