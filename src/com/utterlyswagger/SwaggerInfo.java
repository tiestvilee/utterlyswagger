package com.utterlyswagger;

import com.googlecode.totallylazy.Option;

import java.util.Map;

import static com.googlecode.totallylazy.Option.option;

public class SwaggerInfo {

    public static final String DESCRIPTION = "description";
    public static final String TERMS_OF_SERVICE = "termsOfService";
    public static final String CONTACT_EMAIL = "contactEmail";
    public static final String LICENSE_NAME = "licenceName";
    public static final String LICENSE_URL = "licenceUrl";

    public final String title;
    public final String apiVersion;
    public final Map<String, String> optionalData;

    public SwaggerInfo(String title, String apiVersion, Map<String, String> optionalData) {
        this.title = title;
        this.apiVersion = apiVersion;
        this.optionalData = optionalData;
    }

    public Option<String> get(String key) {
        return option(optionalData.get(key));
    }
}
