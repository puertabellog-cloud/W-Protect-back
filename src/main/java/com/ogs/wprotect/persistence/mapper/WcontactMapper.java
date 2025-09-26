package com.ogs.wprotect.persistence.mapper;

import com.ogs.wprotect.domain.Wcontact;
import com.ogs.wprotect.persistence.entity.Wcontacto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {WuserMapper.class})
public interface WcontactMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "telefono", target = "phone"),
            @Mapping(source = "alias", target = "alias"),
            @Mapping(source = "wusuarioId", target = "userId"),
            @Mapping(source = "wusuario", target = "wuser"),
    })
    Wcontact toWcontact(Wcontacto wcontacto);
    List<Wcontact> toContacts(List<Wcontacto> wcontactos);
    //Optional<List<Wcontact>> toContacts(Optional<List<Wcontacto>> wcontactis);

    @InheritInverseConfiguration
    Wcontacto toWcontacto(Wcontact wcontact);


}
