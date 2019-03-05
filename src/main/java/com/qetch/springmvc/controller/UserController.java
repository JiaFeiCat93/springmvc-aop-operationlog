package com.qetch.springmvc.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qetch.springmvc.annotation.MethodLog;
import com.qetch.springmvc.domain.User;
import com.qetch.springmvc.service.UserService;

@Controller
public class UserController {
	private static final Log logger = LogFactory.getLog(UserController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index")
	public String toLogin(User user) {
		return "login";
	}
	
	@RequestMapping(value = "/login")
	public String userLogin(@ModelAttribute User user, Model model) {
		logger.info("--->userLogin--->user:"+user);
		if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
			model.addAttribute("message", "用户名或密码不能为空！");
			return "forward:/index";
		} else if ("admin".equals(user.getUserName()) && "123456".equals(user.getPassword())) {
			model.addAttribute("user", user);
			return "userinfo";
		} else {
			model.addAttribute("message", "用户名或密码不正确！");
			return "forward:/index";
		}
	}
	
	@RequestMapping(value = "/updateUser")
	@MethodLog(remark = "修改用户信息", operateType = "2")
	public Object initPwd(User user) {
		int result = 0;
		result = userService.updateUser(user);
		return result;
	}
}
