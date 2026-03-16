package com.ogs.wprotect.domain.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsService.class);

    @Value("${twilio.phone.number:}")
    private String twilioPhoneNumber;

    public String sendSms(String toPhoneNumber, String messageBody) {

        try {

            if (twilioPhoneNumber == null || twilioPhoneNumber.isEmpty()) {
                logger.warn("Twilio no configurado. No se puede enviar SMS.");
                return null;
            }

            if (!toPhoneNumber.startsWith("+")) {
                throw new IllegalArgumentException(
                        "El numero de telefono debe incluir codigo de pais (ej: +573001234567)"
                );
            }

            logger.info("Enviando SMS...");
            logger.info("Destino: {}", toPhoneNumber);
            logger.info("Contenido: {}", messageBody);

            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    messageBody
            ).create();

            logger.info("SMS enviado correctamente");
            logger.info("SID: {}", message.getSid());
            logger.info("Estado: {}", message.getStatus());

            return message.getSid();

        } catch (Exception e) {

            logger.error("Error enviando SMS a {}: {}", toPhoneNumber, e.getMessage());
            logger.error("Stack trace:", e);

            return null;
        }
    }

    /**
     * Enviar alerta de emergencia
     */
    public String sendEmergencyAlert(
            String toPhoneNumber,
            String userName,
            String latitude,
            String longitude
    ) {
        String displayName = resolveDisplayName(userName);
        String safeLatitude = (latitude == null || latitude.isBlank()) ? null : latitude.trim();
        String safeLongitude = (longitude == null || longitude.isBlank()) ? null : longitude.trim();

        String messageBody;

        if (safeLatitude != null && safeLongitude != null) {
            String mapsUrl = "maps.google.com/maps?q=" + safeLatitude + "," + safeLongitude;

            messageBody = displayName + " necesita que la ubiques urgentemente. "
                    + "Accede a este link para conocer su ubicacion: "
                    + mapsUrl;
        } else {
            messageBody = displayName + " activo una alerta en WProtect. "
                    + "Intenta comunicarte con ella lo antes posible.";
        }

        logger.info("Alerta de emergencia | Para: {} | Nombre resuelto: {} | Mensaje: {}",
                toPhoneNumber, displayName, messageBody);

        return sendSms(toPhoneNumber, messageBody);
    }

    private String resolveDisplayName(String userName) {
        if (userName == null || userName.isBlank()) {
            return "Tu contacto";
        }

        String value = userName.trim();

        // Si llega correo, convierte a una etiqueta legible en vez de exponer email completo.
        if (value.contains("@")) {
            String localPart = value.substring(0, value.indexOf('@')).replace('.', ' ').replace('_', ' ').trim();
            if (!localPart.isEmpty()) {
                return toTitleCase(localPart);
            }
            return "Tu contacto";
        }

        return value;
    }

    private String toTitleCase(String input) {
        String[] parts = input.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(Character.toUpperCase(p.charAt(0)));
            if (p.length() > 1) {
                sb.append(p.substring(1).toLowerCase());
            }
        }
        return sb.length() == 0 ? "Tu contacto" : sb.toString();
    }

    public boolean isConfigured() {
        return twilioPhoneNumber != null && !twilioPhoneNumber.isEmpty();
    }
}