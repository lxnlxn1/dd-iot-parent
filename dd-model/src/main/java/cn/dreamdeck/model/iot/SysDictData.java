package cn.dreamdeck.model.iot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "设备类别")
@TableName("sys_dict_data")
@EqualsAndHashCode(callSuper = true)
public class SysDictData extends Model<SysDictData> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dict_code", type = IdType.AUTO)
    @ApiModelProperty(value = "字典编码")
    private Integer dictCode;

    @TableField(value = "`dict_id`")
    @ApiModelProperty(value = "字典主键")
    private Integer dictId;

    @TableField(value = "`dict_sort`")
    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;

    @TableField(value = "`dict_label`")
    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @TableField(value = "`dict_value`")
    @ApiModelProperty(value = "字典键值")
    private String dictValue;

    @TableField(value = "`dict_type`")
    @ApiModelProperty(value = "字典类型")
    private String dictType;


}
