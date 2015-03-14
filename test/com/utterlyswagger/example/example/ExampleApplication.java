package com.utterlyswagger.example.example;

import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.RestApplication;
import com.utterlyswagger.SwaggerInfo;
import com.utterlyswagger.SwaggerModule;

public class ExampleApplication extends RestApplication {

    public ExampleApplication(BasePath basePath) {
        super(basePath);

        add(new SwaggerModule(new SwaggerInfo("Swagger Application", "0.1")));
        add(new ExampleModule());
    }
}
