package net.hongkuang.ditui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author administrator
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("net.hongkuang.ditui.project.*.*.mapper")
public class HkApplication {
    public static void main(String[] args) {
        SpringApplication.run(HkApplication.class, args);
    }

}