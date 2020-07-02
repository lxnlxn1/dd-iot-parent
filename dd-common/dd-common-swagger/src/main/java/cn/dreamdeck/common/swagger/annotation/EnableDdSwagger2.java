package cn.dreamdeck.common.swagger.annotation;

import cn.dreamdeck.common.swagger.annotation.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author lxkui
 * @date 2019/10/21
 * 开启dd swagger
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableDdSwagger2 {
}
