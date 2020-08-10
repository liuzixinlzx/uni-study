package com.lzx.uniserver.service.impl;

import com.lzx.uniserver.entity.Version;
import com.lzx.uniserver.mapper.VersionMapper;
import com.lzx.uniserver.service.VersionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2020-08-05
 */
@Service
public class VersionServiceImpl extends ServiceImpl<VersionMapper, Version> implements VersionService {

}
