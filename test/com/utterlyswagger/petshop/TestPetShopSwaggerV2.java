package com.utterlyswagger.petshop;

import org.junit.Test;

import java.util.Map;

import static com.utterlyswagger.path.BasicPath.mapAt;
import static com.utterlyswagger.path.PathAssertions.*;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.AllOf.allOf;

public abstract class TestPetShopSwaggerV2 {

    @Test
    public void definesSwaggerVersion() throws Exception {
        assertThat(
            getSwagger(), stringInPath(is("2.0"), "swagger"));
    }

    @Test
    public void definesExtraSwaggerStuff() throws Exception {
        assertThat(
            getSwagger(),
            allOf(
                stringInPath(is("/v2"), "basePath"),
                stringInPath(is("petstore.swagger.io"), "host")
            ));
    }

    @Test
    public void definesMinimumSwaggerInfo() throws Exception {
        assertThat(
            getSwagger(),
            allOf(
                stringInPath(is("Swagger Petstore"), "info", "title"),
                stringInPath(is("1.0.0"), "info", "version")));
    }

    @Test
    public void definesExtraSwaggerInfo() throws Exception {
        assertThat(
            getSwagger(),
            allOf(
                stringInPath(is("This is a sample server Petstore server.  You can find out more about Swagger at <a href=\"http://swagger.io\">http://swagger.io</a> or on irc.freenode.net, #swagger.  For this sample, you can use the api key \"special-key\" to test the authorization filters"),
                    "info", "description"),
                stringInPath(is("http://helloreverb.com/terms/"),
                    "info", "termsOfService"),
                stringInPath(is("apiteam@wordnik.com"),
                    "info", "contact", "email"),
                stringInPath(is("Apache 2.0"),
                    "info", "license", "name"),
                stringInPath(is("http://www.apache.org/licenses/LICENSE-2.0.html"),
                    "info", "license", "url")));
    }

    @Test
    public void definesPaths() throws Exception {
        String[] endPoints = {"/pet", "/pet/findByStatus", "/pet/findByTags",
            "/pet/{petId}", "/pet/{petId}/uploadImage", "/store/inventory",
            "/store/order", "/store/order/{orderId}", "/user",
            "/user/createWithArray", "/user/createWithList", "/user/login",
            "/user/logout", "/user/{username}"};

        assertThat(
            getSwagger(),
            mapInPathKeys(hasItems(endPoints), "paths"));
    }

    @Test
    public void definesSimpleGetResource() throws Exception {
        assertThat(
            mapAt(getSwagger(), "paths", "/user/logout", "get"),
            allOf(
                stringInPath(is("Logs out current logged in user session"),
                    "summary"),
                listInPath(contains("application/json", "application/xml"),
                    "produces"),
                stringInPath(is("successful operation"),
                    "responses", "default", "description")
            ));
    }

    @Test
    public void definesDeleteResourceWithDescriptionAndMoreResponses() throws Exception {
        assertThat(
            mapAt(getSwagger(), "paths", "/store/order/{orderId}", "delete"),
            allOf(
                stringInPath(is("For valid response try integer IDs with value < 1000. Anything above 1000 or nonintegers will generate API errors"),
                    "description"),
                stringInPath(is("Order not found"),
                    "responses", "404", "description"),
                stringInPath(is("Invalid ID supplied"),
                    "responses", "400", "description")
            ));
    }

    @Test
    public void definesResourceWithMultipleActions() throws Exception {
        assertThat(
            getSwagger(),
            mapInPathKeys(containsInAnyOrder("get", "delete"), "paths", "/store/order/{orderId}"));
    }

    @Test
    public void definesParametersToEndpoint() throws Exception {
        assertThat(
            mapAt(getSwagger(), "paths", "/pet/{petId}", "post", "parameters", 0),
            allOf(
                stringInPath(is("petId"), "name"),
                stringInPath(is("path"), "in"),
                stringInPath(is("ID of pet that needs to be updated"), "description"),
                objectInPath(is(true), "required"),
                stringInPath(is("string"), "type")));


        assertThat(
            mapAt(getSwagger(), "paths", "/pet/{petId}", "post", "parameters", 1),
            allOf(
                stringInPath(is("name"), "name"),
                stringInPath(is("formData"), "in"),
                stringInPath(is("Updated name of the pet"), "description"),
                objectInPath(is(false), "required"),
                stringInPath(is("string"), "type")));

        assertThat(
            mapAt(getSwagger(), "paths", "/pet/{petId}", "post", "parameters", 2),
            allOf(
                stringInPath(is("status"), "name"),
                stringInPath(is("formData"), "in"),
                stringInPath(is("Updated status of the pet"), "description"),
                objectInPath(is(false), "required"),
                stringInPath(is("string"), "type")
            ));
    }

    @Test
    public void definesBodyTypeParameter() throws Exception {
        assertThat(
            mapAt(getSwagger(), "paths", "/pet", "post", "parameters", 0),
            allOf(
                stringInPath(is("body"), "in"),
                stringInPath(is("body"), "name"),
                stringInPath(is("Pet object that needs to be added to the store"), "description")
            ));
    }


    protected abstract Map<String, Object> getSwagger() throws Exception;
}
