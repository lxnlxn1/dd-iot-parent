//package cn.dreamdeck.iot.minio;
//
//
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.EventListener;
//
//import javax.servlet.MultipartConfigElement;
//import java.io.IOException;
//
///**
// * @author zhangh
// * @version V1.0.0
// * @projectName parent
// * @title     IndexSwaggerConfig
// * @package    com.gdd.videos.mini_api.common
// * @date   2019/9/11 15:19
// * @explain 项目启动时默认打开swagger页面
// */
//@Configuration
//public class IndexSwaggerConfig {
//
//    /**
//     * 配置文件上传大小
//     */
//    @Bean
//    public MultipartConfigElement multipartConfigElement(){
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //  单个数据大小 10M
//        factory.setMaxFileSize("10240KB");
//        /// 总上传数据大小 10M
//        factory.setMaxRequestSize("10240KB");
//        return factory.createMultipartConfig();
//    }
//
//}
