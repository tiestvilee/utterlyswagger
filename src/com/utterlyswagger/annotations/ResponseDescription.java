package com.utterlyswagger.annotations;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Repeatable(ResponseDescriptions.class)
public @interface ResponseDescription {
    java.lang.String status();

    java.lang.String description();
}
