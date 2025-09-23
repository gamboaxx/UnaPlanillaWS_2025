/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanillaws.controller;

import cr.ac.una.unaplanillaws.util.JwTokenHelper;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author gambo
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter {
    private static final String AUTHORIZATION_SERVICE_PATH = "validarUsuario";
    private static final String RENEWAL__SERVICE_PATH = "renovarToken";
    private final JwTokenHelper jwTokenHelper = JwTokenHelper.getInstance();
    private static final String AUTHENTICATION_SCHEME = "Bearer";

}