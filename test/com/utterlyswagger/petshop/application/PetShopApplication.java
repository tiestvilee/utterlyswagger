package com.utterlyswagger.petshop.application;

import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.RestApplication;
import com.utterlyswagger.SwaggerModule;

public class PetShopApplication extends RestApplication {
    public PetShopApplication(BasePath basePath) {
        super(basePath);

        add(new SwaggerModule());
    }
}
