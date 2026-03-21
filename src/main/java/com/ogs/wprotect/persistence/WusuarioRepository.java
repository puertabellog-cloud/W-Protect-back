package com.ogs.wprotect.persistence;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.repository.WuserRepository;
import com.ogs.wprotect.persistence.crud.WusuarioCrudRepository;
import com.ogs.wprotect.persistence.entity.Wusuario;
import com.ogs.wprotect.persistence.mapper.WuserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class WusuarioRepository implements WuserRepository {
    @Autowired
    private WusuarioCrudRepository wusuarioCrudRepository;
    @Autowired
    private WuserMapper mapper;

    @Override
    public List<Wuser> getAll() {
        List<Wuser> result = new ArrayList<>();
        for (Wusuario entity : wusuarioCrudRepository.findAll()) {
            result.add(mapper.toWuser(entity));
        }
        return result;
    }

    @Override
    public Wuser getByEmail(String email){
        Wusuario wusuario = wusuarioCrudRepository.findByEmail(email);
        return mapper.toWuser(wusuario);
    }

    @Override
    public Wuser save(Wuser wuser) {
        Wusuario wusuario;
        if (wuser.getId() != 0) {
            // Update: fetch existing entity to preserve password if not changed
            Optional<Wusuario> existingOpt = wusuarioCrudRepository.findById(wuser.getId());
            if (existingOpt.isPresent()) {
                wusuario = existingOpt.get();
                wusuario.setNombre(wuser.getName());
                wusuario.setEmail(wuser.getEmail());
                wusuario.setPerfil(wuser.getProfile());
                wusuario.setTelefono(wuser.getPhone());
                wusuario.setActivo(wuser.isActive());
                wusuario.setDeviceId(wuser.getDeviceId());
                wusuario.setEmergencyMode(wuser.isEmergencyMode());
                if (wuser.getPassword() != null && !wuser.getPassword().isBlank()) {
                    wusuario.setPassword(wuser.getPassword());
                }
            } else {
                wusuario = mapper.toWusuario(wuser);
            }
        } else {
            wusuario = mapper.toWusuario(wuser);
        }
        return mapper.toWuser(wusuarioCrudRepository.save(wusuario));
    }

    @Override
    public Wuser patchById(Integer id, String name, String email, String phone) {
        Wusuario existing = wusuarioCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario con ID " + id + " no encontrado"));
        if (name != null && !name.isBlank()) {
            existing.setNombre(name);
        }
        if (email != null && !email.isBlank()) {
            existing.setEmail(email);
        }
        if (phone != null && !phone.isBlank()) {
            existing.setTelefono(phone);
        }
        return mapper.toWuser(wusuarioCrudRepository.save(existing));
    }

    @Override
    public Optional<Wuser> getByPhone(String phone){
       return wusuarioCrudRepository.findByTelefono(phone).map(wusuario -> mapper.toWuser(wusuario));
    }

    @Override
    public Optional<Wuser> getById(Integer id){
        return wusuarioCrudRepository.findById(id).map(wusuario -> mapper.toWuser(wusuario));
    }
}