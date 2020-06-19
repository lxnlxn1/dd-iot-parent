package cn.dreamdeck.iot.minio;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class MinioComfigVo {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access}")
    private String access;

    @Value("${minio.secret}")
    private String secret;
}
