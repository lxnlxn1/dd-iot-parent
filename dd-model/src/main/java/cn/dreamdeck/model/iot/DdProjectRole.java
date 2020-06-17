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
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdProjectRole对象", description="")
public class DdProjectRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer projectId;

    private Integer projectRoleId;


}
