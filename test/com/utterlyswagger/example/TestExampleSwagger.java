package com.utterlyswagger.example;

import com.googlecode.funclate.json.Json;
import com.googlecode.utterlyidle.Application;
import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.Response;
import com.utterlyswagger.example.example.ExampleApplication;
import org.junit.Test;

import java.util.Map;

import static com.googlecode.utterlyidle.RequestBuilder.get;
import static com.utterlyswagger.path.BasicPath.mapAt;
import static com.utterlyswagger.path.BasicPath.sequenceAt;
import static com.utterlyswagger.path.PathAssertions.stringInPath;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.AllOf.allOf;

public class TestExampleSwagger {
    @Test
    public void copes_with_no_params() throws Exception {
        assertThat(
            sequenceAt(getJson(), "paths", "/no-params", "get", "parameters"),
            iterableWithSize(0));
    }

    @Test
    public void copes_with_unannotated_params() throws Exception {
        assertThat(
            sequenceAt(getJson(), "paths", "/unnamed-param", "get", "parameters"),
            iterableWithSize(0));
    }

    @Test
    public void copes_with_empty_RequestBody() throws Exception {
        assertThat(
            mapAt(getJson(), "paths", "/empty-body", "get", "parameters", 0),
            allOf(
                stringInPath(is("body"), "in"),
                stringInPath(is("body"), "name"),
                not(hasEntry(is("description"), (Object) anything()))
            ));
    }

    private Map<String, Object> getJson() throws Exception {
        Application app = new ExampleApplication(new BasePath(""));

        Response handle = app.handle(get("/swagger/swagger_v2.json").build());
        String jsonString = handle.entity().toString();
        System.out.println("jsonString = " + jsonString);
        return Json.map(jsonString);
    }
}
