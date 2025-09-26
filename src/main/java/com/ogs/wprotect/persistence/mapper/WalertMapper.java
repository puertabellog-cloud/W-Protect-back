package com.ogs.wprotect.persistence.mapper;

import com.ogs.wprotect.domain.Walert;
import com.ogs.wprotect.persistence.entity.Walerta;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {WuserMapper.class})
public interface WalertMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "mensaje", target = "message"),
            @Mapping(source = "latitud", target = "latitude"),
            @Mapping(source = "longitud", target = "longitude"),
            @Mapping(source = "timestamp", target = "timestamp"),
            @Mapping(source = "userId", target = "userId"),
            @Mapping(source = "wusuario", target = "wuser"),
            @Mapping(source = "contactosNotificados", target = "contactsNotified"),
    })
    Walert toWalert(Walerta walerta);
    @InheritInverseConfiguration
    Walerta toWalerta(Walert walert);
}
