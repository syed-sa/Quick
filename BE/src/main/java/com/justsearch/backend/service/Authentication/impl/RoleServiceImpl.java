package com.justsearch.backend.service.Authentication.impl;

import org.springframework.stereotype.Service;

import com.justsearch.backend.model.Role;
import com.justsearch.backend.repository.RoleRepository;
import com.justsearch.backend.service.Authentication.RoleService;
@Service
public class RoleServiceImpl implements RoleService
 {
        private RoleRepository _roleRepository;

        public RoleServiceImpl(RoleRepository roleRepository)
        {
            _roleRepository = roleRepository;
        }

        public Role findByName(String name)
        {
           Role role = _roleRepository.findRoleByName(name);
           return role;
        }
}
