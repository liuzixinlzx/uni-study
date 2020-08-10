package com.lzx.uniserver.mapper;

import com.lzx.uniserver.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alex wong
 * @since 2020-08-06
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
