package com.banshion.intf;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by zhang.rw on 16-4-9.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatisRepository {
    String value() default "";
}