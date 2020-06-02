package cn.dreamdeck.lighting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"cn.dreamdeck"})
public class DdLightingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdLightingApplication.class,args);
    }
}
