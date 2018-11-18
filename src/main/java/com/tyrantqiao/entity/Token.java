package com.tyrantqiao.entity;

import lombok.Data;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
@Data(staticConstructor = "of")
public class Token extends BaseEntity {
    /**
     * 随机数
     */
    private String nonce;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 随机字符串
     */
    private String echostr;
    /**
     * 加密签名
     */
    private String signature;

}
