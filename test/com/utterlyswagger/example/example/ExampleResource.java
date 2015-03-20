package com.utterlyswagger.example.example;

import com.googlecode.utterlyidle.Request;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
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
}
