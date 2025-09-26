package com.ogs.wprotect.persistence;

import com.ogs.wprotect.domain.repository.WalertRepository;
import com.ogs.wprotect.persistence.crud.WalertaCrudRepository;
import com.ogs.wprotect.persistence.entity.Walerta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ogs.wprotect.domain.Walert;
import com.ogs.wprotect.persistence.mapper.WalertMapper;

@Repository
public class WalertaRepository implements WalertRepository {
    @Autowired
    private WalertaCrudRepository walertaCrudRepository;
    @Autowired
    private WalertMapper walertMapper;
    @Override
    public Walert save(Walert walert){
        Walerta walerta = walertMapper.toWalerta(walert);
        return walertMapper.toWalert(walertaCrudRepository.save(walerta));
    }
}