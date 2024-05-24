package com.taohan.online.exam.po;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <p>Title: ExamPlanInfo</p>
 * <p>Description: 考试安排记录</p>
 * 考试计划信息类，用于封装考试计划的相关信息
 */

@Component
@Scope("prototype")

public class ExamPlanInfo {

    private Integer examPlanId; // 考试计划ID
    private CourseInfo course; // 课程信息
    private ClassInfo clazz; // 班级信息
    private ExamPaperInfo examPaper; // 考试试卷信息
    private String beginTime; // 考试开始时间

    /**
     * 获取考试计划ID
     *
     * @return 考试计划的ID值
     */
    public Integer getExamPlanId() {
        return examPlanId;
    }

    /**
     * 设置考试计划ID
     *
     * @param examPlanId 要设置的考试计划ID
     */
    public void setExamPlanId(Integer examPlanId) {
        this.examPlanId = examPlanId;
    }

    /**
     * 获取课程信息
     *
     * @return 课程信息对象
     */
    public CourseInfo getCourse() {
        return course;
    }

    /**
     * 设置课程信息
     *
     * @param course 要设置的课程信息对象
     */
    public void setCourse(CourseInfo course) {
        this.course = course;
    }

    /**
     * 获取班级信息
     *
     * @return 班级信息对象
     */
    public ClassInfo getClazz() {
        return clazz;
    }

    /**
     * 设置班级信息
     *
     * @param clazz 要设置的班级信息对象
     */
    public void setClazz(ClassInfo clazz) {
        this.clazz = clazz;
    }

    /**
     * 获取考试试卷信息
     *
     * @return 考试试卷信息对象
     */
    public ExamPaperInfo getExamPaper() {
        return examPaper;
    }

    /**
     * 设置考试试卷信息
     *
     * @param examPaper 要设置的考试试卷信息对象
     */
    public void setExamPaper(ExamPaperInfo examPaper) {
        this.examPaper = examPaper;
    }

    /**
     * 获取考试开始时间
     *
     * @return 考试开始时间字符串
     */
    public String getBeginTime() {
        return beginTime;
    }

    /**
     * 设置考试开始时间
     *
     * @param beginTime 要设置的考试开始时间字符串
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }


    @Override
    public String toString() {
        return "ExamPlanInfo [examPlanId=" + examPlanId + ", course=" + course
                + ", clazz=" + clazz + ", examPaper=" + examPaper
                + ", beginTime=" + beginTime + "]";
    }

}
