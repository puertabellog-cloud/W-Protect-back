package com.ogs.wprotect.persistence.crud;

import com.ogs.wprotect.persistence.entity.Wcontacto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WcontactoCrudRepository extends CrudRepository<Wcontacto, Integer> {
    Optional<List<Wcontacto>> getByWusuarioId(Integer wusuarioId);

}
