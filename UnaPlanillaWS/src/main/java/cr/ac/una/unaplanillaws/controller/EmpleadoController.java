/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.EmpleadoDto;
import cr.ac.una.unaplanillaws.service.EmpleadoService;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.JwTokenHelper;
import cr.ac.una.unaplanillaws.util.Respuesta;
import cr.ac.una.unaplanillaws.util.Secure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gambo
 */
@Secure
@Path("/EmpleadoController")
@Tag(name="Empleados", description = "Operaciones sobre empleados")
@SecurityRequirement(name = "jwt-auth")
public class EmpleadoController {
    
    @EJB
    EmpleadoService empleadoService;
    
    @GET
    @Path("/usuario/{usuario}/{clave}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Autentica un usario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario Autenticado", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = EmpleadoDto.class))),
        @ApiResponse(responseCode = "404", description = "Usuario No Autenticado", content = @Content(mediaType = MediaType.TEXT_PLAIN)),
        @ApiResponse(responseCode = "500", description = "Error interno durante autenticación del usuario", content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    
    public Response validarUsuario (
            @Parameter(description = "Codigo del usuario")
            @PathParam("usuario") String usuario, 
            @Parameter(description = "Clave del usuario")
            @PathParam("clave") String clave){
        try{
            Respuesta respuesta = empleadoService.validarUsuario(usuario, clave);
            if(!respuesta.getEstado()){
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            } 
            EmpleadoDto empleadoDto = (EmpleadoDto)respuesta.getResultado("Empleado");
            empleadoDto.setToken(JwTokenHelper.getInstance().generatePrivatekey(usuario));
            return Response.ok(empleadoDto).build();
            
        }catch (Exception ex){
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el usuario.").build();
        }
    }
    
    @Secure
    @GET
    @Path("/empleado/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validarUsuario (@PathParam("id") Long id){
        try{
            Respuesta respuesta = empleadoService.getEmpleado(id);
            if(!respuesta.getEstado()){
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            EmpleadoDto empleadoDto = (EmpleadoDto)respuesta.getResultado("Empleado");
            return Response.ok(empleadoDto).build();
            
        }catch (Exception ex){
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el empleado.").build();
        }
    }
    
    
    @POST
    @Path("/empleado")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardarEmpleado (EmpleadoDto empleadoDto){
        try{
            Respuesta respuesta = empleadoService.guardarEmpleado(empleadoDto);
            if(!respuesta.getEstado()){
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            
            return Response.ok((EmpleadoDto)respuesta.getResultado("Empleado")).build();
            
        }catch (Exception ex){
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el empleado.").build();
        }
    }
    
    
    @DELETE
    @Path("/empleado/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminarEmpleado(@PathParam("id") Long id){
        try{
            Respuesta respuesta = empleadoService.eliminarEmpleado(id);
            if(!respuesta.getEstado()){
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            
            return Response.ok().build();
            
        }catch (Exception ex){
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error eliminando el empleado.").build();
        }
    }
    
    
//    (String cedula, String nombre, String pApellido, String sApellido
    @GET
    @Path("/empleados/{cedula}/{nombre}/{pApellido}/{sApellido}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEmpleados (@PathParam("cedula") String cedula, @PathParam("nombre") String nombre,@PathParam("pApellido") String pApellido,
    @PathParam("sApellido") String sApellido){
        try{
            Respuesta respuesta = empleadoService.getEmpleados(cedula, nombre, pApellido, sApellido);
            if(!respuesta.getEstado()){
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            
            return Response.ok(new GenericEntity<List<EmpleadoDto>>
        ((List<EmpleadoDto>)respuesta.getResultado("Empleados")){}).build();
            
        }catch (Exception ex){
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo los empleados.").build();
        }
    }
    
}