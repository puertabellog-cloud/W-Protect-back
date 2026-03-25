package com.ogs.wprotect.persistence.mapper;

import com.ogs.wprotect.domain.Wuser;
import com.ogs.wprotect.persistence.entity.Wusuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WuserMapper {
    @Mappings({
            @Mapping(source = "usuarioId", target = "id"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "perfil", target = "profile"),
            @Mapping(source = "telefono", target = "phone"),
            @Mapping(source = "activo", target = "active"),
            @Mapping(source = "emergencyMode", target = "emergencyMode")
            ,@Mapping(source = "password", target = "password")
    })
    Wuser toWuser(Wusuario wusuario);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "wcontactos", ignore = true),
            @Mapping(target = "walertas", ignore = true),
            @Mapping(target = "password", source = "password")
    })
    Wusuario toWusuario(Wuser wuser);
}