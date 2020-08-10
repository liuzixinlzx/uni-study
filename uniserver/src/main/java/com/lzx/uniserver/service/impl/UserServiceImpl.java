package com.lzx.uniserver.service.impl;

import com.lzx.uniserver.entity.User;
import com.lzx.uniserver.mapper.UserMapper;
import com.lzx.uniserver.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2020-08-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
