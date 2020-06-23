package cn.dreamdeck.model.trash;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxn
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdTrash对象", description="")
public class DdTrash implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "device_id", type = IdType.AUTO)
    private Integer deviceId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "设备名字")
    private String deviceName;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "设备类型")
    private Integer classifyId;

    @ApiModelProperty(value = "型号ID")
    private Integer typeId;

    @ApiModelProperty(value = "设备IP")
    private String deviceIp;

    @ApiModelProperty(value = "设备唯一Id")
    private Integer soleId;

    @ApiModelProperty(value = "科大讯飞标识位")
    private String auiaModel;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "精度")
    private String longitude;

    @ApiModelProperty(value = "更改时间")
    private Date lastTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "品牌Id")
    private Integer brandId;

    @ApiModelProperty(value = "具体类型ID")
    private Integer modelId;


    private String TrashFullList;


}
