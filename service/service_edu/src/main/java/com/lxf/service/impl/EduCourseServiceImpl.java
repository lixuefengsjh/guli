package com.lxf.service.impl;

import com.lxf.entity.EduCourse;
import com.lxf.entity.EduCourseDescription;
import com.lxf.entity.vo.CourseInfoVo;
import com.lxf.mapper.EduCourseMapper;
import com.lxf.service.EduCourseDescriptionService;
import com.lxf.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lxf
 * @since 2021-04-04
 */
@Service
@Slf4j
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
   @Autowired
    private  EduCourseMapper eduCourseMapper;
   @Autowired
   private EduCourseDescriptionService eduCourseDescriptionService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = eduCourseMapper.insert(eduCourse);
        String eduCourseId=eduCourse.getId();
        log.info(String.format("插入课程成功，对应的课程id为：【%s】",eduCourseId));
        if(insert>0){
            EduCourseDescription entity=new EduCourseDescription();
            BeanUtils.copyProperties(courseInfoVo,entity);
            entity.setId(eduCourseId);
            eduCourseDescriptionService.save(entity);
        }
        return eduCourseId;
    }
}
