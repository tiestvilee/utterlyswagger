package com.utterlyswagger;

import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.Response;

import static com.googlecode.totallylazy.json.Json.json;
import static com.googlecode.utterlyidle.ResponseBuilder.response;
import static com.googlecode.utterlyidle.Status.OK;
import static com.utterlyswagger.builder.Operations.operationsFor;
import static com.utterlyswagger.builder.SwaggerV1_2.swaggerV1_2;
import static com.utterlyswagger.builder.SwaggerV2.swaggerV2;

public class SwaggerResource {

    private final SwaggerInfo info;
    private final Resources resources;

    public SwaggerResource(SwaggerInfo info, Resources resources) {
        this.info = info;
        this.resources = resources;
    }

    public Response version2() {
        return response(OK)
            .entity(json(swaggerV2(info, operationsFor(resources))))
            .build();
    }

    public Response version1() {
        return response(OK)
            .entity(json(swaggerV1_2(info, operationsFor(resources))))
            .build();
    }

}
