package com.tyrantqiao.controller;

import com.tyrantqiao.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {
	@Autowired
	private TokenService tokenService;

	/**
	 * 貌似不支持get形式 = =
	 * 由于参数较多，不想用@RequestParam形式，转为使用HttpServletRequest
	 * 以后要注意参数名称，不然很容易导致很多问题啊，像传参都进不来什么的
	 *
	 * @return
	 */
	@GetMapping("/check/signature")
	public String connectServer(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echoStr = request.getParameter("echostr");

		return tokenService.checkSignature(signature, timestamp, nonce, echoStr);
	}

}
