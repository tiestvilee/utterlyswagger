package com.utterlyswagger;

import com.googlecode.totallylazy.Option;

import static com.googlecode.totallylazy.Option.none;
import static com.googlecode.totallylazy.Option.some;

public class SwaggerInfo {

    public final String title;
    public final String apiVersion;
    public final Option<String> description;
    public final Option<String> termsOfService;
    public final Option<String> contactEmail;
    public final Option<String> licenceName;
    public final Option<String> licenceUrl;
    public final Option<String> basePath;
    public final Option<String> host;


    public SwaggerInfo(String title, String apiVersion) {
        this.title = title;
        this.apiVersion = apiVersion;
        description = none();
        termsOfService = none();
        contactEmail = none();
        licenceName = none();
        licenceUrl = none();
        host = none();
        basePath = none();
    }

    public SwaggerInfo(String title, String apiVersion, Option<String> host, Option<String> basePath, Option<String> description, Option<String> termsOfService, Option<String> contactEmail, Option<String> licenceName, Option<String> licenceUrl) {
        this.title = title;
        this.apiVersion = apiVersion;
        this.host = host;
        this.basePath = basePath;
        this.description = description;
        this.termsOfService = termsOfService;
        this.contactEmail = contactEmail;
        this.licenceName = licenceName;
        this.licenceUrl = licenceUrl;
    }

    public SwaggerInfo host(String newValue) {
        return new SwaggerInfo(title, apiVersion, some(newValue), basePath, description, termsOfService, contactEmail, licenceName, licenceUrl);
    }

    public SwaggerInfo basePath(String newValue) {
        return new SwaggerInfo(title, apiVersion, host, some(newValue), description, termsOfService, contactEmail, licenceName, licenceUrl);
    }

    public SwaggerInfo description(String newValue) {
        return new SwaggerInfo(title, apiVersion, host, basePath, some(newValue), termsOfService, contactEmail, licenceName, licenceUrl);
    }

    public SwaggerInfo termsOfService(String newValue) {
        return new SwaggerInfo(title, apiVersion, host, basePath, description, some(newValue), contactEmail, licenceName, licenceUrl);
    }

    public SwaggerInfo contactEmail(String newValue) {
        return new SwaggerInfo(title, apiVersion, host, basePath, description, termsOfService, some(newValue), licenceName, licenceUrl);
    }

    public SwaggerInfo licenceName(String newValue) {
        return new SwaggerInfo(title, apiVersion, host, basePath, description, termsOfService, contactEmail, some(newValue), licenceUrl);
    }

    public SwaggerInfo licenceUrl(String newValue) {
        return new SwaggerInfo(title, apiVersion, host, basePath, description, termsOfService, contactEmail, licenceName, some(newValue));
    }
}
