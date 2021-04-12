package com.lxf.controller;


import com.lxf.entity.EduVideo;
import com.lxf.entity.R;
import com.lxf.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lxf
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/edu/edu-video")
@Api(tags = "小节视频相关处理")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @PostMapping
    @ApiOperation("保存小节内容")
    public R<String> saveOrUpdate(@RequestBody EduVideo video) {
        return eduVideoService.saveOrUpdate(video) ? R.ok().data(video.getId()) : R.error();
    }
    @GetMapping("/{id}")
    @ApiOperation("删除小节")
    public R<String> delete(@PathVariable String id) {
        return eduVideoService.removeById(id)?R.ok():R.error();
    }
}

