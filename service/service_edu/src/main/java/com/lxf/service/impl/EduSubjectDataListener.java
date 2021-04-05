package com.lxf.service.impl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lxf.entity.EduSubject;
import com.lxf.mapper.EduSubjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EduSubjectDataListener extends AnalysisEventListener<EduSubject> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<EduSubject> list = new ArrayList<EduSubject>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private EduSubjectMapper eduSubjectMapper;
    public EduSubjectDataListener(EduSubjectMapper eduSubjectMapper) {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        this.eduSubjectMapper = eduSubjectMapper;
    }
    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(EduSubject data, AnalysisContext context) {
        log.info(String.format("解析到一条数据:{}", data));
        data.setGmtCreate(LocalDateTime.now());
        data.setGmtModified(LocalDateTime.now());
        data.setSort(context.getCurrentRowNum());
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }
    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        eduSubjectMapper.saveBatch(list);
        log.info("存储数据库成功！");
    }
}
