package com.utterlyswagger;

import com.googlecode.totallylazy.Sequence;
import com.googlecode.utterlyidle.*;
import com.googlecode.utterlyidle.annotations.*;
import com.googlecode.utterlyidle.bindings.actions.ResourceMethod;
import com.utterlyswagger.annotations.Summary;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.json.Json.json;
import static com.googlecode.utterlyidle.ResponseBuilder.response;
import static com.googlecode.utterlyidle.Status.OK;

public class SwaggerResource {

    private final SwaggerInfo info;
    private final Resources resources;

    public SwaggerResource(SwaggerInfo info, Resources resources) {
        this.info = info;
        this.resources = resources;
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
            "info", info.asMap(),
            "paths", paths(resources)
        ));
    }

    public static Map<String, Object> paths(Resources resources) {
        return map(sequence(resources)
            .filter(binding -> !binding.hidden())
            .map(binding -> pair("/" + binding.uriTemplate(), swaggerPath(binding))));
    }

    public static Map<String, Object> swaggerPath(Binding binding) {
        Sequence<Annotation> annotations = sequence(
            sequence(binding.action().metaData())
                .filter(metaData -> metaData instanceof ResourceMethod)
                .map(metaData -> (ResourceMethod) metaData)
                .map(resourceMethod -> resourceMethod.value())
                .head()
                .getAnnotations());

        return map(binding.httpMethod().toLowerCase(), map(
            "produces", binding.produces().toList(),
            "summary", getSummary(annotations),
            "responses", map(
                "default", map(
                    "description", "successful operation"))));
    }

    private static String getSummary(Sequence<Annotation> annotations) {
        return annotations
            .find(annotation -> annotation.annotationType().equals(Summary.class))
            .map(summary -> ((Summary) summary).value()).getOrElse("No summary supplied");
    }
}
