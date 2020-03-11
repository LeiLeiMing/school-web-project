package com.secondhand.mapper;

import com.secondhand.pojo.MessagePojo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by LeiMing on 2020/3/11 14:05
 */
public interface MessageMapper extends Mapper<MessagePojo> {

    @Update("update message set status = 1 where id = #{messageid}")
    void changeMessageStatus(String messageid);

    @Select("select count(*) from message where userid = #{id} and status = 0")
    Integer getMessageMount(String id);
}
