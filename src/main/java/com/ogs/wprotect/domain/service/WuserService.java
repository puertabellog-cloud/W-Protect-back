package com.ogs.wprotect.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.repository.WuserRepository;

@Service
public class WuserService implements UserDetailsService {
    @Autowired
    private WuserRepository wuserRepository;

    public List<Wuser> getAll(){
        return wuserRepository.getAll();
    }

    public Wuser getByEmail(String email){
        return wuserRepository.getByEmail(email);
    }

    public Optional<Wuser> getById(Integer id){
        return wuserRepository.getById(id);
    }
    
    public Wuser save(Wuser wuser){
        return wuserRepository.save(wuser);
    }

    public Optional<Wuser> getByPhone(String phone){
        return wuserRepository.getByPhone(phone);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}