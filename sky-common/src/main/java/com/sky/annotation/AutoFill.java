package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* 用于标识方法，用于公共字段的填充处理
* */
@Target(ElementType.METHOD)//作用位置，在方法上
@Retention(RetentionPolicy.RUNTIME)//生命周期
public @interface AutoFill {

    OperationType value();
}
