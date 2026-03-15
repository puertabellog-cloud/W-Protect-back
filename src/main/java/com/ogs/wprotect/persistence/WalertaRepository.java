package com.ogs.wprotect.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ogs.wprotect.domain.Walert;
import com.ogs.wprotect.domain.repository.WalertRepository;
import com.ogs.wprotect.persistence.crud.WalertaCrudRepository;
import com.ogs.wprotect.persistence.entity.Walerta;
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

    @Override
    public List<Walert> getAll() {
        List<Walerta> entities = (List<Walerta>) walertaCrudRepository.findAll();
        return entities.stream().map(walertMapper::toWalert).collect(Collectors.toList());
    }

    @Override
    public Optional<Walert> getById(Integer id) {
        return walertaCrudRepository.findById(id).map(walertMapper::toWalert);
    }
}