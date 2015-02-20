package com.utterlyswagger;

import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.modules.ApplicationScopedModule;
import com.googlecode.utterlyidle.modules.ResourcesModule;
import com.googlecode.yadic.Container;

import static com.googlecode.utterlyidle.annotations.AnnotatedBindings.annotatedClass;

public class SwaggerModule implements ResourcesModule, ApplicationScopedModule {

    private final SwaggerInfo info;

    public SwaggerModule(SwaggerInfo info) {
        this.info = info;
    }

    @Override
    public Resources addResources(Resources resources) throws Exception {
        return resources.add(annotatedClass(SwaggerResource.class));
    }

    @Override
    public Container addPerApplicationObjects(Container container) throws Exception {
        return container.addInstance(SwaggerInfo.class, info);
    }
}
