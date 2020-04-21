package com.secondhand.mapper;

import com.secondhand.Users;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by LeiMing on 2020/4/21 9:43
 */
public interface UserMapper extends Mapper<Users> {

    @Select("select * from user where id = #{id}")
    public Users getUserById(String id);
}
