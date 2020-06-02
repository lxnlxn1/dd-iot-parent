package cn.dreamdeck.common.result;

import cn.dreamdeck.common.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类 {数据查询}
 *
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class DdResult<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public DdResult(){}

    // 返回数据
    protected static <T> DdResult<T> build(T data) {
        DdResult<T> result = new DdResult<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> DdResult<T> build(T body, ResultCodeEnum resultCodeEnum) {
        DdResult<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static<T> DdResult<T> ok(){
        return DdResult.ok(null);
    }

    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> DdResult<T> ok(T data){
        DdResult<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static<T> DdResult<T> fail(){
        return DdResult.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> DdResult<T> fail(T data){
        DdResult<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public DdResult<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public DdResult<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}
