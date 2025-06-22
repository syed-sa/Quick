package com.justsearch.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.justsearch.backend.model.BuisnessCategory;
import org.springframework.lang.Nullable;

public interface CategoryRepository extends JpaRepository<BuisnessCategory, Long> {

    // Method to find a category by its name
    @Nullable
    BuisnessCategory findByName(String name);
}
