package com.utterlyswagger;

import java.util.Map;

import static com.googlecode.totallylazy.Maps.map;

public class SwaggerInfo {

    public final String title;
    public final String apiVersion;

    public SwaggerInfo(String title, String apiVersion) {
        this.title = title;
        this.apiVersion = apiVersion;
    }

    public Map<String, Object> asMap() {
        return map("title", title, "version", apiVersion);
    }
}
