/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.model.TipoPlanillaDto;
import cr.ac.una.unaplanillaws.service.TipoPlanillaService;
import cr.ac.una.unaplanillaws.util.CodigoRespuesta;
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
 * Controlador REST para operaciones de Tipo de Planilla.
 * Patrón idéntico a EmpleadoController.
 *
 * Endpoints:
 *  GET    /TipoPlanillaController/tipoplanilla/{id}
 *  POST   /TipoPlanillaController/tipoplanilla
 *  DELETE /TipoPlanillaController/tipoplanilla/{id}
 *  GET    /TipoPlanillaController/tipoplanillas/{codigo}/{descripcion}/{plaXMes}/{idEmp}/{cedula}
 */
 //@Secure
@Path("/TipoPlanillaController")
@Tag(name = "Tipos de Planilla", description = "Operaciones sobre tipos de planilla")
@SecurityRequirement(name = "jwt-auth")
public class TipoPlanillaController {

    @EJB
    TipoPlanillaService tipoPlanillaService;

    // === Obtener por ID ===
    //@Secure
    @GET
    @Path("/tipoplanilla/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Obtiene un tipo de planilla por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de planilla encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = TipoPlanillaDto.class))),
        @ApiResponse(responseCode = "404", description = "No encontrado",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    public Response getTipoPlanilla(
            @Parameter(description = "Id del tipo de planilla")
            @PathParam("id") Long id) {
        try {
            Respuesta respuesta = tipoPlanillaService.getTipoPlanilla(id);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue())
                               .entity(respuesta.getMensaje()).build();
            }
            TipoPlanillaDto dto = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
            return Response.ok(dto).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue())
                           .entity("Error obteniendo el tipo de planilla.").build();
        }
    }

    // === Crear/Actualizar ===
    @POST
    @Path("/tipoplanilla")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Crea o actualiza un tipo de planilla")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Guardado correctamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = TipoPlanillaDto.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    public Response guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            Respuesta respuesta = tipoPlanillaService.guardarTipoPlanilla(tipoPlanillaDto);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue())
                               .entity(respuesta.getMensaje()).build();
            }
            return Response.ok((TipoPlanillaDto) respuesta.getResultado("TipoPlanilla")).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue())
                           .entity("Error guardando el tipo de planilla.").build();
        }
    }

    // === Eliminar ===
    @DELETE
    @Path("/tipoplanilla/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Elimina un tipo de planilla por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "No encontrado",
            content = @Content(mediaType = MediaType.TEXT_PLAIN)),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    public Response eliminarTipoPlanilla(
            @Parameter(description = "Id del tipo de planilla")
            @PathParam("id") Long id) {
        try {
            Respuesta respuesta = tipoPlanillaService.eliminarTipoPlanilla(id);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue())
                               .entity(respuesta.getMensaje()).build();
            }
            return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue())
                           .entity("Error eliminando el tipo de planilla.").build();
        }
    }

    // === Búsqueda con 5 filtros ===
    @GET
    @Path("/tipoplanillas/{codigo}/{descripcion}/{plaXMes}/{idEmp}/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Lista tipos de planilla filtrando por código, descripción, planillas por mes, id de empleado y cédula. Use '%%' para no filtrar un campo.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado obtenido",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = TipoPlanillaDto.class))),
        @ApiResponse(responseCode = "500", description = "Error interno",
            content = @Content(mediaType = MediaType.TEXT_PLAIN))
    })
    public Response getTiposPlanilla(
            @Parameter(description = "Código")       @PathParam("codigo")     String codigo,
            @Parameter(description = "Descripción")  @PathParam("descripcion") String descripcion,
            @Parameter(description = "Planillas/Mes")@PathParam("plaXMes")     String plaXMes,
            @Parameter(description = "ID Empleado")  @PathParam("idEmp")       String idEmp,
            @Parameter(description = "Cédula")       @PathParam("cedula")      String cedula) {
        try {
            Respuesta respuesta = tipoPlanillaService.getTiposPlanilla(codigo, descripcion, plaXMes, idEmp, cedula);
            if (!respuesta.getEstado()) {
                return Response.status(respuesta.getCodigoRespuesta().getValue())
                               .entity(respuesta.getMensaje()).build();
            }
            @SuppressWarnings("unchecked")
            List<TipoPlanillaDto> lista = (List<TipoPlanillaDto>) respuesta.getResultado("TiposPlanilla");
            return Response.ok(new GenericEntity<List<TipoPlanillaDto>>(lista){}).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue())
                           .entity("Error obteniendo los tipos de planilla.").build();
        }
    }
}
