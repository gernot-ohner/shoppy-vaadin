package dev.ohner.shoppy.backend.persistence;

import dev.ohner.shoppy.backend.persistence.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

    @Query("select i from Ingredient i where i.name LIKE concat('%', :searchTerm, '%') ")
    List<Ingredient> search(String searchTerm);
}
