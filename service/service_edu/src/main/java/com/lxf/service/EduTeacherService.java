package com.lxf.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxf.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lxf
 * @since 2021-03-27
 */
public interface EduTeacherService extends IService<EduTeacher> {
    Page<EduTeacher>  myPage(Long limit, Long size, EduTeacher teacher);
}
