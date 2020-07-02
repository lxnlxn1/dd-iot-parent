//package cn.dreamdeck.iot.minio;
//
//
//import io.minio.MinioClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Minio文件系统初始化
// *
// * @author
// * @date 2020/2/5 14:36
// */
//@Configuration
//public class MinioConfig {
//
//    /**
//     * logger
//     */
//    private final static Logger logger = LoggerFactory.getLogger(MinioConfig.class);
//
//    @Value("${minio.url}")
//    private String url;
//
//    @Value("${minio.access}")
//    private String access;
//
//    @Value("${minio.secret}")
//    private String secret;
//
//    @Value("${minio.bucket}")
//    private String project;
//
//    @Value("${minio.bucket1}")
//    private String device;
//
//
//    /**
//     * Minio文件系统配置
//     *
//     * @param
//     * @return io.minio.MinioClient
//     * @throws
//     * @author wliduo[i@dolyw.com]
//     * @date 2020/2/5 16:10
//     */
//    @Bean(name = "minioClient")
//    public MinioClient minioClient() throws Exception {
//        logger.info("---------- Minio文件系统初始化加载 ----------");
//        MinioClient minioClient = new MinioClient(url, access, secret);
//        // 判断Bucket是否存在
//        boolean isExist = minioClient.bucketExists(project);
//        if(isExist) {
//            logger.info("---------- Minio文件系统project--Bucket已存在 ----------");
//        } else {
//            // 不存在创建一个新的Bucket
//            minioClient.makeBucket(project);
//            logger.info("---------- Minio文件系统project--Bucket已创建 ----------");
//        }
//
//        boolean isExist1 = minioClient.bucketExists(device);
//        if(isExist1) {
//            logger.info("---------- Minio文件系统device--Bucket已存在 ----------");
//        } else {
//            // 不存在创建一个新的Bucket
//            minioClient.makeBucket(device);
//            logger.info("---------- Minio文件系统device--Bucket已创建 ----------");
//        }
//        logger.info("---------- Minio文件系统初始化完成 ----------");
//        return minioClient;
//    }
//
//}
