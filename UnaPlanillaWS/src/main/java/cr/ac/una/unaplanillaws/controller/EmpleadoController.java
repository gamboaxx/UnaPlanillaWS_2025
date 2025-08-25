/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.EmpleadoDto;
import cr.ac.una.unaplanillaws.service.EmpleadoService;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
import cr.ac.una.unaplanillaws.util.Respuesta;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gambo
 */
@Path("/EmpleadoController")
public class EmpleadoController {
    
    @EJB
    EmpleadoService empleadoService;
    
    @GET
    @Path("/usuario/{usuario}/{clave}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    
    public Response validarUsuario (@PathParam("usuario") String usuario, @PathParam("clave") String clave){
        try{
            Respuesta respuesta = empleadoService.validarUsuario(usuario, clave);
            if(!respuesta.getEstado()){
                return Response.status(respuesta.getCodigoRespuesta().getValue()).entity(respuesta.getMensaje()).build();
            }
            EmpleadoDto empleadoDto = (EmpleadoDto)respuesta.getResultado("Empleado");
            return Response.ok(empleadoDto).build();
            
        }catch (Exception ex){
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el usuario.").build();
        }
    }
    
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
    
}