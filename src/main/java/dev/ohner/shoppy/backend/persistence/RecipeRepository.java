package dev.ohner.shoppy.backend.persistence;

import dev.ohner.shoppy.backend.persistence.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, String> {}
