package cn.dreamdeck.model.iot;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统版本
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="系统版本", description="")
public class DdProjectVersion implements Serializable {


    public  String id;

    public  String name;


}
