package com.utterlyswagger.annotations;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)

public @interface ResponseDescriptions {
    ResponseDescription[] value();
}
