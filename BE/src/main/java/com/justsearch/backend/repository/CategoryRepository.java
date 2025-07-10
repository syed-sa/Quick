package com.justsearch.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import com.justsearch.backend.model.BuisnessCategory;
@Repository
public interface CategoryRepository extends JpaRepository<BuisnessCategory, Long> {

    // Method to find a category by its name
    @Nullable
    BuisnessCategory findByName(String name);
}
