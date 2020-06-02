//package cn.dreamdeck.common.minio;
//
//import io.minio.MinioClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * minio 配置信息
// *
// * @author lxkui
// */
////@Data
//@Configuration
////@ConfigurationProperties(prefix = "minio")
//public class MinioProperties {
//
//	private final static Logger logger = LoggerFactory.getLogger(MinioProperties.class);
//
//	/**
//	 * minio 服务地址 http://ip:port
//	 */
//	@Value("${minio.url}")
//	private String url;
//
//	/**
//	 * 用户名
//	 */
//	@Value("${minio.access}")
//	private String accessKey;
//
//	/**
//	 * 密码
//	 */
//	@Value("${minio.secret}")
//	private String secretKey;
//
//	@Bean(name = "minioClient")
//	public MinioClient minioClient() throws Exception {
//		logger.info("---------- Minio文件系统初始化加载 ----------");
//		MinioClient minioClient = new MinioClient(url, accessKey, secretKey);
//		// 判断Bucket是否存在
////		boolean isExist = minioClient.bucketExists(bucket);
////		if(isExist) {
////			logger.info("---------- Minio文件系统Bucket已存在 ----------");
////		} else {
////			// 不存在创建一个新的Bucket
////			minioClient.makeBucket(bucket);
////			logger.info("---------- Minio文件系统Bucket已创建 ----------");
////		}
//		logger.info("---------- Minio文件系统初始化完成 ----------");
//		return minioClient;
//	}
//
//}
