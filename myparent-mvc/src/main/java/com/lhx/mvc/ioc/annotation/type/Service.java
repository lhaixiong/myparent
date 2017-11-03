package com.lhx.mvc.ioc.annotation.type;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Bean
public @interface Service {
    String value() default "";
}
