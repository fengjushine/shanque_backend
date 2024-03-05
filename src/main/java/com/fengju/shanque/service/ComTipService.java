package com.fengju.shanque.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengju.shanque.model.entity.ComTip;

public interface ComTipService extends IService<ComTip> {
    ComTip getRandomTip();
}
