package com.principal.pruebaspringbootweb.service;

import com.principal.pruebaspringbootweb.dto.AuthUserDto;
import com.principal.pruebaspringbootweb.model.Role;
import com.principal.pruebaspringbootweb.model.User;
import com.principal.pruebaspringbootweb.repository.RoleRepository;
import com.principal.pruebaspringbootweb.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(AuthUserDto authUserDto) {
        User user = new User();
        user.setEmail(authUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(authUserDto.getPassword()));
        user.setEnabled(authUserDto.isEnabled());
        List<Role> roles = roleRepository.findAllById(authUserDto.getRoleIds());
        user.setRoles(roles);
        userRepository.save(user);
    }
}
