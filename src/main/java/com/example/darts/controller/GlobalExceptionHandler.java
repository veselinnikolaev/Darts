package com.example.darts.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler({Throwable.class})
//    public ModelAndView handleException(Throwable e){
//        Throwable throwable = e;
//
//        if(throwable.getCause() != null){
//            throwable = throwable.getCause();
//        }
//
//        return new ModelAndView("/common/error")
//                .addObject("message", String.join(" ", Arrays.stream(throwable.getStackTrace()).map(StackTraceElement::toString).toList()));
//    }
//}
