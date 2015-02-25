package com.utterlyswagger.builder;

import com.googlecode.totallylazy.*;
import com.googlecode.utterlyidle.Binding;
import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.bindings.actions.ResourceMethod;
import com.utterlyswagger.SwaggerInfo;
import com.utterlyswagger.annotations.*;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Swagger {
    public static Map<String, Object> swagger(SwaggerInfo info, Resources resources) {
        return map(
            "swagger", "2.0",
            "info", info.asMap(),
            "paths", paths(resources)
        );
    }

    public static Map<String, Map<String, Object>> paths(Resources resources) {
        return sequence(resources)
            .filter(binding -> !binding.hidden())
            .map(binding -> pair("/" + binding.uriTemplate(), pathItem(binding)))
            .foldLeft(map(), Swagger::foldInOperationObjects);
    }

    public static Pair<String, Object> pathItem(Binding binding) {
        return pair(
            binding.httpMethod().toLowerCase(),
            operationObject(binding, annotationsFor(binding)));
    }

    private static Map<String, Object> operationObject(Binding binding, Sequence<Annotation> annotations) {
        return map(
            "produces", binding.produces().toList(),
            "summary", summary(annotations),
            "description", description(annotations),
            "responses", responses(annotations));
    }

    private static String summary(Sequence<Annotation> annotations) {
        return getAnnotationValue(annotations, "", Summary.class, summary -> ((Summary) summary).value());
    }

    private static String description(Sequence<Annotation> annotations) {
        return getAnnotationValue(annotations, "", Description.class, description -> ((Description) description).value());
    }

    private static Map<String, Object> responses(Sequence<Annotation> annotations) {
        Map<String, Object> defaultResponse = map(
            "default", map(
                "description", "successful operation"));

        return getAnnotationValue(annotations, defaultResponse, ResponseDescriptions.class,
            descriptions ->
                map(
                    sequence(((ResponseDescriptions) descriptions).value())
                        .map(desc ->
                            pair(desc.status(), (Object) map("description", desc.description())))));
    }

    private static Sequence<Annotation> annotationsFor(Binding binding) {
        return sequence(
            sequence(binding.action().metaData())
                .filter(metaData -> metaData instanceof ResourceMethod)
                .map(metaData -> (ResourceMethod) metaData)
                .map(ResourceMethod::value)
                .head()
                .getAnnotations());
    }

    private static <T> T getAnnotationValue(Sequence<Annotation> annotations, T defaultResult, Class aClass, Callable1<Annotation, T> getValue) {
        return annotations
            .find(annotation -> annotation.annotationType().equals(aClass))
            .map(getValue)
            .getOrElse(defaultResult);
    }

    private static Map<String, Map<String, Object>> foldInOperationObjects(Map<String, Map<String, Object>> acc, Pair<String, Pair<String, Object>> pair) {
        String uriTemplate = pair.getKey();

        Pair<String, Object> pathItem = pair.getValue();
        String httpVerb = pathItem.getKey();
        Object operationObject = pathItem.getValue();

        if (acc.containsKey(uriTemplate)) {
            acc.get(uriTemplate).put(httpVerb, operationObject);
        } else {
            acc.put(uriTemplate, map(httpVerb, operationObject));
        }
        return acc;
    }
}
