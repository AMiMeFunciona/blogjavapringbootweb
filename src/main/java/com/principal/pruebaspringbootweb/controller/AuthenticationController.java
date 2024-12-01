package com.principal.pruebaspringbootweb.controller;

import com.principal.pruebaspringbootweb.dto.AuthUserDto;
import com.principal.pruebaspringbootweb.dto.RoleDTO;
import com.principal.pruebaspringbootweb.service.RoleService;
import com.principal.pruebaspringbootweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public String showLoginView(Model model) {
        model.addAttribute("authUserDto", new AuthUserDto());
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setEnabled(true);
        List<RoleDTO> roles = roleService.findAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("authUserDto",authUserDto);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute AuthUserDto authUserDto) {
        userService.registerUser(authUserDto);
        return "redirect:/auth/login";
    }

}
