package com.utterlyswagger.petshop;

import com.googlecode.totallylazy.Sequence;
import com.googlecode.totallylazy.json.Json;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

import static com.googlecode.totallylazy.Sequences.sequence;
import static com.utterlyswagger.petshop.path.BasicPath.mapAt;
import static com.utterlyswagger.petshop.path.BasicPath.sequenceAt;
import static com.utterlyswagger.petshop.path.PathAssertions.*;
import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.AllOf.allOf;

public abstract class TestPetShopSwaggerV1_2 {

    public static final String PETSHOP_DESCRIPTION = "This is a sample server Petstore server.  You can find out more about Swagger at <a href=\"http://swagger.io\">http://swagger.io</a> or on irc.freenode.net, #swagger.  For this sample, you can use the api key \"special-key\" to test the authorization filters";

    @Test
    public void definesSwaggerVersion() throws Exception {
        assertThat(
            getSwagger(), stringInPath(is("1.2"), "swaggerVersion"));
    }

    @Test
    public void definesExtraSwaggerStuff() throws Exception {
        assertThat(
            getSwagger(),
            allOf(
                stringInPath(is("http://petstore.swagger.io/v2"), "basePath"),
                stringInPath(is("1.0.0"), "apiVersion")
            ));
    }

    @Test
    public void definesMinimumSwaggerInfo() throws Exception {
        assertThat(
            getSwagger(),
            allOf(
                stringInPath(is("Swagger Petstore"),
                    "info", "title"),
                stringInPath(is(PETSHOP_DESCRIPTION),
                    "info", "description")));
    }

    @Test
    public void definesExtraSwaggerInfo() throws Exception {
        assertThat(
            getSwagger(),
            allOf(
                stringInPath(is("http://helloreverb.com/terms/"),
                    "info", "termsOfServiceUrl"),
                stringInPath(is("apiteam@wordnik.com"),
                    "info", "contact"),
                stringInPath(is("Apache 2.0"),
                    "info", "license"),
                stringInPath(is("http://www.apache.org/licenses/LICENSE-2.0.html"),
                    "info", "licenseUrl")));
    }

    @Test
    public void definesPaths() throws Exception {

        assertThat(
            sequenceAt(getSwagger(), "apis")
                .map(endpoint -> ((Map<String, Object>) endpoint).get("path")),
            hasItems("/pet", "/pet/findByStatus", "/pet/findByTags",
                "/pet/{petId}", "/pet/{petId}/uploadImage", "/store/inventory",
                "/store/order", "/store/order/{orderId}", "/user",
                "/user/createWithArray", "/user/createWithList", "/user/login",
                "/user/logout", "/user/{username}"));
    }

    @Test
    public void definesSimpleGetResource() throws Exception {
        assertThat(
            getEndpointOperation("/user/logout", "GET"),
            allOf(
                stringInPath(is("Logs out current logged in user session"), "summary"),
                stringInPath(is("logout"), "nickname"),
                listInPath(contains("application/json", "application/xml"), "produces")
            ));
    }

    @Test
    public void definesDeleteResourceWithDescriptionAndMoreResponses() throws Exception {
        System.out.println(Json.json(getSwagger()));
        assertThat(
            getEndpointOperation("/store/order/{orderId}", "DELETE"),
            allOf(
                stringInPath(is("For valid response try integer IDs with value < 1000. Anything above 1000 or nonintegers will generate API errors"),
                    "notes"),
                integerInPath(is(new BigDecimal(404)), "responseMessages", 0, "code"),
                stringInPath(is("Order not found"), "responseMessages", 0, "message"),
                integerInPath(is(new BigDecimal(400)), "responseMessages", 1, "code"),
                stringInPath(is("Invalid ID supplied"), "responseMessages", 1, "message")
            ));
    }

    @Test
    public void definesResourceWithMultipleActions() throws Exception {
        assertThat(
            getEndpoint("/store/order/{orderId}").map(operation -> operation.get("method")),
            containsInAnyOrder("GET", "DELETE"));
    }

    @Test
    public void definesParametersToEndpoint() throws Exception {
        assertThat(
            mapAt(getEndpointOperation("/pet/{petId}", "POST"), "parameters", 0),
            allOf(
                stringInPath(is("petId"), "name"),
                stringInPath(is("path"), "paramType"),
                objectInPath(is(true), "required"),
                stringInPath(is("string"), "type")));

        assertThat(
            mapAt(getEndpointOperation("/pet/{petId}", "POST"), "parameters", 1),
            allOf(
                stringInPath(is("name"), "name"),
                stringInPath(is("form"), "paramType"),
                objectInPath(is(false), "required"),
                stringInPath(is("string"), "type")));

        assertThat(
            mapAt(getEndpointOperation("/pet/{petId}", "POST"), "parameters", 2),
            allOf(
                stringInPath(is("form"), "paramType"),
                stringInPath(is("status"), "name"),
                objectInPath(is(false), "required"),
                stringInPath(is("string"), "type")
            ));
    }

    private Sequence<Map<String, Object>> getEndpoint(String endPointName) throws Exception {
        return sequenceAt(getSwagger(), "apis")
            .map(api -> (Map<String, Object>) api)
            .find(api -> api.get("path").equals(endPointName))
            .map(api -> sequence((Iterable<Map<String, Object>>) api.get("operations")))
            .getOrThrow(new RuntimeException(format("Couldn't find end point %s", endPointName)));
    }

    private Map<String, Object> getEndpointOperation(String endPointName, String method) throws Exception {
        return getEndpoint(endPointName)
            .find(operation -> operation.get("method").equals(method))
            .getOrThrow(new RuntimeException(format("Couldn't find end point %s with method %s", endPointName, method)));
    }

    protected abstract Map<String, Object> getSwagger() throws Exception;
}
