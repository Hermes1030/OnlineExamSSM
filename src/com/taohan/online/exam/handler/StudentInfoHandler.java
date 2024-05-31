package com.taohan.online.exam.handler;

import com.taohan.online.exam.po.*;
import com.taohan.online.exam.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taohan.online.exam.test.test.getSimilarityRatio;

/**
 * <p>Title: StudentInfoHandler</p>
 * <p>Description: </p>
 */

@Controller
@SuppressWarnings("all")
public class StudentInfoHandler {

    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private ExamSubjectMiddleInfoService examSubjectMiddleInfoService;
    @Autowired
    private ExamHistoryPaperService examHistoryPaperService;
    @Autowired
    private ExamChooseInfoService examChooseInfoService;
    @Autowired
    private ExamSubjectMiddleInfo esm;
    @Autowired
    private ClassInfo classInfo;
    @Autowired
    private ExamPaperInfo examPaper;
    @Autowired
    private GradeInfo grade;
    @Autowired
    private StudentInfo student;

    @Autowired
    private ExamPaperInfoService examPaperInfoService;

    private Logger logger = Logger.getLogger(StudentInfoHandler.class);

    /**
     * 获取学生集合
     *
     * @param studentId 学生编号
     * @param classId   班级编号
     * @param gradeId   年级编号
     * @param startPage 起始页 default=1
     * @param pageShow  页容量 default=10
     * @return
     */
    @RequestMapping("/students")
    public ModelAndView getCourses(@RequestParam(value = "studentId", required = false) Integer studentId,
                                   @RequestParam(value = "classId", required = false) Integer classId,
                                   @RequestParam(value = "gradeId", required = false) Integer gradeId,
                                   @RequestParam(value = "startPage", required = false, defaultValue = "1") Integer startPage,
                                   @RequestParam(value = "pageShow", required = false, defaultValue = "10") Integer pageShow,
                                   HttpServletRequest request) {
        logger.info("获取学生集合  classId=" + classId + ", gradeId=" + gradeId + ", startPage=" + startPage + ", pageShow=" + pageShow);
        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/students");

        int isAdmin = (int) request.getSession().getAttribute("adminPower");
        if (isAdmin != 1) {
            classId = (int) request.getSession().getAttribute("classId");
        }
        //System.out.println(classId);

        //查询条件处理
        StudentInfo student = new StudentInfo();
        if (studentId != null)
            student.setStudentId(studentId);
        if (classId != null) {
            classInfo.setClassId(classId);
            student.setClassInfo(classInfo);
        }
        if (gradeId != null) {
            grade.setGradeId(gradeId);
            student.setGrade(grade);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        //计算当前查询起始数据索引
        int startIndex = (startPage - 1) * pageShow;
        map.put("student", student);
        map.put("startIndex", startIndex);
        map.put("pageShow", pageShow);

        List<StudentInfo> students = studentInfoService.getStudents(map);
        model.addObject("students", students);

        int studentTotal = 0;
        //获取学生总量
        if (isAdmin != 1) {
//            studentTotal = students.size();
            studentTotal = studentInfoService.getStudentTotalByClassId(classId);
        } else {
            studentTotal = studentInfoService.getStudentTotal();
        }
        //int studentTotal = students.size();
        //计算总页数
        int pageTotal = 1;
        if (studentTotal % pageShow == 0)
            pageTotal = studentTotal / pageShow;
        else
            pageTotal = studentTotal / pageShow + 1;
        model.addObject("pageTotal", pageTotal);
        model.addObject("pageNow", startPage);

        return model;
    }


    /**
     * 根据编号获取学生信息
     *
     * @param studentId
     * @return
     */
    @RequestMapping("/student/{studentId}")
    public ModelAndView getCourseById(@PathVariable("studentId") Integer studentId) {
        logger.info("获取学生 " + studentId);
        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/studentedit");

        StudentInfo student = studentInfoService.getStudentById(studentId);
        model.addObject("student", student);
        List<ClassInfo> classes = classInfoService.getClasses(null);
        model.addObject("classes", classes);

        return model;
    }

    /**
     * 添加或修改学生信息的操作。根据传入的isUpdate参数来判断是进行添加还是修改操作。
     *
     * @param studentId      学生ID, 如果是修改操作，则此ID用于定位要修改的学生信息；如果是添加操作，此ID可选。
     * @param isUpdate       操作标识，1表示修改，0或null表示添加。
     * @param studentName    学生姓名。
     * @param studentAccount 学生账号，唯一标识一个学生。
     * @param studentPwd     学生密码。
     * @param classId        所属班级ID。
     * @return 操作成功后，返回页面重定向信息，将页面重定向到学生列表页面。
     */
    @RequestMapping(value = "/student/student", method = RequestMethod.POST)
    public String isUpdateOrAddCourse(
            @RequestParam(value = "studentId", required = false) Integer studentId,
            @RequestParam(value = "isupdate", required = false) Integer isUpdate,
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam("studentAccount") String studentAccount,
            @RequestParam("studentPwd") String studentPwd,
            @RequestParam("classId") Integer classId) {
        // 初始化学生信息对象并设置参数
        StudentInfo student = new StudentInfo();
        student.setStudentId(studentId);
        student.setStudentName(studentName);
        student.setStudentAccount(studentAccount);
        student.setStudentPwd(studentPwd);
        // 设置班级信息
        classInfo.setClassId(classId);
        student.setClassInfo(classInfo);
        // 根据isUpdate参数判断是进行更新操作还是添加操作
        if (isUpdate != null) {
            logger.info("修改学生 " + student + " 的信息");
            // 执行更新操作
            int row = studentInfoService.isUpdateStudent(student);
        } else {
            logger.info("添加学生 " + student + " 的信息");
            // 执行添加操作
            int row = studentInfoService.isAddStudent(student);
        }
        // 操作成功后，重定向到学生列表页面
        return "redirect:/students";
    }


    /**
     * 删除学生
     *
     * @param studentId
     * @return
     */
    @RequestMapping(value = "/student/{studentId}", method = RequestMethod.DELETE)
    public String isDelTeacher(@PathVariable("studentId") Integer studentId) {
        logger.info("删除学生 " + studentId);
        int row = studentInfoService.isDelStudent(studentId);
        return "redirect:/students";
    }

    /**
     * 预添加学生
     *
     * @return
     */
    @RequestMapping("/preAddStudent")
    public ModelAndView preAddStudent() {
        logger.info("预添加学生信息");
        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/studentedit");
        List<ClassInfo> classes = classInfoService.getClasses(null);
        model.addObject("classes", classes);
        return model;
    }

    /**
     * 学生考试登录验证
     * 此处验证并不合理 登录验证实现如下：
     * 前台学生登录传入账户，后台根据账户获取学生密码
     * 返回学生密码，前台登录焦点离开密码框使用 JavaScript 判断
     *
     * @param studentAccount 学生登录账户
     * @param response
     * @throws IOException
     */
    @RequestMapping("/validateLoginStudent")
    public void validateLoginStudent(@RequestParam("studentAccount") String studentAccount, HttpServletResponse response) throws IOException {
        logger.info("学生账户 " + studentAccount + "，尝试登录考试");
        //获取需要登录的学生对象
        StudentInfo student = studentInfoService.getStudentByAccountAndPwd(studentAccount);
        if (student == null) {
            logger.error("登录学生账户 " + studentAccount + " 不存在");
            response.getWriter().print("n");
        } else {
            logger.error("登录学生账户 " + studentAccount + " 存在");
            response.getWriter().print(student.getStudentPwd());
        }
    }

    /**
     * 学生登录考试
     *
     * @param student 登录学生
     * @param request
     * @return
     */
    @RequestMapping(value = "/studentLogin", method = RequestMethod.POST)
    public ModelAndView studentLogin(StudentInfo student, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        StudentInfo loginStudent = studentInfoService.getStudentByAccountAndPwd(student.getStudentAccount());
        logger.info("学生 " + loginStudent + " 有效登录");
        if (loginStudent == null || !student.getStudentPwd().equals(loginStudent.getStudentPwd())) {
            model.setViewName("reception/suc");
            model.addObject("success", "密码错误");
            return model;
        }
        request.getSession().setAttribute("loginStudent", loginStudent);
        model.setViewName("reception/suc");
        model.addObject("success", "Login success");
        return model;
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping("/exit")
    public String studentClearLogin(HttpSession session) {
        StudentInfo studnet = (StudentInfo) session.getAttribute("loginStudent");
        logger.info("学生 " + studnet.getStudentName() + ", 编号 " + studnet.getStudentId() + " 退出登录");
        session.removeAttribute("loginStudent");

        return "redirect:index.jsp";
    }

    /**
     * 学生注册 验证当前账户是否被占用
     *
     * @param studentAccount 注册账户
     * @param response
     * @throws IOException
     */
    @RequestMapping("/validateAccount")
    public void validateRegisterAccount(@RequestParam("studentAccount") String studentAccount,
                                        HttpServletResponse response) throws IOException {
        logger.info("验证学生账户 " + studentAccount + "，是否已被注册");

        StudentInfo student = studentInfoService.getStudentByAccountAndPwd(studentAccount);

        if (student == null) {
            logger.error("注册学生账户 " + studentAccount + " 可以注册");
            response.getWriter().print("t");
        } else {
            logger.error("注册学生账户 " + studentAccount + " 已被注册");
            response.getWriter().print("f");
        }
    }

    /**
     * 学生注册
     *
     * @param studentName
     * @param studentAccount
     * @param studentPwd
     * @param classId
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/studentReg", method = RequestMethod.POST)
    public void studentRegister(
            @RequestParam("name") String studentName,
            @RequestParam("account") String studentAccount,
            @RequestParam("pwd") String studentPwd,
            @RequestParam("classId") Integer classId,
            HttpServletResponse response) throws IOException {
        ModelAndView model = new ModelAndView();
        student.setStudentName(studentName);
        student.setStudentAccount(studentAccount);
        student.setStudentPwd(studentPwd);
        classInfo.setClassId(classId);
        student.setClassInfo(classInfo);
        logger.info("学生注册 " + student);
        int row = studentInfoService.isAddStudent(student);

        response.getWriter().print("t");
    }

    /**
     * 预注册
     *
     * @return
     */
    @RequestMapping("/preStudentReg")
    public ModelAndView preStudentReg() {
        ModelAndView model = new ModelAndView();
        model.setViewName("reception/register");
        model.addObject("classs", classInfoService.getClasses(null));
        return model;
    }

    /**
     * 处理学生进入考试的请求。
     * 根据提供的班级编号、试卷编号、考生编号等信息，判断学生是否可以进入考试，并返回相应的考试界面或错误信息。
     *
     * @param classId     班级编号，用于指定考试的班级。
     * @param examPaperId 试卷编号，用于指定学生要参加的考试试卷。
     * @param studentId   考生编号，可选参数，用于指定参加考试的学生。如果未提供，则默认为会话中的登录学生。
     * @param examTime    考试时间，用于指定考试的总时长。
     * @param beginTime   考试开始时间，字符串格式，用于记录考试的开始时间。
     * @param gradeId     年级编号，用于指定考试所属的年级。
     * @param session     HttpSession对象，用于获取会话信息，如登录学生信息。
     * @return 返回一个ModelAndView对象， either redirect to the exam interface or an error page.
     */
    @RequestMapping("/begin")
    public ModelAndView beginExam(
            @RequestParam("classId") Integer classId,
            @RequestParam("examPaperId") Integer examPaperId,
            @RequestParam(value = "studentId", required = false) Integer studentId,
            @RequestParam("examTime") Integer examTime,
            @RequestParam("beginTime") String beginTime,
            @RequestParam("gradeId") Integer gradeId,
            HttpSession session) {
        ModelAndView model = new ModelAndView();

        // 检查该学生是否已经对该试卷有考试记录，如果有则不能再次进入考试。
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("studentId", studentId);
        map.put("examPaperId", examPaperId);
        int count = examHistoryPaperService.getHistoryInfoWithIds(map);
        if (session.getAttribute("loginStudent") == null) {
            // 如果学生未登录，返回错误页面。
            model.addObject("error", "请先登录后再操作");
            model.setViewName("error");
            return model;
        } else if (count >= 1) {
            // 如果该学生已经对该试卷有考试记录，返回错误页面。
            model.addObject("error", "你已经考试过了");
            model.setViewName("error");
            return model;
        } else {
            // 如果学生未登录考试，且未对该试卷考试过，则允许进入考试。
            logger.info("学生 " + studentId + " 进入考试 班级 " + classId + " 试卷 " + examPaperId);
            model.setViewName("/reception/exam");

            // 设置试卷信息并获取试卷试题。
            ExamPaperInfo examPaper = new ExamPaperInfo();
            examPaper.setExamPaperId(examPaperId);
            esm.setExamPaper(examPaper);
            List<ExamSubjectMiddleInfo> esms = examSubjectMiddleInfoService.getExamPaperWithSubject(esm);
            logger.info("考试试题总量 " + esms.size());

            // 获取该学生在本次考试中已选择的答案。
            Map<String, Object> choosedMap = new HashMap<String, Object>();
            choosedMap.put("studentId", studentId);
            choosedMap.put("examPaperId", examPaperId);
            List<ExamChooseInfo> chooses = examChooseInfoService.getChooseInfoWithSumScore(choosedMap);

            if (chooses == null || chooses.size() == 0) {
                model.addObject("chooses", null);
            } else {
                model.addObject("chooses", chooses);
            }

            // 向模型添加考试相关数据，用于在考试界面中显示。
            model.addObject("esms", esms);
            model.addObject("sumSubject", esms.size());
            model.addObject("examPaperId", examPaperId);
            model.addObject("examTime", examTime);
            model.addObject("beginTime", beginTime);
            model.addObject("classId", classId);
            model.addObject("gradeId", gradeId);
            return model;
        }
    }


    /**
     * 获取学生历史考试记录
     *
     * @param studentId 学生编号
     * @return
     */
    @RequestMapping("/history/{studentId}")
    public ModelAndView getExamHistoryInfo(@PathVariable("studentId") Integer studentId) {
        ModelAndView model = new ModelAndView();
        if (studentId == null) {
            logger.error("学生编号 为空");
            model.setViewName("error");
            return model;
        }
        logger.info("学生 " + studentId + " 获取考试历史记录");
        //获取历史考试信息记录集合
        List<ExamHistoryPaper> ehps = examHistoryPaperService.getExamHistoryToStudent(studentId);
        model.addObject("ehps", ehps);
        model.setViewName("/reception/examHistory");
        return model;
    }


    /**
     * 处理考生提交的考试。
     * 根据提供的学生ID、试卷ID、班级ID和年级ID，记录学生的考试成绩。
     * 首先，检索该学生针对该试卷的所有答题信息，并计算总分。
     * 然后，检查该考试记录是否已经存在于历史记录中，如果没有，则将总分和相关信息添加到考试历史记录中。
     * 最后，重定向到考试列表页面。
     *
     * @param studentId   学生的唯一标识符。
     * @param examPaperId 试卷的唯一标识符。
     * @param classId     班级的唯一标识符。
     * @param gradeId     年级的唯一标识符。
     * @return 重定向到考试列表页面的URL。
     */
    @RequestMapping(value = "/submit", method = {RequestMethod.POST, RequestMethod.GET})
    public String examSubmit(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("examPaperId") Integer examPaperId,
            @RequestParam("classId") Integer classId,
            @RequestParam("gradeId") Integer gradeId) {
        logger.info("学生 " + studentId + " 提交了试卷 " + examPaperId);
        // 获取学生选择的答案并计算总分
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("studentId", studentId);
        map.put("examPaperId", examPaperId);
        List<ExamChooseInfo> chooses = examChooseInfoService.getChooseInfoWithSumScore(map);
        logger.info("学生 " + studentId + " 共选择了 " + chooses.size() + " 道题");
        // 计算答题总分
        int sumScore = 0;
        for (ExamChooseInfo choose : chooses) {
            SubjectInfo subject = choose.getSubject();
            String chooseResult = choose.getChooseResult();
            String rightResult = subject.getRightResult();
//            // 判断选择题和多选题的正确答案
            if (subject.getSubjectType() == 1 || subject.getSubjectType() == 0) {
                if (chooseResult.equals(rightResult)) {
                    sumScore += subject.getSubjectScore();
                    logger.info("学生 " + studentId + " 第 " + subject.getSubjectId() + " 选择正确答案 " + chooseResult + " 当前总分 " + sumScore);
                } else {
                    logger.info("学生 " + studentId + " 第 " + subject.getSubjectId() + " 答案选择错误 " + chooseResult + " 正确答案为 " + rightResult + " 当前总分 " + sumScore);
                }
            }
            //判断简答题的正确答案
            //根据获取的答案和正确答案的相似度，计算出分数
            if (subject.getSubjectType() == 2) {
                int socore = (int) getSimilarityRatio(chooseResult, rightResult);
                logger.info("获得简答题的答案相关性："+socore);
                if (socore > 50) {
                    sumScore = sumScore + (int)(subject.getSubjectScore() * (socore * 0.01));
                    logger.info("学生 " + studentId + " 第 " + subject.getSubjectId() + " 简答正确答案 " + chooseResult + " 当前总分 " + sumScore);
                } else {
                    logger.info("学生 " + studentId + " 第 " + subject.getSubjectId() + " 简答答案错误 " + chooseResult + " 正确答案为 " + rightResult + " 当前总分 " + sumScore);
                }
            }
//            if (chooseResult.equals(rightResult)) {    // 答案正确
//                sumScore += subject.getSubjectScore();
//                logger.info("学生 " + studentId + " 第 " + subject.getSubjectId() + " 选择正确答案 " + chooseResult + " 当前总分 " + sumScore);
//            } else {
//                logger.info("学生 " + studentId + " 第 " + subject.getSubjectId() + " 答案选择错误 " + chooseResult + " 正确答案为 " + rightResult + " 当前总分 " + sumScore);
//            }
        }
        // 检查是否已存在考试记录，不存在则添加到历史记录
        int count = examHistoryPaperService.getHistoryInfoWithIds(map);
        if (count == 0) {
            // 添加考试记录
            map.put("examScore", sumScore);
            int row = examHistoryPaperService.isAddExamHistory(map);
            logger.info("学生 " + studentId + " 提交的试卷 " + examPaperId + " 已成功处理，并添加到历史记录中");
        }
        // 重定向到考试列表页面
        return "redirect:willexams?gradeId=" + gradeId + "&classId=" + classId + "&studentId=" + studentId;
    }


    /**
     * 学生回顾试卷的功能实现 -- 后台教师查看也调用此方法
     * 该方法用于处理学生回顾试卷的请求，会根据提供的试卷信息和学生信息，重新显示试卷内容和学生作答情况。
     * 如果是教师查看，则会额外传入学生姓名。
     *
     * @param studentId     学生ID，用于识别回顾试卷的学生。
     * @param examPaperId   试卷ID，用于获取特定的试卷内容。
     * @param score         学生在该试卷上的得分。
     * @param examPaperName 试卷名称。
     * @param studentName   学生姓名，可选参数，用于后台教师查看时显示学生姓名。
     * @return 返回一个ModelAndView对象，其中包含了渲染页面所需的所有信息。
     * @throws UnsupportedEncodingException 如果在处理过程中遇到编码不支持的情况，则抛出此异常。
     */
    @RequestMapping("/review")
    public ModelAndView reViewExam(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("examPaperId") Integer examPaperId,
            @RequestParam("score") Integer score,
            @RequestParam("examPaperName") String examPaperName,
            @RequestParam(value = "studentName", required = false) String studentName) throws UnsupportedEncodingException {
        // 初始化ModelAndView对象，用于存储数据和指定视图
        ModelAndView model = new ModelAndView();
        // 检查学生ID是否为空，若为空则重定向到错误页面
        if (studentId == null) {
            model.addObject("error", "请先登录后再操作");
            model.setViewName("error");
            return model;
        } else {
            // 设置当前试卷信息，并获取该试卷的所有试题
            examPaper.setExamPaperId(examPaperId);
            esm.setExamPaper(examPaper);
            List<ExamSubjectMiddleInfo> esms = examSubjectMiddleInfoService.getExamPaperWithSubject(esm);
            // 准备回顾试卷所需的数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("studentId", studentId);
            map.put("examPaperId", examPaperId);
            List<ExamChooseInfo> reviews = examChooseInfoService.getChooseInfoWithExamSubject(map);
            logger.info("学生 " + studentId + " 回顾试卷 " + examPaperId + " 试题数量 " + reviews.size());
            // 添加试卷名称、得分、试题等信息到ModelAndView中，以供页面渲染使用
            model.addObject("examPaperName", examPaperName);
            model.addObject("score", score);
            model.setViewName("reception/review");
            model.addObject("views", reviews);
            model.addObject("esms", esms);
            if (studentName != null) model.addObject("studentName", studentName);
            model.addObject("ExamedPaper", examPaperInfoService.getExamPaper(examPaperId));
            return model;
        }
    }


    /**
     * 学生查看自己信息的控制器方法。
     * 通过学生的ID从服务层获取该学生的信息，并将信息传递给前端页面进行展示。
     *
     * @param studentId 学生的唯一标识符，从URL路径变量中获取。
     * @return 返回一个ModelAndView对象，指定了视图名称和要传递给视图的数据。
     */
    @RequestMapping("/self/{studentId}")
    public ModelAndView selfInfo(@PathVariable("studentId") Integer studentId) {
        // 从服务层根据学生ID获取学生信息
        StudentInfo stu = studentInfoService.getStudentById(studentId);
        ModelAndView model = new ModelAndView();
        // 设置视图名称
        model.setViewName("/reception/self");
        // 向模型中添加数据，用于前端页面展示
        model.addObject("self", stu);
        return model;
    }


    /**
     * 学生修改密码
     *
     * @param pwd
     * @param studentId
     * @param response
     * @throws IOException
     */
    @RequestMapping("/reset/{pwd}/{studentId}")
    public void isResetPwd(
            @PathVariable("pwd") String pwd,
            @PathVariable("studentId") Integer studentId,
            HttpServletResponse response) throws IOException {
        logger.info("学生 " + studentId + " 修改密码");
        student.setStudentId(studentId);
        student.setStudentPwd(pwd);
        int row = studentInfoService.isResetPwdWithStu(student);
        if (row > 0) {
            response.getWriter().print("t");
        } else {
            response.getWriter().print("f");
        }
    }
}