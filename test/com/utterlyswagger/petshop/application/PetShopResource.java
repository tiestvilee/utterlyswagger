package com.utterlyswagger.petshop.application;

import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.annotations.*;
import com.utterlyswagger.annotations.*;

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
    @Summary("Find purchase order by ID")
    @Description("For valid response try integer IDs with value <= 5 or > 10. Other values will generated exceptions")
    @ResponseDescription(status = "404", description = "Order not found")
    @ResponseDescription(status = "400", description = "Invalid ID supplied")
    @ResponseDescription(status = "200", description = "successful operation")
    public Response orderId() { return null; }

    @DELETE
    @Path("/store/order/{orderId}")
    @Description("For valid response try integer IDs with value < 1000. Anything above 1000 or nonintegers will generate API errors")
    @ResponseDescription(status = "404", description = "Order not found")
    @ResponseDescription(status = "400", description = "Invalid ID supplied")
    public Response deleteOrderId() { return null; }

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
    @Produces({"application/json", "application/xml"})
    @Summary("Logs out current logged in user session")
    public Response logout() { return null; }

    @GET
    @Path("/user/{username}")
    public Response username() { return null; }

}
