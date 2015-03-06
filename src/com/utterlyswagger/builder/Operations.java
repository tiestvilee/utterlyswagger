package com.utterlyswagger.builder;

import com.googlecode.totallylazy.*;
import com.googlecode.utterlyidle.Binding;
import com.googlecode.utterlyidle.NamedParameter;
import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.bindings.actions.ResourceMethod;
import com.utterlyswagger.annotations.Description;
import com.utterlyswagger.annotations.ResponseDescription;
import com.utterlyswagger.annotations.ResponseDescriptions;
import com.utterlyswagger.annotations.Summary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static com.googlecode.totallylazy.Callables.when;
import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Option.option;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Predicates.instanceOf;
import static com.googlecode.totallylazy.Predicates.not;
import static com.googlecode.totallylazy.Predicates.second;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Operations {

    public static Map<String, Sequence<Operation>> operationsFor(Resources resources) {
        return squashIntoMap(sequence(resources)
                .filter(binding -> !binding.hidden())
                .map(binding ->
                    pair(
                        operationPath(binding),
                        operation(binding)
                    ))
        );
    }

    private static Map<String, Sequence<Operation>> squashIntoMap(Sequence<Pair<String, Operation>> map) {
        return map
            .foldLeft(map(),
                (acc, pair) -> {
                    if(acc.containsKey(pair.first())) {
                        acc.put(pair.first(), acc.get(pair.first()).append(pair.second()));
                    } else {
                        acc.put(pair.first(), sequence(pair.second()));
                    }
                    return acc;
                });
    }

    private static String operationPath(Binding binding) {return "/" + binding.uriTemplate();}

    private static Operation operation(Binding binding) {
        Method resourceMethod = getResourceMethod(binding);
        return new Operation(
            operationPath(binding),
            methodNameFor(resourceMethod),
            binding.httpMethod(),
            description(annotationsFor(resourceMethod)), summary(annotationsFor(resourceMethod)), binding.produces().toList(), responses(annotationsFor(resourceMethod)), parameters(binding.parameters()));
    }

    private static Sequence<Annotation> annotationsFor(Method method) {
        return sequence(method.getAnnotations());
    }

    private static String methodNameFor(Method method) {
        return method.getName();
    }

    private static Method getResourceMethod(Binding binding) {
        return sequence(binding.action().metaData())
            .filter(metaData -> metaData instanceof ResourceMethod)
            .map(metaData -> (ResourceMethod) metaData)
            .map(ResourceMethod::value)
            .head();
    }

    public static String summary(Sequence<Annotation> annotations) {
        return getAnnotationValue(annotations, "", Summary.class, summary -> ((Summary) summary).value());
    }


    public static String description(Sequence<Annotation> annotations) {
        return getAnnotationValue(annotations, "", Description.class, description -> ((Description) description).value());
    }

    private static Sequence<ResponseDescription> responses(Sequence<Annotation> annotations) {
        return getAnnotationValue(annotations, sequence(DEFAULT_RESPONSE_DESCRIPTION), ResponseDescriptions.class,
            descriptions -> sequence(((ResponseDescriptions) descriptions).value()));
    }

    private static <T> T getAnnotationValue(Sequence<Annotation> annotations, T defaultResult, Class aClass, Callable1<Annotation, T> getValue) {
        return annotations
            .find(annotation -> annotation.annotationType().equals(aClass))
            .map(getValue)
            .getOrElse(defaultResult);
    }

    private static Sequence<Parameter> parameters(Sequence<Pair<Type, Option<com.googlecode.utterlyidle.Parameter>>> parameters) {
        return parameters.map(Operations::parameter);
    }

    private static Parameter parameter(Pair<Type, Option<com.googlecode.utterlyidle.Parameter>> paramPair) {
        NamedParameter param = (NamedParameter) paramPair.second().get();
        Type type = paramPair.first();
        return new Parameter(
            param.name(),
            param.parametersClass().getSimpleName(),
            notOptional(type),
            typeFor(type));
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

    public static Map<String, Object> realiseMap(Pair<String, Object>... pairs) {
        return map(
            sequence(pairs)
                .filter(not(second(instanceOf(None.class))))
                .map(when(
                    pair -> pair.second() instanceof Option,
                    pair -> pair(pair.first(), ((Option) pair.second()).get()))));
    }

    public static class Operation {
        public final String path;
        public final String method;
        public final String javaMethodName;
        public final String description;
        public final String summary;
        public final List<String> produces;
        public final Sequence<ResponseDescription> responses;
        public final Sequence<Parameter> parameters;

        public Operation(String path, String javaMethodName, String method, String description, String summary, List<String> produces, Sequence<ResponseDescription> responses, Sequence<Parameter> parameters) {
            this.path = path;
            this.javaMethodName = javaMethodName;
            this.method = method;
            this.description = description;
            this.summary = summary;
            this.produces = produces;
            this.responses = responses;
            this.parameters = parameters;
        }

        @Override
        public String toString() {
            return "Operation{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", javaMethodName='" + javaMethodName + '\'' +
                ", description='" + description + '\'' +
                ", summary='" + summary + '\'' +
                ", produces=" + produces +
                ", responses=" + responses +
                ", parameters=" + parameters +
                '}';
        }
    }

    public static class Parameter {
        public final String name;
        public final String paramType;
        public final boolean required;
        public final String type;

        public Parameter(String name, String paramType, boolean required, String type) {
            this.name = name;
            this.paramType = paramType;
            this.required = required;
            this.type = type;
        }

        @Override
        public String toString() {
            return "Parameter{" +
                "name='" + name + '\'' +
                ", paramType='" + paramType + '\'' +
                ", required=" + required +
                ", type='" + type + '\'' +
                '}';
        }
    }

    public static final ResponseDescription DEFAULT_RESPONSE_DESCRIPTION = new ResponseDescription() {
        @Override
        public Class<? extends Annotation> annotationType() { return ResponseDescription.class; }

        @Override
        public String status() { return "200"; }

        @Override
        public String description() { return "successful operation"; }
    };
}