package com.ogs.wprotect.domain.service;

import com.ogs.wprotect.domain.Wcontact;
import com.ogs.wprotect.domain.repository.WcontactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WcontactService {
    @Autowired
    private WcontactRepository wcontactRepository;
    public Optional<List<Wcontact>> getByWuserId(Integer wuserId){
        return wcontactRepository.getByWuserId(wuserId);
    }
    public Wcontact save(Wcontact wcontact){
        return  wcontactRepository.save(wcontact);
    }
    public Wcontact delete(Integer id){
        return wcontactRepository.delete(id);
    }
}
