package com.taohan.online.exam.dao;

import com.taohan.online.exam.po.ExamSubjectMiddleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: ExamSubjectMiddleInfoMapper</p>
 * <p>Description: </p>
 */

@Repository
public interface ExamSubjectMiddleInfoMapper {

    public List<ExamSubjectMiddleInfo> getExamPaperWithSubject(ExamSubjectMiddleInfo esm);

    public int isAddESM(Map<String, Object> map);

    public int removeSubjectWithExamPaper(Map<String, Object> map);

    public Integer getEsmByExamIdWithSubjectId(ExamSubjectMiddleInfo esm);

    int getCount(int examPaperId, int subjectId);
}
