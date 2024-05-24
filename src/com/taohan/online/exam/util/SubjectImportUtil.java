package com.taohan.online.exam.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.taohan.online.exam.po.CourseInfo;
import com.taohan.online.exam.po.GradeInfo;
import com.taohan.online.exam.po.SubjectInfo;

/**
 * <p>Title: SubjectImportUtil</p>
 * <p>Description: 试题文件导入工具类</p>
 */


/**
 * 此注释适用于忽略所有警告。通常用于当代码为了特定目的而违反了某些编码规范，但开发者确认这是有意为之的情况下。
 * 并没有参数和返回值，因为它是一个抑制警告的注释，应用于代码块或方法前。
 */
@SuppressWarnings("all")

public class SubjectImportUtil {
    private static int subjectNameIndex;
    private static int optionAIndex;
    private static int optionBIndex;
    private static int optionCIndex;
    private static int optionDIndex;
    private static int rightResultIndex;
    private static int subjectScoreIndex;
    private static int subjectTypeIndex;
    private static int subjectEasyIndex;

    /**
     * 解析Excel文件获取题目信息
     *
     * @param filePath 需要解析的Excel文件路径
     * @param courseId 课程ID
     * @param gradeId  年级ID
     * @param division 分组ID
     * @return 返回包含所有题目信息的列表
     */
    public static List<SubjectInfo> parseSubjectExcel(String filePath, Integer courseId, Integer gradeId, Integer division) {
        List<SubjectInfo> subjects = new LinkedList<SubjectInfo>();
        try {
            // 加载Excel文件
            XSSFWorkbook workBook = new XSSFWorkbook(filePath);
            // 获取第一个工作表
            XSSFSheet sheet = workBook.getSheet("Sheet1");
            // 计算总行数
            int sumRow = sheet.getLastRowNum() - sheet.getFirstRowNum();

            // 处理Excel表头，获取列索引
            XSSFRow firstRow = sheet.getRow(0);
            getCellIndexs(firstRow);

            // 遍历每一行，解析题目信息
            for (int i = 1; i <= sumRow; i++) {
                XSSFRow row = (XSSFRow) sheet.getRow(i);
                XSSFCell subjectName = row.getCell(subjectNameIndex);
                XSSFCell optionA = row.getCell(optionAIndex);
                XSSFCell optionB = row.getCell(optionBIndex);
                XSSFCell optionC = row.getCell(optionCIndex);
                XSSFCell optionD = row.getCell(optionDIndex);
                XSSFCell rightResult = row.getCell(rightResultIndex);
                XSSFCell subjectScore = row.getCell(subjectScoreIndex);
                // 处理分数列为数字类型
                if (subjectScore.getCellType() == 0) {
                    subjectScore.setCellType(1);
                }
                XSSFCell subjectType = row.getCell(subjectTypeIndex);
                XSSFCell subjectEasy = row.getCell(subjectEasyIndex);

                SubjectInfo subject = new SubjectInfo();
                // 设置题目各项属性

                // 如果题目为空则跳过这次循环
                if (subjectName == null || subjectName.toString().isEmpty()) {
                    continue;
                }else{
                    subject.setSubjectName(subjectName.toString());
                }
                subject.setOptionA(optionA == null ? "" : optionA.toString());
                subject.setOptionB(optionB == null ? "" : optionB.toString());
                subject.setOptionC(optionC == null ? "" : optionC.toString());
                subject.setOptionD(optionD == null ? "" : optionD.toString());
                subject.setRightResult(rightResult.toString());
                // 确保subjectScore不为空
                if (subjectScore != null && !subjectScore.toString().isEmpty()) {
                    // 设置题目分数
                    subject.setSubjectScore(Integer.parseInt(subjectScore.toString()));
                } else {
                    // 默认分数为0
                    subject.setSubjectScore(0);
                }

                // 设置题目类型和难度
                if ("单选".equals(row.getCell(subjectTypeIndex).toString())) {
                    subject.setSubjectType(0);
                } else if ("多选".equals(row.getCell(subjectTypeIndex).toString())) {
                    subject.setSubjectType(1);
                } else {
                    subject.setSubjectType(2);
                }

                if ("简单".equals(subjectEasy.toString())) {
                    subject.setSubjectEasy(0);
                } else if ("普通".equals(subjectEasy.toString())) {
                    subject.setSubjectEasy(1);
                } else {
                    subject.setSubjectEasy(2);
                }
                // 设置题目关联的课程、年级和分组信息
                subject.setCourse(new CourseInfo(courseId));
                subject.setGrade(new GradeInfo(gradeId));
                subject.setDivision(division);

                subjects.add(subject);
            }

            // 关闭Excel文件
            workBook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjects;
    }


    /**
     * 解析第一行标题名得到列索引
     *
     * @param firstRow
     */
    private static void getCellIndexs(XSSFRow firstRow) {
        int cellNum = firstRow.getLastCellNum() - firstRow.getFirstCellNum();
        for (int i = 0; i < cellNum; i++) {
            String cell = firstRow.getCell(i).toString();
            if ("题目".equals(cell)) {
                subjectNameIndex = i;
                continue;
            }
            if ("答案A".equals(cell)) {
                optionAIndex = i;
                continue;
            }
            if ("答案B".equals(cell)) {
                optionBIndex = i;
                continue;
            }
            if ("答案C".equals(cell)) {
                optionCIndex = i;
                continue;
            }
            if ("答案D".equals(cell)) {
                optionDIndex = i;
                continue;
            }
            if ("正确答案".equals(cell)) {
                rightResultIndex = i;
                continue;
            }
            if ("分值".equals(cell)) {
                subjectScoreIndex = i;
                continue;
            }
            if ("试题类型".equals(cell)) {
                subjectTypeIndex = i;
                continue;
            }
            if ("难易程度".equals(cell)) {
                subjectEasyIndex = i;
                continue;
            }
        }
    }
}
