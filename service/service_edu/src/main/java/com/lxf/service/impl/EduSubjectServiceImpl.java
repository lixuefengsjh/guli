package com.lxf.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxf.entity.EduSubject;
import com.lxf.entity.GuliException;
import com.lxf.entity.ModuleConstant;
import com.lxf.entity.vo.EduSujectVo;
import com.lxf.mapper.EduSubjectMapper;
import com.lxf.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lxf
 * @since 2021-04-03
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Autowired
    private EduSubjectMapper eduSubjectMapper;

    @Override
    public void importEduSubject(MultipartFile file) {
        if(null==file){
            throw  new GuliException(9999,ModuleConstant.SERVICE_EDU,"上传的文件不能为空");
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("上传文件获取流过程出错");
            e.printStackTrace();
        }
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(inputStream, EduSubject.class, new EduSubjectDataListener(eduSubjectMapper)).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    @Override
    public  List<EduSujectVo> getEduSubject() {
        QueryWrapper<EduSubject> parentWrapper=new QueryWrapper<>();
        parentWrapper.eq("parent_id",0).orderByAsc("sort");
        List<EduSubject> parents = eduSubjectMapper.selectList(parentWrapper);
        log.info(String.format("查询出来的所有父节点为：%s",parents.toString()));
        if(CollUtil.isEmpty(parents)){
            throw  new GuliException(9999,ModuleConstant.SERVICE_EDU,"未查询导数据");
        }
        QueryWrapper<EduSubject> childWrapper=new QueryWrapper<>();
        childWrapper.notIn("parent_id",0).orderByAsc("sort");
        List<EduSubject> childs= eduSubjectMapper.selectList(childWrapper);
        log.info(String.format("查询出来的所有孩子节点为：%s",parents.toString()));
        List<EduSujectVo> result=parents.stream().map(e->{
            EduSujectVo eduSujectVo = new EduSujectVo();
            BeanUtils.copyProperties(e,eduSujectVo);
            return eduSujectVo;
        }).collect(Collectors.toList());
        log.info(String.format("经过处理后的所有父节点为：%s",result.toString()));
        if(CollUtil.isEmpty(childs)){
            return result;
        }
        Map<String,List<EduSujectVo>> groupsChild=childs.stream().map(e->{
            EduSujectVo vo= new EduSujectVo();
            BeanUtils.copyProperties(e,vo);
            return vo;
        }).collect(Collectors.groupingBy(EduSujectVo::getParentId));

        log.info(String.format("经过处理后的所有孩子节点为：%s",groupsChild.toString()));
        return  result.stream().map(e->{
            e.setChilds(groupsChild.get(e.getId()));
            return  e;
        }).collect(Collectors.toList());
    }
}
