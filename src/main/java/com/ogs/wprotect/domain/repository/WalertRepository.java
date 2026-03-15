package com.ogs.wprotect.domain.repository;
import com.ogs.wprotect.domain.Walert;

import java.util.List;
import java.util.Optional;

public interface WalertRepository {
    Walert save(Walert walert);
    List<Walert> getAll();
    Optional<Walert> getById(Integer id);
}
