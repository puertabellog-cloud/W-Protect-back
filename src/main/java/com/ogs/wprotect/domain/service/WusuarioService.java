package com.ogs.wprotect.domain.service;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.persistence.WusuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.Optional;

public class WusuarioService {
    @Autowired
    private WusuarioRepository wusuarioRepository;
    @Secured("ROLE_ADMIN")
    public Wuser getByEmail(String email){
        return wusuarioRepository.getByEmail(email);
    }
    public Optional<Wuser> getById(Integer id){return wusuarioRepository.getById(id);}
    public Wuser save(Wuser wuser){
        return wusuarioRepository.save(wuser);
    }
    public Optional<Wuser> getByPhone(String phone){
        return wusuarioRepository.getByPhone(phone);
    }

}