package com.utterlyswagger.builder;

import com.googlecode.totallylazy.*;
import com.googlecode.utterlyidle.*;
import com.googlecode.utterlyidle.bindings.actions.ResourceMethod;
import com.utterlyswagger.SwaggerInfo;
import com.utterlyswagger.TargetEndpointBaseLocation;
import com.utterlyswagger.annotations.Description;
import com.utterlyswagger.annotations.ResponseDescriptions;
import com.utterlyswagger.annotations.Summary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.googlecode.totallylazy.Callables.when;
import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Option.*;
import static com.googlecode.totallylazy.Option.some;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Predicates.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class SwaggerV1_2 {

    public static Map<String, Object> swaggerV1_2(SwaggerInfo info, TargetEndpointBaseLocation targetEndpointBaseLocation, Resources resources) {
        return mapWithoutOptions(
            pair("swaggerVersion", "1.2"),
            pair("info", swaggerInfo(info)),
            pair("apis", squash(apis(resources))),
            pair("basePath", urlFor(targetEndpointBaseLocation.host, targetEndpointBaseLocation.basePath)),
            pair("apiVersion", info.apiVersion)
        );
    }

    private static Sequence<Map<String, Object>> squash(Sequence<Map<String, Object>> apis) {
        return sequence(apis.foldLeft(new HashMap<String, Map<String, Object>>(),
            (acc, api) -> {
                String path = api.get("path").toString();
                if (acc.containsKey(path)) {
                    Sequence updatedOperations = ((Sequence) acc.get(path).get("operations")).append(((Sequence) api.get("operations")).head());
                    acc.get(path).put("operations", updatedOperations);
                } else {
                    acc.put(path, api);
                }
                return acc;
            }
        ).values());
    }

    private static Option<String> urlFor(Option<String> host, Option<String> basePath) {
        try {
            return some(new URL("http", host.getOrElse(""), basePath.getOrElse("")).toString());
        } catch (MalformedURLException e) {
            return none();
        }
    }

    public static Map<String, Object> swaggerInfo(SwaggerInfo info) {
        return mapWithoutOptions(
            pair("title", (Object) info.title),
            pair("description", info.description()),
            pair("termsOfServiceUrl", info.termsOfService()),
            pair("contact", info.contactEmail()),
            pair("license", info.licenceName()),
            pair("licenseUrl", info.licenceUrl()));
    }

    private static Map<String, Object> mapWithoutOptions(Pair<String, Object>... pairs) {
        return map(
            sequence(pairs)
                .filter(not(second(instanceOf(None.class))))
                .map(when(
                    pair -> pair.second() instanceof Option,
                    pair -> pair(pair.first(), ((Option) pair.second()).get()))));
    }


    public static Sequence<Map<String, Object>> apis(Resources resources) {
        return sequence(resources)
            .filter(binding -> !binding.hidden())
            .map(binding -> apiObject(binding));
    }

    private static Map<String, Object> apiObject(Binding binding) {
        return map(
            "path", "/" + binding.uriTemplate(),
            "operations", sequence(operationObject(binding, annotationsFor(binding)))
        );
    }

    private static Map<String, Object> operationObject(Binding binding, Sequence<Annotation> annotations) {
        return map(sequence(
            pair("method", (Object) binding.httpMethod().toUpperCase()),
            pair("nickname", methodNameFor(binding)),
            pair("notes", description(annotations)),
            pair("summary", summary(annotations)),
            pair("produces", binding.produces().toList()),
            pair("parameters", parameters(binding.parameters())),
            pair("responseMessages", responses(annotations))
        ));
    }

    private static Sequence<Map<String, Object>> parameters(Sequence<Pair<Type, Option<Parameter>>> parameters) {
        return parameters.map(SwaggerV1_2::parameter);
    }

    private static Map<String, Object> parameter(Pair<Type, Option<Parameter>> paramPair) {
        NamedParameter param = (NamedParameter) paramPair.second().get();
        Type type = paramPair.first();
        return map(
            "name", param.name(),
            "paramType", paramLocation(param.parametersClass()),
            "required", notOptional(type),
            "type", typeFor(type)
        );
    }

    private static Map<String, String> typeMap = map(sequence(
        pair("java.lang.String", "string"),
        pair("java.lang.Byte", "integer"),
        pair("java.lang.Short", "integer"),
        pair("java.lang.Integer", "integer"),
        pair("java.lang.Long", "integer"),
        pair("java.lang.Float", "number"),
        pair("java.lang.Double", "number"),
        pair("java.lang.Boolean", "boolean")
    ));

    public static String typeFor(Type type) {
        return option(typeMap.get(typeWithOption(type)))
            .getOrElse("unknown: " + typeWithOption(type));
    }

    private static String typeWithOption(Type type) {
        String typeName = type.getTypeName();
        String optionName = Option.class.getCanonicalName();
        return typeName.startsWith(optionName)
            ? typeName.substring(optionName.length() + 1, typeName.length() - 1)
            : typeName;
    }

    private static boolean notOptional(Type type) {return !type.getTypeName().startsWith(Option.class.getCanonicalName());}

    private static Map<String, String> paramLocation = map(
        "PathParameters", "path",
        "CookieParameters", "cookie",
        "QueryParameters", "query",
        "FormParameters", "form",
        "HeaderParameters", "header"
    );

    private static String paramLocation(Class<? extends Parameters<String, String, ?>> aClass) {
        return option(paramLocation.get(aClass.getSimpleName()))
            .getOrElse("unknown");
    }

    private static String summary(Sequence<Annotation> annotations) {
        return getAnnotationValue(annotations, "", Summary.class, summary -> ((Summary) summary).value());
    }

    private static String description(Sequence<Annotation> annotations) {
        return getAnnotationValue(annotations, "", Description.class, description -> ((Description) description).value());
    }

    private static Sequence<Map<String, Object>> responses(Sequence<Annotation> annotations) {
        Sequence<Map<String, Object>> defaultResponse = sequence(map(
            "code", 200,
            "message", "successful operation"));

        return getAnnotationValue(annotations, defaultResponse, ResponseDescriptions.class, descriptions ->
            sequence(((ResponseDescriptions) descriptions).value())
                .map(desc ->
                    map("code", (Object) Integer.parseInt(desc.status()),
                        "message", desc.description())));
    }

    private static Sequence<Annotation> annotationsFor(Binding binding) {
        return sequence(getResourceMethod(binding).getAnnotations());
    }

    private static String methodNameFor(Binding binding) {
        return getResourceMethod(binding).getName();
    }

    private static Method getResourceMethod(Binding binding) {
        return sequence(binding.action().metaData())
            .filter(metaData -> metaData instanceof ResourceMethod)
            .map(metaData -> (ResourceMethod) metaData)
            .map(ResourceMethod::value)
            .head();
    }

    private static <T> T getAnnotationValue(Sequence<Annotation> annotations, T defaultResult, Class aClass, Callable1<Annotation, T> getValue) {
        return annotations
            .find(annotation -> annotation.annotationType().equals(aClass))
            .map(getValue)
            .getOrElse(defaultResult);
    }
}
