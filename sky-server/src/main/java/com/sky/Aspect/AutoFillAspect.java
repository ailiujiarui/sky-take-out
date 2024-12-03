package com.sky.Aspect;


import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/*
* 自动填充的处理逻辑
* */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /*
    * 切入点
    * */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut(){
    }
    /*
    前置通知为公共字段赋值
    * */
    @Before("autoFillPointcut()")
    public void before(JoinPoint joinPoint){
        log.info("自动填充执行");

        //先获取注解中的输入值
        MethodSignature methodSignature =(MethodSignature) joinPoint.getSignature();
        AutoFill autoFill =methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType value = autoFill.value();

        //获取操作对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length== 0){
            return;
        }
        Object ob = args[0];//获取第一个参数，也就是实体类对象

        //设置公共字段的值
        LocalDateTime time = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        //根据类型的值操作不同的字段
        if(value == OperationType.INSERT){
            try {
                Method setupdateTime = ob.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method user = ob.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                Method setcreateTime = ob.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setcreateUser = ob.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                user.invoke(ob,id);
                setupdateTime.invoke(ob,time);
                setcreateTime.invoke(ob,time);//通过反射来对这个对象调用该方法
                setcreateUser.invoke(ob,id);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else if (value==OperationType.UPDATE) {
            try {
                Method setupdateTime = ob.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method user = ob.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                user.invoke(ob,id);
                setupdateTime.invoke(ob,time);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
