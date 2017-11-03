package com.lhx.mvc.ioc.annotation.filed;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Autowired {
    String value() default "";
}
