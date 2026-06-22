package pw.base;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})  
public @interface TestMeta {

    UserType user() default UserType.NONE;

    String navPath() default "";

    String feature() default "";

    String env() default "";
}