package ink.num;

import ink.num.dao.NumberDao;
import ink.num.dao.UserDao;
import ink.num.model.Number;
import ink.num.model.User;
import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.List;

@SpringBootTest
@MapperScan("ink.num.dao")
class NumberApplicationTests {

    @Autowired
    NumberDao dao;

    @Test
    void contextLoads() {
            Number build = Number.builder()
                    .high_1("1")
                    .high_2("2")
                    .high_3("3")
                    .high_4("4")
                    .time("2020/10/10 10:10:10")
                    .build();
        dao.addData(build);

    }

}
