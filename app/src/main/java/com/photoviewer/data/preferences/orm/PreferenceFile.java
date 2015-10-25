package com.photoviewer.data.preferences.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PreferenceFile {

    /**
     * @return the desired name of the field when it is serialized
     */
    String name();
}

