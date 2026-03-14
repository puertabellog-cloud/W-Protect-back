package com.ogs.wprotect.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogs.wprotect.domain.WLibrary;
import com.ogs.wprotect.domain.repository.WlibraryRepository;

@Service
public class WlibraryService {
    @Autowired
    private WlibraryRepository wlibraryRepository;

    public List<WLibrary> getAll() {
        return wlibraryRepository.getAll();
    }

    public Optional<WLibrary> getById(Integer id) {
        return wlibraryRepository.getById(id);
    }

    public WLibrary save(WLibrary wLibrary) {
        return wlibraryRepository.save(wLibrary);
    }

    public void deleteById(Integer id) {
        wlibraryRepository.deleteById(id);
    }

    public WLibrary patchById(Integer id, String name, String description, String url) {
        return wlibraryRepository.patchById(id, name, description, url);
    }
}
