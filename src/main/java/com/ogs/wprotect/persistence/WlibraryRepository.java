package com.ogs.wprotect.persistence;

import com.ogs.wprotect.domain.WLibrary;
import com.ogs.wprotect.persistence.crud.WlibraryCrudRepository;
import com.ogs.wprotect.persistence.entity.Wlibrary;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class WlibraryRepository implements com.ogs.wprotect.domain.repository.WlibraryRepository {

    @Autowired
    private WlibraryCrudRepository wlibraryCrudRepository;

    @Override
    public List<WLibrary> getAll() {
        List<WLibrary> result = new ArrayList<>();
        for (Wlibrary entity : wlibraryCrudRepository.findAll()) {
            result.add(toDomain(entity));
        }
        return result;
    }

    @Override
    public Optional<WLibrary> getById(Integer id) {
        return wlibraryCrudRepository.findById(id).map(this::toDomain);
    }

    @Override
    public WLibrary save(WLibrary wLibrary) {
        Wlibrary entity = toEntity(wLibrary);
        Wlibrary saved = wlibraryCrudRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public void deleteById(Integer id) {
        wlibraryCrudRepository.deleteById(id);
    }

    @Override
    public WLibrary patchById(Integer id, String name, String description, String url) {
        Wlibrary entity = wlibraryCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Library with id " + id + " not found"));

        if (name != null) {
            entity.setName(name);
        }
        if (description != null) {
            entity.setDescription(description);
        }
        if (url != null) {
            entity.setUrl(url);
        }

        Wlibrary saved = wlibraryCrudRepository.save(entity);
        return toDomain(saved);
    }

    private WLibrary toDomain(Wlibrary entity) {
        WLibrary domain = new WLibrary();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setUrl(entity.getUrl());
        return domain;
    }

    private Wlibrary toEntity(WLibrary domain) {
        Wlibrary entity = new Wlibrary();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setUrl(domain.getUrl());
        return entity;
    }
}
