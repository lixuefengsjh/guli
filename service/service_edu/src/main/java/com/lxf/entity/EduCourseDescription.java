package com.lxf.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 课程简介
 * </p>
 *
 * @author lxf
 * @since 2021-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EduCourseDescription对象", description="课程简介")
public class EduCourseDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "课程简介")
    private String description;


    @ApiModelProperty(value = "创建时间")
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
