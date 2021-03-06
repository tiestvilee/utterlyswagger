package com.utterlyswagger.builder;

import com.googlecode.totallylazy.Sequence;
import com.utterlyswagger.SwaggerInfo;
import com.utterlyswagger.annotations.ResponseDescription;

import java.util.Map;

import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Maps.pairs;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.utterlyswagger.builder.Operations.realiseMap;

public class SwaggerV2 {

    public static final String UNKNOWN = "unknown";

    public static Map<String, Object> swaggerV2(SwaggerInfo info, Map<String, Sequence<Operation>> operations) {
        return realiseMap(
            pair("swagger", "2.0"),
            pair("info", swaggerInfo(info)),
            pair("paths", paths(operations)),
            pair("basePath", info.basePath),
            pair("host", info.host)
        );
    }

    public static Map<String, Object> swaggerInfo(SwaggerInfo info) {
        return realiseMap(
            pair("title", (Object) info.title),
            pair("version", info.apiVersion),
            pair("description", info.description),
            pair("termsOfService", info.termsOfService),
            pair("contact", realiseMap(
                pair("email", info.contactEmail))),
            pair("license", realiseMap(
                pair("name", info.licenceName),
                pair("url", info.licenceUrl))));
    }


    public static Map<String, Map<String, Object>> paths(Map<String, Sequence<Operation>> operations) {
        return map(pairs(operations)
            .map(pair -> pair(pair.first(), pathItem(pair.second()))));
    }

    public static Map<String, Object> pathItem(Sequence<Operation> operations) {
        return map(operations.map(operation ->
                pair(operation.method.toLowerCase(), operationObject(operation)))
        );
    }

    private static Map<String, Object> operationObject(Operation operation) {
        return map(
            "summary", operation.summary,
            "description", operation.description,
            "produces", operation.produces,
            "parameters", parameters(operation.parameters),
            "responses", responses(operation.responses));
    }

    private static Sequence<Map<String, Object>> parameters(Sequence<Parameter> parameters) {
        return parameters.map(SwaggerV2::parameter);
    }

    private static Map<String, String> paramLocation = map(sequence(
        pair("body", "body"),
        pair("PathParameters", "path"),
        pair("CookieParameters", "cookie"),
        pair("QueryParameters", "query"),
        pair("FormParameters", "formData"),
        pair("HeaderParameters", "header")
    ));

    private static Map<String, Object> parameter(Parameter param) {
        return realiseMap(
            pair("name", param.name),
            pair("in", paramLocation.getOrDefault(param.paramType, UNKNOWN)),
            pair("required", param.required),
            pair("type", param.type),
            pair("description", param.description)
        );
    }

    private static Map<String, Object> responses(Sequence<ResponseDescription> responseDescriptions) {
        return map(responseDescriptions.map(desc ->
                pair(desc.status(), (Object) map("description", desc.description()))
        ));
    }

}
