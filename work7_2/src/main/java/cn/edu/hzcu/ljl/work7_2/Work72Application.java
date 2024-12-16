package cn.edu.hzcu.ljl.work7_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableCaching
public class Work72Application {

    public static void main(String[] args) {
        SpringApplication.run(Work72Application.class, args);
    }

}
