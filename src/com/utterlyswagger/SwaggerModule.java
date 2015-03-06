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
    private final TargetEndpointBaseLocation targetEndpointBaseLocation;
    private final String basePath;

    public SwaggerModule(SwaggerInfo info, TargetEndpointBaseLocation targetEndpointBaseLocation) {
        this(info, targetEndpointBaseLocation, "/swagger");
    }

    public SwaggerModule(SwaggerInfo info, TargetEndpointBaseLocation targetEndpointBaseLocation, String basePath) {
        this.info = info;
        this.targetEndpointBaseLocation = targetEndpointBaseLocation;
        this.basePath = basePath;
    }

    @Override
    public Resources addResources(Resources resources) throws Exception {
        return resources
            .add(get(basePath + "/swagger_v2.json")
                .hidden(true)
                .produces(MediaType.APPLICATION_JSON)
                .resource(method(on(SwaggerResource.class).version2()))
                .build())
            .add(get(basePath + "/swagger_v1.2.json")
                .hidden(true)
                .produces(MediaType.APPLICATION_JSON)
                .resource(method(on(SwaggerResource.class).version1()))
                .build());
    }

    @Override
    public Container addPerApplicationObjects(Container container) throws Exception {
        return container
            .addInstance(SwaggerInfo.class, info)
            .addInstance(TargetEndpointBaseLocation.class, targetEndpointBaseLocation);
    }
}
