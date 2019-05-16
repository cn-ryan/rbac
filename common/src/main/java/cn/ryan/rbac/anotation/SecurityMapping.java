package cn.ryan.rbac.anotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface SecurityMapping
{
    String title() default "";

    String value() default "";

    String mname() default "";

    String mcode() default "";

    int rsequence() default 0;

    String mgroup() default "";

    String mapp() default "";

    String mtype() default "";

    boolean entrance() default false;

    boolean display() default true;
}
