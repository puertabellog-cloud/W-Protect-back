package com.ogs.wprotect.domain.repository;

import com.ogs.wprotect.domain.Wcontact;

import java.util.List;
import java.util.Optional;

public interface WcontactRepository {
    Optional<List<Wcontact>> getByWuserId(Integer wuserId);
    Wcontact save(Wcontact wcontact);
    Wcontact delete(Integer id);
}
