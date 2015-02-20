package com.utterlyswagger.petshop;

import com.googlecode.totallylazy.Strings;
import com.googlecode.utterlyidle.*;
import com.googlecode.utterlyidle.handlers.ClientHttpHandler;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PetShopTest {

    private final String petStoreSwaggerJson = Strings.string(PetShopTest.class.getResourceAsStream("swagger.json"));

    @Test
    public void producesSwaggerJson() throws Exception {
        Application application = new PetShopApplication(new BasePath("/"));
        
        assertThat(
            application.handle(RequestBuilder.get("/v2/swagger.json").build()).entity().toString(),
            is(petStoreSwaggerJson));
    }

    @Test
    public void swaggerJsonSameAsForRealPetShop() throws Exception {
        assertThat(
            new ClientHttpHandler(5000).handle(RequestBuilder.get("http://petstore.swagger.io/v2/swagger.json").build()).entity().toString(),
            is(petStoreSwaggerJson));
    }
}
