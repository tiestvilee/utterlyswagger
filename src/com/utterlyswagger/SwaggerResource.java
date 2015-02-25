package com.utterlyswagger;

import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.Response;

import static com.googlecode.totallylazy.json.Json.json;
import static com.googlecode.utterlyidle.ResponseBuilder.response;
import static com.googlecode.utterlyidle.Status.OK;
import static com.utterlyswagger.builder.Swagger.swagger;

public class SwaggerResource {

    private final SwaggerInfo info;
    private final Resources resources;

    public SwaggerResource(SwaggerInfo info, Resources resources) {
        this.info = info;
        this.resources = resources;
    }

    public Response version2() {
        return response(OK)
            .entity(json(swagger(info, resources)))
            .build();
    }

}
