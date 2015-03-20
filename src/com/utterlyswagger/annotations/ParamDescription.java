package com.utterlyswagger.annotations;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Repeatable(ParamDescriptions.class)
public @interface ParamDescription {
    java.lang.String name();

    java.lang.String description();
}
