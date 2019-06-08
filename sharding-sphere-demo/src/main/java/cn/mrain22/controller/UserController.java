package cn.mrain22.controller;

import cn.mrain22.entity.UserEntity;
import cn.mrain22.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/6/7 18:16
 * @Version 1.0
 * @Describe:
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    public boolean insert(UserEntity user) {
        return userService.save(user);
    }

    @GetMapping("/select")
    public List<UserEntity> select() {
        return userService.list();
    }

}
