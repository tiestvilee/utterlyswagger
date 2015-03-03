package com.utterlyswagger;

import com.googlecode.totallylazy.Option;

import java.util.Map;

import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Option.option;

public class SwaggerInfo {

    public static final String BASE_PATH = "basePath";
    public static final String HOST = "host";
    public final String title;
    public final String apiVersion;
    public final Map<String, String> optionalData;

    public SwaggerInfo(String title, String apiVersion, Map<String, String> optionalData) {
        this.title = title;
        this.apiVersion = apiVersion;
        this.optionalData = optionalData;
    }

    public Map<String, Object> asMap() {
        return map("title", title, "version", apiVersion);
    }

    public Option<String> get(String key) {
        return option(optionalData.get(key));
    }
}
