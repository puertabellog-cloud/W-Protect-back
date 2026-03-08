package com.ogs.wprotect.domain.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.twilio.Twilio;

@Service
public class TwilioSmsService {
    
    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsService.class);

    @Value("${twilio.phone.number:}")
    private String twilioPhoneNumber;

    /**
     * Envía un SMS usando Twilio
     * @param toPhoneNumber Número de teléfono destino (formato: +573001234567)
     * @param messageBody Contenido del mensaje
     * @return SID del mensaje enviado
     */
    public String sendSms(String toPhoneNumber, String messageBody) {
        try {
            // Validar que Twilio esté configurado
            if (twilioPhoneNumber == null || twilioPhoneNumber.isEmpty()) {
                logger.warn("⚠ Twilio no configurado. No se puede enviar SMS.");
                return null;
            }

            // Validar formato del número destino
            if (!toPhoneNumber.startsWith("+")) {
                throw new IllegalArgumentException("El número de teléfono debe incluir el código de país (ej: +573001234567)");
            }

            // Enviar SMS
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),        // TO
                    new PhoneNumber(twilioPhoneNumber),    // FROM
                    messageBody                             // BODY
            ).create();

            logger.info("✓ SMS enviado correctamente. SID: {}", message.getSid());
            logger.info("  → Destino: {}", toPhoneNumber);
            logger.info("  → Estado: {}", message.getStatus());

            return message.getSid();

        } catch (Exception e) {
            logger.error("✗ Error enviando SMS a {}: {}", toPhoneNumber, e.getMessage());
            logger.error("Stack trace:", e);
            return null;
        }
    }

    /**
     * Envía SMS de alerta de emergencia
     * @param toPhoneNumber Número destino
     * @param userName Nombre del usuario en emergencia
     * @param latitude Latitud
     * @param longitude Longitud
     * @param message Mensaje de la alerta
     * @return SID del mensaje
     */
    public String sendEmergencyAlert(String toPhoneNumber, String userName, 
                                     String latitude, String longitude, String message) {
        String messageBody = String.format(
                "🚨 ALERTA DE EMERGENCIA 🚨\n\n" +
                "%s necesita ayuda:\n" +
                "\"%s\"\n\n" +
                "Ubicación:\n" +
                "https://maps.google.com/?q=%s,%s\n\n" +
                "Por favor, revisa la app W-Protect para más detalles.",
                userName, message, latitude, longitude
        );

        return sendSms(toPhoneNumber, messageBody);
    }

    /**
     * Verifica si Twilio está configurado correctamente
     * @return true si está configurado, false si no
     */
    public boolean isConfigured() {
        return twilioPhoneNumber != null && !twilioPhoneNumber.isEmpty();
    }
}
