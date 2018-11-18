package com.tyrantqiao.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;

/**
 * @author tyrantqiao
 * date: 2018/11/9
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
@Data
public class User extends BaseEntity {
    private String username;
    private String password;
}
