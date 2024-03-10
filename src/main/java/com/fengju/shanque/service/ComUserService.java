package com.fengju.shanque.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fengju.shanque.model.dto.LoginDTO;
import com.fengju.shanque.model.dto.RegisterDTO;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.model.vo.ProfileVO;

public interface ComUserService extends IService<ComUser> {

    /*
    * 注册功能
    * */
    ComUser executeRegister(RegisterDTO dto);
    /**
     * 获取用户信息
     *
     * @param username
     * @return dbUser
     */
    ComUser getUserByUsername(String username);
    /**
     * 用户登录
     *
     * @param dto
     * @return 生成的JWT的token
     */
    String executeLogin(LoginDTO dto);

    ProfileVO getUserProfile(String userId);
}
