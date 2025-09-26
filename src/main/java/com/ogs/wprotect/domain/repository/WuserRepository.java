package com.ogs.wprotect.domain.repository;
import com.ogs.wprotect.domain.Wuser;

import java.util.List;
import java.util.Optional;

public interface WuserRepository {

    List<Wuser> getAll();
    Wuser getByEmail(String email);
    Wuser save(Wuser wuser);
    Optional<Wuser> getByDeviceId(String deviceId);
    Optional<Wuser> getByPhone(String phone);
    Optional<Wuser> getById(Integer id);
}