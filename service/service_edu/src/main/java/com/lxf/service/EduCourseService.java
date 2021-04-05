package com.lxf.service;

import com.lxf.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxf.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lxf
 * @since 2021-04-04
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
