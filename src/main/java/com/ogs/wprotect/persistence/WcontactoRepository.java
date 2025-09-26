package com.ogs.wprotect.persistence;

import com.ogs.wprotect.domain.Wcontact;
import com.ogs.wprotect.domain.repository.WcontactRepository;
import com.ogs.wprotect.persistence.crud.WcontactoCrudRepository;
import com.ogs.wprotect.persistence.entity.Wcontacto;
import com.ogs.wprotect.persistence.mapper.WcontactMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class WcontactoRepository implements WcontactRepository {
    @Autowired
    private WcontactoCrudRepository wcontactoCrudRepository;
    @Autowired
    private WcontactMapper wcontactMapper;
    @Override
    public Optional<List<Wcontact>> getByWuserId(Integer wuserId){
        Optional<List<Wcontacto>> wcontacto = wcontactoCrudRepository.getByWusuarioId(wuserId);
        return wcontacto.map(act -> wcontactMapper.toContacts(act));
    }
    @Override
    public Wcontact save(Wcontact wcontact){
        Wcontacto wcontacto = wcontactMapper.toWcontacto(wcontact);
        return wcontactMapper.toWcontact(wcontactoCrudRepository.save(wcontacto));
    }
    @Override
    public Wcontact delete(Integer id){
        Wcontacto contacto = wcontactoCrudRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "contacto con id " + id + " no encontrado"));

        wcontactoCrudRepository.delete(contacto);   // elimina la entidad cargada
        return wcontactMapper.toWcontact(contacto);
    }
}
