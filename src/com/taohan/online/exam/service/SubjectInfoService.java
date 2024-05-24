package com.taohan.online.exam.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.taohan.online.exam.po.SubjectInfo;

/**
  *
  * <p>Title: SubjectInfoService</p>
  * <p>Description: </p>
  */

@Repository
public interface SubjectInfoService {

	public List<SubjectInfo> getSubjects(Map<String, Object> map);
	
	public SubjectInfo getSubjectWithId(int subjectId);

	public int getSubjectTotal();

	public int isAddSubject(SubjectInfo subject);

	public int isUpdateSubject(SubjectInfo subject);

	public int isDelSubject(int subjectId);

	public int getCountBySubjectName(String subjectName);
	
	//批量添加试题
	public int isAddSubjects(Map<String, Object> map);

	public SubjectInfo getBySubjectName(String subjectName);
}
