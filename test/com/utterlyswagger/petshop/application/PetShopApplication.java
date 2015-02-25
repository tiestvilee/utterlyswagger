package com.utterlyswagger.petshop.application;

import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.RestApplication;
import com.utterlyswagger.SwaggerInfo;
import com.utterlyswagger.SwaggerModule;

public class PetShopApplication extends RestApplication {
    public PetShopApplication(BasePath basePath) {
        super(basePath);

        add(new SwaggerModule(new SwaggerInfo("Swagger Petstore", "1.0.0"), "/petshop/swagger"));
        add(new PetShopModule());
    }
}
