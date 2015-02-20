package com.utterlyswagger.petshop;

import com.googlecode.totallylazy.json.Json;
import com.googlecode.utterlyidle.Application;
import com.googlecode.utterlyidle.BasePath;
import com.utterlyswagger.petshop.application.PetShopApplication;

import java.util.Map;

import static com.googlecode.utterlyidle.RequestBuilder.get;

public class ModuleJsonTest extends TestPetShopSwagger {

    private final Application application = new PetShopApplication(new BasePath("/"));

    @Override
    protected Map<String, Object> getSwagger() throws Exception {
        return Json.map(application.handle(get("/v2/swagger.json").build()).entity().toString());
    }

}
