package com.utterlyswagger.petshop.application;

import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.RestApplication;
import com.utterlyswagger.SwaggerInfo;
import com.utterlyswagger.SwaggerModule;

import static com.googlecode.totallylazy.Maps.map;
import static com.utterlyswagger.SwaggerInfo.BASE_PATH;
import static com.utterlyswagger.SwaggerInfo.HOST;

public class PetShopApplication extends RestApplication {

    public PetShopApplication(BasePath basePath) {
        super(basePath);

        add(new SwaggerModule(
            new SwaggerInfo(
                "Swagger Petstore",
                "1.0.0",
                map(
                    BASE_PATH, "/v2",
                    HOST, "petstore.swagger.io"
                )),
            "/petshop/swagger"));
        add(new PetShopModule());
    }
}
