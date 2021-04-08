package com.lxf.controller;


import cn.hutool.core.util.StrUtil;
import com.lxf.entity.EduChapter;
import com.lxf.entity.R;
import com.lxf.entity.vo.CharpterVo;
import com.lxf.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lxf
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/edu/edu-chapter")
@Slf4j
@CrossOrigin
@Api(tags = "课程章节管理")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;
    @GetMapping("/{courseId}")
    @ApiOperation("根据课程id，查找所有的章节")
    public R<CharpterVo> queryCharterByCourseId(@PathVariable  String courseId){
        List<CharpterVo> charpterVo= eduChapterService.queryCharterByCourseId(courseId);
        return R.ok().data(charpterVo);
    }
    @PostMapping("/upOrSave")
    @ApiOperation("新增或者修改章节")
    public R<String> updateOrSave(@RequestBody EduChapter eduChapter ){
        Boolean flag;
        if(null!=eduChapter&& !StrUtil.isEmpty(eduChapter.getId())){
            flag= eduChapterService.updateById(eduChapter);
        }else{
            flag= eduChapterService.save(eduChapter);
        }
        if(flag){ return R.ok().data(eduChapter.getId());}
        return R.error();
    }
    @GetMapping("/delete/{id}")
    @ApiOperation("删除章节")
    public R updateOrSave(@PathVariable String id ){return eduChapterService.removeById(id)?R.ok():R.error();}
}

