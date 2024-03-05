package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.mapper.ComTipMapper;
import com.fengju.shanque.model.entity.ComTip;
import com.fengju.shanque.service.ComTipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ComTipServiceImpl extends ServiceImpl<ComTipMapper, ComTip> implements ComTipService {
    @Override
    public ComTip getRandomTip() {
        ComTip todayTip = null;
        try {
            todayTip = this.baseMapper.getRandomTip();
        } catch (Exception e) {
            log.info("tip转化失败");
        }
        return todayTip;
    }
}
