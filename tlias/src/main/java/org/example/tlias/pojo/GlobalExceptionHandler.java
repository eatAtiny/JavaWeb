package org.example.tlias.pojo;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

//全局异常处理器
//@RestControllerAdvice 注解是 Spring MVC 中的一个注解，用于定义全局异常处理器。
// 它可以应用于整个应用程序，用于捕获和处理所有控制器方法中的异常。
// 当控制器方法抛出异常时，Spring MVC 会自动调用全局异常处理器中的方法来处理异常。
// 全局异常处理器通常用于统一处理异常，返回统一的错误信息给客户端。
// 可以通过 @ExceptionHandler 注解来指定处理哪些异常类型。
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    //处理异常
    @ExceptionHandler
    public Result ex(Exception e){//方法形参中指定能够处理的异常类型
        e.printStackTrace();//打印堆栈中的异常信息
        //捕获到异常之后，响应一个标准的Result
        Result result = Result.error("对不起,操作失败,请联系管理员");
        return result;
    }

    @ExceptionHandler
    public Result ex(DataIntegrityViolationException e){
        e.printStackTrace();
        Result result = Result.error("对不起，当前部门下有员工，不能直接删除！");
        return result;
    }

    @ExceptionHandler
    public Result ex(SQLIntegrityConstraintViolationException e){
        e.printStackTrace();
        Result result = Result.error(e.getMessage());
        return result;
    }
    
}