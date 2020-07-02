package cn.dreamdeck.model.iot.device.gateway;

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
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdDeviceGateway对象", description="")
public class DdDeviceGateway implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    private Integer upsideId;

    private Integer brandId;

    private Integer modelId;

    private String deviceName;

    private String deviceIntro;

    private Integer deviceNetworkType;

    private String deviceIp;

    private Integer deviceId;

    private String status;

    private String longitude;

    private String latitude;

    private String concentratorId;

    private String lastTime;

    private String createTime;


}
