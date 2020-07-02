package cn.dreamdeck.iot;

import cn.dreamdeck.common.swagger.annotation.EnableDdSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"cn.dreamdeck"})
@EnableFeignClients({"cn.dreamdeck"})
@EnableDdSwagger2
public class DdIotApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdIotApplication.class,args);
    }
}
