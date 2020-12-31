package com.lottery.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;
import com.google.zxing.WriterException;
import com.lottery.Const;
import com.lottery.exception.InternalServiceException;
import com.lottery.exception.InvalidParameter;
import com.lottery.model.Agent;
import com.lottery.model.Customer;
import com.lottery.model.SiteAdmin;
import com.lottery.service.AgentService;
import com.lottery.service.CustomerService;
import com.lottery.service.SiteAdminService;

@RestController
@RequestMapping(value = "/api/v1")
public class LoginController {
	private static final Logger LOGGER = Logger.getLogger(LoginController.class);

	@Autowired
	private SiteAdminService adminService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private CustomerService customerService;
	@Autowired
    private Producer kaptchaProducer;
	

	@GetMapping(value = "/security-resources/security-codes")
	public void getSecurityCode(HttpServletResponse response, HttpSession session) throws InvalidParameter {
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);
        session.setAttribute(Const.SECURITY_CODE_KEY_SESSION, text);
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
        	LOGGER.error("Fail to create security code pic", e);
        }
	}
	
	@PostMapping(value = "/security-resources/admins/sessions")
	public SiteAdmin postSession(String tel, String password, String securityCode, HttpSession session) throws InvalidParameter {
		if (StringUtils.isEmpty(tel)) {
			throw new InvalidParameter();
		}
		if (StringUtils.isEmpty(password)) {
			throw new InvalidParameter();
		}
		if (StringUtils.isEmpty(securityCode)) {
			throw new InvalidParameter();
		}
		SiteAdmin result =  adminService.validatePassword(tel, password).orElseThrow(InvalidParameter::new);
		session.setAttribute(Const.ADMIN_ID_KEY_IN_SESSION, result.getId());
		return result;
	}
	
	@PostMapping(value = "/security-resources/agents/sessions")
	public Agent postSession(@RequestBody Agent agent, HttpSession session) throws InvalidParameter {
		if (StringUtils.isEmpty(agent.getTel())) {
			throw new InvalidParameter();
		}
		if (StringUtils.isEmpty(agent.getPlainPassword())) {
			throw new InvalidParameter();
		}
		Agent result = agentService.validatePassword(agent.getTel(), agent.getPlainPassword())
				.orElseThrow(InvalidParameter::new);
		session.setAttribute(Const.AGENT_ID_KEY_IN_SESSION, result.getId());
		return result;
	}
	
	@PostMapping(value = "/security-resources/agents")
	public Agent postAgent(@RequestBody Agent agent) throws InvalidParameter, InternalServiceException {
		if (StringUtils.isEmpty(agent.getTel())) {
			throw new InvalidParameter();
		}
		try {
			return agentService.saveOrUpdate(agent);
		} catch (IOException | WriterException e) {
			throw new InternalServiceException();
		}
	}
	
	@PostMapping(value = "/security-resources/customers/sessions")
	public Customer postSession(@RequestBody Customer customer, HttpSession session) throws InvalidParameter {
		if (StringUtils.isEmpty(customer.getTel())) {
			throw new InvalidParameter();
		}
		if (StringUtils.isEmpty(customer.getPlainPassword())) {
			throw new InvalidParameter();
		}
		Customer result =  customerService.validatePassword(customer.getTel(), customer.getPlainPassword()).orElseThrow(InvalidParameter::new);
		session.setAttribute(Const.CUSTOMER_ID_KEY_IN_SESSION, result.getId());
		return result;
	}

	@PostMapping(value = "/security-resources/customers")
	public Customer postAgent(@RequestBody Customer customer) throws InvalidParameter {
		if (StringUtils.isEmpty(customer.getTel())) {
			throw new InvalidParameter();
		}
		if (StringUtils.isEmpty(customer.getPlainPassword())) {
			throw new InvalidParameter();
		}
		if (StringUtils.isEmpty(customer.getAgent())) {
			throw new InvalidParameter();
		}
		if (StringUtils.isEmpty(customer.getAgent().getId())) {
			throw new InvalidParameter();
		}
		return customerService.saveOrUpdate(customer);
	}
}
