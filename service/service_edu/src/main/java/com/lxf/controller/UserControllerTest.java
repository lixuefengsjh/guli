package com.lxf.controller;

import cn.hutool.core.util.ArrayUtil;
import com.lxf.entity.R;
import com.lxf.entity.dto.UserLoginDto;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@Api(tags = "用户管理模块--test")
@RestController
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserControllerTest {
    @PostMapping("/login")
    public R<String>  userLogin(@RequestBody UserLoginDto user ){
        log.info(String.format("进入UserControllerTest====》userLogin获取的参数为user：{%s}",user));
        HashMap<String,String> token=new HashMap<>();
        token.put("token", String.valueOf(UUID.randomUUID()));
        return  R.ok().data(token);
    }
    @GetMapping("/info")
    public R<HashMap>  userInfo(@RequestParam String token ){
        log.info(String.format("进入UserControllerTest====》userInfo获取的参数为token：{%s}",token));
        HashMap<String,Object> result=new HashMap<>();
        result.put("name", "李雪峰");
        result.put("avatar", "http://beijing.aliyuncs.com/avatar/default.jpg");
        result.put("roles", Arrays.asList("admin"));
        return  R.ok().data(result);
    }
}
