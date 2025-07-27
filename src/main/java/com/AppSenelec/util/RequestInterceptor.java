package com.AppSenelec.util;

import com.AppSenelec.model.Log;
import com.AppSenelec.service.ILogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.IOException;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    
    @Autowired
    private ILogService logService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Capturer les informations de la requête
        String userId = request.getHeader("userId"); // Peut être null
        String ipAddress = getClientIpAddress(request);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        
        // Stocker les informations dans les attributs de la requête pour les utiliser après
        request.setAttribute("userId", userId);
        request.setAttribute("ipAddress", ipAddress);
        request.setAttribute("method", method);
        request.setAttribute("uri", uri);
        request.setAttribute("startTime", System.currentTimeMillis());
        
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            // Récupérer les informations stockées
            String userId = (String) request.getAttribute("userId");
            String ipAddress = (String) request.getAttribute("ipAddress");
            String method = (String) request.getAttribute("method");
            String uri = (String) request.getAttribute("uri");
            Long startTime = (Long) request.getAttribute("startTime");
            
            // Calculer le temps de réponse
            long responseTime = System.currentTimeMillis() - startTime;
            
            // Déterminer le statut et le message
            int statusCode = response.getStatus();
            String action = method + " " + uri;
            
            // Déterminer le message selon l'endpoint et le statut
            String message = determineMessage(method, uri, statusCode);
            
            String details = buildLogDetails(method, uri, statusCode, responseTime, ipAddress, userId, message);
            
            // Créer le log
            Log log = Log.builder()
                    .action(action)
                    .date(LocalDateTime.now())
                    .userId(userId != null ? Long.parseLong(userId) : null)
                    .details(details)
                    .build();
            
            logService.save(log);
            
        } catch (Exception e) {
            // Ne pas faire échouer la requête si le logging échoue
            System.err.println("Erreur lors du logging: " + e.getMessage());
        }
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0];
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
    
    private String determineMessage(String method, String uri, int statusCode) {
        // Messages selon l'endpoint et le statut
        if (statusCode >= 200 && statusCode < 300) {
            // Succès
            if (uri.contains("/compteurs") && method.equals("POST")) {
                return "Compteur créé avec succès";
            } else if (uri.contains("/achats") && method.equals("POST")) {
                return "Achat créé avec succès";
            } else if (uri.contains("/tarifs") && method.equals("POST")) {
                return "Tarif créé avec succès";
            } else if (uri.contains("/compteurs") && method.equals("GET")) {
                return "Liste des compteurs récupérée avec succès";
            } else if (uri.contains("/achats") && method.equals("GET")) {
                return "Liste des achats récupérée avec succès";
            } else if (uri.contains("/tarifs") && method.equals("GET")) {
                return "Liste des tarifs récupérée avec succès";
            } else if (uri.contains("/logs") && method.equals("GET")) {
                return "Liste des logs récupérée avec succès";
            } else if (uri.contains("/compteurs/recharge") && method.equals("POST")) {
                return "Recharge effectuée avec succès";
            } else if (method.equals("PUT")) {
                return "Élément mis à jour avec succès";
            } else if (method.equals("DELETE")) {
                return "Élément supprimé avec succès";
            } else {
                return "Opération réussie";
            }
        } else if (statusCode >= 400 && statusCode < 500) {
            // Erreur client
            return "Erreur de requête client";
        } else if (statusCode >= 500) {
            // Erreur serveur
            return "Erreur interne du serveur";
        } else {
            return "Opération effectuée";
        }
    }
    
    private String buildLogDetails(String method, String uri, int statusCode, long responseTime, String ipAddress, String userId, String message) {
        StringBuilder details = new StringBuilder();
        details.append("Message: ").append(message).append(", ");
        details.append("IP: ").append(ipAddress).append(", ");
        details.append("Status: ").append(statusCode).append(", ");
        details.append("Response Time: ").append(responseTime).append("ms");
        
        if (userId != null) {
            details.append(", User ID: ").append(userId);
        }
        
        return details.toString();
    }
} 