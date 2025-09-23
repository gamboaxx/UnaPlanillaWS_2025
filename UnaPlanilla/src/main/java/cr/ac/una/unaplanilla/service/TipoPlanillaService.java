package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.util.Request;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente REST para TipoPlanilla, siguiendo el mismo patrón de EmpleadoService.
 */
public class TipoPlanillaService {

    private static final Logger LOG = Logger.getLogger(TipoPlanillaService.class.getName());

    public Respuesta getTipoPlanilla(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);

            Request request = new Request("TipoPlanillaController/tipoplanilla", "/{id}", parametros);
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            TipoPlanillaDto tipoPlanilla =
                    (TipoPlanillaDto) request.readEntity(TipoPlanillaDto.class);

            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanilla);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar el tipo de planilla.", ex);
            return new Respuesta(false, "Ocurrió un error al consultar el tipo de planilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta getTiposPlanilla(String codigo, String descripcion, String plaXMes, String idEmp, String cedula) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            // El WS espera "%%" como comodín cuando no se filtra
            parametros.put("codigo", wild(codigo));
            parametros.put("descripcion", wild(descripcion));
            parametros.put("plaXMes", wild(plaXMes));
            parametros.put("idEmp", wild(idEmp));
            parametros.put("cedula", wild(cedula));

            Request request = new Request(
                    "TipoPlanillaController/tipoplanillas",
                    "/{codigo}/{descripcion}/{plaXMes}/{idEmp}/{cedula}",
                    parametros
            );
            request.get();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            List<TipoPlanillaDto> lista =
                    (List<TipoPlanillaDto>) request.readEntity(new GenericType<List<TipoPlanillaDto>>() {});
            return new Respuesta(true, "", "", "TiposPlanilla", lista);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al consultar los tipos de planilla.", ex);
            return new Respuesta(false, "Ocurrió un error al consultar los tipos de planilla.", "getTiposPlanilla " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        try {
            Request request = new Request("TipoPlanillaController/tipoplanilla");
            request.post(tipoPlanillaDto);

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            TipoPlanillaDto resultado =
                    (TipoPlanillaDto) request.readEntity(TipoPlanillaDto.class);

            return new Respuesta(true, "", "", "TipoPlanilla", resultado);

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al guardar el tipo de planilla.", ex);
            return new Respuesta(false, "Ocurrió un error al guardar el tipo de planilla.", "guardarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);

            Request request = new Request("TipoPlanillaController/tipoplanilla", "/{id}", parametros);
            request.delete();

            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }

            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrió un error al eliminar el tipo de planilla.", ex);
            return new Respuesta(false, "Ocurrió un error al eliminar el tipo de planilla.", "eliminarTipoPlanilla " + ex.getMessage());
        }
    }

    // Utilidad: convierte null/blank a "%%" para que el WS no filtre ese campo
    private String wild(String s) {
        return (s == null || s.isBlank()) ? "%%" : s;
    }
}
