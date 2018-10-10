package com.tyrantqiao.controller;

import com.tyrantqiao.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
@Controller
@RequestMapping("/token")
public class TokenController {
	@Autowired
	private TokenService tokenService;

	@RequestMapping("/check/signature")
	public String connectServer(HttpServletRequest request, HttpServletResponse response) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echoStr = request.getParameter("echostr");

		return tokenService.checkSignature(signature, timestamp, nonce, echoStr);
	}

}
