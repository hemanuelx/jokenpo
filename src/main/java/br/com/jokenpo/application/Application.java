package br.com.jokenpo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.jokenpo.mvc.entity"})
@EnableJpaRepositories(basePackages = {"br.com.jokenpo.mvc.repository"})
@ComponentScan(basePackages = {"br.com.jokenpo.mvc.controller"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
