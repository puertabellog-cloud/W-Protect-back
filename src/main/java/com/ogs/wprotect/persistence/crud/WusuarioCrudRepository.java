package com.ogs.wprotect.persistence.crud;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.persistence.entity.Wusuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WusuarioCrudRepository  extends CrudRepository<Wusuario, Integer> {
    Wusuario findByEmail(String email);
    Optional<Wusuario> findByDispositivoId(String dispositivoId);
    Optional<Wusuario> findByTelefono(String telefono);
    Optional<Wusuario> findById(Integer id);
}
