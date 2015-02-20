package com.utterlyswagger.petshop;

import com.googlecode.totallylazy.Strings;
import com.googlecode.totallylazy.json.Json;
import com.googlecode.utterlyidle.RequestBuilder;
import com.googlecode.utterlyidle.handlers.ClientHttpHandler;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestExampleJson extends PetShopTest {

    private final static String petStoreSwaggerJson = Strings.string(TestExampleJson.class.getResourceAsStream("swagger.json"));

    @Test
    @Ignore
    public void swaggerJsonSameAsForRealPetShop() throws Exception {
        assertThat(
            new ClientHttpHandler(5000).handle(RequestBuilder.get("http://petstore.swagger.io/v2/swagger.json").build()).entity().toString(),
            is(petStoreSwaggerJson));
    }

    @Override
    protected Map<String, Object> getSwagger() {
        return Json.map(petStoreSwaggerJson);
    }
}
