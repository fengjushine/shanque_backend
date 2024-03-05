package com.fengju.shanque.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengju.shanque.model.dto.RegisterDTO;
import com.fengju.shanque.model.entity.ComUser;

public interface ComUserService extends IService<ComUser> {

    /*
    * 注册功能
    * */
    ComUser executeRegister(RegisterDTO dto);
}
