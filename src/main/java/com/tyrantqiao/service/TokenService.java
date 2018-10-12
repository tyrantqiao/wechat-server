package com.tyrantqiao.service;

import com.tyrantqiao.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
@Slf4j
@Service
public class TokenService {
	public String checkSignature(String signature, String timestamp, String nonce, String echoStr) {
		if (TokenUtil.checkToken(signature, timestamp, nonce)) {
			log.info("成功验证token，返回验证结果");
			return echoStr;
		}
		log.error("无法验证服务器token");
		return "无法验证服务器token";
	}
}
