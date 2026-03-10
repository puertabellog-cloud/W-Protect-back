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
        String safeUserName = (userName == null || userName.isBlank()) ? "usuario" : userName.trim();
        String safeLatitude = (latitude == null || latitude.isBlank()) ? "sin-dato" : latitude.trim();
        String safeLongitude = (longitude == null || longitude.isBlank()) ? "sin-dato" : longitude.trim();

        String messageBody = "WPROTECT alerta de emergencia de " + safeUserName
                + ". Ubicacion: " + safeLatitude + "," + safeLongitude;

        return sendSms(toPhoneNumber, messageBody);
    }

    public boolean isConfigured() {
        return twilioPhoneNumber != null && !twilioPhoneNumber.isEmpty();
    }
}