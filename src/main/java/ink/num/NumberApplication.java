package ink.num;

import ink.num.util.ContinueckRead;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ink.num.dao")
public class NumberApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberApplication.class, args);
        ContinueckRead.kaishi();
    }

}
