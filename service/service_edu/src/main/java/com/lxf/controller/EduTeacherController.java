package com.lxf.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxf.entity.EduTeacher;
import com.lxf.entity.R;
import com.lxf.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lxf
 * @since 2021-03-27
 */
@RestController
@Api(tags = "讲师管理模块")
@RequestMapping("/edu-teacher")
@CrossOrigin
@Slf4j
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @ApiOperation("根据id查询讲师信息")
    @GetMapping("/{id}")
    public R<EduTeacher> findTeacherById(@PathVariable  String id) {
        return R.ok().data(eduTeacherService.getById(id));
    }

    @ApiOperation("查询所有讲师列表")
    @GetMapping("/findAll")
    public R<List<EduTeacher>> findAllTeacher() {
        return R.ok().data(eduTeacherService.list(null));
    }

    @ApiOperation("分页查询：查询讲师列表")
    @PostMapping("/findAllByPage/{limit}/{size}")
    public R<Page<EduTeacher>> findAllTeacherByPage(@PathVariable Long limit, @PathVariable Long size, @RequestBody EduTeacher teacher) {
        log.info(String.format("EduTeacherController====》findAllTeacherByPage获取的参数为user：{%d},{%d},{%s}",limit,size,teacher));
        return R.ok().data(eduTeacherService.myPage(limit, size, teacher));
    }

    @ApiOperation("新增教师")
    @PostMapping("/addTeacher")
    public R<String> addTeacher(@RequestBody EduTeacher teacher) {
        boolean flag ;
        if(null!=teacher&& StrUtil.isEmpty(teacher.getId())){
            flag=eduTeacherService.save(teacher);
        }else{
            flag=eduTeacherService.updateById(teacher);
        }

        if (flag) {
            return R.ok().data(teacher.getId());
        }
        return R.error();
    }

    @ApiOperation("逻辑删除指定的讲师")
//    @ApiImplicitParams注解有个小坑，前段传来的值都变成body
//    @ApiImplicitParams(
//            {@ApiImplicitParam(name = "id",value = "讲师id" , dataType = "String")}
//    )
    @DeleteMapping("/{id}")
    public R<Boolean> removeTeacher(@PathVariable String id) {
        System.out.println(id);
        return R.ok().data(eduTeacherService.removeById(id));
    }

}

