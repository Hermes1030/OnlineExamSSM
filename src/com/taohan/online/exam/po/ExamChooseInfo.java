package com.taohan.online.exam.po;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
  *
  * <p>Title: ExamChooseInfo</p>
  * <p>Description: 考生考试选择答案信息</p>
  */

@Component
@Scope("prototype")
public class ExamChooseInfo {

	private Integer chooseId;
	private StudentInfo student;
	private ExamPaperInfo examPaper;
	private SubjectInfo subject;
	private String chooseResult;
	private Integer subjectType;


	public Integer getChooseId() {
		return chooseId;
	}

	public void setChooseId(Integer chooseId) {
		this.chooseId = chooseId;
	}

	public StudentInfo getStudent() {
		return student;
	}

	public void setStudent(StudentInfo student) {
		this.student = student;
	}

	public ExamPaperInfo getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaperInfo examPaper) {
		this.examPaper = examPaper;
	}

	public SubjectInfo getSubject() {
		return subject;
	}

	public void setSubject(SubjectInfo subject) {
		this.subject = subject;
	}

	public String getChooseResult() {
		return chooseResult;
	}

	public void setChooseResult(String chooseResult) {
		this.chooseResult = chooseResult;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}


	public ExamChooseInfo(Integer chooseId, StudentInfo student, ExamPaperInfo examPaper, SubjectInfo subject, String chooseResult, Integer subjectType) {
		this.chooseId = chooseId;
		this.student = student;
		this.examPaper = examPaper;
		this.subject = subject;
		this.chooseResult = chooseResult;
		this.subjectType = subjectType;
	}

	public ExamChooseInfo() {
	}

	@Override
	public String toString() {
		return "ExamChooseInfo{" +
				"chooseId=" + chooseId +
				", student=" + student +
				", examPaper=" + examPaper +
				", subject=" + subject +
				", chooseResult='" + chooseResult + '\'' +
				", subjectType=" + subjectType +
				'}';
	}
}
