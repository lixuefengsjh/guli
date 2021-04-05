package com.lxf.service;

import com.lxf.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxf.entity.vo.EduSujectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lxf
 * @since 2021-04-03
 */
public interface EduSubjectService extends IService<EduSubject> {

    void importEduSubject(MultipartFile file);

    List<EduSujectVo> getEduSubject();
}
