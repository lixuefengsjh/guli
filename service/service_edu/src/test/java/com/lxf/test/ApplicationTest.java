package com.lxf.test;

import com.lxf.controller.EduTeacherController;


import com.lxf.handler.MyMetaObjectHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void setUp() throws Exception {
        MyMetaObjectHandler bean = applicationContext.getBean(MyMetaObjectHandler.class);
        System.out.println(String.format("port is : [%s]", bean));
    }

}
