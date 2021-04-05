package com.lxf.controller;

import com.lxf.entity.R;
import com.lxf.entity.vo.EduSujectVo;
import com.lxf.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lxf
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/edu/edu-subject")
@CrossOrigin
@Api(tags = "课程分类操作api")
@Slf4j
public class EduSubjectController {
  @Autowired
    private EduSubjectService eduSubjectService;
  @PostMapping("/addEduSubject")
    public R<String> importEduSubject(@RequestParam("file") MultipartFile file){
      log.info("进入EduSubjectController==========》importEduSubject");
      eduSubjectService.importEduSubject(file);
      return R.ok();
  };
  @GetMapping
  @ApiOperation(value = "获取树形结构的课程分类")
  public R<List<EduSujectVo>> getEduSubject(){
    List<EduSujectVo> result=eduSubjectService.getEduSubject();
    if(null==result){
      return  R.error();
    }else{
      return  R.ok().data(result);
    }

  }
}

