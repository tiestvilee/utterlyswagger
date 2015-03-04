package com.utterlyswagger.petshop.application;

import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.RestApplication;
import com.utterlyswagger.SwaggerInfo;
import com.utterlyswagger.SwaggerModule;
import com.utterlyswagger.TargetEndpointBaseLocation;

import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Option.some;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.utterlyswagger.SwaggerInfo.*;

public class PetShopApplication extends RestApplication {

    public PetShopApplication(BasePath basePath) {
        super(basePath);

        add(new SwaggerModule(
            new SwaggerInfo(
                "Swagger Petstore",
                "1.0.0",
                map(sequence(
                    pair(DESCRIPTION, "This is a sample server Petstore server.  You can find out more about Swagger at <a href=\"http://swagger.io\">http://swagger.io</a> or on irc.freenode.net, #swagger.  For this sample, you can use the api key \"special-key\" to test the authorization filters"),
                    pair(TERMS_OF_SERVICE, "http://helloreverb.com/terms/"),
                    pair(CONTACT_EMAIL, "apiteam@wordnik.com"),
                    pair(LICENSE_NAME, "Apache 2.0"),
                    pair(LICENSE_URL, "http://www.apache.org/licenses/LICENSE-2.0.html")
                ))),
            new TargetEndpointBaseLocation(some("/v2"), some("petstore.swagger.io")),
            "/petshop/swagger"));
        add(new PetShopModule());
    }
}
