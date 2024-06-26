package com.taohan.online.exam.dao;

import java.util.List;
import java.util.Map;

import com.taohan.online.exam.po.ClassInfo;
import org.springframework.stereotype.Repository;

import com.taohan.online.exam.po.StudentInfo;

/**
 * <p>Title: StudentInfoMapper</p>
 * <p>Description: </p>
 */

@Repository
public interface StudentInfoMapper {

    public List<StudentInfo> getStudents(Map<String, Object> map);

    public StudentInfo getStudentById(int studentId);

    public int isUpdateStudent(StudentInfo student);

    public int isDelStudent(int studentId);

    public int isAddStudent(StudentInfo student);

    public int getStudentTotal();

    public int getStudentTotalByClassId(int classId);

    public StudentInfo getStudentByAccountAndPwd(String studentAccount);

    public int isResetPwdWithStu(StudentInfo studentInfo);

    public List<StudentInfo> getStudentsByClassId(int classId);
}
