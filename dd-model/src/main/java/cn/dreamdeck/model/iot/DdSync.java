package cn.dreamdeck.model.iot;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxn
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdSync对象", description="")
public class DdSync implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String url;

    private String statrs;

    private String createTime;

    private String updateTime;


}
