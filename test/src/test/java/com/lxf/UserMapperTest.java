package com.lxf;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxf.entity.User;
import com.lxf.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
    @Test
    public void testInsert() {
        System.out.println(("----- insert method test ------"));
        User entity=new User();
        entity.setAge(20);
        entity.setEmail("1203496509@qq.com");
        entity.setName("李雪峰");
//        entity.setId(11L);
       int i= userMapper.insert(entity);
        Assert.assertEquals(1, i);
        System.out.println(entity);
    }
    @Test
    public void testUpdate() {
        System.out.println(("----- Update method test ------"));
//        User entity=new User();
//        entity.setAge(201);
////        entity.setEmail("1203496509@qq.com=1");
//        entity.setName("李雪峰sex");
//        entity.setId(11L);
////        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
////        queryWrapper.eq("id",1373550936000921602L);
////
////        int i= userMapper.update(entity,queryWrapper);
        User user = userMapper.selectById(11l);
        user.setName("李雪峰sex");
        userMapper.updateById(user);

        System.out.println(user);
    }

    @Test
    public void testDelete() {
        System.out.println(("----- Delete method test ------"));
        userMapper.deleteById("1373609849199063041");
    }
    @Test
    public void testQueryByPage() {
        IPage<User> page =new Page(1,3);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("update_time");
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        System.out.println(userIPage.getRecords());
    }
}