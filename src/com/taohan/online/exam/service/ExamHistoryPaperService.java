package com.taohan.online.exam.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.taohan.online.exam.po.ExamHistoryInfo;
import com.taohan.online.exam.po.ExamHistoryPaper;

/**
  *
  * <p>Title: ExamHistoryPaperService</p>
  * <p>Description: </p>
  */

@Repository
public interface ExamHistoryPaperService {

	//查询考试历史信息，针对前台学生查询
	public List<ExamHistoryPaper> getExamHistoryToStudent(int studentId);
	
	public int isAddExamHistory(Map<String, Object> map);
	
	public int getHistoryInfoWithIds(Map<String, Object> map);
	
	public List<ExamHistoryInfo> getExamHistoryToTeacher();

	// 根据老师classid查学生历史试卷
	public List<ExamHistoryInfo> getExamHistoryToTeacherByClassId(Integer classId);
}
