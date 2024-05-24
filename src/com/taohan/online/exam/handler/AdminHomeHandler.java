package com.taohan.online.exam.handler;

import com.google.gson.Gson;
import com.taohan.online.exam.po.ClassInfo;
import com.taohan.online.exam.po.StudentInfo;
import com.taohan.online.exam.service.ExamPaperInfoService;
import com.taohan.online.exam.service.StudentInfoService;
import com.taohan.online.exam.service.SubjectInfoService;
import com.taohan.online.exam.service.TeacherInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: AdminHomeHandler</p>
 * <p>Description: 后台首页相关</p>
 */

@Controller
public class AdminHomeHandler {

    @Autowired
    ExamPaperInfoService examPaperInfoService;
    @Autowired
    SubjectInfoService subjectInfoService;
    @Autowired
    TeacherInfoService teacherInfoService;
    @Autowired
    StudentInfoService studentInfoService;
    @Autowired
    Gson gson;

    private Logger logger = Logger.getLogger(AdminHomeHandler.class);


    @RequestMapping("/homeInfo")
    public void homeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {


        logger.info("加载后台首页相关数据");

        int examPaperTotal = examPaperInfoService.getExamPpaerTotal();
        int subjectTotal = subjectInfoService.getSubjectTotal();
        int teacherTotal = teacherInfoService.getTeacherTotal();
        int studentTotal = 0;

        int isAdmin = (int) request.getSession().getAttribute("adminPower");
        if (isAdmin != 1) {
            ClassInfo classInfo = new ClassInfo();
            classInfo.setClassId((int) request.getSession().getAttribute("classId"));
            StudentInfo student = new StudentInfo();
            student.setClassInfo(classInfo);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("student", student);
            List<StudentInfo> students = studentInfoService.getStudents(map);
            studentTotal = students.size();
        } else {
            studentTotal = studentInfoService.getStudentTotal();
        }


        String json = "{\"examPaperTotal\":" + examPaperTotal + ", " +
                "\"subjectTotal\":" + subjectTotal + ", " +
                "\"teacherTotal\":" + teacherTotal + ", " +
                "\"studentTotal\":" + studentTotal + "}";

        response.getWriter().print(json);
    }
}
