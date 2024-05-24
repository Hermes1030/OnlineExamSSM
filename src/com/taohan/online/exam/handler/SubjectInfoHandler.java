package com.taohan.online.exam.handler;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.taohan.online.exam.po.*;
import org.apache.commons.fileupload.*;

import org.apache.log4j.Logger;
import org.aspectj.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import th.uploadeasy.SaveFileDispose;

import com.taohan.online.exam.service.CourseInfoService;
import com.taohan.online.exam.service.ExamPaperInfoService;
import com.taohan.online.exam.service.ExamSubjectMiddleInfoService;
import com.taohan.online.exam.service.GradeInfoService;
import com.taohan.online.exam.service.SubjectInfoService;
import com.taohan.online.exam.util.SubjectImportUtil;

/**
 * <p>Title: SubjectInfoHandler</p>
 * <p>Description: 试题</p>
 */

@Controller
@SuppressWarnings("all")
public class SubjectInfoHandler {

    @Autowired
    private SubjectInfoService subjectInfoService;
    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private GradeInfoService gradeInfoService;
    @Autowired
    private ExamPaperInfoService examPaperInfoService;
    @Autowired
    private ExamSubjectMiddleInfoService esmService;
    @Autowired
    private SubjectInfo subject;
    @Autowired
    private CourseInfo course;
    @Autowired
    private GradeInfo grade;
    @Autowired
    private ExamPaperInfo examPaper;

    private Logger logger = Logger.getLogger(SubjectInfoHandler.class);


