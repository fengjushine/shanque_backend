package com.fengju.shanque.controller;

import com.fengju.shanque.common.api.ApiResult;
import com.fengju.shanque.model.dto.LoginDTO;
import com.fengju.shanque.model.dto.RegisterDTO;
import com.fengju.shanque.model.entity.ComUser;
import com.fengju.shanque.service.ComUserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class ComUserController {

    @Resource
    private ComUserService comUserService;

    /*
    * 账号注册
    * */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        ComUser user = comUserService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        return ApiResult.success(map);
    }

    /*
    * 账号登录
    * */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        //若登录成功生成token
        String token = comUserService.executeLogin(dto);
        //若token为空
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ApiResult.success(map, "登录成功");
    }

}
