package ink.num.dao;

import ink.num.model.Number;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumberDao {

    @Select("select * from number")
    List<Number> getAll();

    @Insert("insert into number (high_1,high_2,high_3,high_4,time) values (#{high_1},#{high_2},#{high_3}," +
            "#{high_4},#{time})")
    Integer addData(Number number);

    @Select("select * from number where time >#{starTime} and time<#{endTime}")
    List<Number> search(@Param("starTime") String startTime, @Param("endTime") String endTime);
}