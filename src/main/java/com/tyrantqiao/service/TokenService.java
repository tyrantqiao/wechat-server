package com.tyrantqiao.service;

import com.tyrantqiao.util.TokenUtil;
import org.springframework.stereotype.Service;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
@Service
public class TokenService {
	public String checkSignature(String signature, String timestamp, String nonce, String echoStr) {
		if (TokenUtil.checkToken(signature, timestamp, nonce)) {
			return echoStr;
		}
		return "";
	}
}
