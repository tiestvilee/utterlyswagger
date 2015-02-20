package com.utterlyswagger;

import com.googlecode.utterlyidle.MediaType;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.annotations.*;

import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.json.Json.json;
import static com.googlecode.utterlyidle.ResponseBuilder.response;
import static com.googlecode.utterlyidle.Status.OK;

public class SwaggerResource {

    private final SwaggerInfo info;

    public SwaggerResource(SwaggerInfo info) {
        this.info = info;
    }

    @GET
    @Path("/v2/swagger.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response swagIt() {
        return response(OK)
            .entity(buildJson())
            .build();
    }

    private String buildJson() {
        return json(map(
            "swagger", "2.0",
            "basePath", "/v2",
            "info", info.asMap()));
    }
}
