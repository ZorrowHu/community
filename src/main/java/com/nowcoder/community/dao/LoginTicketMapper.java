package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
@Deprecated
public interface LoginTicketMapper {

    //用注解来实现mapper执行怎样的sql语句，而不是通过resources/mapper中的xml文件
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    //Ticket是辨别用户的唯一标识
    LoginTicket selectByTicket(String ticket);

    //实现动态的sql语句
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
                "<if test=\"ticket!=null\"> ",
                    "and 1=1 ", //随便拼的，没啥含义，作演示而已
                "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);

}
