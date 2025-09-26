/*
package com.ogs.oikoom.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmergencyAlertRepository extends JpaRepository<EmergencyAlert, Integer> {

    /**
     * Buscar alertas por usuario ordenadas por fecha descendente

    List<EmergencyAlert> findByUserIdOrderByTimestampDesc(Integer userId);

    /**
     * Buscar alertas donde el teléfono dado es contacto de emergencia
     * Esta query busca alertas de usuarios que tienen el teléfono como contacto

    @Query("SELECT a FROM Walerta a " +
            "WHERE a.user_id IN (" +
            "    SELECT ec.user_id FROM Wcontact ec " +
            "    WHERE ec.phone = :contactPhone" +
            ") " +
            "ORDER BY a.timestamp DESC")
    List<EmergencyAlert> findAlertsForContact(@Param("contactPhone") String contactPhone);

    /**
     * Buscar alertas no vistas para un contacto específico

    @Query("SELECT a FROM Walerta a " +
            "WHERE a.user_id IN (" +
            "    SELECT ec.userId FROM Wcontact ec " +
            "    WHERE ec.phone = :contactPhone" +
            ") " +
            "AND a.seen = false " +
            "ORDER BY a.timestamp DESC")
    List<EmergencyAlert> findUnseenAlertsForContact(@Param("contactPhone") String contactPhone);

    /**
     * Buscar alertas de las últimas 24 horas

    @Query("SELECT a FROM Walerta a " +
            "WHERE a.timestamp >= :since " +
            "ORDER BY a.timestamp DESC")
    List<EmergencyAlert> findRecentAlerts(@Param("since") LocalDateTime since);
}

 */