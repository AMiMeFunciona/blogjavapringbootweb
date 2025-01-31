package com.principal.pruebaspringbootweb.service;


import com.principal.pruebaspringbootweb.dto.RoleDTO;
import com.principal.pruebaspringbootweb.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleDTO> findAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> new ModelMapper().map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

}
