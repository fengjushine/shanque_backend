package com.fengju.shanque.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fengju.shanque.model.entity.ComTip;
import org.springframework.stereotype.Repository;

@Repository
public interface ComTipMapper extends BaseMapper<ComTip> {

    ComTip getRandomTip();

}
