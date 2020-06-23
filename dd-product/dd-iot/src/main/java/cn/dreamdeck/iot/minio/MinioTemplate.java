package cn.dreamdeck.iot.minio;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


/**
 * MinioUtil工具类
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/2/5 17:53
 */
@Component
public class MinioTemplate {



        /**
         * logger
         */
        private final static Logger logger = LoggerFactory.getLogger(MinioTemplate.class);

        @Value("${minio.bucket}")
        private String bucket;

        /**
         * minioClient
         */
        private final MinioClient minioClient;

        @Autowired
        public MinioTemplate(MinioClient minioClient) {
            this.minioClient = minioClient;
        }



    /**
         * 上传文件
         *
         * @param file
         * @return java.lang.String
         * @throws
         * @author wliduo[i@dolyw.com]
         * @date 2020/2/6 16:03
         */
        public String uploadFile(MultipartFile file) {
            try(ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes())) {
                return uploadFile(bais, file.getOriginalFilename());
            } catch (IOException e) {
                logger.error("{}文件上传失败", file.getOriginalFilename());
                return "";
            }
        }

        /**
         * 上传文件
         *
         * @param stream
         * @param fileName
         * @return java.lang.String
         * @throws
         * @author wliduo[i@dolyw.com]
         * @date 2020/2/6 16:04
         */
        public String uploadFile(InputStream stream, String fileName) {
            // 获取当前日期，设置文件夹
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/");
            // 上传文件路径
            StringBuilder objectName = new StringBuilder(localDateTime.format(dateTimeFormatter));
            objectName.append('[').append(System.currentTimeMillis()).append(']').append(fileName);
            try {
                // 上传文件
                minioClient.putObject(bucket, objectName.toString(), stream, new HashMap(16));
                return objectName.toString();
            } catch (Exception e) {
                logger.error("{}文件上传失败", objectName);
                return "";
            }
        }

        /**
         * 获取文件
         *
         * @param objectName
         * @return java.io.InputStream
         * @throws
         * @author wliduo[i@dolyw.com]
         * @date 2020/2/6 16:05
         */
        public InputStream getFile(String objectName) {
            try {
                // 文件是否存在
                minioClient.statObject(bucket, objectName);
                // 获取文件
                return minioClient.getObject(bucket, objectName);
            } catch (Exception e) {
                logger.error("{}文件获取失败", objectName);
                return null;
            }
        }

        /**
         * 直接下载文件
         *
         * @param req
         * @param res
         * @param fid
         * @param fileName
         * @return void
         * @throws
         * @author wliduo[i@dolyw.com]
         * @date 2020/3/5 9:45
         */
        public void lookUploadFile(HttpServletRequest req, HttpServletResponse res, String fid, String fileName) {
            try (InputStream in = this.getFile(fid);
                 OutputStream output = res.getOutputStream()) {
                // 得到输入流
                if (in == null) {
                    try (PrintWriter printWriter = res.getWriter()) {
                        printWriter.append("404 - File Not Exist");
                    } catch (IOException e) {
                        logger.error("数据异常: {}", e);
                    }
                    return;
                }
                res.reset();

                res.addHeader("content-Disposition", "inline;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
                byte[] b = new byte[4096];
                int i = 0;
                while ((i = in.read(b)) > 0) {
                    output.write(b, 0, i);
                }
            } catch (MalformedURLException me) {
                logger.error("数据异常: {}", me);
            } catch (IOException e) {
                logger.error("数据异常: {}", e);
            }
        }

        /**
         * 获取外链
         *
         * @param objectName
         * @return java.lang.String
         * @throws
         * @author wliduo[i@dolyw.com]
         * @date 2020/2/6 16:04
         */
        public String getFileUrl(String objectName) {
            try {
                return minioClient.presignedGetObject(bucket, objectName, 60 * 60 * 24);
            } catch (Exception e) {
                logger.error("{}文件获取失败", objectName);
                return "";
            }
        }


}
