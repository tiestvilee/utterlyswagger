package com.utterlyswagger.petshop.application;

import com.googlecode.totallylazy.Option;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.annotations.DELETE;
import com.googlecode.utterlyidle.annotations.FormParam;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.POST;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.PathParam;
import com.googlecode.utterlyidle.annotations.Produces;
import com.utterlyswagger.annotations.Description;
import com.utterlyswagger.annotations.ParamDescription;
import com.utterlyswagger.annotations.RequestBody;
import com.utterlyswagger.annotations.ResponseDescription;
import com.utterlyswagger.annotations.Summary;

public class PetShopResource {

    @GET
    @Path("/pet")
    public Response pet() { return null; }

    @POST
    @Path("/pet")
    @RequestBody("Pet object that needs to be added to the store")
    public Response postPet() { return null; }

    @GET
    @Path("/pet/findByStatus")
    public Response findByStatus() { return null; }

    @GET
    @Path("/pet/findByTags")
    public Response findByTags() { return null; }

    @GET
    @Path("/pet/{petId:(.*)?}")
    @ParamDescription(name = "petId", description = "ID of pet that needs to be fetched")
    public Response petId(@PathParam("petId") Integer petId) { return null; }

    @POST
    @Path("/pet/{petId}")
    @ParamDescription(name = "petId", description = "ID of pet that needs to be updated")
    @ParamDescription(name = "status", description = "Updated status of the pet")
    @ParamDescription(name = "name", description = "Updated name of the pet")
    public Response updatePetId(@PathParam("petId") String petId, @FormParam("name") Option<String> name, @FormParam("status") Option<String> status) { return null; }

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
