package com.utterlyswagger.builder;

import com.googlecode.totallylazy.Sequence;
import com.utterlyswagger.annotations.ResponseDescription;

import java.util.List;

public class Operation {
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
