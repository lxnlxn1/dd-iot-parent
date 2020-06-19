package cn.dreamdeck.model.iot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdDocType对象", description="")
public class DdDocType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "project_doc_type_id", type = IdType.AUTO)
    private Integer projectDocTypeId;

    private String docTypeDesc;

    private String docTypeName;


}
