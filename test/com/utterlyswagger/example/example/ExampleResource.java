package com.utterlyswagger.example.example;

import com.googlecode.utterlyidle.Request;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.QueryParam;
import com.utterlyswagger.annotations.ParamDescription;
import com.utterlyswagger.annotations.RequestBody;

public class ExampleResource {

    @GET
    @Path("/no-params")
    public Response noParams() { return null; }

    @GET
    @Path("/unnamed-param")
    public Response unnamedParam(Request request) { return null; }

    @GET
    @Path("/empty-body")
    @RequestBody()
    public Response emptyBody(Request request) { return null; }

    @GET
    @Path("/param-no-description")
    public Response paramNoDescription(@QueryParam("strange") String strange) { return null; }

    @GET
    @Path("/param-one-description")
    @ParamDescription(name = "strange", description = "a-description")
    public Response paramOneDescription(@QueryParam("strange") String strange) { return null; }

    @GET
    @Path("/param-two-description")
    @ParamDescription(name = "strange", description = "a-description")
    @ParamDescription(name = "strange2", description = "another-description")
    public Response paramTwoDescription(@QueryParam("strange") String strange, @QueryParam("strange2") String strange2) { return null; }
}
