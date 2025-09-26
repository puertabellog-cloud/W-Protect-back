package com.ogs.wprotect.domain.service;

import com.ogs.wprotect.domain.Walert;
import com.ogs.wprotect.domain.repository.WalertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalertService {
    @Autowired
    private WalertRepository walertRepository;
    public Walert save(Walert walert){
        return walertRepository.save(walert);
    }
}
