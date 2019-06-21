package cn.edu.nju.iselab.mnist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MnistDl4jWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MnistDl4jWebApplication.class, args);
    }

}
