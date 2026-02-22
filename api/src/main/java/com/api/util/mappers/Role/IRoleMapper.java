package com.api.util.mappers.Role;

import com.api.entities.Role;
import com.api.entities.RoleClaim;
import com.api.models.Role.RoleModel;
import com.api.util.mappers.BaseMapper;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface IRoleMapper extends BaseMapper<Role, RoleModel> {
    @Mapping(target = "claims", source = "roleClaims", qualifiedByName = "claimsFromRoleClaims")
    RoleModel toDto(Role entity);

    @Mapping(target = "roleClaims", source = "claims", qualifiedByName = "claimsToRoleClaims")
    Role toEntity(RoleModel model);

    @Named("claimsFromRoleClaims")
    default ArrayList<String> claimsFromRoleClaims(Set<RoleClaim> roleClaims) {
        ArrayList<String> claims = new ArrayList<>();
        if(!roleClaims.isEmpty()) {
            for (RoleClaim roleClaim : roleClaims) {
                claims.add(roleClaim.getClaim());
            }
        }
        return claims;
    }

    @Named("claimsToRoleClaims")
    default Set<RoleClaim> claimsToRoleClaims(ArrayList<String> claims) {
        if(!claims.isEmpty()) {
            return new HashSet<>(claims.stream().map(claim -> {
                RoleClaim roleClaim = new RoleClaim();
                roleClaim.setClaim(claim);
                return roleClaim;
            }).toList());
        }

        return new HashSet<>();
    }

//    @AfterMapping
//    default void linkRoleClaims(@MappingTarget Role role) {
//        if (role.getRoleClaims() != null) {
//            for (RoleClaim claim : role.getRoleClaims()) {
//                claim.setRole(role);
//            }
//        }
//    }
}
