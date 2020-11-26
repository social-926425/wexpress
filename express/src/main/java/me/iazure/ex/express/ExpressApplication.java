package me.iazure.ex.express;

import com.sun.glass.ui.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpressApplication.class, args);
//        SpringApplication springApplication = new SpringApplication(Application.class);
//        springApplication.run(args);
    }

}
