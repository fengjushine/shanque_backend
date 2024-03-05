package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.common.exception.ApiAsserts;
import com.fengju.shanque.mapper.ComUserMapper;
import com.fengju.shanque.model.dto.RegisterDTO;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.service.ComUserService;
import com.fengju.shanque.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
public class ComUserServiceImpl extends ServiceImpl<ComUserMapper, ComUser> implements ComUserService {
    @Override
    public ComUser executeRegister(RegisterDTO dto) {
        //查询是否有相同用户名的用户
        LambdaQueryWrapper<ComUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ComUser::getUsername, dto.getName()).or().eq(ComUser::getEmail, dto.getEmail());
        ComUser umsUser = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(umsUser)) {
            ApiAsserts.fail("账号或邮箱已存在！");
        }
        //builder()方法可以链式的为对象赋值
        ComUser addUser = ComUser.builder()
                .username(dto.getName())
                .alias(dto.getName())
                .password(MD5Utils.getPwd(dto.getPass()))
                .email(dto.getEmail())
                .createTime(new Date())
                .status(true)
                .build();
        baseMapper.insert(addUser);

        return addUser;
    }
}
