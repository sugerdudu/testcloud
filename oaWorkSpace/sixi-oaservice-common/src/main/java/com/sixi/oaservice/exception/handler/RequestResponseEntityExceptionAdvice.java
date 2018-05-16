package com.sixi.oaservice.exception.handler;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class RequestResponseEntityExceptionAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("------ GlobalExceptionHandler -----");
        ex.printStackTrace();
        System.out.println("====== GlobalExceptionHandler =====");

        List<ObjectError> list = ex.getAllErrors();

        if (list.size()>0){
            return handleExceptionInternal(ex, list.get(0).getDefaultMessage(), headers, status, request);
        }

        return handleExceptionInternal(ex, ex.getMessage(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("------ MethodArgumentExceptionHandler -----");
        ex.printStackTrace();
        System.out.println("====== MethodArgumentExceptionHandler =====");

        List<ObjectError> list = ex.getBindingResult().getAllErrors();

        if (list.size()>0){
            return handleExceptionInternal(ex, list.get(0).getDefaultMessage(), headers, status, request);
        }

        return handleExceptionInternal(ex, ex.getMessage(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(
                ex,
                String.format("参数类型不匹配 值 %s 无法转换为 %s",ex.getValue(),ex.getRequiredType().getSimpleName()),
                headers, HttpStatus.OK, request);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, "无法读取请求数据 请检查入参数据是否正确!",
                headers, HttpStatus.OK, request);
    }

}
