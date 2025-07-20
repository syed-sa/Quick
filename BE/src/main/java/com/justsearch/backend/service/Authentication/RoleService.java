package com.justsearch.backend.service.Authentication;

import com.justsearch.backend.model.Role;

public interface RoleService {
     Role findByName(String Name);
}
