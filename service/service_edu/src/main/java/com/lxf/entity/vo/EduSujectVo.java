package com.lxf.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EduSujectVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String parentId;

    private List<EduSujectVo> childs;



}
