package com.lxf.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxf.entity.EduChapter;
import com.lxf.entity.EduVideo;
import com.lxf.entity.vo.CharpterVo;
import com.lxf.mapper.EduChapterMapper;
import com.lxf.mapper.EduVideoMapper;
import com.lxf.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lxf
 * @since 2021-04-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduChapterMapper eduChapterMapper;

    @Autowired
    private EduVideoMapper eduVideoMapper;

    @Override
    public List<CharpterVo> queryCharterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",courseId).orderByAsc("sort");
        List<EduChapter> eduChapters = eduChapterMapper.selectList(wrapper);
        if(CollectionUtil.isEmpty(eduChapters)){
            return  null;
        }
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();

        videoWrapper.in("chapter_id",eduChapters.stream().map(e->e.getId()).collect(Collectors.toList()))
                    .eq("course_id",courseId)
                    .orderByAsc("sort")
        ;
        List<EduVideo> eduVideos = eduVideoMapper.selectList(videoWrapper);
        List<CharpterVo> result=eduChapters.stream().map(e->{
            CharpterVo charpterVo = new CharpterVo();
            BeanUtils.copyProperties(e,charpterVo);
            ArrayList<EduVideo> eduVideos1 = new ArrayList<>();
            for(EduVideo video:eduVideos){
                if(e.getId().equals(video.getChapterId())){
                    eduVideos1.add(video);
                }
            }
            charpterVo.setEduVideos(eduVideos1);
            return charpterVo;
        }).collect(Collectors.toList());
        return result;
    }
}
