package cr.ac.una.unaplanillaws.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("jakartaee10")
public class JakartaEE10Resource {
    
    @GET
    @Path("/{nombre}/{edad}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response ping(@PathParam("nombre")String xxx, @PathParam("edad") Integer edad, @QueryParam("apellido") String apellido){
        return Response.status(Response.Status.OK).entity
                ("Hola " .concat(xxx) .concat(" ").concat(apellido==null?"":apellido).concat(" ").concat(edad.toString()))
                .build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response Adios(String nombre){
        return Response.ok("Adios " .concat(nombre)).build();
                
    }
}
