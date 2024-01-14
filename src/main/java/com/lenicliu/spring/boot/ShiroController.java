package com.lenicliu.spring.boot;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShiroController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping({ "/", "/index" })
	public String index() {
		return "index";
	}

	@GetMapping("guest/home")
	public String home() {
		return "guest/home";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin/admin";
	}

	@GetMapping("/forbidden")
	public String forbidden() {
		return "forbidden";
	}

	@GetMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:index";
	}

	@PostMapping("/login")
	public String login(String username, String password) {
		Subject currentUser = SecurityUtils.getSubject();
		if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
			try {
				currentUser.login(new UsernamePasswordToken(username, password));
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
				return "login";
			}
			return "redirect:index";
		} else {
			return "login";
		}
	}
}