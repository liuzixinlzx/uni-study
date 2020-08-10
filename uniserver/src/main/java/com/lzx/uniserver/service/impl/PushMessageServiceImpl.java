package com.lzx.uniserver.service.impl;

import com.lzx.uniserver.entity.PushMessage;
import com.lzx.uniserver.mapper.PushMessageMapper;
import com.lzx.uniserver.service.PushMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2020-08-10
 */
@Service
public class PushMessageServiceImpl extends ServiceImpl<PushMessageMapper, PushMessage> implements PushMessageService {

}
