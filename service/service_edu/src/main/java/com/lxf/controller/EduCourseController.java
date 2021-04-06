package com.lxf.controller;


import com.lxf.entity.R;
import com.lxf.entity.vo.CourseInfoVo;
import com.lxf.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lxf
 * @since 2021-04-04
 */
@Api(tags = "课程相关的操作api")
@RestController
@RequestMapping("/edu/edu-course")
@Slf4j
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;
    @PostMapping("/add")
    public R<String> courseAdd(@RequestBody CourseInfoVo courseInfoVo) {
        log.info(String.format("EduCourseController====》courseAdd获取的参数为courseInfoVo：{%s}",courseInfoVo));
        return R.ok().data(eduCourseService.saveCourseInfo(courseInfoVo));
    }

}



