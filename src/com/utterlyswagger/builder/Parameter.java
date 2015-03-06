package com.utterlyswagger.builder;

public class Parameter {
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