    /**
     * 获取教师相关的试题信息。
     *
     * @param subjectId   试题主题ID，可选参数。
     * @param gradeId     年级ID，可选参数。
     * @param handAdd     是否手动添加试题标记，可选参数。
     * @param examPaperId 试卷ID，用于手动添加试题到试卷的场景，可选参数。
     * @param subjectEasy 试题难度，可选参数。
     * @param subjectType 试题类型，单选，多选，简答
     * @param courseId    课程ID，可选参数。
     * @param startPage   查询起始页码，默认为1。
     * @param pageShow    每页显示数量，默认为10。
     * @param session     用户会话。
     * @return 返回包含试题信息的ModelAndView对象。
     */
    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public ModelAndView getTeachers(
            @RequestParam(value = "subjectId", required = false) Integer subjectId,
            @RequestParam(value = "gradeId", required = false) Integer gradeId,
            @RequestParam(value = "handAdd", required = false) Integer handAdd,
            @RequestParam(value = "examPaperId", required = false) Integer examPaperId,
            @RequestParam(value = "subjectEasy", required = false) Integer subjectEasy,
            @RequestParam(value = "subjectType", required = false) Integer subjectType,
            @RequestParam(value = "courseId", required = false) Integer courseId,
            @RequestParam(value = "startPage", required = false, defaultValue = "1") Integer startPage,
            @RequestParam(value = "pageShow", required = false, defaultValue = "10") Integer pageShow,
            HttpSession session) {
        logger.info("查询试题集合请求参数：" + "sbujectEasy:[" + subjectEasy + "]," + "subjectType:[" + subjectType + "]," + "courseID:[" + courseId + "]");
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/subjects");

        // 设置查询条件
        if (subjectId != null) subject.setSubjectId(subjectId);
        if (gradeId != null) {
            grade.setGradeId(gradeId);
            subject.setGrade(grade);
        }
        if (subjectEasy != null) subject.setSubjectEasy(subjectEasy);
        if (subjectType != null) subject.setSubjectType(subjectType);
        if (courseId != null) {
            course.setCourseId(courseId);
            subject.setCourse(course);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        // 计算分页查询的起始索引
        int startIndex = (startPage - 1) * pageShow;
        map.put("subject", subject);
        map.put("startIndex", startIndex);
        map.put("pageShow", pageShow);

        // 获取满足条件的试题列表
        List<SubjectInfo> subjects = subjectInfoService.getSubjects(map);
        logger.info("查询到的试题数量：" + subjects.size());
        model.addObject("subjects", subjects);

        // 获取试题的科目
        List<CourseInfo> courses = courseInfoService.getCourses(course);
        model.addObject("courses", courses);

        // 获取年级
        List<GradeInfo> grades = gradeInfoService.getGrades();
        model.addObject("grades", grades);

        // 获取试题总数量，计算总页数
        int subjectTotal = subjectInfoService.getSubjectTotal();
        int pageTotal = subjectTotal % pageShow == 0 ? subjectTotal / pageShow : subjectTotal / pageShow + 1;
        model.addObject("pageTotal", pageTotal);
        model.addObject("pageNow", startPage);

        // 手动添加试题到试卷的逻辑处理
        if (handAdd != null && handAdd == 1) {
            model.addObject("handAdd", "1");
        }

        // 处理选择试题添加到试卷的逻辑
        if (examPaperId != null) {
            model.addObject("examPaperId", examPaperId);
            List<String> ids = (List<String>) session.getAttribute("ids");
            model.addObject("choosed", ids == null ? 0 : ids.size());
        }
        return model;
    }


    /**
     * 添加试题的请求处理方法。
     * @param subject  包含试题信息的对象，其中包含了试题的各种属性，如题目名称、正确答案等。
     * @param response 用于向客户端发送响应的HttpServletResponse对象。
     * @throws IOException 如果发生IO异常。
     */
    @RequestMapping(value = "/addSubject", method = RequestMethod.POST)
    public void addSubject(SubjectInfo subject, HttpServletResponse response) throws IOException {
        // 验证试题对象是否不为空，并对其中的字符串属性进行去空格处理
        if (subject != null) {
            subject.setSubjectName(trimChar(subject.getSubjectName()));
            subject.setRightResult(trimChar(subject.getRightResult()));
            subject.setOptionA(trimChar(subject.getOptionA()));
            subject.setOptionB(trimChar(subject.getOptionB()));
            subject.setOptionC(trimChar(subject.getOptionC()));
            subject.setOptionD(trimChar(subject.getOptionD()));
        }
        // 调用服务层方法，检查并添加试题，返回添加结果
        int row = subjectInfoService.isAddSubject(subject);
        // 向客户端响应添加结果
        response.getWriter().print("试题添加成功!");
    }

    /**
     * 删除试题
     * @param subjectId
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delSubject", method = RequestMethod.POST)
    public void delSubject(@RequestParam("subjectId") Integer subjectId,
                           HttpServletResponse response) throws IOException {
        logger.info("删除试题 " + subjectId);
        int row = subjectInfoService.isDelSubject(subjectId);
        if (row > 0) {
            response.getWriter().print("t");
        } else {
            response.getWriter().print("f");
        }
    }


    /**
     * 修改试题 -- 获取待修改试题信息
     * @param subjectId
     * @return
     */
    @RequestMapping("/subject/{subjectId}")
    public ModelAndView updateSubject(@PathVariable("subjectId") Integer subjectId) {
        logger.info("修改试题 " + subjectId + " 的信息(获取试题信息)");
        SubjectInfo subject = subjectInfoService.getSubjectWithId(subjectId);
        ModelAndView model = new ModelAndView("/admin/subject-test");
        model.addObject("subject", subject);
        List<GradeInfo> grades = gradeInfoService.getGrades();
        model.addObject("grades", grades);
        model.addObject("courses", courseInfoService.getCourses(null));
        return model;
    }

    /**
     * 修改试题
     * @param subject
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/updateSubject", method = RequestMethod.POST)
    public void updateSubject(SubjectInfo subject, HttpServletResponse response) throws IOException {
        logger.info("修改试题 " + subject.getSubjectId() + " 的信息(正式)");
        if (subject != null) {
            subject.setSubjectName(trimChar(subject.getSubjectName()));
            subject.setRightResult(trimChar(subject.getRightResult()));
            subject.setOptionA(trimChar(subject.getOptionA()));
            subject.setOptionB(trimChar(subject.getOptionB()));
            subject.setOptionC(trimChar(subject.getOptionC()));
            subject.setOptionD(trimChar(subject.getOptionD()));
        }
        int row = subjectInfoService.isUpdateSubject(subject);
        if (row > 0) {
            response.getWriter().print("试题修改成功!");
        } else {
            response.getWriter().print("试题修改失败!");
        }
    }


    /**
     * 初始化 导入 excel 试题信息
     *
     * @return
     */
    @RequestMapping(value = "/initImport")
    public ModelAndView initImportExcel() {
        logger.info("初始化 导入 EXCEL 试题信息");
        ModelAndView model = new ModelAndView("admin/importSubject");
        //获取所有科目
        List<CourseInfo> courses = courseInfoService.getCourses(null);
        //获取所有年级
        List<GradeInfo> grades = gradeInfoService.getGrades();
        //获取所有试卷名称
        List<ExamPaperInfo> examPapers = examPaperInfoService.getExamPapersClear();
        model.addObject("courses", courses);
        model.addObject("grades", grades);
//        model.addObject("examPapers", examPapers);
        return model;
    }


    /**
     * 试题导入 处理
     * @param request
     * @param importOption
     * @param excel
     */
    @RequestMapping(value = "/dispatcherUpload", method = RequestMethod.POST)
    public ModelAndView dispatcherUpload(HttpServletRequest request,
                                         @RequestParam("division") Integer division,
                                         @RequestParam("courseId") Integer courseId,
                                         @RequestParam("gradeId") Integer gradeId,
                                         @RequestParam(value = "examPaperId",required = false) Integer examPaperId,
                                         @RequestParam(value ="importOption") String importOption,
                                         @RequestParam(value ="examPaperEasy",required = false) Integer examPaperEasy,
                                         @RequestParam(value ="examPaperName",required = false) String examPaperName,
                                         @RequestParam(value ="examPaperTime",required = false) Integer examPaperTime,
                                         @RequestParam("inputfile") MultipartFile excel) {
        ModelAndView model = new ModelAndView("admin/subjects");
//        ModelAndView model = new ModelAndView("reception/suc");
        String savePath = "";

        try {
            /** 保存上传 excel 文件 */
            // 在 /WEB-INF/ 目录下 创建文件夹 upload 如果不存在的话
            if (!new File(request.getRealPath("/WEB-INF/upload")).exists()) {
                new File(request.getRealPath("/WEB-INF/upload")).mkdir();
            }
            savePath = saveUploadFile(excel, request.getRealPath("/WEB-INF/upload"));
            /** 解析上传 excel 文件, 得到试题集合 */
            List<SubjectInfo> subjects = SubjectImportUtil.parseSubjectExcel(savePath, courseId, gradeId, division);
            /** 只添加试题 */
            if ("0".equals(importOption)) {
                /**
                 * 创建一个用于存储主题信息的Map，并将主题数据添加到该Map中。
                 * 然后调用importSubejctOnly方法，传入主题数据和Map对象。
                 * @param subjects 主题数据集合，通常包含多个主题信息。
                 * @param subjectsMap 用于存储主题信息的Map对象，其中键为"subjects"，值为主题数据集合。
                 */
                Map<String, Object> subjectsMap = new HashMap<String, Object>();
                subjectsMap.put("subjects", subjects);
                importSubejctOnly(subjects, subjectsMap);
            }
//            /** 添加试题到指定的已有试卷 */
//            else if ("1".equals(importOption)) {
//                dispatcherExamPaperAndSubject(subjects, examPaperId);
//            }
//            /** 添加试题到新建试卷 */
//            else if ("2".equals(importOption)) {
//                /** 创建新试卷 */
//                examPaper.setExamPaperName(examPaperName);
//                examPaper.setExamPaperEasy(examPaperEasy);
//                examPaper.setExamPaperTime(examPaperTime);
//                grade.setGradeId(gradeId);
//                examPaper.setGrade(grade);
//                examPaper.setDivision(division);
//                int row = examPaperInfoService.isAddExamPaper(examPaper);
//                logger.info("添加的新试卷 编号 " + examPaper.getExamPaperId());
//                dispatcherNewExamPaperAndSubject(subjects, examPaper.getExamPaperId());
//            }
            if (subjects.size() == 0) {
                model.addObject("success", "操作处理失败，共添加 <b style='color:red;'>" + subjects.size() + "</b> 道题, 请检查上传数据正确性!");
            } else {
                model.addObject("success", "操作处理成功，文件共解析到 " + subjects.size() + " 道题");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.setViewName("error");
            model.addObject("error", "上传失败, 请检查上传数据合理性或联系管理员!");
        } finally {
            /** 删除上传文件 */
            savePath = savePath.replace("/WEB-INF/upload/", "\\WEB-INF\\upload\\");
            deleteUploadFile(savePath);
        }
        return model;
    }

    /**
     * 保存上传 excel 文件
     *
     * @param file 上传文件
     * @return 保存路径
     */
    private String saveUploadFile(MultipartFile file, String rootPath) {
        String fileName = file.getOriginalFilename();
        logger.info("保存上传文件 " + fileName + " 到 " + rootPath);

        try {
            file.transferTo(new File(rootPath + "/" + fileName));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootPath + "/" + fileName;
    }


    /**
     * 只将试题上传到数据库
     *
     * @param subjects
     * @param subjectsMap
     */
    private void importSubejctOnly(List<SubjectInfo> subjects, Map<String, Object> subjectsMap) {
        try {
            if (subjects != null && subjects.size() > 0) {
                int sum = 0;
                //添加试题
                for (SubjectInfo subjectInfo : subjects) {
                    int count = subjectInfoService.getCountBySubjectName(subjectInfo.getSubjectName());
                    if (count != 0) {
                        continue;
                    }
                    int row = subjectInfoService.isAddSubject(subjectInfo);
                    sum += row;
                }
                //int row = subjectInfoService.isAddSubjects(subjectsMap);
                logger.info("只将 excel 中的试题添加到数据库成功 SIZE " + sum);
            } else {
                logger.info("上传试题文件中不存在试题，或解析失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理 试题 添加到 试卷
     * @param subjects    试题集合
     * @param examPaperId 对应试卷编号
     */
    private void dispatcherExamPaperAndSubject(List<SubjectInfo> subjects, Integer examPaperId) {
        //试题总量统计
        int count = 0;
        //试题总分统计
        int score = 0;
        /** 添加试题 */
        for (SubjectInfo subjectInfo : subjects) {
            int num = subjectInfoService.getCountBySubjectName(subjectInfo.getSubjectName());
            if (num != 0) {
                continue;
            }
            int row1 = subjectInfoService.isAddSubject(subjectInfo);
            score += subjectInfo.getSubjectScore();
            count++;
        }
        logger.info("添加试题 SIZE " + count);
        /** 判断是否添加试题到试卷 */
        List<Integer> subjectIds = new ArrayList<Integer>();
        for (SubjectInfo subjectInfo : subjects) {
            SubjectInfo bySubjectName = subjectInfoService.getBySubjectName(subjectInfo.getSubjectName());
            int isExist = esmService.getCount(examPaperId, bySubjectName.getSubjectId());
            if (isExist != 0) {
                continue;
            }
            subjectIds.add(subjectInfo.getSubjectId());
        }
        if (CollectionUtils.isEmpty(subjectIds)) {
            return;
        }
        Map<String, Object> esmMap = new HashMap<String, Object>();
        esmMap.put("examPaperId", examPaperId);
        esmMap.put("subjectIds", subjectIds);

        esmService.isAddESM(esmMap);
        logger.info("添加试题 SIZE " + count + " SCORE " + score + " 到试卷 " + examPaperId);
        //修改试卷信息
        ExamPaperInfo updateExam = examPaperInfoService.getExamPaper(examPaperId);
        Map<String, Object> scoreWithNum = new HashMap<String, Object>();
        scoreWithNum.put("subjectNum", updateExam.getSubjectNum() + count);
        scoreWithNum.put("score", updateExam.getExamPaperScore() + score);
        scoreWithNum.put("examPaperId", examPaperId);
        /** 修改试卷总分 */
        examPaperInfoService.isUpdateExamPaperScore(scoreWithNum);
        /** 修改试卷试题总量 */
        examPaperInfoService.isUpdateExamPaperSubjects(scoreWithNum);
    }

    /**
     * 处理将试题添加到试卷的操作
     * @param subjects    待添加的试题集合
     * @param examPaperId 目标试卷的编号
     */
    private void dispatcherNewExamPaperAndSubject(List<SubjectInfo> subjects, Integer examPaperId) {
        // 初始化试题数量和总分统计
        int count = 0; // 用于记录成功添加的试题数量
        int score = 0; // 用于累计所有试题的总分
        // 遍历试题集合，判断是否已存在相同的试题，若不存在则尝试添加到试题库
        for (SubjectInfo subjectInfo : subjects) {
            int num = subjectInfoService.getCountBySubjectName(subjectInfo.getSubjectName());
            if (num != 0) {
                continue; // 如果试题已存在，则跳过当前试题的处理
            }
            // 尝试添加试题到试题库，成功计数加一
            int row1 = subjectInfoService.isAddSubject(subjectInfo);
            count++;
        }
        // 记录添加的试题数量
        logger.info("添加试题 SIZE " + count);

        // 统计所有试题的总分并收集试题ID
        List<Integer> subjectIds = new ArrayList<>();
        for (SubjectInfo subjectInfo : subjects) {
            score += subjectInfo.getSubjectScore(); // 累计试题总分
            subjectIds.add(subjectInfo.getSubjectId()); // 收集试题ID
        }
        // 准备数据，将试题ID集合添加到试卷
        Map<String, Object> esmMap = new HashMap<String, Object>();
        esmMap.put("examPaperId", examPaperId);
        esmMap.put("subjectIds", subjectIds);
        esmService.isAddESM(esmMap); // 调用服务，添加试题集合到试卷
        // 准备数据，更新试卷的总分和试题数量
        Map<String, Object> scoreWithNum = new HashMap<String, Object>();
        scoreWithNum.put("subjectNum", subjects.size()); // 试题数量
        scoreWithNum.put("score", score); // 试题总分
        scoreWithNum.put("examPaperId", examPaperId); // 试卷编号
        // 更新试卷的总分信息
        examPaperInfoService.isUpdateExamPaperScore(scoreWithNum);
        // 更新试卷的试题数量信息
        examPaperInfoService.isUpdateExamPaperSubjects(scoreWithNum);
    }


    /**
     * 删除指定路径的上传文件。
     * <p>此方法会检查文件是否存在，如果存在则删除该文件，并记录日志信息。</p>
     *
     * @param filePath 要删除的文件的路径。
     */
    private void deleteUploadFile(String filePath) {
        // 根据路径创建File对象
        File file = new File(filePath);

        // 检查文件是否存在，存在则删除
        if (file.exists()) {
            file.delete(); // 删除文件
            logger.info("上传文件已被删除 " + filePath); // 记录删除成功的日志
        }
    }


    /**
     * 预添加试题
     *
     * @return
     */
    @RequestMapping("/preAddSubject")
    public ModelAndView preAddStudent() {
        logger.info("预添加试卷信息");
        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/subject-test");
        List<GradeInfo> grades = gradeInfoService.getGrades();
        model.addObject("grades", grades);
        model.addObject("courses", courseInfoService.getCourses(null));
        return model;
    }

    private String trimChar(String str) {
        if (str != null) {
            return str.replaceAll("^,*|,*$", "");
        }
        return str;
    }

}
