package ink.num.dao;

import ink.num.model.Admin;
import ink.num.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    @Insert("insert into user (name,password,salt,username,email,sex,status) values (#{name},#{password},#{salt},#{username},#{email},#{sex},0)")
    Integer addUser(User user);

    @Select("select * from user where name = #{name}")
    User getByName(@Param("name") String name);
    @Select("select * from user where name = #{name} and status = 1")
    User getByNameStatus(@Param("name") String name);

    @Select("select * from admin where name = #{name}")
    Admin getAdminByName(@Param("name") String name);

    @Select("select * from user")
    List<User> getAll();

    @Delete("delete from user where id = #{id}")
    Integer deleteById(@Param("id") Integer id);
    @Update("update user set status = 1 where id = #{id}")
    Integer updateUserStatus(Integer id);
    @Update("update user set salt = #{salt}, password = #{password} where id = #{id}")
    Integer updatePassord(User user);
}
