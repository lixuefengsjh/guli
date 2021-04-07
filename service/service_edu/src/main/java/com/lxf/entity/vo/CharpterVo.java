package com.lxf.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lxf.entity.EduChapter;
import com.lxf.entity.EduVideo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class CharpterVo {
    private String id;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    List<EduVideo> eduVideos;
}
