package cn.dreamdeck.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"cn.dreamdeck"})
@EnableDiscoveryClient
public class DdUserApplication {

   public static void main(String[] args) {
      SpringApplication.run(DdUserApplication.class, args);
   }

}