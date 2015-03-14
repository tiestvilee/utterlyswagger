package com.utterlyswagger.example.example;

import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.modules.ResourcesModule;

import static com.googlecode.utterlyidle.annotations.AnnotatedBindings.annotatedClass;

public class ExampleModule implements ResourcesModule {
    @Override
    public Resources addResources(Resources resources) throws Exception {
        return resources.add(annotatedClass(ExampleResource.class));
    }
}
