package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

//接口打上Mapper注解才能被容器自动装配
//除了声明还需要一个配置文件，然后MyBatis会在底层自动生成一个实现类
@Mapper
public interface UserMapper {   //这样声明好了就可以了

    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl); //更新头像

    int updatePassword(int id, String password);

}
