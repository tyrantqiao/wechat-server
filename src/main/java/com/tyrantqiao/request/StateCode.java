package com.tyrantqiao.request;

import lombok.Data;

/**
 * @author tyrantqiao
 * date: 2018/11/15
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
@Data
public class StateCode {
    public int code;
    public String msg;

    public StateCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
