package com.utterlyswagger.petshop.application;

import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;

public class PetShopResource {

    @GET
    @Path("/pet")
    public Response pet() { return null; }

    @GET
    @Path("/pet/findByStatus")
    public Response findByStatus() { return null; }

    @GET
    @Path("/pet/findByTags")
    public Response findByTags() { return null; }

    @GET
    @Path("/pet/{petId}")
    public Response petId() { return null; }

    @GET
    @Path("/pet/{petId}/uploadImage")
    public Response uploadImage() { return null; }

    @GET
    @Path("/store/inventory")
    public Response inventory() { return null; }

    @GET
    @Path("/store/order")
    public Response order() { return null; }

    @GET
    @Path("/store/order/{orderId}")
    public Response orderId() { return null; }

    @GET
    @Path("/user")
    public Response user() { return null; }

    @GET
    @Path("/user/createWithArray")
    public Response createWithArray() { return null; }

    @GET
    @Path("/user/createWithList")
    public Response createWithList() { return null; }

    @GET
    @Path("/user/login")
    public Response login() { return null; }

    @GET
    @Path("/user/logout")
    public Response logout() { return null; }

    @GET
    @Path("/user/{username}")
    public Response username() { return null; }

}
