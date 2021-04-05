package com.lxf.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxf.entity.EduTeacher;
import com.lxf.mapper.EduTeacherMapper;
import com.lxf.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lxf
 * @since 2021-03-27
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Page<EduTeacher> myPage(Long limit, Long size, EduTeacher teacher) {
        if(null==limit){
            limit=0L;
        }
        if(null==size){
            size=10L;
        }
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();
        if(teacher!=null){
            if(StrUtil.isNotEmpty(teacher.getName())){
                queryWrapper.like("name",teacher.getName());
            };
            if(StrUtil.isNotEmpty(teacher.getId())){
                queryWrapper.eq("id",teacher.getId());
            };
        }
        queryWrapper.orderByAsc("gmt_create");
        return  this.page(new Page(limit,size),queryWrapper);
    }
}
