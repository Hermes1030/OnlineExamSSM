package com.taohan.online.exam.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taohan.online.exam.dao.TeacherInfoMapper;
import com.taohan.online.exam.po.ClassInfo;
import com.taohan.online.exam.po.StudentInfo;
import com.taohan.online.exam.po.TeacherInfo;
import com.taohan.online.exam.service.StudentInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taohan.online.exam.po.ExamHistoryInfo;
import com.taohan.online.exam.service.ExamHistoryPaperService;

import javax.servlet.http.HttpServletRequest;

/**
  *
  * <p>Title: ExamHistoryInfoHandler</p>
  * <p>Description: </p>
  */

@Controller
public class ExamHistoryInfoHandler {

	@Autowired
	private ExamHistoryPaperService examHistoryPaperService;

	private Logger logger = Logger.getLogger(ExamHistoryInfoHandler.class);

	@RequestMapping("/historys")
	public ModelAndView examHistorys(HttpServletRequest request,
									 @RequestParam(value = "classId", required = false) Integer classId
	) {
		ModelAndView model = new ModelAndView("admin/examHistorys");
		if (classId != null){
			classId = (Integer) request.getSession().getAttribute("classId");
			logger.info("获得教师classID:"+classId);
			List<ExamHistoryInfo> historys = examHistoryPaperService.getExamHistoryToTeacherByClassId(classId);
			model.addObject("historys", historys);
		}else{
			List<ExamHistoryInfo> historys = examHistoryPaperService.getExamHistoryToTeacher();
			model.addObject("historys", historys);
			logger.info("管理员查询历史考试信息 SIZE "+historys.size());
		}
		return model;
	}
}
