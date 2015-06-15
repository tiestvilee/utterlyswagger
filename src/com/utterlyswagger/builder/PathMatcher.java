package com.utterlyswagger.builder;

public class PathMatcher {

    static String operationPath(String uriTemplate) {
        return "/" + uriTemplate.replaceAll("\\{(?<paramName>[^:]+):[^}]+}", "{${paramName}}");
    }
}
