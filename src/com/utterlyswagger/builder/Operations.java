package com.utterlyswagger.builder;

import com.googlecode.totallylazy.*;
import com.googlecode.utterlyidle.Binding;
import com.googlecode.utterlyidle.NamedParameter;
import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.bindings.actions.ResourceMethod;
import com.utterlyswagger.annotations.Description;
import com.utterlyswagger.annotations.ParamDescription;
import com.utterlyswagger.annotations.ParamDescriptions;
import com.utterlyswagger.annotations.RequestBody;
import com.utterlyswagger.annotations.ResponseDescription;
import com.utterlyswagger.annotations.ResponseDescriptions;
import com.utterlyswagger.annotations.Summary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import static com.googlecode.totallylazy.Callables.when;
import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Option.none;
import static com.googlecode.totallylazy.Option.option;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Predicates.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Operations {

    public static Map<String, Sequence<Operation>> operationsFor(Resources resources) {
        return squashIntoMap(sequence(resources)
            .filter(binding -> !binding.hidden())
            .map(binding ->
                pair(
                    operationPath(binding),
                    operation(binding)
                )));
    }

    private static Map<String, Sequence<Operation>> squashIntoMap(Sequence<Pair<String, Operation>> map) {
        return map.foldLeft(map(),
            (acc, pair) -> {
                if (acc.containsKey(pair.first())) {
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
        Sequence<Annotation> annotations = sequence(resourceMethod.getAnnotations());
        return new Operation(
            operationPath(binding),
            resourceMethod.getName(),
            binding.httpMethod(),
            description(annotations),
            summary(annotations),
            binding.produces().toList(),
            responses(annotations),
            parameters(annotations, binding.parameters()));
    }

    private static Method getResourceMethod(Binding binding) {
        return sequence(binding.action().metaData())
            .filter(metaData -> metaData instanceof ResourceMethod)
            .map(metaData -> (ResourceMethod) metaData)
            .map(ResourceMethod::value)
            .head();
    }

    public static String summary(Sequence<Annotation> annotations) {
        return getAnnotationValue(annotations, "", Summary.class,
            summary -> ((Summary) summary).value());
    }

    public static String description(Sequence<Annotation> annotations) {
        return getAnnotationValue(annotations, "", Description.class,
            description -> ((Description) description).value());
    }

    private static Sequence<ResponseDescription> responses(Sequence<Annotation> annotations) {
        Sequence<ResponseDescription> multipleResponses = getAnnotationValue(annotations, Sequences.empty(ResponseDescription.class), ResponseDescriptions.class,
            responseDescriptions -> sequence(((ResponseDescriptions) responseDescriptions).value()));
        Sequence<ResponseDescription> singleResponse = getAnnotationValue(annotations, Sequences.empty(ResponseDescription.class), ResponseDescription.class,
            responseDescriptions -> sequence((ResponseDescription) responseDescriptions));
        Sequence<ResponseDescription> allResponses = multipleResponses.join(singleResponse);
        return allResponses.isEmpty()
            ? sequence(DEFAULT_RESPONSE_DESCRIPTION)
            : allResponses;
    }

    private static <T> T getAnnotationValue(Sequence<Annotation> annotations, T defaultResult, Class aClass, Callable1<Annotation, T> getValue) {
        return annotations
            .find(annotation -> annotation.annotationType().equals(aClass))
            .map(getValue)
            .getOrElse(defaultResult);
    }

    private static Sequence<Parameter> parameters(Sequence<Annotation> annotations, Sequence<Pair<Type, Option<com.googlecode.utterlyidle.Parameter>>> parameters) {
        Sequence<Parameter> methodParameters = methodParameters(annotations, parameters);
        return bodyParameter(annotations)
            .map(body -> methodParameters.append(body))
            .getOrElse(methodParameters);
    }

    private static Sequence<Parameter> methodParameters(Sequence<Annotation> annotations, Sequence<Pair<Type, Option<com.googlecode.utterlyidle.Parameter>>> parameters) {

        Sequence<ParamDescription> descriptions = annotations
            .find(instanceOf(ParamDescriptions.class))
            .map(paramDescriptions -> sequence(((ParamDescriptions) paramDescriptions).value()))
            .getOrElse(sequence());

        Sequence<ParamDescription> singleDescription = annotations
            .find(instanceOf(ParamDescription.class))
            .map(description -> sequence((ParamDescription) description))
            .getOrElse(sequence());

        return parameters
            .map(parameter -> parameter(parameter, singleDescription.join(descriptions)))
            .filter(Option::isDefined)
            .map(Option::get);
    }

    private static Option<Parameter> bodyParameter(Sequence<Annotation> annotations) {
        return annotations
            .find(instanceOf(RequestBody.class))
            .map(annotation -> (RequestBody) annotation)
            .map(annotation -> new Parameter(
                "body", "body", none(), none(),
                annotation.value().equals(RequestBody.UNDEFINED)
                    ? none()
                    : option(annotation.value())));
    }

    private static Option<Parameter> parameter(Pair<Type, Option<com.googlecode.utterlyidle.Parameter>> paramPair, Sequence<ParamDescription> descriptions) {
        Type type = paramPair.first();
        return paramPair.second()
            .filter(instanceOf(NamedParameter.class))
            .map(param -> (NamedParameter) param)
            .map(param -> new Parameter(
                    param.name(),
                    annotationClassOf(param),
                    option(notOptional(type)),
                    option(typeFor(type)),
                    paramDescriptionFor(param.name(), descriptions))
            );
    }

    private static Option<String> paramDescriptionFor(String paramName, Sequence<ParamDescription> descriptions) {
        return descriptions
            .find(description -> description.name().equals(paramName))
            .map(description -> description.description());
    }

    private static String annotationClassOf(NamedParameter param) {return param.parametersClass().getSimpleName();}

    public static String typeFor(Type type) {
        return option(typeMap.get(typeWithOption(type)))
            .getOrElse("unknown: " + typeWithOption(type));
    }

    private static String typeWithOption(Type type) {
        String typeName = type.getTypeName();
        String optionName = Option.class.getCanonicalName();
        return typeName.startsWith(optionName)
            ? removeOption(typeName, optionName)
            : typeName;
    }

    private static String removeOption(String typeName, String optionName) {return typeName.substring(optionName.length() + 1, typeName.length() - 1);}

    private static boolean notOptional(Type type) {return !type.getTypeName().startsWith(Option.class.getCanonicalName());}

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

    public static Map<String, Object> realiseMap(Pair<String, Object>... pairs) {
        return map(
            sequence(pairs)
                .filter(not(second(instanceOf(None.class))))
                .map(when(
                    pair -> pair.second() instanceof Option,
                    pair -> pair(pair.first(), ((Option) pair.second()).get()))));
    }

    public static final ResponseDescription DEFAULT_RESPONSE_DESCRIPTION = new ResponseDescription() {
        @Override
        public Class<? extends Annotation> annotationType() { return ResponseDescription.class; }

        @Override
        public String status() { return "default"; }

        @Override
        public String description() { return "successful operation"; }
    };
}
