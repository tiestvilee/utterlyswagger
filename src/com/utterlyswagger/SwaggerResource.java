package com.utterlyswagger;

import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.Response;

import static com.googlecode.totallylazy.json.Json.json;
import static com.googlecode.utterlyidle.ResponseBuilder.response;
import static com.googlecode.utterlyidle.Status.OK;
import static com.utterlyswagger.builder.SwaggerV2.swagger;

public class SwaggerResource {

    private final SwaggerInfo info;
    private final Resources resources;
    private final TargetEndpointBaseLocation targetEndpointBaseLocation;

    public SwaggerResource(SwaggerInfo info, Resources resources, TargetEndpointBaseLocation targetEndpointBaseLocation) {
        this.info = info;
        this.resources = resources;
        this.targetEndpointBaseLocation = targetEndpointBaseLocation;
    }

    public Response version2() {
        return response(OK)
            .entity(json(swagger(info, targetEndpointBaseLocation, resources)))
            .build();
    }

}
