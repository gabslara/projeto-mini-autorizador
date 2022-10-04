package br.com.autorizador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.autorizador")
public class SpringApp {
    public static void main(String[] args) {

        SpringApplication.run(SpringApp.class, args);
    }
}
