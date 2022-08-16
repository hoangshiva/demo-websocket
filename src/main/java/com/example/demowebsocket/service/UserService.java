package com.example.demowebsocket.service;


import com.example.demowebsocket.domain.Role;
import com.example.demowebsocket.domain.User;
import com.example.demowebsocket.repository.RoleRepository;
import com.example.demowebsocket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User create(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Optional<Role> userRoleOpt = roleRepository.findFirstByName("ROLE_USER");
        user.addRoles(Arrays.asList(userRoleOpt.get()));
        return userRepository.save(user);
    }
}
