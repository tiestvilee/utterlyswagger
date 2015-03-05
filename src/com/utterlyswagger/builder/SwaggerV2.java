package com.utterlyswagger.builder;

import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.None;
import com.googlecode.totallylazy.Option;
import com.googlecode.totallylazy.Pair;
import com.googlecode.totallylazy.Sequence;
import com.googlecode.utterlyidle.Binding;
import com.googlecode.utterlyidle.NamedParameter;
import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.bindings.actions.ResourceMethod;
import com.utterlyswagger.SwaggerInfo;
import com.utterlyswagger.TargetEndpointBaseLocation;
import com.utterlyswagger.annotations.Description;
import com.utterlyswagger.annotations.ResponseDescriptions;
import com.utterlyswagger.annotations.Summary;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.googlecode.totallylazy.Callables.when;
import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Predicates.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class SwaggerV2 {

    public static Map<String, Object> swagger(SwaggerInfo info, TargetEndpointBaseLocation targetEndpointBaseLocation, Resources resources) {
        return mapWithoutOptions(
            pair("swagger", "2.0"),
            pair("info", swaggerInfo(info)),
            pair("paths", paths(resources)),
            pair("basePath", (Object) targetEndpointBaseLocation.basePath),
            pair("host", (Object) targetEndpointBaseLocation.host)
        );
    }

    public static Map<String, Object> swaggerInfo(SwaggerInfo info) {
        return mapWithoutOptions(
            pair("title", (Object) info.title),
            pair("version", info.apiVersion),
            pair("description", info.description()),
            pair("termsOfService", info.termsOfService()),
            pair("contact", mapWithoutOptions(
                pair("email", info.contactEmail()))),
            pair("license", mapWithoutOptions(
                pair("name", info.licenceName()),
                pair("url", info.licenceUrl()))));
    }

    private static Map<String, Object> mapWithoutOptions(Pair<String, Object>... pairs) {
        return map(
            sequence(pairs)
                .filter(not(second(instanceOf(None.class))))
                .map(when(
                    pair -> pair.second() instanceof Option,
                    pair -> pair(pair.first(), ((Option) pair.second()).get()))));
    }


    public static Map<String, Map<String, Object>> paths(Resources resources) {
        return sequence(resources)
            .filter(binding -> !binding.hidden())
            .map(binding -> pair("/" + binding.uriTemplate(), pathItem(binding)))
            .foldLeft(map(), SwaggerV2::foldInOperationObjects);
    }

    public static Pair<String, Object> pathItem(Binding binding) {
        return pair(
            binding.httpMethod().toLowerCase(),
            operationObject(binding, annotationsFor(binding)));
    }

    private static Map<String, Object> operationObject(Binding binding, Sequence<Annotation> annotations) {
        return map(
            "summary", summary(annotations),
            "description", description(annotations),
            "produces", binding.produces().toList(),
            "parameters", parameters(binding.namedParameters()),
            "responses", responses(annotations));
    }

    private static Sequence<Map<String, Object>> parameters(Sequence<NamedParameter> parameters) {
        return parameters
            .map(param -> parameter(param));
    }

    private static Map<String, Object> parameter(NamedParameter param) {
        return map(
            "name", param.name()
        );
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

        return getAnnotationValue(annotations, defaultResponse, ResponseDescriptions.class, descriptions ->
            map(sequence(((ResponseDescriptions) descriptions).value())
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