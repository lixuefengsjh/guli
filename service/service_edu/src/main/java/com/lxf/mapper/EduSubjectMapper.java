package com.lxf.mapper;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxf.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author lxf
 * @since 2021-04-03
 */
public interface EduSubjectMapper extends BaseMapper<EduSubject> {

    void saveBatch(List<EduSubject> list);
}
