package com.tyrantqiao.service;

import com.tyrantqiao.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tyrantqiao
 * date: 2018/11/9
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
@Service
@RequestMapping("/user")
public class UserService {
    @RequestMapping("/sign")
    public void doSubmitRequest(@RequestBody User user){

    }
}
