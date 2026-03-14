package com.ogs.wprotect.domain.repository;

import java.util.List;
import java.util.Optional;

import com.ogs.wprotect.domain.WLibrary;

public interface WlibraryRepository {

    List<WLibrary> getAll();
    Optional<WLibrary> getById(Integer id);
    WLibrary save(WLibrary wLibrary);
    void deleteById(Integer id);
    WLibrary patchById(Integer id, String name, String description, String url);
}