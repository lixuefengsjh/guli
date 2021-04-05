package com.lxf.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data

@TableName("user")
public class User {
    @TableId( type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @TableField(fill=FieldFill.INSERT)
    private LocalDateTime creatTime;

    @TableField(fill=FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Version
    private Long version;

    @TableLogic
    private Integer deleted;
}