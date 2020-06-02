package cn.dreamdeck.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"cn.dreamdeck"})
public class DdIotApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdIotApplication.class,args);
    }
}
