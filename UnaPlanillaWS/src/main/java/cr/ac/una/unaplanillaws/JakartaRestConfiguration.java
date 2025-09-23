package cr.ac.una.unaplanillaws;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures Jakarta RESTful Web Services for the application.
 * @author Juneau
 */
@ApplicationPath("ws")
public class JakartaRestConfiguration extends ResourceConfig {
    
    public JakartaRestConfiguration(){
        super();
        packages("cr.ac.una.unaplanillaws.controller", 
                "io.swagger.v3.jaxrs2.integration.resources");
        
    }
    
}
