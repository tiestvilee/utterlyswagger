package com.utterlyswagger;

import com.googlecode.utterlyidle.MediaType;
import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.modules.ApplicationScopedModule;
import com.googlecode.utterlyidle.modules.ResourcesModule;
import com.googlecode.yadic.Container;

import static com.googlecode.totallylazy.proxy.Call.method;
import static com.googlecode.totallylazy.proxy.Call.on;
import static com.googlecode.utterlyidle.dsl.BindingBuilder.get;

public class SwaggerModule implements ResourcesModule, ApplicationScopedModule {

    private final SwaggerInfo info;
    private final String swaggerBasePath;

    public SwaggerModule(SwaggerInfo info) {
        this(info, "/swagger");
    }

    public SwaggerModule(SwaggerInfo info, String swaggerBasePath) {
        this.info = info;
        this.swaggerBasePath = swaggerBasePath;
    }

    @Override
    public Resources addResources(Resources resources) throws Exception {
        return resources
            .add(get(swaggerBasePath + "/swagger_v2.json")
                .hidden(true)
                .produces(MediaType.APPLICATION_JSON)
                .resource(method(on(SwaggerResource.class).version2()))
                .build())
            .add(get(swaggerBasePath + "/swagger_v1.2.json")
                .hidden(true)
                .produces(MediaType.APPLICATION_JSON)
                .resource(method(on(SwaggerResource.class).version1()))
                .build());
    }

    @Override
    public Container addPerApplicationObjects(Container container) throws Exception {
        return container
            .addInstance(SwaggerInfo.class, info);
    }
}
