package com.taohan.online.exam.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.taohan.online.exam.po.ClassInfo;
import com.taohan.online.exam.po.GradeInfo;
import com.taohan.online.exam.po.TeacherInfo;
import com.taohan.online.exam.service.TeacherInfoService;

/**
  * <p>Title: TeacherInfoHandler</p>
  * <p>Description: 教师</p>
  */

@Controller
@SuppressWarnings("all")
public class TeacherInfoHandler {
	
	@Autowired
	private TeacherInfoService teacherInfoService;
	
	private Logger logger = Logger.getLogger(TeacherInfoHandler.class);
	
	/**
	 * 获取  验证教师信息
	 * @param teacherAccount
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/validateTeacher", method=RequestMethod.POST)
	public void queryTeacherExists(@RequestParam(value="account") String teacherAccount,
			HttpServletResponse response) throws Exception {
		logger.info("获取教师 "+teacherAccount+" 的信息");
		TeacherInfo teacherInfo = null;
		teacherInfo = teacherInfoService.getTeacherByAccount(teacherAccount);
		//教师账户不存在
		if (teacherInfo == null) {
			response.getWriter().print("1");
		} else {			
			response.getWriter().print(teacherInfo.getTeacherPwd());
		}
	}

	/**
	 * 教师登录
	 * @param teacherAccount
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/teacherlogin", method=RequestMethod.POST)
	public String teacherLogin(@RequestParam("teacherAccount") String teacherAccount,
			HttpServletRequest request) {
		if (teacherAccount == null || "".equals(teacherAccount)) {
			logger.error("教师账号为空");
			request.setAttribute("error", "Login information incorrect!");
			return "/error";
		}
		//获取当前登录教师
		TeacherInfo teacherInfo = teacherInfoService.getTeacherByAccount(teacherAccount);
		if(teacherInfo == null){
			logger.error("Account is empty");
			request.setAttribute("error", "Account is empty!");
			return "/error";
		}
		String teacherPwd = request.getParameter("teacherPwd");
		if(!teacherInfo.getTeacherPwd().equals(teacherPwd)){
			logger.error("Login information incorrect");
			request.setAttribute("error", "Login information incorrect!");
			return "/error";
		}

		//将当前登录教师 后台权限存入 Session
		request.getSession().setAttribute("adminPower", teacherInfo.getAdminPower());

		// 获取当前老师的班级ID
		if(teacherInfo.getAdminPower()!=1){
			Integer classId = teacherInfoService.getClassIdByTeacherId(teacherInfo.getTeacherId());
			request.getSession().setAttribute("classId",classId);
			// 获取当前老师的班级Name
			request.getSession().setAttribute("className",teacherInfoService.getClassNameByClassId(classId));
		}

		request.getSession().setAttribute("loginTeacher", teacherInfo);
		return "redirect:admin/index.jsp";
	}
	
	/**
	 * 教师查看自己的信息
	 * @param teacherId
	 * @return
	 */
	@RequestMapping("/selfinfo/{teacherId}")
	public ModelAndView loginTeacherSelf(@PathVariable("teacherId") Integer teacherId) {
		ModelAndView model = new ModelAndView();
		logger.error("教师 "+teacherId+" 查看自己的信息");
		if (teacherId == null) {
			model.setViewName("../error");
			return model;
		} else {
			List<TeacherInfo> teachers = new ArrayList<TeacherInfo>();
			TeacherInfo teacher = teacherInfoService.getTeacherById(teacherId);
			teachers.add(teacher);
			model.addObject("teachers", teachers);
			model.setViewName("/admin/teachers");
			
			return model;
		}
	}
	
	/**
	 * 教师退出登录
	 * @throws IOException 
	 */
	@RequestMapping("/exitTeacher")
	public void exitTeacher(HttpSession session, HttpServletResponse response) throws IOException {
		session.removeAttribute("loginTeacher");
		session.removeAttribute("adminPower");
		response.sendRedirect("admin/login.jsp");
	}
	
