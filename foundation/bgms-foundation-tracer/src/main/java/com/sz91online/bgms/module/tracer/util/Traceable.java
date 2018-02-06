
package com.sz91online.bgms.module.tracer.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Traceable {
    String idField() default "id";

    String nameField();

    String extraFieldName() default "";
}
