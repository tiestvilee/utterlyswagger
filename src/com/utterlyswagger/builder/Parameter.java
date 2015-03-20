package com.utterlyswagger.builder;

import com.googlecode.totallylazy.Option;

public class Parameter {
    public final String name;
    public final String paramType;
    public final Option<Boolean> required;
    public final Option<String> type;
    public final Option<String> description;

    public Parameter(String name, String paramType, Option<Boolean> required, Option<String> type, Option<String> description) {
        this.name = name;
        this.paramType = paramType;
        this.required = required;
        this.type = type;
        this.description = description;
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
