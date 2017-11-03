package com.lhx.mvc.ioc.annotation.type;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Bean {
    String value() default "";
}
