package com.lhx.aggregate.authority;

import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 权限注解，绑定在Controller或Controller的方法上
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Permission {
    int id() default 0;//权限id
    int parent() default 0;//权限父级id
    int type() default PermissionInfo.TYPE_BUTTON;//权限类型,节点还是菜单还是按钮,默认按钮
    String name() default "";//权限名称
    String icon() default "";//权限图标,菜单图标,往往节点和菜单需要
    int sort() default 0;//显示顺序
}
