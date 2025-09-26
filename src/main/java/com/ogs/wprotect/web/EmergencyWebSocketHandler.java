/*
package com.ogs.oikoom.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EmergencyWebSocketHandler extends TextWebSocketHandler {

    private final Map<Integer, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.put(Integer.parseInt(userId), session);
            System.out.println("Usuario " + userId + " conectado via WebSocket");

            // Enviar confirmación de conexión
            sendMessage(session, new WebSocketMessage("CONNECTION_STATUS",
                    "Conectado exitosamente", new Date()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.remove(Integer.parseInt(userId));
            System.out.println("Usuario " + userId + " desconectado");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            JsonNode messageNode = objectMapper.readTree(message.getPayload());
            String type = messageNode.get("type").asText();

            switch (type) {
                case "REGISTER_USER":
                    handleUserRegistration(session, messageNode);
                    break;
                case "MARK_ALERT_SEEN":
                    handleMarkAlertSeen(messageNode);
                    break;
                case "ALERT_RECEIVED":
                    handleAlertReceived(messageNode);
                    break;
                default:
                    System.out.println("Tipo de mensaje no reconocido: " + type);
            }
        } catch (Exception e) {
            System.err.println("Error procesando mensaje WebSocket: " + e.getMessage());
            sendError(session, "Error procesando mensaje: " + e.getMessage());
        }
    }

    private void handleUserRegistration(WebSocketSession session, JsonNode message) {
        try {
            int userId = message.get("data").get("userId").asInt();
            userSessions.put(userId, session);
            System.out.println("Usuario " + userId + " registrado exitosamente");
        } catch (Exception e) {
            System.err.println("Error en registro de usuario: " + e.getMessage());
        }
    }

    private void handleMarkAlertSeen(JsonNode message) {
        // Aquí actualizarías la base de datos para marcar la alerta como vista
        int alertId = message.get("data").get("alertId").asInt();
        System.out.println("Alerta " + alertId + " marcada como vista");
        // TODO: Actualizar en base de datos
    }

    private void handleAlertReceived(JsonNode message) {
        int alertId = message.get("data").get("alertId").asInt();
        int userId = message.get("data").get("userId").asInt();
        System.out.println("Confirmación de recepción de alerta " + alertId + " por usuario " + userId);
        // TODO: Registrar confirmación en base de datos
    }

    // Método público para enviar alertas de emergencia
    public void sendEmergencyAlert(List<Integer> contactUserIds, EmergencyAlert alert) {
        for (Integer contactUserId : contactUserIds) {
            WebSocketSession session = userSessions.get(contactUserId);
            if (session != null && session.isOpen()) {
                try {
                    WebSocketMessage wsMessage = new WebSocketMessage(
                            "EMERGENCY_ALERT",
                            alert,
                            new Date()
                    );
                    sendMessage(session, wsMessage);
                    System.out.println("Alerta enviada a usuario " + contactUserId + " via WebSocket");
                } catch (Exception e) {
                    System.err.println("Error enviando alerta a usuario " + contactUserId + ": " + e.getMessage());
                }
            } else {
                System.out.println("Usuario " + contactUserId + " no está conectado");
                // TODO: Aquí podrías enviar push notification como fallback
            }
        }
    }

    private void sendMessage(WebSocketSession session, WebSocketMessage message) throws Exception {
        String json = objectMapper.writeValueAsString(message);
        session.sendMessage(new TextMessage(json));
    }

    private void sendError(WebSocketSession session, String errorMessage) {
        try {
            WebSocketMessage error = new WebSocketMessage("ERROR", errorMessage, new Date());
            sendMessage(session, error);
        } catch (Exception e) {
            System.err.println("Error enviando mensaje de error: " + e.getMessage());
        }
    }

    private String getUserIdFromSession(WebSocketSession session) {
        // Extraer userId de los parámetros de query
        String query = session.getUri().getQuery();
        if (query != null && query.contains("userId=")) {
            return query.split("userId=")[1].split("&")[0];
        }
        return null;
    }

    // Obtener usuarios conectados
    public Set<Integer> getConnectedUsers() {
        return userSessions.keySet();
    }

    // Verificar si un usuario está conectado
    public boolean isUserConnected(Integer userId) {
        return userSessions.containsKey(userId) &&
                userSessions.get(userId).isOpen();
    }
}
*/
