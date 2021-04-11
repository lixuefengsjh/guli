package com.lxf.service;

import com.lxf.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxf.entity.vo.CharpterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lxf
 * @since 2021-04-04
 */
public interface EduChapterService extends IService<EduChapter> {

    List<CharpterVo> queryCharterByCourseId(String courseId);

    boolean deleteCharpterAndVideoById(String id);
}
