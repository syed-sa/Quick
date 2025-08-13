
package com.justsearch.backend.dto;
public class RoleDto {
    public RoleDto(String name) {
        this.name = name;
        
    }

    private long id;

    private String name;

    private String description;

    public String getName()
    {
        return name;
    }

}