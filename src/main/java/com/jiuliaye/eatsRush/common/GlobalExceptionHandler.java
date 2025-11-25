package com.jiuliaye.eatsRush.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
//对于某段代码可能出现的异常情况，可以使用try...catch 进行异常捕获，但是这样对于每个代码段都需要添加try...catch，代码冗余，定义一个通用的全局异常处理器，就可以解决本项目的所有异常。
@ControllerAdvice(annotations = {RestController.class, Controller.class})   //指定拦截哪一类的Controller
@ResponseBody   // 将方法的返回值 R 对象转换为json格式的数据, 响应给页面;上述的两个注解也可以合并成为一个注解 @RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)   //指定拦截哪一类的异常，比如这里是SQL 完整性约束违规异常
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error(ex.getMessage());

        if (ex.getMessage().contains("Duplicate entry")) {  //唯一性约束异常
            //举例：java.sql.SQLIntegrityConstraintViolationException: Duplicate entry "abc" for key 'idx_username'
            String[] split = ex.getMessage().split(" ");    //解析异常提示信息，获取出是那个值违背了唯一约束
            String msg = split[2] + "已存在";  //组装错误信息并返回："abc"已存在
            return R.error(msg);
        }

        return R.error("未知错误");
    }

    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex) {
        log.error(ex.getMessage());

        return R.error(ex.getMessage());
    }

}
