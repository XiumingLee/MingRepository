package cn.mrain22.service.Impl;

import cn.mrain22.entity.UserEntity;
import cn.mrain22.mapper.UserMapper;
import cn.mrain22.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/6/7 18:14
 * @Version 1.0
 * @Describe:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
}
