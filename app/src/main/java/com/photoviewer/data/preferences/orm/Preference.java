package com.photoviewer.data.preferences.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Preference {

    /**
     * @return the desired name of the field when it is serialized
     */
    String name() default "";
    /**
     * @return the state of the field serialization - false - if it will not be serialized
     */
    boolean enabled() default true;
}
