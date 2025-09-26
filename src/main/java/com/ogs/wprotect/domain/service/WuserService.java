package com.ogs.wprotect.domain.service;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.domain.repository.WuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.access.annotation.Secured;

import java.util.Optional;

@Service
public class WuserService implements UserDetailsService {
    @Autowired
    private WuserRepository wuserRepository;
    @Secured("ROLE_ADMIN")
    public Wuser getByEmail(String email){
        return wuserRepository.getByEmail(email);
    }
    public Wuser save(Wuser wuser){
        return wuserRepository.save(wuser);
    }
    public Optional<Wuser> getByDeviceId(String deviceId){
        return wuserRepository.getByDeviceId(deviceId);
    }
    public Optional<Wuser> getByPhone(String phone){
        return wuserRepository.getByPhone(phone);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
