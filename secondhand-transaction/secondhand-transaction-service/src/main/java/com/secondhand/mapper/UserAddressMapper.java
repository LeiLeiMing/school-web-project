package com.secondhand.mapper;

import com.secondhand.project.pojo.UserAddressPojo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by LeiMing on 2020/4/21 18:38
 */
public interface UserAddressMapper extends Mapper<UserAddressPojo> {

    @Select("select * from useraddress where id = #{addressid}")
    public UserAddressPojo getAddressById(String addressid);
}
