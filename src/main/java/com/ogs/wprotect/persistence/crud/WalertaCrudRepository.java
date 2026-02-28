package com.ogs.wprotect.persistence.crud;

import com.ogs.wprotect.persistence.entity.Walerta;
import com.ogs.wprotect.persistence.entity.AlertStatus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WalertaCrudRepository extends CrudRepository<Walerta, Integer> {
    List<Walerta> findByStatusAndExpiresAtBefore(AlertStatus status, LocalDateTime time);
}
