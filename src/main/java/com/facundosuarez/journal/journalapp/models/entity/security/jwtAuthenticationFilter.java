package com.facundosuarez.journal.journalapp.models.entity.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.facundosuarez.journal.journalapp.models.entity.auth.userEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class jwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

   
    private jwt jwtUtiles;


    public jwtAuthenticationFilter(jwt jwtUtiles) {
        this.jwtUtiles = jwtUtiles;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        userEntity userEntity =null;
        String body ="";                
        String username = "";
        String password="";
           
            // Imprimir todos los encabezados de la solicitud
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                
            }

            // Imprimir los parámetros de la solicitud
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                body = paramName;
                String paramValue = request.getParameter(paramName);
              
            }      

            // Imprimir el cuerpo de la solicitud (ten en cuenta que esto solo funciona si la solicitud se puede leer múltiples veces)
            try {
              // String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                System.out.println("Cuerpo de la solicitud: " + request.getInputStream().getClass().getName());
                 userEntity = new ObjectMapper().readValue(request.getInputStream(), userEntity.class);
                    username = userEntity.getCorreo();
                    password = userEntity.getPassword();
            } catch (IOException e) {
                System.out.println("Error de carga de datos:" + e);
                e.printStackTrace();
            }
       
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            return getAuthenticationManager().authenticate(authenticationToken);
        
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        
                CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();
         String token = jwtUtiles.generateTokenDetail(user.getUsername());
         
         response.addHeader("token", token);

         Map<String, Object> httpResponse = new HashMap<>();
         httpResponse.put("token", token);
         httpResponse.put("message", "Autenticacion Correcta");
         httpResponse.put("correo", user.getUsername());
         httpResponse.put("userName", user.getName());     
         httpResponse.put("id", user.getUid()); // Reemplaza "valor del uid" con el valor real

              // Convertir el objeto JSON en una cadena
              String jsonResponse = new ObjectMapper().writeValueAsString(httpResponse);
         response.getWriter().write(jsonResponse);
         response.setStatus(200);
         response.setContentType(MediaType.APPLICATION_JSON_VALUE);
         response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
    
    
}
