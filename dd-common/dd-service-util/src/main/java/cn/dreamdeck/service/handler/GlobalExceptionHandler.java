package cn.dreamdeck.service.handler;


import cn.dreamdeck.common.execption.DdException;
import cn.dreamdeck.common.result.DdResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public DdResult error(Exception e){
        e.printStackTrace();
        return DdResult.fail();
    }

    /**
     * 自定义异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(DdException.class)
    @ResponseBody
    public DdResult error(DdException e){
        return DdResult.fail(e.getMessage());
    }
}