	/**
	 * 查询教师集合
	 * @param startPage
	 * @param pageShow
	 * @return
	 */
	@RequestMapping(value="/teachers", method=RequestMethod.GET)
	public ModelAndView getTeachers(
			@RequestParam(value="startPage", required=false, defaultValue="1") Integer startPage,  //当前页码,默认第一页
			@RequestParam(value="pageShow", required=false, defaultValue="10") Integer pageShow /*每页显示数据量，默认10条*/) {
		logger.info("查询教师集合");
		ModelAndView model = new ModelAndView();
		model.setViewName("admin/teachers");
		List<TeacherInfo> teachers;
		Map<String, Object> map = new HashMap<String, Object>();
		//计算当前查询起始数据索引
		int startIndex = (startPage-1) * pageShow;
		map.put("startIndex", startIndex);
		map.put("pageShow", pageShow);
		map.put("teacher", null);
		teachers = teacherInfoService.getTeachers(map);
		model.addObject("teachers", teachers);
		//获取教师总量
		int teacherTotal = teacherInfoService.getTeacherTotal();
		//计算总页数
		int pageTotal = 1;
		if (teacherTotal % pageShow == 0)
			pageTotal = teacherTotal / pageShow;
		else
			pageTotal = teacherTotal / pageShow + 1;			
		model.addObject("pageTotal", pageTotal);
		model.addObject("pageNow", startPage);
		return model;
	}
	
	/**
	 * 预修改教师
	 * @param teacherId
	 * @return
	 */
	@RequestMapping(value="/teacher/{teacherId}", method=RequestMethod.GET)
	public ModelAndView preUpdateTeacher(@PathVariable("teacherId") Integer teacherId) {
		logger.info("预修改教师处理");
		ModelAndView model = new ModelAndView();
		//获取要修改教师
		TeacherInfo teacher = teacherInfoService.getTeacherById(teacherId);
		model.setViewName("/admin/teacheredit");
		model.addObject("teacher", teacher);
		return model;
	}
	

	/**
	 * 处理教师信息的更新或添加请求。
	 * @param teacherId 教师ID，如果为更新操作，则需要提供此ID；可选参数。
	 * @param isUpdate 标记是否为更新操作的参数，1表示更新，0或null表示添加；可选参数。
	 * @param teacherName 教师姓名，必填参数。
	 * @param teacherAccount 教师账号，必填参数。
	 * @param teacherPwd 教师密码，必填参数。
	 * @param adminPower 教师权限等级，必填参数。
	 * @return 返回重定向到教师信息列表页面的字符串。
	 */
	@RequestMapping(value="/teacher/teacher", method=RequestMethod.POST)
	public String isUpdateOrAddTeacher(@RequestParam(value="teacherId", required=false) Integer teacherId,
	        @RequestParam(value="isupdate", required=false) Integer isUpdate,
	        @RequestParam("teacherName") String teacherName,
	        @RequestParam("teacherAccount") String teacherAccount,
	        @RequestParam("teacherPwd") String teacherPwd,
	        @RequestParam("adminPower") Integer adminPower) {
	    // 创建教师信息对象并设置参数
	    TeacherInfo teacher = new TeacherInfo();
	    teacher.setTeacherId(teacherId);
	    teacher.setTeacherName(teacherName);
	    teacher.setTeacherAccount(teacherAccount);
	    teacher.setTeacherPwd(teacherPwd);
	    teacher.setAdminPower(adminPower);
	    if (isUpdate != null) {  // 判断是更新操作还是添加操作
	        // 如果是更新操作，则调用服务层进行信息更新
	        logger.info("修改教师 "+teacher+" 的信息");
	        int row = teacherInfoService.isUpdateTeacherInfo(teacher);
	    } else {  // 添加操作
	        // 如果不是更新操作，则调用服务层进行信息添加
	        logger.info("添加教师 "+teacher+" 的信息");
	        int row = teacherInfoService.isAddTeacherInfo(teacher);
	    }
	    // 无论添加还是更新成功，都重定向到教师信息列表页面
	    return "redirect:/teachers";
	}
	
	/**
	 * 删除教师
	 * @param teacherId
	 * @return
	 */
	@RequestMapping(value="/teacher/{teacherId}", method=RequestMethod.DELETE)
	public String isDelTeacher(@PathVariable("teacherId") Integer teacherId) {
		logger.info("删除教师 "+teacherId);
		int row = teacherInfoService.isDelTeacherInfo(teacherId);
		return "redirect:/teachers";
	}
}
