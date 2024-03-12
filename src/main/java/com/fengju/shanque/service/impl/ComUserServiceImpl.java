package com.fengju.shanque.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fengju.shanque.common.exception.ApiAsserts;
import com.fengju.shanque.jwt.JwtUtil;
import com.fengju.shanque.mapper.ComFollowMapper;
import com.fengju.shanque.mapper.ComTopicMapper;
import com.fengju.shanque.mapper.ComUserMapper;
import com.fengju.shanque.model.dto.LoginDTO;
import com.fengju.shanque.model.dto.RegisterDTO;
import com.fengju.shanque.model.entity.ComFollow;
import com.fengju.shanque.model.entity.ComPost;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.model.vo.ProfileVO;
import com.fengju.shanque.service.ComUserService;
import com.fengju.shanque.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ComUserServiceImpl extends ServiceImpl<ComUserMapper, ComUser> implements ComUserService {

    @Autowired
    private ComTopicMapper comTopicMapper;

    @Autowired
    private ComFollowMapper comFollowMapper;

    @Override
    public ComUser executeRegister(RegisterDTO dto) {
        //查询是否有相同用户名的用户
        LambdaQueryWrapper<ComUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ComUser::getUsername, dto.getName()).or().eq(ComUser::getEmail, dto.getEmail());
        ComUser comUser = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(comUser)) {
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

    @Override
    public ComUser getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<ComUser>().eq(ComUser::getUsername, username));
    }

    @Override
    public String executeLogin(LoginDTO dto) {
        String token = null;
        try {
            ComUser user = getUserByUsername(dto.getUsername());
            String encodePwd = MD5Utils.getPwd(dto.getPassword());
            if(!encodePwd.equals(user.getPassword()))
            {
                throw new Exception("密码错误");
            }
            token = JwtUtil.generateToken(String.valueOf(user.getUsername()));
        } catch (Exception e) {
            log.warn("用户不存在or密码验证失败=======>{}", dto.getUsername());
        }
        return token;
    }

    @Override
    public ProfileVO getUserProfile(String userId) {
        ProfileVO profile = new ProfileVO();
        ComUser user = baseMapper.selectById(userId);
        BeanUtils.copyProperties(user, profile);
        //用户文章数
        int count = comTopicMapper.selectCount(new LambdaQueryWrapper<ComPost>().eq(ComPost::getUserId, userId));
        profile.setTopicCount(count);

        //粉丝数
        int followers = comFollowMapper.selectCount(new LambdaQueryWrapper<ComFollow>().eq(ComFollow::getParentId, userId));
        profile.setFollowerCount(followers);
        return profile;
    }
}
