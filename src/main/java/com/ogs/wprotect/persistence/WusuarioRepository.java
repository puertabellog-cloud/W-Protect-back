package com.ogs.wprotect.persistence;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.repository.WuserRepository;
import com.ogs.wprotect.persistence.crud.WusuarioCrudRepository;
import com.ogs.wprotect.persistence.entity.Wusuario;
import com.ogs.wprotect.persistence.mapper.WuserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return null;
    }

    @Override
    public Wuser getByEmail(String email){
        Wusuario wusuario = wusuarioCrudRepository.findByEmail(email);
        return mapper.toWuser(wusuario);
    }

    @Override
    public Wuser save(Wuser wuser) {
        Wusuario wusuario = mapper.toWusuario(wuser);
        return mapper.toWuser(wusuarioCrudRepository.save(wusuario));
    }
    @Override
    public Optional<Wuser> getByDeviceId(String deviceId){
        return  wusuarioCrudRepository.findByDispositivoId(deviceId).map(wusuario -> mapper.toWuser(wusuario));
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
