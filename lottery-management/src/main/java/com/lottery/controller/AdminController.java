package com.lottery.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lottery.Const;
import com.lottery.exception.InvalidParameter;
import com.lottery.model.Agent;
import com.lottery.service.AdminService;

@RestController
@RequestMapping(value = "/api/v1")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@PostMapping(value = "/admins/sessions")
	public Agent postSession(@RequestBody Agent agent, HttpSession session) throws InvalidParameter {
		if (StringUtils.isEmpty(agent.getTel())) {
			throw new InvalidParameter();
		}
		if (StringUtils.isEmpty(agent.getPlainPassword())) {
			throw new InvalidParameter();
		}
		Agent result =  adminService.validatePassword(agent.getTel(), agent.getPlainPassword()).orElseThrow(InvalidParameter::new);
		session.setAttribute(Const.AGENT_ID_KEY_IN_SESSION, result.getId());
		return result;
	}
}
